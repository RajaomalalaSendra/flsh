package com.flsh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flsh.controller.HomeController;
import com.flsh.interfaces.UserService;
import com.flsh.model.User;

@Service("userDetailsService")
public final class UserDetailsServiceImp implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImp.class);
	
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
	    UserBuilder builder = null;
	    if (user != null) {
	      logger.info("Successfully found user");
	      
	      builder = org.springframework.security.core.userdetails.User.withUsername(username);
	      builder.disabled(!user.isEnabled());
	      builder.password(user.getPassword());
	      String[] authorities = user.getAuthorities()
	          .stream().map(a -> a.getAuthority()).toArray(String[]::new);

	      builder.authorities(authorities);
	    } else {
	      throw new UsernameNotFoundException("User not found.");
	    }
	    return builder.build();
	}
	
	public User getLoggedUser(String username) {
		return userService.findByUsername(username);
	}

}
