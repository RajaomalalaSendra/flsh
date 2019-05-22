package com.flsh.model;

public class Moyenne { 
	private int moyenne_id;
	private int students_id;
	private int study_units_id;
	private String moyenne_val;
	private String moyenne_acquis;
	
	public int getMoyenneId() {
		return this.moyenne_id;
	}
	public void setMoyenneId(int moyenne_id) {
		this.moyenne_id = moyenne_id;
	}
	public int getStudentsId() {
		return this.students_id;
	}
	public void setStudentsId(int students_id) {
		this.students_id = students_id;
	}
	public int getStudyUnitsId() {
		return this.study_units_id;
	}
	public void setStudyUnitsId(int study_units_id) {
		this.study_units_id = study_units_id;
	}
	public String getMoyenneVal() {
		return this.moyenne_val;
	}
	public void setMoyenneVal(String moyenne_val) {
		this.moyenne_val = moyenne_val;
	}
	public String getMoyenneAcquis() {
		return this.moyenne_acquis;
	}
	public void setMoyenneAcquis(String moyenne_acquis) {
		this.moyenne_acquis = moyenne_acquis;
	}
}
