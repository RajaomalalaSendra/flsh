package com.flsh.model;

public class ProfessorStudyUnit {
    //	ue_id 	prof_id 
	private int prof_std_unt_id;
	private int study_unit_id;
	private int professor_id;
	private int professor_civilite;
	private int isresponsable;

	private String professor_name;
	private String professor_last_name;
	private String study_unit_libelle;
	private String parcours_libelle;
	private String level_libelle;
	
	public int getStudy_unit_id() {
		return study_unit_id;
	}
	
	public void setStudy_unit_id(int study_unit_id) {
		this.study_unit_id = study_unit_id;
	}
	
	public int getProfessor_id() {
		return professor_id;
	}
	
	public void setProfessor_id(int professor_id) {
		this.professor_id = professor_id;
	}

	public String getProfessor_name() {
		return professor_name;
	}

	public void setProfessor_name(String professor_name) {
		this.professor_name = professor_name;
	}

	public String getProfessor_last_name() {
		return professor_last_name;
	}

	public void setProfessor_last_name(String professor_last_name) {
		this.professor_last_name = professor_last_name;
	}

	public int getProfessor_civilite() {
		return professor_civilite;
	}

	public void setProfessor_civilite(int professor_civilite) {
		this.professor_civilite = professor_civilite;
	}

	public String getStudy_unit_libelle() {
		return study_unit_libelle;
	}

	public void setStudy_unit_libelle(String study_unit_libelle) {
		this.study_unit_libelle = study_unit_libelle;
	}
	
	public String getCivilite() {
		if(professor_civilite == 2) {
			return "Mlle";
		} else if (professor_civilite == 3) {
				return "Mme"; 
		} else {
			return "Mr";
		}
	}

	public int getProf_std_unt_id() {
		return prof_std_unt_id;
	}

	public void setProf_std_unt_id(int prof_std_unt_id) {
		this.prof_std_unt_id = prof_std_unt_id;
	}

	public String getParcours_libelle() {
		return parcours_libelle;
	}

	public void setParcours_libelle(String parcours_libelle) {
		this.parcours_libelle = parcours_libelle;
	}

	public String getLevel_libelle() {
		return level_libelle;
	}

	public void setLevel_libelle(String level_libelle) {
		this.level_libelle = level_libelle;
	}
	
	public String getResponsability() {
		return "Responsable";
	}
	public String getNonResponsability() {
		return "-";
	}
	
	public String getIsResponsable() {
		String responsable;
		responsable = isresponsable == 0 ? "Non Responsable" : "Responsable";
		return responsable;
	}
	
	public void setIsResponsable(int i) {
		this.isresponsable = i;
	}

}
