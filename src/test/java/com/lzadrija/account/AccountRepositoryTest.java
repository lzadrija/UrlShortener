package com.lzadrija.account;

import com.lzadrija.MainConfiguration;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainConfiguration.class)
@WebAppConfiguration
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository repo;

    @After
    public void tearDown() {
        repo.deleteAll();
    }

    @Test
    public void shouldStoreGivenAccount() {

        Account acc = new Account("Bob?1986", "xC345Fc0");

        Account savedAcc = repo.save(acc);

        assertThat(savedAcc).isEqualToComparingFieldByField(acc);
    }

    @Test
    public void whenGivenIdShouldFindAccount() {

        Account savedAcc = repo.saveAndFlush(new Account("Alice!1987", "b1Tr86yh"));

        Account foundAcc = repo.findOne(savedAcc.getId());

        assertThat(savedAcc).isEqualToComparingFieldByField(foundAcc);
    }

    @Test
    public void shouldFindAllAccounts() {

        List<Account> accs = Arrays.asList(new Account("Ciril#1977", "t84Y6hTr"), new Account("Don#1966", "dR48614r"));
        List<Account> savedAccs = repo.save(accs);

        List<Account> foundAccs = repo.findAll();

        assertThat(foundAccs).containsOnlyElementsOf(savedAccs);
    }

}
