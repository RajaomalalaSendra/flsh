package com.flsh.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.flsh.model.Authorities;
import com.flsh.model.Professor;
import com.flsh.model.User;
import com.mysql.jdbc.Statement;
import com.flsh.interfaces.ProfessorService;
public class ProfessorServiceImpl implements ProfessorService {
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ProfessorServiceImpl(DataSource dsrc) {
		jdbcTemplate = new JdbcTemplate(dsrc);
	}

	@Override
	public List<Professor> getAllProfessor() {
		String sql_prof = "SELECT * FROM  Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id WHERE Utilisateur.uti_type = 2 ";		
		List<Professor> professors = jdbcTemplate.query(sql_prof, new ProfessorMapper());
		return professors;
	}

	@Override
	public Professor getProfessorDetails(int id) {
		String sql = "SELECT * FROM Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id WHERE prof_id = " + id ;
		List<Professor> professors = jdbcTemplate.query(sql, new ProfessorMapper());
		return professors.size() > 0 ? professors.get(0) : null;
	}
	private boolean checkUsernameExists(String username, int uti_id) {
		String sql = "SELECT * FROM Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id WHERE Utilisateur.uti_login = '"+username+"'";
		if(uti_id != 0) {
			sql += " and Professeur.uti_id != " + uti_id;
		}
		List<Professor> prof = jdbcTemplate.query(sql, new ProfessorMapper());
		return prof.size() > 0;
	}
	private boolean checkEmailExists(String email, int uti_id) {
		String sql = "SELECT * FROM Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id WHERE Utilisateur.uti_email = '"+email+"'";
		if(uti_id != 0) {
			sql += " and Professeur.uti_id != " + uti_id;
		}
		List<Professor> prof = jdbcTemplate.query(sql, new ProfessorMapper());
		return prof.size() > 0;
	}

	@Override
	public JSONObject saveProfessor(Professor professor) {
		// TODO Auto-generated method stub
		JSONObject rtn = new JSONObject();
		if(this.checkUsernameExists(professor.getProfessorName(), professor.getUserId())) {
			rtn.put("status", 0);
	  	    rtn.put("message", professor.getUserId() == 0 ? "Echec de l'enregistrement! Le nom de professeur existe" : "Le nom de professeur déjà utilisé par un autre utilisateur");
			return rtn;
		}
		if (this.checkEmailExists(professor.getProfessorEmail(), professor.getUserId())) {
			rtn.put("status", 0);
	  	    rtn.put("message", professor.getUserId() == 0 ? "Echec de l'enregistrement! L'email que vous avez entrez existe deja" : "L'email que vous avez entrez est déjà utilisé par un autre utilisateur");
			return rtn;
		}
		
		// the sql for the utilisateur table
		String sql_user;
		if (professor.getUserId() == 0) {
			sql_user = "INSERT INTO Utilisateur(uti_nom, uti_prenom, uti_login, uti_email, uti_type, uti_passwd) VALUES(?, ?, ?, ?, sha1(?), ?)";
		} else {
			sql_user = professor.getProfessorPassword().equals("") ? "UPDATE Utilisateur SET  uti_nom = ?, uti_prenom = ?, uti_login = ?, uti_email = ?, uti_type = ? WHERE uti_id = ?" : "UPDATE Utilisateur SET  uti_nom = ?, uti_prenom = ?, uti_login = ?, uti_email = ?, uti_type = ?, uti_passwd = sha1(?) WHERE uti_id = ?";
		}
		
		System.out.println(" Professor Id: " + professor.getProfessorId());
		System.out.println("||||||||User Type: " + professor.getUserType()+"|||||||||||");
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
		    @Override
		    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		        PreparedStatement statement = con.prepareStatement(sql_user, Statement.RETURN_GENERATED_KEYS);
		        statement.setString(1, professor.getProfessorLastName());
		        statement.setString(2, professor.getProfessorName());
		        statement.setString(3, professor.getProfessorLogin());
		        statement.setString(4, professor.getProfessorEmail());
		        statement.setInt(5, professor.getUserType());
		        if(!professor.getProfessorPassword().equals("")) statement.setString(6, professor.getProfessorPassword());
		        if (professor.getUserId() != 0) statement.setInt( professor.getProfessorPassword().equals("") ? 6 : 7, professor.getUserId());
		        return statement;
		    }
		}, holder);
        // get the key of the utilisateur table
		int uti_id_key;
		if (professor.getUserId() == 0) {
			uti_id_key = holder.getKey().intValue();
		} else {
			uti_id_key = professor.getUserId();
		}
		System.out.println(" Uti User Key: " + uti_id_key);
        // the sql for the professeur table
		String sql_prof = professor.getProfessorId() == 0 ? "INSERT INTO Professeur(prof_adresse, prof_contact, uti_id) values( ?, ?, ?)" : 
										"UPDATE Professeur SET  prof_adresse = ?, prof_contact = ?, uti_id = ? WHERE prof_id = ?";
		boolean save_prof = jdbcTemplate.execute (sql_prof, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, professor.getProfessorAdresse());
				ps.setString(2, professor.getProfessorContact());
				ps.setInt(3, uti_id_key);
				if (professor.getProfessorId() != 0) ps.setInt(4, professor.getProfessorId());
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		rtn.put("status", save_prof ? 1 : 0);
  	    rtn.put("message", save_prof ? "Enregistré avec succès" : "Echec de l'enregistrement! Veuillez réessayer");
		return rtn;
	}

	@Override
	public JSONObject deleteProfessor(int id, int user_id) {
		// TODO Auto-generated method stub
		String sql_professor = "DELETE FROM Professeur WHERE Professeur.prof_id = "+ id + " AND Professeur.uti_id = ?";
		String sql_user = "DELETE FROM Utilisateur WHERE Utilisateur.uti_id = ?";
		JSONObject rtn = new JSONObject();
	    jdbcTemplate.update(sql_professor, user_id);
	    int j = jdbcTemplate.update(sql_user, user_id);
	    rtn.put("status", j >= 0 ? 1 : 0);
	    rtn.put("message", j >= 0 ? "Supprimé!" : "Echec de la suppression! Veuillez réessayer.");
		return rtn;
	}
}

class ProfessorMapper implements RowMapper<Professor> {
	public Professor mapRow(ResultSet rs, int arg1) throws SQLException {
		Professor prof = new Professor();
		prof.setProfessorId(rs.getInt("prof_id"));
		prof.setProfessorLastName(rs.getString("uti_nom"));
		prof.setProfessorName(rs.getString("uti_prenom"));
		prof.setProfessorEmail(rs.getString("uti_email"));
		prof.setProfessorLogin(rs.getString("uti_login"));
		prof.setProfessorPassword(rs.getString("uti_passwd"));
	    prof.setProfessorAdresse(rs.getString("prof_adresse"));
	    prof.setProfessorContact(rs.getString("prof_contact"));
	    prof.setUserId(rs.getInt("uti_id"));
	    prof.setUserType(rs.getInt("uti_type"));
	    return prof;
	}

}