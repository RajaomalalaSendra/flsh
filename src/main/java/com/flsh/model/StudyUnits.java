package com.flsh.model;

public class StudyUnits {
	private int studyunits_id;
	private int parcours_id;
	private String studyunits_libelle;
	private String studyunits_type;
	
	public int getStudyUnitsId() {
		return this.studyunits_id;
	}
	public void setStudyUnitsId(int studyunits_id) {
		this.studyunits_id = studyunits_id;
	}
	public int getParcoursId() {
		return this.parcours_id;
	}
	public void setParcourId(int parcours_id) {
		this.parcours_id = parcours_id;
	}
	public String getStudyunitsLibelle() {
		return this.studyunits_libelle;
	}
	public void setStudyunitsLibelle(String studyunits_libelle) {
		this.studyunits_libelle = studyunits_libelle;
	}
	public String getStudyunitsType() {
		return this.studyunits_type;
	}
	public void setStudyunitsType(String studyunits_type) {
		this.studyunits_type = studyunits_type;
	}
}