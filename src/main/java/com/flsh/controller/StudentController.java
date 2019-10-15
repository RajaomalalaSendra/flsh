package com.flsh.controller;



import java.util.List;
import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.flsh.model.Level;
import com.flsh.model.Student;
import com.flsh.model.StudyUnit;

@Controller
public class StudentController {
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	PeriodService periodService;

	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public ModelAndView listStudents(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("students/accueil");
		mav.addObject("menu", "student");
		mav.addObject("submenu", "all_students");
		mav.addObject("univYears", periodService.getAllUnivYears());
		mav.addObject("levels", periodService.getAllLevels());
		mav.addObject("students", studentService.getAllStudents());
		mav.addObject("number", studentService.getStudentsNumber());
		mav.addObject("sc", request.getSession().getServletContext());
		return mav;
	}
	
	@RequestMapping(value = "/students/bypage", method = RequestMethod.GET)
	public ModelAndView listStudentsByPage(HttpServletRequest request, HttpServletResponse response) {
		int numPage = !request.getParameter("page").equals("") ? Integer.parseInt(request.getParameter("page")) : 1;
		ModelAndView mav = new ModelAndView("students/list_students");
		mav.addObject("students", studentService.getStudentsByPage(numPage));
		mav.addObject("sc", request.getSession().getServletContext());
		return mav;
	}
	
