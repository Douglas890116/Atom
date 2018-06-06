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
public class PTUtils {
	
	
	/**********初始化游戏类型************/
	public static Map<String, String> __GameType = new HashMap<String, String>(){{
		this.put("Card Games", "卡牌游戏");
		this.put("Live Games", "真人视讯游戏");
		this.put("Mini games", "迷你游戏");
		
		this.put("Progressive Slot Machines", "奖池老虎机游戏");
		this.put("Sidegames", "小游戏");
		this.put("Scratchcards", "刮刮乐游戏");
		this.put("Slot Machines", "老虎机");
		this.put("Video Slots", "视频老虎机");
		this.put("Scratch Cards", "刮刮乐游戏");
		this.put("Fixed Odds", "固定赔率游戏");
		this.put("Fixed-Odds Games", "固定赔率游戏");
		this.put("Table Games", "桌面游戏");
		this.put("Table & Card Games", "桌面和卡牌游戏");
	}};
	
	
	
}