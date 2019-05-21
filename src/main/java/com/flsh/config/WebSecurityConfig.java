package com.flsh.config;

import com.flsh.service.UserService;
import com.flsh.utils.Sha1PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new Sha1PasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests().anyRequest().hasAnyRole("ADMIN", "PROF", "SECRETARY")
			.and()
			.authorizeRequests().antMatchers("/login**").permitAll()
			.and()
			.formLogin().loginPage("/login").loginProcessingUrl("/loginProcess").permitAll()
			.and()
			.logout().logoutSuccessUrl("/login").permitAll()
		    .and()
		    .csrf().disable();
	}
	
}
