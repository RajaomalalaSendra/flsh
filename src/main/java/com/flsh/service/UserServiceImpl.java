package com.flsh.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import com.flsh.controller.HomeController;
import com.flsh.interfaces.UserService;
import com.flsh.model.Authorities;
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
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0 ? users.get(0) : null;
	}


	@Override
	public User findByUsername(String username) {
		String sql = "select * from Utilisateur join Role on Role.rol_id = Utilisateur.uti_type where uti_login='" + username + "' or uti_email='" + username + "'";
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0 ? users.get(0) : null;
	}


	@Override
	public List<User> getAllUser() {
		String sql = "select * from Utilisateur join Role on Role.rol_id = Utilisateur.uti_type";
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users;
	}


	@Override
	public User getUserDetails(int id) {
		String sql = "select * from Utilisateur join Role on Role.rol_id = Utilisateur.uti_type where uti_id=" + id ;
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0 ? users.get(0) : null;
	}

	private boolean checkUsernameExists(String username) {
		String sql = "select * from Utilisateur join Role on Role.rol_id = Utilisateur.uti_type where uti_login = '"+username+"'";
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0;
	}
	private boolean checkEmailExists(String email) {
		String sql = "select * from Utilisateur join Role on Role.rol_id = Utilisateur.uti_type where uti_email = '"+email+"'";
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0;
	}

	@Override
	public JSONObject  saveUser(User user) {
		// TODO Auto-generated method stub
		JSONObject rtn = new JSONObject();
		if(this.checkUsernameExists(user.getUsername())) {
			rtn.put("status", 0);
	  	    rtn.put("message", "Echec de l'enregistrement! Le nom d'utilisateur existe");
			return rtn;
		}
		if (this.checkEmailExists(user.getEmail())) {
			rtn.put("status", 0);
	  	    rtn.put("message", "Echec de l'enregistrement! L'email que vous avez entrez existe deja");
			return rtn;
		}
		
		String sql = "insert into Utilisateur(uti_login, uti_email, uti_passwd, uti_type) values(?,?,sha1(?),?)";
		boolean save = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getEmail());
				ps.setString(3, user.getPassword());
				ps.setString(4, user.getType());
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		rtn.put("status", save ? 1 : 0);
  	    rtn.put("message", save ? "Enregistré avec succès" : "Echec de l'enregistrement! Veuillez réessayer");
		return rtn;
	}
}

class UserMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int arg1) throws SQLException {
	    User user = new User();
	    user.setId(rs.getInt("uti_id"));
	    user.setUsername(rs.getString("uti_login"));
	    user.setPassword(rs.getString("uti_passwd"));
	    user.setEmail(rs.getString("uti_email"));
	    user.setType(rs.getString("uti_type"));
	    Set<Authorities> authorities = new HashSet();
	    authorities.add(new Authorities(rs.getString("rol_libelle")));
	    user.setAuthorities(authorities);
	    return user;
	}
}
