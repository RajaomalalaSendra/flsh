package com.flsh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.interfaces.EducationService;

@Controller
public class EducationController {

	@Autowired
	EducationService educationService;
	
	@RequestMapping(value = "/educations/cyclesandlevel/", method = RequestMethod.GET)
	public ModelAndView manageLevels(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("educations/accueil");
		mav.addObject("listCycle", educationService.getAllCycle());
		HttpSession session = request.getSession();
		mav.addObject("username", session.getAttribute("username") );
		mav.addObject("menu", "home");
		mav.addObject("submenu", "cycle_level_parcours");
		return mav;
	}
	
	@RequestMapping(value = "/education/saveCycle", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String saveCycle(HttpServletRequest request, HttpServletResponse response) {
		int idCycle = request.getParameter("idCycle") == "" ? 0 : Integer.parseInt(request.getParameter("idCycle"));
		int idLevel = request.getParameter("idLevel") == "" ? 0 : Integer.parseInt(request.getParameter("idLevel"));
		boolean save;
		String libelle = request.getParameter("labelCycle");
		switch(request.getParameter("type")) {
			case "level" : 
				save = educationService.saveLevel(libelle, idCycle, idLevel);
				break;
			case "parcours" : 
				int idParcours = request.getParameter("idParcours") == "" ? 0 : Integer.parseInt(request.getParameter("idParcours"));
				save = educationService.saveParcours(libelle, idLevel, idParcours);
				break;
			default:
				save = educationService.saveCycle(libelle, idCycle);
				break;
				
		}
		System.out.print("Saving "+save);
		JSONObject rtn = new JSONObject();
	    rtn.put("status", save ? 1 : 0);
	    rtn.put("message", save ? "Enregistré avec succès" : "Echec de l'enregistrement! Veuillez réessayer");
		return rtn.toString();
	}
	
	@RequestMapping(value = "/education/deleteCycle", method = RequestMethod.GET)
	@ResponseBody
	public String deleteCycle(HttpServletRequest request, HttpServletResponse response) {
		int id = request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		JSONObject rtn = educationService.deleteCycle(id);
		return rtn.toString();
	}
	
	@RequestMapping(value = "/education/deleteLevel", method = RequestMethod.GET)
	@ResponseBody
	public String deleteLevel(HttpServletRequest request, HttpServletResponse response) {
		int id = request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		JSONObject rtn = educationService.deleteLevel(id);
		return rtn.toString();
	}
	
	@RequestMapping(value = "/education/deleteParcours", method = RequestMethod.GET)
	@ResponseBody
	public String deleteParcours(HttpServletRequest request, HttpServletResponse response) {
		int id = request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		JSONObject rtn = educationService.deleteParcours(id);
		return rtn.toString();
	}
}
