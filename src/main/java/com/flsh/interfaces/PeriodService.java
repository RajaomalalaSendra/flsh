package com.flsh.interfaces;

import java.util.List;

import org.json.JSONObject;

import com.flsh.model.Cycles;
import com.flsh.model.Level;
import com.flsh.model.NumberStudent;
import com.flsh.model.Parcours;
import com.flsh.model.Period;
import com.flsh.model.UniversityYear;

public interface PeriodService {
	public List<UniversityYear> getAllUnivYears();
	
	public JSONObject saveUnivYear(UniversityYear au);
	
	public JSONObject deleteUnivYear(int id);
	
	public UniversityYear getUnivYearById(int id);
	
	public List<Level> getAllLevels();
	
	public List<Period> getNiveauPeriodsById(int id, int idAU);
	
	public JSONObject savePeriod(Period period);
	
	public JSONObject deletePeriod(int id);
	
	public Period getPeriodById(int idPeriod);
	
	public List<Parcours> getParcoursByLevelId(int idLevel);

	public Level getLevelById(int idLevel);
	
	public Parcours getParcoursByAUAndLevelAndStudentId(int idAU, int idLevel, int idStudent);

	public int getIdLevelByIdStudentAndIdUY(int idStudent, int idUY);

	public Cycles getCyclesById(int idCycle);

	int getIdCycleByIdStudentAndIdUY(int idStudent, int idUY);

	public String getCategoryName(int idCategory);

	public int getIdCycleByIdLevel(int idLevel);
	
	public int getStudentNumberByUYIdAndByLevel(int univYear, String level);

	public List<Cycles> getCycleLevelsWithNumber();
}
