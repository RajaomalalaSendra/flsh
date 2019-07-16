package com.flsh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.interfaces.EducationService;
import com.flsh.interfaces.NoteService;
import com.flsh.interfaces.PeriodService;
import com.flsh.interfaces.TeachingService;
import com.flsh.model.User;

@Controller
public class NotesController {
	
	@Autowired
	TeachingService techingService;
	
	@Autowired
	PeriodService periodService;
	
	@Autowired
	NoteService noteService;
	
	@RequestMapping(value = "/educations/notes", method = RequestMethod.GET)
	public ModelAndView manageNotes(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView("notes/accueil");
		if(request.getParameter("saisie_mode") == null || request.getParameter("univYear") == null || (request.getParameter("prof") == null && request.getParameter("level") == null)) {
			mav.addObject("univYears", periodService.getAllUnivYears());
			mav.addObject("professors", techingService.getAllProfessor());
			mav.addObject("levels", periodService.getAllLevels());
		} else if(request.getParameter("saisie_mode").equals("prof") && !request.getParameter("univYear").equals("") && !request.getParameter("prof").equals("")) {
			int idProf = Integer.parseInt(request.getParameter("prof"));
			int idUY = Integer.parseInt(request.getParameter("univYear"));
			System.out.print("id de l'enseignant");
			mav = new ModelAndView("notes/saisie_prof");
			mav.addObject("infos_uy", periodService.getUnivYearById(idUY));
			mav.addObject("ecs", noteService.getECProfessor(idProf));
			
		} else if(request.getParameter("saisie_mode").equals("level") && !request.getParameter("univYear").equals("") && !request.getParameter("level").equals("")) {
			int idLevel = Integer.parseInt(request.getParameter("level"));
			int idUY = Integer.parseInt(request.getParameter("univYear"));
			System.out.print("id de l'enseignant");
			mav = new ModelAndView("notes/saisie_level");
			
		} 
		
		mav.addObject("menu", "home");
		mav.addObject("submenu", "notes");
		return mav;
	}

}
