package com.starbank.star.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.StringJoiner;

@Configuration
public class LiquibaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.second-datasource.liquibase")
    public LiquibaseProperties secondLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase secondLiquibase(
            @Qualifier("secondDataSource") DataSource dataSource,
            LiquibaseProperties secondLiquibaseProperties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(secondLiquibaseProperties.getChangeLog());

        // Преобразуем список контекстов в строку, разделенную запятыми
        if (secondLiquibaseProperties.getContexts() != null) {
            StringJoiner contextJoiner = new StringJoiner(",");
            secondLiquibaseProperties.getContexts().forEach(contextJoiner::add);
            liquibase.setContexts(contextJoiner.toString());
        }

        liquibase.setDefaultSchema(secondLiquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema(secondLiquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(secondLiquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(secondLiquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(secondLiquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst(secondLiquibaseProperties.isDropFirst());
        liquibase.setShouldRun(secondLiquibaseProperties.isEnabled());
        return liquibase;
    }
}