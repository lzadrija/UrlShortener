package com.lzadrija.url;

import com.lzadrija.url.registration.ServiceAddressFactory;
import com.lzadrija.url.registration.UrlRegistrationData;
import com.lzadrija.url.registration.UrlRegistrationService;
import com.lzadrija.url.statistics.UrlHit;
import com.lzadrija.url.statistics.UrlHitsService;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class UrlController {

    private final UrlRegistrationService urlRegService;
    private final ServiceAddressFactory addressFactory;
    private final UrlHitsService hitsService;

    @Autowired
    public UrlController(UrlRegistrationService urlRegService, ServiceAddressFactory addressFactory, UrlHitsService hitsService) {
        this.urlRegService = urlRegService;
        this.addressFactory = addressFactory;
        this.hitsService = hitsService;
    }

    @RequestMapping(value = "/register", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortUrl> register(@Valid @RequestBody UrlRegistrationData data, Principal principal, HttpServletRequest request) {

        RedirectUrl redirectUrl = urlRegService.register(principal.getName(), data);
        String address = addressFactory.create(request);

        return ResponseEntity
                .status(CREATED)
                .body(ShortUrl.create(redirectUrl, address));
    }

    @RequestMapping(value = "/{shortUrl}", method = GET)
    public ModelAndView redirectUsingShortUrl(@PathVariable String shortUrl) {

        UrlHit hit = hitsService.record(shortUrl);
        RedirectUrl redirectUrl = hit.getRedirectUrl();

        RedirectView rv = new RedirectView(redirectUrl.getLongUrl(), false);
        rv.setStatusCode(redirectUrl.getRedirectHttpStatus());
        return new ModelAndView(rv);
    }

}
