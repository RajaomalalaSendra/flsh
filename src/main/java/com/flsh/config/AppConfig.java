package com.flsh.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import com.flsh.service.UserDetailsServiceImp;
import com.flsh.service.UserService;
import com.flsh.service.UserServiceImpl;
import com.flsh.utils.Config;

/**
 * @author Danielson Andriaritiana
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
    "com.flsh"
})
public class AppConfig implements WebMvcConfigurer {

	// Handle HTTP GET requests for /resources/** by efficiently serving
	// static resources under ${webappRoot}/resources/

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		UrlPathHelper pathHelper = new UrlPathHelper();
		pathHelper.setRemoveSemicolonContent(false); // For @MatrixVariable's
		configurer.setUrlPathHelper(pathHelper);
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
	
}
