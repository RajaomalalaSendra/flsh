package com.flsh.model;

import java.util.HashMap;

public class EvaluationCourseStudent {
	private int course_id;
	private int studyunit_id;
	private int professor_id;
	private String course_libelle;
	private int course_credit;
	private int course_credit_obtenu;
	private int course_notation;
	private double course_coefficient;
	private String professor;
	private HashMap<String, String> periodicalEvaluations;
	
	public HashMap<String, String> getPeriodicalEvaluations() {
		return periodicalEvaluations;
	}
	public void setPeriodicalEvaluations(HashMap<String, String> periodicalEvaluations) {
		this.periodicalEvaluations = periodicalEvaluations;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	public int getStudyunit_id() {
		return studyunit_id;
	}
	public void setStudyunit_id(int studyunit_id) {
		this.studyunit_id = studyunit_id;
	}
	public int getProfessor_id() {
		return professor_id;
	}
	public void setProfessor_id(int professor_id) {
		this.professor_id = professor_id;
	}
	public String getCourse_libelle() {
		return course_libelle;
	}
	public void setCourse_libelle(String course_libelle) {
		this.course_libelle = course_libelle;
	}
	public int getCourse_credit() {
		return course_credit;
	}
	public void setCourse_credit(int course_credit) {
		this.course_credit = course_credit;
	}
	public int getCourse_notation() {
		return course_notation;
	}
	public void setCourse_notation(int course_notation) {
		this.course_notation = course_notation;
	}
	public double getCourse_coefficient() {
		return course_coefficient;
	}
	public void setCourse_coefficient(double course_coefficient) {
		this.course_coefficient = course_coefficient;
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public String getPeriodNoteBySessionTypeAndId(int sessionType, int idPeriod) {
		try {
			return periodicalEvaluations.get(idPeriod + "_" + sessionType);
		} catch(Exception e) {
			return "";
		}
	}
	public int getCourse_credit_obtenu() {
		return course_credit_obtenu;
	}
	public void setCourse_credit_obtenu(int course_credit_obtenu) {
		this.course_credit_obtenu = course_credit_obtenu;
	}
}
