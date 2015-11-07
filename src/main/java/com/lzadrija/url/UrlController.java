package com.lzadrija.url;

import com.lzadrija.url.registration.UrlRegistrationData;
import com.lzadrija.url.registration.UrlRegistrationService;
import com.lzadrija.url.shortening.ServerAddressFactory;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UrlController {

    private final UrlRegistrationService urlRegService;
    private final ServerAddressFactory serverAddressFactory;

    @Autowired
    public UrlController(UrlRegistrationService urlRegService, ServerAddressFactory serverAddressFactory) {
        this.urlRegService = urlRegService;
        this.serverAddressFactory = serverAddressFactory;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortUrlResource> register(@Valid @RequestBody UrlRegistrationData data, Principal principal, HttpServletRequest request) {

        RedirectUrl redirectUrl = urlRegService.register(principal.getName(), data);
        String address = serverAddressFactory.create(request);

        return ResponseEntity
                .status(CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ShortUrlResource.create(redirectUrl, address));
    }

}
