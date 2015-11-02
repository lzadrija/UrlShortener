package com.lzadrija.account.registration;

public class AccountVerificationException extends RuntimeException {

    public AccountVerificationException() {
        super();
    }

    public AccountVerificationException(String message) {
        super(message);
    }

    public AccountVerificationException(Throwable cause) {
        super(cause);
    }

    public AccountVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

}
