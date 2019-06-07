package com.flsh.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;
import com.flsh.model.Professors;
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