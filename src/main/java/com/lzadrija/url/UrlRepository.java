package com.lzadrija.url;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<RedirectUrl, String> {

}
