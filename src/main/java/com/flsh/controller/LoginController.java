package com.flsh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.flsh.model.Login;
import com.flsh.model.User;
import com.flsh.service.UserService;

@Controller
public class LoginController {
	  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	  @Autowired
	  UserService userService;

	  @RequestMapping(value = "/login", method = RequestMethod.GET)
	  public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mav = new ModelAndView("login");
	    mav.addObject("login", new Login());
	    return mav;
	  }

	  @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	  public ModelAndView loginProcess(HttpSession session, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("login") Login login) {

	    ModelAndView mav = null;
	    User user = userService.validateUser(login);
	    if (null != user) {;  
	         session.setAttribute("username", user.getUsername());  
	         logger.info("User infos is .", session.getAttribute("username"));
	    	 mav = new ModelAndView("redirect:/");
	    } else {
		    mav = new ModelAndView("login");
		    mav.addObject("message", "Username or Password is wrong!!");
	    }
	    
	    return mav;
	  }
}
