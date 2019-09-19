package com.flsh.controller;


import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.interfaces.DeliberationService;
import com.flsh.interfaces.PeriodService;
import com.flsh.interfaces.StudentService;
import com.flsh.model.Cycles;
import com.flsh.model.EvaluationUEECStudent;
import com.flsh.model.Level;
import com.flsh.model.Parcours;
import com.flsh.model.Student;
import com.flsh.model.UniversityYear;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ExportPdfController {

	@Autowired
	StudentService studentService;
	
	@Autowired
	PeriodService periodService;
	
	@Autowired 
	DeliberationService delibService;
	
	@RequestMapping(value = "/students/result", method = RequestMethod.GET)
	@ResponseBody
	ModelAndView exportPdfResultExamStudent(HttpServletRequest request, HttpServletResponse response){
		int idStudent = request.getParameter("idStudent") == ""   ? 0 : Integer.parseInt(request.getParameter("idStudent"));
		int idUY = request.getParameter("idUY") == ""   ? 0 : Integer.parseInt(request.getParameter("idUY"));
		int idLevel = periodService.getIdLevelByIdStudentAndIdUY(idStudent, idUY);
		int idCycle = periodService.getIdCycleByIdStudentAndIdUY(idStudent, idUY);
		
		Student student = studentService.getStudentById(idStudent);
		UniversityYear univYearStudent = periodService.getUnivYearById(idUY);
		Level levelStudent = periodService.getLevelById(idLevel);
		Parcours parcoursStudent = periodService.getParcoursByAUAndLevelAndStudentId(idUY, idLevel, idStudent);
		Cycles cycleStudent = periodService.getCyclesById(idCycle);
		String delibDecisionCurrentUser = delibService.getDelibDecisionCurrentUser(idUY, idLevel, idStudent);
		int total = 0;
		
		List<EvaluationUEECStudent> dataEvaluationsStudent = delibService.getInfosEvaluationsByStudentLevelUnivYearAndParcours(idUY, idStudent, idLevel, parcoursStudent.getParcoursId());
		
		ModelAndView mavExportPdf = new ModelAndView("export_pdf/acceuil");

		// Load all of these datas into the acceuil
		mavExportPdf.addObject("student", student);
		mavExportPdf.addObject("universityYearStudent", univYearStudent);
		mavExportPdf.addObject("cycleStudent", cycleStudent);
		mavExportPdf.addObject("levelStudent", levelStudent);
		mavExportPdf.addObject("delibCurrentUser", delibDecisionCurrentUser);
		
		mavExportPdf.addObject("parcoursStudent", parcoursStudent);
		mavExportPdf.addObject("dataEvaluationsStudent", dataEvaluationsStudent);
		mavExportPdf.addObject("periodesStudent", periodService.getNiveauPeriodsById(idLevel, idUY));
		mavExportPdf.addObject("dateNow", LocalDate.now());
		
		return mavExportPdf;
    }	
}