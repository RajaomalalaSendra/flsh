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
	public int getId() {
		return this.course_id;
	}
	public void setId(int course_id) {
		this.course_id = course_id;
	}
	public int getStudyUnitId() {
		return this.studyunit_id;
	}
	public void setStudyUnitId(int studyunit_id) {
		this.studyunit_id = studyunit_id;
	}
	public int getProfessorId() {
		return this.professor_id;
	}
	public void setProfessorId(int professor_id) {
		this.professor_id = professor_id;
	}
	public String getCourseLibelle() {
		return this.course_libelle;
	}
	public void setCourseLibelle(String course_libelle) {
		this.course_libelle = course_libelle;
	}
	public int getCourseCredit() {
		return this.course_credit;
	}
	public void setCourseCredit(int course_credit) {
		this.course_credit = course_credit;
	}
	public int getCourseNotation(){
		return this.course_notation;
	}
	public void setCourseNotation(int course_notation){
		this.course_notation = course_notation;
	}
	public double getCourseCoefficient() {
		return this.course_coefficient;
	}
	public void setCourseCoefficient(double course_coefficient) {
		this.course_coefficient = course_coefficient;
	}
	public String getCourseVolumehoraire() {
		return this.course_volumehoraire;
	}
	public void setCourseVolumehoraire(String course_volumehoraire) {
		this.course_volumehoraire = course_volumehoraire;
	}
	public double getCourseTravailpresenciel() {
		return this.course_travailpresenciel;
	}
	public void setCourseTravailpresentiel(double course_travailpresenciel) {
		this.course_travailpresenciel = course_travailpresenciel;
	}
	public double getCourseTravailpersonnel() {
		return this.course_travailpersonnel;
	}
	public void setCourseTravailpersonnel(double course_travailpersonnel) {
		this.course_travailpersonnel = course_travailpersonnel;
	}
}
