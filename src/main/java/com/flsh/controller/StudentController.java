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
import com.flsh.interfaces.StudentService;
import com.flsh.model.Student;

@Controller
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	PeriodService periodService;

	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public ModelAndView listStudents(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("students/accueil");
		mav.addObject("univYears", periodService.getAllUnivYears());
		mav.addObject("levels", periodService.getAllLevels());
		mav.addObject("students", studentService.getAllStudents());
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/student/details", method = RequestMethod.GET)
	public String detailsStudent(HttpServletRequest request, HttpServletResponse response) {
		int id = !request.getParameter("id").equals("") ? Integer.parseInt(request.getParameter("id")) : 0;
		Student student = studentService.getStudentById(id);
		JSONObject rtn = new JSONObject();
		rtn.put("status", student == null ? 0 : 1);
		rtn.put("message", student == null ? "Echec de la récupération des infos de l'étudiant" : "Infos récupérées");
		if(student != null) {
			JSONObject infos = new JSONObject();
			infos.put("civilite", student.getStudent_civ());
			infos.put("nom", student.getStudent_name());
			infos.put("prenom", student.getStudent_lastname());
			infos.put("datenaissance", student.getStudent_birthdate());
			infos.put("nationalite", student.getStudent_nationality());
			infos.put("passeport", student.getStudent_passport());
			infos.put("cin", student.getStudent_cin());
			infos.put("datecin", student.getStudent_cindate());
			infos.put("lieucin", student.getStudent_cinlocation());
			infos.put("adresse", student.getStudent_adress());
			infos.put("email", student.getStudent_email());
			infos.put("dernieretab", student.getStudent_lastetab());
			infos.put("conjoint", student.getStudent_nameconjoint());
			infos.put("pere", student.getStudent_namefather());
			infos.put("professionpere", student.getStudent_jobfather());
			infos.put("mere", student.getStudent_namemother());
			infos.put("professionmere", student.getStudent_jobmother());
			infos.put("id", student.getStudent_id());
			rtn.put("infos", infos);
		}
		return rtn.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/student/save", method = RequestMethod.POST)
	public String saveStudent(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("student") Student student) {
		JSONObject rtn;
		if(student.getStudent_id() == 0) {
			int idUY = !request.getParameter("subs_iduy").equals("") ? Integer.parseInt(request.getParameter("subs_iduy")) : 0;
			int idLevel = !request.getParameter("subs_level").equals("") ? Integer.parseInt(request.getParameter("subs_level")) : 0;
			int idPrc = !request.getParameter("subs_parcours").equals("") ? Integer.parseInt(request.getParameter("subs_parcours")) : 0;
			int paid = !request.getParameter("subs_inscription").equals("") ? Integer.parseInt(request.getParameter("subs_inscription")) : 0;
			rtn = studentService.saveStudent(student, idUY, idLevel, idPrc, paid);
		} else {
			rtn = studentService.saveStudent(student);
		}
		return rtn.toString();
	}
	
	@RequestMapping(value = "/student/loadParcoursByLevel", method = RequestMethod.GET)
	public ModelAndView loadParcoursByLevel(HttpServletRequest request, HttpServletResponse response) {
		int idLevel = !request.getParameter("id").equals("") ? Integer.parseInt(request.getParameter("id")) : 0;
		ModelAndView mav = new ModelAndView("students/option_parcours");
		mav.addObject("parcours", periodService.getParcoursByLevelId(idLevel));
		return mav;
	}
	
	@RequestMapping(value = "/student/loadUEToChooseByParcours", method = RequestMethod.GET)
	public ModelAndView loadUEChoiceByParcours(HttpServletRequest request, HttpServletResponse response) {
		int idParcours = !request.getParameter("id").equals("") ? Integer.parseInt(request.getParameter("id")) : 0;
		ModelAndView mav = new ModelAndView("students/ue_choice");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/student/delete", method = RequestMethod.GET)
	public String deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		int idStudent = !request.getParameter("id").equals("") ? Integer.parseInt(request.getParameter("id")) : 0;
		JSONObject rtn = studentService.deleteStudent(idStudent);
		return rtn.toString();
	}
}
