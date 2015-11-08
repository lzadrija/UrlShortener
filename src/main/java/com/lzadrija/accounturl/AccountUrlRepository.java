package com.lzadrija.accounturl;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountUrlRepository extends JpaRepository<AccountRegisteredUrl, AccountRegisteredUrlId> {

    List<AccountRegisteredUrl> findAllByAccountId(String accountId);
}
