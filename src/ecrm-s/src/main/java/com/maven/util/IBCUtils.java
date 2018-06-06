package com.maven.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 沙巴体育
 * @author klay
 *
 */
public class IBCUtils {
	
	/**********初始化运动类型************/
	public static Map<String, String> __SportType = new HashMap<String, String>(){{
		this.put("1", "足球");
		this.put("2", "篮球");
		this.put("3", "足球");
		this.put("4", "冰上曲棍球");
		this.put("5", "网球");
		this.put("6", "排球");
		this.put("7", "台球");
		this.put("8", "棒球");
		this.put("9", "羽毛球");
		this.put("10", "高尔夫");
		this.put("11", "赛车");
		this.put("12", "游泳");
		this.put("13", "政治");
		this.put("14", "水球");
		this.put("15", "跳水");
		this.put("16", "拳击");
		this.put("17", "射箭");
		this.put("18", "乒乓球");
		this.put("19", "举重");
		this.put("20", "皮划艇");
		this.put("21", "体操");
		this.put("22", "田径");
		this.put("23", "马术");
		this.put("24", "手球");
		this.put("25", "飞镖");
		this.put("26", "橄榄球");
		this.put("27", "板球");
		this.put("28", "曲棍球");
		this.put("29", "冬季运动");
		this.put("30", "壁球");
		this.put("31", "娱乐");
		this.put("32", "网前球");
		this.put("33", "骑自行车");
		
		this.put("41", "铁人三项 ");
		this.put("42", "摔跤");
		
		this.put("43", "电子竞技");
		this.put("44", "泰拳");
		this.put("50", "板球游戏");
		this.put("99", "其他运动");
		
		this.put("99MP", "混合足球Mix Parlay");
		this.put("151", "赛马");
		this.put("152", "灰狗 ");
		this.put("153", "马具 ");
		this.put("154", "赛马固定赔率 ");
		this.put("161", "数字游戏 ");
		this.put("180", "虚拟足球 ");
		this.put("181", "虚拟赛马 ");
		
		this.put("182", "虚拟灵狮");
		this.put("183", "虚拟赛道");
		this.put("184", "虚拟F1");
		this.put("185", "虚拟自行车");
		this.put("186", "虚拟网球");
		
		this.put("202", "基诺");
		this.put("251", "赌场");
		this.put("208", "RNG游戏");
		this.put("209", "迷你游戏");
		this.put("210", "移动");
	}};
	
	/**********初始化赔率类型************/
	public static Map<String, String> __OddsType = new HashMap<String, String>(){{
		this.put("1", "马来人的赔率");
		this.put("2", "香港赔率");
		this.put("3", "十进制的赔率");
		this.put("4", "印度赔率");
		this.put("5", "美国赔率");
	}};
	
	/**********初始化状态数据************/
	public static Map<String, String> __TicketStatus = new HashMap<String, String>(){{
		this.put("waiting", "（等）");
		this.put("running", "（进行 中）");
		this.put("won", "（赢） ");
		this.put("lose", "（输）");
		this.put("draw", "（平 局） ");
		this.put("reject", "（拒 绝） ");
		this.put("refund", "（退 钱） ");
		this.put("void", "（取 消） ");
		this.put("half won", "（上半场赢）");
		this.put("half lose", "（上半场输）");
	}};
	
}