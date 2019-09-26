package com.flsh.model;

import java.util.HashSet;
import java.util.List;

public class StudyUnit {
	private int studyunit_id;
	private int parcours_id;
	private String studyunit_libellecourt;
	private String studyunit_libellelong;
	private HashSet<Course> Courses;
	private List<Professor> Responsables;
	private List<ProfessorStudyUnit> ResponsablesId;
	
	public int getStudyunit_id() {
		return studyunit_id;
	}
	public void setStudyunit_id(int studyunit_id) {
		this.studyunit_id = studyunit_id;
	}
	public int getParcours_id() {
		return parcours_id;
	}
	public void setParcours_id(int parcours_id) {
		this.parcours_id = parcours_id;
	}
	public HashSet<Course> getCourses() {
		return Courses;
	}
	public void setCourses(HashSet<Course> courses) {
		Courses = courses;
	}
	public String toString() {
		return " \n==========================\n" + "id parcours: " + parcours_id + " id ue: " + studyunit_id + "libellecour: " + studyunit_libellecourt + " libelle: " + studyunit_libellelong + " \n==========================\n";
	}
	public List<Professor> getResponsables() {
		return Responsables;
	}
	public void setResponsables(List<Professor> responsables) {
		Responsables = responsables;
	}
	public List<ProfessorStudyUnit> getResponsablesId() {
		return ResponsablesId;
	}
	public void setResponsablesId(List<ProfessorStudyUnit> responsablesId) {
		ResponsablesId = responsablesId;
	}
	public String getStudyunit_libellecourt() {
		return studyunit_libellecourt;
	}
	public void setStudyunit_libellecourt(String studyunit_libellecourt) {
		this.studyunit_libellecourt = studyunit_libellecourt;
	}
	public String getStudyunit_libellelong() {
		return studyunit_libellelong;
	}
	public void setStudyunit_libellelong(String studyunit_libellelong) {
		this.studyunit_libellelong = studyunit_libellelong;
	}
	public boolean checkProfIsResp(int idProf) {
		boolean res = false;
		if(Responsables != null) {
			System.out.print("\nthere are responsibles");
			for(Professor prof : Responsables) {
				if(prof.getProfessor_id() == idProf) {
					System.out.print("\nProf responsable\n");
					return true;
				}
			}
		}
		System.out.print("\nNon responsible");
		return res;
	}
	
}