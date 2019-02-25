package com.nvidia.resources.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.PathResource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Class who Configure the database connection
 * 
 * @author Satnam Singh
 * @since 1.0
 *
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.nvidia.resources" })
@PropertySource(value = { "file:${RESOURCES_ENV}/config/config.properties" }, ignoreResourceNotFound = false)
@EnableCaching
public class JpaConfiguration {

	@Autowired
	private Environment env;

	@Value("${ehcache.file.path}")
	private String ehCacheFilePath;

	@Bean
	public DataSource dataSource() {
		final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		return dsLookup.getDataSource(env.getProperty("jndi"));
	}

	/**
	 * Method used to set the JPA properties
	 * 
	 */
	@Bean
	public Map<String, Object> jpaProperties() {
		Map<String, Object> props = new HashMap<>();
		props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		return props;
	}

	/**
	 * Method used to set the JPA Vendor Adptor
	 * 
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		return hibernateJpaVendorAdapter;
	}

	/**
	 * Method used to set the Transaction Manager
	 * 
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(localContainerEntityManagerFactoryBean().getObject());
	}

	/**
	 * Method used to set the JEntity Manager Factory
	 * 
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
		localContainerEntityManagerFactoryBean.setDataSource(this.dataSource());
		localContainerEntityManagerFactoryBean.setJpaPropertyMap(this.jpaProperties());
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(this.jpaVendorAdapter());
		return localContainerEntityManagerFactoryBean;
	}

	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new PathResource(ehCacheFilePath));
		cmfb.setShared(true);
		return cmfb;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
