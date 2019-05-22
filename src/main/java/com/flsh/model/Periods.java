package com.flsh.model;

public class Periods {
    // per_id 	au_id 	niv_id 	per_libellecourt 	per_libellelong
	// per_debut 	per_fin 	per_aratrappage 
	private int periods_id;
	private int university_year_id;
	private int level_id;
	private String periods_libellecourt;
	private String periods_libellelong;
	private String periods_debut;
	private String periods_fin;
	private String periods_ratrappage;
	
	public int getPeriodsId() {
		return periods_id;
	}
	public void setPeriodsId(int periods_id) {
		this.periods_id = periods_id;
	}
	public int getUniversityYearId() {
		return university_year_id;
	}
	public void setUniversityYearId(int university_year_id) {
		this.university_year_id = university_year_id;
	}
	public int getLevelId() {
		return level_id;
	}
	public void setLevelId(int level_id) {
		this.level_id = level_id;
	}
	public String getPeriodsLibellelong() {
		return periods_libellelong;
	}
	public void setPeriodsLibellelong(String  periods_libellelong) {
		this.periods_libellelong = periods_libellelong;
	}
	public String getPeriodsDebut() {
		return periods_debut;
	}
	public void setPeriodsDebut(String  periods_debut) {
		this.periods_debut = periods_debut;
	}
	public String getPeriodsFin() {
		return periods_fin;
	}
	public void setPeriodsFin(String  periods_fin) {
		this.periods_fin = periods_fin;
	}
	public String getPeriodsRatrappage() {
		return periods_ratrappage;
	}
	public void setPeriodsRatrappage(String  periods_rattrapage) {
		this.periods_ratrappage = periods_rattrapage;
	}
	
}
