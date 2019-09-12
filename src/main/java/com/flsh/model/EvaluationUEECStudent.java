package com.flsh.model;

import java.util.List;

public class EvaluationUEECStudent {
	private int studyunit_id;
	private int parcours_id;
	private int credit_ue;
	private int valid_credit_ue;
	private String studyunit_libelle;
	private String studyunit_type;
	private List<EvaluationCourseStudent> CoursesEvaluations;
	
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
	public String getStudyunit_libelle() {
		return studyunit_libelle;
	}
	public void setStudyunit_libelle(String studyunit_libelle) {
		this.studyunit_libelle = studyunit_libelle;
	}
	public String getStudyunit_type() {
		return studyunit_type;
	}
	public void setStudyunit_type(String studyunit_type) {
		this.studyunit_type = studyunit_type;
	}
	public List<EvaluationCourseStudent> getCoursesEvaluations() {
		return CoursesEvaluations;
	}
	public void setCoursesEvaluations(List<EvaluationCourseStudent> coursesEvaluations) {
		CoursesEvaluations = coursesEvaluations;
	}
	public int getCredit_ue() {
		return credit_ue;
	}
	public void setCredit_ue(int credit_ue) {
		this.credit_ue = credit_ue;
	}
	public int getValid_credit_ue() {
		return valid_credit_ue;
	}
	public void setValid_credit_ue(int valid_credit_ue) {
		this.valid_credit_ue = valid_credit_ue;
	}
}
