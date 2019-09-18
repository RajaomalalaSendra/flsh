package com.flsh.config;

import com.flsh.model.User;
import com.flsh.service.UserDetailsServiceImp;
import com.flsh.interfaces.UserService;
import com.flsh.service.UserServiceImpl;
import com.flsh.utils.Config;
import com.flsh.utils.Sha1PasswordEncoder;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	UserService userService;
	
	@Bean
	public HttpFirewall defaultHttpFirewall() {
	    return new DefaultHttpFirewall();
	}
	
	@Bean
    public UserService userService() {
        UserService usr = new UserServiceImpl(dataSource());
        return usr;
    }
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	 
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUsername(Config.DB_USER);
	    dataSource.setPassword(Config.DB_PASSWORD);
	    dataSource.setUrl("jdbc:mysql://"+Config.DB_URL); 
 
        return dataSource;
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImp();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new Sha1PasswordEncoder());
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
           .antMatchers("/resources/**"); // #3
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests().antMatchers("/users**", "/users/", "/educations/cyclesandlevel**", "/educations/cyclesandlevel/", "/educations/periods**", "/educations/periods/").hasAuthority("ADMIN")
	    	.antMatchers("/educations/notes**", "/educations/notes/", "/ue**", "/ue/", "/course/byPeriod**", "/course/byPeriod/").hasAnyAuthority("ADMIN", "PROF")
	    	.antMatchers("/students", "/students/", "/professors", "/professors/", "/ec/saveCoursePeriod", "/ec/saveCoursePeriod/*").hasAnyAuthority("ADMIN", "SECRETARY")
			.anyRequest().hasAnyAuthority("ADMIN", "PROF", "SECRETARY")
			.and()
			.authorizeRequests().antMatchers("/login**").permitAll()
			.and()
			.formLogin().loginPage("/login").loginProcessingUrl("/loginProcess").permitAll()
			.successHandler((req,res,auth)->{    //Success handler invoked after successful authentication
		         for (GrantedAuthority authority : auth.getAuthorities()) {
		            System.out.println(authority.getAuthority());
		            logger.info("Authority "+authority.getAuthority());
		         }
		         System.out.println(auth.getName());
		         logger.info("Successfully logged "+auth.getName());
		         User userLogged = userService.findByUsername(auth.getName());
		         req.getSession().setAttribute("username", userLogged.getUsername());
		         req.getSession().setAttribute("user", userLogged);
		         res.sendRedirect("/scolarLMD/"); // Redirect user to index/home page
		     })
			.failureHandler((req,res,exp)->{  // Failure handler invoked after authentication failure
		         String errMsg="";
		         if(exp.getClass().isAssignableFrom(BadCredentialsException.class)){
		            errMsg="Invalid username or password.";
		         }else{
		            errMsg="Unknown error - "+exp.getMessage();
		         }
		         logger.info("Error : "+ errMsg);
		         req.getSession().setAttribute("message", errMsg);
		         res.sendRedirect("/scolarLMD/login"); // Redirect user to login page with error message.
		      })
			.and()
			.logout().logoutUrl("/signout").logoutSuccessUrl("/login").permitAll()
		    .and()
		    .csrf().disable();
	}
	
}
