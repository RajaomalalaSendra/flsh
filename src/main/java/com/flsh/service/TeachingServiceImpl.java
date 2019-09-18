package com.flsh.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.flsh.interfaces.TeachingService;
import com.flsh.model.Course;
import com.flsh.model.Parcours;
import com.flsh.model.Professor;
import com.flsh.model.ProfessorCourse;
import com.flsh.model.ProfessorStudyUnit;
import com.flsh.model.StudyUnit;
import com.mysql.jdbc.Statement;

public class TeachingServiceImpl implements TeachingService {
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
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
			List<Professor> profResponsables = this.getProfResponsables(unit.getStudyunit_id());
			unit.setResponsables(profResponsables);
		    listUnits.add(unit);
		}
		return listUnits;
	}
	
	

	@Override
	public HashSet<StudyUnit> getUnitsByParcoursWithPeriods(int idParcours, int idUY, int idLevel) {
		HashSet<StudyUnit> listUnits = new HashSet<StudyUnit>();
		String sql = "SELECT * FROM Unite_Enseignement where prc_id = "+idParcours;
		List<StudyUnit> units = jdbcTemplate.query(sql, new UnitsMapper());
		for(StudyUnit unit : units) {
			Set<Course> listCourses = new HashSet<Course>(this.getCourseByIdWithPeriods( unit.getStudyunit_id(), idUY, idLevel));
			unit.setCourses((HashSet<Course>) listCourses);
			List<Professor> profResponsables = this.getProfResponsables(unit.getStudyunit_id());
			unit.setResponsables(profResponsables);
		    listUnits.add(unit);
		}
		return listUnits;
	}

	private List<Course> getCourseByIdWithPeriods(int idUnit, int idUY, int idLevel) {
		String sql = "SELECT Element_Constitutif.*, civ_libellecourt, uti_nom, uti_prenom, (select group_concat(per_id) from Periode_EC where Periode_EC.ec_id = Element_Constitutif.ec_id and per_id in (select per_id from Periode where niv_id = "+idLevel+" and au_id = "+idUY+")) as cours_period  FROM  Element_Constitutif " + 
						" JOIN Professeur ON Professeur.prof_id = Element_Constitutif.prof_id " + 
						" JOIN Utilisateur ON Professeur.uti_id = Utilisateur.uti_id " + 
						" JOIN Civilite ON Civilite.civ_id = Utilisateur.civ_id WHERE ue_id = "+idUnit;
		List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses;
	}

	@Override
	public HashSet<StudyUnit> getUnitsByParcours(int idParcours) {
		HashSet<StudyUnit> listUnits = new HashSet<StudyUnit>();
		String sql = "SELECT * FROM Unite_Enseignement where prc_id = "+idParcours;
		List<StudyUnit> units = jdbcTemplate.query(sql, new UnitsMapper());
		for(StudyUnit unit : units) {
			Set<Course> listCourses = new HashSet<Course>(this.getCourseById( unit.getStudyunit_id()));
			unit.setCourses((HashSet<Course>) listCourses);
			List<Professor> profResponsables = this.getProfResponsables(unit.getStudyunit_id());
			unit.setResponsables(profResponsables);
		    listUnits.add(unit);
		}
		return listUnits;
	}
	
	
	public List<Course> getCourseById(int idUnits) {
		String sql = "SELECT Element_Constitutif.*, civ_libellecourt, uti_nom, uti_prenom  FROM  Element_Constitutif "
				+ "JOIN Professeur ON Professeur.prof_id = Element_Constitutif.prof_id "
				+ "JOIN Utilisateur ON Professeur.uti_id = Utilisateur.uti_id "
				+ "JOIN Civilite ON Civilite.civ_id = Utilisateur.civ_id WHERE ue_id = "+idUnits;
		List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses;
	}
	
	public List<Parcours> getAllParcours(){
		String sql = "SELECT * FROM Parcours";
		List<Parcours> parcours = jdbcTemplate.query(sql, new ParcourMapper());
		return parcours;
	}
	

	@Override
	public JSONObject saveStudyUnit(StudyUnit studyUnit, String profResponsable) {
		JSONObject rtn = new JSONObject();
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		
		String  sql = studyUnit.getStudyunit_id() == 0 ? "INSERT INTO  Unite_Enseignement(prc_id, ue_libelle, ue_type)  VALUES(?, ?, ?)" : 
		"UPDATE Unite_Enseignement SET prc_id = ?, ue_libelle = ?, ue_type = ? WHERE ue_id = ?";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
		    
			@Override
		    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		        statement.setInt(1, studyUnit.getParcours_id());
		        statement.setString(2, studyUnit.getStudyunit_libelle());
		        statement.setString(3, studyUnit.getStudyunit_type());
				if (studyUnit.getStudyunit_id() != 0) statement.setInt(4, studyUnit.getStudyunit_id());
		        return statement;
		    }
		}, holder);
		
		if (studyUnit.getStudyunit_id() == 0) {
			studyUnit.setStudyunit_id(holder.getKey().intValue());
		}
		
		String sql_profresp = "DELETE FROM Prof_Ue WHERE ue_id = " + studyUnit.getStudyunit_id();
		jdbcTemplate.update(sql_profresp);
		
		String[] listIdProfResponsable = profResponsable.split(";");
		for(int i = 0; i < listIdProfResponsable.length; i++) {
			String idProf = listIdProfResponsable[i];
			String insert = "INSERT INTO Prof_Ue (ue_id, prof_id) VALUES (?,?)";
			jdbcTemplate.execute(insert, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, studyUnit.getStudyunit_id());
				ps.setInt(2, Integer.parseInt(idProf));
				return ps.executeUpdate() > 0 ? true : false;
			}});
		}
		
		boolean save = true;
		rtn.put("status", save ? 1 : 0);
  	    rtn.put("message", save ? "Enregistré avec succès" : "Echec de l'enregistrement! Veuillez réessayer");
		return rtn;
	}
	@Override
	public StudyUnit getUeDetails(int id) {
		String sql = "SELECT * FROM Unite_Enseignement JOIN Parcours ON Parcours.prc_id = Unite_Enseignement.prc_id  WHERE ue_id=" + id ;
		List<StudyUnit> units = jdbcTemplate.query(sql, new UnitsMapper());
		return units.size() > 0 ? units.get(0) : null;
	}

	@Override
	public JSONObject deleteStudyUnit(int id) {
		JSONObject rtn = new JSONObject();
		String sql_element = "DELETE  FROM  Element_Constitutif WHERE Element_Constitutif.ue_id = ?";
		String sql_prof = "DELETE FROM Prof_Ue WHERE Prof_Ue.ue_id = ?";
		String sql = "DELETE FROM Unite_Enseignement  WHERE  Unite_Enseignement.ue_id = ?";
		jdbcTemplate.update(sql_element, id);
		jdbcTemplate.update(sql_prof, id);
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
		JSONObject rtn = new JSONObject();
		String sql = "DELETE  FROM  Element_Constitutif WHERE ec_id = ?";
		int i = jdbcTemplate.update(sql, id);
	    rtn.put("status", i >= 0 ? 1 : 0);
	    rtn.put("message", i >= 0 ? "Supprimé!" : "Echec de la suppression! Veuillez réessayer.");
		return rtn;
	}

	@Override
	public JSONObject saveCoursePeriod(int idEC, int idPer, String add) {
		System.out.print(add);
		JSONObject rtn = new JSONObject();
		if(add.equals("true")) {
			String  sql = "INSERT INTO  Periode_EC(per_id, ec_id)  VALUES(?, ?)";
			boolean save = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {

				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setInt(1, idPer);
					ps.setInt(2, idEC);
					return ps.executeUpdate() > 0 ? true : false;
				}
			});
			rtn.put("status", save ? 1 : 0);
	  	    rtn.put("message", save ? "Enregistré avec succès" : "Echec de l'enregistrement! Veuillez réessayer");
		} else {
			String sql = "DELETE  FROM  Periode_EC WHERE ec_id = ? and per_id = ?";
			boolean res = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setInt(1, idEC);
					ps.setInt(2, idPer);
					return ps.executeUpdate() >= 0 ? true : false;
				}
			});
		    rtn.put("status", res ? 1 : 0);
		    rtn.put("message", res ? "Supprimé!" : "Echec de la suppression! Veuillez réessayer.");
		}
		return rtn;
	}

	@Override
	public List<ProfessorStudyUnit> getProfessorById(int ue) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM Prof_Ue WHERE ue_id = " + ue;
		List<ProfessorStudyUnit> profsUe = jdbcTemplate.query(sql, new ProfessorStudyUnitMapper());
		return profsUe;
	}
	
	public List<Professor> getProfResponsables(int idUE) {
		String sql = "SELECT Professeur.*, Utilisateur.* "
				+ "FROM Professeur "
				+ "JOIN Prof_Ue ON Prof_Ue.prof_id = Professeur.prof_id "
				+ "JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id  "
				+ "WHERE ue_id = " + idUE;
		List<Professor> profsUe = jdbcTemplate.query(sql, new ProfessorMapper());
		return profsUe;
	}

	@Override
	public List<StudyUnit> getALLUnits() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM Unite_Enseignement";
		List<StudyUnit> Study = jdbcTemplate.query(sql, new UnitsMapper());
		return Study;
	}
	
	@Override
	public List<Course> getAllCourses(){
		String sql = "SELECT * FROM Element_Constitutif";
		List<Course> Courses = jdbcTemplate.query(sql,  new CourseMapper());
		return Courses;
	}

	
	@Override
	public List<ProfessorStudyUnit> getStudyUntsByProfId(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT *, Unite_Enseignement.ue_libelle, Parcours.prc_libelle, Niveau.niv_libelle, 1 as is_responsable, "
				+ "(SELECT GROUP_CONCAT(ec_libelle separator '<br/>') FROM Element_Constitutif WHERE Element_Constitutif.ue_id = Unite_Enseignement.ue_id AND Element_Constitutif.prof_id = "+id+") as ecs "
				+ "FROM Prof_Ue "
				+ "JOIN Unite_Enseignement ON Unite_Enseignement.ue_id = Prof_Ue.ue_id "
				+ "JOIN Parcours ON Parcours.prc_id = Unite_Enseignement.prc_id "
				+ "JOIN Niveau ON Niveau.niv_id = Parcours.niv_id "
				+ "WHERE prof_id = " + id+" "
				+ "UNION "
				+ "SELECT *, Unite_Enseignement.ue_libelle, Parcours.prc_libelle, Niveau.niv_libelle, 0 as is_responsable, "
				+ "(SELECT GROUP_CONCAT(ec_libelle separator '<br/>') FROM Element_Constitutif WHERE Element_Constitutif.ue_id = Unite_Enseignement.ue_id AND Element_Constitutif.prof_id = "+id+") as ecs "
				+ "FROM Prof_Ue "
				+ "JOIN Unite_Enseignement ON Unite_Enseignement.ue_id = Prof_Ue.ue_id "
				+ "JOIN Parcours ON Parcours.prc_id = Unite_Enseignement.prc_id "
				+ "JOIN Niveau ON Niveau.niv_id = Parcours.niv_id "
				+ "WHERE prof_id != "+id+" AND Prof_Ue.ue_id in (select ue_id from Element_Constitutif WHERE prof_id = "+id+")";
		List<ProfessorStudyUnit> profsUe = jdbcTemplate.query(sql, new ProfessorStudyUnitMapper());
		return profsUe;
	}

	@Override
	public List<Course> getEcDetailsByProfId(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT ec_libelle, ue_libelle, niv_libelle, prc_libelle FROM Element_Constitutif "
				+ "JOIN Unite_Enseignement ON Unite_Enseignement.ue_id = Element_Constitutif.ue_id "
				+ "JOIN Parcours ON Parcours.prc_id = Unite_Enseignement.prc_id  "
				+ "JOIN Niveau ON Niveau.niv_id = Parcours.niv_id "
				+ "WHERE prof_id =" + id;
		List<Course> Courses = jdbcTemplate.query(sql,  new CourseMapper());
		return Courses;
	}
	
	@Override
	public List<ProfessorCourse> getProfessorCourses(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT ec_libelle, ue_libelle, niv_libelle, prc_libelle FROM Element_Constitutif "
				+ "JOIN Unite_Enseignement ON Unite_Enseignement.ue_id = Element_Constitutif.ue_id "
				+ "JOIN Parcours ON Parcours.prc_id = Unite_Enseignement.prc_id  "
				+ "JOIN Niveau ON Niveau.niv_id = Parcours.niv_id "
				+ "WHERE prof_id =" + id;
		List<ProfessorCourse> Courses = jdbcTemplate.query(sql,  new ProfessorCourseMapper());
		return Courses;
	}

	@Override
	public Professor getProfessorByUserId(int id) {
		String sql_prof = "SELECT * FROM  Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id WHERE Utilisateur.uti_type = 2 and Professeur.uti_id = "+id;		
		List<Professor> professors = jdbcTemplate.query(sql_prof, new ProfessorMapper());
		return professors.size() > 0 ? professors.get(0) : null;
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
	    try {
	    	System.out.print(rs.getString("cours_period"));
	    	courses.setIdPeriods(rs.getString("cours_period"));
	    } catch(Exception e) {
	    	System.out.print("No period data");
	    }
	    try {
	    	courses.setProfessor(rs.getString("civ_libellecourt") + " " + rs.getString("uti_nom") + " " + rs.getString("uti_prenom"));
	    } catch(Exception e) {
	    	System.out.print("No prof data");
	    }
	    String infosUeLevl = "";
	    try {
	    	infosUeLevl = rs.getString("ue_libelle");
	    	infosUeLevl += " - " + rs.getString("niv_libelle");
	    } catch(Exception e) {
	    	System.out.print("No niveau data");
	    }
	    courses.setUeNiveau(infosUeLevl);
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
class ProfessorStudyUnitMapper implements RowMapper<ProfessorStudyUnit> {
	public ProfessorStudyUnit mapRow(ResultSet rs, int arg1) throws SQLException {
		ProfessorStudyUnit profStdUnt = new ProfessorStudyUnit();
		profStdUnt.setStudy_unit_id(rs.getInt("ue_id"));
		profStdUnt.setProfessor_id(rs.getInt("prof_id"));
		try {
			profStdUnt.setStudy_unit_libelle(rs.getString("ue_libelle"));
		} catch(Exception e) {
			System.out.print("\nNo ue Libelle");
		}
		try {
			profStdUnt.setLevel_libelle(rs.getString("niv_libelle"));
		} catch(Exception e) {
			System.out.print("\nNo Level Libelle");
		}
		try {
			profStdUnt.setParcours_libelle(rs.getString("prc_libelle"));
		} catch(Exception e) {
			System.out.print("\nNo Parcours Libelle");
		}
		try {
			profStdUnt.setIsResponsable(rs.getInt("is_responsable"));
			System.out.print(rs.getInt("is_responsable"));
			
		} catch(Exception e) {
			System.out.print("\nNo responsibility data");
		}
		try {
			profStdUnt.setEcs_libelle(rs.getString("ecs"));
		} catch(Exception e) {
			System.out.print("\nNo Ecs Libelle");
		}
		return profStdUnt;
	}
}

class ProfessorCourseMapper implements RowMapper<ProfessorCourse> {
	public ProfessorCourse mapRow(ResultSet rs, int arg1) throws SQLException {
		ProfessorCourse prof_course = new ProfessorCourse();
		prof_course.setParcours_libelle(rs.getString("prc_libelle"));
		prof_course.setLevel_libelle(rs.getString("niv_libelle"));
		prof_course.setStudy_unit_libelle(rs.getString("ue_libelle"));
		prof_course.setCourse_libelle(rs.getString("ec_libelle"));
	    return prof_course;
	}
}