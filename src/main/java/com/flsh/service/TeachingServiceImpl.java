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
import com.flsh.interfaces.TeachingService;
import com.flsh.model.Course;
import com.flsh.model.Parcours;
import com.flsh.model.Professor;
import com.flsh.model.StudyUnit;
import com.flsh.model.User;

public class TeachingServiceImpl implements TeachingService {
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	public TeachingServiceImpl(DataSource dsrc) {
		jdbcTemplate = new JdbcTemplate(dsrc);
	}

	@Override
	public HashSet<StudyUnit> getAllUnits() {
		HashSet<StudyUnit> listUnits = new HashSet<StudyUnit>();
		String sql = "SELECT * FROM Unite_Enseignement";
		List<StudyUnit> units = jdbcTemplate.query(sql, new UnitsMapper());
		for(StudyUnit unit : units) {
			Set<Course> listCourses = new HashSet<Course>(this.getCourseById( unit.getStudyunit_id()));
			unit.setCourses((HashSet<Course>) listCourses);
		    listUnits.add(unit);
		}
		return listUnits;
	}
	public List<Course> getCourseById(int idUnits) {
		String sql = "SELECT * FROM  Element_Constitutif WHERE ue_id = "+idUnits;
		List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses;
	}
	public List<Parcours> getAllParcours(){
		String sql = "SELECT * FROM Parcours";
		List<Parcours> parcours = jdbcTemplate.query(sql, new ParcourMapper());
		return parcours;
	}



	@Override
	public JSONObject saveStudyUnit(StudyUnit studyUnit) {
		JSONObject rtn = new JSONObject();
		
		String  sql = studyUnit.getStudyunit_id() == 0 ? "INSERT INTO  Unite_Enseignement(prc_id, ue_libelle, ue_type)  VALUES(?, ?, ?)" : 
		"UPDATE Unite_Enseignement SET prc_id = ?, ue_libelle = ?, ue_type = ? WHERE ue_id = ?";
		boolean save = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, studyUnit.getParcours_id());
				ps.setString(2, studyUnit.getStudyunit_libelle());
				ps.setString(3, studyUnit.getStudyunit_type());
				if (studyUnit.getStudyunit_id() != 0) ps.setInt(4, studyUnit.getStudyunit_id());
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		rtn.put("status", save ? 1 : 0);
  	    rtn.put("message", save ? "Enregistré avec succès" : "Echec de l'enregistrement! Veuillez réessayer");
		return rtn;
	}
	@Override
	public StudyUnit getUeDetails(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM Unite_Enseignement JOIN Parcours ON Parcours.prc_id = Unite_Enseignement.prc_id  WHERE ue_id=" + id ;
		List<StudyUnit> units = jdbcTemplate.query(sql, new UnitsMapper());
		return units.size() > 0 ? units.get(0) : null;
	}

	@Override
	public JSONObject deleteStudyUnit(int id) {
		// TODO Auto-generated method stub
		JSONObject rtn = new JSONObject();
		String sql_element = "DELETE  FROM  Element_Constitutif WHERE Element_Constitutif.ue_id = ?";
		String sql = "DELETE FROM Unite_Enseignement  WHERE Unite_Enseignement.ue_id = ?";
		jdbcTemplate.update(sql_element, id);
		int template = jdbcTemplate.update(sql, id);
	    rtn.put("status", template >= 0 ? 1 : 0);
	    rtn.put("message", template >= 0 ? "Supprimé!" : "Echec de la suppression! Veuillez réessayer.");
		return rtn;
	}


	@Override
	public JSONObject saveCourse(Course course) {
		JSONObject rtn = new JSONObject();
		String  sql;
		if (course.getCourse_id() == 0) {
			sql = "INSERT INTO  Element_Constitutif(ue_id, prof_id, ec_libelle, ec_credit, ec_notation, "
					+ "ec_coefficient, ec_volumehoraire, ec_travailpresenciel, ec_travailpersonnel)  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		} else {
			sql = "UPDATE Element_Constitutif SET ue_id = ?, prof_id = ?, ec_libelle = ?, ec_credit = ?, "
					+ "ec_notation = ?, ec_coefficient = ?, ec_volumehoraire = ?, ec_travailpresenciel = ?, "
					+ "ec_travailpersonnel = ? WHERE ec_id = ?";
		}
		boolean save = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, course.getStudyunit_id());
				ps.setInt(2, course.getProfessor_id());
				ps.setString(3, course.getCourse_libelle());
				ps.setInt(4, course.getCourse_credit());
				ps.setInt(5, course.getCourse_notation());
				ps.setDouble(6, course.getCourse_coefficient());
				ps.setString(7, course.getCourse_volumehoraire());
				ps.setDouble(8, course.getCourse_travailpresenciel());
				ps.setDouble(9, course.getCourse_travailpersonnel());
				if (course.getCourse_id() != 0) ps.setInt(10, course.getCourse_id());
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		rtn.put("status", save ? 1 : 0);
  	    rtn.put("message", save ? "Enregistré avec succès" : "Echec de l'enregistrement! Veuillez réessayer");
		return rtn;
	}
	@Override
	public Course getEcDetails(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM Element_Constitutif  WHERE ec_id=" + id ;
		List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses.size() > 0 ? courses.get(0) : null;
	}

	public List<Professor> getAllProfessor() {
		String sql_prof = "SELECT * FROM  Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id WHERE Utilisateur.uti_type = 2 ";		
		List<Professor> professors = jdbcTemplate.query(sql_prof, new ProfessorMapper());
		return professors;
	}

	@Override
	public JSONObject deleteCourse(int id) {
		// TODO Auto-generated method stub
		JSONObject rtn = new JSONObject();
		String sql = "DELETE  FROM  Element_Constitutif WHERE ec_id = ?";
		int i = jdbcTemplate.update(sql, id);
	    rtn.put("status", i >= 0 ? 1 : 0);
	    rtn.put("message", i >= 0 ? "Supprimé!" : "Echec de la suppression! Veuillez réessayer.");
		return rtn;
	}
}

class UnitsMapper implements RowMapper<StudyUnit> {
	public StudyUnit mapRow(ResultSet rs, int arg1) throws SQLException {
	    StudyUnit units = new StudyUnit();
	    units.setStudyunit_id(rs.getInt("ue_id"));
	    units.setParcours_id(rs.getInt("prc_id"));
	    units.setStudyunit_libelle(rs.getString("ue_libelle"));
	    units.setStudyunit_type(rs.getString("ue_type"));
	    return units;
	}
}

class CourseMapper implements RowMapper<Course> {
	public Course mapRow(ResultSet rs, int arg1) throws SQLException {
	    Course courses = new Course(); 
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

class ParcourMapper implements RowMapper<Parcours> {
	public Parcours mapRow(ResultSet rs, int arg1) throws SQLException {
		Parcours prc = new Parcours();
		prc.setLevelId(rs.getInt("niv_id"));
		prc.setParcoursId(rs.getInt("prc_id"));
		prc.setParcoursLibelle(rs.getString("prc_libelle"));
		return prc;
	}
}