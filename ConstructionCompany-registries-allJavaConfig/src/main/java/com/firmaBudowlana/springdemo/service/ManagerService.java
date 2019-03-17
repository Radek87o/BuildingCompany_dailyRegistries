package com.firmaBudowlana.springdemo.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.firmaBudowlana.springdemo.entity.User;

public interface ManagerService {
	public LinkedHashMap<Integer,String> getManagers();
	public LinkedHashMap<Integer,String> getManagersWithoutAdmin();
	public List<User> getManagerListWithoutAdmin();
	public User getProjectManager(int projectId);
	public void deleteManager(int userId);
}
