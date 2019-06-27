package com.flsh.interfaces;


import java.util.HashSet;

import com.flsh.model.StudyUnits;

public interface TeachingService {
	HashSet<StudyUnits> getAllUnits();
	StudyUnits detailsUE(int id);
}
