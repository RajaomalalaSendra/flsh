package com.flsh.interfaces;


import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;

import com.flsh.model.StudyUnit;
import com.flsh.model.Course;
import com.flsh.model.Parcours;
import com.flsh.model.Professor;

public interface TeachingService {
	HashSet<StudyUnit> getAllUnits();
	List<Parcours> getAllParcours();
	JSONObject saveStudyUnit(StudyUnit studyUnit);
	StudyUnit getUeDetails(int id);
	JSONObject deleteStudyUnit(int id);
	JSONObject saveCourse(Course course);
	Course getEcDetails(int id);
	List<Professor> getAllProfessor();
	JSONObject deleteCourse(int id);
}
