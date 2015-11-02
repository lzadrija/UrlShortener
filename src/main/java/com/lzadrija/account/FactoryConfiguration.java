package com.lzadrija.account;

import com.lzadrija.persistance.JpaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Configuration
@Import(JpaConfiguration.class)
class FactoryConfiguration {

    @Autowired
    private Environment env;
    @Autowired
    private AccountRepository repo;

    @Bean
    AccountFactory accountFactory() {
        return new AccountFactory(repo, env);
    }

}
