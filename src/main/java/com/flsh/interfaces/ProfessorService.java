package com.flsh.interfaces;

import java.util.List;

import org.json.JSONObject;

import com.flsh.model.Professors;


public interface ProfessorService {
	List<Professors> getAllProfessor();
	Professors getProfessorDetails(int id);
	JSONObject saveProfessor(Professors professor);
}
