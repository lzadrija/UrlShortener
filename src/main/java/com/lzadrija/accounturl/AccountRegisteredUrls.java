/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lzadrija.accounturl;

import com.lzadrija.account.AccountRepository;
import com.lzadrija.exception.ResourceNotFoundException;
import com.lzadrija.url.RedirectUrl;
import com.lzadrija.url.UrlRepository;
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
public class AccountRegisteredUrls {

    private static final Logger logger = LoggerFactory.getLogger(AccountRegisteredUrls.class);

    private final AccountUrlRepository accountUrlRepo;
    private final UrlHitsService hitsService;
    private final UrlRepository urlRepo;
    private final AccountRepository accountRepo;

    @Autowired
    public AccountRegisteredUrls(AccountUrlRepository accountUrlRepo, UrlHitsService hitsService, UrlRepository urlRepo, AccountRepository accountRepo) {
        this.accountUrlRepo = accountUrlRepo;
        this.hitsService = hitsService;
        this.urlRepo = urlRepo;
        this.accountRepo = accountRepo;
    }

    public AccountUrlStatistic getStatisticForAccount(String accountId) {

        verifyAccount(accountId);

        return new AccountUrlStatistic(getUrlHitsByLongUrl(accountId));
    }

    private void verifyAccount(String accountId) {
        if (!accountRepo.exists(accountId)) {
            logger.error("Unable to find account by ID: \"{}\"", accountId);
            throw new ResourceNotFoundException("Unable to find account by ID:\"" + accountId + "\"");
        }
    }

    private Map<String, Long> getUrlHitsByLongUrl(String accountId) {

        return accountUrlRepo.findAllByAccountId(accountId)
                .stream()
                .map((AccountRegisteredUrl accUrl) -> urlRepo.findOne(accUrl.getShortUrl()))
                .map((RedirectUrl redirectUrl) -> hitsService.getHitsCount(redirectUrl.getShortened()))
                .collect(Collectors.toMap(UrlHitCount::getLongUrl, UrlHitCount::getCount));
    }
}
