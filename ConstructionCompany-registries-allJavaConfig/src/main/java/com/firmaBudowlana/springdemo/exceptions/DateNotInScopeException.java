package com.firmaBudowlana.springdemo.exceptions;

import org.springframework.stereotype.Component;

@Component
public class DateNotInScopeException extends Exception {
	
	public DateNotInScopeException() {
		// TODO Auto-generated constructor stub
	}
	
	public DateNotInScopeException(String message) {
		super(message);
	}
}
