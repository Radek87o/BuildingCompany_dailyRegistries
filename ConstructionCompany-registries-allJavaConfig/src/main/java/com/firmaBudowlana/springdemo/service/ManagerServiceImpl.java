package com.firmaBudowlana.springdemo.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firmaBudowlana.springdemo.dao.ManagerDao;
import com.firmaBudowlana.springdemo.entity.User;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private ManagerDao dao;
	
	@Override
	@Transactional
	public LinkedHashMap<Integer, String> getManagers() {
		return dao.getManagers();
	}

	@Override
	@Transactional
	public LinkedHashMap<Integer, String> getManagersWithoutAdmin() {
		return dao.getManagersWithoutAdmin();
	}

	@Override
	@Transactional
	public List<User> getManagerListWithoutAdmin() {
		return dao.getManagerListWithoutAdmin();
	}

	@Override
	@Transactional
	public User getProjectManager(int projectId) {
		return dao.getProjectManager(projectId);
	}

	@Override
	@Transactional
	public void deleteManager(int userId) {
		dao.deleteManager(userId);
	}

}
