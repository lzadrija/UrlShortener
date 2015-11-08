package com.lzadrija.url.statistics;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlHitsRepository extends JpaRepository<ShortUrlHit, Long> {

    List<ShortUrlHit> findAllByShortUrl(String shortUrl);
}
