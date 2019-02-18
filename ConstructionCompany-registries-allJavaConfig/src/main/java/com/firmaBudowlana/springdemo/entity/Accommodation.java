package com.firmaBudowlana.springdemo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="nocleg")
public class Accommodation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_noclegu")
	private int id;
	
	@Column(name="nazwa_noclegu")
	@NotNull(message="To pole nie mo¿e byæ puste")
	@Size(min=1, message="To pole nie mo¿e byæ puste")
	private String accommodationName;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="accommodation", cascade= {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<Registry> registries;
	
	public Accommodation() {
		// TODO Auto-generated constructor stub
	}
	
	public Accommodation(String accommodationName) {
		this.accommodationName = accommodationName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccommodationName() {
		return accommodationName;
	}

	public void setAccommodationName(String accommodationName) {
		this.accommodationName = accommodationName;
	}

	public List<Registry> getRegistries() {
		return registries;
	}

	public void setRegistries(List<Registry> registries) {
		this.registries = registries;
	}

	@Override
	public String toString() {
		return "Nocleg [id=" + id + ", nazwaNoclegu=" + accommodationName + "]";
	}
	
	
}
