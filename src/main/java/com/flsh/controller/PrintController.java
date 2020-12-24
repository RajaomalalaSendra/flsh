package com.flsh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.interfaces.PeriodService;
import com.flsh.interfaces.PrintService;
import com.flsh.interfaces.StudentService;
import com.flsh.model.Level;
import com.flsh.model.Parcours;
import com.flsh.model.Period;
import com.flsh.model.Cycles;

@Controller
public class PrintController {
	@Autowired
	StudentService studentService;
	
	@Autowired
	PeriodService periodService;
	
	@Autowired
	PrintService printService;
	
	@RequestMapping(value = "/print_results/final", method = RequestMethod.GET)
	public ModelAndView accueilPeriods(HttpServletRequest request, HttpServletResponse response) {
		int idUY = !request.getParameter("uy").equals("") ? Integer.parseInt(request.getParameter("uy")) : 0;
		int idLevel = !request.getParameter("level").equals("") ? Integer.parseInt(request.getParameter("level")) : 0;
		int idCategory = !request.getParameter("category").equals("") ? Integer.parseInt(request.getParameter("category")) : 0;
		int idCycle = periodService.getIdCycleByIdLevel(idLevel);
		Cycles cycle = periodService.getCyclesById(idCycle);
			
		ModelAndView mav = new ModelAndView("results/final");
		mav.addObject("UnivYear", periodService.getUnivYearById(idUY));
		mav.addObject("Level", periodService.getLevelById(idLevel));
		mav.addObject("Category", periodService.getCategoryName(idCategory));
		mav.addObject("Students", studentService.getAllStudentsByCategory(idUY, idLevel, idCategory));
		mav.addObject("Cycle", cycle);
		
		return mav;
	}
	
	@RequestMapping(value = "/print_results/partial", method = RequestMethod.GET)
	public ModelAndView getPartialResults(HttpServletRequest request, HttpServletResponse response) {
		int idUY = !request.getParameter("uy").equals("") ? Integer.parseInt(request.getParameter("uy")) : 0;
		int idLevel = !request.getParameter("level").equals("") ? Integer.parseInt(request.getParameter("level")) : 0;
		int idPracours = !request.getParameter("prc").equals("") ? Integer.parseInt(request.getParameter("prc")) : 0;
		int idPeriod = !request.getParameter("period").equals("") ? Integer.parseInt(request.getParameter("period")) : 0;
		int idCycle = periodService.getIdCycleByIdLevel(idLevel);
		Cycles cycle = periodService.getCyclesById(idCycle);
		
		Period period = periodService.getPeriodById(idPeriod);
		Level level = periodService.getLevelById(idLevel);
		Parcours prc = periodService.getParcoursById(idPracours);
		System.out.print("calling >>>>>>>>>>>>>>>>>>>>> ");
		ModelAndView mav = new ModelAndView("results/partial");
		mav.addObject("UnivYear", periodService.getUnivYearById(idUY));
		mav.addObject("studyUnits", printService.getLevelStudyUnitandCourses(idPracours));
		mav.addObject("Students", printService.getStudentsandPartialResultsByLevelandUYandPer(idPracours, idUY, idPeriod));
		mav.addObject("period", period.getPeriod_libellelong());
		mav.addObject("level", level.getLevelLibelle());
		mav.addObject("parcours", prc.getParcoursLibelle());
		mav.addObject("Cycle", cycle);
		
		return mav;
	}
}
