package com.flsh.controller;

import java.util.List;

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

import com.flsh.interfaces.DeliberationService;
import com.flsh.interfaces.PeriodService;
import com.flsh.interfaces.StudentService;
import com.flsh.interfaces.TeachingService;
import com.flsh.model.EvaluationUEECStudent;
import com.flsh.model.Level;
import com.flsh.model.Student;
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
	ModelAndView getDelib(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	    int univYearId = request.getParameter("univYear") == ""   ? 0 : Integer.parseInt(request.getParameter("univYear"));
		ModelAndView mav = new ModelAndView("deliberation/detail");
		UniversityYear detailUnivYear = delibService.getDetailUnivYear(univYearId);
		
		List<Level> levels = periodService.getAllLevels();
		mav.addObject("idLevel", session.getAttribute("idLevel"));
		
		mav.addObject("students", studentService.getAllStudents());
		mav.addObject("currentUnivYear", detailUnivYear);
		mav.addObject("levels", levels);
	    return mav;
	}
	
	@RequestMapping(value = "/deliberation/students_list", method = RequestMethod.GET)
	@ResponseBody
	ModelAndView getDelibForStudentId(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		int univYearId = request.getParameter("univYear") == ""   ? 0 : Integer.parseInt(request.getParameter("univYear"));
	    int idParcours = request.getParameter("idParcours") == ""   ? 0 : Integer.parseInt(request.getParameter("idParcours"));
		ModelAndView mav = new ModelAndView("deliberation/students_list");
		
		mav.addObject("idStudent", session.getAttribute("idStudent"));
		List<Student> students = studentService.getStudentsByUnivYearAndParcours(univYearId, idParcours);

		mav.addObject("students", students);
	    return mav;
	}
	
	@RequestMapping(value = "/deliberation/getEvaluationData", method = RequestMethod.POST)
	@ResponseBody
	ModelAndView getDelibList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		int univYearId = request.getParameter("idUnivYear") == ""   ? 0 : Integer.parseInt(request.getParameter("idUnivYear"));
		int idStudent = request.getParameter("idStudent") == "" ? 0 : Integer.parseInt(request.getParameter("idStudent"));
		int idLevel = request.getParameter("idLevel") == "" ? 0 : Integer.parseInt(request.getParameter("idLevel"));
		int idPrc = request.getParameter("idPrc") == "" ? 0 : Integer.parseInt(request.getParameter("idPrc"));
		
		// session data
		session.setAttribute("idPrc", idPrc);
		session.setAttribute("idStudent", idStudent);
		session.setAttribute("idLevel", idLevel);
		
		List<EvaluationUEECStudent> dataEvaluations = delibService.getInfosEvaluationsByStudentLevelUnivYearAndParcours(univYearId, idStudent, idLevel, idPrc);
		String delibDecisionCurrentUser = delibService.getDelibDecisionCurrentUser(univYearId, idLevel, idStudent);
		ModelAndView mav = new ModelAndView("deliberation/deliberation_list");
		
		
		
		System.out.print("\n"+session.getAttribute("idUnivYear") + " " +
				session.getAttribute("idStudent") + " " + session.getAttribute("idLevel") +"\n");
		
		mav.addObject("id_univ_year", session.getAttribute("idUnivYear"));
		mav.addObject("id_current_student", session.getAttribute("idStudent"));
		mav.addObject("id_current_level", session.getAttribute("idLevel"));
		System.out.print("\ntest is over here for today\n");
		
		// model and view for values
		mav.addObject("delibCurrentUser", delibDecisionCurrentUser);
        mav.addObject("periodes", periodService.getNiveauPeriodsById(idLevel, univYearId));
		mav.addObject("dataEvaluations", dataEvaluations);
		
	    return mav;
	}
	
	@RequestMapping(value = "/deliberation/student_detail", method = RequestMethod.GET)
	@ResponseBody
	ModelAndView getStudentDetail(HttpServletRequest request, HttpServletResponse response) {
		int idStudent = request.getParameter("idStudent") == "" ? 0 : Integer.parseInt(request.getParameter("idStudent"));
	    ModelAndView mav = new ModelAndView("deliberation/student_detail");
	    
	    Student student = studentService.getStudentById(idStudent);
		mav.addObject("student", student);
		mav.addObject("sc", request.getSession().getServletContext());
	    return mav;
	}
	
	@RequestMapping(value = "deliberation/save_moyenneUE", method = RequestMethod.POST)
	@ResponseBody
	public String saveMoyenneUE(HttpServletRequest request, HttpServletResponse response) {
		int idStudent = request.getParameter("idStudent") == "" ? 0 : Integer.parseInt(request.getParameter("idStudent"));
		int idUE = request.getParameter("idUE") == "" ? 0 : Integer.parseInt(request.getParameter("idUE"));
		int idPeriod = request.getParameter("idPeriod") == "" ? 0 : Integer.parseInt(request.getParameter("idPeriod"));
		float moyenneUE = request.getParameter("moyenneUE") == "" ? 0 : Float.parseFloat(request.getParameter("moyenneUE"));
		int typeSession = request.getParameter("typeSession") == "" ? 0 : Integer.parseInt(request.getParameter("typeSession"));
		
        JSONObject save = delibService.saveMoyenneUE(idStudent, idUE, idPeriod, moyenneUE, typeSession);
	    return save.toString();
	}
	
	@RequestMapping(value = "deliberation/save_credit", method = RequestMethod.POST)
	@ResponseBody
	public String saveCreditECAndUE(HttpServletRequest request, HttpServletResponse response) {
		int idEC = request.getParameter("idEC") == "" ? 0 : Integer.parseInt(request.getParameter("idEC"));
		int idUE = request.getParameter("idUE") == "" ? 0 : Integer.parseInt(request.getParameter("idUE"));
		int creditEC = request.getParameter("creditEC") == "" ? 0 : Integer.parseInt(request.getParameter("creditEC"));
		int creditUE = request.getParameter("creditUE") == "" ? 0 : Integer.parseInt(request.getParameter("creditUE"));
		int idStudent = request.getParameter("idStudent") == "" ? 0 : Integer.parseInt(request.getParameter("idStudent"));

		JSONObject save = delibService.saveCreditECAndUE(idEC, idUE, creditEC, creditUE, idStudent);
	    return save.toString();
	}
	
	@RequestMapping(value = "deliberation/save_validCredit", method = RequestMethod.POST)
	@ResponseBody
	public String saveValidityCreditUE(HttpServletRequest request, HttpServletResponse response) {
		int valValid = request.getParameter("valValid") == "" ? 0 : Integer.parseInt(request.getParameter("valValid"));
		int idStudent = request.getParameter("idStudent") == "" ? 0 : Integer.parseInt(request.getParameter("idStudent"));
		int idUE = request.getParameter("idUE") == "" ? 0 : Integer.parseInt(request.getParameter("idUE"));

		JSONObject save = delibService.saveValidCreditUE(valValid, idStudent, idUE);
	    return save.toString();
	}
	
	@RequestMapping(value = "deliberation/save_delib_decision", method = RequestMethod.POST)
	@ResponseBody
	public String saveDeliberationDecision(HttpServletRequest request, HttpServletResponse response) {
		int delibDecision = 1;
		int idStudent = request.getParameter("idStudent") == "" ? 0 : Integer.parseInt(request.getParameter("idStudent"));
		int idLevel = request.getParameter("idLevel") == "" ? 0 : Integer.parseInt(request.getParameter("idLevel"));
		int idAU = request.getParameter("idUnivYear") == "" ? 0 : Integer.parseInt(request.getParameter("idUnivYear"));
		int idParcours = request.getParameter("idPrc") == "" ? 0 : Integer.parseInt(request.getParameter("idPrc"));
		String passage = request.getParameter("passage");
		
		
		JSONObject save = delibService.saveDeliberationDecision(delibDecision, idStudent, idLevel, idAU, idParcours, passage);
	    return save.toString();
	}

	@RequestMapping(value = "deliberation/save_cumule", method = RequestMethod.POST)
	@ResponseBody
	public String saveStudentECCumule(HttpServletRequest request, HttpServletResponse response) {
		int idStudent = request.getParameter("idStudent") == "" ? 0 : Integer.parseInt(request.getParameter("idStudent"));
		int idEC = request.getParameter("idEC") == "" ? 0 : Integer.parseInt(request.getParameter("idEC"));
		String type = request.getParameter("type");

		JSONObject save = delibService.saveStudentCumule(idStudent, idEC, type);
	    return save.toString();
	}
}
