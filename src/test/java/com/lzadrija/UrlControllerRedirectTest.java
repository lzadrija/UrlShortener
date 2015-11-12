package com.lzadrija;

import com.lzadrija.account.Account;
import com.lzadrija.exception.ResourceNotFoundException;
import com.lzadrija.url.RedirectUrl;
import com.lzadrija.url.UrlController;
import com.lzadrija.url.registration.UrlRegistrationData;
import com.lzadrija.url.statistics.UrlHit;
import com.lzadrija.url.statistics.UrlHitsService;
import java.net.URI;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UrlControllerRedirectTest extends BaseControllerTest {

    @Mock
    private UrlHitsService hitsService;
    @InjectMocks
    private UrlController controller;

    private MockMvc mockMvc;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        mockMvc = createBasicMockMvcBuilder(controller).build();
    }

    @Test
    public void givenValidShortUrlShouldRedirect() throws Exception {

        String shortUrl = "D";
        String longUrl = "https://www.youtube.com/watch?v=kxopViU98Xo";
        int status = MOVED_PERMANENTLY.value();
        RedirectUrl redirectUrl = RedirectUrl.create(new Account("EpicSax{Guy", "2ui7k8Un"), shortUrl, new UrlRegistrationData(longUrl, status));

        when(hitsService.record(shortUrl)).thenReturn(UrlHit.create(redirectUrl));

        mockMvc.perform(get(URI.create("/" + shortUrl)))
                .andExpect(status().is(status))
                .andExpect(redirectedUrl(longUrl))
                .andDo(print());
    }

    @Test
    public void givenNotRegisteredShortUrlShouldThrowException() throws Exception {

        String shortUrl = "n0T";
        String exMsg = "URL: \"" + shortUrl + "\" is not a registered short URL";
        when(hitsService.record(shortUrl)).thenThrow(new ResourceNotFoundException(exMsg));

        mockMvc.perform(get(URI.create("/" + shortUrl)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(toJson(new ResultDescription(exMsg, false))))
                .andDo(print());
    }

}
