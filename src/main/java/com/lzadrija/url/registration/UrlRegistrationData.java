package com.lzadrija.url.registration;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class UrlRegistrationData {

    @NotNull
    @URL
    private String url;
    @Range(min = 301, max = 302, message = "Redirect type must be set to one of these two HTTP statuses: MOVED_PERMANENTLY(301) or FOUND(302)")
    private Integer redirectType;

    public UrlRegistrationData() {
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
    public String toString() {
        return "UrlRegisterRequest{" + "url=" + url + ", redirectType=" + redirectType + '}';
    }

}
