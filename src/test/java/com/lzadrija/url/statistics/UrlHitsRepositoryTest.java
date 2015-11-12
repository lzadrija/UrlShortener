package com.lzadrija.url.statistics;

import com.lzadrija.BaseTest;
import com.lzadrija.account.Account;
import com.lzadrija.account.AccountRepository;
import com.lzadrija.url.RedirectUrl;
import com.lzadrija.url.UrlRepository;
import com.lzadrija.url.registration.UrlRegistrationData;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UrlHitsRepositoryTest extends BaseTest {

    @Autowired
    private UrlHitsRepository hitsRepo;
    @Autowired
    private UrlRepository urlRepo;
    @Autowired
    private AccountRepository accRepo;

    @After
    public void tearDown() {
        hitsRepo.deleteAll();
        urlRepo.deleteAll();
        accRepo.deleteAll();
    }

    @Test
    public void givenRedirectUrlShouldCountHits() {

        Account account = new Account("IllyrioMopatis{", "rtd4vG4v");
        RedirectUrl redirectUrl = RedirectUrl.create(account, "Aeg0n",
                                                     new UrlRegistrationData("http://asoiaf.westeros.org/index.php?/topic/87031-illyrio-and-varys-plan/"));
        UrlHit urlHit = UrlHit.create(redirectUrl);
        accRepo.save(account);
        urlRepo.save(redirectUrl);
        hitsRepo.save(urlHit);

        long count = hitsRepo.countByRedirectUrl(redirectUrl);

        assertThat(count).isEqualTo(1L);
    }

}
