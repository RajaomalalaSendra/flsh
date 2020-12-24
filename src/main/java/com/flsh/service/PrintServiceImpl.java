package com.flsh.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.flsh.interfaces.PrintService;
import com.flsh.model.Course;
import com.flsh.model.Professor;
import com.flsh.model.Student;
import com.flsh.model.StudyUnit;

public class PrintServiceImpl implements PrintService {

	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public PrintServiceImpl(DataSource dsrc) {
		this.dataSource = dsrc;
		jdbcTemplate = new JdbcTemplate(dsrc);
	}
	
	@Override
	public List<Student> getStudentsandPartialResultsByLevelandUYandPer(int idParcours, int idUY, int idPer) {
		String queryStudent = "SELECT Etudiant.*, net_ecchoisis, 0 as cumulant, '' as ec_cumules, "
						+ "(SELECT GROUP_CONCAT(concat(ec_id,'_', evale_ok) separator ';') FROM Evaluation_Etudiant WHERE etd_id = Etudiant.etd_id AND per_id = "+idPer+" AND exam_id = (SELECT exam_id FROM Examen WHERE per_id = "+idPer+" AND exam_sessiontype = 1 limit 1)) as ec_results "
						+ " FROM Etudiant "
						+ "JOIN Niveau_Etudiant ON Niveau_Etudiant.etd_id = Etudiant.etd_id "
						+ "WHERE Niveau_Etudiant.prc_id = "+idParcours+" AND Niveau_Etudiant.au_id = "+ idUY+" "
						+ "UNION "
						+ "SELECT Etudiant.*, '' as net_ecchoisis, 1 as cumulant, "
						+ "(SELECT GROUP_CONCAT(ec_id separator '_') FROM Etudiant_Cumule WHERE Etudiant_Cumule.etd_id = Etudiant.etd_id AND Etudiant_Cumule.au_id = "+idUY+") as ec_cumules, "
						+ "(SELECT GROUP_CONCAT(concat(ec_id,'_', evale_ok) separator ';') FROM Evaluation_Etudiant WHERE etd_id = Etudiant.etd_id AND per_id = "+idPer+" AND exam_id = (SELECT exam_id FROM Examen WHERE per_id = "+idPer+" AND exam_sessiontype = 1 limit 1)) as ec_results "
						+ "FROM Etudiant "
						+ "JOIN Etudiant_Cumule ON Etudiant_Cumule.etd_id = Etudiant.etd_id "
						+ "JOIN Element_Constitutif ON Element_Constitutif.ec_id = Etudiant_Cumule.ec_id "
						+ "JOIN Unite_Enseignement ON Unite_Enseignement.ue_id = Element_Constitutif.ue_id "
						+ "JOIN Parcours ON Parcours.prc_id = Unite_Enseignement.prc_id "
						+ "WHERE Parcours.prc_id = "+idParcours+" AND Etudiant_Cumule.au_id = "+idUY;
		System.out.print("\n "+queryStudent);
		List<Student> students = jdbcTemplate.query(queryStudent, new StudentMapper());
		return students;
	}

	@Override
	public List<StudyUnit> getLevelStudyUnitandCourses(int idParcours) {
		List<StudyUnit> listUnits = new ArrayList<StudyUnit>();
		String queryST = "SELECT Unite_Enseignement.* FROM Unite_Enseignement "
				+ "JOIN Parcours ON Parcours.prc_id = Unite_Enseignement.prc_id "
				+ "WHERE niv_id = "+idParcours;
		List<StudyUnit> units = jdbcTemplate.query(queryST, new UnitsMapper());
		for(StudyUnit unit : units) {
			Set<Course> listCourses = new HashSet<Course>(this.getCourseById( unit.getStudyunit_id()));
			unit.setCourses((HashSet<Course>) listCourses);
		    listUnits.add(unit);
		}
		System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.print(queryST+" "+listUnits.size());
		System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return listUnits;
	}
	
	private List<Course> getCourseById(int idUnits) {
		String sql = "SELECT Element_Constitutif.*, civ_libellecourt, uti_nom, uti_prenom  FROM  Element_Constitutif "
				+ "JOIN Professeur ON Professeur.prof_id = Element_Constitutif.prof_id "
				+ "JOIN Utilisateur ON Professeur.uti_id = Utilisateur.uti_id "
				+ "JOIN Civilite ON Civilite.civ_id = Utilisateur.civ_id WHERE ue_id = "+idUnits;
		List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses;
	}

}
