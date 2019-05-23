package com.flsh.model;

public class Professors {
	private String professor_login;
	private String professor_password;
	private String professor_name;
	private String professor_last_name;
	private String professor_adresse;
	private String professor_email;
	private String professor_contact;
	private int user_id;
	
	public String getProfessorLogin() {
		return this.professor_login;
	}
	public void setProfessorLogin( String professor_login) {
		this.professor_login = professor_login;
	}
	public String getProfessorPassword() {
		return this.professor_password;
	}
	public void setProfessorPassword(String professor_password) {
		this.professor_password = professor_password;
	}
	public String getProfessorName() {
		return this.professor_name;
	}
	public void setProfessorName(String professor_name) {
		this.professor_name = professor_name;
	}
	public String getProfessorLastName() {
		return this.professor_last_name;
	}
	public void setProfessorLastName(String professor_last_name) {
		this.professor_last_name = professor_last_name;
	}
	public String getProfessorAdresse() {
		return this.professor_adresse;
	}
	public void setProfessorAdresse(String professor_adresse) {
		this.professor_adresse = professor_adresse;
	}
	public String getProfessorEmail() {
		return this.professor_email;
	}
	public void setProfessorEmail(String professor_email) {
		this.professor_email = professor_email;
	}
	public String getProfessorContact() {
		return this.professor_contact;
	}
	public void setProfessorContact(String professor_contact) {
		this.professor_contact = professor_contact;
	}
	public int getUserId() {
		return this.user_id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
}
