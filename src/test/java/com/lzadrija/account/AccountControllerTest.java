package com.lzadrija.account;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzadrija.MainConfiguration;
import com.lzadrija.account.registration.AccountExceptionsHandler;
import com.lzadrija.account.registration.AccountId;
import com.lzadrija.account.registration.AccountRegistration;
import com.lzadrija.account.registration.AccountRegistrationException;
import java.lang.reflect.Method;
import java.net.URI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MainConfiguration.class)
public class AccountControllerTest {

    private ObjectMapper jsonMapper;

    @Mock
    private Environment env;
    @Mock
    private AccountFactory factory;
    @InjectMocks
    private AccountController controller;
    private MockMvc mockMvc;

    @Before
    public void setUp() {

        jsonMapper = new ObjectMapper();
        jsonMapper.setSerializationInclusion(Include.NON_NULL);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setHandlerExceptionResolvers(createExceptionResolver(), new ResponseStatusExceptionResolver())
                .build();
    }

    private ExceptionHandlerExceptionResolver createExceptionResolver() {

        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver() {

            @Override
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {

                Method method = new ExceptionHandlerMethodResolver(AccountExceptionsHandler.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new AccountExceptionsHandler(), method);
            }
        };
        resolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        resolver.afterPropertiesSet();
        return resolver;
    }

    @Test
    public void whenAccountIdExistCreationShouldFail() throws Exception {

        String id = "Gimli*1980";
        String exMsg = "Account ID: " + id + " already exists";
        when(factory.create(id)).thenThrow(new AccountRegistrationException(exMsg));

        RequestBuilder req = post(URI.create("/account")).contentType(MediaType.APPLICATION_JSON).content(toJson(new AccountId(id)));

        mockMvc.perform(req)
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(toJson(new AccountRegistration(exMsg, false))))
                .andDo(print());
    }

    @Test
    public void whenGivenValidIdCreateAccount() throws Exception {

        Account a = new Account("Hodor(1936", "s4tbG685");
        String msg = "Your account is opened";
        when(factory.create(a.getId())).thenReturn(a);
        when(env.getRequiredProperty("account.opened")).thenReturn(msg);

        RequestBuilder req = post(URI.create("/account")).contentType(MediaType.APPLICATION_JSON).content(toJson(new AccountId(a.getId())));
        mockMvc.perform(req)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(toJson(AccountRegistration.create(msg, true, a))))
                .andDo(print());
    }

    @Test
    public void whenAccountIdIsBlankCreationShouldFail() throws Exception {

        String id = "                 ";

        RequestBuilder req = post(URI.create("/account")).contentType(MediaType.APPLICATION_JSON).content(toJson(new AccountId(id)));

        String exMsg = String.format("[accountId = \"%s\", may not be empty]", id);
        mockMvc.perform(req)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(toJson(new AccountRegistration(exMsg, false))))
                .andDo(print());
    }

    private String toJson(Object value) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(value);
    }
}
