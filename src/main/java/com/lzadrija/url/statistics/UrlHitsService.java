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
public class UrlHitsService {

    private static final Logger logger = LoggerFactory.getLogger(UrlHitsService.class);

    private final ShortUrlRegistrationValidator validator;
    private final UrlRepository urlRepo;
    private final UrlHitsRepository hitsRepo;

    @Autowired
    public UrlHitsService(ShortUrlRegistrationValidator validator, UrlRepository urlRepo, UrlHitsRepository hitsRepo) {
        this.validator = validator;
        this.urlRepo = urlRepo;
        this.hitsRepo = hitsRepo;
    }

    public RedirectUrl record(String shortUrl) {

        verifyUrl(shortUrl);

        RedirectUrl redirectUrl = urlRepo.findOne(shortUrl);
        hitsRepo.save(UrlHit.create(redirectUrl));

        return redirectUrl;
    }

    public UrlHitCount getHitsCount(String shortUrl) {

        verifyUrl(shortUrl);

        RedirectUrl redirectUrl = urlRepo.findOne(shortUrl);
        Long count = hitsRepo.countByRedirectUrl(redirectUrl);
        return new UrlHitCount(redirectUrl, count);
    }

    private void verifyUrl(String shortUrl) {

        if (!validator.isRegisteredShortUrl(shortUrl)) {
            logger.error("Unable to recognize URL: \"{}\", as a registered short URL", shortUrl);
            throw new ResourceNotFoundException("URL: \"" + shortUrl + "\" is not a registered short URL");
        }
    }

}
