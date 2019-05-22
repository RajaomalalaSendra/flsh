package com.flsh.model;

public class StudentsLevel {
    // etd_id 	niv_id 	au_id 
	private int students_id;
	private int level_id;
	private int university_year_id;
	
	public int getStudentsId() {
		return this.students_id;
	}
	public void setStudentsId(int students_id) {
		this.students_id = students_id;
	}
	public int getLevelId() {
		return this.level_id;
	}
	public void setLevelId(int level_id) {
		this.level_id = level_id;
	}
	public int getUniversityYearId() {
		return this.university_year_id;
	}
	public void setUniversityYearId(int university_year_id) {
		this.university_year_id = university_year_id;
	}
}
