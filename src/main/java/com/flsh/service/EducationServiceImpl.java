package com.flsh.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import com.flsh.interfaces.EducationService;
import com.flsh.model.Cycles;
import com.flsh.model.Level;
import com.flsh.model.Parcours;

public class EducationServiceImpl implements EducationService {

	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(EducationServiceImpl.class);
	
	@Autowired
	public EducationServiceImpl(DataSource dsrc) {
		this.dataSource = dsrc;
		jdbcTemplate = new JdbcTemplate(dsrc);
	}
	
	@Override
	public HashSet<Cycles> getAllCycle() {
		HashSet<Cycles> listCycle = new HashSet<Cycles>();
		String sql = "select * from Cycle order by cyc_id desc";
		List<Cycles> cycles = jdbcTemplate.query(sql, new CycleMapper());
		logger.info("Cycles captured ");
		for (Cycles cycl : cycles) {
			Set<Level> levelsByCycleId = new HashSet<Level>( this.getLevelsByCycleId(cycl.getCyclesId()) );
			cycl.setLevels((HashSet<Level>) levelsByCycleId);
			System.out.println(cycl);
			listCycle.add(cycl);
		}
		return listCycle;
	}

	public List<Level> getLevelsByCycleId(int idCycle) {
		String sql = "select * from Niveau where cyc_id = "+idCycle+" order by niv_id desc";
		List<Level> levels = jdbcTemplate.query(sql, new LevelMapper(dataSource));
		return levels;
	}

