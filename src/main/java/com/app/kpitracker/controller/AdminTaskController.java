package com.app.kpitracker.controller;

import com.app.kpitracker.model.User;
import com.app.kpitracker.model.UserTask;
import com.app.kpitracker.service.TaskService;
import com.app.kpitracker.service.UserService;
import com.app.kpitracker.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/user-task")
public class AdminTaskController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private TaskService taskService;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
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
    }


    private User getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return user;
    }

}