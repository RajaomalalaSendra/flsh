package com.flsh.model;

public class Professor {
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
	
		public int getProfessor_id() {
		return professor_id;
	}

	public void setProfessor_id(int professor_id) {
		this.professor_id = professor_id;
	}

	public String getProfessor_password() {
		return professor_password;
	}

	public void setProfessor_password(String professor_password) {
		this.professor_password = professor_password;
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

	public String getProfessor_login() {
		return professor_login;
	}

	public void setProfessor_login(String professor_login) {
		this.professor_login = professor_login;
	}

	public String getProfessor_adresse() {
		return professor_adresse;
	}

	public void setProfessor_adresse(String professor_adresse) {
		this.professor_adresse = professor_adresse;
	}

	public String getProfessor_email() {
		return professor_email;
	}

	public void setProfessor_email(String professor_email) {
		this.professor_email = professor_email;
	}

	public String getProfessor_contact() {
		return professor_contact;
	}

	public void setProfessor_contact(String professor_contact) {
		this.professor_contact = professor_contact;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

		public String toString() {
		return "id: " + professor_id +  " name: " + professor_name + " first name: " + professor_last_name + " login name: " + professor_login +
				" adresse: " + professor_adresse + " contact " + professor_contact + " professor password " + professor_password + " user Id:  " + user_id
				+ "  user Type " + user_type + " professor mail " + professor_email;
	}
	
}
