package com.flsh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.interfaces.PeriodService;
import com.flsh.interfaces.StudentService;

@Controller
public class PrintController {
	@Autowired
	StudentService studentService;
	
	@Autowired
	PeriodService periodService;
	
	@RequestMapping(value = "/print_results/final", method = RequestMethod.GET)
	public ModelAndView accueilPeriods(HttpServletRequest request, HttpServletResponse response) {
		int idUY = !request.getParameter("uy").equals("") ? Integer.parseInt(request.getParameter("uy")) : 0;
		int idLevel = !request.getParameter("level").equals("") ? Integer.parseInt(request.getParameter("level")) : 0;
		int idCategory = !request.getParameter("category").equals("") ? Integer.parseInt(request.getParameter("category")) : 0;
		
		
		ModelAndView mav = new ModelAndView("results/final");
		mav.addObject("UnivYear", periodService.getUnivYearById(idUY));
		mav.addObject("Level", periodService.getLevelById(idLevel));
		
		return mav;
	}
}
