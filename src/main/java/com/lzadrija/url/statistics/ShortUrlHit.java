package com.lzadrija.url.statistics;

import com.lzadrija.url.RedirectUrl;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "short_url_hit")
public class ShortUrlHit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String shortUrl;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date hitTime;

    public ShortUrlHit() {
    }

    public ShortUrlHit(String shortUrl, Date hitTime) {
        this.shortUrl = shortUrl;
        this.hitTime = hitTime;
    }

    static ShortUrlHit create(RedirectUrl redirectUrl) {
        return new ShortUrlHit(redirectUrl.getShortened(), new Date());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Date getHitTime() {
        return hitTime;
    }

    public void setHitTime(Date hitTime) {
        this.hitTime = hitTime;
    }

    @Override
    public String toString() {
        return String.format("ShortUrlHits: [Short URL = %s, hit time = %s]", shortUrl, hitTime);
    }

}
