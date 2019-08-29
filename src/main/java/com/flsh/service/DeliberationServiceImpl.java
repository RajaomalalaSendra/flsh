package com.flsh.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import com.flsh.interfaces.DeliberationService;
import com.flsh.model.EvaluationCourseStudent;
import com.flsh.model.EvaluationUEECStudent;
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
		String sql = "SELECT Unite_Enseignement.*, "
				+ "(SELECT ac_credit FROM Acquisition_Ue WHERE etd_id = "+ idStudent 
				+ " AND ue_id = Unite_Enseignement.ue_id LIMIT 1) as credit_ue, "
				+ "(SELECT ac_acquis FROM Acquisition_Ue WHERE etd_id = "+ idStudent 
				+ " AND ue_id = Unite_Enseignement.ue_id LIMIT 1) as valid_credit_ue "
				+ "FROM Unite_Enseignement " 
				+ "WHERE prc_id = " + idPrc;
		System.out.print("\n"+sql+"\n");
		List<EvaluationUEECStudent> delibs = jdbcTemplate.query(sql, new DeliberationMapper(dataSource, jdbcTemplate, univYearId, idStudent, idLevel, idPrc));
		return delibs;
	}
	
	@Override
	public JSONObject saveMoyenneUE(int idStudent, int idUE, int idPeriod, float moyenneUE, int typeSession) {
		// TODO Auto-generated method stub
		JSONObject save = new JSONObject();
		String deleteMoyenneUE = "DELETE FROM Moyenne_Ue WHERE etd_id = ? AND ue_id = ? "
								 + "AND exam_id =  (SELECT exam_id FROM Examen WHERE per_id = "
								 + idPeriod + " AND exam_sessiontype = " + typeSession + " LIMIT 1) "
								 + "AND per_id = ?"; 
		jdbcTemplate.execute (deleteMoyenneUE, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, idStudent);
				ps.setInt(2, idUE);
				ps.setInt(3, idPeriod);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});

		
		String saveMoyenneUE = "INSERT INTO Moyenne_Ue(etd_id, ue_id, per_id, exam_id, moyue_val)"
				+ " VALUES(?, ?, ?, (SELECT exam_id FROM Examen WHERE per_id = "
				+ idPeriod + " AND exam_sessiontype = " + typeSession + " LIMIT 1), ?)";
		
		boolean savingMoyenne = jdbcTemplate.execute (saveMoyenneUE, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, idStudent);
				ps.setInt(2, idUE);
				ps.setInt(3, idPeriod);
				ps.setFloat(4, moyenneUE);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		
		save.put("status", savingMoyenne ? 1 : 0);
		save.put("message", savingMoyenne ? "Notes Enregistrés avec succès" : "Echec de l'enregistrement des notes! Veuillez réessayer");
		return save;
	}

	@Override
	public JSONObject saveValidCreditUE(int valValid, int idStudent, int idUE) {
		// TODO Auto-generated method stub
		JSONObject save = new JSONObject();
		
		String sqlSavingValidCreditUE = "UPDATE Acquisition_Ue SET  ac_acquis = ? WHERE ue_id = ? AND  etd_id = ?";
		
		boolean savingValidCreditUE = jdbcTemplate.execute (sqlSavingValidCreditUE, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, valValid);
				ps.setInt(2, idUE);
				ps.setInt(3, idStudent);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});		
		
		save.put("status", savingValidCreditUE ? 1 : 0);
		save.put("message", savingValidCreditUE ? "Credit Enregistré avec succès" : "Echec de l'enregistrement de Credit! Veuillez réessayer");
		return save;
	}
	
	@Override
	public JSONObject saveCreditECAndUE(int idEC, int idUE, int creditEC, int creditUE, int idStudent) {
		// TODO Auto-generated method stub
		JSONObject save = new JSONObject();
		
		// Delete creditEC and creditUE
		String deleteCreditEC = "DELETE FROM Credit_Ec WHERE ec_id = ? AND  etd_id = ?";
		
		jdbcTemplate.execute (deleteCreditEC, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, idEC);
				ps.setInt(2, idStudent);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		
		String deleteCreditUE = "DELETE FROM Acquisition_Ue WHERE ue_id = ? AND  etd_id = ?";
		
		jdbcTemplate.execute (deleteCreditUE, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, idUE);
				ps.setInt(2, idStudent);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		
		// Save Credit UE and Credit EC
		String saveCreditEC = "INSERT INTO Credit_Ec(ec_id, etd_id, credit_obtenu)"
				+ " VALUES(?, ?, ?)";
		
		jdbcTemplate.execute (saveCreditEC, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, idEC);
				ps.setInt(2, idStudent);
				ps.setInt(3, creditEC);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		
		String saveCreditUE = "INSERT INTO Acquisition_Ue(ue_id, etd_id, ac_credit)"
				+ " VALUES(?, ?, ?)";
		
		boolean savingCredit = jdbcTemplate.execute (saveCreditUE, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, idUE);
				ps.setInt(2, idStudent);
				ps.setInt(3, creditUE);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});

		save.put("status", savingCredit ? 1 : 0);
		save.put("message", savingCredit ? "Credit Enregistré avec succès" : "Echec de l'enregistrement de Credit! Veuillez réessayer");
		return save;
	}

	@Override
	public JSONObject saveDeliberationDecision(int delibDecision, int idStudent, int idLevel, int idAU,
			int idParcours, String passage) {
		JSONObject save = new JSONObject();
        System.out.print("\n Id de l etudiqnt "+ idStudent);
		String sqlSavingDelibDecision = "UPDATE Niveau_Etudiant SET  net_deliberation = ?, net_passage = ? "
				+ "WHERE au_id = ? AND niv_id = ? AND prc_id = ? AND etd_id = ? ";
		
		boolean savingDelibDecision = jdbcTemplate.execute (sqlSavingDelibDecision, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, delibDecision);
				ps.setString(2, passage);
				ps.setInt(3, idAU);
				ps.setInt(4, idLevel);
				ps.setInt(5, idParcours);
				ps.setInt(6, idStudent);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		save.put("status", savingDelibDecision ? 1 : 0);
		save.put("message", savingDelibDecision ? "Delibération Enregistré avec succès" : "Echec de l'enregistrement de Credit! Veuillez réessayer");
		return save;
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
		// For UE
		delibs.setStudyunit_id(rs.getInt("ue_id"));
		delibs.setParcours_id(rs.getInt("prc_id"));
		delibs.setStudyunit_libelle(rs.getString("ue_libelle"));
		delibs.setStudyunit_type(rs.getString("ue_type"));
		delibs.setCredit_ue(rs.getInt("credit_ue"));
		delibs.setValid_credit_ue(rs.getInt("valid_credit_ue"));
		// For EC
		delibs.setCoursesEvaluations(this.getEvaluationsCourseByUE(rs.getInt("ue_id"), univYearId, idStudent, idLevel, idPrc));
		return delibs;
	}
	
	private List<EvaluationCourseStudent> getEvaluationsCourseByUE(int ueId, int univYearId, int idStudent, int idLevel, int idPrc ) {
		String sql = "SELECT Element_Constitutif.*, ( SELECT GROUP_CONCAT(concat(Evaluation_Etudiant.per_id, \"_\", Examen.exam_sessiontype, \"_\", avale_evaluation) SEPARATOR \";\") FROM `Evaluation_Etudiant` " 
				+ "JOIN Examen ON Examen.exam_id = Evaluation_Etudiant.exam_id WHERE etd_id = " + idStudent +" AND ec_id = Element_Constitutif.ec_id LIMIT 1) as evaluations, "
				+ "(SELECT credit_obtenu FROM Credit_Ec WHERE etd_id = "+ idStudent +" AND Credit_Ec.ec_id = Element_Constitutif.ec_id LIMIT 1) as ec_credit_obtenu "
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
	    course.setCourse_credit_obtenu(rs.getInt("ec_credit_obtenu"));
	    String evaluations = rs.getString("evaluations");
	    String[] tmpEvals = evaluations != null ? evaluations.split(";") : new String[0];
		HashMap<String, String> listEvaluations = new HashMap<String, String>();
		for(String eval : tmpEvals) {
			String[] tmp = eval.split("_");
			listEvaluations.put(tmp[0] +"_"+ tmp[1], tmp[2]);
		}
		course.setPeriodicalEvaluations(listEvaluations);
	    return course;
	}
  }