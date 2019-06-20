package com.flsh.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.flsh.controller.HomeController;
import com.flsh.interfaces.TeachingService;
import com.flsh.model.Courses;
import com.flsh.model.Professor;
import com.flsh.model.StudyUnits;

public class TeachingServiceImpl implements TeachingService {
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	public TeachingServiceImpl(DataSource dsrc) {
		jdbcTemplate = new JdbcTemplate(dsrc);
	}

	@Override
	public List<StudyUnits> getAllUnity() {
		String sql = "SELECT * FROM Unite_Enseignement JOIN Parcours ON Parcours.prc_id = Unite_Enseignement.prc_id";
		List<StudyUnits> units = jdbcTemplate.query(sql, new UnitsMapper());
		return units;
	}

	@Override
	public List<Courses> getAllComplementary() {
		String sql = "SELECT * FROM Element_Constitutif";
		List<Courses> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses;
	}

	@Override
	public StudyUnits detailsUE(int id) {
		String sql = "SELECT * FROM Unite_Enseignement  WHERE ue_id = " + id ;
		List<StudyUnits> units = jdbcTemplate.query(sql, new UnitsMapper());
		return units.size() > 0 ? units.get(0) : null;
	}

}

class UnitsMapper implements RowMapper<StudyUnits> {
	public StudyUnits mapRow(ResultSet rs, int arg1) throws SQLException {
	    StudyUnits units = new StudyUnits();
	    units.setStudyunits_id(rs.getInt("ue_id"));
	    units.setParcours_id(rs.getInt("prc_id"));
	    units.setStudyunits_libelle(rs.getString("ue_libelle"));
	    units.setStudyunits_type(rs.getString("ue_type"));
	    return units;
	}
}

class CourseMapper implements RowMapper<Courses> {
	public Courses mapRow(ResultSet rs, int arg1) throws SQLException {
	    Courses courses = new Courses(); 
	    courses.setCourse_id(rs.getInt("ec_id"));
	    courses.setStudyunit_id(rs.getInt("ue_id"));
	    courses.setProfessor_id(rs.getInt("prof_id"));
	    courses.setCourse_libelle(rs.getString("ec_libelle"));
	    courses.setCourse_credit(rs.getInt("ec_credit"));
	    courses.setCourse_notation(rs.getInt("ec_notation"));
	    courses.setCourse_coefficient(rs.getInt("ec_coefficient"));
	    courses.setCourse_volumehoraire(rs.getString("ec_volumehoraire"));
	    courses.setCourse_travailpresenciel(rs.getDouble("ec_travailpresenciel"));
	    courses.setCourse_travailpersonnel(rs.getDouble("ec_travailpersonnel"));
	    return courses;
	}
}

