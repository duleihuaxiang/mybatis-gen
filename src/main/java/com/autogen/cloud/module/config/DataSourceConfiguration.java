package com.autogen.cloud.module.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by Spector on 2017/6/6.
 */
@Configuration
@PropertySource("classpath:mybatis-config.properties")
public class DataSourceConfiguration {

    @Value("${jdbc_driver}")
    private String driver;

    @Value("${jdbc_url}")
    private String url;

    @Value("${jdbc_user}")
    private String username;

    @Value("${jdbc_password}")
    private String password;

    @Bean
    public DriverManagerDataSource dataSource() throws ClassNotFoundException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    public String getDatabaseName() {
        return url.substring(url.indexOf("/", url.indexOf("//")+2)+1, url.indexOf("?"));
    }
}
