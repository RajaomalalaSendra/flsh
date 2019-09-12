package com.flsh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {
	private SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public String convertToFrenchDate(String dateString) throws ParseException {
		return formater.format(sqlFormat.parse(dateString));
	}
	
	public String convertToSqlDate(String dateString) throws ParseException {
		return sqlFormat.format(formater.parse(dateString));
	}
}
