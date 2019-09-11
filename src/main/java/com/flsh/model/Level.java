package com.flsh.model;

import java.util.HashSet;

public class Level {
    // niv_id 	cyc_id 	niv_libelle 
	private int level_id;
	private int cycle_id;
	private String level_libelle;
	private HashSet<Parcours> parcours;
	
	public int getLevelId() {
		return this.level_id;
	}
	public void setLevelId(int level_id) {
		this.level_id = level_id;
	}
	public int getCycleId() {
		return cycle_id;
	}
	public void setCycleId(int cycle_id) {
		this.cycle_id = cycle_id;
	}
	public String getLevelLibelle() {
		return this.level_libelle;
	}
	public void setLevelLibelle(String level_libelle) {
		this.level_libelle = level_libelle;
	}
	public HashSet<Parcours> getParcours() {
		return parcours;
	}
	public void setParcours(HashSet<Parcours> parcours) {
		this.parcours = parcours;
	}
	
	public String toString() {
		String parcoursString = "( ";
		for(Parcours prc : this.parcours) {
			parcoursString += prc.toString()+", ";
		}
		parcoursString += " )";
		return "id: "+this.level_id+" | libelle: "+this.level_libelle+" | parcours: "+parcoursString;
	}
}
