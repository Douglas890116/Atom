package com.test;

import java.util.Date;

public class UtilDateConvertSQLDate {
	
	public static void main(String[] args) {
		Date date = new Date();
		java.sql.Timestamp date2 = new java.sql.Timestamp(date.getTime());
		System.out.println("SQLDate:"+date2);
		System.out.println("UnitDate:"+date);
		
	}

}
