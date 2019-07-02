package com.flsh.model;

import java.util.HashSet;

public class StudyUnit {
	private int studyunit_id;
	private int parcours_id;
	private String studyunit_libelle;
	private String studyunit_type;
	private HashSet<Course> Courses;
	public int getStudyunit_id() {
		return studyunit_id;
	}
	public void setStudyunit_id(int studyunit_id) {
		this.studyunit_id = studyunit_id;
	}
	public int getParcours_id() {
		return parcours_id;
	}
	public void setParcours_id(int parcours_id) {
		this.parcours_id = parcours_id;
	}
	public String getStudyunit_libelle() {
		return studyunit_libelle;
	}
	public void setStudyunit_libelle(String studyunit_libelle) {
		this.studyunit_libelle = studyunit_libelle;
	}
	public String getStudyunit_type() {
		return studyunit_type;
	}
	public void setStudyunit_type(String studyunit_type) {
		this.studyunit_type = studyunit_type;
	}
	public HashSet<Course> getCourses() {
		return Courses;
	}
	public void setCourses(HashSet<Course> courses) {
		Courses = courses;
	}
	public String toString() {
		return " \n==========================\n" + "id parcours: " + parcours_id + " id ue: " + studyunit_id + "type: " + studyunit_type + " libelle: " + studyunit_libelle + " \n==========================\n";
	}
	
}