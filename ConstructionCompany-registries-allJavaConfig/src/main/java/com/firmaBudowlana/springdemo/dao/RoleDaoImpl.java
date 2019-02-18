package com.firmaBudowlana.springdemo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.firmaBudowlana.springdemo.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Role findRoleByName(String theRoleName) {
		Session session = sessionFactory.getCurrentSession();
		Query<Role> theQuery = session.createQuery("from Role where name=:roleName", Role.class);
		theQuery.setParameter("roleName", theRoleName);
		Role theRole = theQuery.getSingleResult();
		return theRole;
	}	
}
