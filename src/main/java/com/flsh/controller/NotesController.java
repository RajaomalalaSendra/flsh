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

import com.flsh.interfaces.NoteService;
import com.flsh.interfaces.PeriodService;
import com.flsh.interfaces.TeachingService;
import com.flsh.model.Professor;
import com.flsh.model.User;

@Controller
public class NotesController {
	
	@Autowired
	TeachingService teachingService;
	
	@Autowired
	PeriodService periodService;
	
	@Autowired
	NoteService noteService;
	
	@RequestMapping(value = "/educations/notes", method = RequestMethod.GET)
	public ModelAndView manageNotes(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView("notes/accueil");
		User user = (User) session.getAttribute("user");
		if(request.getParameter("saisie_mode") == null || request.getParameter("univYear") == null || (request.getParameter("prof") == null && request.getParameter("level") == null)) {
			mav.addObject("univYears", periodService.getAllUnivYears());
			mav.addObject("professors", teachingService.getAllProfessor());
			mav.addObject("levels", periodService.getAllLevels());
			mav.addObject("isProf", user == null ? false : user.getType().equals("2"));
			if(user != null && user.getType().equals("2")) {
				mav.addObject("prof", teachingService.getProfessorByUserId(user.getId()));
			}
		} else if(request.getParameter("saisie_mode").equals("prof") && !request.getParameter("univYear").equals("") && !request.getParameter("prof").equals("")) {
			int idProf = Integer.parseInt(request.getParameter("prof"));
			int idUY = Integer.parseInt(request.getParameter("univYear"));
			
			mav = new ModelAndView("notes/saisie_prof");
			mav.addObject("isProf", user == null ? false : user.getType().equals("2"));
			if(user != null && user.getType().equals("2")) {
				Professor prof = teachingService.getProfessorByUserId(user.getId());
				idProf = prof.getProfessor_id();
			}
			mav.addObject("infos_uy", periodService.getUnivYearById(idUY));
			mav.addObject("ecs", noteService.getECProfessor(idProf));
			mav.addObject("professors", teachingService.getAllProfessor());
			mav.addObject("profSelected", idProf);
		} else if(request.getParameter("saisie_mode").equals("level") && !request.getParameter("univYear").equals("") && !request.getParameter("level").equals("")) {
			int idLevel = Integer.parseInt(request.getParameter("level"));
			int idUY = Integer.parseInt(request.getParameter("univYear"));
			mav = new ModelAndView("notes/saisie_level");
			mav.addObject("levels", periodService.getAllLevels());
			mav.addObject("levelSelected", idLevel);
			mav.addObject("infos_uy", periodService.getUnivYearById(idUY));
		}
		mav.addObject("menu", "home");
		mav.addObject("submenu", "notes");
		return mav;
	}
	
	@RequestMapping(value = "/educations/getEcProf", method = RequestMethod.GET)
	public ModelAndView getEcProf(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView("notes/ec_prof");
		int idProf = request.getParameter("idProf") != null && !request.getParameter("idProf").equals("") ? Integer.parseInt(request.getParameter("idProf")) : 0;
		mav.addObject("ecs", noteService.getECProfessor(idProf));
		return mav;
	}
	
	@RequestMapping(value = "/educations/getEcParcours", method = RequestMethod.GET)
	public ModelAndView getEcParcours(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView("notes/ec_prof");
		int idPrc = request.getParameter("idPrc") != null && !request.getParameter("idPrc").equals("") ? Integer.parseInt(request.getParameter("idPrc")) : 0;
		mav.addObject("ecs", noteService.getECParcours(idPrc));
		return mav;
	}
	
	@RequestMapping(value = "/educations/getEcEvaluations", method = RequestMethod.GET)
	public ModelAndView getEcEvaluations(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView("notes/ec_eval");
		int idEC = request.getParameter("idEC") != null && !request.getParameter("idEC").equals("") ? Integer.parseInt(request.getParameter("idEC")) : 0;
		int idUY = request.getParameter("idUY") != null && !request.getParameter("idUY").equals("") ? Integer.parseInt(request.getParameter("idUY")) : 0;
		mav.addObject("exams", noteService.getExamsByECandUY(idEC, idUY));
		mav.addObject("infosEC", teachingService.getEcDetails(idEC));
		mav.addObject("students", noteService.getStudentsandEvalsByECandUY(idEC, idUY));
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/educations/saveMoyStudent", method = RequestMethod.POST)
	public String saveMoyStudent(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView("notes/ec_prof");
		int idStudent = request.getParameter("idStudent") != null && !request.getParameter("idStudent").equals("") ? Integer.parseInt(request.getParameter("idStudent")) : 0;
		int idPer = request.getParameter("idPer") != null && !request.getParameter("idPer").equals("") ? Integer.parseInt(request.getParameter("idPer")) : 0;
		int idEC = request.getParameter("idEC") != null && !request.getParameter("idEC").equals("") ? Integer.parseInt(request.getParameter("idEC")) : 0;
		int idExam = request.getParameter("idExam") != null && !request.getParameter("idExam").equals("") ? Integer.parseInt(request.getParameter("idExam")) : 0;
		String noteVal  = request.getParameter("noteVal");
		JSONObject rtn = noteService.saveMoyExamStudent(idStudent, idExam, idPer, idEC, noteVal);
		return rtn.toString();
	}
}
