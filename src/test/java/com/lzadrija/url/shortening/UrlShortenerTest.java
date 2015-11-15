package com.lzadrija.url.shortening;

import com.lzadrija.url.UrlRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UrlShortenerTest {

    private final ShortUrlSymbols symbols = ShortUrlSymbols.generate();
    @Mock
    private UrlRepository urlRepo;
    @InjectMocks
    private UrlShortener shortener;

    @Test
    public void givenFirstUrlShouldShortenWithFirstCharInBase() {
        generateBasedOnCountAndAssertShortUrl(0L, "a");
    }

    @Test
    public void givenAlmostBaseNumOfUrlsShouldShortenWithLastCharInBase() {
        generateBasedOnCountAndAssertShortUrl(symbols.getBase() - 1, "9");
    }

    @Test
    public void givenBaseNumOfUrlsShouldShortenWithTwoChars() {
        generateBasedOnCountAndAssertShortUrl(symbols.getBase(), "ba");
    }

    @Test
    public void givenAlmostBaseNumSquareOfUrlsShouldShortenWithTwoLastCharsInBase() {
        generateBasedOnCountAndAssertShortUrl(symbols.getBase() * symbols.getBase() - 1, "99");
    }

    @Test
    public void givenBaseNumSquareOfUrlsShouldShortenWithThreeChars() {
        generateBasedOnCountAndAssertShortUrl(symbols.getBase() * symbols.getBase(), "baa");
    }

    @Test
    public void givenLargeNumOfUrlsShortenWithMoreChars() {
        generateBasedOnCountAndAssertShortUrl(new Double(Math.pow(2, 36)).longValue(), "bnaN5hc");
    }

    private void generateBasedOnCountAndAssertShortUrl(long registeredUrlsCount, String expectedShortUrl) {

        when(urlRepo.count()).thenReturn(registeredUrlsCount);

        String shortUrl = shortener.createShortUrl();

        assertThat(shortUrl).isNotNull();
        assertThat(shortUrl).isEqualTo(expectedShortUrl);
    }

}
