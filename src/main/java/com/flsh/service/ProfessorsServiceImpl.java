package com.flsh.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;
import com.flsh.model.Professors;
import com.flsh.model.Login;
public class ProfessorsServiceImpl {
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ProfessorsServiceImpl(DataSource dsrc) {
		jdbcTemplate = new JdbcTemplate(dsrc);
	}

	public void register_professor(Professors prof) {
		String sql = "insert into professeur values(?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, new Object[] { prof.getProfessorLogin(), prof.getProfessorPassword(), prof.getProfessorName(),prof.getProfessorLastName(),
		    		prof.getProfessorAdresse(), prof.getProfessorEmail(), prof.getProfessorContact(), prof.getUserId() });
	}

	public Professors validateProfessor(Login login) {
		String sql = "select * from professeur where prof_login='" + login.getUsername() + "' and  	prof_password='" + login.getPassword()
		    + "'";
		List<Professors> professors = jdbcTemplate.query(sql, new ProfessorMapper());
		return professors.size() > 0 ? professors.get(0) : null;
	}
}

class ProfessorMapper implements RowMapper<Professors> {
	public Professors mapRow(ResultSet rs, int arg1) throws SQLException {
	    Professors prof = new Professors();
	    prof.setProfessorLogin(rs.getString("professor_login"));
	    prof.setProfessorPassword(rs.getString("professor_password"));
	    prof.setProfessorName(rs.getString("professor_name"));
	    prof.setProfessorName(rs.getString("professor_last_name"));
	    prof.setProfessorAdresse(rs.getString("professor_adresse"));
	    prof.setProfessorEmail(rs.getString("professor_email"));
	    prof.setProfessorContact(rs.getString("professor_contact"));
	    prof.setUserId(rs.getInt("user_id"));
	    return prof;
	}

}