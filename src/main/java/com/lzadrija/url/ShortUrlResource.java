package com.lzadrija.url;

import java.util.Objects;

public class ShortUrlResource {

    private String shortUrl;

    public ShortUrlResource() {
    }

    private ShortUrlResource(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public static ShortUrlResource create(RedirectUrl redirectUrl, String address) {
        String shortUrlAddress = address + redirectUrl.getShortened();
        return new ShortUrlResource(shortUrlAddress);
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
               && Objects.equals(this.shortUrl, ((ShortUrlResource) obj).shortUrl);
    }

    @Override
    public String toString() {
        return "ShortUrlResource{"
               + "shortUrl=" + shortUrl
               + '}';
    }

}
