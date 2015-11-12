package com.lzadrija.url.registration;

import com.lzadrija.url.UrlRepository;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlRegistrationValidator {

    private final HttpServletRequest request;
    private final ServerAddressFactory serverAddressFactory;
    private final UrlRepository urlRepo;

    @Autowired
    public ShortUrlRegistrationValidator(HttpServletRequest request, ServerAddressFactory serverAddressFactory, UrlRepository urlRepo) {
        this.request = request;
        this.serverAddressFactory = serverAddressFactory;
        this.urlRepo = urlRepo;
    }

    public boolean isRegisteredShortUrlWithDomain(String url) {
        String serverAddress = serverAddressFactory.create(request);
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
