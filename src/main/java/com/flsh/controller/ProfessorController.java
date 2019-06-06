package com.flsh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.model.Professors;
import com.flsh.interfaces.ProfessorsService;

@Controller
public class ProfessorController {
	  ProfessorsService professorsService;

	  @RequestMapping(value = "/professors", method = RequestMethod.GET)
	  public ModelAndView showProfessor(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView prof = new ModelAndView("professors");
	    prof.addObject("professors");
	    return prof;
	  }
	  @RequestMapping(value = "/professor/save", method = RequestMethod.POST)
	  public ModelAndView saveProfessor(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("professors") Professors professors) {
	    System.out.print(professors);
	    ModelAndView prof = new ModelAndView("redirect:/");
	    prof.addObject("professors");
	    return prof;
	  }
	  @RequestMapping(value = "/professor/add", method = RequestMethod.GET)
	  public ModelAndView addProfessor(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView prof = new ModelAndView("add_professors");
	    prof.addObject("add_professors");
	    return prof;
	  }
	  @RequestMapping(value = "/professor/detail?id=", method = RequestMethod.GET)
	  public ModelAndView detailProfessor(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView prof = new ModelAndView("professors");
	    prof.addObject("professors");
	    return prof;
	  }
	  @RequestMapping(value = "/professor/remove?id=", method = RequestMethod.GET)
	  public ModelAndView removeProfessor(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView prof = new ModelAndView("professors");
	    prof.addObject("professors");
	    return prof;
	  }
}
