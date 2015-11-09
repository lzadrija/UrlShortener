package com.lzadrija.url.registration;

import com.lzadrija.account.Account;
import com.lzadrija.account.AccountRepository;
import com.lzadrija.exception.RegistrationException;
import com.lzadrija.url.RedirectUrl;
import com.lzadrija.url.UrlRepository;
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
    private final AccountRepository accountRepo;
    private final UrlRepository urlRepo;

    @Autowired
    public UrlRegistrationService(UrlShortener shortener, UrlRepository urlRepo, AccountRepository accountRepo, ShortUrlRegistrationValidator validator) {
        this.shortener = shortener;
        this.urlRepo = urlRepo;
        this.accountRepo = accountRepo;
        this.validator = validator;
    }

    public RedirectUrl register(String accountId, UrlRegistrationData data) {

        verifyUrl(data.getUrl());

        Account account = accountRepo.getOne(accountId);
        RedirectUrl redirectUrl = createRedirectUrl(account, data);

        logger.debug("Created Redirect URL: {}", redirectUrl);
        return redirectUrl;
    }

    private void verifyUrl(String url) {

        if (validator.isRegisteredShortUrlWithDomain(url)) {
            logger.error("Unable to register URL: \"{}\", it is an already registered short URL", url);
            throw new RegistrationException("URL: \"" + url + "\" is an already registered short URL");
        }
    }

    private RedirectUrl createRedirectUrl(Account account, UrlRegistrationData data) {

        String shortUrl = shortener.createShortUrl();
        RedirectUrl redirectUrl = RedirectUrl.create(account, shortUrl, data);
        return urlRepo.save(redirectUrl);
    }

}
