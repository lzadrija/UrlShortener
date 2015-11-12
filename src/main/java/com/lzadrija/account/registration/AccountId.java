package com.lzadrija.account.registration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AccountId {

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9_!\\$+-]{3,15}$",
             message = "Account ID can only contain (min 3 and max 15): alphanumeric and special characters: _-+!$")
    private String accountId;

    public AccountId() {
    }

    public AccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "AccountId{" + "accountId=" + accountId + '}';
    }

}
