package com.firmaBudowlana.springdemo.dao;

import java.util.List;

import com.firmaBudowlana.springdemo.entity.Accommodation;

public interface AccommodationDao {
	
	//methods handling Accommodation.class
	public void saveAccommodation(Accommodation accommodation);
	public List<Accommodation> getAccommodationsList();
}
