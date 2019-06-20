package com.flsh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.model.Professor;
import com.flsh.model.User;
import com.flsh.interfaces.ProfessorService;

@Controller
public class ProfessorController {
	  @Autowired
	  ProfessorService professorService;

	  @RequestMapping(value = "/professors", method = RequestMethod.GET)
	  public ModelAndView showProfessor(HttpServletRequest request, HttpServletResponse response) {
		List<Professor> professors=professorService.getAllProfessor();
		ModelAndView prof = new ModelAndView("users/professors");
	    prof.addObject("professors", professors);
	    return prof;
	  }
	  
	  @RequestMapping(value = "/professor/details", method = RequestMethod.GET)
	  @ResponseBody
	  public String getUserDetails(HttpServletRequest request, HttpServletResponse response) {
		  int id = request.getParameter("id") == null || request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		  Professor professor = professorService.getProfessorDetails(id);
		  JSONObject rtn = new JSONObject();
		  rtn.put("id", professor.getProfessorId());
		  rtn.put("professor_last_name", professor.getProfessorLastName());
		  rtn.put("professor_name", professor.getProfessorName());
		  rtn.put("professor_email", professor.getProfessorEmail());
		  rtn.put("professor_adresse", professor.getProfessorAdresse());
		  rtn.put("professor_login", professor.getProfessorLogin());
		  rtn.put("professor_contact", professor.getProfessorContact());
		  rtn.put("professor_password", professor.getProfessorPassword());
		  rtn.put("user_type", professor.getUserType());
		  rtn.put("uti_id", professor.getUserId());
		  return rtn.toString();
	  }
	  @RequestMapping(value = "/professor/save", method = RequestMethod.POST)
	  @ResponseBody
	  public String saveProfessor(HttpServletRequest request, HttpServletResponse response) {
		int id = request.getParameter("id") == null || request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		String lastname = request.getParameter("professor_last_name");
		String firstname = request.getParameter("professor_name");
		String login_name = request.getParameter("professor_login");
		String email = request.getParameter("professor_email");
		String passwd = request.getParameter("professor_password");
		String adresse = request.getParameter("professor_adresse");
		String contact = request.getParameter("professor_contact");
		int type = request.getParameter("user_type") == null || request.getParameter("user_type") == "" ? 2 : Integer.parseInt(request.getParameter("user_type"));
		int user_id = request.getParameter("user_id") == null || request.getParameter("user_id") == "" ? 0 : Integer.parseInt(request.getParameter("user_id"));		
        Professor professor = new Professor(id, lastname, firstname, login_name, email, passwd, adresse,  contact, type, user_id );
        System.out.println(professor);
      	JSONObject rtn = professorService.saveProfessor(professor);
  		return rtn.toString();
	  }
	  
	  @RequestMapping(value = "/professor/delete", method = RequestMethod.GET)
		@ResponseBody
		public String deleteProfessor(HttpServletRequest request, HttpServletResponse response) {
		  	int id_before = request.getParameter("id") == ""   ? 0 : Integer.parseInt(request.getParameter("id"));
		    Professor professor = professorService.getProfessorDetails(id_before);
		  	int user_id = professor.getUserId();
		  	int id = professor.getProfessorId();
			System.out.println("Prof Id: " + id);
			System.out.println("User Id: " + user_id);
			JSONObject rtn = professorService.deleteProfessor(id, user_id);
			return rtn.toString();
		}
	  
}
