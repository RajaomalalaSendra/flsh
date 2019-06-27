package com.flsh.interfaces;

import java.util.List;

import org.json.JSONObject;

import com.flsh.model.Student;

public interface StudentService {
	
	public List<Student> getAllStudents();
	
	public Student getStudentById(int idStudent);

	public JSONObject saveStudent(Student student);
	
	public JSONObject saveStudent(Student student, int uy, int level, int prc, int paid);
	
	public JSONObject deleteStudent(int idStudent);

}
