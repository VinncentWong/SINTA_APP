package com.sinta.sinta_app.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfiguration {
    
    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.
                httpBasic().disable().
                csrf().disable().
                cors((customizer) -> {
                    customizer.configurationSource((request) -> {
                        CorsConfiguration corsConfiguration = new CorsConfiguration();
                        corsConfiguration.setAllowedHeaders(List.of("*"));
                        corsConfiguration.setAllowedMethods(List.of("*"));
                        corsConfiguration.setAllowedOrigins(List.of("*"));
                        return corsConfiguration;
                    });
                }).
                authorizeRequests().anyRequest().permitAll().
                and()
                .build();
    }
}
