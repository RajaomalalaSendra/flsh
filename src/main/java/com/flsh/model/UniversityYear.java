package com.flsh.model;

public class UniversityYear {
   // 	au_id 	au_libelle 	au_debut 	au_fin 
	private int university_year_id;
	private String university_year_libelle;
	private String university_year_beginning;
	private String university_year_ending;
	
	public int getUniversityYearId() {
		return this.university_year_id;
	}
	public void setUniversityYearId(int university_year_id) {
		this.university_year_id = university_year_id;
	}
	public String getUniversityYearLibelle() {
		return this.university_year_libelle;
	}
	public void setUniversityYearLibelle(String university_year_libelle) {
		this.university_year_libelle = university_year_libelle;
	}
	public String getUniversityYearBeginning() {
		return this.university_year_beginning;
	}
	public void setUniversityYearBeginning(String university_year_beginning) {
		this.university_year_beginning = university_year_beginning;
	}
	public String getUniversityYearEnding() {
		return this.university_year_ending;
	}
	public void setUniversityYearEnding(String university_year_ending) {
		this.university_year_ending = university_year_ending;
	}
}
