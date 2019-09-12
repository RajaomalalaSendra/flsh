package com.flsh.controller;

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
import java.util.List;

import com.flsh.model.User;
import com.flsh.interfaces.UserService;

@Controller
public class UserController {
	  //private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	  @Autowired
	  UserService userService;

	  @RequestMapping(value = "/users", method = RequestMethod.GET)
	  public ModelAndView showUsers(HttpServletRequest request, HttpServletResponse response) {
		  List<User> users = userService.getAllUser();
		  int number = userService.getUsersNumber();
		  ModelAndView m = new ModelAndView("users/users");
		  HttpSession session = request.getSession();
		  m.addObject("menu", "system");
		  m.addObject("submenu", "all_users");
		  m.addObject("username", session.getAttribute("username") );
	      m.addObject("users", users);  
	      m.addObject("number", number);  
	      return m;    
	  }
	  
	  @RequestMapping(value = "/users/bypage", method = RequestMethod.GET)
	  public ModelAndView showUsersByPage(HttpServletRequest request, HttpServletResponse response) {
		  int page = request.getParameter("page") == null || request.getParameter("page") == "" ? 1 : Integer.parseInt(request.getParameter("page"));
		  List<User> users = userService.getUsersByPage(page);
		  ModelAndView m = new ModelAndView("users/list_users");
	      m.addObject("users", users);   
	      return m;    
	  }
	  
	  @RequestMapping(value = "/users/search", method = RequestMethod.GET)
	  public ModelAndView searchUserByCriteria(HttpServletRequest request, HttpServletResponse response) {
		  int page = request.getParameter("page") == null || request.getParameter("page") == "" ? 1 : Integer.parseInt(request.getParameter("page"));
		  String criteria = request.getParameter("criteria");
		  List<User> users = userService.getUsersByCriteria(criteria, page);
		  ModelAndView m = new ModelAndView("users/list_users");
	      m.addObject("users", users);  
	      m.addObject("showPage", true);  
	      return m;    
	  }
	  
	  @RequestMapping(value = "/user/details", method = RequestMethod.GET)
	  @ResponseBody
	  public String getUserDetails(HttpServletRequest request, HttpServletResponse response) {
		  int id = request.getParameter("id") == null || request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
		  User user = userService.getUserDetails(id);
		  JSONObject rtn = new JSONObject();
		  rtn.put("id", user.getId());
		  rtn.put("lastname", user.getLastname());
		  rtn.put("firstname", user.getFirstname());
		  rtn.put("username", user.getUsername());
		  rtn.put("email", user.getEmail());
		  rtn.put("type", user.getType());
		  rtn.put("typecomputed", user.getTypeComputed());
		  rtn.put("civilite", user.getCiv());
		  return rtn.toString();
	  }
	  
	  @RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	  @ResponseBody
	  public String deleteUser(HttpServletRequest request, HttpServletResponse response) {
		  int id = request.getParameter("id") == ""   ? 0 : Integer.parseInt(request.getParameter("id"));
		  JSONObject rtn = userService.deleteUser(id);
		  return rtn.toString();
	  }
	  
      @RequestMapping(value = "/user/save", method = RequestMethod.POST)
	  @ResponseBody
	  public String saveUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user") User user) {
  		System.out.print(user);
    	JSONObject rtn = userService.saveUser(user);
  		return rtn.toString();
	  }
	  
}
