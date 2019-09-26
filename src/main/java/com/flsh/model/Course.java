package com.flsh.model;

import java.util.List;

public class Course{  
	private int course_id;
	private int studyunit_id;
	private int professor_id;
	private String course_libellecourt;
	private String course_libellelong;
	private String course_type;
	private int course_credit;
	private int course_notation;
	private double course_coefficient;
	private String course_volumehoraire;
	private double course_travailpresenciel;
	private double course_travailpersonnel;
	private String professor;
	private String idPeriods;
	private String ueNiveau;
	
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
	public String getCourse_libellecourt() {
		return course_libellecourt;
	}
	public void setCourse_libellecourt(String course_libellecourt) {
		this.course_libellecourt = course_libellecourt;
	}
	public String getCourse_libellelong() {
		return course_libellelong;
	}
	public void setCourse_libellelong(String course_libellelong) {
		this.course_libellelong = course_libellelong;
	}
	public String getCourse_type() {
		return course_type;
	}
	public void setCourse_type(String course_type) {
		this.course_type = course_type;
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
	
	public String toString() {
		return " \n ==================================================================\n" +
				"id ec " + course_id + " id ue " + studyunit_id + " professor id " + professor_id 
				+ " libellecourt " + course_libellecourt 
				+ " libellelong " + course_libellelong
				+ " type " + course_type 
				+ " \n ==================================================================\n";
	}
	public String getProfName(int id) {
		Professor prof = new Professor();
		prof.setProfessor_id(id);
		return prof.getCivilite() + " "+ prof.getProfessor_last_name() + " " +  prof.getProfessor_name();
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public String getIdPeriods() {
		return idPeriods;
	}
	public void setIdPeriods(String idPeriods) {
		this.idPeriods = idPeriods;
	}
	public String getUeNiveau() {
		return ueNiveau;
	}
	public void setUeNiveau(String ueNiveau) {
		this.ueNiveau = ueNiveau;
	}
	
}
