package com.firmaBudowlana.springdemo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.firmaBudowlana.springdemo.entity.User;
import com.firmaBudowlana.springdemo.user.AppUser;

public interface UserService extends UserDetailsService {
	
	public User findByUsername(String username);
	
	public void save(AppUser theAppUser);
	
	public void update(AppUser theAppUser, int userId);
	
	public AppUser populateAppUserToUpdate(int userId);
	
	public void updateTheUser(int userId, AppUser theAppUser);
}
