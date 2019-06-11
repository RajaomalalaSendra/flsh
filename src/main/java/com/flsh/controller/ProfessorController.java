package com.flsh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

// import com.flsh.model.Login;
// import com.flsh.model.Professors;
import com.flsh.service.ProfessorsService;

@Controller
public class ProfessorController {
	  ProfessorsService professorsService;

	  @RequestMapping(value = "/professors", method = RequestMethod.GET)
	  public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView prof = new ModelAndView("professors");
	    prof.addObject("professors");
	    return prof;
	  }
}
