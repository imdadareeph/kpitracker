package com.app.kpitracker.repository;

/**
 * Created by Imdad Areeph
 */

import com.app.kpitracker.model.Role;
import com.app.kpitracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	 User findByEmail(String email);
	List<User> findByRole(Role role);
}
