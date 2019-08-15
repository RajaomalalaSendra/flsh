package com.flsh.interfaces;

import java.util.List;

import org.json.JSONObject;

import com.flsh.model.EvaluationUEECStudent;
import com.flsh.model.UniversityYear;

public interface DeliberationService {

	UniversityYear getDetailUnivYear(int univYearId);

	List<EvaluationUEECStudent> getInfosEvaluationsByStudentLevelUnivYearAndParcours(int univYearId, int idStudent, int idLevel, int idPrc);

	JSONObject saveMoyenneUE(int idStudent, int idUE, int idPeriod, float moyenneUE, int typeSession);
	
}