	@RequestMapping(value = "/students/search", method = RequestMethod.GET)
	public ModelAndView searchStudentsByPage(HttpServletRequest request, HttpServletResponse response) {
		int numPage = request.getParameter("page") != null && !request.getParameter("page").equals("") ? Integer.parseInt(request.getParameter("page")) : 1;
		String criteria = request.getParameter("criteria");
		ModelAndView mav = new ModelAndView("students/list_students");
		mav.addObject("students", studentService.getStudentsByCriteria(criteria, numPage));
		mav.addObject("sc", request.getSession().getServletContext());
		if(numPage == 1) mav.addObject("isSearch", true);
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
			infos.put("imageurl", student.getCroppedImageURL(request.getSession().getServletContext()));
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
			String dateInscription = request.getParameter("subs_date");
			
			HashSet<StudyUnit> ueList = studentService.getParcoursChoiceUnits(idPrc);
			String choixprc = "";
		
			for(StudyUnit ue : ueList) {
				if(choixprc.equals("")) {
					choixprc = ue.getStudyunit_id()+"_"+request.getParameter(""+ue.getStudyunit_id());
				} else {
					choixprc += ";"+ue.getStudyunit_id()+"_"+request.getParameter(""+ue.getStudyunit_id());
				}
			}
			rtn = studentService.saveStudent(student, idUY, idLevel, idPrc, paid, dateInscription, choixprc);
		} else {
			rtn = studentService.saveStudent(student);
		}
		if (rtn.getInt("status") != 0 && request.getParameter("cropped") != null) {
			student.setStudent_id(rtn.getInt("idEt"));
			student.setImage_cropped(request.getParameter("cropped"));
			studentService.getCroppedImageUrl(student);
		}
		return rtn.toString();
	}
	
	@RequestMapping(value = "/student/loadParcoursByLevel", method = RequestMethod.GET)
	public ModelAndView loadParcoursByLevel(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		int idLevel = !request.getParameter("id").equals("") ? Integer.parseInt(request.getParameter("id")) : 0;
		ModelAndView mav = new ModelAndView("students/option_parcours");
		mav.addObject("idPrc", session.getAttribute("idPrc"));
		mav.addObject("parcours", periodService.getParcoursByLevelId(idLevel));
		return mav;
	}
	
	@RequestMapping(value = "/student/loadUEToChooseByParcours", method = RequestMethod.GET)
	public ModelAndView loadUEChoiceByParcours(HttpServletRequest request, HttpServletResponse response) {
		int idParcours = !request.getParameter("id").equals("") ? Integer.parseInt(request.getParameter("id")) : 0;
		HashSet<StudyUnit> ueList = studentService.getParcoursChoiceUnits(idParcours);
		ModelAndView mav = new ModelAndView("students/ue_choice");
		mav.addObject("ueList", ueList);
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/student/delete", method = RequestMethod.GET)
	public String deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		int idStudent = !request.getParameter("id").equals("") ? Integer.parseInt(request.getParameter("id")) : 0;
		JSONObject rtn = studentService.deleteStudent(idStudent);
		return rtn.toString();
	}
	
	@RequestMapping(value = "/students/subscriptions", method = RequestMethod.GET)
	public ModelAndView subscriptionStudent(HttpServletRequest request, HttpServletResponse response) {
		List<Student> students = studentService.getAllStudents();
		List<Level> levels = periodService.getAllLevels();
		System.out.print("\n=====================\nTotal delib: " + studentService.getTotalNumberDelib(students, levels) + "\n=====================");
		
		ModelAndView mav = new ModelAndView("students/accueil_inscription");
		mav.addObject("menu", "student");
		mav.addObject("submenu", "subscription");
		mav.addObject("univYears", periodService.getAllUnivYears());
		mav.addObject("levels", levels);
		mav.addObject("total_number_delib", studentService.getTotalNumberDelib(students, levels));
		mav.addObject("allParcours", studentService.getAllParcours());
		mav.addObject("sc", request.getSession().getServletContext());
		return mav;
	}
	
	@RequestMapping(value = "/students/loadStudentsByUnivYearAndLevel", method = RequestMethod.POST)
	public ModelAndView loadStudentsByUnivYearAndLevel(HttpServletRequest request, HttpServletResponse response) {
		int idUY = !request.getParameter("idUY").equals("") ? Integer.parseInt(request.getParameter("idUY")) : 0;
		int idLevel = !request.getParameter("idLevel").equals("") ? Integer.parseInt(request.getParameter("idLevel")) : 0;
		int numPage = request.getParameter("page") != null && !request.getParameter("page").equals("") ? Integer.parseInt(request.getParameter("page")) : 1;
		
		ModelAndView mav = new ModelAndView("students/level_students");
		mav.addObject("students", studentService.getStudentsByUnivYearAndLevel(idUY, idLevel, numPage));
		if(numPage == 1) mav.addObject("showPage", true);
		mav.addObject("sc", request.getSession().getServletContext());
		mav.addObject("idLevel", idLevel);
		mav.addObject("idUY", idUY);
		return mav;
	}
	
	@RequestMapping(value = "/students/searchSubscribed", method = RequestMethod.GET)
	public ModelAndView searchInSubscribedStudents(HttpServletRequest request, HttpServletResponse response) {
		int idUY = !request.getParameter("idUY").equals("") ? Integer.parseInt(request.getParameter("idUY")) : 0;
		int idLevel = !request.getParameter("idLevel").equals("") ? Integer.parseInt(request.getParameter("idLevel")) : 0;
		String searchCriteria = request.getParameter("criteria");
		int numPage = request.getParameter("page") != null && !request.getParameter("page").equals("") ? Integer.parseInt(request.getParameter("page")) : 1;
		
		ModelAndView mav = new ModelAndView("students/level_students");
		mav.addObject("students", studentService.getStudentsByUnivYearAndLevelAndCriteria(idUY, idLevel, searchCriteria, numPage));
		if(numPage == 1) mav.addObject("showPage", true);
		mav.addObject("sc", request.getSession().getServletContext());
		mav.addObject("idLevel", idLevel);
		return mav;
	}
	
	@RequestMapping(value = "/students/getECOptionParcours", method = RequestMethod.GET)
	public ModelAndView getECOptionParcours(HttpServletRequest request, HttpServletResponse response) {
		int idParcours = !request.getParameter("idParcours").equals("") ? Integer.parseInt(request.getParameter("idParcours")) : 0;
		ModelAndView mav = new ModelAndView("students/ec_parcours");
		mav.addObject("ecs", studentService.getECListByParcours(idParcours));
		return mav;
	}
	
	@RequestMapping(value = "/student/getECCumuleStudent", method = RequestMethod.POST)
	public ModelAndView getECCumuleStudent(HttpServletRequest request, HttpServletResponse response) {
		int idStudent = !request.getParameter("idStudent").equals("") ? Integer.parseInt(request.getParameter("idStudent")) : 0;
		int idUY = !request.getParameter("idUY").equals("") ? Integer.parseInt(request.getParameter("idUY")) : 0;
		ModelAndView mav = new ModelAndView("students/ec_cumule");
		mav.addObject("ecs", studentService.getStudentECCumuleList(idStudent, idUY));
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/student/saveSubscription", method = RequestMethod.POST)
	public String saveSubscriptionStudent(HttpServletRequest request, HttpServletResponse response) {
		JSONObject rtn;
		int idStudent = !request.getParameter("subs_idstudent").equals("") ? Integer.parseInt(request.getParameter("subs_idstudent")) : 0;
		int idUY = !request.getParameter("subs_iduy").equals("") ? Integer.parseInt(request.getParameter("subs_iduy")) : 0;
		int idLevel = !request.getParameter("subs_level").equals("") ? Integer.parseInt(request.getParameter("subs_level")) : 0;
		int idPrc = !request.getParameter("subs_parcours").equals("") ? Integer.parseInt(request.getParameter("subs_parcours")) : 0;
		int paid = !request.getParameter("subs_inscription").equals("") ? Integer.parseInt(request.getParameter("subs_inscription")) : 0;
		String dateInscription = request.getParameter("subs_date");
		String cumules = request.getParameter("etd_cumules");
		HashSet<StudyUnit> ueList = studentService.getParcoursChoiceUnits(idPrc);
		String choixprc = "";
		for(StudyUnit ue : ueList) {
			if(choixprc.equals("")) {
				choixprc = ue.getStudyunit_id()+"_"+request.getParameter(""+ue.getStudyunit_id());
			} else {
				choixprc += ";"+ue.getStudyunit_id()+"_"+request.getParameter(""+ue.getStudyunit_id());
			}
		}
		rtn = studentService.saveSubscriptionStudent(idStudent, idUY, idLevel, idPrc, paid, dateInscription, choixprc, cumules);
		return rtn.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/student/getSubscriptionDetails", method = RequestMethod.POST)
	public String detailsSubcriptionStudent(HttpServletRequest request, HttpServletResponse response) {
		int idStudent = !request.getParameter("idStudent").equals("") ? Integer.parseInt(request.getParameter("idStudent")) : 0;
		int idUY = !request.getParameter("idUY").equals("") ? Integer.parseInt(request.getParameter("idUY")) : 0;
		int idLevel = !request.getParameter("idLevel").equals("") ? Integer.parseInt(request.getParameter("idLevel")) : 0;
		System.out.print("id stud "+idStudent+" idUY "+idUY+" idLevel "+idLevel);
		JSONObject rtn;
		if(idStudent == 0 || idUY == 0 || idLevel == 0) {
			rtn = new JSONObject();
			rtn.put("status", 0);
			rtn.put("message", "Paramètre de récupération manquant. Veuillez réessayer");
			return rtn.toString();
		}
		
		rtn = studentService.getSubscriptionInfos(idStudent, idUY, idLevel);
		return rtn.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/student/deleteSubscription", method = RequestMethod.GET)
	public String deleteSubcriptionStudent(HttpServletRequest request, HttpServletResponse response) {
		int idStudent = !request.getParameter("idStudent").equals("") ? Integer.parseInt(request.getParameter("idStudent")) : 0;
		int idUY = !request.getParameter("idUY").equals("") ? Integer.parseInt(request.getParameter("idUY")) : 0;
		JSONObject rtn = studentService.deleteSubscriptionStudent(idStudent, idUY);
		return rtn.toString();
	}
}
