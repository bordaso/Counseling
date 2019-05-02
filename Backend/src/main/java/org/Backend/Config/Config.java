package org.Backend.Config;

import java.util.Properties;

import javax.sql.DataSource;

import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Message;
import org.Backend.Entities.MessageDetails;
import org.Backend.Entities.NotificationSetup;
import org.Backend.Entities.Patient;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
// @EnableAspectJAutoProxy(proxyTargetClass=true)
// @EnableLoadTimeWeaving(aspectjWeaving=AspectJWeaving.ENABLED)
// @EnableSpringConfigured
@EnableWebSecurity
@ComponentScan({ "org.Backend", "org.Backend.Config", "org.Backend.DAOs", "org.Backend.Entities",
		"org.Backend.Services", "org.Backend.Utilities", "org.Backend.Converters" })
public class Config extends WebSecurityConfigurerAdapter {

	// org.apache.log4j.Logger logger = LogManager.getLogger(Config.class);
	// logger.debug("test");

	@Autowired
	private Environment env;

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());
		Properties props = new Properties();
		props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

		factoryBean.setHibernateProperties(props);
		factoryBean.setAnnotatedClasses(User.class, Employee.class, Patient.class, Bookings.class, BookingDetails.class,
				Message.class, MessageDetails.class, NotificationSetup.class);
		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());

		return transactionManager;
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("USER").and().withUser("admin")
//				.password("{noop}password").roles("USER", "ADMIN");
//	}
//	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http
//		.authorizeRequests()
//			.anyRequest().authenticated()
//			.and()
//		.formLogin().and()
//		.httpBasic();
		
//	http.formLogin().and()
//	.authorizeRequests()
//	.antMatchers("/login").authenticated()
//	.antMatchers(HttpMethod.POST, "/postMethodPlace").authenticated()
//	.anyRequest().permitAll().and()
//	.requiresChannel()
//	.antMatchers("/login").requiresSecure();
		
		
		http
		.formLogin()
		.and()
		.authorizeRequests()
		.antMatchers("/login.html").authenticated()
		.anyRequest().permitAll();
//		.and().requiresChannel()
//		.antMatchers("/login.html").requiresSecure().and()
//        .portMapper()               
//        .http(8080).mapsTo(8443);;
	}
	
	
    @Bean
    public UserDetailsService userDetailsService() {
    	// withDefaultPasswordEncoder() is allowed for testing/demo purposes
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
        return manager;

    }

}
