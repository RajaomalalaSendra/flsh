package com.flsh.model;

public class EvaluationCourseStudent {
	private int course_id;
	private int studyunit_id;
	private int professor_id;
	private String course_libelle;
	private int course_credit;
	private int course_notation;
	private double course_coefficient;
	private String professor;
	private String periodicalEvaluations;
	
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
	public String getPeriodicalEvaluations() {
		return periodicalEvaluations;
	}
	public void setPeriodicalEvaluations(String periodicalEvaluations) {
		this.periodicalEvaluations = periodicalEvaluations;
	}
	
}
