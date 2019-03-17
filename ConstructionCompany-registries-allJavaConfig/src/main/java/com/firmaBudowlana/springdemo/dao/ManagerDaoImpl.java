package com.firmaBudowlana.springdemo.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.firmaBudowlana.springdemo.entity.Project;
import com.firmaBudowlana.springdemo.entity.User;

@Repository
public class ManagerDaoImpl implements ManagerDao{
	
	
	//handling all the methods referring to User.class and not related with new user's registration and user's authenthication
	//The app is dedicated to Company's Construction Manager, therefore app user is a manager as well
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public LinkedHashMap<Integer, String> getManagers() {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> managers = new LinkedHashMap<Integer, String>();
		Query<?> theQuery = session
				.createQuery("select k.id, concat(k.firstName,' ',k.lastName) from User k order by k.lastName");
		Iterator<?> iterator = theQuery.getResultList().iterator();
		while (iterator.hasNext()) {
			Object[] item = (Object[]) iterator.next();
			int id = (int) item[0];
			String fullName = (String) item[1];
			managers.put(id, fullName);
		}
		return managers;
	}

	@Override
	public LinkedHashMap<Integer, String> getManagersWithoutAdmin() {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> managers = new LinkedHashMap<Integer, String>();
		Query<?> theQuery = session
				.createQuery("select k.id, concat(k.firstName,' ',k.lastName) from User k where k.id<>1 order by k.lastName");
		Iterator<?> iterator = theQuery.getResultList().iterator();
		while (iterator.hasNext()) {
			Object[] item = (Object[]) iterator.next();
			int id = (int) item[0];
			String fullName = (String) item[1];
			managers.put(id, fullName);
		}
		return managers;
	}

	@Override
	public List<User> getManagerListWithoutAdmin() {
		List<User> managerList = new ArrayList<User>();
		Session session = sessionFactory.getCurrentSession();
		Query<User> theQuery = session.createQuery("from User u where u.id<>1 order by u.lastName", User.class);
		managerList = theQuery.getResultList();
		return managerList;
	}
	
	@Override
	public User getProjectManager(int projectId) {
		Session session = sessionFactory.getCurrentSession();
		Project project = session.get(Project.class, projectId);
		Hibernate.initialize(project.getUser());
		User user = project.getUser();
		return user;
	}

	@Override
	public void deleteManager(int userId) {
		Session session = sessionFactory.getCurrentSession();
		User manager = session.get(User.class, userId);
		Hibernate.initialize(manager.getProjects());
		List<Project> managerProjects = manager.getProjects();
		for (Project tempProject : managerProjects) {
			tempProject.setUser(null);
		}
		session.delete(manager);
	}
	
}
