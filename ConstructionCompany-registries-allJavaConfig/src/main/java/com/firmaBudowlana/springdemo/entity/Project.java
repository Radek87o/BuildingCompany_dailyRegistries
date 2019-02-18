package com.firmaBudowlana.springdemo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "projekt")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_projektu")
	private int id;

	@Column(name = "nazwa_projektu")
	@NotNull(message = "To pole nie mo¿e byæ puste")
	@Size(min = 1, message = "To pole nie mo¿e byæ puste")
	private String projectName;

	@Column(name = "status_projektu")
	private boolean projectStatus;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany()
	@JoinTable(name = "projekt_pracownik", joinColumns = @JoinColumn(name = "projekt_id"), inverseJoinColumns = @JoinColumn(name = "pracownik_id"))
	private List<Employee> employees;

	@OneToMany(mappedBy = "project", cascade =CascadeType.ALL)
	private List<Registry> registries;

	public Project() {

	}

	public Project(String projectName) {
		this.projectName = projectName;
		this.projectStatus = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public boolean isProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(boolean projectStatus) {
		this.projectStatus = projectStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Registry> getRegistries() {
		return registries;
	}

	public void setRegistries(List<Registry> registries) {
		this.registries = registries;
	}

	@Override
	public String toString() {
		return "Projekt [id=" + id + ", nazwaProjektu=" + projectName + "]";
	}
}
