package com.flsh.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.flsh.model.Course;
import com.flsh.model.EvaluationUEECStudent;
import com.flsh.model.PeriodLibelle;
import com.flsh.model.TotalCredit;
import com.flsh.model.UniversityYear;

public interface DeliberationService {

	UniversityYear getDetailUnivYear(int univYearId);

	List<EvaluationUEECStudent> getInfosEvaluationsByStudentLevelUnivYearAndParcours(int univYearId, int idStudent, int idLevel, int idPrc);
	
}
