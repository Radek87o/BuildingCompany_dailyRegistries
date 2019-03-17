package com.firmaBudowlana.springdemo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firmaBudowlana.springdemo.dao.ProjectDao;
import com.firmaBudowlana.springdemo.entity.Project;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectDao dao;

	@Override
	@Transactional
	public void saveProject(Project project, int userId) {
		dao.saveProject(project, userId);
	}

	@Override
	@Transactional
	public List<Project> getAllProjects() {
		return dao.getAllProjects();
	}

	@Override
	@Transactional
	public List<Project> getOngoingProjects() {
		return dao.getOngoingProjects();
	}

	@Override
	@Transactional
	public List<Project> getOngoingManagerProjects(int userId) {
		return dao.getOngoingManagerProjects(userId);
	}

	@Override
	@Transactional
	public Project getProject(int projectId) {
		return dao.getProject(projectId);
	}

	@Override
	@Transactional
	public void deleteProject(int projectId) {
		dao.deleteProject(projectId);
	}
}
