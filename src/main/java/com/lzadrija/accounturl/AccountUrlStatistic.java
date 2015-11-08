package com.lzadrija.accounturl;

import java.util.Map;

public class AccountUrlStatistic {

    private Map<String, Long> urlHitsByLongUrl;

    public AccountUrlStatistic() {
    }

    public AccountUrlStatistic(Map<String, Long> urlHitsByLongUrl) {
        this.urlHitsByLongUrl = urlHitsByLongUrl;
    }

    public Map<String, Long> getUrlHitsByLongUrl() {
        return urlHitsByLongUrl;
    }

    public void setUrlHitsByLongUrl(Map<String, Long> urlHitsByLongUrl) {
        this.urlHitsByLongUrl = urlHitsByLongUrl;
    }

    @Override
    public String toString() {
        return "AccountUrlStatistic{"
               + "urlHitsByLongUrl=" + urlHitsByLongUrl + '}';
    }

}
