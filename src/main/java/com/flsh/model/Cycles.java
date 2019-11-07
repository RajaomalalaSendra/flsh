package com.flsh.model;

import java.util.HashMap;
import java.util.HashSet;

public class Cycles {
	private int cycles_id;
	private String cycles_libelle;
	private HashSet<Level> levels;
	private HashMap<String, Integer> LevelsNumber; 
    private int nombreInscrit;
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
	public HashMap<String, Integer> getLevelsNumber() {
		return LevelsNumber;
	}
	public void setLevelsNumber(HashMap<String, Integer> levelsNumber) {
		LevelsNumber = levelsNumber;
	}
	public int getNombreInscrit() {
		return nombreInscrit;
	}
	public void setNombreInscrit(int nombreInscrit) {
		this.nombreInscrit = nombreInscrit;
	}
	
	public int getPercent(String level) {
		int number = this.LevelsNumber.get(level);
		double percent =  ((double)number/(double)this.nombreInscrit) * 100;
	
		int percent_int = (int) percent;
		
		return percent_int;
	}
	
}
