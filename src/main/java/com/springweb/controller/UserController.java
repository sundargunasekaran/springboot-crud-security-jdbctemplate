package com.springweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springweb.model.UserModel;
import com.springweb.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView userLogin() {
		return new ModelAndView("login");
	}
	
	@RequestMapping(value ="/login", method = RequestMethod.POST)
	public ModelAndView postLogin(@ModelAttribute("user") UserModel user) {
		
		boolean op = userService.validateUserLogin(user);
		if(op){
			return new ModelAndView("redirect:/list");
		}else{
			ModelAndView model = new ModelAndView("login");
			model.addObject("errorMsg", "Bad Credentials.Please try again later!!!");
			return model;
		}
		
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView getAllusers() {
		ModelAndView model = new ModelAndView("user_list");
		List<UserModel> list = userService.getAllUserDetails();

		model.addObject("userList", list);
		return model;
	}

	@RequestMapping(value = "/update/{userId}", method = RequestMethod.GET)
	public ModelAndView editUser(@ModelAttribute("user") UserModel user) {
		ModelAndView model = new ModelAndView("user_registration");
		Map< String, String > roles = new HashMap<String, String>();
		roles.put("0", "--Select--");
		roles.put("1", "Admin");
		roles.put("2", "User");
		roles.put("3", "Guests");
         
		model.addObject("roles", roles);
		UserModel userModel = userService.findUserById(user);
		model.addObject("user", userModel);

		return model;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addUser() {
		ModelAndView model = new ModelAndView("user_registration");

		UserModel user = new UserModel();
		model.addObject("user", user);
		Map< String, String > roles = new HashMap<String, String>();
		roles.put("0", "--Select--");
		roles.put("1", "Admin");
		roles.put("2", "User");
		roles.put("3", "Guests");
         
		model.addObject("roles", roles);
		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveOrUpdate(@ModelAttribute("user") UserModel user) {
		if (user.getUserId() > 0) {
			userService.updateUser(user);
		} else {
			userService.addUser(user);
		}

		return new ModelAndView("redirect:/list");
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable("id") int id) {
		userService.deleteUser(id);

		return new ModelAndView("redirect:/list");
	}

}
