package com.maven.util;

import java.util.Map;
import java.util.regex.Pattern;

public class SortUtils {

	public static final String ORDER_FEILD = "ORDER_FEILD";
	private static final String ORDER_DESC = "DESC";
	private static final String ORDER_ASC = "ASC";

	public static String makeSortConditon(Map<String, Object> object) {
		String sortString = "";
		for (String s : object.keySet()) {
			if (s.startsWith(ORDER_FEILD)) {
				String[] feilds = s.split("_");
				if (feilds.length == 3 
						&& Pattern.matches("^[A-Za-z1-9-]+$", feilds[2])) {
					String order = String.valueOf(object.get(s));
					if (order.equals(ORDER_DESC) || order.equals(ORDER_ASC)) {
						sortString = feilds[2] + " " + order;
					}
				}
			}
		}
		return sortString.length() == 0 ? null : sortString;
	}
	public static void main(String[] args) {
	}

}
