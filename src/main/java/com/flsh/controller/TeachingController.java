package com.flsh.controller;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.interfaces.PeriodService;
import com.flsh.interfaces.TeachingService;
import com.flsh.model.StudyUnit;
import com.flsh.model.Course;
import com.flsh.model.Level;
import com.flsh.model.Parcours;
import com.flsh.model.Professor;

@Controller
public class TeachingController {
	
	@Autowired
	TeachingService teachingService;
	
	@Autowired
	PeriodService periodService;
	
	@RequestMapping(value = "/ue", method = RequestMethod.GET)
	public ModelAndView showTeaching(HttpServletRequest request, HttpServletResponse response) {
		List<Level> levels = periodService.getAllLevels();
		List<Professor> profs = teachingService.getAllProfessor();
		ModelAndView teaching = new ModelAndView("ue/accueil");
	    teaching.addObject("levels", levels);
	    teaching.addObject("profs", profs);
	    return teaching;
	}
	
	@RequestMapping(value = "/ue/list", method = RequestMethod.GET)
	public ModelAndView loadUEList(HttpServletRequest request, HttpServletResponse response) {
		int idParcours = request.getParameter("idParcours") == null || request.getParameter("idParcours") == "" ? 0 : Integer.parseInt(request.getParameter("idParcours"));
		HashSet<StudyUnit> units = teachingService.getUnitsByParcours(idParcours);
		ModelAndView teaching = new ModelAndView("ue/parcours_list");
	    teaching.addObject("units", units);
	    return teaching;
	}
	
	@RequestMapping(value = "/ue/details", method = RequestMethod.GET)
	@ResponseBody
	public String getUeDetails(HttpServletRequest request, HttpServletResponse response) {
	  int id = request.getParameter("id") == null || request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
	  StudyUnit teaching_detail = teachingService.getUeDetails(id);
	  JSONObject rtn = new JSONObject();
	  rtn.put("id", teaching_detail.getStudyunit_id());
	  rtn.put("id_parcours", teaching_detail.getParcours_id());
	  rtn.put("type", teaching_detail.getStudyunit_type());
	  rtn.put("libelle", teaching_detail.getStudyunit_libelle());
	  return rtn.toString();
	}
	
	@RequestMapping(value = "/ue/save", method = RequestMethod.POST)
	@ResponseBody   
	public String saveTeaching(HttpServletRequest request, HttpServletResponse response) {
		StudyUnit studyunit = new StudyUnit(); 
		int idUE = request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		int idParcoursUE = request.getParameter("id_parcours") == "" ? 0 : Integer.parseInt(request.getParameter("id_parcours"));
		String type = request.getParameter("type");
		String libelle = request.getParameter("libelle");
		// using the setter method
		studyunit.setStudyunit_id(idUE);
		studyunit.setParcours_id(idParcoursUE);
		studyunit.setStudyunit_libelle(libelle);
		studyunit.setStudyunit_type(type);
		System.out.print(studyunit);
		JSONObject rtn = teachingService.saveStudyUnit(studyunit);
  		return rtn.toString();
	}
	
	@RequestMapping(value = "/ue/delete", method = RequestMethod.GET)
	@ResponseBody
	public String deleteTeaching(HttpServletRequest request, HttpServletResponse response) {
		int id = request.getParameter("id") == ""   ? 0 : Integer.parseInt(request.getParameter("id"));
		JSONObject rtn = teachingService.deleteStudyUnit(id);
		return rtn.toString();
	}
	@RequestMapping(value = "/ec/details", method = RequestMethod.GET)
	@ResponseBody
	public String getEcDetails(HttpServletRequest request, HttpServletResponse response) {
	  int id = request.getParameter("id") == null || request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
	  Course course_detail = teachingService.getEcDetails(id);
	  JSONObject rtn = new JSONObject();
	  rtn.put("id", course_detail.getCourse_id());
	  rtn.put("id_ue", course_detail.getStudyunit_id());
	  rtn.put("id_prof", course_detail.getProfessor_id());
	  rtn.put("coefficient", course_detail.getCourse_coefficient());
	  rtn.put("notation", course_detail.getCourse_notation());
	  rtn.put("credit", course_detail.getCourse_credit());
	  rtn.put("personnel", course_detail.getCourse_travailpersonnel());
	  rtn.put("presenciel", course_detail.getCourse_travailpresenciel());
	  rtn.put("libelle", course_detail.getCourse_libelle());
	  rtn.put("horaire", course_detail.getCourse_volumehoraire());
	  return rtn.toString();
	}
	
	@RequestMapping(value = "/ec/save", method = RequestMethod.POST)
	@ResponseBody   
	public String saveCourse(HttpServletRequest request, HttpServletResponse response) {
		int id = request.getParameter("id") == null || request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		int idProf = request.getParameter("id_prof") == null || request.getParameter("id_prof") == "" ? 0 : Integer.parseInt(request.getParameter("id_prof"));
		int idUe = request.getParameter("id_ue") == null || request.getParameter("id_ue") == "" ? 0 : Integer.parseInt(request.getParameter("id_ue"));
		String libelle = request.getParameter("libelle");
		int credit = Integer.parseInt(request.getParameter("credit"));
		int notation = Integer.parseInt(request.getParameter("notation"));
		double coefficient = Double.parseDouble(request.getParameter("coefficient"));
		String volume_horaire = request.getParameter("horaire");
		double presence = Double.parseDouble(request.getParameter("presenciel"));
		double personnel = Double.parseDouble(request.getParameter("personnel"));
		// setter for course
		Course course = new Course();
		course.setCourse_id(id);
		course.setProfessor_id(idProf);
		course.setStudyunit_id(idUe);
		course.setCourse_libelle(libelle);
		course.setCourse_credit(credit);
		course.setCourse_notation(notation);
		course.setCourse_coefficient(coefficient);
		course.setCourse_volumehoraire(volume_horaire);
		course.setCourse_travailpersonnel(personnel);
		course.setCourse_travailpresenciel(presence);
		System.out.print(course);
		JSONObject rtn = teachingService.saveCourse(course);
  		return rtn.toString();
	}
	
	@RequestMapping(value = "/ec/delete", method = RequestMethod.GET)
	@ResponseBody
	public String deleteCourse(HttpServletRequest request, HttpServletResponse response) {
		int id = request.getParameter("id") == ""   ? 0 : Integer.parseInt(request.getParameter("id"));
		JSONObject rtn = teachingService.deleteCourse(id);
		return rtn.toString();
	}
}
