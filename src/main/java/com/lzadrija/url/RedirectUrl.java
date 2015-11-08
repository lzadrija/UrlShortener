package com.lzadrija.url;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.FOUND;

@Entity
@Table(name = "redirect_url")
public class RedirectUrl implements Serializable {

    @Id
    private String shortened;
    @Column(nullable = false)
    private String longUrl;
    private Integer redirectType;

    public RedirectUrl() {
    }

    public RedirectUrl(String shortened, String longUrl, Integer redirectType) {
        this.shortened = shortened;
        this.longUrl = longUrl;
        this.redirectType = (redirectType != null) ? redirectType : FOUND.value();
    }

    public String getShortened() {
        return shortened;
    }

    public void setShortened(String shortened) {
        this.shortened = shortened;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public HttpStatus getRedirectHttpStatus() {
        return HttpStatus.valueOf(redirectType);
    }

    public Integer getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(Integer redirectType) {
        this.redirectType = redirectType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(shortened);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && (getClass() == obj.getClass())
               && Objects.equals(this.shortened, ((RedirectUrl) obj).shortened);
    }

    @Override
    public String toString() {
        return String.format("URL: [Shortened = %s, Full = %s, RedirectType = %d]", shortened, longUrl, redirectType);
    }

}
