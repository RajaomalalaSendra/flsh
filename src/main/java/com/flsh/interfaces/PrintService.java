package com.flsh.interfaces;

import java.util.List;

import com.flsh.model.Student;
import com.flsh.model.StudyUnit;

public interface PrintService {
	
	List<Student> getStudentsandPartialResultsByLevelandUYandPer(int idLevel, int idUY, int idPer);
	
	List<StudyUnit> getLevelStudyUnitandCourses(int idLevel);
}
