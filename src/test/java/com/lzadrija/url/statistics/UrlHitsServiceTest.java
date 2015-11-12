package com.lzadrija.url.statistics;

import com.lzadrija.BaseTest;
import com.lzadrija.account.Account;
import com.lzadrija.exception.ResourceNotFoundException;
import com.lzadrija.url.RedirectUrl;
import com.lzadrija.url.UrlRepository;
import com.lzadrija.url.registration.ShortUrlRegistrationValidator;
import com.lzadrija.url.registration.UrlRegistrationData;
import java.util.Arrays;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

public class UrlHitsServiceTest extends BaseTest {

    @Mock
    private ShortUrlRegistrationValidator validator;
    @Mock
    private UrlRepository urlRepo;
    @Mock
    private UrlHitsRepository hitsRepo;
    @InjectMocks
    private UrlHitsService service;

    @Test
    public void whenGivenShortUrlShouldRecordHit() {

        String shortUrl = "0k0";
        RedirectUrl redirectUrl = RedirectUrl.create(new Account("Mithrandir3434", "415rf44g"), shortUrl,
                                                     new UrlRegistrationData("http://tolkiengateway.net/wiki/Of_the_Coming_of_the_Elves_and_the_Captivity_of_Melkor"));
        UrlHit hit = UrlHit.create(redirectUrl);
        when(validator.isRegisteredShortUrl(shortUrl)).thenReturn(true);
        when(urlRepo.findOne(shortUrl)).thenReturn(redirectUrl);
        when(hitsRepo.save(any(UrlHit.class))).thenReturn(hit);

        UrlHit createdHit = service.record(shortUrl);

        assertThat(createdHit).isEqualTo(hit);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void givenNotExistentShortUrlShouldThrowException() {

        String shortUrl = "TriBute";
        when(validator.isRegisteredShortUrl(shortUrl)).thenReturn(false);

        service.record(shortUrl);
    }

    @Test
    public void givenAccountShouldGetHitsByLongUrl() {

        Account account = new Account("Bella+d0nna", "df68h5Jh");
        String longUrl = "http://askmiddlearth.tumblr.com/post/40752487001/gandalf-and-the-tooks";
        RedirectUrl redirectUrl = RedirectUrl.create(account, "d0NNAa", new UrlRegistrationData(longUrl));
        long expectedCount = 56L;
        when(urlRepo.findAllByAccount(account)).thenReturn(Arrays.asList(redirectUrl));
        when(hitsRepo.countByRedirectUrl(redirectUrl)).thenReturn(expectedCount);

        Map<String, Long> hitsByLongUrl = service.getHitsByLongUrl(account);

        assertThat(hitsByLongUrl).containsEntry(longUrl, expectedCount);
    }

}
