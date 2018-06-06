package com.maven.game.enums;

import java.util.HashMap;
import java.util.Map;


public enum NHQGameType {
	BJL("1", "百家乐",GameBigType.SX),
	LH("2", "龙虎",GameBigType.SX),
	FT("3", "番摊",GameBigType.SX),
	LP("4", "轮盘",GameBigType.SX),
	SB("5", "骰宝",GameBigType.SX),
	
	;
	
	private String code;
	private String value;
	private String gameBigType;
	public static Map<String,String> map; 
	private NHQGameType(String code, String value, String gameBigType) {
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
		for (NHQGameType enums : NHQGameType.values()) {
            if (enums.getCode().equals(code)) {
                return enums.value;
            }
        }
		return null;
	}

	public static String getGameBigTypeByCode(String code){
		for (NHQGameType enums : NHQGameType.values()) {
            if (enums.getCode().equals(code)) {
                return enums.gameBigType;
            }
        }
		return null;
	}
	public static Map<String,String> getGameType(){
		if (null == map) map = new HashMap<String, String>();
		for (NHQGameType s : NHQGameType.values()) {
			map.put(s.code, s.value);
		}
		return map;
	}
}
