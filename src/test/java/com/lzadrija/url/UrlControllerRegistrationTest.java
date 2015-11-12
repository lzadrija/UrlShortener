package com.lzadrija.url;

import com.lzadrija.BaseControllerTest;
import com.lzadrija.ResultDescription;
import com.lzadrija.account.Account;
import com.lzadrija.account.AccountRepository;
import com.lzadrija.exception.RegistrationException;
import com.lzadrija.url.registration.ServerAddressFactory;
import com.lzadrija.url.registration.UrlRegistrationData;
import com.lzadrija.url.registration.UrlRegistrationService;
import java.net.URI;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UrlControllerRegistrationTest extends BaseControllerTest {

    @Autowired
    private AccountRepository repo;
    @Autowired
    private Filter springSecurityFilterChain;

    @Mock
    private UrlRegistrationService urlRegService;
    @Mock
    private ServerAddressFactory serverAddressFactory;
    @InjectMocks
    private UrlController controller;

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
    public void givenValidUrlRegistrationDataShouldRegister() throws Exception {

        Account a = new Account("CapitainKirk", "VSr4492v");
        repo.save(a);

        String shortUrl = "a";
        String address = "http://localhost:8080/" + shortUrl;
        UrlRegistrationData data = new UrlRegistrationData("https://www.youtube.com/watch?v=tkBVDh7my9Q");
        RedirectUrl redirectUrl = RedirectUrl.create(a, shortUrl, data);

        when(urlRegService.register(a.getId(), data)).thenReturn(redirectUrl);
        when(serverAddressFactory.create(any(HttpServletRequest.class))).thenReturn(address);

        RequestBuilder req = post(URI.create("/register")).with(httpBasic(a.getId(), a.getPassword())).contentType(APPLICATION_JSON).content(toJson(data));
        mockMvc.perform(req)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(toJson(ShortUrlResource.create(redirectUrl, address))))
                .andDo(print());
    }

    @Test
    public void givenNonExistingAccountShouldReturnUnauthorized() throws Exception {

        Account a = new Account("Harry$Kim", "etS054hv");
        UrlRegistrationData data = new UrlRegistrationData("https://www.youtube.com/watch?v=w6EyYoNxXK8");

        RequestBuilder req = post(URI.create("/register")).with(httpBasic(a.getId(), a.getPassword())).contentType(APPLICATION_JSON).content(toJson(data));
        mockMvc.perform(req)
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(toJson(new ResultDescription("Bad credentials", false))))
                .andDo(print());
    }

    @Test
    public void givenRegisteredShortUrlWithDomainRegistrationShouldFail() throws Exception {

        Account a = new Account("Lore2", "r598Y6u7");
        repo.save(a);

        String longUrl = "https://www.youtube.com/watch?v=DBT7nUxJpC4";
        UrlRegistrationData data = new UrlRegistrationData(longUrl);
        String exMsg = "URL: \"" + longUrl + "\" is an already registered short URL";
        when(urlRegService.register(a.getId(), data)).thenThrow(new RegistrationException(exMsg));

        RequestBuilder req = post(URI.create("/register")).with(httpBasic(a.getId(), a.getPassword())).contentType(APPLICATION_JSON).content(toJson(data));
        mockMvc.perform(req)
                .andExpect(status().isConflict())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(toJson(new ResultDescription(exMsg, false))))
                .andDo(print());
    }

}
