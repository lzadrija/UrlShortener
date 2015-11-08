package com.lzadrija.account;

import com.lzadrija.account.registration.AccountId;
import com.lzadrija.account.registration.AccountRegistration;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AccountController {

    private final AccountFactory factory;

    @Autowired
    public AccountController(AccountFactory factory) {
        this.factory = factory;
    }

    @ResponseBody
    @RequestMapping(value = "/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountRegistration> createAccount(@Valid @RequestBody AccountId accId) {

        Account account = factory.create(accId.getAccountId());

        return ResponseEntity
                .status(CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createRegistrationResponse(account));
    }

    private AccountRegistration createRegistrationResponse(Account account) {
        return AccountRegistration.create("Your account is opened", true, account);
    }

}
