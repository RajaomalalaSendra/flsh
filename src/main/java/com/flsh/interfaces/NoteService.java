package com.flsh.interfaces;

import java.util.List;

import org.json.JSONObject;

import com.flsh.model.Course;
import com.flsh.model.Exam;
import com.flsh.model.Student;

public interface NoteService {

	List<Course> getECProfessor(int idProf);

	List<Exam> getExamsByECandUY(int idEC, int idUY);

	List<Student> getStudentsandEvalsByECandUY(int idEC, int idUY);

	JSONObject saveMoyExamStudent(int idStudent, int idExam, int idPer, int idEC, String noteVal);

	List<Course> getECParcours(int idPrc);

}
