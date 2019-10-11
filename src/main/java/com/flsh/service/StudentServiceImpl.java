package com.flsh.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
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
import com.flsh.model.Course;
import com.flsh.model.Parcours;
import com.flsh.model.Student;
import com.flsh.model.StudyUnit;

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
	
	@Autowired
	ServletContext servletContext;
	
	@Override
	public List<Student> getAllStudents() {
		String queryStudent = "SELECT * FROM Etudiant ORDER BY etd_id limit 100";
		List<Student> students = jdbcTemplate.query(queryStudent, new StudentMapper());
		return students;
	}

	@Override
	public int getStudentsNumber() {
		String sql = "SELECT COUNT(*) from Etudiant WHERE 1";
		int studentsNumber = jdbcTemplate.queryForObject(sql, Integer.class);
		return studentsNumber;
	}

	@Override
	public List<Student> getStudentsByPage(int numPage) {
		String queryStudent = "SELECT * FROM Etudiant ORDER BY etd_id limit 100 offset "+ (100 * (numPage - 1));
		List<Student> students = jdbcTemplate.query(queryStudent, new StudentMapper());
		return students;
	}

	@Override
	public List<Student> getStudentsByCriteria(String criteria, int numPage) {
		String queryIntro = "SELECT *, (SELECT COUNT(*) ";
		String querySearch = "FROM Etudiant ";
		if(!criteria.equals("")) {
			if(criteria.matches("-?\\d+(\\.\\d+)?")) {
				querySearch += "WHERE etd_cin LIKE '%"+criteria+"%' OR etd_numeropasseport LIKE '%"+criteria+"%' ";
			} else {
				String[] tmpCriteria = criteria.split(" ");
				switch(tmpCriteria.length) {
					case 2:
						querySearch += "WHERE ( etd_nom LIKE '%"+tmpCriteria[0]+"%' AND etd_prenom LIKE '%"+tmpCriteria[1]+"%')";
						querySearch += " OR ( etd_nom LIKE '%"+tmpCriteria[1]+"%' AND etd_prenom LIKE '%"+tmpCriteria[0]+"%')";
						querySearch += " OR etd_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_adresse LIKE '%"+criteria+"%' ";
						break;
					case 3:
						querySearch += "WHERE ( etd_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+"%' AND etd_prenom LIKE '%"+tmpCriteria[2]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND etd_nom LIKE '%"+tmpCriteria[0]+"%')";
						querySearch += " OR ( etd_nom LIKE '%"+tmpCriteria[2]+" "+tmpCriteria[1]+"%' AND etd_prenom LIKE '%"+tmpCriteria[0]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[0]+"%' AND etd_nom LIKE '%"+tmpCriteria[2]+"%')";
						querySearch += " OR etd_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_adresse LIKE '%"+criteria+"%' ";
						break;
					case 4:
						querySearch += "WHERE ( etd_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+"%' AND etd_prenom LIKE '%"+tmpCriteria[2]+" "+tmpCriteria[3]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[2]+" "+tmpCriteria[3]+"%' AND etd_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+"%')";
						querySearch += " OR ( etd_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[2]+"%' AND etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[0]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[0]+"%' AND etd_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[2]+"%')";
						querySearch += " OR ( etd_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[0]+"%' AND etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[2]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND etd_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[0]+"%')";
						querySearch += " OR ( etd_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND etd_prenom LIKE '%"+tmpCriteria[3]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND etd_nom LIKE '%"+tmpCriteria[3]+"%')";
						querySearch += " OR etd_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_adresse LIKE '%"+criteria+"%' ";
						break;
					default:
						querySearch += "WHERE etd_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_adresse LIKE '%"+criteria+"%' ";
						break;
				}
			}
		}
		String query = queryIntro +" "+querySearch+") as maxnumber "+querySearch+" limit 100 offset "+ (100 * (numPage - 1));
		List<Student> students = jdbcTemplate.query(query, new StudentMapper());
		return students;
	}

	@Override
	public List<Student> getStudentsByUnivYearAndLevel(int idUY, int idLevel, int numPage) {
		String queryStudent = idLevel == 0 ? "SELECT Etudiant.*, ( select count(*) FROM Etudiant WHERE etd_id not in (select etd_id from Niveau_Etudiant where au_id = "+idUY+") ) as maxnumber FROM Etudiant WHERE etd_id not in (select etd_id from Niveau_Etudiant where au_id = "+idUY+") LIMIT 100 OFFSET "+(100 * (numPage - 1)) 
								: "SELECT Etudiant.*, (SELECT count(*) FROM Etudiant join Niveau_Etudiant on Niveau_Etudiant.etd_id = Etudiant.etd_id where au_id = "+idUY+" and niv_id = "+idLevel+") as maxnumber FROM Etudiant join Niveau_Etudiant on Niveau_Etudiant.etd_id = Etudiant.etd_id"
								+ " where au_id = "+idUY+" and niv_id = "+idLevel+" LIMIT 100 OFFSET "+(100 * (numPage - 1));
		System.out.print(queryStudent);
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
			rtn.put("message", "Echec de l'insertion de l'étudiant dans la base!");
			return rtn;
		} else {
			rtn.put("status", 1);
			rtn.put("message", "Enregistré avec succès!");
			int idStudent = student.getStudent_id() != 0 ? student.getStudent_id() : holder.getKey().intValue();
			rtn.put("idEt", idStudent);
			return rtn;
		}
	}

	@Override
	public JSONObject saveStudent(Student student, int uy, int level, int prc, int paid, String dateInscription, String choixprc) {
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
			rtn.put("message", "Echec de l'insertion de l'étudiant dans la base!");
			return rtn;
		} else {
			int idStud = holder.getKey().intValue();
			res = this.saveDataSubscription(idStud, uy, level, prc, paid, dateInscription, choixprc, null);
			if(res <= 0) {
				rtn.put("status", 0);
				rtn.put("message", "Echec de l'enregistrement de l'inscription!!");
			} else {
				rtn.put("status", 1);
				rtn.put("message", "Enregistré avec succès!");
				rtn.put("idEt", idStud);
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
	
	@Override
	public HashSet<StudyUnit> getParcoursChoiceUnits(int idParcours) {
		HashSet<StudyUnit> listUnits = new HashSet<StudyUnit>();
		String sql = "SELECT * FROM Unite_Enseignement where ue_id in (select ue_id from Element_Constitutif where ec_type = 'C') and prc_id = "+idParcours;
		List<StudyUnit> units = jdbcTemplate.query(sql, new UnitsMapper());
		for(StudyUnit unit : units) {
			Set<Course> listCourses = new HashSet<Course>(this.getChoiceCourseById( unit.getStudyunit_id()));
			unit.setCourses((HashSet<Course>) listCourses);
		    listUnits.add(unit);
		}
		return listUnits;
	}
	
	private List<Course> getChoiceCourseById(int idUnits) {
		String sql = "SELECT * FROM  Element_Constitutif WHERE ec_type = 'C' AND ue_id = "+idUnits;
		List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses;
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

	@Override
	public JSONObject saveSubscriptionStudent(int idStudent, int idUY, int idLevel, int idPrc, int paid, String dateInscription, String choixprc, String cumules) {
		JSONObject rtn = new JSONObject();
		int res = this.saveDataSubscription(idStudent, idUY, idLevel, idPrc, paid, dateInscription, choixprc, cumules);
		if(res <= 0) {
			rtn.put("status", 0);
			rtn.put("message", "Echec de l'enregistrement de l'inscription!!");
		} else {
			rtn.put("status", 1);
			rtn.put("message", "Inscription enregistrée avec succès!");
		}
		return rtn;
	}
	
	private int saveDataSubscription(int idStudent, int idUY, int idLevel, int idPrc, int paid, String dateInscription, String choixprc, String cumules) {
		String queryInsert = "INSERT INTO Niveau_Etudiant(au_id, niv_id, etd_id, prc_id, net_inscription, net_dateinscription, net_ecchoisis) values(:uy, :lvl, :id, :prc, :paid, :date, :choix)";
		String sqlDeleteSubs = "DELETE FROM Niveau_Etudiant WHERE etd_id = ? and au_id = ?";
		
		jdbcTemplate.execute (sqlDeleteSubs, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, idStudent);
				ps.setInt(2, idUY);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		
		System.out.print("\ncumules");
		System.out.print(cumules);
		System.out.print("\n<<<<<<\n");
		
		if(cumules != null) {
			String sqlDeleteCumules = "DELETE FROM Etudiant_Cumule WHERE etd_id = ? and au_id = ?";
			jdbcTemplate.execute (sqlDeleteCumules, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setInt(1, idStudent);
					ps.setInt(2, idUY);
					System.out.print("\ndeleted cumules");
					return ps.executeUpdate() >= 0 ? true : false;
				}
			});
			if(cumules.contentEquals("")) {
				String queryCumule = "UPDATE Etudiant_Cumule SET au_id = :uy WHERE au_id IS NULL";
				SqlParameterSource parameters = new MapSqlParameterSource()
						.addValue("uy", idUY);
				System.out.print("\nupdated cumules");
				namedJdbcTemplate.update(queryCumule, parameters);
			} else {
				String[] idECs = cumules.split("_");
				for(String idEC : idECs) {
					String queryCumule = "INSERT INTO Etudiant_Cumule(etd_id, ec_id, au_id) VALUES(:std, :ec, :uy)";
					SqlParameterSource parameters = new MapSqlParameterSource()
							.addValue("std", idStudent)
							.addValue("ec", Integer.parseInt(idEC))
							.addValue("uy", idUY);
					System.out.print("\nadded cumules for EC "+idEC);
					namedJdbcTemplate.update(queryCumule, parameters);
				}
			}
		}
		
		SqlParameterSource parameters = new MapSqlParameterSource()
						.addValue("uy", idUY)
						.addValue("lvl", idLevel)
						.addValue("id", idStudent)
						.addValue("prc", idPrc)
						.addValue("paid", paid)
						.addValue("date", dateInscription)
						.addValue("choix", choixprc);
		return namedJdbcTemplate.update(queryInsert, parameters);
	}

	@Override
	public JSONObject getSubscriptionInfos(int idStudent, int idUY, int idLevel) {
		String sql = "SELECT * FROM Niveau_Etudiant where au_id = "+idUY+" and niv_id = "+idLevel+" and etd_id = "+idStudent;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		System.out.print(sql);
		System.out.print(rows);
		JSONObject rtn = new JSONObject();
		if(rows.size() == 0) {
			rtn.put("status", 0);
			rtn.put("message", "Cet étudiant n'est pas inscrit à ce niveau");
		} else {
			JSONObject infos = new JSONObject();
			Map<String, Object> subs = rows.get(0);
			infos.put("idUY", idUY);
			infos.put("idLevel", idLevel);
			infos.put("idStudent", idLevel);
			infos.put("parcours", subs.get("prc_id"));
			infos.put("inscrit", subs.get("net_inscription"));
			infos.put("date", subs.get("net_dateinscription"));
			infos.put("choix", subs.get("net_ecchoisis"));
			rtn.put("infos", infos);
			rtn.put("status", 1);
			rtn.put("message", "Infos récupérées");
		}
		return rtn;
	}

	@Override
	public JSONObject deleteSubscriptionStudent(int idStudent, int idUY) {
		String sqlDeleteSubs = "DELETE FROM Niveau_Etudiant WHERE etd_id = ? and au_id = ?";
		String sqlDeleteEvals = "DELETE Evaluation_Etudiant.*, Moyenne_Ue.* from Evaluation_Etudiant, Moyenne_Ue WHERE Evaluation_Etudiant.etd_id = Moyenne_Ue.etd_id and Evaluation_Etudiant.per_id = Moyenne_Ue.per_id and Moyenne_Ue.etd_id = ? and Moyenne_Ue.per_id in (select per_id from Periode where au_id = ?) ";
		JSONObject rtn = new JSONObject();
		boolean res = true;
		res = jdbcTemplate.execute (sqlDeleteEvals, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, idStudent);
				ps.setInt(2, idUY);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		
		if(!res) {
			rtn.put("status", 0);
		    rtn.put("message", "Echec de la suppression des évaluations de l'étudiant! Veuillez réessayer");
		    return rtn;
		}
		
		res = jdbcTemplate.execute (sqlDeleteSubs, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, idStudent);
				ps.setInt(2, idUY);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		rtn.put("status", res ? 1 : 0);
	    rtn.put("message", res ? "Suppression réussie" : "Echec de la désinscription! Veuillez réessayer");
		return rtn;
	}

	/**
	 * Search subscribed student
	 */
	@Override
	public Object getStudentsByUnivYearAndLevelAndCriteria(int idUY, int idLevel, String criteria, int numPage) {
		String queryIntro = "SELECT *, (SELECT COUNT(*) ";
		String querySearch = "FROM Etudiant ";
		String levelCriteria = idLevel == 0 ? " AND etd_id not in  (SELECT etd_id FROM Niveau_Etudiant WHERE au_id = "+idUY+") " : " AND etd_id in  (SELECT etd_id FROM Niveau_Etudiant WHERE au_id = "+idUY+" AND niv_id = "+idLevel+") ";
		if(!criteria.equals("")) {
			if(criteria.matches("-?\\d+(\\.\\d+)?")) {
				querySearch += "WHERE ( etd_cin LIKE '%"+criteria+"%' OR etd_numeropasseport LIKE '%"+criteria+"%' )"+levelCriteria;
			} else {
				String[] tmpCriteria = criteria.split(" ");
				switch(tmpCriteria.length) {
					case 2:
						querySearch += "WHERE (( etd_nom LIKE '%"+tmpCriteria[0]+"%' AND etd_prenom LIKE '%"+tmpCriteria[1]+"%')";
						querySearch += " OR ( etd_nom LIKE '%"+tmpCriteria[1]+"%' AND etd_prenom LIKE '%"+tmpCriteria[0]+"%')";
						querySearch += " OR etd_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_adresse LIKE '%"+criteria+"%' )"+levelCriteria;
						break;
					case 3:
						querySearch += "WHERE (( etd_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+"%' AND etd_prenom LIKE '%"+tmpCriteria[2]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND etd_nom LIKE '%"+tmpCriteria[0]+"%')";
						querySearch += " OR ( etd_nom LIKE '%"+tmpCriteria[2]+" "+tmpCriteria[1]+"%' AND etd_prenom LIKE '%"+tmpCriteria[0]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[0]+"%' AND etd_nom LIKE '%"+tmpCriteria[2]+"%')";
						querySearch += " OR etd_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_adresse LIKE '%"+criteria+"%' )"+levelCriteria;
						break;
					case 4:
						querySearch += "WHERE (( etd_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+"%' AND etd_prenom LIKE '%"+tmpCriteria[2]+" "+tmpCriteria[3]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[2]+" "+tmpCriteria[3]+"%' AND etd_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+"%')";
						querySearch += " OR ( etd_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[2]+"%' AND etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[0]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[0]+"%' AND etd_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[2]+"%')";
						querySearch += " OR ( etd_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[0]+"%' AND etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[2]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND etd_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[0]+"%')";
						querySearch += " OR ( etd_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND etd_prenom LIKE '%"+tmpCriteria[3]+"%')";
						querySearch += " OR ( etd_prenom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND etd_nom LIKE '%"+tmpCriteria[3]+"%')";
						querySearch += " OR etd_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_adresse LIKE '%"+criteria+"%' )"+levelCriteria;
						break;
					default:
						querySearch += "WHERE (etd_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR etd_adresse LIKE '%"+criteria+"%' )"+levelCriteria;
						break;
				}
			}
		} else {
			querySearch += idLevel == 0 ? " WHERE etd_id not in  (SELECT etd_id FROM Niveau_Etudiant WHERE au_id = "+idUY+") " : " WHERE etd_id in  (SELECT etd_id FROM Niveau_Etudiant WHERE au_id = "+idUY+" AND niv_id = "+idLevel+") ";
		}
		String query = queryIntro +" "+querySearch+") as maxnumber "+querySearch+" limit 100 offset "+ (100 * (numPage - 1));
		System.out.print("\n search query : "+query+"\n");
		List<Student> students = jdbcTemplate.query(query, new StudentMapper());
		return students;
	}

	@Override
	public List<Student> getStudentsByUnivYearAndParcours(int idUY, int idParcours) {
		String queryStudent = idParcours == 0 ? "SELECT * FROM Etudiant WHERE etd_id not in (select etd_id from Niveau_Etudiant where au_id = "+idUY+")" 
				: "SELECT * FROM Etudiant join Niveau_Etudiant on Niveau_Etudiant.etd_id = Etudiant.etd_id"
				+ " WHERE au_id = "+idUY+" AND prc_id = "+idParcours;
		System.out.print(queryStudent);
		List<Student> students = jdbcTemplate.query(queryStudent, new StudentMapper());
		return students;
	}

	@Override
	public List<Parcours> getAllParcours() {
		String queryParcours = "SELECT Parcours.*, niv_libelle, 1 as show_niv "
				+ "FROM Parcours "
				+ "JOIN Niveau ON Parcours.niv_id = Niveau.niv_id";
		List<Parcours> parcours = jdbcTemplate.query(queryParcours, new ParcoursMapper());
		return parcours;
	}

	@Override
	public List<Course> getECListByParcours(int idPrc) {
		String sql = "SELECT Element_Constitutif.*, ue_libellecourt, ue_libellelong FROM Element_Constitutif "
				+ "join Unite_Enseignement on Unite_Enseignement.ue_id = Element_Constitutif.ue_id "
				+ "join Parcours on Unite_Enseignement.prc_id = Parcours.prc_id "
				+ "join Niveau on Parcours.niv_id = Niveau.niv_id "
				+ "WHERE Unite_Enseignement.prc_id = "+idPrc;
		List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses;
	}

	@Override
	public List<Course> getStudentECCumuleList(int idStudent, int idUY) {
		String sql = "SELECT Element_Constitutif.*, ue_libellecourt, ue_libellelong, niv_libelle FROM Element_Constitutif "
				+ "join Unite_Enseignement on Unite_Enseignement.ue_id = Element_Constitutif.ue_id "
				+ "join Etudiant_Cumule on Etudiant_Cumule.ec_id = Element_Constitutif.ec_id "
				+ "join Parcours on Unite_Enseignement.prc_id = Parcours.prc_id "
				+ "join Niveau on Niveau.niv_id = Parcours.niv_id "
				+ "WHERE Etudiant_Cumule.etd_id = "+idStudent+" AND Etudiant_Cumule.au_id = "+idUY;
		List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses;
	}
	
	@Override
	public void getCroppedImageUrl(Student student) {
		String nameImage = "Student_" + student.getStudent_id() + ".jpeg";
		
		if(student.getImage_cropped() != null) {
			byte[] decodedImg = Base64.getDecoder()
                    .decode(student.getImage_cropped().replace("data:image/jpeg;base64,", "").getBytes(StandardCharsets.UTF_8));
			
			Path destinationFile = Paths.get(servletContext.getRealPath("resources/img/student"), nameImage);
			try {
				String path = servletContext.getRealPath("resources/img/student");
				System.out.println(path);
				Files.write(destinationFile, decodedImg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		try {
			System.out.print("\n Max number : "+rs.getInt("maxnumber")+"\n");
			student.setNumber(rs.getInt("maxnumber"));
		} catch (Exception e) {
			System.out.print("\n No max number \n");
		}
		try {
			String evaluations = rs.getString("evaluations");
			String[] tmpEvals = evaluations.split(";");
			HashMap<String, String> listEvaluations = new HashMap<String, String>();
			for(String eval : tmpEvals) {
				String[] tmp = eval.split("_");
				listEvaluations.put(tmp[0]+"-"+tmp[1], tmp[2]);
			}
			student.setEvaluations(listEvaluations);
		} catch (Exception e) {
			System.out.print("\nNo evaluation data\n");
			//e.printStackTrace();
		}
		return student;
	}
	
}