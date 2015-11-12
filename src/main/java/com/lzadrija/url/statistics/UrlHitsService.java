package com.lzadrija.url.statistics;

import com.lzadrija.account.Account;
import com.lzadrija.exception.ResourceNotFoundException;
import com.lzadrija.url.RedirectUrl;
import com.lzadrija.url.UrlRepository;
import com.lzadrija.url.registration.ShortUrlRegistrationValidator;
import java.util.Map;
import java.util.stream.Collectors;
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

    public UrlHit record(String shortUrl) {

        verifyUrl(shortUrl);

        RedirectUrl redirectUrl = urlRepo.findOne(shortUrl);
        return hitsRepo.save(UrlHit.create(redirectUrl));
    }

    private void verifyUrl(String shortUrl) {

        if (!validator.isRegisteredShortUrl(shortUrl)) {
            logger.error("Unable to recognize URL: \"{}\", as a registered short URL", shortUrl);
            throw new ResourceNotFoundException("URL: \"" + shortUrl + "\" is not a registered short URL");
        }
    }

    public Map<String, Long> getHitsByLongUrl(Account account) {

        return urlRepo.findAllByAccount(account).stream()
                .map((RedirectUrl redirectUrl) -> getHitsCount(redirectUrl))
                .collect(Collectors.toMap(UrlHitCount::getLongUrl, UrlHitCount::getCount));
    }

    private UrlHitCount getHitsCount(RedirectUrl redirectUrl) {
        Long count = hitsRepo.countByRedirectUrl(redirectUrl);
        return new UrlHitCount(redirectUrl, count);
    }

}
