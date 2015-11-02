package com.lzadrija.account;

import com.lzadrija.account.registration.AccountId;
import com.lzadrija.account.registration.AccountRegistration;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@PropertySource("classpath:Messages.properties")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final Environment env;
    private final AccountFactory factory;

    @Autowired
    public AccountController(Environment env, AccountFactory factory) {
        this.env = env;
        this.factory = factory;
    }

    @ResponseBody
    @RequestMapping(value = "/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountRegistration> createAccount(@Valid @RequestBody AccountId accId) {

        Account account = factory.create(accId.getAccountId());
        logger.debug("Opened account with id: {}", account.getId());

        return ResponseEntity
                .status(CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createRegistrationResponse(account));
    }

    private AccountRegistration createRegistrationResponse(Account account) {
        return AccountRegistration.create(env.getRequiredProperty("account.opened"), true, account);
    }

}
