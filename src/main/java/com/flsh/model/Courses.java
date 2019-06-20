package com.flsh.model;

public class Courses{  
	private int course_id;
	private int studyunit_id;
	private int professor_id;
	private String course_libelle;
	private int course_credit;
	private int course_notation;
	private double course_coefficient;
	private String course_volumehoraire;
	private double course_travailpresenciel;
	private double course_travailpersonnel;
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
	public String getCourse_volumehoraire() {
		return course_volumehoraire;
	}
	public void setCourse_volumehoraire(String course_volumehoraire) {
		this.course_volumehoraire = course_volumehoraire;
	}
	public double getCourse_travailpresenciel() {
		return course_travailpresenciel;
	}
	public void setCourse_travailpresenciel(double course_travailpresenciel) {
		this.course_travailpresenciel = course_travailpresenciel;
	}
	public double getCourse_travailpersonnel() {
		return course_travailpersonnel;
	}
	public void setCourse_travailpersonnel(double course_travailpersonnel) {
		this.course_travailpersonnel = course_travailpersonnel;
	}
	
}
