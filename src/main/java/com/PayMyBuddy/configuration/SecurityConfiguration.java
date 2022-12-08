package com.PayMyBuddy.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.PayMyBuddy.service.UserService;

@Configuration
@ComponentScan(basePackages = { "com.PayMyBuddy.security" })
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserService userService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			.antMatchers(
				 "/registration",
				 "/h2",
				 "/login",
	                "/js/**",
	                "/css/**").permitAll()
	   		.anyRequest()
	   		.authenticated()
	   		.and()
	   		.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/home", true)
	   		.and()
	   		.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
	   		.logoutSuccessUrl("/login");
	}

}
