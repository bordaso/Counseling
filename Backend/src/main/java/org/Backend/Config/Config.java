package org.Backend.Config;

import java.util.ArrayList;
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
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
// @EnableAspectJAutoProxy(proxyTargetClass=true)
// @EnableLoadTimeWeaving(aspectjWeaving=AspectJWeaving.ENABLED)
// @EnableSpringConfigured
@EnableWebSecurity 
@ComponentScan({ "org.Backend", "org.Backend.Config", "org.Backend.DAOs", "org.Backend.Entities",
		"org.Backend.Services", "org.Backend.Utilities", "org.Backend.Converters" })
public class Config  extends WebSecurityConfigurerAdapter{

	// org.apache.log4j.Logger logger = LogManager.getLogger(Config.class);
	// logger.debug("test");
	
	@Autowired
    CustomSuccessHandler customSuccessHandler;
	
	@Autowired
	CustomAccessDeniedHandler customAccessDeniedHandler;	
	
	@Autowired
	CustomAuthenticationFailureHandler  customAuthenticationFailureHandler;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	
	public Config() {
		System.out.println("hello from config");
		
	}

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

	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(authProvider());
	    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		 http.authorizeRequests()
		 	.antMatchers("/").permitAll() 
		 	//.antMatchers("/rest/**").authenticated()
		 	.antMatchers("/rest/service/logincheck").permitAll()
		 	.antMatchers("/rest/service/shutdown").permitAll()
		 	.antMatchers("/rest/service/all/**").access("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PATIENT')")
		 	.antMatchers("/rest/service/employee/**").access("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
		 	.antMatchers("/rest/service/user/**").access("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
		 	.antMatchers("/login.component.html").access("hasRole('ROLE_ANONYMOUS')")
		 	.antMatchers("/rest/service/admin/**").access("hasRole('ROLE_EMPLOYEE') and hasRole('ROLE_ADMIN')")
	        .antMatchers("/dashboard/**").access("hasRole('ROLE_EMPLOYEE')")
	        .and().userDetailsService(userDetailsService).formLogin()
	        .loginPage("/#/login")
	        .loginProcessingUrl("/dashboard/login")
	        .successHandler(customSuccessHandler)	
	        .failureHandler(customAuthenticationFailureHandler)
	        .usernameParameter("id").passwordParameter("pw")
	     //   .and().csrf()
	        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID")	        
	        .and().csrf().disable()
	     //   .and()
	        .exceptionHandling().accessDeniedPage("/p401.html").accessDeniedHandler(customAccessDeniedHandler)
	        .and().cors();
		

	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	  @Bean
	  public CorsConfigurationSource corsConfigurationSource() {
	        final CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(new ArrayList<String>() {{add("*");}});
	        configuration.setAllowedMethods(new ArrayList<String>() {{add("HEAD");add("GET");add("POST");add("PUT");add("DELETE");add("PATCH"); }}); 
	        // setAllowCredentials(true) is important, otherwise:
	        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
	        configuration.setAllowCredentials(true);
	        // setAllowedHeaders is important! Without it, OPTIONS preflight request
	        // will fail with 403 Invalid CORS request
	        configuration.setAllowedHeaders(new ArrayList<String>() {{add("Authorization");add("Cache-Control");add("Content-Type");add("bdid");add("bdresponse");}});
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	
//	@Bean
//	public RequestContextListener getRequestContextListener() {
//	    return new RequestContextListener();
//	}

}
