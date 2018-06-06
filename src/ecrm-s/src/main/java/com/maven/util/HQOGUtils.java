package com.maven.util;

import org.apache.commons.lang.StringUtils;

/**
 * 东方OG帮助类
 * @author klay
 *
 */
public class HQOGUtils {

	public static String getGameName(String aoiGameNameId) {
		if(StringUtils.isBlank(aoiGameNameId)) return "";
		if("11".equals(aoiGameNameId)) return "百家乐";
		if("12".equals(aoiGameNameId)) return "龙虎";
		if("13".equals(aoiGameNameId)) return "轮盘";
		if("14".equals(aoiGameNameId)) return "骰宝";
		if("15".equals(aoiGameNameId)) return "德州扑克";
		if("16".equals(aoiGameNameId)) return "番摊";
		return aoiGameNameId;
	}
}