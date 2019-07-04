package com.flsh.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.flsh.interfaces.DeliberationService;
import com.flsh.model.Authorities;
import com.flsh.model.Course;
import com.flsh.model.Deliberation;
import com.flsh.model.PeriodLibelle;
import com.flsh.model.Student;
import com.flsh.model.TotalCredit;
import com.flsh.model.UniversityYear;
import com.flsh.model.User;

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
	public List<Deliberation> getDelibByUYAndStdAndPerId(int univYearId, int idStudent, int idPeriod) {
		// TODO Auto-generated method stub
		String sql = "SELECT Evaluation_Etudiant.*, Element_Constitutif.ec_libelle, Element_Constitutif.ec_notation , Unite_Enseignement.ue_libelle , " 
				+ "Periode.per_libellecourt, Periode.per_aratrappage "
				+ "FROM `Evaluation_Etudiant` " 
				+ "JOIN Element_Constitutif ON Element_Constitutif.ec_id = Evaluation_Etudiant.ec_id "
				+ "JOIN Unite_Enseignement ON Unite_Enseignement.ue_id = Element_Constitutif.ue_id "
				+ "JOIN Periode ON Periode.per_id = Evaluation_Etudiant.per_id "
				+ "JOIN Annee_Universitaire ON Annee_Universitaire.au_id = Periode.au_id "
				+ "WHERE etd_id = " + idStudent  
				+ " AND Annee_Universitaire.au_id = " + univYearId
				+ " AND Periode.per_id = " + idPeriod;
		
		List<Deliberation> delibs = jdbcTemplate.query(sql, new DeliberationMapper());
		return delibs;
	}

	@Override
	public List<PeriodLibelle> getDelibByIdLevel(int idLevel) {
		// TODO Auto-generated method stub
		String sql = "SELECT Periode.per_libellecourt, Periode.per_id FROM Periode WHERE Periode.niv_id = "+ idLevel;
		List<PeriodLibelle> list_delib =  jdbcTemplate.query(sql, new DeliberationPeriodeMapper());
		return list_delib;
	}

	@Override
	public List<Course> getAllCourses() {
		// TODO Auto-generated method stub
		String sql = "SELECT ec_libelle FROM Element_Constitutif";
		List<Course> list_delib =  jdbcTemplate.query(sql, new DeliberationCourseMapper());
		return list_delib;
	}

	@Override
	public TotalCredit getTotalCredit(int univYearId, int idStudent) {
		// TODO Auto-generated method stub
		String sql = "SELECT SUM(evale_creditobtenu) as total_credit FROM `Evaluation_Etudiant` " + 
				"JOIN Periode ON Periode.per_id = Evaluation_Etudiant.per_id " + 
				"JOIN Annee_Universitaire ON Annee_Universitaire.au_id = Periode.au_id " + 
				"WHERE etd_id = " + idStudent 
				+ " AND Annee_Universitaire.au_id = " + univYearId;
		List<TotalCredit> totalCredit =  jdbcTemplate.query(sql, new TotalCreditMapper()); 
		return totalCredit.size() > 0 ? totalCredit.get(0) : null;
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

class DeliberationMapper implements RowMapper<Deliberation>{
	public Deliberation mapRow(ResultSet rs, int arg1) throws SQLException {
		Deliberation delibs = new Deliberation();
		// other than student
		delibs.setCredit(rs.getString("evale_creditobtenu"));
		delibs.setPeriod_id(rs.getInt("per_id"));
		delibs.setEc_notation(rs.getInt("ec_notation"));
		delibs.setEc_libelle(rs.getString("ec_libelle"));
		delibs.setEvaluation(rs.getString("avale_evaluation"));
		delibs.setPeriod_libelle(rs.getString("per_libellecourt"));
		delibs.setPeriod_rattrapage(rs.getInt("per_aratrappage"));
		delibs.setUe_libelle(rs.getString("ue_libelle"));
		return delibs;
	}
	
}

class DeliberationPeriodeMapper implements RowMapper<PeriodLibelle>{
	public PeriodLibelle mapRow(ResultSet rs, int arg1) throws SQLException {
		PeriodLibelle delibs = new PeriodLibelle();
		delibs.setPeriod_libelle(rs.getString("per_libellecourt"));
		delibs.setPeriod_id(rs.getInt("per_id"));
		return delibs;
	}
	
}

class DeliberationCourseMapper implements RowMapper<Course>{
	public Course mapRow(ResultSet rs, int arg1) throws SQLException {
		Course course = new Course();
		course.setCourse_libelle(rs.getString("ec_libelle"));
		return course;
	}
	
}

class TotalCreditMapper implements RowMapper<TotalCredit>{
	public TotalCredit mapRow(ResultSet rs, int arg1) throws SQLException {
		TotalCredit totalCredit = new TotalCredit();
		totalCredit.setTotalCredit(rs.getInt("total_credit"));
		return totalCredit;
	}
	
}