package com.flsh.model;

public class Exam { 
	private int exam_id;
	private int period_id;
	private String exam_libelle;
	private String exam_sessiontype;
	
	public int getExamId() {
		return this.exam_id;
	}
	public void setExamId(int exam_id) {
		this.exam_id = exam_id;
	}
	public int getPeriodId() {
		return this.period_id;
	}
	public void setPeriodId(int period_id) {
		this.period_id = period_id;
	}
	public String getExamLibelle() {
		return this.exam_libelle;
	}
	public void setExamLibelle(String exam_libelle) {
		this.exam_libelle = exam_libelle;
	}
	public String getExamSessiontype() {
		return this.exam_sessiontype;
	}
	public void setExamSessiontype(String exam_sessiontype) {
		this.exam_sessiontype = exam_sessiontype;
	}
}
