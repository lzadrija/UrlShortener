package com.lzadrija.url.statistics;

import com.lzadrija.url.RedirectUrl;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "url_hit")
public class UrlHit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private RedirectUrl redirectUrl;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date hitTime;

    public UrlHit() {
    }

    public UrlHit(RedirectUrl redirectUrl, Date hitTime) {
        this.redirectUrl = redirectUrl;
        this.hitTime = hitTime;
    }

    public static UrlHit create(RedirectUrl redirectUrl) {
        return new UrlHit(redirectUrl, new Date());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RedirectUrl getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(RedirectUrl redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Date getHitTime() {
        return hitTime;
    }

    public void setHitTime(Date hitTime) {
        this.hitTime = hitTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && (getClass() == obj.getClass())
               && Objects.equals(this.id, ((UrlHit) obj).id);
    }

    @Override
    public String toString() {
        return String.format("ShortUrlHits: [Redirect URL = %s, hit time = %s]", redirectUrl, hitTime);
    }

}
