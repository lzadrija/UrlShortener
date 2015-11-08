package com.lzadrija.url.registration;

import com.lzadrija.exception.RegistrationException;
import com.lzadrija.url.RedirectUrl;
import com.lzadrija.url.UrlRepository;
import com.lzadrija.accounturl.AccountRegisteredUrl;
import com.lzadrija.accounturl.AccountUrlRepository;
import com.lzadrija.url.shortening.UrlShortener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UrlRegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(UrlRegistrationService.class);

    private final ShortUrlRegistrationValidator validator;
    private final UrlShortener shortener;
    private final UrlRepository urlRepo;
    private final AccountUrlRepository accUrlRepo;

    @Autowired
    public UrlRegistrationService(UrlShortener shortener, UrlRepository urlRepo, AccountUrlRepository accUrlRepo, ShortUrlRegistrationValidator validator) {
        this.shortener = shortener;
        this.urlRepo = urlRepo;
        this.accUrlRepo = accUrlRepo;
        this.validator = validator;
    }

    public RedirectUrl register(String accountId, UrlRegistrationData data) {

        verifyUrl(data.getUrl());

        RedirectUrl redirectUrl = createRedirectUrl(data);
        registerUrlForAccount(accountId, redirectUrl);

        logger.debug("Created Redirect URL: {}", redirectUrl);
        return redirectUrl;
    }

    private void verifyUrl(String url) {

        if (validator.isRegisteredShortUrlWithDomain(url)) {
            logger.error("Unable to register URL: \"{}\", it is an already registered short URL", url);
            throw new RegistrationException("URL: \"" + url + "\" is an already registered short URL");
        }
    }

    private RedirectUrl createRedirectUrl(UrlRegistrationData data) {

        String shortUrl = shortener.createShortUrl();
        RedirectUrl redirectUrl = new RedirectUrl(shortUrl, data.getUrl(), data.getRedirectType());
        return urlRepo.save(redirectUrl);
    }

    private void registerUrlForAccount(String accountId, RedirectUrl redirectUrl) {
        AccountRegisteredUrl accountUrl = new AccountRegisteredUrl(accountId, redirectUrl.getShortened());
        accUrlRepo.save(accountUrl);
    }
}
