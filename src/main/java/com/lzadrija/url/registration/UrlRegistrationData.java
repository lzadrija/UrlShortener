package com.lzadrija.url.registration;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class UrlRegistrationData {

    @NotNull
    @URL(regexp = "^(http|https)\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(:[a-zA-Z0-9]*)?/?([a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&amp;%\\$#\\=~])*$")
    private String url;
    @Range(min = 301, max = 302, message = "Redirect type must be set to one of these two HTTP statuses: MOVED_PERMANENTLY(301) or FOUND(302)")
    private Integer redirectType;

    public UrlRegistrationData() {
    }

    public UrlRegistrationData(String url) {
        this.url = url;
    }

    public UrlRegistrationData(String url, Integer redirectType) {
        this.url = url;
        this.redirectType = redirectType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(Integer redirectType) {
        this.redirectType = redirectType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.url);
        hash = 47 * hash + Objects.hashCode(this.redirectType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && (getClass() == obj.getClass())
               && Objects.equals(this.url, ((UrlRegistrationData) obj).url)
               && Objects.equals(this.redirectType, ((UrlRegistrationData) obj).redirectType);
    }

    @Override
    public String toString() {
        return "UrlRegisterRequest{"
               + "url=" + url
               + ", redirectType=" + redirectType
               + '}';
    }

}
