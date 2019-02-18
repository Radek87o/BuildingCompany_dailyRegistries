package com.firmaBudowlana.springdemo.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.firmaBudowlana.springdemo.dao.RegistryDao;
import com.firmaBudowlana.springdemo.dao.RoleDao;
import com.firmaBudowlana.springdemo.dao.UserDao;
import com.firmaBudowlana.springdemo.entity.Role;
import com.firmaBudowlana.springdemo.entity.User;
import com.firmaBudowlana.springdemo.user.AppUser;

@Service
public class UserServiceImpl implements UserService {
	
	//methods referring to user's registration and authentication of the user trying to log in
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	RegistryDao rejestrDao;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	@Override
	@Transactional
	public void save(AppUser theAppUser) {
		User theUser = new User();
		theUser.setFirstName(theAppUser.getFirstName());
		theUser.setLastName(theAppUser.getLastName());
		theUser.setUsername(theAppUser.getUsername());
		theUser.setPassword(passwordEncoder.encode(theAppUser.getPassword()));
		
		theUser.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_MANAGER")));
		
		userDao.save(theUser);
	}
	
	@Override
	@Transactional
	public void update(AppUser theAppUser, int userId) {
		User theUser = rejestrDao.getManager(userId);
		theUser.setFirstName(theAppUser.getFirstName());
		theUser.setLastName(theAppUser.getLastName());
		theUser.setUsername(theAppUser.getUsername());
		theUser.setPassword(passwordEncoder.encode(theAppUser.getPassword()));
		
		theUser.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_MANAGER")));
		
		userDao.save(theUser);
	}
	
	
	
	@Override
	@Transactional
	public void updateTheUser(int userId, AppUser theAppUser) {
		String firstName = theAppUser.getFirstName();
		String lastName = theAppUser.getLastName();
		String username = theAppUser.getUsername();
		String password = passwordEncoder.encode(theAppUser.getPassword());
		userDao.updateTheUser(userId, firstName, lastName, password, username);
	}

	@Override
	@Transactional
	public AppUser populateAppUserToUpdate(int userId) {
		User theUser = rejestrDao.getManager(userId);
		AppUser theAppUser=new AppUser();
		theAppUser.setFirstName(theUser.getFirstName());
		theAppUser.setLastName(theUser.getLastName());
		theAppUser.setUsername(theUser.getUsername());
		theAppUser.setPassword(theUser.getPassword());
		theAppUser.setMatchingPassword(theUser.getPassword());
		return theAppUser;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User theUser = userDao.findByUsername(username);
		if(theUser==null) {
			throw new  UsernameNotFoundException("Niepoprawna nazwa u¿ytkownika lub has³o");
		}
		return new org.springframework.security.core.userdetails.User(theUser.getUsername(), theUser.getPassword(),mapRolesToAuthorities(theUser.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	
	
	

}
