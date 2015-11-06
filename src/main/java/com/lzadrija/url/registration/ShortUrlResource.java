package com.lzadrija.url.registration;

public class ShortUrlResource {

    private String shortUrl;

    public ShortUrlResource() {
    }

    public ShortUrlResource(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    @Override
    public String toString() {
        return "ShortUrlResource{"
                + "shortUrl=" + shortUrl + '}';
    }

}
