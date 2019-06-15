package com.flsh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

import com.flsh.model.Login;
import com.flsh.model.User;
import com.flsh.interfaces.UserService;

@Controller
public class UserController {
	  //private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	  @Autowired
	  UserService userService;

	  @RequestMapping(value = "/users", method = RequestMethod.GET)
	  public ModelAndView showUser(HttpServletRequest request, HttpServletResponse response) {
		  List<User> users=userService.getAllUser();
		  ModelAndView m = new ModelAndView("users/users");
	      m.addObject("users",users);  
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