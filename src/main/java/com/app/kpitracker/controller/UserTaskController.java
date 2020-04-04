package com.app.kpitracker.controller;

/**
 * Created by Imdad Areeph
 */


import com.app.kpitracker.model.Task;
import com.app.kpitracker.model.User;
import com.app.kpitracker.model.UserTask;
import com.app.kpitracker.service.TaskService;
import com.app.kpitracker.service.UserService;
import com.app.kpitracker.service.UserTaskService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user-task")
public class UserTaskController {


	@Autowired
	private UserService userService;

	@Autowired
	private UserTaskService userTaskService;

	@Autowired
	private TaskService taskService;


	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newUserTask() {
		List<Task> unassignedTasksList= findUnassignedTasks();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user_task", new UserTask());
		modelAndView.addObject("users", userService.findAll());
		modelAndView.addObject("tasks", unassignedTasksList);
		//modelAndView.addObject("tasks", taskService.findAll());
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		modelAndView.addObject("mode", "MODE_NEW");
		modelAndView.setViewName("user_task");
		return modelAndView;
	}

	private List<Task> findUnassignedTasks() {
		List<Task> tasks = new ArrayList<>();
		List<Task> userTasksforView = new ArrayList<>();
		tasks = taskService.findAll();
		for(Task t:tasks){
			if(t.getUserTask()!=null && t.getUserTask().size()<1){
				userTasksforView.add(t);
			}
		}
		return userTasksforView;
	}

	/*@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView allUserTasks() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("rule", new UserTask());
		modelAndView.addObject("user_tasks", userTaskService.findAll());
		modelAndView.addObject("users", userService.findAll());
		modelAndView.addObject("tasks", taskService.findAll());
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		modelAndView.addObject("mode", "MODE_ALL");
		modelAndView.setViewName("user_task");
		return modelAndView;
	}*/

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUserTask(@Valid UserTask userTask, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("redirect:/myprofile/mytasks?id="+getUser().getId());
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		userTaskService.save(userTask);
		return modelAndView;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateUserTask(@RequestParam int id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("rule", new UserTask());
		modelAndView.addObject("user_task", userTaskService.findUserTask(id));
		modelAndView.addObject("users", userService.findAll());
		modelAndView.addObject("tasks", taskService.findAll());
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		modelAndView.addObject("mode", "MODE_UPDATE");
		modelAndView.setViewName("user_task");
		return modelAndView;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteUserTask(@RequestParam int id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/user-task/all");
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		userTaskService.delete(id);
		return modelAndView;
	}

	private User getUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
	}

}
