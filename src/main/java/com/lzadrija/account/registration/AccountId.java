package com.lzadrija.account.registration;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

public class AccountId {

    @NotNull
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

    @Override
    public String toString() {
        return "AccountId{" + "accountId=" + accountId + '}';
    }

}
