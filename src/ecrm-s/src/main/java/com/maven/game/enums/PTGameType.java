package com.maven.game.enums;

public enum PTGameType {
	ARCADE("Arcade Games", "Arcade Games",GameBigType.DZ),
	PROGRESSIVE("Progressive Video Pokers", "Progressive Video Pokers",GameBigType.DZ),
	TABLEGC("Table and Card Games", "Table and Card Games",GameBigType.DZ),
	CARD("Card Games", "Card Games",GameBigType.DZ),
	VideoPORKERS("Video Pokers", "Video Pokers",GameBigType.DZ),
	TABLEG("Table Games", "Table Games",GameBigType.DZ),
	SLOT("Slot Machines", "Slot Machines",GameBigType.DZ),
	SIDEGAMES("Sidegames", "Sidegames",GameBigType.DZ),
	FIXED("Fixed Odds", "Fixed Odds",GameBigType.DZ),
	MACHINES("Progressive Slot Machines", "Progressive Slot Machines",GameBigType.DZ),
	SCRATCHCARDS("Scratchcards", "Scratchcards",GameBigType.DZ),
	
	Live("Live Games", "Live Games","SX"),
	
	;
	
	private String code;
	private String value;
	private String gameBigType;

	private PTGameType(String code, String value, String gameBigType) {
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
		for (PTGameType enums : PTGameType.values()) {
            if (enums.getCode().equals(code)) {
                return enums.value;
            }
        }
		return null;
	}

	public static String getGameBigTypeByCode(String code){
		for (PTGameType enums : PTGameType.values()) {
            if (enums.getCode().equals(code)) {
                return enums.gameBigType;
            }
        }
		return null;
	}
}
