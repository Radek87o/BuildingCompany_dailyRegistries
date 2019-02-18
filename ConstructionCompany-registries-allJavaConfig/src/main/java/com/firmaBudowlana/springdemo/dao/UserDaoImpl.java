package com.firmaBudowlana.springdemo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.firmaBudowlana.springdemo.entity.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public User findByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query<User> theQuery = session.createQuery("from User where username=:username", User.class);
		theQuery.setParameter("username", username);
		User user = null;
		try {
			user = theQuery.getSingleResult();
		} catch (Exception e) {
			user=null;
		}
		return user;
	}

	@Override
	public void save(User theUser) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(theUser);
	}

	@Override
	public void updateTheUser(int userId, String firstName, String lastName, String password, String username) {
		Session session = sessionFactory.getCurrentSession();
		User theUser = session.get(User.class, userId);
		theUser.setFirstName(firstName);
		theUser.setLastName(lastName);
		theUser.setPassword(password);
		theUser.setUsername(username);
		session.update(theUser);
	}

}
