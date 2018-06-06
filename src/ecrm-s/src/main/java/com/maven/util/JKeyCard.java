package com.maven.util;

import java.util.HashMap;
import java.util.Map;

public enum JKeyCard {
	黑桃A("1","SA.png"),
	黑桃2("2","S2.png"),
	黑桃3("3","S3.png"),
	黑桃4("4","S4.png"),
	黑桃5("5","S5.png"),
	黑桃6("6","S6.png"),
	黑桃7("7","S7.png"),
	黑桃8("8","S8.png"),
	黑桃9("9","S9.png"),
	黑桃10("10","S10.png"),
	黑桃J("11","SJ.png"),
	黑桃Q("12","SQ.png"),
	黑桃K("13","SK.png"),
	红桃A("14","HA.png"),
	红桃2("15","H2.png"),
	红桃3("16","H3.png"),
	红桃4("17","H4.png"),
	红桃5("18","H5.png"),
	红桃6("19","H6.png"),
	红桃7("20","H7.png"),
	红桃8("21","H8.png"),
	红桃9("22","H9.png"),
	红桃10("23","H10.png"),
	红桃J("24","HJ.png"),
	红桃Q("25","HQ.png"),
	红桃K("26","HK.png"),
	梅花A("27","CA.png"),
	梅花2("28","C2.png"),
	梅花3("29","C3.png"),
	梅花4("30","C4.png"),
	梅花5("31","C5.png"),
	梅花6("32","C6.png"),
	梅花7("33","C7.png"),
	梅花8("34","C8.png"),
	梅花9("35","C9.png"),
	梅花10("36","C10.png"),
	梅花J("37","CJ.png"),
	梅花Q("38","CQ.png"),
	梅花K("39","CQ.png"),
	方片A("40","DA.png"),
	方片2("41","D2.png"),
	方片3("42","D3.png"),
	方片4("43","D4.png"),
	方片5("44","D5.png"),
	方片6("45","D6.png"),
	方片7("46","D7.png"),
	方片8("47","D8.png"),
	方片9("48","D9.png"),
	方片10("49","D10.png"),
	方片J("50","DJ.png"),
	方片Q("51","DQ.png"),
	方片K("52","DK.png"),
	;
	private String keys ;
	
	private String card;
	
	public static Map<String,String> map; 
	
	private JKeyCard(String key ,String card){
		this.keys=key;
		this.card=card;
	}
	public static Map<String,String> getCard(){
		if (null == map) map = new HashMap<String, String>();
		for (JKeyCard s : JKeyCard.values()) {
			map.put(s.keys, s.card);
		}
		return map;
	}

}
