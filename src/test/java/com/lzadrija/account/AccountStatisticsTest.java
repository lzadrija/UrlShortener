package com.lzadrija.account;

import com.lzadrija.BaseControllerTest;
import com.lzadrija.ResultDescription;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountStatisticsTest extends BaseControllerTest {

    @Autowired
    private AccountRepository repo;
    @Autowired
    private Filter springSecurityFilterChain;

    @Mock
    private AccountRegisteredUrlService registeredUrlService;
    @InjectMocks
    private AccountController controller;

    private MockMvc mockMvc;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        mockMvc = createBasicMockMvcBuilder(controller)
                .apply(springSecurity(springSecurityFilterChain)).build();
    }

    @After
    public void tearDown() {
        repo.deleteAll();
    }

    @Test
    public void givenRegisteredAccountShouldGetUrlStatistic() throws Exception {

        Account a = new Account("Smaug", "4d8tg8Tg");
        repo.save(a);

        Map<String, Long> statistic = new HashMap<>();
        statistic.put("http://google.com", 123L);
        when(registeredUrlService.getStatisticForAccount(a.getId())).thenReturn(statistic);

        mockMvc.perform(get(URI.create("/statistic/" + a.getId())).with(httpBasic(a.getId(), a.getPassword())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(toJson(statistic)))
                .andDo(print());
    }

    @Test
    public void givenNonExistentAccountShouldReturnUnauthorized() throws Exception {

        Account a = new Account("Sar_-+!$uman", "5986rdH4");

        mockMvc.perform(get(URI.create("/statistic/" + a.getId())).with(httpBasic(a.getId(), a.getPassword())))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(toJson(new ResultDescription("Bad credentials", false))))
                .andDo(print());
    }

}
