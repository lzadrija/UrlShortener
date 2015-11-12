package com.lzadrija.account.registration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lzadrija.ResultDescription;
import com.lzadrija.account.Account;
import com.lzadrija.help.api.resources.AccountRegistrationResource;

public class AccountRegistration extends ResultDescription implements AccountRegistrationResource {

    private static final String SUCCESS_REGISTRATION_MSG = "Your account is opened";

    @JsonInclude(Include.NON_NULL)
    private String password;

    public AccountRegistration() {
    }

    public AccountRegistration(String description, boolean success) {
        super(description, success);
    }

    private AccountRegistration(String description, boolean success, String password) {
        this(description, success);
        this.password = password;
    }

    public static AccountRegistration create(Account account) {
        return new AccountRegistration(SUCCESS_REGISTRATION_MSG, true, account.getPassword());
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountRegistrationResponse{"
               + "description=" + description
               + ", success=" + success
               + ", password=" + password
               + '}';
    }

}
