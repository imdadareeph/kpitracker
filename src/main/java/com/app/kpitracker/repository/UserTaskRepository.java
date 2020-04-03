package com.app.kpitracker.repository;

/**
 * Created by Imdad Areeph
 */

import com.app.kpitracker.model.Task;
import com.app.kpitracker.model.User;
import com.app.kpitracker.model.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userTaskRepository")
public interface UserTaskRepository extends JpaRepository<UserTask, Integer> {
    List<UserTask> findByTask (Task task);
    List<UserTask> findByUser (User user);
}
