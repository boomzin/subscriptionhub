package com.boomzin.subscriptionhub.config;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfig {

    @Bean
    @ConfigurationProperties("db")
    DataSource dataSource() {
        return new HikariDataSource();
    }

    @Bean
    DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    ConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource()));
    }

    @Bean
    Settings jooqSettings() {
        Settings settings = new Settings();
        settings.setRenderSchema(true);
        settings.setExecuteWithOptimisticLocking(true);
        settings.withExecuteWithOptimisticLockingExcludeUnversioned(true);
        settings.setRenderNameCase(RenderNameCase.LOWER);
        return settings;
    }

    @Bean
    DSLContext context() {
        return new DefaultDSLContext(connectionProvider(), SQLDialect.POSTGRES, jooqSettings());
    }
}
