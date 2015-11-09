package com.lzadrija.accounturl;

import com.lzadrija.account.AccountRepository;
import com.lzadrija.exception.ResourceNotFoundException;
import com.lzadrija.url.RedirectUrl;
import com.lzadrija.url.statistics.UrlHitCount;
import com.lzadrija.url.statistics.UrlHitsService;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountRegisteredUrlService {

    private static final Logger logger = LoggerFactory.getLogger(AccountRegisteredUrlService.class);

    private final AccountUrlRepository accountUrlRepo;
    private final UrlHitsService hitsService;
    private final AccountRepository accountRepo;

    @Autowired
    public AccountRegisteredUrlService(AccountRepository accountRepo, AccountUrlRepository accountUrlRepo, UrlHitsService hitsService) {
        this.accountRepo = accountRepo;
        this.accountUrlRepo = accountUrlRepo;
        this.hitsService = hitsService;
    }

    public Map<String, Long> getStatisticForAccount(String accountId) {

        verifyAccount(accountId);
        return getHitsByLongUrl(accountId);
    }

    private Map<String, Long> getHitsByLongUrl(String accountId) {

        return accountUrlRepo.findAllByAccountId(accountId).stream()
                .map((AccountRegisteredUrl accUrl) -> hitsService.getHitsCount(accUrl.getShortUrl()))
                .collect(Collectors.toMap(UrlHitCount::getLongUrl, UrlHitCount::getCount));
    }

    public void registerUrlForAccount(String accountId, RedirectUrl redirectUrl) {

        verifyAccount(accountId);

        AccountRegisteredUrl accountUrl = new AccountRegisteredUrl(accountId, redirectUrl.getShortened());
        accountUrlRepo.save(accountUrl);
    }

    private void verifyAccount(String accountId) {
        if (!accountRepo.exists(accountId)) {
            logger.error("Unable to find account by ID: \"{}\"", accountId);
            throw new ResourceNotFoundException("Unable to find account by ID:\"" + accountId + "\"");
        }
    }
}
