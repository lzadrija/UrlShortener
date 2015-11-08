package com.lzadrija.url.statistics;

import com.lzadrija.exception.ResourceNotFoundException;
import com.lzadrija.url.RedirectUrl;
import com.lzadrija.url.UrlRepository;
import com.lzadrija.url.registration.ShortUrlRegistrationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShortUrlHitsService {

    private static final Logger logger = LoggerFactory.getLogger(ShortUrlHitsService.class);

    private final ShortUrlRegistrationValidator validator;
    private final UrlRepository urlRepo;
    private final ShortUrlHitsRepository hitsRepo;

    @Autowired
    public ShortUrlHitsService(ShortUrlRegistrationValidator validator, UrlRepository urlRepo, ShortUrlHitsRepository hitsRepo) {
        this.validator = validator;
        this.urlRepo = urlRepo;
        this.hitsRepo = hitsRepo;
    }

    public RedirectUrl record(String shortUrl) {

        verifyUrl(shortUrl);

        RedirectUrl redirectUrl = urlRepo.findOne(shortUrl);
        hitsRepo.save(ShortUrlHit.create(redirectUrl));

        return redirectUrl;
    }

    private void verifyUrl(String shortUrl) {

        if (!validator.isRegisteredShortUrl(shortUrl)) {
            logger.error("Unable to recognize URL: \"" + shortUrl + "\", as a registered short URL");
            throw new ResourceNotFoundException("URL: \"" + shortUrl + "\" is not a registered short URL");
        }
    }

}
