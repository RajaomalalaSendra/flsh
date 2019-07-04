package com.flsh.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.interfaces.DeliberationService;
import com.flsh.interfaces.PeriodService;
import com.flsh.interfaces.StudentService;
import com.flsh.interfaces.TeachingService;
import com.flsh.model.Course;
import com.flsh.model.Deliberation;
import com.flsh.model.Level;
import com.flsh.model.PeriodLibelle;
import com.flsh.model.Student;
import com.flsh.model.TotalCredit;
import com.flsh.model.UniversityYear;

@Controller
public class DeliberationController {
	
	@Autowired
	PeriodService periodService;
	
	@Autowired 
	DeliberationService delibService;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	TeachingService teachingService;
	
	@RequestMapping(value = "/educations/deliberation", method = RequestMethod.GET)
	public ModelAndView startDelib(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("deliberation/accueil");
		mav.addObject("univYears", periodService.getAllUnivYears());
		mav.addObject("menu", "home");
		mav.addObject("submenu", "deliberation");
		return mav;
	}
	
	@RequestMapping(value = "/deliberation", method = RequestMethod.GET)
	@ResponseBody
	ModelAndView getDelib(HttpServletRequest request, HttpServletResponse response) {
	    int univYearId = request.getParameter("univYear") == ""   ? 0 : Integer.parseInt(request.getParameter("univYear"));
		ModelAndView mav = new ModelAndView("deliberation/detail");
		UniversityYear detailUnivYear = delibService.getDetailUnivYear(univYearId);
		
		List<Level> levels = periodService.getAllLevels();
		
		mav.addObject("students", studentService.getAllStudents());
		mav.addObject("univ_year", detailUnivYear);
		mav.addObject("levels", levels);
	    return mav;
	}
	
	@RequestMapping(value = "/deliberation/students_list", method = RequestMethod.GET)
	@ResponseBody
	ModelAndView getDelibForStudentId(HttpServletRequest request, HttpServletResponse response) {
		int univYearId = request.getParameter("univYear") == ""   ? 0 : Integer.parseInt(request.getParameter("univYear"));
	    int idParcours = request.getParameter("idParcours") == ""   ? 0 : Integer.parseInt(request.getParameter("idParcours"));
		ModelAndView mav = new ModelAndView("deliberation/students_list");
		
		List<Student> students = studentService.getStudentsByUnivYearAndParcours(univYearId, idParcours);

		mav.addObject("students", students);
	    return mav;
	}
	
	@RequestMapping(value = "/deliberation/deliberation_list", method = RequestMethod.GET)
	@ResponseBody
	ModelAndView getDelibList(HttpServletRequest request, HttpServletResponse response) {
		int univYearId = request.getParameter("univYear") == ""   ? 0 : Integer.parseInt(request.getParameter("univYear"));
		int idStudent = request.getParameter("idStudent") == "" ? 0 : Integer.parseInt(request.getParameter("idStudent"));
		int idPeriodOne = request.getParameter("idPeriodOne") == "" ? 0 : Integer.parseInt(request.getParameter("idPeriodOne"));
		int idPeriodTwo = request.getParameter("idPeriodTwo") == "" ? 0 : Integer.parseInt(request.getParameter("idPeriodTwo"));
		
		ModelAndView mav = new ModelAndView("deliberation/deliberation_list");
	    
		List<Deliberation> delibPeriodOne = delibService.getDelibByUYAndStdAndPerId(univYearId, idStudent, idPeriodOne);
		List<Deliberation> delibPeriodTwo = delibService.getDelibByUYAndStdAndPerId(univYearId, idStudent, idPeriodTwo);
        TotalCredit totalCredit = delibService.getTotalCredit(univYearId, idStudent);
		
		mav.addObject("delibs_period_one", delibPeriodOne);
		mav.addObject("delibs_period_two", delibPeriodTwo);
		mav.addObject("total", totalCredit);
	    return mav;
	}
	
	@RequestMapping(value = "/deliberation/student_detail", method = RequestMethod.GET)
	@ResponseBody
	ModelAndView getStudentDetail(HttpServletRequest request, HttpServletResponse response) {
		int idStudent = request.getParameter("idStudent") == "" ? 0 : Integer.parseInt(request.getParameter("idStudent"));
	    ModelAndView mav = new ModelAndView("deliberation/student_detail");
	    
	    Student student = studentService.getStudentById(idStudent);
		mav.addObject("student", student);
	    return mav;
	}
	
	@RequestMapping(value = "/deliberation/periods_list", method = RequestMethod.GET)
	@ResponseBody
	ModelAndView getPeriodsList(HttpServletRequest request, HttpServletResponse response) {
		int idLevel = request.getParameter("idLevel") == "" ? 0 : Integer.parseInt(request.getParameter("idLevel"));
	    ModelAndView mav = new ModelAndView("deliberation/periods_list");
	    
	    List<PeriodLibelle> periods = delibService.getDelibByIdLevel(idLevel);
		mav.addObject("periods", periods);
	    return mav;
	}	

}
