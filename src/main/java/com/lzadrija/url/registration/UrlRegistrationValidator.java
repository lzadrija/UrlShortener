package com.lzadrija.url.registration;

import com.lzadrija.url.UrlRepository;
import com.lzadrija.url.shortening.ServerAddressFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlRegistrationValidator {

    private final HttpServletRequest request;
    private final ServerAddressFactory serverAddressFactory;
    private final UrlRepository urlRepo;

    @Autowired
    public UrlRegistrationValidator(HttpServletRequest request, ServerAddressFactory serverAddressFactory, UrlRepository urlRepo) {
        this.request = request;
        this.serverAddressFactory = serverAddressFactory;
        this.urlRepo = urlRepo;
    }

    public boolean isValid(String longUrl) {
        return longUrl != null && !longUrl.isEmpty()
               ? isNotARegisteredShortUrl(longUrl)
               : true;
    }

    private boolean isNotARegisteredShortUrl(String longUrl) {
        String serverAddress = serverAddressFactory.create(request);
        return !(longUrl.startsWith(serverAddress) && urlRepo.findOne(longUrl.replace(serverAddress, "")) != null);
    }
}
