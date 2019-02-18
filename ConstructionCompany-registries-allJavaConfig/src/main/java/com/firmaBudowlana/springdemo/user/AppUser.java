package com.firmaBudowlana.springdemo.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.firmaBudowlana.springdemo.validation.FieldMatch;

@FieldMatch.List(@FieldMatch(first="password", second="matchingPassword", message="Has³a musz¹ byæ identyczne" ))
public class AppUser {
	
	//Helper method to validate if the password of the new created user is correct
	//only users having role 'ADMIN' are eligible to create new user
	
	@NotNull(message="To pole nie mo¿e byæ puste")
	@Size(min=1, message="To pole nie mo¿e byæ puste")
	private String username;
	
	@NotNull(message="To pole nie mo¿e byæ puste")
	@Size(min=1, message="To pole nie mo¿e byæ puste")
	private String password;
	
	@NotNull(message="To pole nie mo¿e byæ puste")
	@Size(min=1, message="To pole nie mo¿e byæ puste")
	private String matchingPassword;
	
	@NotNull(message="To pole nie mo¿e byæ puste")
	@Size(min=1, message="To pole nie mo¿e byæ puste")
	private String firstName;
	
	@NotNull(message="To pole nie mo¿e byæ puste")
	@Size(min=1, message="To pole nie mo¿e byæ puste")
	private String lastName;
	
	public AppUser() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "AppUser [username=" + username + ", password=" + password + ", matchingPassword=" + matchingPassword
				+ ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	
}
