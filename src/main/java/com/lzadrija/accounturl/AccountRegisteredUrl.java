package com.lzadrija.accounturl;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "account_url")
@IdClass(AccountRegisteredUrlId.class)
public class AccountRegisteredUrl {

    @Id
    private String accountId;
    @Id
    private String shortUrl;

    public AccountRegisteredUrl() {
    }

    public AccountRegisteredUrl(String accountId, String shortUrl) {
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
    public String toString() {
        return String.format("AccountRegisteredUrl: [Account ID = %s, short URL = %s]", accountId, shortUrl);
    }

}
