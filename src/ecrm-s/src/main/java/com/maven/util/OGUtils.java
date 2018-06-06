package com.maven.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


/**
 * AG亚游的帮助类
 * 来自于AG亚游.pdf
 * @author klay
 *
 */
public class OGUtils {
	
	/**********初始化平台类型************/
	public static Map<String, String> __GameType = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;

	{

		this.put("11", "百家乐");
		this.put("12", "龙虎");
		this.put("13", "轮盘");
		this.put("14", "骰宝");
		this.put("15", "德州扑克");
		this.put("16", "番摊");		
		this.put("17", "电子游戏");
		this.put("18", "彩票");
		this.put("19", "杰克");
		this.put("20", "三公");
		
	}};
	
	/**********初始化平台类型************/
	public static Map<String, String> __Betting_King = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;

	{
		this.put("111", "标准百家乐");
		this.put("112", "免佣百家乐");
		this.put("113", "超级百家乐");
		this.put("114", "标准连环百家乐");
		this.put("115", "免佣连环百家乐");
		this.put("116", "7点百家乐");
		this.put("211", "龙虎");
		this.put("311", "轮盘");
		this.put("411", "骰宝");
		this.put("511", "德州");
		this.put("611", "番摊");
		this.put("711", "电子游戏");
		this.put("811", "Live 3D");
		this.put("911", "杰克");
		this.put("1000", "三公");
	}};
	
	/**********初始化平台的大厅类型************/
	public static Map<String, String> __Betting_King_CONTENT = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;

	{
		//百家乐
		this.put("101", "闲");
        this.put("102", "庄");
        this.put("103", "和");
        this.put("104", "闲对");
        this.put("105", "庄对");
        this.put("106", "总点单");
        this.put("107", "总点双");
        this.put("108", "闲点单");
        this.put("109", "闲点双");
        this.put("110", "庄点单");
        this.put("111", "庄点双");
        this.put("112", "总点大");
        this.put("113", "总点小");
        this.put("114", "闲点大");
        this.put("115", "闲点小");
        this.put("116", "庄点大");
        this.put("117", "庄点小");
        this.put("118", "总牌大");
        this.put("119", "总牌小");
        this.put("120", "闲牌大");
        this.put("121", "闲牌小");
        this.put("122", "庄牌大");
        this.put("123", "庄牌小");
        this.put("124", "闲龙宝");
        this.put("125", "庄龙宝");
        this.put("126", "Super6");
        this.put("127", "Super7");
        this.put("130", "超级和0");        
        this.put("131", "超级和1");
        this.put("132", "超级和2");
        this.put("133", "超级和3");        
        this.put("134", "超级和4");
        this.put("135", "超级和5");
        this.put("136", "超级和6");
        this.put("137", "超级和7");
        this.put("138", "超级和8");
        this.put("139", "超级和9");
        
        //龙虎
        this.put("201", "龙");
        this.put("202", "虎");
        this.put("203", "和");
        this.put("204", "龙单");
        this.put("205", "龙双");
        this.put("206", "龙大");
        this.put("207", "龙小");
        this.put("208", "龙红");
        this.put("209", "龙黑");
        this.put("210", "龙方块");
        this.put("211", "龙梅花");
        this.put("212", "龙红桃");
        this.put("213", "龙黑桃");
        this.put("214", "虎单");
        this.put("215", "虎双");
        this.put("216", "虎大");
        this.put("217", "虎小");
        this.put("218", "虎红");
        this.put("219", "虎黑");
        this.put("220", "虎方块");
        this.put("221", "虎梅花");
        this.put("222", "虎红桃");
        this.put("223", "虎黑桃");
        
        
        //骰宝
        this.put("401", "点数4");
        this.put("402", "点数5");
        this.put("403", "点数6");
        this.put("404", "点数7");
        this.put("405", "点数8");
        this.put("406", "点数9");
        this.put("407", "点数10");
        this.put("408", "点数11");
        this.put("409", "点数12");
        this.put("410", "点数13");
        this.put("411", "点数14");
        this.put("412", "点数15");
        this.put("413", "点数16");
        this.put("414", "点数17");
        this.put("415", "小");
        this.put("416", "大");
        this.put("417", "三军1");
        this.put("418", "三军2");
        this.put("419", "三军3");
        this.put("420", "三军4");
        this.put("421", "三军5");
        this.put("422", "三军6");        
        this.put("423", "短牌1");
        this.put("424", "短牌2");
        this.put("425", "短牌3");
        this.put("426", "短牌4");
        this.put("427", "短牌5");
        this.put("428", "短牌6");
        this.put("429", "围骰1");
        this.put("430", "围骰2");
        this.put("431", "围骰3");
        this.put("432", "围骰4");
        this.put("433", "围骰5");
        this.put("434", "围骰6");
        this.put("435", "全骰");
        this.put("436", "长牌1~2");        
        this.put("437", "长牌1~3");
        this.put("438", "长牌1~4");
        this.put("439", "长牌1~5");
        this.put("440", "长牌1~6");
        this.put("441", "长牌2~3");
        this.put("442", "长牌2~4");
        this.put("443", "长牌2~5");
        this.put("444", "长牌2~6");
        this.put("445", "长牌3~4");
        this.put("446", "长牌3~5");
        this.put("447", "长牌3~6");
        this.put("448", "长牌4~5");
        this.put("449", "长牌4~6");
        this.put("450", "长牌5~6");
        this.put("451", "单");
        this.put("452", "双");        
        this.put("453", "112");
        this.put("454", "113");
        this.put("455", "114");
        this.put("456", "115");
        this.put("457", "116");
        this.put("458", "122");
        this.put("459", "133");        
        this.put("460", "144");
        this.put("461", "155");
        this.put("462", "166");
        this.put("463", "223");
        this.put("464", "224");
        this.put("465", "225");
        this.put("466", "226");
        this.put("467", "233");
        this.put("468", "244");
        this.put("469", "255");
        this.put("470", "266");        
        this.put("471", "334");
        this.put("472", "335");
        this.put("473", "336");
        this.put("474", "344");
        this.put("475", "355");
        this.put("476", "366");
        this.put("477", "445");
        this.put("478", "446");
        this.put("479", "455");
        this.put("480", "466");
        this.put("481", "556");
        this.put("482", "566");        
        this.put("483", "123");
        this.put("484", "124");
        this.put("485", "125");
        this.put("486", "126");
        this.put("487", "134");
        this.put("488", "135");
        this.put("489", "136");
        this.put("490", "145");        
        this.put("491", "146");
        this.put("492", "156");
        this.put("493", "234");
        this.put("494", "235");
        this.put("495", "236");
        this.put("496", "245");
        this.put("497", "246");
        this.put("498", "256");
        this.put("499", "345");
        this.put("4100", "346");
        this.put("4101", "356");
        this.put("4102", "456");
        
        //轮盘
        this.put("501", "1");
        this.put("502", "2");
        this.put("503", "3");
        this.put("504", "4");
        this.put("505", "5");
        this.put("506", "6");
        this.put("507", "7");
        this.put("508", "8");
        this.put("509", "9");
        this.put("510", "10");
        this.put("511", "11");
        this.put("511", "11");
        this.put("512", "12");
        this.put("513", "13");
        this.put("514", "14");
        this.put("515", "15");
        this.put("516", "16");
        this.put("517", "17");
        this.put("518", "18");
        this.put("519", "19");
        this.put("520", "20");
        this.put("521", "21");
        this.put("522", "22");
        this.put("523", "23");
        this.put("524", "24");
        this.put("525", "25");
        this.put("526", "26");
        this.put("527", "27");
        this.put("528", "28");
        this.put("529", "29");
        this.put("530", "30");
        this.put("531", "31");
        this.put("532", "32");
        this.put("533", "33");
        this.put("534", "34");
        this.put("535", "35");
        this.put("536", "36");
        this.put("537", "0");
        this.put("538", "3,0");
        this.put("539", "3,6");
        this.put("540", "6,9");
        
        this.put("541", "9,12");
        this.put("542", "12,15");
        this.put("543", "15,18");
        this.put("544", "18,21");
        this.put("545", "21,24");
        this.put("546", "24,27");
        this.put("547", "27,30");
        this.put("548", "30,33");
        this.put("549", "33,36");
        
        this.put("550", "2,3");
        this.put("551", "5,6");
        this.put("552", "8,9");
        this.put("553", "11,12");
        this.put("554", "14,15");
        this.put("555", "17,18");
        this.put("556", "20,21");
        this.put("557", "23,24");
        this.put("558", "26,27");
        this.put("559", "29,30");
        
        this.put("560", "32,33");
        this.put("561", "35,36");
        this.put("562", "2,0");
        this.put("563", "2,5");
        this.put("564", "5,8");
        this.put("565", "8,11");
        this.put("566", "11,14");
        this.put("567", "14,17");
        this.put("568", "17,20");
        this.put("569", "20,23");
        
        this.put("570", "23,26");
        this.put("571", "26,29");
        this.put("572", "29,32");
        this.put("573", "32,35");
        this.put("574", "1,2");
        this.put("575", "4,5");
        this.put("576", "7,8");
        this.put("577", "10,11");
        this.put("578", "13,14");
        this.put("579", "16,17");
        
        this.put("580", "19,20");
        this.put("581", "22,23");
        this.put("582", "25,26");
        this.put("583", "28,29");
        this.put("584", "31,32");
        this.put("585", "34,35");
        this.put("586", "1,0");
        this.put("587", "1,4");
        this.put("588", "4,7");
        this.put("589", "7,10");
        
        this.put("590", "10,13");
        this.put("591", "13,16");
        this.put("592", "16,19");
        this.put("593", "19,22");
        this.put("594", "22,25");
        this.put("595", "25,28");
        this.put("596", "28,31");
        this.put("597", "31,34");
        this.put("598", "2,3,0");
        this.put("599", "1,2,0");
        
        this.put("600", "1,2,3");
        this.put("601", "4,5,6");
        this.put("602", "7,8,9");
        this.put("603", "10,11,12");
        this.put("604", "13,14,15");
        this.put("605", "16,17,18");
        this.put("606", "19,20,21");
        this.put("607", "22,23,24");
        this.put("608", "25,26,27");
        this.put("609", "28,29,30");
        
        this.put("610", "31,32,33");
        this.put("611", "34,35,36");
        this.put("612", "2,5,3,6");
        this.put("613", "5,8,6,9");
        this.put("614", "8,11,9,12");
        this.put("615", "11,14,12,15");
        this.put("616", "14,17,15,18");
        this.put("617", "17,20,18,21");
        this.put("618", "20,23,21,24");
        this.put("619", "23,26,24,27");
        
        this.put("620", "26,29,27,30");
        this.put("621", "29,32,30,33");
        this.put("622", "32,35,33,36");
        this.put("623", "1,4,2,5");
        this.put("624", "4,7,5,8");
        this.put("625", "7,10,8,11");
        this.put("626", "10,13,11,14");
        this.put("627", "13,16,14,17");
        this.put("628", "16,19,17,20");
        this.put("629", "19,22,20,23");
        
        this.put("630", "22,25,23,26");
        this.put("631", "25,28,26,29");
        this.put("632", "28,31,29,32");
        this.put("633", "31,34,32,35");
        this.put("634", "1,2,3,0");
        this.put("635", "1,2,3,4,5,6");
        this.put("636", "4,5,6,7,8,9");
        this.put("637", "7,8,9,10,11,12");
        this.put("638", "10,11,12,13,14,15");
        this.put("639", "13,14,15,16,17,18");
        
        this.put("640", "16,17,18,19,20,21");
        this.put("641", "19,20,21,22,23,24");
        this.put("642", "22,23,24,25,26,27");
        this.put("643", "25,26,27,28,29,30");
        this.put("644", "28,29,30,31,32,33");
        this.put("645", "31,32,33,34,35,36");
        this.put("646", "3,6,9,12,15,18,21,24,27,30,33,36");
        this.put("647", "2,5,8,11,14,17,20,23,26,29,32,35");
        this.put("648", "1,4,7,10,13,16,19,22,25,28,31,34");
        this.put("649", "1,2,3,4,5,6,7,8,9,10,11,12");
        
        this.put("650", "13,14,15,16,17,18,19,20,21,22,23,24");
        this.put("651", "25,26,27,28,29,30,31,32,33,34,35,36");
        this.put("652", "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18");
        this.put("653", "2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36");
        this.put("654", "1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36");
        this.put("655", "2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35");
        this.put("656", "1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35");
        this.put("657", "19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36");

        
        //番摊
        this.put("742", "正 1");
        this.put("743", "正 2");
        this.put("744", "正 3");
        this.put("745", "正 4");
        this.put("746", "角1_2");
        this.put("747", "角2_3");
        this.put("748", "角3_4");
        this.put("749", "角4_1");        
        this.put("750", "1 番");
        this.put("751", "2 番");
        this.put("752", "3 番");
        this.put("753", "4 番");
        this.put("754", "1 念2");
        this.put("755", "1 念3");
        this.put("756", "1 念4");
        this.put("757", "2 念1");
        this.put("758", "2 念3");
        this.put("759", "2 念4");
        this.put("760", "3 念1");
        this.put("761", "3 念2");
        this.put("762", "3 念4");
        this.put("763", "4 念1");
        this.put("764", "4 念2");
        this.put("765", "4 念3");        
        this.put("766", "单");
        this.put("767", "双");
        this.put("768", "1,2 四 通");
        this.put("769", "1,2 三 通");        
        this.put("770", "1,3 四 通");
        this.put("771", "1,3 二 通");
        this.put("772", "1,4 三 通");
        this.put("773", "1,4 二 通");
        this.put("774", "2,3 四 通");
        this.put("775", "2,3 一 通");
        this.put("776", "2,4 三 通");
        this.put("777", "2,4 一 通");
        this.put("778", "3,4 二 通");
        this.put("779", "3,4 一 通");
        this.put("780", "三門(3,2,1)");
        this.put("781", "三門(2,1,4)");
        this.put("782", "三門(1,4,3)");
        this.put("783", "三門(4,3,2)");

        
        //三公
        this.put("1001", "Win");
        this.put("1002", "Lose");
        this.put("1003", "Tie");
        this.put("1004", "3 Picture");
        this.put("1005", "pairPlus");
	}};
	
	/**
	 * 获取投注内容详情
	 * 
	 * 101^1.0^-1.0^,
	 * 
	 * @param GameBettingContent
	 * @return
	 */
	public static String getGameBettingContent(String GameBettingContent) {
		try {
			String[] strs = GameBettingContent.split(",");
			StringBuffer result = new StringBuffer();
			String kindname = null;
			for (String string : strs) {
				if(string != null && !string.equals("") ) {
					String[] s = string.split("\\^");
					kindname = __Betting_King_CONTENT.get(s[0]);
					result.append("下注[").append(kindname == null ? s[0] : kindname ).append("]");
					result.append(",下注").append(s[1]).append(",输赢").append(MoneyHelper.doubleFormat(s[2])).append("</br>");
				}
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return GameBettingContent;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getGameBettingContent("102^19.0^18.049999999999997^,102^19.0^18.049999999999997^,102^19.0^18.049999999999997^,"));
	}

}