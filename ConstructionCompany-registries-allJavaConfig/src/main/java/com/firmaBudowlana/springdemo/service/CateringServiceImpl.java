package com.firmaBudowlana.springdemo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firmaBudowlana.springdemo.dao.CateringDao;
import com.firmaBudowlana.springdemo.entity.Catering;

@Service
public class CateringServiceImpl implements CateringService{
	
	@Autowired
	private CateringDao dao;

	@Override
	@Transactional
	public void saveCatering(Catering catering) {
		dao.saveCatering(catering);
	}

	@Override
	@Transactional
	public List<Catering> getCatering() {
		return dao.getCatering();
	}
	
	
}
