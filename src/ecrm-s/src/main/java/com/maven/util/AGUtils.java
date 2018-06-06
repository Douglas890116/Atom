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
public class AGUtils {
	
	/**********初始化平台类型************/
	public static Map<String, String> __PlatformType = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("AGIN", "AG国际厅");
		this.put("AGQ", "AG旗舰厅极速版");
		this.put("HUNTER", "捕鱼王");
		this.put("XIN", "XIN电子");
	}};
	
	/**********初始化平台的大厅类型************/
	public static Map<String, String> __Roundype = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("DSP", "国际厅");
		this.put("AGQ", "旗舰厅");
		this.put("VIP", "包桌厅");
		this.put("LED", "竞咪厅");
		this.put("LOTTO", "彩票");
		this.put("AGHH", "豪华厅");
	}};
	
	
	/**
	 * 根据游戏类型获取游戏名称
	 * @param gameType
	 * @return
	 */
	public static String getGameName(String gameType) {
		if(StringUtils.isBlank(gameType)) return "";
		if("BAC".contentEquals(gameType)) return "百家乐";
		if("CBAC".contentEquals(gameType)) return "包桌百家乐";
		if("LINK".contentEquals(gameType)) return "连环百家乐";
		if("DT".contentEquals(gameType)) return "龙虎";
		if("SHB".contentEquals(gameType)) return "骰宝";
		if("ROU".contentEquals(gameType)) return "轮盘";
		if("FT".contentEquals(gameType)) return "番摊";
		if("LBAC".contentEquals(gameType)) return "竞咪百家乐";
		if("ULPK".contentEquals(gameType)) return "终极德州扑克";
		if("SBAC".contentEquals(gameType)) return "保險百家樂";
		if("SL1".contentEquals(gameType)) return "巴西世界杯";
		if("SL2".contentEquals(gameType)) return "疯狂水果店";
		if("SL3".contentEquals(gameType)) return "3D水族馆";
		if("PK_J".contentEquals(gameType)) return "视频扑克(杰克高手)";
		if("SL4".contentEquals(gameType)) return "极速赛ft";
		if("PKBJ".contentEquals(gameType)) return "新视频扑克(杰克高手)";
		if("FRU".contentEquals(gameType)) return "水果拉霸";
		if("HUNTER".contentEquals(gameType)) return "捕鱼王";
		if("SLM1".contentEquals(gameType)) return "美女沙排(沙滩排球)";
		if("SLM2".contentEquals(gameType)) return "运财羊(新年运财羊)";
		if("SLM3".contentEquals(gameType)) return "武圣传";
		if("SC01".contentEquals(gameType)) return "幸运老虎机";
		if("TGLW".contentEquals(gameType)) return "极速幸运轮";
		if("SLM4".contentEquals(gameType)) return "武则天";
		if("TGCW".contentEquals(gameType)) return "赌场战争";
		if("SB01".contentEquals(gameType)) return "太空漫游";
		if("SB02".contentEquals(gameType)) return "复古花园";
		if("SB03".contentEquals(gameType)) return "关东煮";
		if("SB04".contentEquals(gameType)) return "牧场咖啡";
		if("SB05".contentEquals(gameType)) return "甜一甜屋";
		if("SB06".contentEquals(gameType)) return "日本武士";
		if("SB07".contentEquals(gameType)) return "象棋老虎机";
		if("SB08".contentEquals(gameType)) return "麻将老虎机";
		if("SB09".contentEquals(gameType)) return "西洋棋老虎机";
		if("SB10".contentEquals(gameType)) return "开心农场";
		if("SB11".contentEquals(gameType)) return "夏日营地";
		if("SB12".contentEquals(gameType)) return "海底漫游";
		if("SB13".contentEquals(gameType)) return "鬼马小丑";
		if("SB14".contentEquals(gameType)) return "机动乐园";
		if("SB15".contentEquals(gameType)) return "惊吓鬼屋";
		if("SB16".contentEquals(gameType)) return "疯狂马戏团";
		if("SB17".contentEquals(gameType)) return "海洋剧场";
		if("SB18".contentEquals(gameType)) return "水上乐园";
		if("SB25".contentEquals(gameType)) return "土地神";
		if("SB26".contentEquals(gameType)) return "布袋和尚";
		if("SB27".contentEquals(gameType)) return "正财神";
		if("SB28".contentEquals(gameType)) return "武财神";
		if("SB29".contentEquals(gameType)) return "偏财神";
		if("SB19".contentEquals(gameType)) return "空中战争";
		if("SB20".contentEquals(gameType)) return "摇滚狂迷";
		if("SB21".contentEquals(gameType)) return "越野机ft";
		if("SB22".contentEquals(gameType)) return "埃及奥秘";
		if("SB23".contentEquals(gameType)) return "欢乐时光";
		if("SB24".contentEquals(gameType)) return "侏罗纪";
		if("AV01".contentEquals(gameType)) return "性感女仆";
		if("XG01".contentEquals(gameType)) return "龙珠";
		if("XG02".contentEquals(gameType)) return "幸运 8";
		if("XG03".contentEquals(gameType)) return "闪亮女郎";
		if("XG04".contentEquals(gameType)) return "金鱼";
		if("XG05".contentEquals(gameType)) return "中国新年";
		if("XG06".contentEquals(gameType)) return "海盗王";
		if("XG07".contentEquals(gameType)) return "鲜果狂热";
		if("XG08".contentEquals(gameType)) return "小熊猫";
		if("XG09".contentEquals(gameType)) return "大豪客";
		if("SB30".contentEquals(gameType)) return "灵猴献瑞";
		if("SB31".contentEquals(gameType)) return "天空守护者";
		if("PKBD".contentEquals(gameType)) return "百搭二王";
		if("PKBB".contentEquals(gameType)) return "红利百搭";
		if("SB32".contentEquals(gameType)) return "齐天大圣";
		if("SB33".contentEquals(gameType)) return "糖果碰碰乐";
		if("SB34".contentEquals(gameType)) return "冰河世界";
		if("FRU2".contentEquals(gameType)) return "水果拉霸 2";
		if("TG01".contentEquals(gameType)) return "21 点 (电子游戏)";
		if("TG02".contentEquals(gameType)) return "百家乐 (电子游戏)";
		if("TG03".contentEquals(gameType)) return "轮盘 (电子游戏)";
		if("SB35".contentEquals(gameType)) return "欧洲列强争霸";
		if("SB36".contentEquals(gameType)) return "捕鱼王者";
		if("SB37".contentEquals(gameType)) return "上海百乐门";
		if("SB38".contentEquals(gameType)) return "竞技狂热";
		if("SB39".contentEquals(gameType)) return "太空水果";
		if("SB40".contentEquals(gameType)) return "秦始皇";
		if("TA01".contentEquals(gameType)) return "多手二十一点 低额投注";
		if("TA02".contentEquals(gameType)) return "多手二十一点";
		if("TA03".contentEquals(gameType)) return "多手二十一点 高额投注";
		if("TA04".contentEquals(gameType)) return "1 手二十一点 低额投注";
		if("TA05".contentEquals(gameType)) return "1 手二十一点";
		if("TA06".contentEquals(gameType)) return "1 手二十一点 高额投注";
		if("TA07".contentEquals(gameType)) return "Hilo 低额投注";
		if("TA08".contentEquals(gameType)) return "Hilo";
		if("TA09".contentEquals(gameType)) return "Hilo 高額投注";
		if("TA0A".contentEquals(gameType)) return "5 手 Hilo";
		if("TA0B".contentEquals(gameType)) return "5 手 Hilo 高额投注";
		if("TA0C".contentEquals(gameType)) return "3 手 Hilo 高额投注";
		if("TA0F".contentEquals(gameType)) return "轮盘 高额投注";
		if("TA0G".contentEquals(gameType)) return "轮盘";
		if("TA0Z".contentEquals(gameType)) return "5 手杰克高手";
		if("TA10".contentEquals(gameType)) return "5 手百搭小丑";
		if("TA11".contentEquals(gameType)) return "5 手百搭二王";
		if("TA12".contentEquals(gameType)) return "1 手杰克高手";
		if("TA13".contentEquals(gameType)) return "10 手杰克高手";
		if("TA14".contentEquals(gameType)) return "25 手杰克高手";
		if("TA15".contentEquals(gameType)) return "50 手杰克高手";
		if("TA17".contentEquals(gameType)) return "1 手百搭小丑";
		if("TA18".contentEquals(gameType)) return "10 手百搭小丑";
		if("TA19".contentEquals(gameType)) return "25 手百搭小丑";
		if("TA1A".contentEquals(gameType)) return "50 手百搭小丑";
		if("TA1C".contentEquals(gameType)) return "1 手百搭二王";
		if("TA1D".contentEquals(gameType)) return "10 手百搭二王";
		if("TA1E".contentEquals(gameType)) return "25 手百搭二王";
		if("TA1F".contentEquals(gameType)) return "50 手百搭二王";
		if("TA0U".contentEquals(gameType)) return "经典轿ft";
		if("TA0V".contentEquals(gameType)) return "星际大战";
		if("TA0W".contentEquals(gameType)) return "海盗夺宝";
		if("TA0X".contentEquals(gameType)) return "巴黎茶座";
		if("TA0Y".contentEquals(gameType)) return "金龙献宝";
		if("XG10".contentEquals(gameType)) return "龙舟竞渡";
		if("XG11".contentEquals(gameType)) return "中秋佳节";
		if("XG12".contentEquals(gameType)) return "韩风劲舞";
		if("XG13".contentEquals(gameType)) return "美女大格斗";
		if("XG14".contentEquals(gameType)) return "龙凤呈祥";
		if("XG16".contentEquals(gameType)) return "黄金对垒";
		if("TA0P".contentEquals(gameType)) return "怪兽食坊";
		if("TA0S".contentEquals(gameType)) return "足球竞赛";
		if("TA0L".contentEquals(gameType)) return "无法无天";
		if("TA0M".contentEquals(gameType)) return "法老秘密";
		if("TA0N".contentEquals(gameType)) return "烈火战ft";
		if("TA0O".contentEquals(gameType)) return "捕猎季节";
		if("TA0Q".contentEquals(gameType)) return "日与夜";
		if("TA0R".contentEquals(gameType)) return "七大奇迹";
		if("TA0T".contentEquals(gameType)) return "珠光宝气";
		if("27".contentEquals(gameType)) return "江苏快三";
		if("24".contentEquals(gameType)) return "重庆时时彩";
		if("13".contentEquals(gameType)) return "中国福彩 3D";
		if("25".contentEquals(gameType)) return "北京快乐 8";
		if("26".contentEquals(gameType)) return "湖南快乐十分";
		if("29".contentEquals(gameType)) return "十一运夺金";
		if("23".contentEquals(gameType)) return "江西时时彩";
		if("DZPK".contentEquals(gameType)) return "德州撲克";
		if("GDMJ".contentEquals(gameType)) return "廣東麻將";
		if("FIFA".contentEquals(gameType)) return "体育";
		return gameType;
	}
	/**
	 * 获取游戏玩法名称
	 * @param playValue
	 * @return
	 */
	public static String getPlayType(String playValue, String gameType) {
		if(StringUtils.isBlank(playValue)) return "";
		if(!StringUtils.isBlank(gameType)
			&& (gameType.equals("BAC") || gameType.equals("CBAC")
			||  gameType.equals("LINK") || gameType.equals("LBAC"))
			||  gameType.equals("TG02") || gameType.equals("DT")) {
			if("1".contentEquals(playValue)) return "庄";
			if("2".contentEquals(playValue)) return "闲";
			if("3".contentEquals(playValue)) return "和";
			if("4".contentEquals(playValue)) return "庄对";
			if("5".contentEquals(playValue)) return "闲对";
			if("6".contentEquals(playValue)) return "大";
			if("7".contentEquals(playValue)) return "小";
			if("8".contentEquals(playValue)) return "莊保險";
			if("9".contentEquals(playValue)) return "閑保險";
			if("11".contentEquals(playValue)) return "庄免佣";
			if("12".contentEquals(playValue)) return "庄龙宝";
			if("13".contentEquals(playValue)) return "闲龙宝";
			if("21".contentEquals(playValue)) return "龙";
			if("22".contentEquals(playValue)) return "虎";
			if("23".contentEquals(playValue)) return "和（龙虎）";
		}
		if("41".contentEquals(playValue)) return "大 (big)";
		if("42".contentEquals(playValue)) return "小 (small)";
		if("43".contentEquals(playValue)) return "单 (single)";
		if("44".contentEquals(playValue)) return "双 (double)";
		if("45".contentEquals(playValue)) return "全围 (all wei)";
		if("46".contentEquals(playValue)) return "围 1 (wei 1)";
		if("47".contentEquals(playValue)) return "围 2 (wei 2)";
		if("48".contentEquals(playValue)) return "围 3 (wei 3)";
		if("49".contentEquals(playValue)) return "围 4 (wei 4)";
		if("50".contentEquals(playValue)) return "围 5 (wei 5)";
		if("51".contentEquals(playValue)) return "围 6 (wei 6)";
		if("52".contentEquals(playValue)) return "单点 1 (single 1)";
		if("53".contentEquals(playValue)) return "单点 2 (single 2)";
		if("54".contentEquals(playValue)) return "单点 3 (single 3)";
		if("55".contentEquals(playValue)) return "单点 4 (single 4)";
		if("56".contentEquals(playValue)) return "单点 5 (single 5)";
		if("57".contentEquals(playValue)) return "单点 6 (single 6)";
		if("58".contentEquals(playValue)) return "对子 1 (double 1)";
		if("59".contentEquals(playValue)) return "对子 2 (double 2)";
		if("60".contentEquals(playValue)) return "对子 3 (double 3)";
		if("61".contentEquals(playValue)) return "对子 4 (double 4)";
		if("62".contentEquals(playValue)) return "对子 5 (double 5)";
		if("63".contentEquals(playValue)) return "对子 6 (double 6)";
		if("64".contentEquals(playValue)) return "组合 12 (combine 12)";
		if("65".contentEquals(playValue)) return "组合 13 (combine 13)";
		if("66".contentEquals(playValue)) return "组合 14 (combine 14)";
		if("67".contentEquals(playValue)) return "组合 15 (combine 15)";
		if("68".contentEquals(playValue)) return "组合 16 (combine 16)";
		if("69".contentEquals(playValue)) return "组合 23 (combine 23)";
		if("70".contentEquals(playValue)) return "组合 24 (combine 24)";
		if("71".contentEquals(playValue)) return "组合 25 (combine 25)";
		if("72".contentEquals(playValue)) return "组合 26 (combine 26)";
		if("73".contentEquals(playValue)) return "组合 34 (combine 34)";
		if("74".contentEquals(playValue)) return "组合 35 (combine 35)";
		if("75".contentEquals(playValue)) return "组合 36 (combine 36)";
		if("76".contentEquals(playValue)) return "组合 45 (combine 45)";
		if("77".contentEquals(playValue)) return "组合 46 (combine 46)";
		if("78".contentEquals(playValue)) return "组合 56 (combine 56)";
		if("79".contentEquals(playValue)) return "和值 4 (sum 4)";
		if("80".contentEquals(playValue)) return "和值 5 (sum 5)";
		if("81".contentEquals(playValue)) return "和值 6 (sum 6)";
		if("82".contentEquals(playValue)) return "和值 7 (sum 7)";
		if("83".contentEquals(playValue)) return "和值 8 (sum 8)";
		if("84".contentEquals(playValue)) return "和值 9 (sum 9)";
		if("85".contentEquals(playValue)) return "和值 10 (sum 10)";
		if("86".contentEquals(playValue)) return "和值 11 (sum 11)";
		if("87".contentEquals(playValue)) return "和值 12 (sum 12)";
		if("88".contentEquals(playValue)) return "和值 13 (sum 13)";
		if("89".contentEquals(playValue)) return "和值 14 (sum 14)";
		if("90".contentEquals(playValue)) return "和值 15 (sum 15)";
		if("91".contentEquals(playValue)) return "和值 16 (sum 16)";
		if("92".contentEquals(playValue)) return "和值 17 (sum 17)";
		if("101".contentEquals(playValue)) return "直接注";
		if("102".contentEquals(playValue)) return "分注";
		if("103".contentEquals(playValue)) return "街注";
		if("104".contentEquals(playValue)) return "三數";
		if("105".contentEquals(playValue)) return "4 個號碼";
		if("106".contentEquals(playValue)) return "角注";
		if("107".contentEquals(playValue)) return "列注(列 1)";
		if("108".contentEquals(playValue)) return "列注(列 2)";
		if("109".contentEquals(playValue)) return "列注(列 3)";
		if("110".contentEquals(playValue)) return "線注";
		if("111".contentEquals(playValue)) return "打一";
		if("112".contentEquals(playValue)) return "打二";
		if("113".contentEquals(playValue)) return "打三";
		if("114".contentEquals(playValue)) return "紅";
		if("115".contentEquals(playValue)) return "黑";
		if("116".contentEquals(playValue)) return "大";
		if("117".contentEquals(playValue)) return "小";
		if("118".contentEquals(playValue)) return "單";
		if("119".contentEquals(playValue)) return "雙";
		if("130".contentEquals(playValue)) return "1 番";
		if("131".contentEquals(playValue)) return "2 番";
		if("132".contentEquals(playValue)) return "3 番";
		if("133".contentEquals(playValue)) return "4 番";
		if("134".contentEquals(playValue)) return "1 念 2";
		if("135".contentEquals(playValue)) return "1 念 3";
		if("136".contentEquals(playValue)) return "1 念 4";
		if("137".contentEquals(playValue)) return "2 念 1";
		if("138".contentEquals(playValue)) return "2 念 3";
		if("139".contentEquals(playValue)) return "2 念 4";
		if("140".contentEquals(playValue)) return "3 念 1";
		if("141".contentEquals(playValue)) return "3 念 2";
		if("142".contentEquals(playValue)) return "3 念 4";
		if("143".contentEquals(playValue)) return "4 念 1";
		if("144".contentEquals(playValue)) return "4 念 2";
		if("145".contentEquals(playValue)) return "4 念 3";
		if("146".contentEquals(playValue)) return "角(1,2)";
		if("147".contentEquals(playValue)) return "單";
		if("148".contentEquals(playValue)) return "角(1,4)";
		if("149".contentEquals(playValue)) return "角(2,3)";
		if("150".contentEquals(playValue)) return "雙";
		if("151".contentEquals(playValue)) return "角(3,4)";
		if("152".contentEquals(playValue)) return "1,2 四 通";
		if("153".contentEquals(playValue)) return "1,2 三 通";
		if("154".contentEquals(playValue)) return "1,3 四 通";
		if("155".contentEquals(playValue)) return "1,3 二 通";
		if("156".contentEquals(playValue)) return "1,4 三 通";
		if("157".contentEquals(playValue)) return "1,4 二 通";
		if("158".contentEquals(playValue)) return "2,3 四 通";
		if("159".contentEquals(playValue)) return "2,3 一 通";
		if("160".contentEquals(playValue)) return "2,4 三 通";
		if("161".contentEquals(playValue)) return "2,4 一 通";
		if("162".contentEquals(playValue)) return "3,4 二 通";
		if("163".contentEquals(playValue)) return "3,4 一 通";
		if("164".contentEquals(playValue)) return "三門(3,2,1)";
		if("165".contentEquals(playValue)) return "三門(2,1,4)";
		if("166".contentEquals(playValue)) return "三門(1,4,3)";
		if("167".contentEquals(playValue)) return "三門(4,3,2)";
		if("6".contentEquals(playValue)) return "任选一";
		if("7".contentEquals(playValue)) return "任选二";
		if("8".contentEquals(playValue)) return "任选三";
		if("9".contentEquals(playValue)) return "任选四";
		if("10".contentEquals(playValue)) return "任选五";
		if("17".contentEquals(playValue)) return "任选六";
		if("18".contentEquals(playValue)) return "任选七";
		if("19".contentEquals(playValue)) return "任选八";
		if("20".contentEquals(playValue)) return "任选九";
		if("21".contentEquals(playValue)) return "任选十";
		if("279".contentEquals(playValue)) return "直选";
		if("281".contentEquals(playValue)) return "组三";
		if("282".contentEquals(playValue)) return "组六";
		if("503".contentEquals(playValue)) return "和值";
		if("504".contentEquals(playValue)) return "二同号单选";
		if("505".contentEquals(playValue)) return "二同号复选";
		if("506".contentEquals(playValue)) return "二不同号";
		if("507".contentEquals(playValue)) return "三不同号";
		if("508".contentEquals(playValue)) return "三同号单选";
		if("509".contentEquals(playValue)) return "三同号通选";
		if("510".contentEquals(playValue)) return "三连号通选";
		if("515".contentEquals(playValue)) return "任选二";
		if("516".contentEquals(playValue)) return "任选三";
		if("517".contentEquals(playValue)) return "任选四";
		if("518".contentEquals(playValue)) return "任选五";
		if("519".contentEquals(playValue)) return "任选六";
		if("520".contentEquals(playValue)) return "任选七";
		if("521".contentEquals(playValue)) return "任选八";
		if("522".contentEquals(playValue)) return "前一直选";
		if("523".contentEquals(playValue)) return "前二直选";
		if("524".contentEquals(playValue)) return "前二组选";
		if("525".contentEquals(playValue)) return "前三直选";
		if("526".contentEquals(playValue)) return "前三组选";
		if("527".contentEquals(playValue)) return "任选二";
		if("528".contentEquals(playValue)) return "任选三";
		if("529".contentEquals(playValue)) return "任选四";
		if("530".contentEquals(playValue)) return "任选五";
		if("531".contentEquals(playValue)) return "大小单双";
		if("576".contentEquals(playValue)) return "一星直选";
		if("581".contentEquals(playValue)) return "二星直选";
		if("582".contentEquals(playValue)) return "三星直选";
		if("584".contentEquals(playValue)) return "五星直选";
		if("585".contentEquals(playValue)) return "二星组选";
		if("586".contentEquals(playValue)) return "三星组三";
		if("587".contentEquals(playValue)) return "三星组六";
		if("588".contentEquals(playValue)) return "五星通选";
		if("631".contentEquals(playValue)) return "大小单双";
		if("632".contentEquals(playValue)) return "任选一";
		if("633".contentEquals(playValue)) return "任选二";
		if("634".contentEquals(playValue)) return "四星直选";
		if("635".contentEquals(playValue)) return "四星通选";
		if("676".contentEquals(playValue)) return "一星直选";
		if("681".contentEquals(playValue)) return "二星直选";
		if("682".contentEquals(playValue)) return "三星直选";
		if("684".contentEquals(playValue)) return "五星直选";
		if("685".contentEquals(playValue)) return "二星组选";
		if("686".contentEquals(playValue)) return "三星组三";
		if("687".contentEquals(playValue)) return "三星组六";
		if("688".contentEquals(playValue)) return "五星通选";
		if("180".contentEquals(playValue)) return "底注+盲注";
		if("181".contentEquals(playValue)) return "一倍加注";
		if("182".contentEquals(playValue)) return "二倍加注";
		if("183".contentEquals(playValue)) return "三倍加注";
		if("184".contentEquals(playValue)) return "四倍加注";
		if("190".contentEquals(playValue)) return "主队";
		if("191".contentEquals(playValue)) return "客队";
		if("192".contentEquals(playValue)) return "和";
		if("193".contentEquals(playValue)) return "大";
		if("194".contentEquals(playValue)) return "小";
		if("195".contentEquals(playValue)) return "单";
		if("196".contentEquals(playValue)) return "双";
		return playValue;
	}

}