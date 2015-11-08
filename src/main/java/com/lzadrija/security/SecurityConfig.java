package com.lzadrija.security;

import org.springframework.context.annotation.Configuration;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.httpBasic()
                .authenticationEntryPoint(new ErroneousAuthenticationEntryPoint())
                .and().authorizeRequests()
                .antMatchers(POST, "/url-shortener/register").hasRole("USER")
//                .antMatchers(GET, "/url-shortener/statistic").hasRole("USER")
                .and().csrf().disable();
    }

}
