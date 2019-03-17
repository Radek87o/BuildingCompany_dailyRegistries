package com.firmaBudowlana.springdemo.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.firmaBudowlana.springdemo.entity.User;

public interface ManagerDao {
	
	//interface handles all the methods referring to User.class and not related with new user's registration and user's authenthication
	//The app is dedicated to Company's Construction Manager, therefore app user is a manager as well
	
	public LinkedHashMap<Integer,String> getManagers();
	public LinkedHashMap<Integer,String> getManagersWithoutAdmin();
	public List<User> getManagerListWithoutAdmin();
	public User getProjectManager(int projectId);
	public void deleteManager(int userId);
	
	
}
