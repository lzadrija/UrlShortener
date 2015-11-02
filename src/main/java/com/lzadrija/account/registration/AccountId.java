package com.lzadrija.account.registration;

import org.hibernate.validator.constraints.NotBlank;

public class AccountId {

    @NotBlank
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

}