	@Override
	public boolean saveCycle(String libelle, int id) {
		String sql = id == 0 ? "INSERT INTO Cycle(cyc_libelle) VALUES(?)" 
							: "UPDATE Cycle SET cyc_libelle = ? WHERE cyc_id = ?";
		return jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, libelle);
				if(id != 0) ps.setInt(2, id);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
	}

	@Override
	public JSONObject deleteCycle(int id) {
		// Delete querys
		String sql = "DELETE FROM Cycle WHERE cyc_id = ?";
		String sqlDeleteNiveau = "DELETE FROM Niveau WHERE cyc_id = ?";
		String sqlDeleteParcours = "DELETE FROM Parcours WHERE niv_id in ( SELECT niv_id FROM Niveau WHERE cyc_id = ? ) ";
		String sqlDeleteStudents = "DELETE FROM Niveau_Etudiant WHERE niv_id in ( SELECT niv_id FROM Niveau WHERE cyc_id = ? ) ";
		String sqlDeleteCourses = "DELETE Unite_Enseignement.*, Element_Constitutif.* from Unite_Enseignement, Element_Constitutif WHERE Element_Constitutif.ue_id=Unite_Enseignement.ue_id AND Unite_Enseignement.prc_id in (select prc_id from Parcours where niv_id in ( SELECT niv_id FROM Niveau WHERE cyc_id = ? )) ";
		
		// Computing response variables
		JSONObject rtn = new JSONObject();
		boolean res = true;
		
		//Check evaluation Data
		if(this.checkDataEvalExist("cycle", id)) {
			rtn.put("status", -1);
		    rtn.put("message", "Vous ne pouvez pas supprimer ce cycle! Des étudiants dans ce cycle ont des évaluations enregistrés dans la base.");
		    return rtn;
		}
		
		// Deleting the lower level dependency in the structure
		res = jdbcTemplate.execute (sqlDeleteCourses, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, id);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		if(res) {
			res = jdbcTemplate.execute (sqlDeleteStudents, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setInt(1, id);
					return ps.executeUpdate() >= 0 ? true : false;
				}
			});
		} else {
			rtn.put("status", res ? 1 : 0);
		    rtn.put("message", res ? "Suppression réussie!" : "Echec de la suppression des cours dans le cycle! Veuillez réessayer");
		    return rtn;
		}
		
		if(res) { // Delete the middle level dependency
			res = jdbcTemplate.execute (sqlDeleteParcours, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setInt(1, id);
					return ps.executeUpdate() >= 0 ? true : false;
				}
			});
			if(res) { // Delete the middle level dependency
				res = jdbcTemplate.execute (sqlDeleteNiveau, new PreparedStatementCallback<Boolean>() {
					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ps.setInt(1, id);
						return ps.executeUpdate() >= 0 ? true : false;
					}
				});
				
				if(res) { // Delete the specified row
					res = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {
						@Override
						public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
							ps.setInt(1, id);
							return ps.executeUpdate() >= 0 ? true : false;
						}
					});
					rtn.put("status", res ? 1 : 0);
				    rtn.put("message", res ? "Suppression réussie!" : "Echec de la suppression du cycle! Veuillez réessayer");
				} else {
					rtn.put("status", 0);
				    rtn.put("message", "Echec de la suppression des niveaux dans le cycle! Veuillez réessayer");
				}
			} else {
				rtn.put("status", 0);
			    rtn.put("message", "Echec de la suppression des parcours dans le cycle! Veuillez réessayer");
			}
		} else {
			rtn.put("status", 0);
		    rtn.put("message", "Echec de la suppression des étudiants dans le cycle! Veuillez réessayer");
		}
		return rtn;
	}

	@Override
	public boolean saveLevel(String libelle, int cycleId, int id) {
		String sql = id == 0 ? "INSERT INTO Niveau(niv_libelle, cyc_id) VALUES(?, ?)" 
				: "UPDATE Niveau SET niv_libelle = ?, cyc_id = ? WHERE niv_id = ?";
		return jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, libelle);
				ps.setInt(2, cycleId);
				if(id != 0) ps.setInt(3, id);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
	}

	@Override
	public boolean saveParcours(String libelle, int levelId, int id) {
		String sql = id == 0 ? "INSERT INTO Parcours(prc_libelle, niv_id) VALUES(?, ?)" 
				: "UPDATE Parcours SET prc_libelle = ?, niv_id = ? WHERE prc_id = ?";
		return jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, libelle);
				ps.setInt(2, levelId);
				if(id != 0) ps.setInt(3, id);
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
	}

	@Override
	public JSONObject deleteLevel(int id) {
		String sql = "DELETE FROM Niveau WHERE niv_id = ?";
		String sqlDeleteParcours = "DELETE FROM Parcours WHERE niv_id = ? ";
		String sqlDeleteStudents = "DELETE FROM Niveau_Etudiant WHERE niv_id = ? ";
		String sqlDeleteCourses = "DELETE Unite_Enseignement.*, Element_Constitutif.* from Unite_Enseignement, Element_Constitutif WHERE Element_Constitutif.ue_id=Unite_Enseignement.ue_id AND Unite_Enseignement.prc_id in (select prc_id from Parcours where niv_id = ? ) ";
		
		// Computing response variables
		JSONObject rtn = new JSONObject();
		boolean res = true;
		
		//Check evaluation Data
		if(this.checkDataEvalExist("niveau", id)) {
			rtn.put("status", -1);
		    rtn.put("message", "Vous ne pouvez pas supprimer ce niveau! Des étudiants dans ce niveau ont des évaluations enregistrés dans la base.");
		    return rtn;
		}
		
		// Deleting the lower level dependency in the structure
		res = jdbcTemplate.execute (sqlDeleteCourses, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, id);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		
		if(res) {
			res = jdbcTemplate.execute (sqlDeleteStudents, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setInt(1, id);
					return ps.executeUpdate() >= 0 ? true : false;
				}
			});
		} else {
			rtn.put("status", 0);
		    rtn.put("message", "Echec de la suppression des cours dans le niveau! Veuillez réessayer");
		    return rtn;
		}
		
		if(res) { // Delete the middle level dependency
			res = jdbcTemplate.execute (sqlDeleteParcours, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setInt(1, id);
					return ps.executeUpdate() >= 0 ? true : false;
				}
			});
			if(res) { // Delete the middle level dependency
				res = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {
					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						ps.setInt(1, id);
						return ps.executeUpdate() >= 0 ? true : false;
					}
				});
				rtn.put("status", res ? 1 : 0);
			    rtn.put("message", res ? "Suppression réussie!" : "Echec de la suppression du cycle! Veuillez réessayer");
			} else {
				rtn.put("status", 0);
			    rtn.put("message", "Echec de la suppression des parcours dans le niveau! Veuillez réessayer");
			}
		} else {
			rtn.put("status", 0);
		    rtn.put("message", "Echec de la suppression des étudiants dans le niveau! Veuillez réessayer");
		}
		return rtn;
	}

	@Override
	public JSONObject deleteParcours(int id) {
		String sql = "DELETE FROM Parcours WHERE prc_id = ?";
		String sqlDeleteStudents = "DELETE FROM Niveau_Etudiant WHERE prc_id = ? ";
		String sqlDeleteCourses = "DELETE Unite_Enseignement.*, Element_Constitutif.* from Unite_Enseignement, Element_Constitutif WHERE Element_Constitutif.ue_id=Unite_Enseignement.ue_id AND Unite_Enseignement.prc_id = ? ";
		
		// Computing response variables
		JSONObject rtn = new JSONObject();
		boolean res = true;
		
		//Check evaluation Data
		if(this.checkDataEvalExist("parcours", id)) {
			rtn.put("status", -1);
		    rtn.put("message", "Vous ne pouvez pas supprimer ce parcours! Des étudiants dans ce parcours ont des évaluations enregistrés dans la base.");
		    return rtn;
		}
		
		// Deleting the lower level dependency in the structure
		res = jdbcTemplate.execute (sqlDeleteCourses, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, id);
				return ps.executeUpdate() >= 0 ? true : false;
			}
		});
		
		if(res) {
			res = jdbcTemplate.execute (sqlDeleteStudents, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setInt(1, id);
					return ps.executeUpdate() >= 0 ? true : false;
				}
			});
		} else {
			rtn.put("status", 0);
		    rtn.put("message", "Echec de la suppression des cours dans le parcours! Veuillez réessayer");
		    return rtn;
		}
		
		if(res) { // Delete the middle level dependency
		
			// Deleting the lower level dependency in the structure
			res = jdbcTemplate.execute (sql, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setInt(1, id);
					return ps.executeUpdate() >= 0 ? true : false;
				}
			});
			rtn.put("status", res ? 1 : 0);
		    rtn.put("message", res ? "Suppression réussie" : "Echec de la suppression du parcours! Veuillez réessayer");
		} else {
			rtn.put("status", 0);
		    rtn.put("message", "Echec de la suppression des étudiants dans le parcours! Veuillez réessayer");
		}
	    return rtn;
	}
	
	private boolean checkDataEvalExist(String type, int idType) {
		String sql ;
		switch(type) {
			case "parcours":
				sql = "SELECT COUNT(*) from Evaluation_Etudiant WHERE etd_id in (SELECT etd_id from Niveau_Etudiant where prc_id = "+idType+") ";
				break;
			case "niveau":
				sql = "SELECT COUNT(*) from Evaluation_Etudiant WHERE etd_id in (SELECT etd_id from Niveau_Etudiant where niv_id = "+idType+") ";
				break;
			default:
				sql = "SELECT COUNT(*) from Evaluation_Etudiant WHERE etd_id in (SELECT etd_id from Niveau_Etudiant where niv_id in (select niv_id from Niveau where cyc_id = "+idType+")) ";
				break;
		}
		
		int evalNumber = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return evalNumber > 0;
	}
	
}

