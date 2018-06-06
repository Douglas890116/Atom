package com.maven.util;

import org.apache.commons.lang.StringUtils;

/**
 * SGS申博游戏帮助类
 * @author klay
 *
 */
public class SGSUtils {

	public static String getGameName(String gameId) {
		if(StringUtils.isBlank(gameId)) return "";
		if(gameId.equals("DragonsLuck")) return "龙之谕";
		if(gameId.equals("WildFight")) return "功夫神话";
		if(gameId.equals("RedPhoenixRising")) return "凤凰涅槃";
		if(gameId.equals("WildSpartans")) return "斯巴达勇士";
		if(gameId.equals("ChineseTreasures")) return "华夏祥瑞";
		if(gameId.equals("FortuneFest")) return "恭贺新禧";
		if(gameId.equals("JadeCharms")) return "珠光宝器";
		if(gameId.equals("OceanFortune")) return "深海宝藏";
		if(gameId.equals("GodofWealth")) return "财神";
		if(gameId.equals("DivineWays")) return "圣兽传奇";
		if(gameId.equals("JingleBells")) return "铃儿响叮当";
		if(gameId.equals("GoldenLotus")) return "金莲呈祥";
		if(gameId.equals("MagicGate")) return "五福临门";
		if(gameId.equals("EpicJourney")) return "西游寻宝";
		if(gameId.equals("LuckyWizard")) return "幸运魔法师";
		if(gameId.equals("WinterWonders")) return "精灵童话";
		if(gameId.equals("GoldenLamps")) return "神灯之谜";
		if(gameId.equals("GoldenOffer")) return "麒麟送宝";
		if(gameId.equals("BlueDiamond")) return "蓝钻风暴";
		if(gameId.equals("FiveStar")) return "幸运五星";
		if(gameId.equals("MegaJade")) return "翡翠王";
		if(gameId.equals("ThreeKingdoms")) return "三国争霸";
		if(gameId.equals("GoldenToad")) return "富贵金蟾";
		if(gameId.equals("PussNBoots")) return "靴猫剑客";
		if(gameId.equals("GoldStar")) return "摇滚星";
		if(gameId.equals("GemsGoneWild")) return "宝石暴走";
		if(gameId.equals("AncientScript")) return "寻找法老";
		if(gameId.equals("TempleOfGold")) return "金殿丽影";
		if(gameId.equals("NeptunesRiches")) return "海神的赏赐";
		if(gameId.equals("PuntoBanco")) return "百家乐";
		if(gameId.equals("LuckyHalloween")) return "幸运万圣节";
		if(gameId.equals("RainbowJackpots")) return "彩虹的祝福";
		if(gameId.equals("FortuneHouse")) return "财富满屋";
		if(gameId.equals("FG-FLASH")) return "财神到";
		if(gameId.equals("LG-FLASH")) return "魔幻花园";
		if(gameId.equals("FTC-FLASH")) return "幸运水果";
		if(gameId.equals("SV-FLASH")) return "海底世界";
		if(gameId.equals("CTS-FLASH")) return "动物世界";
		if(gameId.equals("RBN-FLASH")) return "妲己转生";
		if(gameId.equals("RDP-FLASH")) return "";
		if(gameId.equals("RDP-FLASH")) return "机器人舞会";
		if(gameId.equals("BCT-FLASH")) return "";
		if(gameId.equals("PQ-FLASH")) return "海贼女王";
		if(gameId.equals("BCT-FLASH")) return "百家乐";
		if(gameId.equals("1")) return "百家乐 01";
		if(gameId.equals("2")) return "百家乐 02";
		if(gameId.equals("3")) return "百家乐 03";
		if(gameId.equals("4")) return "百家乐 04";
		if(gameId.equals("5")) return "百家乐 05";
		if(gameId.equals("6")) return "百家乐 06";
		if(gameId.equals("51")) return "星级百家乐 01";
		if(gameId.equals("52")) return "星级百家乐 02";
		if(gameId.equals("53")) return "星级百家乐 03";
		if(gameId.equals("54")) return "星级百家乐 04";
		if(gameId.equals("55")) return "星级百家乐 05";
		if(gameId.equals("56")) return "星级百家乐 06";
		if(gameId.equals("35")) return "极速龙虎";
		if(gameId.equals("20")) return "骰宝";
		if(gameId.equals("38")) return "轮盘";
		if(gameId.equals("42")) return "斗牛";
		if(gameId.equals("30")) return "三公";
		if(gameId.equals("25")) return "雀九";
		if(gameId.equals("sunbetlobby")) return "申博真人娱乐馆";
		if(gameId.equals("82")) return "自选多合一";
		return gameId;
	}
}
