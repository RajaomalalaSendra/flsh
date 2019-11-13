package com.flsh.model;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.servlet.ServletContext;

public class Student{
	
	private int student_id;
	private int student_civ;
	private String student_name;
	private String student_lastname;
	private String student_birthdate;
	private String student_nationality;
	private String student_passport;
	private String student_cin;
	private String student_cinlocation;
	private String student_cindate;
	private String student_adress;
	private String student_email;
	private String student_lastetab;
	private String student_nameconjoint;
	private String student_namefather;
	private String student_jobfather;
	private String student_namemother;
	private String student_jobmother;
	private int[] ec_choisis;
	private int[] ec_cumules;
	private HashMap<String, String> evaluations;
	private HashMap<String, String> ec_results;
	private int number;
	private String image_cropped;
	private int netdelib;
	private boolean cumulant = false;
	
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public String getCivilite() {
		switch(student_civ) {
			case 2: return "Mlle"; 
			case 3: return "Mme"; 
			default: return "Mr";
		}
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getStudent_lastname() {
		return student_lastname;
	}
	public void setStudent_lastname(String student_lastname) {
		this.student_lastname = student_lastname;
	}
	public String getStudent_nationality() {
		return student_nationality;
	}
	public void setStudent_nationality(String student_nationality) {
		this.student_nationality = student_nationality;
	}
	public String getStudent_adress() {
		return student_adress;
	}
	public void setStudent_adress(String student_adress) {
		this.student_adress = student_adress;
	}
	public String getStudent_email() {
		return student_email;
	}
	public void setStudent_email(String student_email) {
		this.student_email = student_email;
	}
	public String getStudent_lastetab() {
		return student_lastetab;
	}
	public void setStudent_lastetab(String student_lasttab) {
		this.student_lastetab = student_lasttab;
	}
	public String getStudent_nameconjoint() {
		return student_nameconjoint;
	}
	public void setStudent_nameconjoint(String student_nameconjoint) {
		this.student_nameconjoint = student_nameconjoint;
	}
	public String getStudent_namefather() {
		return student_namefather;
	}
	public void setStudent_namefather(String student_namefather) {
		this.student_namefather = student_namefather;
	}
	public String getStudent_namemother() {
		return student_namemother;
	}
	public void setStudent_namemother(String student_namemother) {
		this.student_namemother = student_namemother;
	}
	public String getStudent_birthdate() {
		return student_birthdate;
	}
	public void setStudent_birthdate(String student_birthdate) {
		this.student_birthdate = student_birthdate;
	}
	public int getStudent_civ() {
		return student_civ;
	}
	public void setStudent_civ(int student_civ) {
		this.student_civ = student_civ;
	}
	public String getStudent_passport() {
		return student_passport;
	}
	public void setStudent_passport(String student_passport) {
		this.student_passport = student_passport;
	}
	public String getStudent_cin() {
		return student_cin;
	}
	public void setStudent_cin(String student_cin) {
		this.student_cin = student_cin;
	}
	public String getStudent_cinlocation() {
		return student_cinlocation;
	}
	public void setStudent_cinlocation(String student_cinlocation) {
		this.student_cinlocation = student_cinlocation;
	}
	public String getStudent_cindate() {
		return student_cindate;
	}
	public void setStudent_cindate(String student_cindate) {
		this.student_cindate = student_cindate;
	}
	public String getStudent_jobfather() {
		return student_jobfather;
	}
	public void setStudent_jobfather(String student_jobfather) {
		this.student_jobfather = student_jobfather;
	}
	public String getStudent_jobmother() {
		return student_jobmother;
	}
	public void setStudent_jobmother(String student_jobmother) {
		this.student_jobmother = student_jobmother;
	}
	public String getEvaluation(String examPeriode) {
		try {
			return evaluations.get(examPeriode);
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public HashMap<String, String> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(HashMap<String, String> evaluations) {
		this.evaluations = evaluations;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getImage_cropped() {
		return image_cropped;
	}
	public void setImage_cropped(String image_cropped) {
		this.image_cropped = image_cropped;
	}
	
	public String getCroppedImageURL(ServletContext servletContext) {
		String imageURL = "/scolarLMD/resources/img/student/default.png";
		System.out.print("Servletcontext: " + servletContext);
		
		try {
			String nameImage = "Student_" + this.student_id + ".jpeg";
			String fileImageStudent = Paths.get(servletContext.getRealPath("resources/img/student"),nameImage).normalize().toString();
			File f = new File(fileImageStudent);
			if(f.exists()) {
				imageURL = "/scolarLMD/resources/img/student/" + nameImage;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return imageURL;
		
	}
	
	public void setNet_delib(int netdelib) {
		this.netdelib = netdelib;
	}
	
	public int getNet_delib() {
		return this.netdelib;
	}
	
	public void setEcResults(HashMap<String, String> results) {
		this.ec_results = results;
	}
	
	public boolean isEcChoisi(int idEC) {
		if(ec_choisis != null && ec_choisis.length > 0) {
			for(int ec : ec_choisis) {
				if(ec == idEC) return true;
			}
		}
		return false;
	}
	
	public int[] getEc_choisis() {
		return ec_choisis;
	}
	
	public void setEc_choisis(int[] ec_choisis) {
		this.ec_choisis = ec_choisis;
	}
	
	public boolean isCumulant() {
		return cumulant;
	}
	
	public void setCumulant(boolean cumulant) {
		this.cumulant = cumulant;
	}
	
	public int[] getEc_cumules() {
		return ec_cumules;
	}
	
	public void setEc_cumules(int[] ec_cumules) {
		this.ec_cumules = ec_cumules;
	}
	
	public boolean isECCumule(int idEC) {
		if(ec_cumules != null && ec_cumules.length > 0) {
			for(int ec : ec_cumules) {
				System.out.print("\n Checking ec "+idEC+" "+ec);
				if(ec == idEC) return true;
			}
		}
		return false;
	}
	
	/**
	 * Check by the logic the result of EC evaluation of a student
	 * @param idEC
	 * @param ecType
	 * @return
	 */
	public String getECOk(int idEC, String ecType) {
		boolean ok = false;
		try {
			ok = ec_results.get(""+idEC).equals("1");
		} catch (Exception e) {
			System.out.print("\nNo result for this ec");
		}
		if(this.isCumulant()) { 
			//If the student cumulated the EC, there is no way to check type or anything else
			System.out.print("\nCumulante "+ec_cumules);
			if(this.isECCumule(idEC)) {
				return ok ? "O" : "+";
			} else {
				return "";
			}
		} else {
			if(ecType.equals("O")) {
				return ok ? "O" : "+";
			} else if(ecType.equals("C")) {
				if(this.isEcChoisi(idEC)) {
					return ok ? "O" : "+";
				} else {
					return "";
				}
			} else {
				return ok ? "O" : "";
			}
		}
		
	}
}