package com.flsh.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.flsh.controller.HomeController;
import com.flsh.model.Login;
import com.flsh.model.User;

public class UserServiceImpl implements UserService {
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	public UserServiceImpl(DataSource dsrc) {
		jdbcTemplate = new JdbcTemplate(dsrc);
	}
	

	public void register(User user) {
		String sql = "insert into users values(?,?,?,?)";
		jdbcTemplate.update(sql, new Object[] { user.getUsername(), user.getEmail(), user.getPassword(), user.getType() });
	}

	public User validateUser(Login login) {
		logger.info(login.getUsername()+"      "+ login.getPassword());
		String sql = "select * from Utilisateur where ( uti_login='" + login.getUsername() + "' or uti_email='" + login.getUsername() + "') and uti_passwd=sha1('" + login.getPassword()
		    + "')";
		logger.info(sql);
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0 ? users.get(0) : null;
	}
}

class UserMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int arg1) throws SQLException {
	    User user = new User();
	    user.setUsername(rs.getString("uti_login"));
	    user.setPassword(rs.getString("uti_passwd"));
	    user.setEmail(rs.getString("uti_email"));
	    user.setType(rs.getString("uti_type"));
	    return user;
	}
}
