package com.lzadrija.url;

import com.lzadrija.BaseTest;
import com.lzadrija.account.Account;
import com.lzadrija.account.AccountRepository;
import com.lzadrija.url.registration.UrlRegistrationData;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UrlRepositoryTest extends BaseTest {

    @Autowired
    private UrlRepository urlRepo;
    @Autowired
    private AccountRepository accRepo;

    @After
    public void tearDown() {
        urlRepo.deleteAll();
        accRepo.deleteAll();
    }

    @Test
    public void shouldfindAllByAccount() {

        Account account = new Account("Khaleesi14", "d4rtgyvt");
        RedirectUrl firstUrl = RedirectUrl.create(account, "Dr0G0n",
                                                  new UrlRegistrationData("http://thewertzone.blogspot.hr/2012/04/mapping-daeneryss-journey-in-song-of.html"));
        RedirectUrl secondUrl = RedirectUrl.create(account, "rhAeGal",
                                                   new UrlRegistrationData("http://gameofthrones.wikia.com/wiki/Liberation_of_Slaver's_Bay"));
        accRepo.save(account);
        urlRepo.save(firstUrl);
        urlRepo.save(secondUrl);

        List<RedirectUrl> foundUrls = urlRepo.findAllByAccount(account);

        assertThat(foundUrls).contains(firstUrl, secondUrl);
    }

}
