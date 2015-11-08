package com.lzadrija.url;

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
    public String toString() {
        return "ShortUrlResource{"
               + "shortUrl=" + shortUrl + '}';
    }

}
