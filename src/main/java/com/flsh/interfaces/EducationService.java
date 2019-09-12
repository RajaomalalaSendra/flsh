package com.flsh.interfaces;

import java.util.HashSet;

import org.json.JSONObject;

import com.flsh.model.Cycles;

public interface EducationService {
	
	public HashSet<Cycles> getAllCycle();
	
	public boolean saveCycle(String libelle, int id);
	
	public boolean saveLevel(String libelle, int cycleId, int id);
	
	public boolean saveParcours(String libelle, int levelId, int id);

	public JSONObject deleteCycle(int id);
	
	public JSONObject deleteLevel(int id);
	
	public JSONObject deleteParcours(int id);
}
