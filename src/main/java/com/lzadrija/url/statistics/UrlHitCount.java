package com.lzadrija.url.statistics;

import com.lzadrija.url.RedirectUrl;

public class UrlHitCount {

    private final RedirectUrl redirectUrl;
    private final Long count;

    public UrlHitCount(RedirectUrl redirectUrl, Long count) {
        this.redirectUrl = redirectUrl;
        this.count = count;
    }

    public RedirectUrl getRedirectUrl() {
        return redirectUrl;
    }

    public String getLongUrl() {
        return redirectUrl.getLongUrl();
    }

    public Long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "UrlHitCount{"
               + "redirectUrl=" + redirectUrl
               + ", count=" + count
               + '}';
    }

}
