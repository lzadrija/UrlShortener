package com.lzadrija.url;

import com.lzadrija.BaseControllerTest;
import com.lzadrija.ResultDescription;
import com.lzadrija.account.Account;
import com.lzadrija.account.AccountRepository;
import com.lzadrija.url.registration.UrlRegistrationData;
import java.net.URI;
import javax.servlet.Filter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.MULTIPLE_CHOICES;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UrlControllerConstraintsTest extends BaseControllerTest {

    @Autowired
    private AccountRepository repo;
    @Autowired
    private Filter springSecurityFilterChain;
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
    public void givenNullUrlRegistrationShouldFail() throws Exception {

        Account a = new Account("Spot1", "f4GRE87t");
        repo.save(a);

        String longUrl = null;
        String exMsg = String.format("[url = \"%s\", %s]", longUrl, "may not be null");

        callAndAssertRegistrationDataBadRequest(a, new UrlRegistrationData(longUrl), exMsg);
    }

    @Test
    public void givenInvalidUrlRegistrationShouldFail() throws Exception {

        Account a = new Account("Spot2", "yaw95sda");
        repo.save(a);

        String longUrl = "notAUrl";
        String exMsg = String.format("[url = \"%s\", %s]", longUrl, "must be a valid URL");

        callAndAssertRegistrationDataBadRequest(a, new UrlRegistrationData(longUrl), exMsg);
    }

    @Test
    public void givenUrlWithInvalidCharactersRegistrationShouldFail() throws Exception {

        Account a = new Account("Spot3", "m38jKh98");
        repo.save(a);

        String longUrl = "http://tjnrtyj\\+48rhger()?)(*&^%$#";

        String exMsg = String.format("[url = \"%s\", %s]", longUrl, "must be a valid URL");

        callAndAssertRegistrationDataBadRequest(a, new UrlRegistrationData(longUrl), exMsg);
    }

    @Test
    public void givenInvalidRedirectTypeRegistrationShouldFail() throws Exception {

        Account a = new Account("Spot4", "2m3IH42w");
        repo.save(a);

        String longUrl = "https://www.youtube.com/watch?v=auHXg1H0bAQ";
        int redirectType = MULTIPLE_CHOICES.value();
        String exMsg = String.format("[redirectType = \"%d\", %s]", redirectType,
                                     "Redirect type must be set to one of these two HTTP statuses: MOVED_PERMANENTLY(301) or FOUND(302)");
        callAndAssertRegistrationDataBadRequest(a, new UrlRegistrationData(longUrl, redirectType), exMsg);
    }

    private void callAndAssertRegistrationDataBadRequest(Account a, UrlRegistrationData data, String exMsg) throws Exception {

        RequestBuilder req = post(URI.create("/register")).with(httpBasic(a.getId(), a.getPassword())).contentType(APPLICATION_JSON).content(toJson(data));

        mockMvc.perform(req)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(toJson(new ResultDescription(exMsg, false))))
                .andDo(print());
    }

}
