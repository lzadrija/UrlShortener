package com.lzadrija.url.statistics;

import com.lzadrija.url.RedirectUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlHitsRepository extends JpaRepository<UrlHit, Long> {

    Long countByRedirectUrl(RedirectUrl redirectUrl);

}
