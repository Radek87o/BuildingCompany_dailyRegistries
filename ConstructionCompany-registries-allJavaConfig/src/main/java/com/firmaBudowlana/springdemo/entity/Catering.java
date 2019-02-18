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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="catering")
public class Catering {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_catering")
	private int id;
	
	@Column(name="nazwa_catering")
	@NotNull(message="To pole nie mo¿e byæ puste")
	@Size(min=1, message="To pole nie mo¿e byæ puste")
	private String cateringName;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="catering", cascade= {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<Registry> registries;
	
	public Catering() {
		// TODO Auto-generated constructor stub
	}

	public Catering(String cateringName) {
		this.cateringName = cateringName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCateringName() {
		return cateringName;
	}

	public void setCateringName(String cateringName) {
		this.cateringName = cateringName;
	}

	public List<Registry> getRegistries() {
		return registries;
	}

	public void setRegistries(List<Registry> registries) {
		this.registries = registries;
	}

	@Override
	public String toString() {
		return "Catering [id=" + id + ", nazwaCatering=" + cateringName + "]";
	}
	
}
