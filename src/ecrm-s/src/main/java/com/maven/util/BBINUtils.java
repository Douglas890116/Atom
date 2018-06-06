package com.maven.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * BBGIN 游戏帮助类
 * 来自于EVEB-BBIN波音—最新api接入档.pdf
 * @author klay
 *
 */
public class BBINUtils {
	
	/**********下注玩法3001百家乐
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3001WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1", "莊");
		this.put("2", "閒");
		this.put("3", "和");
		this.put("4", "莊對");
		this.put("5", "閒對");
		this.put("6", "大");
		this.put("7", "小");
		this.put("8", "莊單");
		this.put("9", "莊雙");
		this.put("10", "閒單");
		this.put("11", "閒雙");
		this.put("12", "任意對子");
		this.put("13", "完美對子");
		this.put("14", "莊(免傭)");
		this.put("15", "超級六(免傭)");
	}};
	
	/**********下注玩法3002二八槓
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3002WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1", "上門贏");
		this.put("2", "上門輸");
		this.put("3", "中門贏");
		this.put("4", "中門輸");
		this.put("5", "下門贏");
		this.put("6", "下門輸");
		this.put("7", "上門和");
		this.put("8", "上門對");
		this.put("9", "中門和");
		this.put("10", "中門對");
		this.put("11", "下門和");
		this.put("12", "下門對");
	}};
	/**********下注玩法3003龙虎斗
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3003WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1", "虎");
		this.put("2", "龍");
		this.put("3", "和");
		this.put("4", "虎單");
		this.put("5", "虎雙");
		this.put("6", "龍單");
		this.put("7", "龍雙");
		this.put("8", "虎黑");
		this.put("9", "虎紅");
		this.put("10", "龍黑");
		this.put("11", "龍紅");
	}};
	/**********下注玩法3005三公
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3005WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1", "閒1贏");
		this.put("2", "閒1輸");
		this.put("3", "閒1和");
		this.put("4", "閒1三公");
		this.put("5", "閒1對牌以上");
		this.put("6", "閒2贏");
		this.put("7", "閒2輸");
		this.put("8", "閒2和");
		this.put("9", "閒2三公");
		this.put("10", "閒2對牌以上");
		this.put("11", "閒3贏");
		this.put("12", "閒3輸");
		this.put("13", "閒3和");
		this.put("14", "閒3三公");
		this.put("15", "閒3對牌以上");
		this.put("16", "莊對牌以上");
	}};
	/**********下注玩法3006溫州牌九
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3006WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1", "順門贏");
		this.put("2", "閒家一輸");
		this.put("3", "出門贏");
		this.put("4", "閒家二輸");
		this.put("5", "到門贏");
		this.put("6", "閒家三輸");
	}};
	/**********下注玩法3007輪盤
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3007WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("0", "直注(0)");
		this.put("1", "直注(1)");
		this.put("2", "直注(2)");
		this.put("3", "直注(3)");
		this.put("4", "直注(4)");
		this.put("5", "直注(5)");
		this.put("6", "直注(6)");
		this.put("7", "直注(7)");
		this.put("8", "直注(8)");
		this.put("9", "直注(9)");
		this.put("10", "直注(10)");
		
		this.put("11", "直注(11)");
		this.put("12", "直注(12)");
		this.put("13", "直注(13)");
		this.put("14", "直注(14)");
		this.put("15", "直注(15)");
		this.put("16", "直注(16)");
		this.put("17", "直注(17)");
		this.put("18", "直注(18)");
		this.put("19", "直注(19)");
		this.put("20", "直注(20)");
		
		this.put("21", "直注(21)");
		this.put("22", "直注(22)");
		this.put("23", "直注(23)");
		this.put("24", "直注(24)");
		this.put("25", "直注(25)");
		this.put("26", "直注(26)");
		this.put("27", "直注(27)");
		this.put("28", "直注(28)");
		this.put("29", "直注(29)");
		this.put("30", "直注(30)");
		
		this.put("31", "直注(31)");
		this.put("32", "直注(32)");
		this.put("33", "直注(33)");
		this.put("34", "直注(34)");
		this.put("35", "直注(35)");
		this.put("36", "直注(36)");
		this.put("37", "分注(0,1)");
		this.put("38", "分注(0,2)");
		this.put("39", "分注(0,3)");
		this.put("40", "分注(1,2)");
		
		this.put("41", "分注(1,4)");
		this.put("42", "分注(2,3)");
		this.put("43", "分注(2,5)");
		this.put("44", "分注(3,6)");
		this.put("45", "分注(4,5)");
		this.put("46", "分注(4,7)");
		this.put("47", "分注(5,6)");
		this.put("48", "分注(5,8)");
		this.put("49", "分注(6,9)");
		this.put("50", "分注(7,8)");
		
		this.put("51", "分注(7,10)");
		this.put("52", "分注(8,9)");
		this.put("53", "分注(8,11)");
		this.put("54", "分注(9,12)");
		this.put("55", "分注(10,11)");
		this.put("56", "分注(10,13)");
		this.put("57", "分注(11,12)");
		this.put("58", "分注(11,14)");
		this.put("59", "分注(12,15)");
		this.put("60", "分注(13,14)");
		
		this.put("61", "分注(13,16)");
		this.put("62", "分注(14,15)");
		this.put("63", "分注(14,17)");
		this.put("64", "分注(15,18)");
		this.put("65", "分注(16,17)");
		this.put("66", "分注(16,19)");
		this.put("67", "分注(17,18)");
		this.put("68", "分注(17,20)");
		this.put("69", "分注(18,21)");
		this.put("70", "分注(19,20)");
		
		this.put("71", "分注(19,22)");
		this.put("72", "分注(20,21)");
		this.put("73", "分注(20,23)");
		this.put("74", "分注(21,24)");
		this.put("75", "分注(22,23)");
		this.put("76", "分注(22,25)");
		this.put("77", "分注(23,24)");
		this.put("78", "分注(23,26)");
		this.put("79", "分注(24,27)");
		this.put("80", "分注(25,26)");
		
		this.put("81", "分注(25,28)");
		this.put("82", "分注(26,27)");
		this.put("83", "分注(26,29)");
		this.put("84", "分注(27,30)");
		this.put("85", "分注(28,29)");
		this.put("86", "分注(28,31)");
		this.put("87", "分注(29,30)");
		this.put("88", "分注(29,32)");
		this.put("89", "分注(30,33)");
		this.put("90", "分注(31,32)");
		
		this.put("91", "分注(31,34)");
		this.put("92", "分注(32,33)");
		this.put("93", "分注(32,35)");
		this.put("94", "分注(33,36)");
		this.put("95", "分注(34,35)");
		this.put("96", "分注(35,36)");
		this.put("97", "街注(1,2,3)");
		this.put("98", "街注(4,5,6)");
		this.put("99", "街注(7,8,9)");
		this.put("100", "街注(10,11,12)");
		
		this.put("101", "街注(13,14,15)");
		this.put("102", "街注(16,17,18)");
		this.put("103", "街注(19,20,21)");
		this.put("104", "街注(22,23,24)");
		this.put("105", "街注(25,26,27)");
		this.put("106", "街注(28,29,30)");
		this.put("107", "街注(31,32,33)");
		this.put("108", "街注(34,35,36)");
		this.put("109", "三數(0,1,2)");
		this.put("110", "三數(0,2,3)");
		
		this.put("111", "角注(1,2,4,5)");
		this.put("112", "角注(2,3,5,6)");
		this.put("113", "角注(4,5,7,8)");
		this.put("114", "角注(5,6,8,9)");
		this.put("115", "角注(7,8,10,11)");
		this.put("116", "角注(8,9,11,12)");
		this.put("117", "角注(10,11,13,14)");
		this.put("118", "角注(11,12,14,15)");
		this.put("119", "角注(13,14,16,17)");
		this.put("120", "角注(14,15,17,18)");
		
		this.put("121", "角注(16,17,19,20)");
		this.put("122", "角注(17,18,20,21)");
		this.put("123", "角注(19,20,22,23)");
		this.put("124", "角注(20,21,23,24)");
		this.put("125", "角注(22,23,25,26)");
		this.put("126", "角注(23,24,26,27)");
		this.put("127", "角注(25,26,28,29)");
		this.put("128", "角注(26,27,29,30)");
		this.put("129", "角注(28,29,31,32)");
		this.put("130", "角注(29,30,32,33)");
		
		this.put("131", "角注(31,32,34,35)");
		this.put("132", "角注(32,33,35,36)");
		this.put("133", "四個號碼(0,1,2,3)");
		this.put("134", "線注(1,2,3,4,5,6)");
		this.put("135", "線注(4,5,6,7,8,9)");
		this.put("136", "線注(7,8,9,10,11,12)");
		this.put("137", "線注(10,11,12,13,14,15)");
		this.put("138", "線注(13,14,15,16,17,18)");
		this.put("139", "線注(16,17,18,19,20,21)");
		this.put("140", "線注(19,20,21,22,23,24)");
		
		this.put("141", "線注(22,23,24,25,26,27)");
		this.put("142", "線注(25,26,27,28,29,30)");
		this.put("143", "線注(28,29,30,31,32,33)");
		this.put("144", "線注(31,32,33,34,35,36)");
		this.put("145", "列注(1st)");
		this.put("146", "列注(2nd)");
		this.put("147", "列注(3th)");
		this.put("148", "打(1st)");
		this.put("149", "打(2nd)");
		this.put("150", "打(3th)");
		
		this.put("151", "紅/黑(紅)");
		this.put("152", "紅/黑(黑)");
		this.put("153", "單/雙(單)");
		this.put("154", "單/雙(雙)");
		this.put("155", "大/小(1‐18)");
		this.put("156", "大/小(19‐36)");
		
	}};
	
	/**********下注玩法3008骰寶
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3008WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1", "大/小(小)");
		this.put("2", "大/小(大)");
		this.put("4", "點數/4點");
		this.put("5", "點數/5點");
		this.put("6", "點數/6點");
		this.put("7", "點數/7點");
		this.put("8", "點數/8點");
		this.put("9", "點數/9點");
		this.put("10", "點數/10點");
		
		this.put("11", "點數/11點");
		this.put("12", "點數/12點");
		this.put("13", "點數/13點");
		this.put("14", "點數/14點");
		this.put("15", "點數/15點");
		this.put("16", "點數/16點");
		this.put("17", "點數/17點");
		this.put("18", "短牌(1,2)");
		this.put("19", "短牌(1,3)");
		this.put("20", "短牌(1,4)");
		
		this.put("21", "短牌(1,5)");
		this.put("22", "短牌(1,6)");
		this.put("23", "短牌(2,3)");
		this.put("24", "短牌(2,4)");
		this.put("25", "短牌(2,5)");
		this.put("26", "短牌(2,6)");
		this.put("27", "短牌(3,4)");
		this.put("28", "短牌(3,5)");
		this.put("29", "短牌(3,6)");
		this.put("30", "短牌(4,5)");
		
		this.put("31", "短牌(4,6)");
		this.put("32", "短牌(5,6)");
		this.put("33", "長牌(1,1)");
		this.put("34", "長牌(2,2)");
		this.put("35", "長牌(3,3)");
		this.put("36", "長牌(4,4)");
		this.put("37", "長牌(5,5)");
		this.put("38", "長牌(6,6)");
		this.put("39", "圍骰(1,1,1)");
		this.put("40", "圍骰(2,2,2)");
		
		this.put("41", "圍骰(3,3,3)");
		this.put("42", "圍骰(4,4,4)");
		this.put("43", "圍骰(5,5,5)");
		this.put("44", "圍骰(6,6,6)");
		this.put("45", "全圍");
		this.put("46", "三軍(1)");
		this.put("47", "三軍(2)");
		this.put("48", "三軍(3)");
		this.put("49", "三軍(4)");
		this.put("50", "三軍(5)");
		
		this.put("51", "三軍(6)");
		this.put("52", "單/雙(單)");
		this.put("53", "單/雙(雙)");
		
		
	}};
	
	/**********下注玩法3010德州撲克
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3010WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1", "底注");
		this.put("2", "翻牌");
		this.put("3", "轉牌");
		this.put("4", "河牌");
		this.put("5", "Bonus");
	}};
	
	/**********下注玩法3011色碟
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3011WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1", "4 白");
		this.put("2", "4 紅");
		this.put("3", "3 白 1 紅");
		this.put("4", "3 紅 1 白");
		this.put("5", "單");
		this.put("6", "雙");
	}};
	
	/**********下注玩法3012牛牛
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3012WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1", "閒1平倍");
		this.put("2", "閒1翻倍");
		this.put("3", "閒1預扣額度");
		this.put("4", "閒2平倍");
		this.put("5", "閒2翻倍");
		this.put("6", "閒2預扣額度");
		this.put("7", "閒3平倍");
		this.put("8", "閒3翻倍");
		this.put("9", "閒3預扣額度");
	}};
	
	/**********下注玩法3014無限21點
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3014WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1", "閒");
		this.put("2", "分牌");
		this.put("3", "保險");
		this.put("4", "加倍");
	}};
	
	/**********下注玩法3015番攤
	 * 
	 * 2,1:35,5.00,0.00*5,1:35,5.00,0.00*9,1:35,5.00,0.00
	 * 
	 * 玩法,賠率,下注,派彩*玩法,賠率,下注,派彩*玩法,賠率,下注,派彩
	 * 
	 * ************/
	public static Map<String, String> __3015WagerType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1", "1番");
		this.put("2", "2番");
		this.put("3", "3番");
		this.put("4", "4番");
		this.put("5", "1念2");
		this.put("6", "1念3");
		this.put("7", "1念4");
		this.put("8", "2念1");
		this.put("9", "2念3");
		this.put("10", "2念4");
		this.put("11", "3念1");
		this.put("12", "3念2");
		this.put("13", "3念4");
		this.put("14", "4念1");
		this.put("15", "4念2");
		this.put("16", "4念3");
		this.put("17", "1,2角");
		this.put("18", "2,3角");
		this.put("19", "3,4角");
		this.put("20", "4,1角");
		
