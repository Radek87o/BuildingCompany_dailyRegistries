package com.firmaBudowlana.springdemo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.firmaBudowlana.springdemo.entity.Project;
import com.firmaBudowlana.springdemo.entity.User;

@Repository
public class ProjectDaoImpl implements ProjectDao {
	
	//all the methods handling CRUD for Project.class
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveProject(Project project, int userId) {
		Session session = sessionFactory.getCurrentSession();
		User manager = session.get(User.class, userId);
		project.setUser(manager);
		session.merge(project);
	}

	@Override
	public List<Project> getAllProjects() {
		List<Project> projects;
		Session session = sessionFactory.getCurrentSession();
		Query<Project> theQuery = session.createQuery("from Project", Project.class);
		projects = theQuery.getResultList();
		return projects;
	}

	@Override
	public List<Project> getOngoingProjects() {
		List<Project> ongoingProjects = new ArrayList<Project>();
		Session session = sessionFactory.getCurrentSession();
		Query<Project> theQuery = session.createQuery("from Project p where p.projectStatus=0", Project.class);
		ongoingProjects = theQuery.getResultList();
		return ongoingProjects;
	}

	@Override
	public List<Project> getOngoingManagerProjects(int userId) {
		Session session = sessionFactory.getCurrentSession();
		Query<Project> theQuery = session.createQuery("select p from Project p left join p.user u where p.projectStatus=0 and u.id=:userId", Project.class);
		theQuery.setParameter("userId", userId);
		List<Project> projects = theQuery.getResultList();
		return projects;
	}

	@Override
	public Project getProject(int projectId) {
		Session session = sessionFactory.getCurrentSession();
		Project project = session.get(Project.class, projectId);
		return project;
	}

	@Override
	public void deleteProject(int projectId) {
		Session session = sessionFactory.getCurrentSession();
		Project project = session.get(Project.class, projectId);
		session.delete(project);
	}

}
