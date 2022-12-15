package com.sinta.sinta_app.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class PostgresqlConfiguration {

    @Value("${DRIVER_NAME}")
    private String driver;

    @Value("${DB_USERNAME}")
    private String username;

    @Value("${DB_PASSWORD}")
    private String password;

    @Value("${URL}")
    private String url;

    @Bean
    public DataSource hikariDataSource(){
        HikariDataSource hikari = new HikariDataSource();
        hikari.setDriverClassName(driver);
        hikari.setUsername(username);
        hikari.setPassword(password);
        hikari.setConnectionTimeout(10000);
        hikari.setJdbcUrl(url);
        return hikari;
    }
}
