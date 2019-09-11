package com.flsh.model;

public class StudentsEvaluation {
	private int evaluation_id;
	private int student_id;
	private int study_units_id;
	private int exam_id;
	private int period_id;
	private String evaluation_evaluation;
	private String evaluation_creditobtenu;
	
	public int getEvaluationId() {
		return this.evaluation_id;
	}
	public void setEvaluationId(int evaluation_id) {
		this.evaluation_id = evaluation_id;
	}
	public int getStudentId() {
		return this.student_id;
	}
	public void setStudentId(int student_id) {
		this.student_id = student_id;
	}
	public int getStudyUnitsId() {
		return this.study_units_id;
	}
	public void setStudyUnits(int study_units_id) {
		this.study_units_id = study_units_id;
	}
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
	public String getEvaluationEvaluation() {
		return this.evaluation_evaluation;
	}
	public void setEvaluationEvaluation(String evaluation_evaluation) {
		this.evaluation_evaluation = evaluation_evaluation;
	}
	public String getEvaluationCreditobtenu() {
		return this.evaluation_creditobtenu;
	}
	public void setEvaluationCreditobtenu(String evaluation_creditobtenu) {
		this.evaluation_creditobtenu = evaluation_creditobtenu;
	}
}
