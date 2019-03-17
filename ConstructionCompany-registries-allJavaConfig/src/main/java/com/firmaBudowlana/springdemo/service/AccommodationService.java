package com.firmaBudowlana.springdemo.service;

import java.util.List;

import com.firmaBudowlana.springdemo.entity.Accommodation;

public interface AccommodationService {
	
	public void saveAccommodation(Accommodation accommodation);
	public List<Accommodation> getAccommodationsList();
}
