package com.maven.game.enums;

public enum TAGGameType {
	
	BJL("BAC", "百家乐",GameBigType.SX),
	BZBJL("CBAC", "包桌百家乐",GameBigType.SX),
	LXHBJ("LINK", "连环百家乐",GameBigType.SX),
	LH("DT", "龙虎",GameBigType.SX),
	SB("SHB", "骰宝",GameBigType.SX),
	LP("ROU", "轮盘",GameBigType.SX),
	FT("FT", "番攤",GameBigType.SX),
	
	SJB("FIFA","世界盃",GameBigType.TY),
	
	LHJ("SL1","老虎機",GameBigType.DZ),
	SGD("SL2", "水果店",GameBigType.DZ),
	SZG("SL3", "水族館",GameBigType.DZ),
	PK_J("PK_J", "视频扑克(杰克高手)",GameBigType.DZ),
	SL4("SL4", "极速赛车",GameBigType.DZ),
	PKBJ("PKBJ", "新视频扑克(杰克高手)",GameBigType.DZ),
	FRU("FRU", "水果拉霸",GameBigType.DZ),
	HUNTER("HUNTER", "捕鱼王",GameBigType.DZ),
	SLM1("SLM1", "美女沙排(沙滩排球)",GameBigType.DZ),
	SLM2("SLM2", "运财羊(新年运财羊)",GameBigType.DZ),
	SLM3("SLM3", "武圣传",GameBigType.DZ),
	SC01("SC01", "幸运老虎机",GameBigType.DZ),
	TGLW("TGLW", "极速幸运轮",GameBigType.DZ),
	SLM4("SLM4", "武则天",GameBigType.DZ),
	TGCW("TGCW", "赌场战争",GameBigType.DZ),
	SB01("SB01", "太空漫游",GameBigType.DZ),
	SB02("SB02", "复古花园",GameBigType.DZ),
	SB03("SB03", "关东煮",GameBigType.DZ),
	SB04("SB04", "牧场咖啡",GameBigType.DZ),
	SB05("SB05", "甜一甜屋",GameBigType.DZ),
	
	SB06("SB06", "日本武士",GameBigType.DZ),
	SB07("SB07", "象棋老虎机",GameBigType.DZ),
	SB08("SB08", "麻将老虎机",GameBigType.DZ),
	SB09("SB09", "西洋棋老虎机",GameBigType.DZ),
	SB10("SB10", "开心农场",GameBigType.DZ),
	SB11("SB11", "夏日营地",GameBigType.DZ),
	SB12("SB12", "海底漫游",GameBigType.DZ),
	SB13("SB13", "鬼马小丑",GameBigType.DZ),
	SB14("SB14", "机动乐园",GameBigType.DZ),
	SB15("SB15", "惊吓鬼屋",GameBigType.DZ),
	SB16("SB16", "疯狂马戏团",GameBigType.DZ),
	SB17("SB17", "海洋剧场",GameBigType.DZ),
	SB18("SB18", "水上乐园",GameBigType.DZ),
	   
	;	
	private String code;
	private String value;
	private String gameBigType;

	private TAGGameType(String code, String value, String gameBigType) {
		this.code = code;
		this.value = value;
		this.gameBigType = gameBigType;
	}

	public String getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}
	
	public String getGameBigType() {
		return this.gameBigType;
	}
	
	public static String getValueByCode(String code){
		for (TAGGameType enums : TAGGameType.values()) {
            if (enums.getCode().equals(code)) {
                return enums.value;
            }
        }
		return null;
	}

	public static String getGameBigTypeByCode(String code){
		for (TAGGameType enums : TAGGameType.values()) {
            if (enums.getCode().equals(code)) {
                return enums.gameBigType;
            }
        }
		return null;
	}
}
