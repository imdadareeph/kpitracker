package com.app.kpitracker.service;

import com.app.kpitracker.model.Task;
import com.app.kpitracker.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskService {

	private final TaskRepository taskRepository;
	

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	public List<Task> findAll(){
		List<Task> tasks = new ArrayList<>();
		tasks = taskRepository.findAll();
		return tasks;
	}

	public List<Task> findAllTasks(int id){
		List<Task> tasks = new ArrayList<>();
		List<Integer> ids =new ArrayList<>();
		ids.add(id);
		tasks = taskRepository.findAll(ids);
		return tasks;
	}
	
	public Task findTask(int id){
		return taskRepository.findOne(id);
	}
	
	public void save(Task task){
		taskRepository.save(task);
	}
	
	public void delete(int id){
		taskRepository.delete(id);

	}
}
