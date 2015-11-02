package com.lzadrija.account;

import com.lzadrija.MainConfiguration;
import com.lzadrija.account.registration.AccountVerificationException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainConfiguration.class)
public class AccountFactoryTest {

    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]*$";

    @Mock
    private Environment env;
    @Mock
    private AccountRepository repo;
    @InjectMocks
    private AccountFactory factory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        factory = new AccountFactory(repo, env);
    }

    @Test
    public void whenGivenValidIdShouldCreateAccount() {

        String id = "Floyd%1956";
        when(repo.save(any(Account.class))).then(i -> i.getArgumentAt(0, Account.class));

        Account a = factory.create(id);

        assertThat(a).isNotNull();
        assertThat(a).isEqualToIgnoringGivenFields(new Account(id, null), "password");
        assertThat(a.getPassword()).isNotNull();
        assertThat(a.getPassword()).hasSize(8);
        assertThat(a.getPassword()).matches(ALPHANUMERIC_REGEX);
    }

    @Test(expected = AccountVerificationException.class)
    public void whenGivenExistingIdShouldFailToCreateAccount() {

        String id = "Eorl$1988";
        when(repo.findOne(id)).thenReturn(new Account(id, null));
        when(env.getRequiredProperty("accountId.already.exists")).thenReturn("Account ID: " + id + " already exists");

        factory.create(id);
    }
}
