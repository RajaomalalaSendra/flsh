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

import com.flsh.interfaces.PeriodService;
import com.flsh.model.Level;
import com.flsh.model.Period;
import com.flsh.model.UniversityYear;

public class PeriodServiceImpl implements PeriodService {
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	NamedParameterJdbcTemplate namedJdbcTemplate;  
	
	private static final Logger logger = LoggerFactory.getLogger(PeriodServiceImpl.class);
	
	@Autowired
	public PeriodServiceImpl(DataSource dsrc) {
		this.dataSource = dsrc;
		jdbcTemplate = new JdbcTemplate(dsrc);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dsrc);
		logger.info("Init period service");
	}

	@Override
	public List<UniversityYear> getAllUnivYears() {
		String sql = "select * from Annee_Universitaire order by au_debut asc";
		List<UniversityYear> aus = jdbcTemplate.query(sql, new AUMapper());
		return aus;
	}

	@Override
	public JSONObject saveUnivYear(UniversityYear au) {
		JSONObject rtn = new JSONObject();
		String sql = au.getUniversity_year_id() == 0 ? "INSERT INTO Annee_Universitaire(au_libelle, au_debut, au_fin) VALUES(?, ?, ?)" 
				: "UPDATE Annee_Universitaire SET au_libelle = ?, au_debut = ?, au_fin = ? WHERE au_id = ?";
		boolean save = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {
		
		@Override
		public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			ps.setString(1, au.getUniversity_year_libelle());
			ps.setString(2, au.getUniversity_year_beginning());
			ps.setString(3, au.getUniversity_year_ending());
			if(au.getUniversity_year_id() != 0) ps.setInt(4, au.getUniversity_year_id());
			return ps.executeUpdate() > 0 ? true : false;
		}
		});
		rtn.put("status", save ? 1 : 0);
		rtn.put("message", save ? "Enregistré avec succès" : "Echec de l'enregistrement! Veuillez réessayer.");
		return rtn;
	}

	@Override
	public JSONObject deleteUnivYear(int id) {
		JSONObject rtn = new JSONObject();
		if(this.checkDataEvalExist("au", id)) {
			rtn.put("status", -1);
		    rtn.put("message", "Vous ne pouvez pas supprimer cette année universitaire! Des étudiants ont des évaluations enregistrés dans la base dans cette année.");
		    return rtn;
		}
		boolean res = true;
		String sqlDeletePeriodExam = "DELETE Periode.*, Examen.* from Periode, Examen WHERE Examen.per_id=Periode.per_id AND Periode.au_id = ? ";
		String sqlDeleteEtudiant = "DELETE FROM Niveau_Etudiant WHERE au_id = ?";
		String sqlDelete = "DELETE FROM Annee_Universitaire WHERE au_id = ?";
		res = jdbcTemplate.execute (sqlDeletePeriodExam, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, id);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		if(!res) {
			rtn.put("status", 0);
		    rtn.put("message", "Echec de la suppression des examens et ou des périodes dans l'année universitaire! Veuillez réessayer");
		    return rtn;
		}
		res = jdbcTemplate.execute (sqlDeleteEtudiant, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, id);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		if(!res) {
			rtn.put("status", 0);
		    rtn.put("message", "Echec de la suppression des étudiants dans l'année universitaire! Veuillez réessayer");
		    return rtn;
		}
		res = jdbcTemplate.execute (sqlDelete, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, id);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		rtn.put("status", res ? 1 : 0);
	    rtn.put("message", res ? "Suppression réussie" : "Echec de la suppression de l'année universitaire! Veuillez réessayer");
		return rtn;
	}

	@Override
	public UniversityYear getUnivYearById(int id) {
		String sql = "select * from Annee_Universitaire where au_id = "+id;
		List<UniversityYear> aus = jdbcTemplate.query(sql, new AUMapper());
		return aus.size() > 0 ? aus.get(0) : null;
	}

	@Override
	public List<Level> getAllLevels() {
		String sql = "select * from Niveau order by niv_libelle asc";
		List<Level> levels = jdbcTemplate.query(sql, new LevelMapper(dataSource));
		System.out.print(levels);
		return levels;
	}

	@Override
	public List<Period> getNiveauPeriodsById(int id, int au) {
		String sql = "SELECT Periode.*, "
						+ "(select exam_libelle from Examen where per_id = Periode.per_id and exam_sessiontype = 1) as exam_libelle, "
						+ "(select exam_datedebut from Examen where per_id = Periode.per_id and exam_sessiontype = 1) as exam_debut, "
						+ "(select exam_datefin from Examen where per_id = Periode.per_id and exam_sessiontype = 1) as exam_fin, "
						+ "(select exam_libelle from Examen where per_id = Periode.per_id and exam_sessiontype = 2) as rattr_libelle, "
						+ "(select exam_datedebut from Examen where per_id = Periode.per_id and exam_sessiontype = 2) as rattr_debut, "
						+ "(select exam_datefin from Examen where per_id = Periode.per_id and exam_sessiontype = 2) as rattr_fin "
						+ "from Periode "
						+ "where niv_id = "+ id +" and au_id = "+au
						+ " order by per_libellecourt";
		List<Period> periods = jdbcTemplate.query(sql, new PeriodMapper());
		return periods;
	}

	@Override
	public JSONObject savePeriod(Period period) {
		JSONObject rtn = new JSONObject();
		String request_period = period.getPeriod_id() == 0 ? "INSERT INTO Periode(au_id, niv_id, per_libellecourt, per_libellelong, per_debut, per_fin, per_aratrappage) values(:au, :niv, :libcourt, :liblong, :perdeb, :perfin, :rattr)"
															: "UPDATE Periode SET au_id = :au, niv_id = :niv, per_libellecourt = :libcourt, per_libellelong = :liblong, per_debut = :perdeb, per_fin = :perfin, per_aratrappage = :rattr WHERE per_id = :perId";
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("au", period.getUniversity_year_id())
				.addValue("niv", period.getLevel_id())
				.addValue("libcourt", period.getPeriod_libellecourt())
				.addValue("liblong", period.getPeriod_libellelong())
				.addValue("perdeb", period.getPeriod_debut())
				.addValue("perfin", period.getPeriod_fin())
				.addValue("rattr", period.isA_ratrappage() ? 1 : 0);
		if(period.getPeriod_id() != 0) {
			parameters = new MapSqlParameterSource()
					.addValue("au", period.getUniversity_year_id())
					.addValue("niv", period.getLevel_id())
					.addValue("libcourt", period.getPeriod_libellecourt())
					.addValue("liblong", period.getPeriod_libellelong())
					.addValue("perdeb", period.getPeriod_debut())
					.addValue("perfin", period.getPeriod_fin())
					.addValue("rattr", period.isA_ratrappage() ? 1 : 0)
					.addValue("perId", period.getPeriod_id());
		}
		int res = namedJdbcTemplate.update(request_period, parameters, holder);
		if(res <= 0) {
			rtn.put("status", 0);
			rtn.put("message", "Echec de l'insertion de la période dans la base!");
			return rtn;
		}
		int periodid = 0;
		if(period.getPeriod_id() == 0) periodid = holder.getKey().intValue();
		String request_exam = period.getPeriod_id() == 0 ? "INSERT INTO Examen(per_id, exam_libelle, exam_sessiontype, exam_datedebut, exam_datefin) values(:per, :lib, 1, :debut, :fin)" 
														: "UPDATE Examen SET exam_libelle = :lib, exam_datedebut = :debut, exam_datefin = :fin WHERE per_id = :per AND exam_sessiontype = 1";
		parameters = new MapSqlParameterSource()
				.addValue("per", period.getPeriod_id() == 0 ? periodid : period.getPeriod_id())
				.addValue("lib", period.getExam_libelle())
				.addValue("debut", period.getExam_debut())
				.addValue("fin", period.getExam_fin());
		res = namedJdbcTemplate.update(request_exam, parameters);
		if(res <= 0) {
			rtn.put("status", 0);
			rtn.put("message", "Echec de l'insertion de l'examen dans la base!");
			return rtn;
		}
		// System.out.print("2nd execution "+res);
		if(period.isA_ratrappage()) {
			if(period.getPeriod_id() != 0) {
				String sqlDeleteExam = "DELETE FROM Examen WHERE per_id = ? and exam_sessiontype = 2";
				boolean b = jdbcTemplate.execute (sqlDeleteExam, new PreparedStatementCallback<Boolean>() {
					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ps.setInt(1, period.getPeriod_id());
						return ps.executeUpdate() >= 0 ? true : false;
					}
				});
				if(!b) {
					rtn.put("status", 0);
					rtn.put("message", "Echec du traitement du rattrapage!");
					return rtn;
				}
			}
			String request_rattr = "INSERT INTO Examen(per_id, exam_libelle, exam_sessiontype, exam_datedebut, exam_datefin) values(:per, :lib, 2, :debut, :fin)";
			parameters = new MapSqlParameterSource()
					.addValue("per", period.getPeriod_id() == 0 ? periodid : period.getPeriod_id())
					.addValue("lib", period.getRattr_libelle())
					.addValue("debut", period.getRattr_debut())
					.addValue("fin", period.getRattr_fin());
			namedJdbcTemplate.update(request_rattr, parameters);
			// System.out.print("3rd execution "+res);
			if(res <= 0) {
				rtn.put("status", 0);
				rtn.put("message", "Echec de l'insertion du rattrapage dans la base!");
				return rtn;
			}
		}
		rtn.put("status", 1);
		rtn.put("message", "Succès de l'enregistrement!");
		return rtn;
	}
	
	@Override
	public JSONObject deletePeriod(int id) {
		JSONObject rtn = new JSONObject();
		if(this.checkDataEvalExist("periode", id)) {
			rtn.put("status", -1);
		    rtn.put("message", "Vous ne pouvez pas supprimer cette période! Des étudiants ont des évaluations enregistrés dans la base dans cette période.");
		    return rtn;
		}
		boolean res = true;
		String sqlDeletePeriodExam = "DELETE FROM Examen WHERE Examen.per_id = ? ";
		String sqlDelete = "DELETE FROM Periode WHERE per_id = ?";
		res = jdbcTemplate.execute (sqlDeletePeriodExam, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, id);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		if(!res) {
			rtn.put("status", 0);
		    rtn.put("message", "Echec de la suppression des examens dans la période! Veuillez réessayer");
		    return rtn;
		}
		res = jdbcTemplate.execute (sqlDelete, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, id);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		rtn.put("status", res ? 1 : 0);
	    rtn.put("message", res ? "Suppression période réussie" : "Echec de la suppression de l'année universitaire! Veuillez réessayer");
		return rtn;
	}

	@Override
	public Period getPeriodById(int idPeriod) {
		String sql = "SELECT Periode.*, "
				+ "(select exam_libelle from Examen where per_id = Periode.per_id and exam_sessiontype = 1) as exam_libelle, "
				+ "(select exam_datedebut from Examen where per_id = Periode.per_id and exam_sessiontype = 1) as exam_debut, "
				+ "(select exam_datefin from Examen where per_id = Periode.per_id and exam_sessiontype = 1) as exam_fin, "
				+ "(select exam_libelle from Examen where per_id = Periode.per_id and exam_sessiontype = 2) as rattr_libelle, "
				+ "(select exam_datedebut from Examen where per_id = Periode.per_id and exam_sessiontype = 2) as rattr_debut, "
				+ "(select exam_datefin from Examen where per_id = Periode.per_id and exam_sessiontype = 2) as rattr_fin "
				+ "from Periode "
				+ "where per_id = "+ idPeriod;
		List<Period> periods = jdbcTemplate.query(sql, new PeriodMapper());
		return periods.size() > 0 ? periods.get(0) : null;
	}
	
	private boolean checkDataEvalExist(String type, int idType) {
		String sql ;
		switch(type) {
			case "periode":
				sql = "SELECT COUNT(*) from Evaluation_Etudiant WHERE per_id = "+idType;
				break;
			default:
				sql = "SELECT COUNT(*) from Evaluation_Etudiant WHERE per_id in (SELECT per_id from Periode where au_id = "+idType+")";
				break;
		}
		int evalNumber = jdbcTemplate.queryForObject(sql, Integer.class);
		return evalNumber > 0;
	}
	
}

