package com.flsh.model;

public class Parcours {
    //prc_id 	prc_libelle 	niv_id 
	private int parcours_id;
	private String parcours_libelle;
	private int level_id;
	
	public int getParcoursId() {
		return parcours_id;
	}
	public void setParcoursId(int parcours_id) {
		this.parcours_id = parcours_id;
	}
	public int getLevelId() {
		return level_id;
	}
	public void setLevelId(int level_id) {
		this.level_id = level_id;
	}
	public String getParcoursLibelle() {
		return parcours_libelle;
	}
	public void setParcoursLibelle(String parcours_libelle) {
		this.parcours_libelle = parcours_libelle;
	}
	
	public String toString() {
		return "parcours_id: "+ this.parcours_id +" | parcours_libelle: "+ this.parcours_libelle;
	}
}
