package com.firmaBudowlana.springdemo.dao;

import com.firmaBudowlana.springdemo.entity.User;


public interface UserDao {
	
	public User findByUsername(String username);
	public User findById(int userId);
	public void save(User theUser);	
	public void updateTheUser(int userId, String firstName, String lastName, String password, String username);
}
