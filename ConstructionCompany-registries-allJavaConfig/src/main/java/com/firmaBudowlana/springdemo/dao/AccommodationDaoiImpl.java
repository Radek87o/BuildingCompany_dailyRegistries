package com.firmaBudowlana.springdemo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.firmaBudowlana.springdemo.entity.Accommodation;

@Repository
public class AccommodationDaoiImpl implements AccommodationDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveAccommodation(Accommodation accommodation) {
		Session session = sessionFactory.getCurrentSession();
		session.save(accommodation);	
	}

	@Override
	public List<Accommodation> getAccommodationsList() {
		List<Accommodation> accommodations = new ArrayList<Accommodation>();
		Session session = sessionFactory.getCurrentSession();
		Query<Accommodation> theQuery = session.createQuery("from Accommodation", Accommodation.class);
		accommodations = theQuery.getResultList();
		return accommodations;
	}
}
