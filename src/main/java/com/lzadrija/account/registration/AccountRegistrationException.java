package com.lzadrija.account.registration;

import static org.springframework.http.HttpStatus.CONFLICT;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = CONFLICT)
public class AccountRegistrationException extends RuntimeException {

    public AccountRegistrationException() {
        super();
    }

    public AccountRegistrationException(String message) {
        super(message);
    }

    public AccountRegistrationException(Throwable cause) {
        super(cause);
    }

    public AccountRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

}
