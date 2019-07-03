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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.flsh.interfaces.StudentService;
import com.flsh.model.Period;
import com.flsh.model.Student;

public class StudentServiceImpl implements StudentService {

	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	NamedParameterJdbcTemplate namedJdbcTemplate; 
	
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Autowired
	public StudentServiceImpl(DataSource dsrc) {
		this.dataSource = dsrc;
		jdbcTemplate = new JdbcTemplate(dsrc);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dsrc);
		logger.info("Init period service");
	}
	
	@Override
	public List<Student> getAllStudents() {
		String queryStudent = "SELECT * FROM Etudiant ORDER BY etd_id";
		List<Student> students = jdbcTemplate.query(queryStudent, new StudentMapper());
		return students;
	}

	@Override
	public Student getStudentById(int idStudent) {
		String queryStudent = "SELECT * FROM Etudiant WHERE etd_id = "+idStudent;
		List<Student> students = jdbcTemplate.query(queryStudent, new StudentMapper());
		return students.size() > 0 ? students.get(0) : null;
	}

	@Override
	public JSONObject saveStudent(Student student) {
		String queryInsert = student.getStudent_id() == 0 ? "INSERT INTO Etudiant(civ_id, etd_nom, etd_prenom, etd_datenaissance, etd_nationalite, etd_numeropasseport, etd_cin, etd_datecin, etd_lieucin, etd_adresse, etd_email, etd_dernieretab, etd_nomconjoint, etd_nompere, etd_professionpere, etd_nommere, etd_professionmere) "
															+ "values(:civ, :nom, :prenom, :datenaiss, :nat, :psprt, :cin, :datecin, :lieucin, :adr, :mail, :dernet, :conj, :per, :pper, :mer, :pmer)" :
															"UPDATE Etudiant SET civ_id = :civ, etd_nom = :nom, etd_prenom = :prenom, etd_datenaissance = :datenaiss, etd_nationalite = :nat, etd_numeropasseport = :psprt, etd_cin = :cin, etd_datecin = :datecin, etd_lieucin = :lieucin, etd_adresse = :adr, etd_email = :mail, etd_dernieretab = :dernet, etd_nomconjoint = :conj, etd_nompere = :per, etd_professionpere = :pper, etd_nommere = :mer, etd_professionmere = :pmer WHERE etd_id = :idEt";
		JSONObject rtn = new JSONObject();
		String check = this.checkStudentInfo(student);
		System.out.print("Request result");
		System.out.print(check);
		if(!check.equals("")) {
			rtn.put("status", 0);
			rtn.put("message", check);
			return rtn;
		}
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("civ", student.getStudent_civ())
				.addValue("nom", student.getStudent_name())
				.addValue("prenom", student.getStudent_lastname())
				.addValue("datenaiss", student.getStudent_birthdate())
				.addValue("nat", student.getStudent_nationality())
				.addValue("psprt", student.getStudent_passport())
				.addValue("cin", student.getStudent_cin())
				.addValue("datecin", student.getStudent_cin().equals("") ? null : student.getStudent_cindate())
				.addValue("lieucin", student.getStudent_cinlocation())
				.addValue("adr", student.getStudent_adress())
				.addValue("mail", student.getStudent_email())
				.addValue("dernet", student.getStudent_lastetab())
				.addValue("conj", student.getStudent_nameconjoint())
				.addValue("per", student.getStudent_namefather())
				.addValue("pper", student.getStudent_jobfather())
				.addValue("mer", student.getStudent_namemother())
				.addValue("pmer", student.getStudent_jobmother());
		if(student.getStudent_id() != 0) {
			parameters = new MapSqlParameterSource()
					.addValue("civ", student.getStudent_civ())
					.addValue("nom", student.getStudent_name())
					.addValue("prenom", student.getStudent_lastname())
					.addValue("datenaiss", student.getStudent_birthdate())
					.addValue("nat", student.getStudent_nationality())
					.addValue("psprt", student.getStudent_passport())
					.addValue("cin", student.getStudent_cin())
					.addValue("datecin", student.getStudent_cin().equals("") ? null : student.getStudent_cindate())
					.addValue("lieucin", student.getStudent_cinlocation())
					.addValue("adr", student.getStudent_adress())
					.addValue("mail", student.getStudent_email())
					.addValue("dernet", student.getStudent_lastetab())
					.addValue("conj", student.getStudent_nameconjoint())
					.addValue("per", student.getStudent_namefather())
					.addValue("pper", student.getStudent_jobfather())
					.addValue("mer", student.getStudent_namemother())
					.addValue("pmer", student.getStudent_jobmother())
					.addValue("idEt", student.getStudent_id());
		}
		int res = namedJdbcTemplate.update(queryInsert, parameters, holder);
		if(res <= 0) {
			rtn.put("status", 0);
			rtn.put("message", "Echec de l'insertion de la période dans la base!");
			return rtn;
		} else {
			rtn.put("status", 1);
			rtn.put("message", "Enregistré avec succès!");
			return rtn;
		}
	}

	@Override
	public JSONObject saveStudent(Student student, int uy, int level, int prc, int paid) {
		String queryInsert = "INSERT INTO Etudiant(civ_id, etd_nom, etd_prenom, etd_datenaissance, etd_nationalite, etd_numeropasseport, etd_cin, etd_datecin, etd_lieucin, etd_adresse, etd_email, etd_dernieretab, etd_nomconjoint, etd_nompere, etd_professionpere, etd_nommere, etd_professionmere) "
								+ "values(:civ, :nom, :prenom, :datenaiss, :nat, :psprt, :cin, :datecin, :lieucin, :adr, :mail, :dernet, :conj, :per, :pper, :mer, :pmer)";
		
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		JSONObject rtn = new JSONObject();
		
		String check = this.checkStudentInfo(student);
		System.out.print("Request result");
		System.out.print(check);
		if(!check.equals("")) {
			rtn.put("status", 0);
			rtn.put("message", check);
			return rtn;
		}
		SqlParameterSource parameters = new MapSqlParameterSource()
											.addValue("civ", student.getStudent_civ())
											.addValue("nom", student.getStudent_name())
											.addValue("prenom", student.getStudent_lastname())
											.addValue("datenaiss", student.getStudent_birthdate())
											.addValue("nat", student.getStudent_nationality())
											.addValue("psprt", student.getStudent_passport())
											.addValue("cin", student.getStudent_cin())
											.addValue("datecin", student.getStudent_cin().equals("") ? null : student.getStudent_cindate())
											.addValue("lieucin", student.getStudent_cinlocation())
											.addValue("adr", student.getStudent_adress())
											.addValue("mail", student.getStudent_email())
											.addValue("dernet", student.getStudent_lastetab())
											.addValue("conj", student.getStudent_nameconjoint())
											.addValue("per", student.getStudent_namefather())
											.addValue("pper", student.getStudent_jobfather())
											.addValue("mer", student.getStudent_namemother())
											.addValue("pmer", student.getStudent_jobmother());
		int res = namedJdbcTemplate.update(queryInsert, parameters, holder);
		if(res <= 0) {
			rtn.put("status", 0);
			rtn.put("message", "Echec de l'insertion de la période dans la base!");
			return rtn;
		} else {
			int idStud = holder.getKey().intValue();
			queryInsert = "INSERT INTO Niveau_Etudiant(au_id, niv_id, etd_id, prc_id, net_inscription) values(:uy, :lvl, :id, :prc, :paid)";
			parameters = new MapSqlParameterSource()
							.addValue("uy", uy)
							.addValue("lvl", level)
							.addValue("id", idStud)
							.addValue("prc", prc)
							.addValue("paid", paid);
			res = namedJdbcTemplate.update(queryInsert, parameters);
			if(res <= 0) {
				rtn.put("status", 0);
				rtn.put("message", "Echec de l'enregistrement de l'inscription!!");
			} else {
				rtn.put("status", 1);
				rtn.put("message", "Enregistré avec succès!");
			}
			return rtn;
		}
	}

	@Override
	public JSONObject deleteStudent(int idStudent) {
		String sql = "DELETE FROM Etudiant WHERE etd_id = ?";
		String sqlDeleteLevels = "DELETE FROM Niveau_Etudiant WHERE etd_id = ? ";
		String sqlDeleteEvals = "DELETE Evaluation_Etudiant.*, Moyenne_Ue.* from Evaluation_Etudiant, Moyenne_Ue WHERE Evaluation_Etudiant.etd_id = Moyenne_Ue.etd_id and Moyenne_Ue.etd_id = ? ";
		
		// Computing response variables
		JSONObject rtn = new JSONObject();
		boolean res = true;
		
		// Deleting the lower level dependency in the structure
		res = jdbcTemplate.execute (sqlDeleteEvals, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, idStudent);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		
		if(res) {
			res = jdbcTemplate.execute (sqlDeleteLevels, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setInt(1, idStudent);
					return ps.executeUpdate() >= 0 ? true : false;
				}
			});
		} else {
			rtn.put("status", 0);
		    rtn.put("message", "Echec de la suppression des évaluations de l'étudiant! Veuillez réessayer");
		    return rtn;
		}
		
		if(res) { // Delete the middle level dependency
		
			// Deleting the lower level dependency in the structure
			res = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setInt(1, idStudent);
					return ps.executeUpdate() >= 0 ? true : false;
				}
			});
			rtn.put("status", res ? 1 : 0);
		    rtn.put("message", res ? "Suppression réussie" : "Echec de la suppression de l'étudiant! Veuillez réessayer");
		} else {
			rtn.put("status", 0);
		    rtn.put("message", "Echec de la suppression des niveaux parcouru par l'étudiant! Veuillez réessayer");
		}
	    return rtn;
	}
	
	private String checkStudentInfo(Student student) {
		String ifUpdate = student.getStudent_id() == 0 ? "" : " and etd_id != "+student.getStudent_id();
		String sql = "SELECT COUNT(*) from Etudiant WHERE etd_nom = '"+student.getStudent_name()+"' and etd_prenom = '"+student.getStudent_lastname()+"'"+ifUpdate;
		int evalNumber = jdbcTemplate.queryForObject(sql, Integer.class);
		if(evalNumber > 0) {
			return "Un étudiant ayant les mêmes nom et prénom existe dans la base!";
		}
		if(!student.getStudent_cin().equals("")) {
			sql = "SELECT COUNT(*) from Etudiant WHERE etd_cin = '"+student.getStudent_cin()+"'"+ifUpdate;
			evalNumber = jdbcTemplate.queryForObject(sql, Integer.class);
			if(evalNumber > 0) {
				return "Le numéro de CIN entré est associé à un étudiant enregistré!";
			}
		}
		if(!student.getStudent_passport().equals("")) {
			sql = "SELECT COUNT(*) from Etudiant WHERE etd_numeropasseport = '"+student.getStudent_passport()+"'"+ifUpdate;
			evalNumber = jdbcTemplate.queryForObject(sql, Integer.class);
			if(evalNumber > 0) {
				return "Le numéro de passeport entré est associé à un étudiant enregistré!";
			}
		}
		if(!student.getStudent_email().equals("")) {
			sql = "SELECT COUNT(*) from Etudiant WHERE etd_email = '"+student.getStudent_email()+"'"+ifUpdate;
			evalNumber = jdbcTemplate.queryForObject(sql, Integer.class);
			if(evalNumber > 0) {
				return "L'email entré est associé à un étudiant enregistré!";
			}
		}
		
		return "";
	}
	
}

class StudentMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		Student student = new Student();
		student.setStudent_id(rs.getInt("etd_id"));
		student.setStudent_civ(rs.getInt("civ_id"));
		student.setStudent_name(rs.getString("etd_nom"));
		student.setStudent_lastname(rs.getString("etd_prenom"));
		student.setStudent_birthdate(rs.getString("etd_datenaissance"));
		student.setStudent_adress(rs.getString("etd_adresse"));
		student.setStudent_email(rs.getString("etd_email"));
		student.setStudent_lastetab(rs.getString("etd_dernieretab"));
		student.setStudent_nameconjoint(rs.getString("etd_nomconjoint"));
		student.setStudent_namefather(rs.getString("etd_nompere"));
		student.setStudent_namemother(rs.getString("etd_nommere"));
		student.setStudent_nationality(rs.getString("etd_nationalite"));
		student.setStudent_passport(rs.getString("etd_numeropasseport"));
		student.setStudent_cin(rs.getString("etd_cin"));
		student.setStudent_cindate(rs.getString("etd_datecin"));
		student.setStudent_cinlocation(rs.getString("etd_lieucin"));
		student.setStudent_jobfather(rs.getString("etd_professionpere"));
		student.setStudent_jobmother(rs.getString("etd_professionmere"));
		return student;
	}
	
}