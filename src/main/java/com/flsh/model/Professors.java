package com.flsh.model;

public class Professors {
	private int professor_id;
	private String professor_password;
	private String professor_name;
	private String professor_last_name;
	private String professor_login;
	private String professor_adresse;
	private String professor_email;
	private String professor_contact;
	private int user_id;
	private int user_type;
	
	public Professors() {
		/// Professors for us
	}
	public Professors(int id, String name, String first, String log, String mail, String passwd, String adr, String contact, int type, int usid ) {
		this.professor_id = id;
		this.professor_last_name = name;
		this.professor_name = first;
		this.professor_login = log;
		this.professor_email = mail;
		this.professor_password = passwd;
		this.professor_adresse = adr;
		this.professor_contact = contact;
		this.user_id = usid;
		this.user_type = type;
	}
	public int getProfessorId() {
		return professor_id;
	}
	public void setProfessorId( int professor_id) {
		this.professor_id = professor_id;
	}
	public String getProfessorPassword() {
		return professor_password;
	}
	public void setProfessorPassword(String professor_password) {
		this.professor_password = professor_password;
	}
	public String getProfessorName() {
		return professor_name;
	}
	public void setProfessorName(String professor_name) {
		this.professor_name = professor_name;
	}
	public String getProfessorLastName() {
		return professor_last_name;
	}
	public void setProfessorLastName(String professor_last_name) {
		this.professor_last_name = professor_last_name;
	}
	public String getProfessorAdresse() {
		return professor_adresse;
	}
	public void setProfessorAdresse(String professor_adresse) {
		this.professor_adresse = professor_adresse;
	}
	public String getProfessorEmail() {
		return professor_email;
	}
	public void setProfessorEmail(String professor_email) {
		this.professor_email = professor_email;
	}
	public String getProfessorContact() {
		return professor_contact;
	}
	public void setProfessorContact(String professor_contact) {
		this.professor_contact = professor_contact;
	}
	public int getUserId() {
		return user_id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public String getProfessorLogin() {
		return professor_login;
	}
	public void setProfessorLogin(String professor_login) {
		this.professor_login = professor_login;
	}
	public String toString() {
		return "id: " + professor_id +  " name: " + professor_name + " first name: " + professor_last_name + " login name: " + professor_login +
				" adresse: " + professor_adresse + " contact " + professor_contact + " professor password " + professor_password + " user Id:  " + user_id
				+ "  user Type " + user_type + " professor mail " + professor_email;
	}
	public int getUserType() {
		return user_type;
	}
	public void setUserType(int user_type) {
		this.user_type = user_type;
	}
}
