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
<<<<<<< HEAD
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
=======
>>>>>>> Fix config not working
	
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
		String sql = "SELECT * FROM Utilisateur JOIN Role ON Role.rol_id = Utilisateur.uti_type WHERE uti_type != 2";
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users;
	}


	@Override
	public User getUserDetails(int id) {
		String sql = "select * from Utilisateur join Role on Role.rol_id = Utilisateur.uti_type where uti_id=" + id ;
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0 ? users.get(0) : null;
	}

	//Verification functions
	private boolean checkUsernameExists(String username, int id) {
		String sql = "select * from Utilisateur join Role on Role.rol_id = Utilisateur.uti_type where uti_login = '"+username+"'";
		if(id != 0) {
			sql += " and uti_id != " + id;
		}
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0;
	}
	
	private boolean checkEmailExists(String email, int id) {
		String sql = "select * from Utilisateur join Role on Role.rol_id = Utilisateur.uti_type where uti_email = '"+email+"'";
		if(id != 0) {
			sql += " and uti_id != " + id;
		}
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0;
	}

	private boolean checkUserPassword(int id, String password) {
		String sql = "select * from Utilisateur join Role on Role.rol_id = Utilisateur.uti_type where uti_passwd = sha1('"+password+"') and uti_id = " + id;
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0;
	}
	
	private boolean checkCanDeleteUser(int id) {
		String sql = "select * from Utilisateur join Role on Role.rol_id = Utilisateur.uti_type where uti_type = '1' and uti_id != " + id;
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0;
	}

	private boolean checkCanUpdateProfil(User user) {
		User lastUser = this.getUserDetails(user.getId());
		if(!lastUser.getType().equals(user.getType()) && lastUser.getType().equals("1")) {
			String sql = "select * from Utilisateur join Role on Role.rol_id = Utilisateur.uti_type where uti_type = '1' and uti_id != " + user.getId();
			List<User> users = jdbcTemplate.query(sql, new UserMapper());
			return users.size() > 0;
		}
		return true;
	}

	@Override
	public JSONObject  saveUser(User user) {
		// TODO Auto-generated method stub
		JSONObject rtn = new JSONObject();
		if(this.checkUsernameExists(user.getUsername(), user.getId())) {
			rtn.put("status", 0);
	  	    rtn.put("message", user.getId() == 0 ? "Echec de l'enregistrement! Le nom d'utilisateur existe" : "Le nom d'utilisateur déjà utilisé par un autre utilisateur");
			return rtn;
		}
		if (this.checkEmailExists(user.getEmail(), user.getId())) {
			rtn.put("status", 0);
	  	    rtn.put("message", user.getId() == 0 ? "Echec de l'enregistrement! L'email que vous avez entré existe deja" : "L'email que vous avez entré est déjà utilisé par un autre utilisateur");
			return rtn;
		}
		
		if(user.getId() != 0 && !this.checkCanUpdateProfil(user)) {
			rtn.put("status", 0);
	  	    rtn.put("message", "On ne peut changer de type un super utilisateur si il n'y a qu'un seul utilisateur de ce type dans la base.");
			return rtn;
		}
		
		String sql;
		if (user.getId() == 0 ) {
			sql = "INSERT INTO  Utilisateur(civ_id, uti_nom, uti_prenom, uti_login, uti_email,  uti_type,  uti_passwd) values(?, ?, ?, ?, ?, ?, sha1(?))"; 
		} else {
			sql = user.getPassword().equals("") ? "UPDATE Utilisateur SET civ_id = ?, uti_nom = ?, uti_prenom = ?, uti_login = ?, uti_email = ?,  uti_type = ? WHERE uti_id = ? " : "UPDATE Utilisateur SET civ_id = ?, uti_nom = ?, uti_prenom = ?, uti_login = ?, uti_email = ?, uti_type = ?,  uti_passwd = sha1(?) WHERE uti_id = ?";
		}
										
		boolean save = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, user.getCiv());
				ps.setString(2, user.getLastname());
				ps.setString(3, user.getFirstname());
				ps.setString(4, user.getUsername());
				ps.setString(5, user.getEmail());
				ps.setString(6, user.getType());
				if(!user.getPassword().equals("")) ps.setString(7, user.getPassword());
				if (user.getId() != 0) ps.setInt(user.getPassword().equals("") ? 7 : 8, user.getId());
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		rtn.put("status", save ? 1 : 0);
  	    rtn.put("message", save ? "Enregistré avec succès" : "Echec de l'enregistrement! Veuillez réessayer");
		return rtn;
	}

	@Override
	public JSONObject deleteUser(int id) {
		String sql = "DELETE FROM Utilisateur WHERE uti_id = ?";
		JSONObject rtn = new JSONObject();
		if(!this.checkCanDeleteUser(id)) {
			rtn.put("status", 0);
		    rtn.put("message", "Cet utilisateur ne peut être supprimé. Il doit au moins y avoir un super utilisateur dans la base.");
		    return rtn;
		}
	    int i = jdbcTemplate.update(sql, id);
	    rtn.put("status", i >= 0 ? 1 : 0);
	    rtn.put("message", i >= 0 ? "Supprimé!" : "Echec de la suppression! Veuillez réessayer.");
		return rtn;
	}


	@Override
	public JSONObject saveAccount(int id, String lastname, String firstname, String username, String email, String type,
			String newpassword, String password) {
		JSONObject rtn = new JSONObject();
		if(this.checkUsernameExists(username, id)) {
			rtn.put("status", 0);
	  	    rtn.put("message", "Le nom d'utilisateur déjà utilisé par un autre utilisateur");
			return rtn;
		}
		if(this.checkEmailExists(email, id)) {
			rtn.put("status", 0);
	  	    rtn.put("message", "L'email que vous avez entré est déjà utilisé par un autre utilisateur");
			return rtn;
		}
		if(!newpassword.equals("") && !this.checkUserPassword(id, password) ) {
			rtn.put("status", 0);
	  	    rtn.put("message", "Le mot de passe que vous avez entré est incorrect");
			return rtn;
		}
		String sql = newpassword.equals("") ? "UPDATE Utilisateur SET uti_nom = ?, uti_prenom = ?, uti_login = ?, uti_email = ?,  uti_type = ? WHERE uti_id = ?" : "UPDATE Utilisateur SET uti_nom = ?, uti_prenom = ?, uti_login = ?, uti_email = ?, uti_type = ?, uti_passwd = sha1(?) WHERE uti_id = ?";
		boolean save = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, lastname);
				ps.setString(2, firstname);
				ps.setString(3, username);
				ps.setString(4, email);
				ps.setString(5, type);
				if(!newpassword.equals("")) ps.setString(6, newpassword);
				ps.setInt(newpassword.equals("") ? 6 : 7, id);
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
	    user.setCiv(rs.getInt("civ_id"));
	    user.setLastname(rs.getString("uti_nom"));
	    user.setFirstname(rs.getString("uti_prenom"));
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
