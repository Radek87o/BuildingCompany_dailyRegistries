package com.firmaBudowlana.springdemo.dao;

import com.firmaBudowlana.springdemo.entity.Role;

public interface RoleDao {
	
	public Role findRoleByName(String theRoleName);
}
