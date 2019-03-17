package com.firmaBudowlana.springdemo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.firmaBudowlana.springdemo.entity.Catering;

@Repository
public class CateringDaoImpl implements CateringDao{
		
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveCatering(Catering catering) {
		Session session = sessionFactory.getCurrentSession();
		session.save(catering);	
	}

	@Override
	public List<Catering> getCatering() {
		List<Catering> listCatering = new ArrayList<Catering>();
		Session session = sessionFactory.getCurrentSession();
		Query<Catering> theQuery = session.createQuery("from Catering", Catering.class);
		listCatering = theQuery.getResultList();
		return listCatering;
	}
	
	
}
