package com.firmaBudowlana.springdemo.dao;

import java.util.List;

import com.firmaBudowlana.springdemo.entity.Catering;

public interface CateringDao {
	
	//methods handling Catering.class
	
	public void saveCatering(Catering catering);
	public List<Catering> getCatering();
}
