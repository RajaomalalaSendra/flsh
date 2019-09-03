package com.flsh.interfaces;

import javax.sql.DataSource;

import com.flsh.model.Login;
import com.flsh.model.User;
import java.util.List;

import org.json.JSONObject;

public interface UserService {
	void register(User user);
	
	User validateUser(Login login);
	
	User findByUsername(String username);
	
	User getUserDetails(int id);
	
	List<User> getAllUser();
	
	JSONObject saveUser(User user);
	
	JSONObject deleteUser(int id);
	
	JSONObject saveAccount(int id, String lastname, String firstname, String username, String  email, String  type, String  newpassword, String  password);

	int getUsersNumber();
	
}