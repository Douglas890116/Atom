package com.maven.util;

import org.apache.commons.lang.StringUtils;

import com.maven.game.enums.GameBigType;
import com.maven.game.enums.GameEnum.Enum_GameType;
/**
 * 游戏帮助类
 * @author klay
 *
 */
public class GameUtils {
	/**
	 * 获取游戏大类中文名称
	 * @param type
	 * @return
	 */
	public static String getGameBigTypeName(String gameBigType) {
		if(StringUtils.isBlank(gameBigType)) return "";
		if(gameBigType.equals(GameBigType.TY)) return "体育";
		if(gameBigType.equals(GameBigType.SX)) return "视讯";
		if(gameBigType.equals(GameBigType.CP)) return "彩票";
		if(gameBigType.equals(GameBigType.DZ)) return "电子";
		if(gameBigType.equals(GameBigType.QP)) return "棋牌";
		return gameBigType;
	}
	/**
	 * 通过游戏名称简写
	 * 获取游戏类型中文名称
	 * @param bettingcode
	 * @return
	 */
	public static String getGameTypeNameBybettingCode(String bettingcode) {
		if(StringUtils.isBlank(bettingcode)) return "";
    	for (Enum_GameType eg : Enum_GameType.values()) {
			if(bettingcode.equals(eg.bettingcode))
				return eg.name;
		}
    	return bettingcode;
	}
	/**
	 * 通过游戏名称
	 * 类型获取游戏中文名称
	 * @param gameType
	 * @return
	 */
	public static String getGameTypeNameByGameType(String gameType) {
		if(StringUtils.isBlank(gameType)) return "";
		for (Enum_GameType eg : Enum_GameType.values()) {
			if(gameType.equals(eg.gametype))
				return eg.name;
		}
		return gameType;
	}
	public static void main(String[] args) {
		String gameType = "W88Game";
		System.out.println(GameUtils.getGameTypeNameByGameType(gameType));
	}
}
