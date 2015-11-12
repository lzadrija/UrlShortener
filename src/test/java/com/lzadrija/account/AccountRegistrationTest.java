package com.lzadrija.account;

import com.lzadrija.BaseControllerTest;
import com.lzadrija.account.registration.AccountId;
import com.lzadrija.account.registration.AccountRegistration;
import com.lzadrija.exception.RegistrationException;
import java.net.URI;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountRegistrationTest extends BaseControllerTest {

    @Mock
    private AccountFactory factory;

    @InjectMocks
    private AccountController controller;
    private MockMvc mockMvc;

    @Before
    @Override
    public void setUp() {

        super.setUp();
        mockMvc = createBasicMockMvcBuilder(controller).build();
    }

    @Test
    public void givenExistingAccountIdCreationShouldFail() throws Exception {

        String id = "Gimli1980";
        String exMsg = "Account ID: " + id + " already exists";
        when(factory.create(id)).thenThrow(new RegistrationException(exMsg));

        RequestBuilder req = post(URI.create("/account")).contentType(APPLICATION_JSON).content(toJson(new AccountId(id)));

        mockMvc.perform(req)
                .andExpect(status().isConflict())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(toJson(new AccountRegistration(exMsg, false))))
                .andDo(print());
    }

    @Test
    public void givenValidIdShouldCreateAccount() throws Exception {

        Account a = new Account("_-+!$", "s4tbG685");
        when(factory.create(a.getId())).thenReturn(a);

        RequestBuilder req = post(URI.create("/account")).contentType(APPLICATION_JSON).content(toJson(new AccountId(a.getId())));
        mockMvc.perform(req)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(toJson(AccountRegistration.create(a))))
                .andDo(print());
    }

    @Test
    public void givenBlankAccountIdCreationShouldFail() throws Exception {

        String id = null;
        String exMsg = getExMsg(id, "may not be null");

        callAndAssertAccountIdBadRequest(id, exMsg);
    }

    @Test
    public void givenAccountIdWithSlashesCreationShouldFail() throws Exception {

        String id = "Merry/Pippin";
        String exMsg = getExMsg(id, "Account ID can only contain (min 3 and max 15): alphanumeric and special characters: _-+!$");

        callAndAssertAccountIdBadRequest(id, exMsg);
    }

    private void callAndAssertAccountIdBadRequest(String id, String exMsg) throws Exception {

        RequestBuilder req = post(URI.create("/account")).contentType(APPLICATION_JSON).content(toJson(new AccountId(id)));

        mockMvc.perform(req)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(toJson(new AccountRegistration(exMsg, false))))
                .andDo(print());
    }

    private String getExMsg(String id, String description) {
        return String.format("[accountId = \"%s\", %s]", id, description);
    }

}
