package com.firmaBudowlana.springdemo.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "rejestr")
public class Registry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rejestr")
	private int id;

	@Column(name = "data")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "projekt_id")
	private Project project;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "pracownik_id")
	private Employee employee;

	@Column(name = "czas_pracy")
	private int workingTime;

	@Column(name = "nieobecnosc")
	private String absence;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "catering_id")
	private Catering catering;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "nocleg_id")
	private Accommodation accommodation;

	@Column(name = "data_wprowadzenia")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfIssue;


	public Registry() {
		// TODO Auto-generated constructor stub
	}

	public Registry(Date date, int workingTime, String absence) {
		this.date = date;
		this.workingTime = workingTime;
		this.absence = absence;
		this.dateOfIssue = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(int workingTime) {
		this.workingTime = workingTime;
	}

	public String getAbsence() {
		return absence;
	}

	public void setAbsence(String absence) {
		this.absence = absence;
	}

	public Catering getCatering() {
		return catering;
	}

	public void setCatering(Catering catering) {
		this.catering = catering;
	}

	public Accommodation getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Rejestr [id=" + id + ", data=" + date + ", projekt=" + project +", pracownik=" + employee + ", czasPracy=" + workingTime
				+ ", nieobecnosc=" + absence + ", catering=" + catering + ", nocleg=" + accommodation
				+ ", dataWprowadzenia=" + dateOfIssue +"]";
	}

}
