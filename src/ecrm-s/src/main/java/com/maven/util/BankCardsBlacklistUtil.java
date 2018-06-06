package com.maven.util;

import java.util.List;

import com.maven.cache.SystemCache;
import com.maven.entity.BankCardsBlacklist;

public class BankCardsBlacklistUtil {
	/**
	 * 检查用户银行卡相关信息是否在黑名单中
	 * @param info
	 * @return true - 没有问题；false - 存在黑名单中
	 */
	public static boolean checkUserInfo(String info) {
		List<BankCardsBlacklist> blacklist = SystemCache.getInstance().getBankCardsBlacklist();
		if (blacklist != null && blacklist.size() > 0) {
			for (BankCardsBlacklist cardsBlacklist : blacklist) {
				if (cardsBlacklist.isExist(info)) return false;
			}
		}
		return true;
	}
}