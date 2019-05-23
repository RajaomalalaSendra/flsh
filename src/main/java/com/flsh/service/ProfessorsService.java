package com.flsh.service;

import com.flsh.model.Login;
import com.flsh.model.Professors;


public interface ProfessorsService {
		void register_professor(Professors prof);
		Professors validateProfessor(Login login);
}
