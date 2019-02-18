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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="pracownik")
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pracownika")
	private int id;
	
	@Column(name="imie")
	@NotNull(message="To pole nie mo¿e byæ puste")
	@Size(min=1, message="To pole nie mo¿e byæ puste")
	private String firstName;
	
	@Column(name="nazwisko")
	@NotNull(message="To pole nie mo¿e byæ puste")
	@Size(min=1, message="To pole nie mo¿e byæ puste")
	private String lastName;
	
	@ManyToMany
	@JoinTable(name="projekt_pracownik", joinColumns=@JoinColumn(name="pracownik_id"),
				inverseJoinColumns=@JoinColumn(name="projekt_id"))
	private List<Project> projects;
	
	@OneToMany(mappedBy="employee", cascade= CascadeType.ALL)
	private List<Registry> registries;
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public Employee(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Registry> getRegistries() {
		return registries;
	}

	public void setRegistries(List<Registry> registries) {
		this.registries = registries;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "Pracownik [id=" + id + ", imie=" + firstName + ", nazwisko=" + lastName + "]";
	}

}
