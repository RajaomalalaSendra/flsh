package com.flsh.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.flsh.interfaces.NoteService;
import com.flsh.model.Course;
import com.flsh.model.Exam;
import com.flsh.model.Student;

public class NoteServiceImpl implements NoteService {
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	NamedParameterJdbcTemplate namedJdbcTemplate;  
	
	private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
	
	@Autowired
	public NoteServiceImpl(DataSource dsrc) {
		this.dataSource = dsrc;
		jdbcTemplate = new JdbcTemplate(dsrc);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dsrc);
		logger.info("Init period service");
	}

	@Override
	public List<Course> getECProfessor(int idProf) {
		String sql = "SELECT Element_Constitutif.*, niv_libelle, ue_libellecourt, ue_libellelong FROM Element_Constitutif "
					+ "join Unite_Enseignement on Unite_Enseignement.ue_id = Element_Constitutif.ue_id "
					+ "join Parcours on Unite_Enseignement.prc_id = Parcours.prc_id "
					+ "join Niveau on Parcours.niv_id = Niveau.niv_id WHERE prof_id = "+idProf
					+ " UNION SELECT Element_Constitutif.*, niv_libelle, ue_libellecourt, ue_libellelong FROM Element_Constitutif "
					+ "join Unite_Enseignement on Unite_Enseignement.ue_id = Element_Constitutif.ue_id "
					+ "join Parcours on Unite_Enseignement.prc_id = Parcours.prc_id "
					+ "join Niveau on Parcours.niv_id = Niveau.niv_id WHERE Element_Constitutif.ue_id in (select Prof_Ue.ue_id FROM Prof_Ue WHERE Prof_Ue.prof_id = "+idProf+") and prof_id != "+idProf;
		List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses;
	}

	@Override
	public List<Exam> getExamsByECandUY(int idEC, int idUY) {
		String sql = "SELECT Examen.* FROM Examen "
					+ "JOIN Periode_EC ON Periode_EC.per_id = Examen.per_id "
					+ "JOIN Periode ON Examen.per_id = Periode.per_id WHERE ec_id = "+idEC+" AND au_id = "+idUY;
		List<Exam> periods = jdbcTemplate.query(sql, new ExamMapper());
		return periods;
	}
	
	/**
	 * Get the list of students in EC to insert notes
	 */
	@Override
	public List<Student> getStudentsandEvalsByECandUY(int idEC, int idUY) {
		String sql = "SELECT Etudiant.*, "
				+ "(SELECT GROUP_CONCAT(concat(exam_id, '_',per_id,'_',evale_evaluation) separator ';') FROM Evaluation_Etudiant WHERE per_id IN (select per_id from Periode where au_id = "+idUY+") and ec_id = "+idEC+" and etd_id = Etudiant.etd_id) as evaluations "
				+ "FROM Etudiant "
				+ "JOIN Niveau_Etudiant ON Niveau_Etudiant.etd_id = Etudiant.etd_id "
				+ "JOIN Unite_Enseignement ON Unite_Enseignement.prc_id = Niveau_Etudiant.prc_id "
				+ "JOIN Element_Constitutif ON Unite_Enseignement.ue_id = Element_Constitutif.ue_id "
				+ "WHERE ec_id = "+idEC+" AND au_id = "+idUY+" "
				// Add students 
				+ "UNION "
				+ "SELECT Etudiant.*, "
				+ "(SELECT GROUP_CONCAT(concat(exam_id, '_',per_id,'_',evale_evaluation) separator ';') FROM Evaluation_Etudiant WHERE per_id IN (select per_id from Periode where au_id = "+idUY+") and ec_id = "+idEC+" and etd_id = Etudiant.etd_id) as evaluations "
				+ "FROM Etudiant "
				+ "JOIN Etudiant_Cumule ON Etudiant_Cumule.etd_id = Etudiant.etd_id "
				+ "JOIN Element_Constitutif ON Etudiant_Cumule.ec_id = Element_Constitutif.ec_id "
				+ "WHERE Element_Constitutif.ec_id = "+idEC+" AND Etudiant_Cumule.au_id = "+idUY;
		List<Student> students = jdbcTemplate.query(sql, new StudentMapper());
		return students;
	}

	@Override
	public JSONObject saveMoyExamStudent(int idStudent, int idExam, int idPer, int idEC, String noteVal) {
		JSONObject rtn = new JSONObject();
		String sql = "select evale_id from Evaluation_Etudiant where etd_id = "+idStudent+" and ec_id = "+idEC+" and exam_id = "+idExam+" and per_id = "+idPer;
		int evalId = 0; 
		try {
			evalId = jdbcTemplate.queryForObject(sql, Integer.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(evalId == 0) {
			sql = "INSERT into Evaluation_Etudiant(etd_id, ec_id, exam_id, per_id, evale_evaluation) values("+idStudent+", "+idEC+", "+idExam+", "+idPer+", '"+noteVal+"')";
		} else {
			sql = "UPDATE Evaluation_Etudiant set evale_evaluation = '"+noteVal+"' WHERE evale_id = "+evalId;
		}
		boolean res = namedJdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		rtn.put("status", !res ? 0 : 1 );
		rtn.put("message", !res ? "Echec de l'enregistrement de la note!" : "Enregistré avec succès!");
		return rtn;
	}

	@Override
	public List<Course> getECParcours(int idPrc) {
		String sql = "SELECT Element_Constitutif.*, ue_libellecourt, ue_libellelong FROM Element_Constitutif "
				+ "join Unite_Enseignement on Unite_Enseignement.ue_id = Element_Constitutif.ue_id "
				+ "join Parcours on Unite_Enseignement.prc_id = Parcours.prc_id "
				+ "join Niveau on Parcours.niv_id = Niveau.niv_id WHERE Unite_Enseignement.prc_id = "+idPrc;
		List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses;
	}
	
}

class ExamMapper implements RowMapper<Exam> {

	@Override
	public Exam mapRow(ResultSet rs, int rowNum) throws SQLException {
		Exam exam = new Exam();
		exam.setExamId(rs.getInt("exam_id"));
		exam.setPeriodId(rs.getInt("per_id"));
		exam.setExamLibelle(rs.getString("exam_libelle"));
		exam.setExamSessiontype(rs.getString("exam_sessiontype"));
		return exam;
	}
	
}
