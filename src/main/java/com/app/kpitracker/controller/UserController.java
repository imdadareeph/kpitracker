package com.app.kpitracker.controller;

/**
 * Created by Imdad Areeph
 */

import com.app.kpitracker.model.User;
import com.app.kpitracker.service.RoleService;
import com.app.kpitracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/users")
public class UserController {


	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;



	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView allUsers() {
		ModelAndView modelAndView = new ModelAndView();
		//POINT=7 http://stackoverflow.com/questions/22364886/neither-bindingresult-nor-plain-target-object-for-bean-available-as-request-attr
		modelAndView.addObject("users", userService.findAll());
		modelAndView.addObject("mode", "MODE_ALL");
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		modelAndView.setViewName("user");
		return modelAndView;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/users/all");
		user.setPassword(userService.findUser(user.getId()).getPassword());
		user.setActive(userService.findUser(user.getId()).getActive());
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		userService.save(user);
		return modelAndView;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateUser(@RequestParam int id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("rule", new User());
		modelAndView.addObject("user", userService.findUser(id));
		modelAndView.addObject("roles", roleService.findAll());
		modelAndView.addObject("mode", "MODE_UPDATE");
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		modelAndView.setViewName("user");
		return modelAndView;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteUser(@RequestParam int id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/users/all");
		modelAndView.addObject("rule", new User());
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		userService.delete(id);
		return modelAndView;
	}

	private User getUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
	}
}







