package com.lzadrija.account.registration;

import com.lzadrija.account.AccountController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(assignableTypes = {AccountController.class}, basePackages = {"com.lzadrija.account"})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccountExceptionsHandler {

    @ResponseBody
    @ExceptionHandler(AccountRegistrationException.class)
    public ResponseEntity<AccountRegistration> accountRegistrationExceptionHandler(AccountRegistrationException ex) {

        AccountRegistration body = new AccountRegistration(ex.getMessage(), false);
        return ResponseEntity.status(CONFLICT).contentType(APPLICATION_JSON).body(body);
    }

}
