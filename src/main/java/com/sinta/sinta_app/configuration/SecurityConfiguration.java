package com.sinta.sinta_app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfiguration {
    
    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.httpBasic().disable().csrf().disable().build();
    }
}