class CycleMapper implements RowMapper<Cycles> {
	public Cycles mapRow(ResultSet rs, int arg1) throws SQLException {
	    Cycles cycle = new Cycles();
	    cycle.setCycleLibelle(rs.getString("cyc_libelle"));
	    cycle.setCyclesId(rs.getInt("cyc_id"));
	    return cycle;
	}
}

class LevelMapper implements RowMapper<Level> {
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
	public LevelMapper(DataSource dsrc) {
		super();
		this.dataSource = dsrc;
		jdbcTemplate = new JdbcTemplate(dsrc);
	}
	
	@SuppressWarnings("unchecked")
	public Level mapRow(ResultSet rs, int arg1) throws SQLException {
		Level level = new Level();
		level.setCycleId(rs.getInt("cyc_id"));
		level.setLevelId(rs.getInt("niv_id"));
		level.setLevelLibelle(rs.getString("niv_libelle"));
		Set<Parcours> parcoursByLevelId = new HashSet<Parcours>( this.getParcoursByLevelId(rs.getInt("niv_id")) );
		level.setParcours((HashSet<Parcours>) parcoursByLevelId);
		return level;
	}
		
	private List<Parcours> getParcoursByLevelId(int idLevel) {
		String sql = "select * from Parcours where niv_id = "+idLevel+" order by prc_id desc";
		List<Parcours> parcours = jdbcTemplate.query(sql, new ParcoursMapper());
		return parcours;
	}
}

class ParcoursMapper implements RowMapper<Parcours> {
	public Parcours mapRow(ResultSet rs, int arg1) throws SQLException {
		Parcours prc = new Parcours();
		prc.setLevelId(rs.getInt("niv_id"));
		prc.setParcoursId(rs.getInt("prc_id"));
		try {
			int i = rs.getInt("show_niv");
			if(i == 1) {
				prc.setParcoursLibelle(rs.getString("prc_libelle") + " (" +rs.getString("niv_libelle")+")");
			} else {
				prc.setParcoursLibelle(rs.getString("prc_libelle"));
			}
		} catch (Exception e) {
			prc.setParcoursLibelle(rs.getString("prc_libelle"));
		}
		return prc;
	}
}
