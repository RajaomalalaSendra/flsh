package com.flsh.model;

import java.util.HashSet;

public class Cycles {
	private int cycles_id;
	private String cycles_libelle;
	private HashSet<Level> levels;
    
	public int getCyclesId() {
		return this.cycles_id;
	}
	public void setCyclesId(int cycles_id) {
		this.cycles_id = cycles_id;
	}
	public String getCycleLibelle() {
		return this.cycles_libelle;
	}
	public void setCycleLibelle(String cycle_libelle) {
		this.cycles_libelle = cycle_libelle;
	}
	public HashSet<Level> getLevels() {
		return levels;
	}
	public void setLevels(HashSet<Level> levels) {
		this.levels = levels;
	}
	
	public String toString() {
		String levelstring = "( ";
		for(Level level : this.levels) {
			levelstring += level.toString()+", ";
		}
		levelstring += " )";
		return "id: "+this.cycles_id+" | libelle: "+this.cycles_libelle+" levels: "+levelstring;
		
	}
}
