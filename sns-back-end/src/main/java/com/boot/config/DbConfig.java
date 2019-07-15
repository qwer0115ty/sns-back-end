package com.boot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableJpaRepositories(basePackages="com.boot.jpa",  entityManagerFactoryRef="entityManagerFactory", transactionManagerRef="transactionManager")
@EnableTransactionManagement
@Configuration
@EnableConfigurationProperties
public class DbConfig {
	@Bean
	@ConfigurationProperties("hikari.config")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource(@Autowired HikariConfig hikariConfig) {
		HikariDataSource d = new HikariDataSource(hikariConfig);
		return d;
	}
	
	@Bean("entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Autowired DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean b = new LocalContainerEntityManagerFactoryBean();
		b.setDataSource(dataSource);
		b.setPackagesToScan("com.boot.model");
		
		HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
		vendor.setShowSql(true);
		b.setJpaVendorAdapter(vendor);
		
		return b;
	}
	
	@Bean("transactionManager")
	public JpaTransactionManager jpaTransactionManager(@Autowired DataSource dataSource) {
		JpaTransactionManager tr = new JpaTransactionManager();
		tr.setDataSource(dataSource);
		return tr;
	}
}
