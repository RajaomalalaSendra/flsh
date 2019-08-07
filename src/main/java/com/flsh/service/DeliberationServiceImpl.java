package com.flsh.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.flsh.interfaces.DeliberationService;
import com.flsh.model.Course;
import com.flsh.model.EvaluationCourseStudent;
import com.flsh.model.EvaluationUEECStudent;
import com.flsh.model.Parcours;
import com.flsh.model.UniversityYear;


public class DeliberationServiceImpl implements DeliberationService{
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(EducationServiceImpl.class);
	
	@Autowired
	public DeliberationServiceImpl(DataSource dsrc) {
		this.dataSource = dsrc;
		jdbcTemplate = new JdbcTemplate(dsrc);
	}

	@Override
	public UniversityYear getDetailUnivYear(int univYearId) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM Annee_Universitaire  WHERE au_id=" + univYearId ;
		List<UniversityYear> univYears = jdbcTemplate.query(sql, new UnivYearMapper());
		return univYears.size() > 0 ? univYears.get(0) : null;
	}

	@Override
	public List<EvaluationUEECStudent> getInfosEvaluationsByStudentLevelUnivYearAndParcours(int univYearId, int idStudent, int idLevel, int idPrc) {
		// TODO Auto-generated method stub
		String sql = "SELECT Unite_Enseignement.* "
				+ "FROM Unite_Enseignement " 
				+ "WHERE prc_id = " + idPrc;
		System.out.print("\n"+sql+"\n");
		List<EvaluationUEECStudent> delibs = jdbcTemplate.query(sql, new DeliberationMapper(dataSource, jdbcTemplate, univYearId, idStudent, idLevel, idPrc));
		return delibs;
	}

}

class UnivYearMapper implements RowMapper<UniversityYear> {
	public UniversityYear mapRow(ResultSet rs, int arg1) throws SQLException {
		UniversityYear univYear = new UniversityYear();
	    univYear.setUniversity_year_id(rs.getInt("au_id"));
	    univYear.setUniversity_year_libelle(rs.getString("au_libelle"));
	    univYear.setUniversity_year_beginning(rs.getString("au_debut"));
	    univYear.setUniversity_year_ending(rs.getString("au_fin"));
	    return univYear;
	}
}

class DeliberationMapper implements RowMapper<EvaluationUEECStudent>{
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	int univYearId;
	int idStudent;
	int idLevel;
	int idPrc;
	
	public DeliberationMapper(DataSource dataSource, JdbcTemplate jdbcTemplate, int univYearId, int idStudent, int idLevel, int idPrc) {
		super();
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
		this.univYearId = univYearId;
		this.idStudent = idStudent;
		this.idLevel = idLevel;
		this.idPrc = idPrc;
	}
	
	public EvaluationUEECStudent mapRow(ResultSet rs, int arg1) throws SQLException {
		EvaluationUEECStudent delibs = new EvaluationUEECStudent();
		// other than student
		delibs.setStudyunit_id(rs.getInt("ue_id"));
		delibs.setParcours_id(rs.getInt("prc_id"));
		delibs.setStudyunit_libelle(rs.getString("ue_libelle"));
		delibs.setStudyunit_type(rs.getString("ue_type"));
		
		delibs.setCoursesEvaluations(this.getEvaluationsCourseByUE(rs.getInt("ue_id"), univYearId, idStudent, idLevel, idPrc));
		return delibs;
	}
	
	private List<EvaluationCourseStudent> getEvaluationsCourseByUE(int ueId, int univYearId, int idStudent, int idLevel, int idPrc ) {
		String sql = "SELECT Element_Constitutif.* "
				+ "FROM Element_Constitutif "
				+ "WHERE ue_id = " + ueId;
		List<EvaluationCourseStudent> evaluationsCourses = jdbcTemplate.query(sql, new EvaluationCourseMapper());
		return evaluationsCourses;
	}
	
}


class EvaluationCourseMapper implements RowMapper<EvaluationCourseStudent>{
	public EvaluationCourseStudent mapRow(ResultSet rs, int arg1) throws SQLException {
		EvaluationCourseStudent course = new EvaluationCourseStudent();
		course.setCourse_libelle(rs.getString("ec_libelle"));
		course.setStudyunit_id(rs.getInt("ue_id"));
		course.setProfessor_id(rs.getInt("prof_id"));
		course.setCourse_libelle(rs.getString("ec_libelle"));
		course.setCourse_credit(rs.getInt("ec_credit"));
	    course.setCourse_notation(rs.getInt("ec_notation"));
	    course.setCourse_coefficient(rs.getInt("ec_coefficient"));
	    course.setCourse_id(rs.getInt("ec_id"));
		
	    return course;
	}
	
}