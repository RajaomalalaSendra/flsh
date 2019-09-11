package com.flsh.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.ArrayList;
import java.util.List;

import com.flsh.model.Professor;
import com.flsh.model.ProfessorStudyUnit;
import com.mysql.jdbc.Statement;
import com.flsh.interfaces.ProfessorService;
public class ProfessorServiceImpl implements ProfessorService {
	
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ProfessorServiceImpl(DataSource dsrc) {
		jdbcTemplate = new JdbcTemplate(dsrc);
	}

	@Override
	public List<Professor> getAllProfessor() {
		String sql_prof = "SELECT * FROM  Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id   WHERE Utilisateur.uti_type = 2 LIMIT 50";		
		List<Professor> professors = jdbcTemplate.query(sql_prof, new ProfessorMapper());
		return professors;
	}

	@Override
	public List<Professor> getProfessorsByPage(int pageNumber) {
		String sql_prof = "SELECT * FROM  Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id   WHERE Utilisateur.uti_type = 2 LIMIT 50 OFFSET "+((pageNumber - 1) * 100 );		
		List<Professor> professors = jdbcTemplate.query(sql_prof, new ProfessorMapper());
		return professors;
	}

	@Override
	public List<Professor> searchProfessorsByPage(String criteria, int numPage) {
		String queryIntro = "SELECT *, (SELECT COUNT(*) ";
		String querySearch = "FROM Professeur  JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id ";
		if(!criteria.equals("")) {
			if(criteria.matches("-?\\d+(\\.\\d+)?")) {
				querySearch += "WHERE prof_contact LIKE '%"+criteria+"%' ";
			} else {
				String[] tmpCriteria = criteria.split(" ");
				switch(tmpCriteria.length) {
					case 2:
						querySearch += "WHERE ( uti_nom LIKE '%"+tmpCriteria[0]+"%' AND uti_prenom LIKE '%"+tmpCriteria[1]+"%')";
						querySearch += " OR ( uti_nom LIKE '%"+tmpCriteria[1]+"%' AND uti_prenom LIKE '%"+tmpCriteria[0]+"%')";
						querySearch += " OR uti_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR uti_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR prof_adresse LIKE '%"+criteria+"%' ";
						break;
					case 3:
						querySearch += "WHERE ( uti_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+"%' AND uti_prenom LIKE '%"+tmpCriteria[2]+"%')";
						querySearch += " OR ( uti_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND uti_nom LIKE '%"+tmpCriteria[0]+"%')";
						querySearch += " OR ( uti_nom LIKE '%"+tmpCriteria[2]+" "+tmpCriteria[1]+"%' AND uti_prenom LIKE '%"+tmpCriteria[0]+"%')";
						querySearch += " OR ( uti_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[0]+"%' AND uti_nom LIKE '%"+tmpCriteria[2]+"%')";
						querySearch += " OR uti_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR uti_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR prof_adresse LIKE '%"+criteria+"%' ";
						break;
					case 4:
						querySearch += "WHERE ( uti_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+"%' AND uti_prenom LIKE '%"+tmpCriteria[2]+" "+tmpCriteria[3]+"%')";
						querySearch += " OR ( uti_prenom LIKE '%"+tmpCriteria[2]+" "+tmpCriteria[3]+"%' AND uti_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+"%')";
						querySearch += " OR ( uti_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[2]+"%' AND uti_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[0]+"%')";
						querySearch += " OR ( uti_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[0]+"%' AND uti_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[2]+"%')";
						querySearch += " OR ( uti_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[0]+"%' AND uti_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[2]+"%')";
						querySearch += " OR ( uti_prenom LIKE '%"+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND uti_nom LIKE '%"+tmpCriteria[3]+" "+tmpCriteria[0]+"%')";
						querySearch += " OR ( uti_nom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND uti_prenom LIKE '%"+tmpCriteria[3]+"%')";
						querySearch += " OR ( uti_prenom LIKE '%"+tmpCriteria[0]+" "+tmpCriteria[1]+" "+tmpCriteria[2]+"%' AND uti_nom LIKE '%"+tmpCriteria[3]+"%')";
						querySearch += " OR uti_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR uti_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR prof_adresse LIKE '%"+criteria+"%' ";
						break;
					default:
						querySearch += "WHERE uti_nom LIKE '%"+criteria+"%' ";
						querySearch += " OR uti_prenom LIKE '%"+criteria+"%' ";
						querySearch += " OR prof_adresse LIKE '%"+criteria+"%' ";
						break;
				}
			}
		}
		String query = queryIntro +" "+querySearch+") as maxnumber "+querySearch+" limit 50 offset "+ (50 * (numPage - 1));
		List<Professor> professors = jdbcTemplate.query(query, new ProfessorMapper());
		return professors;
	}

	@Override
	public Professor getProfessorDetails(int id) {
		String sql = "SELECT * FROM Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id WHERE prof_id = " + id ;
		List<Professor> professors = jdbcTemplate.query(sql, new ProfessorMapper());
		return professors.size() > 0 ? professors.get(0) : null;
	}
	
	@Override
	public int getProfsNumber() {
		String sql = "SELECT COUNT(*) FROM  Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id   WHERE Utilisateur.uti_type = 2 ";
		int profsNumber = jdbcTemplate.queryForObject(sql, Integer.class);
		return profsNumber;
	}
	
	private boolean checkUsernameExists(String username, int uti_id) {
		String sql = "SELECT * FROM Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id WHERE Utilisateur.uti_login = '"+username+"'";
		if(uti_id != 0) {
			sql += " and Professeur.uti_id != " + uti_id;
		}
		List<Professor> prof = jdbcTemplate.query(sql, new ProfessorMapper());
		return prof.size() > 0;
	}
	private boolean checkEmailExists(String email, int uti_id) {
		String sql = "SELECT * FROM Professeur JOIN Utilisateur ON Utilisateur.uti_id = Professeur.uti_id WHERE Utilisateur.uti_email = '"+email+"'";
		if(uti_id != 0) {
			sql += " and Professeur.uti_id != " + uti_id;
		}
		List<Professor> prof = jdbcTemplate.query(sql, new ProfessorMapper());
		return prof.size() > 0;
	}

	@Override
	public JSONObject saveProfessor(Professor professor) {
		// TODO Auto-generated method stub
		JSONObject rtn = new JSONObject();
		if(this.checkUsernameExists(professor.getProfessor_name(), professor.getUser_id())) {
			rtn.put("status", 0);
	  	    rtn.put("message", professor.getUser_id() == 0 ? "Echec de l'enregistrement! Le nom de professeur existe" : "Le nom de professeur déjà utilisé par un autre utilisateur");
			return rtn;
		}
		if (this.checkEmailExists(professor.getProfessor_email(), professor.getUser_id())) {
			rtn.put("status", 0);
	  	    rtn.put("message", professor.getUser_id() == 0 ? "Echec de l'enregistrement! L'email que vous avez entrez existe deja" : "L'email que vous avez entrez est déjà utilisé par un autre utilisateur");
			return rtn;
		}
		
		// the sql for the utilisateur table
		String sql_user;
		if (professor.getUser_id() == 0) {
			sql_user = "INSERT INTO Utilisateur(uti_nom, uti_prenom, uti_login, uti_email, uti_type, civ_id, uti_passwd) VALUES(?, ?, ?, ?, ?, ?, sha1(?))";
		} else {
			sql_user = professor.getProfessor_password().equals("") ? "UPDATE Utilisateur SET  uti_nom = ?, uti_prenom = ?, uti_login = ?, uti_email = ?, uti_type = ?, civ_id = ? WHERE uti_id = ?" : "UPDATE Utilisateur SET  uti_nom = ?, uti_prenom = ?, uti_login = ?, uti_email = ?, uti_type = ?, civ_id = ?, uti_passwd = sha1(?) WHERE uti_id = ?";
		}
		
		System.out.println(" Professor Id: " + professor.getProfessor_id());
		System.out.println("||||||||User Type: " + professor.getUser_type()+"|||||||||||");
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
		    @Override
		    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		        PreparedStatement statement = con.prepareStatement(sql_user, Statement.RETURN_GENERATED_KEYS);
		        statement.setString(1, professor.getProfessor_last_name());
		        statement.setString(2, professor.getProfessor_name());
		        statement.setString(3, professor.getProfessor_login());
		        statement.setString(4, professor.getProfessor_email());
		        statement.setInt(5, professor.getUser_type());
		        statement.setInt(6, professor.getUser_civilite());
		        if(!professor.getProfessor_password().equals("")) statement.setString(7, professor.getProfessor_password());
		        if (professor.getUser_id() != 0) statement.setInt( professor.getProfessor_password().equals("") ? 7 : 8, professor.getUser_id());
		        return statement;
		    }
		}, holder);
        // get the key of the utilisateur table
		int uti_id_key;
		if (professor.getUser_id() == 0) {
			uti_id_key = holder.getKey().intValue();
		} else {
			uti_id_key = professor.getUser_id();
		}
		System.out.println(" Uti User Key: " + uti_id_key);
        // the sql for the professeur table
		String sql_prof = professor.getProfessor_id() == 0 ? "INSERT INTO Professeur(prof_adresse, prof_contact, uti_id) values( ?, ?, ?)" : 
										"UPDATE Professeur SET  prof_adresse = ?, prof_contact = ?, uti_id = ? WHERE prof_id = ?";
		boolean save_prof = jdbcTemplate.execute (sql_prof, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, professor.getProfessor_adresse());
				ps.setString(2, professor.getProfessor_contact());
				ps.setInt(3, uti_id_key);
				if (professor.getProfessor_id() != 0) ps.setInt(4, professor.getProfessor_id());
				return ps.executeUpdate() > 0 ? true : false;
			}
		});
		rtn.put("status", save_prof ? 1 : 0);
  	    rtn.put("message", save_prof ? "Enregistré avec succès" : "Echec de l'enregistrement! Veuillez réessayer");
		return rtn;
	}

	@Override
	public JSONObject deleteProfessor(int id, int user_id) {
		// TODO Auto-generated method stub
		String sql_professor = "DELETE FROM Professeur WHERE Professeur.prof_id = "+ id + " AND Professeur.uti_id = ?";
		String sql_user = "DELETE FROM Utilisateur WHERE Utilisateur.uti_id = ?";
		JSONObject rtn = new JSONObject();
	    jdbcTemplate.update(sql_professor, user_id);
	    int j = jdbcTemplate.update(sql_user, user_id);
	    rtn.put("status", j >= 0 ? 1 : 0);
	    rtn.put("message", j >= 0 ? "Supprimé!" : "Echec de la suppression! Veuillez réessayer.");
		return rtn;
	}

	@Override
	public List<ProfessorStudyUnit> getAllProfessorUnitStudy() {
		// TODO Auto-generated method stub
		String sql = "SELECT *, Utilisateur.uti_nom, Utilisateur.uti_prenom, Utilisateur.civ_id, Unite_Enseignement.ue_libelle  FROM Prof_Ue "
				+ "JOIN Professeur ON Professeur.prof_id = Prof_Ue.prof_id "
				+ "JOIN Utilisateur ON Professeur.uti_id = Utilisateur.uti_id "
				+ "JOIN Unite_Enseignement ON Unite_Enseignement.ue_id = Prof_Ue.ue_id";
		List<ProfessorStudyUnit> profsUe = jdbcTemplate.query(sql, new ProfessorResponsableMapper());
		return profsUe;
	}
	
	@Override
	public List<String> getListProfUntStd(){
		List<ProfessorStudyUnit> getAllProf = getAllProfessorUnitStudy();
		List<String> allProfList = new ArrayList<String>();
		
		getAllProf.forEach(prof ->  allProfList.add(prof.getStudy_unit_id(), prof.getCivilite() + " " + prof.getProfessor_last_name() + " " + prof.getProfessor_name()));
		System.out.print("Here is: " + allProfList);
		return allProfList;
	}
}

