package com.lzadrija.url;

import java.util.Objects;

public class ShortUrl {

    private String shortUrl;

    public ShortUrl() {
    }

    private ShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public static ShortUrl create(RedirectUrl redirectUrl, String address) {
        String shortUrlAddress = address + redirectUrl.getShortened();
        return new ShortUrl(shortUrlAddress);
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.shortUrl);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && (getClass() == obj.getClass())
               && Objects.equals(this.shortUrl, ((ShortUrl) obj).shortUrl);
    }

    @Override
    public String toString() {
        return "ShortUrl{"
               + "shortUrl=" + shortUrl
               + '}';
    }

}
