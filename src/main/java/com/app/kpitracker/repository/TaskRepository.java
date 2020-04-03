package com.app.kpitracker.repository;

/**
 * Created by Imdad Areeph
 */

import com.app.kpitracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
