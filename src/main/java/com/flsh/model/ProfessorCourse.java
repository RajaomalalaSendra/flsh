package com.flsh.model;

public class ProfessorCourse {
	private String course_libelle;
	private String level_libelle;
	private String parcours_libelle;
	private String study_unit_libelle;
	
	public String getCourse_libelle() {
		return course_libelle;
	}
	
	public void setCourse_libelle(String course_libelle) {
		this.course_libelle = course_libelle;
	}
	
	public String getLevel_libelle() {
		return level_libelle;
	}
	
	public void setLevel_libelle(String level_libelle) {
		this.level_libelle = level_libelle;
	}
	
	public String getParcours_libelle() {
		return parcours_libelle;
	}
	
	public void setParcours_libelle(String parcours_libelle) {
		this.parcours_libelle = parcours_libelle;
	}
	
	public String getStudy_unit_libelle() {
		return study_unit_libelle;
	}
	
	public void setStudy_unit_libelle(String study_unit_libelle) {
		this.study_unit_libelle = study_unit_libelle;
	}
	
	public String getProfessorCourse() {
		return "Professeur";
	}
	
}
