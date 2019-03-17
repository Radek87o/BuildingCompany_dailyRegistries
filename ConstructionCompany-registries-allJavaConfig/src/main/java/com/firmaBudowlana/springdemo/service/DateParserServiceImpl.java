package com.firmaBudowlana.springdemo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.firmaBudowlana.springdemo.exceptions.DateNotInScopeException;
import com.firmaBudowlana.springdemo.exceptions.IncorrectDateFormat;

@Service
public class DateParserServiceImpl implements DateParserService{
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	//helper methods to handle exceptions of incompatible date format
	
	@Override
	public Date parseDate(String dateStr) throws ParseException, DateNotInScopeException {
		Date theDate = formatter.parse(dateStr);
		final LocalDate MIN_LOCAL_DATE = LocalDate.of(2019, 1, 1);
		final LocalDate MAX_LOCAL_DATE = LocalDate.now(ZoneId.systemDefault());
		if (convertToLocalDateViaInstant(theDate).isBefore(MIN_LOCAL_DATE)
				|| convertToLocalDateViaInstant(theDate).isAfter(MAX_LOCAL_DATE)) {
				throw new DateNotInScopeException("Podano datê spoza zakresu - mo¿esz podaæ datê nie wczeœniejsz¹ ni¿ '2019-01-01' i nie póŸniejsz¹ ni¿ data dzisiejsza");
			}
		return theDate;
	}

	@Override
	public String convertDateToString(Date theDate) {
		String dateStr = formatter.format(theDate);
		return dateStr;
	}

	@Override
	public String convertUnparseableDateString(String dateStr) {
		
		String strMonth = dateStr.substring(4,7);
		String strDay = dateStr.substring(8, 10);
		String strYear = dateStr.substring(24,28);
		
		if(strMonth.equals("Jan")) {
			strMonth="01";
		}
		else if(strMonth.equals("Feb")) {
			strMonth="02";
		}
		else if(strMonth.equals("Mar")) {
			strMonth="03";
		}
		else if(strMonth.equals("Apr")) {
			strMonth="04";
		}
		else if(strMonth.equals("May")) {
			strMonth="05";
		}
		else if(strMonth.equals("Jun")) {
			strMonth="06";
		}
		else if(strMonth.equals("Jul")) {
			strMonth="07";
		}
		else if(strMonth.equals("Aug")) {
			strMonth="08";
		}
		else if(strMonth.equals("Sep")) {
			strMonth="09";
		}
		else if(strMonth.equals("Oct")) {
			strMonth="10";
		}
		else if(strMonth.equals("Nov")) {
			strMonth="11";
		}
		else if(strMonth.equals("Dec")) {
			strMonth="12";
		}
		
		String correctDateStr = strYear+"-"+strMonth+"-"+strDay;
		return correctDateStr;
	}

	@Override
	public String checkStrDateBeforeParse(String strDate) throws IncorrectDateFormat {
		
		if(strDate==null || strDate.isEmpty()) {
			throw new IncorrectDateFormat("data nie mo¿e byæ pusta");
		}
		else if(strDate.length()!=10) {
			throw new IncorrectDateFormat("prawid³owy format daty ma 10 znaków, podany ci¹g znaków ma inn¹ d³ugoœæ");
		}
		else if(!(Character.isDigit(strDate.charAt(0))
				&&Character.isDigit(strDate.charAt(1))
				&&Character.isDigit(strDate.charAt(2))
				&&Character.isDigit(strDate.charAt(3))
				&&strDate.charAt(4)=='-'
				&&Character.isDigit(strDate.charAt(5))
				&&Character.isDigit(strDate.charAt(6))
				&&strDate.charAt(7)=='-'
				&&Character.isDigit(strDate.charAt(8))
				&&Character.isDigit(strDate.charAt(9)))) {
			throw new IncorrectDateFormat("nieprawid³owy format daty - prawid³owy format to 'yyyy-MM-dd'");
		}
		
		return strDate;
	}
	
	
}
