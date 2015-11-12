package com.lzadrija.url.registration;

import com.lzadrija.BaseTest;
import com.lzadrija.account.Account;
import com.lzadrija.account.AccountRepository;
import com.lzadrija.exception.RegistrationException;
import com.lzadrija.url.RedirectUrl;
import com.lzadrija.url.UrlRepository;
import com.lzadrija.url.shortening.UrlShortener;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

public class UrlRegistrationServiceTest extends BaseTest {

    @Mock
    private AccountRepository accountRepo;
    @Mock
    private ShortUrlRegistrationValidator validator;
    @Mock
    private UrlShortener shortener;
    @Mock
    private UrlRepository urlRepo;
    @InjectMocks
    private UrlRegistrationService service;

    @Test
    public void givenRedirectUrlForExistingAccountShouldRegister() {

        Account a = new Account("Lann+The2Clever", null);
        UrlRegistrationData regData = new UrlRegistrationData("http://awoiaf.westeros.org/index.php/House_Lannister");
        String shortUrl = "ab";
        RedirectUrl expectedUrl = RedirectUrl.create(a, shortUrl, regData);

        when(accountRepo.getOne(a.getId())).thenReturn(a);
        when(validator.isRegisteredShortUrlWithDomain(regData.getUrl())).thenReturn(false);
        when(shortener.createShortUrl()).thenReturn(shortUrl);
        when(urlRepo.save(any(RedirectUrl.class))).thenReturn(expectedUrl);

        RedirectUrl registeredUrl = service.register(a.getId(), regData);

        assertThat(registeredUrl).isEqualTo(expectedUrl);
        assertThat(registeredUrl).isEqualToComparingFieldByField(expectedUrl);
    }

    @Test(expected = RegistrationException.class)
    public void givenRegisteredShortUrlWithDomainForExistingAccountShouldThrowException() {

        Account a = new Account("Brann8The+Builder9", "1we1G8b6");
        UrlRegistrationData regData = new UrlRegistrationData("http://localhost:8181/sTarK", MOVED_PERMANENTLY.value());

        when(accountRepo.getOne(a.getId())).thenReturn(a);
        when(validator.isRegisteredShortUrlWithDomain(regData.getUrl())).thenReturn(true);

        service.register(a.getId(), regData);
    }

}
