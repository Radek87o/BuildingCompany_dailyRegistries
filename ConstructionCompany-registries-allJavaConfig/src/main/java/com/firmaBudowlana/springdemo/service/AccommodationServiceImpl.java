package com.firmaBudowlana.springdemo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firmaBudowlana.springdemo.dao.AccommodationDao;
import com.firmaBudowlana.springdemo.entity.Accommodation;

@Service
public class AccommodationServiceImpl implements AccommodationService{
	
	@Autowired
	private AccommodationDao dao;

	@Override
	@Transactional
	public void saveAccommodation(Accommodation accommodation) {
		dao.saveAccommodation(accommodation);	
	}

	@Override
	@Transactional
	public List<Accommodation> getAccommodationsList() {
		return dao.getAccommodationsList();
	}
	
	
}
