package com.lzadrija.url;

import com.lzadrija.url.registration.ShortUrlResource;
import com.lzadrija.url.registration.UrlRegistrationData;
import com.lzadrija.url.registration.UrlRegistrationService;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UrlController {

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    private final UrlRegistrationService urlRegService;

    @Autowired
    public UrlController(UrlRegistrationService urlRegService) {
        this.urlRegService = urlRegService;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortUrlResource> register(@Valid @RequestBody UrlRegistrationData input, HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        RedirectUrl redirectUrl = urlRegService.register(auth.getName(), input);
        String shortUrlReference = createShortUrlReference(redirectUrl, request);

        return ResponseEntity
                .status(CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ShortUrlResource(shortUrlReference));
    }

    private String createShortUrlReference(RedirectUrl redirectUrl, HttpServletRequest request) {
        String shortUrlReference;
        try {
            URL url = new URL(request.getRequestURL().toString());
            shortUrlReference = String.format("http://%s:%s/%s", url.getHost(), url.getPort(), redirectUrl.getShortened());
        } catch (MalformedURLException ex) {
            logger.error("Unable to obtain host and port From URL", ex);
            shortUrlReference = redirectUrl.getShortened();
        }
        logger.debug("Created short url: " + shortUrlReference);
        return shortUrlReference;
    }
}
