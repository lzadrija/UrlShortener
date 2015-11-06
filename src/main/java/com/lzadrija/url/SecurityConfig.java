package com.lzadrija.url;

import com.lzadrija.account.Account;
import com.lzadrija.account.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/url-shortener/register/**").access("hasRole('ROLE_USER')").
                and().formLogin();
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    protected static class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

        private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);

        @Autowired
        AccountRepository accountRepository;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Bean
        UserDetailsService userDetailsService() {
            return (accountId) -> {
                logger.error("in userDetailsService" + accountId);
                Account a = accountRepository.findOne(accountId);
                if (a != null) {
                    return new User(a.getId(), a.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("USER", "write"));
                }
                throw new UsernameNotFoundException("Could not find the account with account ID: \"" + accountId + "\"");
            };
        }

    }
}
