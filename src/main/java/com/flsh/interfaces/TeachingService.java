package com.flsh.interfaces;

import java.util.List;

import com.flsh.model.StudyUnits;
import com.flsh.model.Courses;

public interface TeachingService {
	List<StudyUnits> getAllUnity();
	List<Courses> getAllComplementary();
	StudyUnits detailsUE(int id);
}
