package com.lzadrija.account;

import com.lzadrija.exception.RegistrationException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountFactoryTest {

    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]*$";

    @Mock
    private AccountRepository repo;
    @InjectMocks
    private AccountFactory factory;

    @Test
    public void givenValidIdShouldCreateAccount() {

        String id = "Floyd%1956";
        when(repo.save(any(Account.class))).then(i -> i.getArgumentAt(0, Account.class));

        Account a = factory.create(id);

        assertThat(a).isNotNull();
        assertThat(a).isEqualToIgnoringGivenFields(new Account(id, null), "password");
        assertThat(a.getPassword()).isNotNull();
        assertThat(a.getPassword()).hasSize(8);
        assertThat(a.getPassword()).matches(ALPHANUMERIC_REGEX);
    }

    @Test(expected = RegistrationException.class)
    public void givenExistingIdShouldFailToCreateAccount() {

        String id = "Eorl$1988";
        when(repo.exists(id)).thenReturn(true);

        factory.create(id);
    }
}
