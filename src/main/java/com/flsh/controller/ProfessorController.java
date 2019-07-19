package com.flsh.controller;

import java.util.HashSet;
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

import com.flsh.model.Course;
import com.flsh.model.Professor;
import com.flsh.model.ProfessorStudyUnit;
import com.flsh.model.StudyUnit;
import com.flsh.interfaces.ProfessorService;
import com.flsh.interfaces.TeachingService;

@Controller
public class ProfessorController {
	  @Autowired
	  ProfessorService professorService;

	  @Autowired
	  TeachingService teachingService;
	  
	  @RequestMapping(value = "/professors", method = RequestMethod.GET)
	  public ModelAndView showProfessor(HttpServletRequest request, HttpServletResponse response) {
		List<Professor> professors=professorService.getAllProfessor();
		ModelAndView prof = new ModelAndView("users/professors");
	    prof.addObject("professors", professors);
	    prof.addObject("menu", "professor");
	    prof.addObject("submenu", "all_professors");
	    return prof;
	  }
	  
	  @RequestMapping(value = "/professor/details", method = RequestMethod.GET)
	  @ResponseBody
	  public String getUserDetails(HttpServletRequest request, HttpServletResponse response) {
		  int id = request.getParameter("id") == null || request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		  Professor professor = professorService.getProfessorDetails(id);
		  JSONObject rtn = new JSONObject();
		  rtn.put("professor_id", professor.getProfessor_id());
		  rtn.put("professor_last_name", professor.getProfessor_last_name());
		  rtn.put("professor_name", professor.getProfessor_name());
		  rtn.put("professor_email", professor.getProfessor_email());
		  rtn.put("professor_adresse", professor.getProfessor_adresse());
		  rtn.put("professor_login", professor.getProfessor_login());
		  rtn.put("professor_contact", professor.getProfessor_contact());
		  rtn.put("professor_password", professor.getProfessor_password());
		  rtn.put("user_type", professor.getUser_type());
		  rtn.put("user_id", professor.getUser_id());
		  rtn.put("user_civilite", professor.getUser_civilite());
		  return rtn.toString();
	  }
	  
	  @RequestMapping(value = "/professor/save", method = RequestMethod.POST)
	  @ResponseBody
	  public String saveProfessor(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("professor") Professor professor) {
        System.out.println(professor);
      	JSONObject rtn = professorService.saveProfessor(professor);
  		return rtn.toString();
	  }
	  
	  @RequestMapping(value = "/professor/delete", method = RequestMethod.GET)
		@ResponseBody
		public String deleteProfessor(HttpServletRequest request, HttpServletResponse response) {
		  	int id_before = request.getParameter("id") == ""   ? 0 : Integer.parseInt(request.getParameter("id"));
		    Professor professor = professorService.getProfessorDetails(id_before);
		  	int user_id = professor.getUser_id();
		  	int id = professor.getProfessor_id();
			System.out.println("Prof Id: " + id);
			System.out.println("User Id: " + user_id);
			JSONObject rtn = professorService.deleteProfessor(id, user_id);
			return rtn.toString();
		}
	  
	  @RequestMapping(value = "/professor/courses", method = RequestMethod.GET)
	  public ModelAndView showProfessorCourses(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView prof = new ModelAndView("users/professor_courses");
		List<Professor> professors = professorService.getAllProfessor();
		
		prof.addObject("professors", professors);
		
		prof.addObject("menu", "professor");
	    prof.addObject("submenu", "prof_courses");
		return prof;
	  }
	  
	  @RequestMapping(value = "/professor/courses/list", method = RequestMethod.GET)
		public ModelAndView loadUEList(HttpServletRequest request, HttpServletResponse response) {
			int id = request.getParameter("id") == null || request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
			Professor prof = professorService.getProfessorDetails(id);
			List<ProfessorStudyUnit> stdUnts = teachingService.getStudyUntsByProfId(id);
			List<Course> courses = teachingService.getEcDetailsByProfId(id);
			ModelAndView profView = new ModelAndView("users/professor_course_list");
			
			profView.addObject("prof", prof);
			profView.addObject("stdUnts", stdUnts);
			profView.addObject("courses", courses);
		    return profView;
		}
}
