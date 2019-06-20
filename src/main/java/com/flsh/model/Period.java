package com.flsh.model;

import java.text.ParseException;

import com.flsh.utils.DateUtils;

public class Period {
    // per_id 	au_id 	niv_id 	per_libellecourt 	per_libellelong
	// per_debut 	per_fin 	per_aratrappage 
	private int period_id;
	private int university_year_id;
	private int level_id;
	private String period_libellecourt;
	private String period_libellelong;
	private String period_debut;
	private String period_fin;
	private boolean a_ratrappage;
	private String exam_libelle;
	private String exam_debut;
	private String exam_fin;
	private String rattr_libelle;
	private String rattr_debut;
	private String rattr_fin;
	
	public String toString() {
		return "Idperiod "+period_id +" \n au_id: "+university_year_id +"\n Level:"+level_id+"\n Libcourt:  "+ period_libellecourt+"\nLiblong: "+period_libellelong
				+"\n Début: "+period_debut+"\nFin: "+period_fin+"\nHasRattr: " +( a_ratrappage ? 1 : 0) 
				+"\n Exam: "+exam_libelle+" debut : "+exam_debut+" fin: "+exam_fin+"\n rattr: "+rattr_libelle+" debut: "+rattr_debut+" fin: "+rattr_fin;
	}
	
	private DateUtils dateUtils = new DateUtils();
	
	public int getPeriod_id() {
		return period_id;
	}
	public void setPeriod_id(int periods_id) {
		this.period_id = periods_id;
	}
	public int getUniversity_year_id() {
		return university_year_id;
	}
	public void setUniversity_year_id(int university_year_id) {
		this.university_year_id = university_year_id;
	}
	public int getLevel_id() {
		return level_id;
	}
	public void setLevel_id(int level_id) {
		this.level_id = level_id;
	}
	public String getPeriod_libellecourt() {
		return period_libellecourt;
	}
	public void setPeriod_libellecourt(String periods_libellecourt) {
		this.period_libellecourt = periods_libellecourt;
	}
	public String getPeriod_libellelong() {
		return period_libellelong;
	}
	public void setPeriod_libellelong(String periods_libellelong) {
		this.period_libellelong = periods_libellelong;
	}
	public String getPeriod_debut() {
		return period_debut;
	}
	public String getPerDebut() throws ParseException {
		return dateUtils.convertToFrenchDate(period_debut);
	}
	public void setPeriod_debut(String periods_debut) {
		this.period_debut = periods_debut;
	}
	public String getPeriod_fin() {
		return period_fin;
	}
	public String getPerFin() throws ParseException {
		return dateUtils.convertToFrenchDate(period_fin);
	}
	public void setPeriod_fin(String periods_fin) {
		this.period_fin = periods_fin;
	}
	public boolean isA_ratrappage() {
		return a_ratrappage;
	}
	public void setA_ratrappage(boolean periods_ratrappage) {
		this.a_ratrappage = periods_ratrappage;
	}
	public String getExam_libelle() {
		return exam_libelle;
	}
	public void setExam_libelle(String exam_libelle) {
		this.exam_libelle = exam_libelle;
	}
	public String getExam_debut() {
		return exam_debut;
	}
	public String getExamDebut() throws ParseException {
		return dateUtils.convertToFrenchDate(exam_debut);
	}
	public void setExam_debut(String exam_debut) {
		this.exam_debut = exam_debut;
	}
	public String getExam_fin() {
		return exam_fin;
	}
	public String getExamFin() throws ParseException {
		return dateUtils.convertToFrenchDate(exam_fin);
	}
	public void setExam_fin(String exam_fin) {
		this.exam_fin = exam_fin;
	}
	public String getRattr_libelle() {
		return rattr_libelle;
	}
	public void setRattr_libelle(String rattr_libelle) {
		this.rattr_libelle = rattr_libelle;
	}
	public String getRattr_debut() {
		return rattr_debut;
	}
	public String getRattrDebut() throws ParseException {
		return this.isA_ratrappage() ? dateUtils.convertToFrenchDate(rattr_debut) : "";
	}
	public void setRattr_debut(String rattr_debut) {
		this.rattr_debut = rattr_debut;
	}
	public String getRattr_fin() {
		return rattr_fin;
	}
	public String getRattrFin() throws ParseException {
		return this.isA_ratrappage() ? dateUtils.convertToFrenchDate(rattr_fin) : "";
	}
	public void setRattr_fin(String rattr_fin) {
		this.rattr_fin = rattr_fin;
	}
}
