package com.lzadrija.url;

import com.lzadrija.account.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<RedirectUrl, String> {

    List<RedirectUrl> findAllByAccount(Account account);
}
