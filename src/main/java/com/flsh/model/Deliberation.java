package com.flsh.model;

public class Deliberation {
	private String period_libelle;
	private int period_rattrapage;
	private int period_id;
	private int ec_notation;
	private String evaluation;
	private String credit;
	private String ue_libelle;
	private String ec_libelle;
	
	public String getPeriod_libelle() {
		return period_libelle;
	}
	public void setPeriod_libelle(String period_libelle) {
		this.period_libelle = period_libelle;
	}
	public int getPeriod_rattrapage() {
		return period_rattrapage;
	}
	public void setPeriod_rattrapage(int period_rattrapage) {
		this.period_rattrapage = period_rattrapage;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getUe_libelle() {
		return ue_libelle;
	}
	public void setUe_libelle(String ue_libelle) {
		this.ue_libelle = ue_libelle;
	}
	public String getEc_libelle() {
		return ec_libelle;
	}
	public void setEc_libelle(String ec_libelle) {
		this.ec_libelle = ec_libelle;
	}

	public int getPeriod_id() {
		return period_id;
	}
	public void setPeriod_id(int period_id) {
		this.period_id = period_id;
	}
	public int getEc_notation() {
		return ec_notation;
	}
	public void setEc_notation(int ec_notation) {
		this.ec_notation = ec_notation;
	}

}
