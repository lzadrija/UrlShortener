package com.lzadrija.url.security;

import com.lzadrija.account.Account;
import com.lzadrija.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (accountId) -> {
            Account a = accountRepository.findOne(accountId);
            if (a != null) {
                return new User(a.getId(), a.getPassword(), true, true, true, true,
                                AuthorityUtils.createAuthorityList("USER"));
            }
            throw new UsernameNotFoundException("Could not find the account with account ID: \"" + accountId + "\"");
        };
    }

}
