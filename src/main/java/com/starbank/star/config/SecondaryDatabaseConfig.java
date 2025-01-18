package com.starbank.star.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class SecondaryDatabaseConfig {

    @Primary
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
}