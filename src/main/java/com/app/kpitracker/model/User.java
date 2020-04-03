package com.app.kpitracker.model;

/**
 * Created by Imdad Areeph
 */

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "email")
	@Email(message = "*Please enter a valid email adress!")
	@NotEmpty(message = "*Please provide an email! This field can not be empty!")
	private String email;
	
	@Column(name = "password")
	@Length(min = 3, message = "*Your password can not be less than 3 characters!")
	@NotEmpty(message = "*Please provide your password! This field can not be empty!")
	@Transient
	
	private String password;
	@Column(name = "name")
	@NotEmpty(message = "*Please provide your name! This field can not be empty!")
	private String name;
	
	@Column(name = "active")
	private int active;
	
	
	@ManyToOne
	private Role role;
	
	@OneToMany(mappedBy = "user")
	private Set<UserTask> userTask = new HashSet<UserTask>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	public Set<UserTask> getUserTask() {
		return userTask;
	}

	public void setUserTask(Set<UserTask> userTask) {
		this.userTask = userTask;
	}
	
	

}
