package com.lzadrija.url.registration.account;

import java.io.Serializable;
import java.util.Objects;

public class AccountRegisteredUrlId implements Serializable {

    private String accountId;
    private String shortUrl;

    public AccountRegisteredUrlId() {
    }

    public AccountRegisteredUrlId(String accountId, String shortUrl) {
        this.accountId = accountId;
        this.shortUrl = shortUrl;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.accountId);
        hash = 67 * hash + Objects.hashCode(this.shortUrl);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && (getClass() == obj.getClass())
                && Objects.equals(this.accountId, ((AccountRegisteredUrlId) obj).accountId)
                && Objects.equals(this.shortUrl, ((AccountRegisteredUrlId) obj).shortUrl);
    }

}
