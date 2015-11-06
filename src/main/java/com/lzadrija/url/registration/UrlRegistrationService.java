package com.lzadrija.url.registration;

import com.lzadrija.account.Account;
import com.lzadrija.url.RedirectUrl;
import com.lzadrija.url.UrlRepository;
import com.lzadrija.url.registration.account.AccountRegisteredUrl;
import com.lzadrija.url.registration.account.AccountRegisteredUrlId;
import com.lzadrija.url.registration.account.AccountUrlRepository;
import com.lzadrija.url.shortening.UrlShorteningService;
import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@PropertySource("classpath:Messages.properties")
public class UrlRegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(UrlRegistrationService.class);

    private final Environment env;
    private final UrlShorteningService service;

    private final UrlRepository urlRepo;
    private final AccountUrlRepository accUrlRepo;

    @Autowired
    public UrlRegistrationService(Environment env, UrlRepository urlRepo, AccountUrlRepository accUrlRepo, UrlShorteningService service) {
        this.env = env;
        this.urlRepo = urlRepo;
        this.accUrlRepo = accUrlRepo;
        this.service = service;
    }

    public RedirectUrl register(UrlRegistrationData registrationData) {

        Account account = new Account("Gimli", "1LoG2KFu");

        validateInput(account, registrationData.getUrl());

        RedirectUrl redirectUrl = createRedirectUrl(registrationData);
        registerUrlForAccount(account, redirectUrl);

        return redirectUrl;
    }

    private void validateInput(Account account, String longUrl) {

        AccountRegisteredUrlId accUrlId = new AccountRegisteredUrlId(account.getId(), longUrl);

        if (accUrlRepo.findOne(accUrlId) != null) {
            logger.error("Unable to register URL:" + longUrl + ", because it is a short URL already registered by account ID:" + account.getId());
            throw new UrlRegistrationException(MessageFormat.format(env.getRequiredProperty("long.url.equals.registered.short.url"), longUrl));
        }
    }

    private RedirectUrl createRedirectUrl(UrlRegistrationData input) {

        String shortUrl = service.createShortUrl();
        RedirectUrl redirectUrl = new RedirectUrl(shortUrl, input.getUrl(), input.getRedirectType());
        logger.debug("Created redirect URL: " + redirectUrl);
        return urlRepo.save(redirectUrl);
    }

    private void registerUrlForAccount(Account account, RedirectUrl redirectUrl) {
        AccountRegisteredUrl accountUrl = new AccountRegisteredUrl(account.getId(), redirectUrl.getShortened());
        logger.debug("Created Account registered URL: " + accountUrl);
        accUrlRepo.save(accountUrl);
    }
}
