package com.flsh.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import com.flsh.interfaces.EducationService;
import com.flsh.interfaces.PeriodService;
import com.flsh.service.EducationServiceImpl;
import com.flsh.service.PeriodServiceImpl;
import com.flsh.interfaces.UserService;
import com.flsh.service.UserServiceImpl;
import com.flsh.interfaces.ProfessorService;
import com.flsh.interfaces.StudentService;
import com.flsh.interfaces.TeachingService;
import com.flsh.service.ProfessorServiceImpl;
import com.flsh.service.StudentServiceImpl;
import com.flsh.service.TeachingServiceImpl;
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

	/**
	 * Beans configuration
	 * @return @Bean
	 */
	@Bean
    public UserService userService() {
        UserService usr = new UserServiceImpl(dataSource());
        return usr;
    }
	
	@Bean
	public EducationService educationService() {
		EducationService esrv = new EducationServiceImpl(dataSource());
		return esrv;
	}
	
	@Bean
    public ProfessorService professorService() {
        ProfessorService prof = new ProfessorServiceImpl(dataSource());
        return prof;
    }
	
	@Bean
	public TeachingService teachingService() {
		TeachingService teaching = new TeachingServiceImpl(dataSource());
		return teaching;
	}
	
	@Bean
	public PeriodService periodService() {
		PeriodService ps = new PeriodServiceImpl(dataSource());
		return ps;
	}
	
	@Bean
	public StudentService studentService() {
		StudentService stsrv = new StudentServiceImpl(dataSource());
		return stsrv;
	}
	
	/**
	 * DataSource configuration
	 * @return dataSource
	 */
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
