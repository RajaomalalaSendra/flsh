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

import com.flsh.model.Professors;
import com.flsh.model.User;
import com.flsh.interfaces.ProfessorService;

@Controller
public class ProfessorController {
	  @Autowired
	  ProfessorService professorService;

	  @RequestMapping(value = "/professors", method = RequestMethod.GET)
	  public ModelAndView showProfessor(HttpServletRequest request, HttpServletResponse response) {
		List<Professors> professors=professorService.getAllProfessor();
		ModelAndView prof = new ModelAndView("users/professors");
	    prof.addObject("professors", professors);
	    return prof;
	  }
	  
	  @RequestMapping(value = "/professor/details", method = RequestMethod.GET)
	  @ResponseBody
	  public String getUserDetails(HttpServletRequest request, HttpServletResponse response) {
		  int id = request.getParameter("id") == null || request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		  Professors professor = professorService.getProfessorDetails(id);
		  JSONObject rtn = new JSONObject();
		  rtn.put("id", professor.getProfessorId());
		  rtn.put("lastname", professor.getProfessorLastName());
		  rtn.put("firstname", professor.getProfessorName());
		  rtn.put("email", professor.getProfessorEmail());
		  rtn.put("contact", professor.getProfessorContact());
		  return rtn.toString();
	  }
	  @RequestMapping(value = "/professor/save", method = RequestMethod.POST)
	  @ResponseBody
	  public String saveProfessor(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("professor") Professors professor) {
  		System.out.print(professor);
    	JSONObject rtn = professorService.saveProfessor(professor);
  		return rtn.toString();
	  }
	  
}
