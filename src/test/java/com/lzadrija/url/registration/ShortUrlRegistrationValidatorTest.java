package com.lzadrija.url.registration;

import com.lzadrija.BaseTest;
import com.lzadrija.url.UrlRepository;
import javax.servlet.http.HttpServletRequest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

public class ShortUrlRegistrationValidatorTest extends BaseTest {

    private static final String VALID_SERVER_ADDRESS = "http://localhost:8181/";

    @Mock
    private HttpServletRequest request;
    @Mock
    private ServiceAddressFactory addressFactory;
    @Mock
    private UrlRepository urlRepo;
    @InjectMocks
    private ShortUrlRegistrationValidator validator;

    @Test
    public void givenRegisteredShortUrlWithDomainShouldReturnTrue() {

        String url = VALID_SERVER_ADDRESS + "ab";
        when(addressFactory.create(request)).thenReturn(VALID_SERVER_ADDRESS);
        when(urlRepo.exists("ab")).thenReturn(true);

        boolean result = validator.isRegisteredShortUrlWithDomain(url);

        assertThat(result).isTrue();
    }

    @Test
    public void givenShortUrlShouldReturnFalseForIsRegisteredShortUrlWithDomain() {

        String url = "ab";
        when(addressFactory.create(request)).thenReturn(VALID_SERVER_ADDRESS);

        boolean result = validator.isRegisteredShortUrlWithDomain(url);

        assertThat(result).isFalse();
    }

    @Test
    public void givenServerAddressShouldReturnFalseForIsRegisteredShortUrlWithDomain() {

        String url = VALID_SERVER_ADDRESS;
        when(addressFactory.create(request)).thenReturn(VALID_SERVER_ADDRESS);

        boolean result = validator.isRegisteredShortUrlWithDomain(url);

        assertThat(result).isFalse();
    }

    @Test
    public void givenRegisteredShortUrlShouldReturnTrueForIsRegisteredShortUrl() {

        String url = "ab";
        when(urlRepo.exists(url)).thenReturn(true);

        boolean result = validator.isRegisteredShortUrl(url);

        assertThat(result).isTrue();
    }

    @Test
    public void givenRegisteredShortUrlWithDomainShouldReturnFalseForIsRegisteredShortUrl() {

        String url = VALID_SERVER_ADDRESS + "ab";
        when(urlRepo.exists(url)).thenReturn(false);

        boolean result = validator.isRegisteredShortUrl(url);

        assertThat(result).isFalse();
    }

}
