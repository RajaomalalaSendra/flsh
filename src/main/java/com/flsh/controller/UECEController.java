package com.flsh.controller;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.interfaces.TeachingService;
import com.flsh.model.StudyUnits;
import com.flsh.model.Course;

@Controller
public class UECEController {
	@Autowired
	TeachingService teachingService;
 
	@RequestMapping(value = "/uece", method = RequestMethod.GET)
	  public ModelAndView showTeaching(HttpServletRequest request, HttpServletResponse response) {
		HashSet<StudyUnits> units=teachingService.getAllUnits();
		ModelAndView teaching = new ModelAndView("uece/alluece");
	    teaching.addObject("units", units);
	    return teaching;
	  }
	/*
	@RequestMapping(value = "/uece/details/ue", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView detailsUeTeaching(HttpServletRequest request, HttpServletResponse response) {
		int id = request.getParameter("id") == null || request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		StudyUnits detailsUe = teachingService.detailsUE(id);
		JSONObject rtn = new JSONObject();
		rtn.put("id", detailsUe.getStudyUnitsId());
		rtn.put("parcour_id", detailsUe.getParcoursId());
		rtn.put("libelle", detailsUe.getStudyunitsLibelle());
		rtn.put("type", detailsUe.getStudyunitsType());
  		return rtn;
	}
	@RequestMapping(value = "/uece/save/ue", method = RequestMethod.POST)
	@ResponseBody   
	public ModelAndView saveTeaching(HttpServletRequest request, HttpServletResponse response) {
		int id = request.getParameter("id") == null || request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		StudyUnits ue = teachingService.detailsUE(id); 
		JSONObject rtn = teachingService.saveUE(ue);
  		return rtn.toString();
	}
*/
}
