package de.socketTest.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecConfig extends WebSecurityConfigurerAdapter {

    @Override
    //@SuppressFBWarnings(value = "SPRING_CSRF_PROTECTION_DISABLED", justification = "csrf disabled to avoid 405 error in POST Requests")
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().formLogin().permitAll();

        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }
}