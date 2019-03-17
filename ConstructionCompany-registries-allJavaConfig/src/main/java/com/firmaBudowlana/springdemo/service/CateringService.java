package com.firmaBudowlana.springdemo.service;

import java.util.List;

import com.firmaBudowlana.springdemo.entity.Catering;

public interface CateringService {
	
	public void saveCatering(Catering catering);
	public List<Catering> getCatering();
}
