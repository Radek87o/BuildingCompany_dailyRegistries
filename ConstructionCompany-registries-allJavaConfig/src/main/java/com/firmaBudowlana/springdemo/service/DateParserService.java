package com.firmaBudowlana.springdemo.service;

import java.text.ParseException;
import java.util.Date;

import com.firmaBudowlana.springdemo.exceptions.DateNotInScopeException;
import com.firmaBudowlana.springdemo.exceptions.IncorrectDateFormat;

public interface DateParserService {
	
	//helper interface to handle all the operations of converting String to Date and Date to String
	public Date parseDate(String dateStr) throws ParseException, DateNotInScopeException;
	public String convertDateToString(Date theDate);
	public String convertUnparseableDateString(String dateStr);
	public String checkStrDateBeforeParse(String strDate) throws IncorrectDateFormat;
}
