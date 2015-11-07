package com.lzadrija.account;

import com.lzadrija.account.registration.AccountRegistrationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountFactory {

    private static final Logger logger = LoggerFactory.getLogger(AccountFactory.class);
    public static final int PASSWORD_LENGTH = 8;

    private final AccountRepository repo;

    @Autowired
    public AccountFactory(AccountRepository repo) {
        this.repo = repo;
    }

    public Account create(String id) {

        verifyIdExistence(id);

        String password = generatePassword();
        return repo.save(new Account(id, password));
    }

    private void verifyIdExistence(String id) {

        if (repo.findOne(id) != null) {
            logger.error("Account ID: " + id + " already exists");
            throw new AccountRegistrationException("Account ID: " + id + " already exists");
        }
    }

    private String generatePassword() {
        return RandomStringUtils.random(PASSWORD_LENGTH, true, true);
    }

}