class AUMapper implements RowMapper<UniversityYear> {
	public UniversityYear mapRow(ResultSet rs, int arg1) throws SQLException {
	    UniversityYear AU = new UniversityYear();
	    AU.setUniversity_year_id(rs.getInt("au_id"));
	    AU.setUniversity_year_libelle(rs.getString("au_libelle"));
	    AU.setUniversity_year_beginning( rs.getString("au_debut"));
	    AU.setUniversity_year_ending(rs.getString("au_fin"));
	    return AU;
	}
}

class PeriodMapper implements RowMapper<Period> {

	@Override
	public Period mapRow(ResultSet rs, int rowNum) throws SQLException {
		Period period = new Period();
		period.setPeriod_id(rs.getInt("per_id"));
		period.setPeriod_libellecourt(rs.getString("per_libellecourt"));
		period.setPeriod_libellelong(rs.getString("per_libellelong"));
		period.setPeriod_debut(rs.getString("per_debut"));
		period.setPeriod_fin(rs.getString("per_fin"));
		period.setExam_libelle(rs.getString("exam_libelle"));
		period.setExam_debut(rs.getString("exam_debut"));
		period.setExam_fin(rs.getString("exam_fin"));
		period.setRattr_libelle(rs.getString("rattr_libelle"));
		period.setRattr_debut(rs.getString("rattr_debut"));
		period.setRattr_fin(rs.getString("rattr_fin"));
		period.setA_ratrappage(rs.getInt("per_aratrappage") == 1);
		return period;
	}
	
}
