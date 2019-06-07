package com.flsh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	  
}
