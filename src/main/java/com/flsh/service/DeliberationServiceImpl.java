package com.flsh.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import com.flsh.interfaces.DeliberationService;
import com.flsh.model.EvaluationCourseStudent;
import com.flsh.model.EvaluationUEECStudent;
import com.flsh.model.UniversityYear;
import com.flsh.model.User;


public class DeliberationServiceImpl implements DeliberationService{
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
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
		String sql = "SELECT Unite_Enseignement.*, 0 as cumule, "
				+ "(SELECT GROUP_CONCAT( concat(per_id, '_', (select exam_sessiontype from Examen where exam_id = Moyenne_Ue.exam_id limit 1), '_',  moyue_credit) separator ';') FROM Moyenne_Ue WHERE etd_id = "+ idStudent 
				+ " AND ue_id = Unite_Enseignement.ue_id AND exam_id in (select exam_id from Examen where exam_sessiontype = 1)) as credits_ue, "
				+ "(SELECT ac_acquis FROM Acquisition_Ue WHERE etd_id = "+ idStudent 
				+ " AND ue_id = Unite_Enseignement.ue_id LIMIT 1) as valid_credit_ue "
				+ "FROM Unite_Enseignement " 
				+ "WHERE prc_id = " + idPrc+ " "
				+ "UNION "
				+ "SELECT DISTINCT Unite_Enseignement.*, 1 as cumule, "
				+ "(SELECT GROUP_CONCAT( concat(per_id, '_', (select exam_sessiontype from Examen where exam_id = Moyenne_Ue.exam_id limit 1), '_', moyue_credit) separator ';') FROM Moyenne_Ue WHERE etd_id = "+ idStudent 
				+ " AND ue_id = Unite_Enseignement.ue_id AND exam_id in (select exam_id from Examen where exam_sessiontype = 1)) as credits_ue, "
				+ "(SELECT ac_acquis FROM Acquisition_Ue WHERE etd_id = "+ idStudent 
				+ " AND ue_id = Unite_Enseignement.ue_id LIMIT 1) as valid_credit_ue "
				+ "FROM Unite_Enseignement "
				+ "JOIN Element_Constitutif ON Element_Constitutif.ue_id = Unite_Enseignement.ue_id "
				+ "JOIN Etudiant_Cumule ON Element_Constitutif.ec_id = Etudiant_Cumule.ec_id "
				+ "WHERE Etudiant_Cumule.etd_id = " + idStudent + " AND Etudiant_Cumule.au_id = "+ univYearId;
		System.out.print("\n"+sql+"\n");
		List<EvaluationUEECStudent> delibs = jdbcTemplate.query(sql, new DeliberationMapper(dataSource, jdbcTemplate, univYearId, idStudent, idLevel, idPrc));
		return delibs;
	}
	
	@Override
	public JSONObject saveMoyenneUE(int idStudent, int idUE, int idPeriod, float moyenneUE, int typeSession) {
		// TODO Auto-generated method stub
		JSONObject save = new JSONObject();
		String selectMoyenneUE = "SELECT count(*) FROM Moyenne_Ue WHERE etd_id = "+idStudent+" AND ue_id = "+idUE
								 + " AND exam_id = (SELECT exam_id FROM Examen WHERE per_id = "
								 + idPeriod + " AND exam_sessiontype = " + typeSession + " LIMIT 1) "
								 + "AND per_id = "+idPeriod; 
		boolean moyExist = false;
		try{
			moyExist = jdbcTemplate.queryForObject(selectMoyenneUE, Integer.class) > 0;
		} catch (Exception e) {
			System.out.print("\nNo current Deliberation");
			e.printStackTrace();
		}

		
		String saveMoyenneUE = moyExist ?
				"UPDATE Moyenne_Ue SET moyue_val = ? WHERE etd_id = ? AND ue_id = ? AND per_id = ? AND "
				+ "exam_id = (SELECT exam_id FROM Examen WHERE per_id = " 
				+ idPeriod + " AND exam_sessiontype = " + typeSession + " LIMIT 1)"
				:
				"INSERT INTO Moyenne_Ue(moyue_val, etd_id, ue_id, per_id, exam_id)"
				+ " VALUES(?, ?, ?, ?, (SELECT exam_id FROM Examen WHERE per_id = "
				+ idPeriod + " AND exam_sessiontype = " + typeSession + " LIMIT 1))";
		
		boolean savingMoyenne = jdbcTemplate.execute (saveMoyenneUE, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setFloat(1, moyenneUE);
				ps.setInt(2, idStudent);
				ps.setInt(3, idUE);
				ps.setInt(4, idPeriod);
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
	public JSONObject saveCreditECAndUE(int idEC, int idUE, int idPer, int idExam, int creditEC, int creditUE, int idStudent) {
		JSONObject save = new JSONObject();
		
		// Save Credit UE and Credit EC
		String saveCreditEC = "UPDATE Evaluation_Etudiant SET evale_creditobtenu = ? WHERE ec_id = ? AND etd_id = ? AND per_id = ? AND exam_id = ?";
		
		jdbcTemplate.execute (saveCreditEC, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, creditEC);
				ps.setInt(2, idEC);
				ps.setInt(3, idStudent);
				ps.setInt(4, idPer);
				ps.setInt(5, idExam);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		
		String saveCreditUE = "UPDATE Moyenne_Ue SET moyue_credit = ? WHERE ue_id = ? AND etd_id = ? AND per_id = ? AND exam_id = ?";
		
		boolean savingCredit = jdbcTemplate.execute (saveCreditUE, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, creditUE);
				ps.setInt(2, idUE);
				ps.setInt(3, idStudent);
				ps.setInt(4, idPer);
				ps.setInt(5,  idExam);
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

	@Override
	public String getDelibDecisionCurrentUser(int univYearId, int idLevel, int idStudent) {
		String delibCurrentUser = "None";
		
		String sql = "SELECT net_passage FROM Niveau_Etudiant WHERE "
				+ "au_id = " + univYearId + " AND niv_id = " + idLevel + " AND etd_id = " + idStudent;
		System.out.print(sql);
		try{
			delibCurrentUser = jdbcTemplate.queryForObject(sql, String.class);
		} catch (Exception e) {
			System.out.print("\nNo current Deliberation");
			e.printStackTrace();
		}
		
		System.out.print("\nDelibCurr: " + delibCurrentUser);
		return delibCurrentUser;
	}

	@Override
	public JSONObject saveStudentCumule(int idStudent, int idEC, int uy, String type) {
		String query = type.equals("add") ? "INSERT INTO Etudiant_Cumule(etd_id, ec_id, au_enregistrement) VALUES(?, ?, ?)" : "DELETE FROM Etudiant_Cumule WHERE etd_id = ? AND ec_id = ? AND au_enregistrement = ?";
		boolean savingDelibDecision = jdbcTemplate.execute (query, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, idStudent);
				ps.setInt(2, idEC);
				ps.setInt(3, uy);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		JSONObject save = new JSONObject();
		save.put("status", savingDelibDecision ? 1 : 0);
		save.put("message", savingDelibDecision ? "EC cumule enregistré avec succès" : "Echec de l'enregistrement du cumule! Veuillez réessayer");
		return save;
	}

	@Override
	public JSONObject saveStudentECOK(int idStudent, int idEC, int idPer, int ok) {
		// La requete suppose qu'on ne met ok que les résultats de chaque session normale
		String query = "UPDATE Evaluation_Etudiant SET evale_ok = ? WHERE etd_id = ? AND ec_id = ? AND per_id = ? AND exam_id = (SELECT exam_id FROM Examen WHERE per_id = ? AND exam_sessiontype = 1 LIMIT 1)";
		boolean savingDelibDecision = jdbcTemplate.execute (query, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, ok);
				ps.setInt(2, idStudent);
				ps.setInt(3, idEC);
				ps.setInt(4, idPer);
				ps.setInt(5, idPer);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		JSONObject save = new JSONObject();
		save.put("status", savingDelibDecision ? 1 : 0);
		save.put("message", savingDelibDecision ? "EC statut enregistré avec succès" : "Echec de l'enregistrement du statut! Veuillez réessayer");
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
		delibs.setStudyunit_libelle(rs.getString("ue_libellelong"));
		delibs.setValid_credit_ue(rs.getInt("valid_credit_ue"));
		delibs.setCumule(rs.getInt("cumule") == 1);
		String res = rs.getString("credits_ue");
		if(res != null && res != "") {
			String[] credits = res.split(";");
			HashMap <String, String> ueCredits = new HashMap<String, String>();
			for(String dataCredit : credits) {
				String[] tmp = dataCredit.split("_");
				ueCredits.put(tmp[0]+"_"+tmp[1], tmp[2]);
			}
			delibs.setPeriod_credits(ueCredits);
		}
		
		// For EC
		delibs.setCoursesEvaluations(this.getEvaluationsCourseByUE(rs.getInt("ue_id"), rs.getInt("cumule"), univYearId, idStudent, idLevel, idPrc));
		return delibs;
	}
	
	private List<EvaluationCourseStudent> getEvaluationsCourseByUE(int ueId, int cumule, int univYearId, int idStudent, int idLevel, int idPrc ) {
		String sql = cumule == 0 ?
				"SELECT Element_Constitutif.*, ( SELECT GROUP_CONCAT(concat(Evaluation_Etudiant.per_id, \"_\", Examen.exam_sessiontype, \"_\", evale_evaluation, \"_\", evale_ok, \"_\", evale_creditobtenu) SEPARATOR \";\") FROM `Evaluation_Etudiant` " 
				+ "JOIN Examen ON Examen.exam_id = Evaluation_Etudiant.exam_id WHERE etd_id = " + idStudent +" AND ec_id = Element_Constitutif.ec_id LIMIT 1) as evaluations, "
				+ "(SELECT count(ec_id) FROM Etudiant_Cumule WHERE etd_id = "+ idStudent +" AND ec_id = Element_Constitutif.ec_id AND au_enregistrement = "+ univYearId +") as ec_cumule "
				+ "FROM Element_Constitutif "
				+ "WHERE ue_id = " + ueId 
				:
				"SELECT Element_Constitutif.*, ( SELECT GROUP_CONCAT(concat(Evaluation_Etudiant.per_id, \"_\", Examen.exam_sessiontype, \"_\", evale_evaluation, \"_\", evale_ok, \"_\", evale_creditobtenu) SEPARATOR \";\") FROM `Evaluation_Etudiant` " 
				+ "JOIN Examen ON Examen.exam_id = Evaluation_Etudiant.exam_id WHERE etd_id = " + idStudent +" AND ec_id = Element_Constitutif.ec_id LIMIT 1) as evaluations, "
				+ "(SELECT count(ec_id) FROM Etudiant_Cumule WHERE etd_id = "+ idStudent +" AND ec_id = Element_Constitutif.ec_id AND au_enregistrement = "+ univYearId +") as ec_cumule "
				+ "FROM Element_Constitutif "
				+ "JOIN Etudiant_Cumule ON Element_Constitutif.ec_id = Etudiant_Cumule.ec_id "
				+ "WHERE ue_id = " + ueId +" AND Etudiant_Cumule.etd_id = "+ idStudent +" AND Etudiant_Cumule.au_id = "+ univYearId;
		List<EvaluationCourseStudent> evaluationsCourses = jdbcTemplate.query(sql, new EvaluationCourseMapper());
		return evaluationsCourses;
	}
	
}



class EvaluationCourseMapper implements RowMapper<EvaluationCourseStudent>{
	public EvaluationCourseStudent mapRow(ResultSet rs, int arg1) throws SQLException {
		EvaluationCourseStudent course = new EvaluationCourseStudent();
		course.setStudyunit_id(rs.getInt("ue_id"));
		course.setProfessor_id(rs.getInt("prof_id"));
		//course.setCourse_libelle(rs.getString("ec_libellecourt"));
		course.setCourse_libelle(rs.getString("ec_libellelong"));
		course.setCourse_type(rs.getString("ec_type"));
		course.setCourse_credit(rs.getInt("ec_credit"));
	    course.setCourse_notation(rs.getInt("ec_notation"));
	    course.setCourse_coefficient(rs.getInt("ec_coefficient"));
	    course.setCourse_id(rs.getInt("ec_id"));
	    course.setCourse_volumehoraire(rs.getString("ec_volumehoraire"));
	    course.setCumule(rs.getInt("ec_cumule") > 0);
	    String evaluations = rs.getString("evaluations");
	    String[] tmpEvals = evaluations != null ? evaluations.split(";") : new String[0];
		HashMap<String, String[]> listEvaluations = new HashMap<String, String[]>();
		for(String eval : tmpEvals) {
			String[] tmp = eval.split("_");
			String[] dataEval = {tmp[2], tmp[3], tmp[4]};
			listEvaluations.put(tmp[0] +"_"+ tmp[1], dataEval);
		}
		course.setPeriodicalEvaluations(listEvaluations);
	    return course;
	}
  }