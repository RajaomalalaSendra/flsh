package com.flsh.interfaces;


import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;

import com.flsh.model.StudyUnit;
import com.flsh.model.Course;
import com.flsh.model.Parcours;
import com.flsh.model.Professor;
import com.flsh.model.ProfessorStudyUnit;

public interface TeachingService {
	HashSet<StudyUnit> getAllUnits();
	
	List<StudyUnit> getALLUnits();
	
	List<Parcours> getAllParcours();
	
	JSONObject saveStudyUnit(StudyUnit studyUnit, String profResponsable);
	
	JSONObject saveProfessorStudyUnit(ProfessorStudyUnit professor_study_unit);
	
	StudyUnit getUeDetails(int id);
	
	JSONObject deleteStudyUnit(int id);
	
	JSONObject saveCourse(Course course);
	
	Course getEcDetails(int id);
	
	List<ProfessorStudyUnit> getProfessorById(int ue);
	
	List<Professor> getAllProfessor();
	
	JSONObject deleteCourse(int id);

	HashSet<StudyUnit> getUnitsByParcours(int idParcours);

	HashSet<StudyUnit> getUnitsByParcoursWithPeriods(int idParcours, int idUY, int idLevel);

	JSONObject saveCoursePeriod(int idEC, int idPer, String add);
}
