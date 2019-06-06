package com.flsh.model;

import java.util.HashSet;
import java.util.Set;

public class User {
	
	  private int id;
	  private String username;
	  private String password;
	  private String email;
	  private String type;
	  private Boolean enabled = true;
	  private Set<Authorities> authorities = new HashSet<Authorities>();
	
	  public String getUsername() {
		  return username;
	  }
	
	  public void setUsername(String username) {
		  this.username = username;
	  }
	
	  public String getPassword() {
		  return password;
	  }
	
	  public void setPassword(String password) {
		  this.password = password;
	  }
	
	  public String getType() {
		  return type;
	  }
	  
	  public String getTypeComputed() {
		  switch(type) {
		  	case "1":
		  		return "Super Utilisateur";
		  	case "2":
		  		return "Adminisatrion";
		  	default:
		  		return "Professeur";
		  }
	  }
	
	  public void setType(String type) {
		  this.type = type;
	  }
	
	  public String getEmail() {
		  return email;
	  }
	
	  public void setEmail(String email) {
		  this.email = email;
	  }

	public Boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Set<Authorities> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Set<Authorities> authorities) {
		this.authorities = authorities;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		return "username "+username+" email "+email+" password "+password+ " type " + type;
	}

}



