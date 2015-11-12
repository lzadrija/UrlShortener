package com.lzadrija.url.statistics;

import com.lzadrija.url.RedirectUrl;
import java.util.Objects;

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
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.redirectUrl);
        hash = 17 * hash + Objects.hashCode(this.count);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && (getClass() == obj.getClass())
               && Objects.equals(this.redirectUrl, ((UrlHitCount) obj).redirectUrl)
               && Objects.equals(this.count, ((UrlHitCount) obj).count);
    }

    @Override
    public String toString() {
        return "UrlHitCount{"
               + "redirectUrl=" + redirectUrl
               + ", count=" + count
               + '}';
    }

}
