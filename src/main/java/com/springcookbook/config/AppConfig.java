package com.springcookbook.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.springcookbook.controller", "com.springcookbook.service",
		"com.spring_cookbook.dao" })
// @Import({ DatabaseConfig.class, SecurityConfig.class })
public class AppConfig {
	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		final String[] definitions = { "/WEB-INF/tiles.xml" };
		tilesConfigurer.setDefinitions(definitions);
		return tilesConfigurer;
	}

	// declare Tiles as a view resolver
	@Bean
	public ViewResolver tilesViewResolver() {
		TilesViewResolver resolver = new TilesViewResolver();
		return resolver;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/springwebapp");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}

	// @Bean
	// public DataSource dataSource() {
	// DriverManagerDataSource dataSource = new DriverManagerDataSource();
	// dataSource.setDriverClassName("org.postgresql.Driver");
	// dataSource.setUrl("jdbc:postgresql://localhost:5432/db1");
	// dataSource.setUsername("user1");
	// dataSource.setPassword("pass1");
	// return dataSource;
	// }
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}
}
