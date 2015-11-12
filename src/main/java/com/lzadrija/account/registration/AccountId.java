package com.lzadrija.account.registration;

import com.lzadrija.help.api.resources.AccountIdResource;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AccountId implements AccountIdResource {

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9_!\\$+-]{3,15}$",
             message = "Account ID can only contain (min 3 and max 15): alphanumeric and special characters: _-+!$")
    private String accountId;

    public AccountId() {
    }

    public AccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.accountId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && (getClass() == obj.getClass()) && Objects.equals(this.accountId, ((AccountId) obj).accountId);
    }

    @Override
    public String toString() {
        return "AccountId{" + "accountId=" + accountId + '}';
    }

}
