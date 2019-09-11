package com.flsh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.interfaces.PeriodService;

@Controller
public class DeliberationController {
	
	@Autowired
	PeriodService periodService;
	
	@RequestMapping(value = "/educations/deliberation", method = RequestMethod.GET)
	public ModelAndView startDelib(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("deliberation/accueil");
		mav.addObject("univYears", periodService.getAllUnivYears());
		mav.addObject("menu", "home");
		mav.addObject("submenu", "deliberation");
		return mav;
	}

}
