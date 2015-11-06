package com.lzadrija.url.registration;

import static org.springframework.http.HttpStatus.CONFLICT;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = CONFLICT)
public class UrlRegistrationException extends RuntimeException {

    public UrlRegistrationException() {
        super();
    }

    public UrlRegistrationException(String message) {
        super(message);
    }

    public UrlRegistrationException(Throwable cause) {
        super(cause);
    }

    public UrlRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

}
