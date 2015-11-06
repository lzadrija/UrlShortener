package com.lzadrija.persistance;

import static com.lzadrija.persistance.JpaConfiguration.DbProperty.DRIVER;
import static com.lzadrija.persistance.JpaConfiguration.DbProperty.JPA_DIALECT;
import static com.lzadrija.persistance.JpaConfiguration.DbProperty.PASSWORD;
import static com.lzadrija.persistance.JpaConfiguration.DbProperty.URL;
import static com.lzadrija.persistance.JpaConfiguration.DbProperty.USERNAME;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:JpaConfiguration.properties")
@EnableJpaRepositories(basePackages = {"com.lzadrija.account", "com.lzadrija.url"})
public class JpaConfiguration {

    private static final String[] ENTITYMANAGER_PACKAGES_TO_SCAN = new String[]{"com.lzadrija.account", "com.lzadrija.url"};

    @Autowired
    private Environment env;

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean emFactoryBean = new LocalContainerEntityManagerFactoryBean();
        emFactoryBean.setJpaVendorAdapter(vendorAdapter);
        emFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        emFactoryBean.setDataSource(dataSource());

        Properties properties = new Properties();
        properties.setProperty(JPA_DIALECT.getName(), env.getRequiredProperty(JPA_DIALECT.getName()));
        emFactoryBean.setJpaProperties(properties);

        emFactoryBean.afterPropertiesSet();

        return emFactoryBean.getObject();
    }

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty(DRIVER.getName()));
        dataSource.setUrl(env.getRequiredProperty(URL.getName()));
        dataSource.setUsername(env.getRequiredProperty(USERNAME.getName()));
        dataSource.setPassword(env.getRequiredProperty(PASSWORD.getName()));

        return dataSource;
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    enum DbProperty {

        DRIVER("db.driver"),
        URL("db.url"),
        USERNAME("db.username"),
        PASSWORD("db.password"),
        JPA_DIALECT("hibernate.dialect");

        private final String propName;

        private DbProperty(String propertyName) {
            this.propName = propertyName;
        }

        String getName() {
            return propName;
        }

    }

}
