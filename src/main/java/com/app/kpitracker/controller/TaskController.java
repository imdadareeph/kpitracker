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
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/tasks")
public class TaskController {


	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserTaskService userTaskService;

	@Autowired
	private UserService userService;

	@RequestMapping(value="/new", method = RequestMethod.GET)
	public ModelAndView newTask(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("task", new Task());
		modelAndView.addObject("tasks", taskService.findAll());
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		modelAndView.addObject("mode", "MODE_NEW");
		modelAndView.setViewName("task");
		return modelAndView;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveTask(@Valid Task task, BindingResult bindingResult) {
		task.setDateCreated(new Date());
		taskService.save(task);
		ModelAndView modelAndView = new ModelAndView("redirect:/tasks/all");
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		return modelAndView;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView allTasks() {
		//List<Task> unassignedTasksList= findUnassignedTasks();
		List<Task> unassignedTasksList2= findUnassignedTasksList();

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("rule", new Task());
		if("ADMIN".equalsIgnoreCase(getUser().getRole().getRole())){
			modelAndView.addObject("tasks", taskService.findAll());
		}else {
			modelAndView.addObject("tasks", unassignedTasksList2);
		}
		//modelAndView.addObject("tasks", taskService.findAll());
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		modelAndView.addObject("mode", "MODE_ALL");
		modelAndView.setViewName("task");
		return modelAndView;
	}

	private List<Task> findUnassignedTasksList() {
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

	private List<Task> findUnassignedTasks() {
		List<Task> tasks = new ArrayList<>();
		tasks=taskService.findAll();
		List<UserTask> userTasks = new ArrayList<>();
		List<Task> userTasksforView = new ArrayList<>();
		userTasks = userTaskService.findAll();

		/*List<Integer> taskids = new ArrayList<>();
		for (UserTask u: userTasks){
			taskids.add(u.getTask().getId());
		}*/
		for(Task t:tasks){
			if(null!=userTasks && userTasks.size()>0){
				for(UserTask ut:userTasks){
					if(t.getId()!=ut.getTask().getId()){
						userTasksforView.add(t);
					}
				}
			}else{
				userTasksforView.add(t);
			}

		}
		return userTasksforView;
	}

	@RequestMapping(value = "/saveupdate", method = RequestMethod.POST)
	public ModelAndView saveupdate(@Valid Task task, BindingResult bindingResult) {
		task.setDateCreated(new Date());
		taskService.save(task);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("rule", new User());
		modelAndView.addObject("user", getUser());
		modelAndView.addObject("usertasks", userTaskService.findByUser(userService.findUser(getUser().getId())));
		modelAndView.addObject("mode", "MODE_TASKS");
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		//modelAndView = getModelAndView(task, modelAndView);
		modelAndView.setViewName("user_profile");
		return modelAndView;
	}

	private ModelAndView getModelAndView(@Valid Task task, ModelAndView modelAndView) {
		if("ADMIN".equalsIgnoreCase(getUser().getRole().getRole())){
			modelAndView = getModelAndViewForUpdate(task, modelAndView);
		}else{
			modelAndView = getModelAndViewForUpdate(task, modelAndView);
		}
		return modelAndView;
	}

	private ModelAndView getModelAndViewForUpdate(@Valid Task task, ModelAndView modelAndView) {
		if(null!=task && null!=task.getUserTask() && task.getUserTask().size()>0){
			modelAndView.setViewName("user_profile");
		}else{
			modelAndView = new ModelAndView("redirect:/tasks/all");
			modelAndView.addObject("auth", getUser());
			modelAndView.addObject("control", getUser().getRole().getRole());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/tasklist", method = RequestMethod.GET)
	public ModelAndView myAllTasks(@RequestParam int id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("rule", new Task());
		modelAndView.addObject("tasks", taskService.findAllTasks(id));
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		modelAndView.addObject("mode", "MODE_ALL");
		modelAndView.setViewName("task");
		return modelAndView;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateTask(@RequestParam int id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("rule", new Task());
		modelAndView.addObject("task", taskService.findTask(id));
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control",getUser().getRole().getRole());
		modelAndView.addObject("mode", "MODE_UPDATE");
		modelAndView.setViewName("task");
		return modelAndView;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteTask(@RequestParam int id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/tasks/all");
		modelAndView.addObject("rule", new Task());
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		taskService.delete(id);
		return modelAndView;
	}

	@RequestMapping(value = "/per_inf", method = RequestMethod.GET)
	public ModelAndView infTask(@RequestParam int id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("rule", new Task());
		modelAndView.addObject("task", taskService.findTask(id));
		modelAndView.addObject("usertasks", userTaskService.findByTask(taskService.findTask(id)));
		modelAndView.addObject("auth", getUser());
		modelAndView.addObject("control", getUser().getRole().getRole());
		modelAndView.addObject("mode", "MODE_INF");
		modelAndView.setViewName("task");
		return modelAndView;
	}

	private User getUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
	}
}
