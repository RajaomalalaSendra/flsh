package com.flsh.service;

import javax.sql.DataSource;

import com.flsh.model.Login;
import com.flsh.model.User;


public interface UserService {
	void register(User user);
	
	User validateUser(Login login);
}