class ProfessorMapper implements RowMapper<Professor> {
	public Professor mapRow(ResultSet rs, int arg1) throws SQLException {
		Professor prof = new Professor();
		prof.setProfessor_id(rs.getInt("prof_id"));
		prof.setProfessor_last_name(rs.getString("uti_nom"));
		prof.setProfessor_name(rs.getString("uti_prenom"));
		prof.setProfessor_email(rs.getString("uti_email"));
		prof.setProfessor_login(rs.getString("uti_login"));
		prof.setProfessor_password(rs.getString("uti_passwd"));
	    prof.setProfessor_adresse(rs.getString("prof_adresse"));
	    prof.setProfessor_contact(rs.getString("prof_contact"));
	    prof.setUser_id(rs.getInt("uti_id"));
	    prof.setUser_type(rs.getInt("uti_type"));
	    prof.setUser_civilite(rs.getInt("civ_id"));
	    try {
	    	prof.setNumber(rs.getInt("maxnumber"));
	    } catch (Exception e) {
	    	System.out.print("\n No number for pagination \n");
	    }
	    return prof;
	}

}
class ProfessorResponsableMapper implements RowMapper<ProfessorStudyUnit> {
	public ProfessorStudyUnit mapRow(ResultSet rs, int arg1) throws SQLException {
		ProfessorStudyUnit profUnitStudy = new ProfessorStudyUnit();
		profUnitStudy.setProfessor_id(rs.getInt("prof_id"));
		profUnitStudy.setStudy_unit_id(rs.getInt("ue_id"));
		profUnitStudy.setProfessor_name(rs.getString("uti_nom"));
		profUnitStudy.setProfessor_last_name(rs.getString("uti_prenom")); 
		profUnitStudy.setProfessor_civilite(rs.getInt("civ_id"));
		profUnitStudy.setStudy_unit_libelle(rs.getString("ue_libelle"));
		return profUnitStudy;
	}
}