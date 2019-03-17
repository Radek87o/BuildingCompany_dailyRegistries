package com.firmaBudowlana.springdemo.service;

import java.util.List;

import com.firmaBudowlana.springdemo.entity.Project;

public interface ProjectService {
	public void saveProject(Project project, int userId);
	public List<Project> getAllProjects();
	public List<Project> getOngoingProjects();
	public List<Project> getOngoingManagerProjects(int userId);
	public Project getProject(int projectId);
	public void deleteProject(int projectId);
}
