package com.maven.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * SA游戏帮助类
 * 来自于沙龙SA.xlsx
 * @author klay
 *
 */
public class SAUtils {

	/**********初始化游戏类型************/
	public static Map<String, String> __GameType = new HashMap<String, String>(){{
		this.put("bac", "百家乐");
		this.put("dtx", "龙虎");
		this.put("sicbo", "骰宝");
		this.put("ftan", "番摊");
		this.put("rot", "轮盘");
		this.put("slot", "电子游艺");
		this.put("lottery", "48 彩/48彩其他玩法");
		this.put("minigame", "小游戏");
		
	}};
	
	
	public static String getGameName(String gameId) {
		if(StringUtils.isBlank(gameId)) return "";
		if(gameId.equals("EG-SLOT-S001")) return "大闹天宫";
		if(gameId.equals("EG-SLOT-S001")) return "大闹天宫";
		if(gameId.equals("EG-SLOT-S002")) return "嫦娥奔月";
		if(gameId.equals("EG-SLOT-A001")) return "过大年";
		if(gameId.equals("EG-SLOT-A002")) return "三星报囍";
		if(gameId.equals("EG-SLOT-A012")) return "趣怪丧尸";
		if(gameId.equals("EG-SLOT-A005")) return "梦幻女神";
		if(gameId.equals("EG-SLOT-A004")) return "龙虎";
		if(gameId.equals("EG-SLOT-A009")) return "同校生";
		if(gameId.equals("EG-SLOT-A003")) return "锦衣卫";
		if(gameId.equals("EG-SLOT-A010")) return "欢乐农场";
		if(gameId.equals("EG-SLOT-A011")) return "赤龙";
		if(gameId.equals("EG-SLOT-S003")) return "黄飞鸿";
		if(gameId.equals("EG-SLOT-A017")) return "南北狮王";
		if(gameId.equals("EG-SLOT-A013")) return "比基尼狂热";
		if(gameId.equals("EG-SLOT-A014")) return "幸运金钻";
		if(gameId.equals("EG-SLOT-A016")) return "热带宝藏";
		if(gameId.equals("EG-SLOT-A015")) return "脆爆水果";
		if(gameId.equals("EG-SLOT-A006")) return "济公";
		return gameId;
	}
}
