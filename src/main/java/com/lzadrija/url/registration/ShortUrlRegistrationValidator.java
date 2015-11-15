package com.lzadrija.url.registration;

import com.lzadrija.url.UrlRepository;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlRegistrationValidator {

    private final HttpServletRequest request;
    private final ServiceAddressFactory addressFactory;
    private final UrlRepository urlRepo;

    @Autowired
    public ShortUrlRegistrationValidator(HttpServletRequest request, ServiceAddressFactory addressFactory, UrlRepository urlRepo) {
        this.request = request;
        this.addressFactory = addressFactory;
        this.urlRepo = urlRepo;
    }

    public boolean isRegisteredShortUrlWithDomain(String url) {
        String serverAddress = addressFactory.create(request);
        return isNotBlank(url)
               && url.startsWith(serverAddress) && urlRepo.exists(url.replace(serverAddress, ""));
    }

    public boolean isRegisteredShortUrl(String url) {
        return isNotBlank(url) && urlRepo.exists(url);
    }

    private boolean isNotBlank(String longUrl) {
        return longUrl != null && !longUrl.isEmpty();
    }
}
