package com.lzadrija.account;

import com.lzadrija.BaseTest;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountRepositoryTest extends BaseTest {

    @Autowired
    private AccountRepository repo;

    @After
    public void tearDown() {
        repo.deleteAll();
    }

    @Test
    public void givenAccountShouldStoreIt() {

        Account acc = new Account("Bob?1986", "xC345Fc0");

        Account savedAcc = repo.save(acc);

        assertThat(savedAcc).isEqualToComparingFieldByField(acc);
    }

    @Test
    public void givenIdShouldFindAccount() {

        Account savedAcc = repo.saveAndFlush(new Account("Alice!1987", "b1Tr86yh"));

        Account foundAcc = repo.findOne(savedAcc.getId());

        assertThat(savedAcc).isEqualToComparingFieldByField(foundAcc);
    }

    @Test
    public void shouldFindAllAccounts() {

        List<Account> accs = Arrays.asList(new Account("Ciril+1977", "t84Y6hTr"), new Account("Don#1966", "dR48614r"));
        List<Account> savedAccs = repo.save(accs);

        List<Account> foundAccs = repo.findAll();

        assertThat(foundAccs).containsOnlyElementsOf(savedAccs);
    }

}
