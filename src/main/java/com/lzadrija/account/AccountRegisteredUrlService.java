package com.lzadrija.account;

import com.lzadrija.exception.ResourceNotFoundException;
import com.lzadrija.url.statistics.UrlHitsService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountRegisteredUrlService {

    private static final Logger logger = LoggerFactory.getLogger(AccountRegisteredUrlService.class);

    private final AccountRepository accountRepo;
    private final UrlHitsService hitsService;

    @Autowired
    public AccountRegisteredUrlService(AccountRepository accountRepo, UrlHitsService hitsService) {
        this.accountRepo = accountRepo;
        this.hitsService = hitsService;
    }

    public Map<String, Long> getStatisticForAccount(String accountId) {

        verifyAccount(accountId);

        Account account = accountRepo.getOne(accountId);
        return hitsService.getHitsByLongUrl(account);
    }

    private void verifyAccount(String accountId) {
        if (!accountRepo.exists(accountId)) {
            logger.error("Unable to find account by ID: \"{}\"", accountId);
            throw new ResourceNotFoundException("Unable to find account by ID:\"" + accountId + "\"");
        }
    }
}
