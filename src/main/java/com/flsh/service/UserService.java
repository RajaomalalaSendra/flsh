package com.flsh.service;

import com.flsh.model.Login;
import com.flsh.model.User;
import java.util.List;

public interface UserService {
	void register(User user);
	
	User validateUser(Login login);
	
	User findByUsername(String username);
}