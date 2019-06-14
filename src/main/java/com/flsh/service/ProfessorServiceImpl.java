package com.flsh.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;
import com.flsh.model.Professors;
import com.flsh.model.User;
import com.flsh.interfaces.ProfessorService;
public class ProfessorServiceImpl implements ProfessorService {
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ProfessorServiceImpl(DataSource dsrc) {
		jdbcTemplate = new JdbcTemplate(dsrc);
	}

	@Override
	public List<Professors> getAllProfessor() {
		String sql = "select * from Professeur join Utilisateur on Utilisateur.uti_id = Professeur.uti_id";
		List<Professors> professors = jdbcTemplate.query(sql, new ProfessorMapper());
		return professors;
	}

	@Override
	public Professors getProfessorDetails(int id) {
		String sql = "SELECT * FROM Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id WHERE prof_id = " + id ;
		List<Professors> professors = jdbcTemplate.query(sql, new ProfessorMapper());
		return professors.size() > 0 ? professors.get(0) : null;
	}
	private boolean checkUsernameExists(String username, int id) {
		String sql = "select * from Professeur join Utilisateur on Utilisateur.uti_id = Professeur.uti_id where uti_login = '"+username+"'";
		if(id != 0) {
			sql += " and uti_id != " + id;
		}
		List<Professors> prof = jdbcTemplate.query(sql, new ProfessorMapper());
		return prof.size() > 0;
	}
	private boolean checkEmailExists(String email, int id) {
		String sql = "select * from Professeur join Utilisateur on Utilisateur.uti_id = Professeur.uti_id where uti_email = '"+email+"'";
		if(id != 0) {
			sql += " and uti_id != " + id;
		}
		List<Professors> prof = jdbcTemplate.query(sql, new ProfessorMapper());
		return prof.size() > 0;
	}

	@Override
	public JSONObject saveProfessor(Professors professor) {
		// TODO Auto-generated method stub
		JSONObject rtn = new JSONObject();
		if(this.checkUsernameExists(professor.getProfessorName(), professor.getProfessorId())) {
			rtn.put("status", 0);
	  	    rtn.put("message", professor.getProfessorId() == 0 ? "Echec de l'enregistrement! Le nom de professeur existe" : "Le nom de professeur déjà utilisé par un autre utilisateur");
			return rtn;
		}
		if (this.checkEmailExists(professor.getProfessorEmail(), professor.getProfessorId())) {
			rtn.put("status", 0);
	  	    rtn.put("message", professor.getProfessorId() == 0 ? "Echec de l'enregistrement! L'email que vous avez entrez existe deja" : "L'email que vous avez entrez est déjà utilisé par un autre utilisateur");
			return rtn;
		}
		
		String sql = professor.getProfessorId() == 0 ? "insert into Professeur(prof_nom, prof_prenom, prof_adresse, prof_email, prof_contact, uti_id) values(?, ?, ?, ?, ?, ?)" : 
										"UPDATE Professeur SET prof_nom = ?, prof_prenom = ?, prof_adresse = ?, prof_email = ?, prof_contact = ?, uti_id = ? WHERE prof_id = ?";
		boolean save = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, professor.getProfessorLastName());
				ps.setString(2, professor.getProfessorName());
				ps.setString(3, professor.getProfessorAdresse());
				ps.setString(4, professor.getProfessorEmail());
				ps.setString(5, professor.getProfessorContact());
				ps.setInt(6, professor.getUserId());
				if (professor.getProfessorId() != 0) ps.setInt(7, professor.getProfessorId());
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		rtn.put("status", save ? 1 : 0);
  	    rtn.put("message", save ? "Enregistré avec succès" : "Echec de l'enregistrement! Veuillez réessayer");
		return rtn;
	}
}

class ProfessorMapper implements RowMapper<Professors> {
	public Professors mapRow(ResultSet rs, int arg1) throws SQLException {
	    Professors prof = new Professors();
	    prof.setProfessorId(rs.getInt("prof_id"));
	    prof.setProfessorName(rs.getString("prof_nom"));
	    prof.setProfessorLastName(rs.getString("prof_prenom"));
	    prof.setProfessorAdresse(rs.getString("prof_adresse"));
	    prof.setProfessorEmail(rs.getString("prof_email"));
	    prof.setProfessorContact(rs.getString("prof_contact"));
	    prof.setUserId(rs.getInt("uti_id"));
	    return prof;
	}

}