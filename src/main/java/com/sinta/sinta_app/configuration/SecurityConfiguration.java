package com.sinta.sinta_app.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.sinta.sinta_app.security.filter.JwtFilter;

@Configuration
public class SecurityConfiguration {

    private final JwtFilter filter;

    @Autowired
    public SecurityConfiguration(JwtFilter filter){
        this.filter = filter;
    }
    
    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.
                addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class).
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
                authorizeRequests()
                .mvcMatchers("/trip/create/**").authenticated()
                .anyRequest().permitAll().
                and()
                .build();
    }
}
