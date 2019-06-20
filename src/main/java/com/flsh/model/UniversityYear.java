package com.flsh.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.flsh.utils.DateUtils;

public class UniversityYear {
   // 	au_id 	au_libelle 	au_debut 	au_fin 
	private int university_year_id;
	private String university_year_libelle;
	private String university_year_beginning;
	private String university_year_ending;
	
	private DateUtils dateUtils = new DateUtils();
	
	public int getUniversity_year_id() {
		return university_year_id;
	}
	public void setUniversity_year_id(int university_year_id) {
		this.university_year_id = university_year_id;
	}
	public String getUniversity_year_libelle() {
		return university_year_libelle;
	}
	public void setUniversity_year_libelle(String university_year_libelle) {
		this.university_year_libelle = university_year_libelle;
	}
	public String getUniversity_year_beginning() {
		return university_year_beginning;
	}
	public void setUniversity_year_beginning(String university_year_beginning) {
		this.university_year_beginning = university_year_beginning;
	}
	public String getUniversity_year_ending() {
		return university_year_ending;
	}
	public void setUniversity_year_ending(String university_year_ending) {
		this.university_year_ending = university_year_ending;
	}
	public String getStart() throws ParseException {
		return dateUtils.convertToFrenchDate(university_year_beginning);
	}
	public String getEnd() throws ParseException {
		return dateUtils.convertToFrenchDate(university_year_ending);
	}
	
	public String toString() {
		return "id "+university_year_id +" libelle "+university_year_libelle+" debut "+university_year_beginning+" fin "+university_year_ending; 
	}
	
}
