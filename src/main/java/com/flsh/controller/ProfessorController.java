package com.flsh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.model.Login;
import com.flsh.model.Professors;
import com.flsh.service.ProfessorsService;

@Controller
public class ProfessorController {
	  ProfessorsService professorsService;

	  @RequestMapping(value = "/professors", method = RequestMethod.GET)
	  public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView prof = new ModelAndView("professors");
	    prof.addObject("professors", new Login());
	    return prof;
	  }

	  @RequestMapping(value = "/loginProfessors", method = RequestMethod.POST)
	  public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("login") Login login) {

	    ModelAndView prof = null;
	    Professors professor = professorsService.validateProfessor(login);
	    if (null != professor) {
	    	prof = new ModelAndView("welcome_prof");
	    	prof.addObject("firstname", professor.getProfessorName());
	    } else {
		    prof = new ModelAndView("professors");
		    prof.addObject("message_prof", "Username or Password is wrong!!");
	    }
	    
	    return prof;
	  }
}
