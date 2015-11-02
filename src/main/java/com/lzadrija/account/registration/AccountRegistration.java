package com.lzadrija.account.registration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lzadrija.account.Account;

public class AccountRegistration {

    private String description;
    private boolean success;
    @JsonInclude(Include.NON_NULL)
    private String password;

    public AccountRegistration() {
    }

    public AccountRegistration(String description, boolean success) {
        this.description = description;
        this.success = success;
    }

    private AccountRegistration(String description, boolean success, String password) {
        this.description = description;
        this.success = success;
        this.password = password;
    }

    public static AccountRegistration create(String description, boolean success, Account account) {
        return new AccountRegistration(description, success, account.getPassword());
    }

    public String getDescription() {
        return description;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
