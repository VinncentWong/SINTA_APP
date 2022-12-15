package com.sinta.sinta_app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    
    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.
                httpBasic().disable().
                csrf().disable().
                authorizeRequests().anyRequest().permitAll().
                and()
                .build();
    }
}
