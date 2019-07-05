package com.flsh.interfaces;

import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;

import com.flsh.model.Student;
import com.flsh.model.StudyUnit;

public interface StudentService {
	
	public List<Student> getAllStudents();
	
	public Student getStudentById(int idStudent);

	public JSONObject saveStudent(Student student);
	
	public JSONObject saveStudent(Student student, int uy, int level, int prc, int paid, String dateInscription, String choixprc);
	
	public JSONObject deleteStudent(int idStudent);

	HashSet<StudyUnit> getParcoursChoiceUnits(int idParcours);

}
