package com.maven.game.enums;

public enum AGGameType {
	BJL("11", "百家乐",GameBigType.SX),
	LHD("12", "龙虎",GameBigType.SX),
	LP("13", "轮盘",GameBigType.SX),
	SB("14", "骰宝",GameBigType.SX),
	DZPK("15", "德州扑克",GameBigType.SX),
	FT("16", "番摊",GameBigType.SX),
	
	DZYX("17", "电子游戏",GameBigType.DZ)
	;
	
	private String code;
	private String value;
	private String gameBigType;

	private AGGameType(String code, String value, String gameBigType) {
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
		for (AGGameType enums : AGGameType.values()) {
            if (enums.getCode().equals(code)) {
                return enums.value;
            }
        }
		return null;
	}

	public static String getGameBigTypeByCode(String code){
		for (AGGameType enums : AGGameType.values()) {
            if (enums.getCode().equals(code)) {
                return enums.gameBigType;
            }
        }
		return null;
	}
}