		this.put("21", "2,3一通");
		this.put("22", "2,4一通");
		this.put("23", "3,4一通");
		this.put("24", "1,3二通");
		this.put("25", "1,4二通");
		
		this.put("26", "3,4二通");
		this.put("27", "1,2三通");
		this.put("28", "1,4三通");
		this.put("29", "2,4三通");
		this.put("30", "1,2四通");
		this.put("31", "1,3四通");
		this.put("32", "2,3四通");
		this.put("33", "三門(4,3,2)");
		this.put("34", "三門(1,4,3)");
		this.put("35", "三門(2,1,4)");
		this.put("36", "三門(3,2,1)");
		this.put("37", "單");
		this.put("38", "雙");
	}};
	
	
	public static String getBbinWagerDetail(String bbinWagerDetail, String gametype) {
		if(bbinWagerDetail != null && !bbinWagerDetail.equals("")) {
			
			try {
				String[] strs = bbinWagerDetail.split("\\*");
				StringBuffer result = new StringBuffer();
				for (String string : strs) {
					if(string != null && !string.equals("") ) {
						String[] s = string.split(",");
						if(gametype.equals("3001")) {
							result.append("下注[").append(__3001WagerType.get(s[0])).append("]");
						} else if(gametype.equals("3002")) {
							result.append("下注[").append(__3002WagerType.get(s[0])).append("]");
						} else if(gametype.equals("3003")) {
							result.append("下注[").append(__3003WagerType.get(s[0])).append("]");
						} else if(gametype.equals("3005")) {
							result.append("下注[").append(__3005WagerType.get(s[0])).append("]");
						} else if(gametype.equals("3006")) {
							result.append("下注[").append(__3006WagerType.get(s[0])).append("]");
						} else if(gametype.equals("3007")) {
							result.append("下注[").append(__3007WagerType.get(s[0])).append("]");
						} else if(gametype.equals("3008")) {
							result.append("下注[").append(__3008WagerType.get(s[0])).append("]");
						} else if(gametype.equals("3010")) {
							result.append("下注[").append(__3010WagerType.get(s[0])).append("]");
						} else if(gametype.equals("3011")) {
							result.append("下注[").append(__3011WagerType.get(s[0])).append("]");
						} else if(gametype.equals("3012")) {
							result.append("下注[").append(__3012WagerType.get(s[0])).append("]");
						} else if(gametype.equals("3014")) {
							result.append("下注[").append(__3014WagerType.get(s[0])).append("]");
						} else if(gametype.equals("3015")) {
							result.append("下注[").append(__3015WagerType.get(s[0])).append("]");
						}
						
						result.append(",赔率").append(s[1]).append(",下注").append(s[2]).append(",派彩").append(s[3]).append("</br>");
					}
				}
				return result.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bbinWagerDetail;
	}
	public static void main(String[] args) {
		System.out.println(getBbinWagerDetail("2,1:1,150.00,150.00*4,1:11,20.00,-20.00*5,1:11,20.00,-20.00", "3001"));
	}
	
	/**
	 * 根据游戏类型获取游戏名称
	 * @param gameType
	 * @return
	 */
	public static String getGameName(String gameType) {
		if(StringUtils.isBlank(gameType)) return "";
		if("BK".equals(gameType)) return "篮球";
		if("BS".equals(gameType)) return "棒球";
		if("F1".equals(gameType)) return "其他";
		if("FB".equals(gameType)) return "美式足球";
		if("FT".equals(gameType)) return "足球";
		if("IH".equals(gameType)) return "冰球";
		if("SP".equals(gameType)) return "冠军赛";
		if("TN".equals(gameType)) return "网球";
		if("CB".equals(gameType)) return "混合过关";
		if("LT".equals(gameType)) return "六合彩";
		if("BJ3D".equals(gameType)) return "3D彩";
		if("PL3D".equals(gameType)) return "排列三";
		if("BBPK".equals(gameType)) return "BB PK3";
		if("BB3D".equals(gameType)) return "BB3D";
		if("BBKN".equals(gameType)) return "BB快乐彩";
		if("BBRB".equals(gameType)) return "BB滚球王";
		if("SH3D".equals(gameType)) return "上海时时乐";
		if("CQSC".equals(gameType)) return "重庆时时彩";
		if("TJSC".equals(gameType)) return "天津时时彩";
		if("JXSC".equals(gameType)) return "江西时时彩";
		if("XJSC".equals(gameType)) return "新疆时时彩";
		if("MTPK".equals(gameType)) return "幸运飞艇";
		if("CQSF".equals(gameType)) return "重庆幸运农场";
		if("GXSF".equals(gameType)) return "广西十分彩";
		if("TJSF".equals(gameType)) return "天津十分彩";
		if("BJPK".equals(gameType)) return "北京PK拾";
		if("BJKN".equals(gameType)) return "北京快乐8";
		if("CAKN".equals(gameType)) return "加拿大卑斯";
		if("GDE5".equals(gameType)) return "广东11选5";
		if("JXE5".equals(gameType)) return "江西11选5";
		if("SDE5".equals(gameType)) return "山东十一运夺金";
		if("CQWC".equals(gameType)) return "重庆百变王牌";
		if("JLQ3".equals(gameType)) return "吉林快3";
		if("JSQ3".equals(gameType)) return "江苏快3";
		if("AHQ3".equals(gameType)) return "安徽快3";
		if("15006".equals(gameType)) return "3D玉蒲团";
		if("15016".equals(gameType)) return "厨王争霸";
		if("15018".equals(gameType)) return "激情243";
		if("15019".equals(gameType)) return "倩女幽魂";
		if("15021".equals(gameType)) return "全民狗仔";
		if("15022".equals(gameType)) return "怒火领空";
		if("15024".equals(gameType)) return "2014世足赛";
		if("3001".equals(gameType)) return "百家乐";
		if("3002".equals(gameType)) return "二八杠";
		if("3003".equals(gameType)) return "龙虎斗";
		if("3005".equals(gameType)) return "三公";
		if("3006".equals(gameType)) return "温州牌九";
		if("3007".equals(gameType)) return "轮盘";
		if("3008".equals(gameType)) return "骰宝";
		if("3010".equals(gameType)) return "德州扑克";
		if("3011".equals(gameType)) return "色碟";
		if("3012".equals(gameType)) return "牛牛";
		if("3014".equals(gameType)) return "无限21点";
		if("3015".equals(gameType)) return "番摊";
		if("5005".equals(gameType)) return "惑星战记";
		if("5006".equals(gameType)) return "Starburst";
		if("5007".equals(gameType)) return "激爆水果盘";
		if("5008".equals(gameType)) return "猴子爬树";
		if("5009".equals(gameType)) return "金刚爬楼";
		if("5011".equals(gameType)) return "西游记";
		if("5012".equals(gameType)) return "外星争霸";
		if("5013".equals(gameType)) return "传统";
		if("5014".equals(gameType)) return "丛林";
		if("5015".equals(gameType)) return "FIFA2010";
		if("5016".equals(gameType)) return "史前丛林冒险";
		if("5017".equals(gameType)) return "星际大战";
		if("5018".equals(gameType)) return "齐天大圣";
		if("5019".equals(gameType)) return "水果乐园";
		if("5020".equals(gameType)) return "热带风情";
		if("5025".equals(gameType)) return "法海斗白蛇";
		if("5026".equals(gameType)) return "2012 伦敦奥运";
		if("5027".equals(gameType)) return "功夫龙";
		if("5028".equals(gameType)) return "中秋月光派对";
		if("5029".equals(gameType)) return "圣诞派对";
		if("5030".equals(gameType)) return "幸运财神";
		if("5034".equals(gameType)) return "王牌5PK";
		if("5035".equals(gameType)) return "加勒比扑克";
		if("5039".equals(gameType)) return "鱼虾蟹";
		if("5040".equals(gameType)) return "百搭二王";
		if("5041".equals(gameType)) return "7PK";
		if("5042".equals(gameType)) return "异星战场";
		if("5047".equals(gameType)) return "尸乐园";
		if("5048".equals(gameType)) return "特务危机";
		if("5049".equals(gameType)) return "玉蒲团";
		if("5050".equals(gameType)) return "战火佳人";
		if("5057".equals(gameType)) return "明星97";
		if("5058".equals(gameType)) return "疯狂水果盘";
		if("5059".equals(gameType)) return "马戏团";
		if("5060".equals(gameType)) return "动物奇观五";
		if("5061".equals(gameType)) return "超级7";
		if("5062".equals(gameType)) return "龙在囧途";
		if("5063".equals(gameType)) return "水果拉霸";
		if("5064".equals(gameType)) return "扑克拉霸";
		if("5065".equals(gameType)) return "筒子拉霸";
		if("5066".equals(gameType)) return "足球拉霸";
		if("5070".equals(gameType)) return "黄金大转轮";
		if("5073".equals(gameType)) return "百家乐大转轮";
		if("5076".equals(gameType)) return "数字大转轮";
		if("5077".equals(gameType)) return "水果大转轮";
		if("5078".equals(gameType)) return "象棋大转轮";
		if("5079".equals(gameType)) return "3D数字大转轮";
		if("5080".equals(gameType)) return "乐透转轮";
		if("5083".equals(gameType)) return "钻石列车";
		if("5084".equals(gameType)) return "圣兽传说";
		if("5086".equals(gameType)) return "海底派对";
		if("5088".equals(gameType)) return "斗大";
		if("5089".equals(gameType)) return "红狗";
		if("5091".equals(gameType)) return "三国拉霸";
		if("5092".equals(gameType)) return "封神榜";
		if("5093".equals(gameType)) return "金瓶梅";
		if("5094".equals(gameType)) return "金瓶梅2";
		if("5095".equals(gameType)) return "斗鸡";
		if("5101".equals(gameType)) return "欧式轮盘";
		if("5102".equals(gameType)) return "美式轮盘";
		if("5103".equals(gameType)) return "彩金轮盘";
		if("5104".equals(gameType)) return "法式轮盘";
		if("5106".equals(gameType)) return "三国";
		if("5115".equals(gameType)) return "经典21点";
		if("5116".equals(gameType)) return "西班牙21点";
		if("5117".equals(gameType)) return "维加斯21点";
		if("5118".equals(gameType)) return "奖金21点";
		if("5131".equals(gameType)) return "皇家德州扑克";
		if("5201".equals(gameType)) return "火焰山";
		if("5202".equals(gameType)) return "月光宝盒";
		if("5203".equals(gameType)) return "爱你一万年";
		if("5204".equals(gameType)) return "2014 FIFA";
		if("5401".equals(gameType)) return "天山侠侣传";
		if("5402".equals(gameType)) return "夜市人Th";
		if("5403".equals(gameType)) return "七剑传说";
		if("5404".equals(gameType)) return "沙滩排球";
		if("5405".equals(gameType)) return "暗器之王";
		if("5406".equals(gameType)) return "神舟27";
		if("5407".equals(gameType)) return "大红帽与小野狼";
		if("5601".equals(gameType)) return "秘境冒险";
		if("5701".equals(gameType)) return "连连看";
		if("5703".equals(gameType)) return "发达啰";
		if("5704".equals(gameType)) return "斗牛";
		if("5705".equals(gameType)) return "聚宝盆";
		if("5706".equals(gameType)) return "浓情巧克力";
		if("5707".equals(gameType)) return "金钱豹";
		if("5801".equals(gameType)) return "海豚世界";
		if("5802".equals(gameType)) return "阿基里斯";
		if("5803".equals(gameType)) return "阿兹特克宝藏";
		if("5804".equals(gameType)) return "大明星";
		if("5805".equals(gameType)) return "凯萨帝国";
		if("5806".equals(gameType)) return "奇幻花园";
		if("5808".equals(gameType)) return "浪人武士";
		if("5809".equals(gameType)) return "空战英豪";
		if("5810".equals(gameType)) return "航海时代";
		if("5811".equals(gameType)) return "狂欢夜";
		if("5821".equals(gameType)) return "国际足球";
		if("5823".equals(gameType)) return "发大财";
		if("5824".equals(gameType)) return "恶龙传说";
		if("5825".equals(gameType)) return "金莲";
		if("5826".equals(gameType)) return "金矿工";
		if("5827".equals(gameType)) return "老船长";
		if("5828".equals(gameType)) return "霸王龙";
		if("5831".equals(gameType)) return "高球之旅";
		if("5832".equals(gameType)) return "高速卡车";
		if("5833".equals(gameType)) return "沉默武士";
		if("5835".equals(gameType)) return "喜福牛年";
		if("5836".equals(gameType)) return "龙卷风";
		if("5837".equals(gameType)) return "喜福猴年";
		if("5901".equals(gameType)) return "连环夺宝";
		if("5902".equals(gameType)) return "糖果派对";
		if("5903".equals(gameType)) return "秦皇秘宝";
		if("5888".equals(gameType)) return "JACKPOT";
		return "";
	}
}