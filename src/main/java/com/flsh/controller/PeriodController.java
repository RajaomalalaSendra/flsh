package com.flsh.controller;

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
import com.flsh.model.Period;
import com.flsh.model.UniversityYear;

@Controller
public class PeriodController {

	@Autowired
	PeriodService periodService;
	
	
	@RequestMapping(value = "/educations/periods/", method = RequestMethod.GET)
	public ModelAndView accueilPeriods(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("educations/periods");
		mav.addObject("listAnneeU", periodService.getAllUnivYears()); 
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/educations/saveAU", method = RequestMethod.POST)
	public String saveAU(HttpServletRequest request, HttpServletResponse response, @ModelAttribute UniversityYear universityYear) {
		System.out.print(universityYear.toString());
		JSONObject retrn = periodService.saveUnivYear(universityYear);
		return retrn.toString();
	}
	

	@ResponseBody
	@RequestMapping(value = "/educations/detailsAU", method = RequestMethod.GET)
	public String detailsAU(HttpServletRequest request, HttpServletResponse response) {
		int idUnivYear = request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		UniversityYear uy = periodService.getUnivYearById(idUnivYear);
		JSONObject rtn = new JSONObject();
		rtn.put("status", uy == null ? 0 : 1);
		rtn.put("message", uy == null ? "Echec du chargement des infos de l'année scolaire!" : "Infos récupérées!");
		if(uy != null) {
			JSONObject infos = new JSONObject();
			infos.put("year_id", uy.getUniversity_year_id());
			infos.put("year_libelle", uy.getUniversity_year_libelle());
			infos.put("year_beginning", uy.getUniversity_year_beginning());
			infos.put("year_ending", uy.getUniversity_year_ending());
			rtn.put("infos", infos);
		}
		return rtn.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/educations/deletePeriod", method = RequestMethod.POST)
	public String deletePeriod(HttpServletRequest request, HttpServletResponse response) {
		int id = request.getParameter("idItemDeletePeriod") == "" ? 0 : Integer.parseInt(request.getParameter("idItemDeletePeriod"));
		String type = request.getParameter("typeDeletionPeriod");
		JSONObject rtn;
		if(type.equals("periode")) {
			rtn = periodService.deletePeriod(id);
		} else {
			rtn = periodService.deleteUnivYear(id);
		}
		return rtn.toString();
	}
	
	@RequestMapping(value = "/educations/managePeriods", method = RequestMethod.GET)
	public ModelAndView managePeriods(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("educations/manage_period");
		int idUnivYear = request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		mav.addObject("au", periodService.getUnivYearById(idUnivYear)); 
		mav.addObject("levels", periodService.getAllLevels());
		return mav;
	}
	
	@RequestMapping(value = "/educations/loadPeriodsNiveau", method = RequestMethod.GET)
	public ModelAndView loadPeriods(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("educations/list_period");
		int idLevel = request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		int idAU = request.getParameter("au") == "" ? 0 : Integer.parseInt(request.getParameter("au"));
		mav.addObject("periods", periodService.getNiveauPeriodsById(idLevel, idAU)); 
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/educations/saveLevelPeriod", method = RequestMethod.POST)
	public String saveLevelPeriod(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("period") Period period ) {
		System.out.print(period);
		JSONObject rtn = periodService.savePeriod(period);
		return rtn.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/educations/detailsPeriod", method = RequestMethod.GET)
	public String detailsPeriod(HttpServletRequest request, HttpServletResponse response) {
		int idPer = request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		Period per = periodService.getPeriodById(idPer);
		JSONObject rtn = new JSONObject();
		rtn.put("status", per == null ? 0 : 1);
		rtn.put("message", per == null ? "Echec du chargement des infos de la période!" : "Infos récupérées!");
		if(per != null) {
			JSONObject infos = new JSONObject();
			infos.put("per_id", per.getPeriod_id());
			infos.put("per_libellecourt", per.getPeriod_libellecourt());
			infos.put("per_libellelong", per.getPeriod_libellelong());
			infos.put("per_debut", per.getPeriod_debut());
			infos.put("per_fin", per.getPeriod_fin());
			infos.put("per_arattr", per.isA_ratrappage());
			infos.put("exam_libelle", per.getExam_libelle());
			infos.put("exam_debut", per.getExam_debut());
			infos.put("exam_fin", per.getExam_fin());
			infos.put("rattr_libelle", per.getRattr_libelle());
			infos.put("rattr_debut", per.getRattr_debut());
			infos.put("rattr_fin", per.getRattr_fin());
			rtn.put("infos", infos);
		}
		return rtn.toString();
	}
}
