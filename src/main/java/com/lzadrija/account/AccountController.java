package com.lzadrija.account;

import com.lzadrija.account.registration.AccountId;
import com.lzadrija.account.registration.AccountRegistration;
import com.lzadrija.accounturl.AccountRegisteredUrlService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AccountController {

    private final AccountFactory factory;
    private final AccountRegisteredUrlService registeredUrls;

    @Autowired
    public AccountController(AccountFactory factory, AccountRegisteredUrlService registeredUrls) {
        this.factory = factory;
        this.registeredUrls = registeredUrls;
    }

    @ResponseBody
    @RequestMapping(value = "/account", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountRegistration> createAccount(@Valid @RequestBody AccountId accId) {

        Account account = factory.create(accId.getAccountId());
        AccountRegistration accountRegistration = AccountRegistration.create(account);

        return ResponseEntity
                .status(CREATED)
                .body(accountRegistration);
    }

    @ResponseBody
    @RequestMapping(value = "/statistic/{accountId}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> register(@PathVariable("accountId") String accountId) {

        Map<String, Long> statistic = registeredUrls.getStatisticForAccount(accountId);
        return ResponseEntity.ok(statistic);
    }

}
