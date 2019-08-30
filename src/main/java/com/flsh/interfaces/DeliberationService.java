package com.flsh.interfaces;

import java.util.List;

import org.json.JSONObject;

import com.flsh.model.EvaluationUEECStudent;
import com.flsh.model.UniversityYear;

public interface DeliberationService {

	UniversityYear getDetailUnivYear(int univYearId);

	List<EvaluationUEECStudent> getInfosEvaluationsByStudentLevelUnivYearAndParcours(int univYearId, int idStudent, int idLevel, int idPrc);

	JSONObject saveMoyenneUE(int idStudent, int idUE, int idPeriod, float moyenneUE, int typeSession);

	JSONObject saveCreditECAndUE(int idEC, int idUE, int creditEC, int creditUE, int idStudent);

	JSONObject saveValidCreditUE(int valValid, int idStudent, int idUE);

	JSONObject saveDeliberationDecision(int delibDecision, int idStudent, int idLevel, int idAU, int idParcours, String passage);

	String getDelibDecisionCurrentUser(int univYearId, int idLevel, int idStudent);
	
}
