package com.flsh.interfaces;

import java.util.List;

import org.json.JSONObject;

import com.flsh.model.Professor;
import com.flsh.model.ProfessorStudyUnit;


public interface ProfessorService {
	
	List<Professor> getAllProfessor();
	
	Professor getProfessorDetails(int id);
	
	JSONObject saveProfessor(Professor professor);
	
	JSONObject deleteProfessor(int id, int user_id);
	
	List<ProfessorStudyUnit> getAllProfessorUnitStudy();

	List<String> getListProfUntStd();
}
