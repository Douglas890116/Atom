package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.FtpUtils;
import com.hy.pull.common.util.GameHttpUtil;

/**
 * 拉取PNG游戏数据工具类
 * 创建日期：2016-12-29
 * @author temdy
 */
public class PNGGame {
	
	
	/**
	 * 获取PNG游戏数据列表
	 * @param agent 代理帐号
	 * @param dir 数据文件目录
	 * @param max 上次拉取最大值
	 * @param ftp ftp服务器对象
	 * @param code 代理编码
	 * @return 数据列表
	 */
	public static List<Map<String, Object>> getData(String agent, String dir, String max, FtpUtils ftp,String code) {
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			max = getMaxDate(max);
			//System.out.println(max);
			String p1 = "/".concat(dir).concat("/").concat(max.substring(0, 8)).concat("/");//游戏数据路径
			String p2 = "/".concat(dir).concat("/lostAndfound/").concat(max.substring(0, 8)).concat("/");//游戏补单数据路径
			//System.out.println(p1);
			//System.out.println(p2);
			//第1步拉取正常数据
			ftp.ftpClient.changeWorkingDirectory(p1);
			List<String> ls1 = ftp.getFileList(p1);
			//System.out.println(ls1);
			int size1 = ls1.size();
			if(size1 > 0){
				int index = ls1.indexOf(max);
				max = ls1.get(size1-1);//获取最大值
				ftp.setMax(max);
				if(index > 0){
					ls1 = ls1.subList(index, size1);//重新获取文件列表
				}
				list.addAll(getData(code,ftp,ls1));
			}
			//第2步拉取补单数据
			ftp.ftpClient.changeWorkingDirectory(p2);
			List<String> ls2 = ftp.getFileList(p2);
			int size2 = ls2.size();
			if(size2 > 0){
				list.addAll(getData(code,ftp,ls2));
			}
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取TAG游戏数据列表
	 * @param agent 代理帐号
	 * @param ftp ftp服务器对象
	 * @param ls 文件列表
	 * @return 数据列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getData(String agent, FtpUtils ftp,List<String> ls) {
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();			
			int size = ls.size();
			if(size > 0){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				String filePath = null;
				String str = null;
				for(int i = 0; i < size; i++){
					filePath = ls.get(i);
					str = ftp.readFile(filePath);
					if(str == null){
						continue;
					}
					String xmlf = "<?xml version='1.0' encoding='UTF-8'?><result>" + str + "</result>";
					Document doc = DocumentHelper.parseText(xmlf); // 将字符串转为XML
					Element rootElt = doc.getRootElement(); // 获取根节点
					Iterator<Element> iter = rootElt.elementIterator("row"); // 获取根节点下的子节点head
					Map<String, Object> info = null;
					String dataType = null;
					Element itemEle = null;
					String bettime = null;
					double cost = 0;
					double Earn = 0;
					while (iter.hasNext()) {
						info = new HashMap<String, Object>();
						itemEle = iter.next();
						dataType = itemEle.attributeValue("dataType");
						if ("HSR".equals(dataType)) {
							info.put("billNo", itemEle.attributeValue("ID"));
							info.put("agentCode", itemEle.attributeValue("tradeNo"));
							info.put("gameCode", itemEle.attributeValue("gameCode"));
							info.put("playerName", itemEle.attributeValue("playerName"));
							cost = GameHttpUtil.StringToDouble(itemEle.attributeValue("Cost"));
							Earn = GameHttpUtil.StringToDouble(itemEle.attributeValue("Earn"));
							info.put("netAmount", Earn - cost);
							bettime = itemEle.attributeValue("SceneStartTime");
							if (!"".equals(GameHttpUtil.StringToSpace(bettime))) {
								Calendar cal = Calendar.getInstance();
								cal.setTime(df.parse(bettime));
								cal.add(Calendar.HOUR, 12);
								bettime = df.format(cal.getTime());
							}
							info.put("betTime", bettime);
							info.put("gameType", itemEle.attributeValue("platformType"));
							info.put("betAmount", Earn);
							info.put("validBetAmount", Earn);
							info.put("flag", itemEle.attributeValue("flag"));
							info.put("playType", itemEle.attributeValue("platformType"));
							info.put("currency", itemEle.attributeValue("currency"));
							info.put("tableCode", itemEle.attributeValue("Roomid"));
							info.put("loginIP", itemEle.attributeValue("IP"));
							info.put("recalcuTime", null);
							info.put("platformId", null);
							info.put("platformType", itemEle.attributeValue("platformType"));
							info.put("stringex", null);
							info.put("remark", itemEle.attributeValue("previousAmount"));
							info.put("round", itemEle.attributeValue("Roombet"));
							info.put("result", itemEle.attributeValue("transferAmount"));
							info.put("beforeCredit", GameHttpUtil.StringToDouble(itemEle.attributeValue("previousAmount")));
							info.put("deviceType", null);
							info.put("betAmountBase", null);
							info.put("betAmountBonus", null);
							info.put("netAmountBase", null);
							info.put("netAmountBonus", null);
							info.put("slottype", null);
							info.put("mainbillno", null);
							info.put("createtime", df.format(new Date()));
							info.put("Platformflag", agent);
						} else {
							info.put("billNo", itemEle.attributeValue("billNo"));
							info.put("playerName", itemEle.attributeValue("playerName"));
							info.put("agentCode", itemEle.attributeValue("agentCode"));
							info.put("gameType", itemEle.attributeValue("gameType"));
							info.put("gameCode", itemEle.attributeValue("gameCode"));
							info.put("netAmount", GameHttpUtil.StringToDouble(itemEle.attributeValue("netAmount")));
							bettime = itemEle.attributeValue("betTime");
							if (!"".equals(GameHttpUtil.StringToSpace(bettime))) {
								Calendar cal = Calendar.getInstance();
								cal.setTime(df.parse(itemEle.attributeValue("betTime")));
								cal.add(Calendar.HOUR, 12);
								bettime = df.format(cal.getTime());
							}
							info.put("betTime", bettime);
							info.put("betAmount", GameHttpUtil.StringToDouble(itemEle.attributeValue("betAmount")));
							info.put("validBetAmount", GameHttpUtil.StringToDouble(itemEle.attributeValue("validBetAmount")));
							info.put("flag", itemEle.attributeValue("flag"));
							info.put("playType", itemEle.attributeValue("playType"));
							info.put("currency", itemEle.attributeValue("currency"));
							info.put("tableCode", itemEle.attributeValue("tableCode"));
							info.put("loginIP", itemEle.attributeValue("loginIP"));
							info.put("recalcuTime", itemEle.attributeValue("recalcuTime"));
							info.put("platformId", itemEle.attributeValue("platformId"));
							info.put("platformType", itemEle.attributeValue("platformType"));
							info.put("stringex", itemEle.attributeValue("stringex"));
							info.put("remark", itemEle.attributeValue("remark"));
							info.put("round", itemEle.attributeValue("round"));
							info.put("result", itemEle.attributeValue("result"));
							info.put("beforeCredit", GameHttpUtil.StringToDouble(itemEle.attributeValue("beforeCredit")));
							info.put("deviceType", itemEle.attributeValue("deviceType"));
							info.put("betAmountBase", GameHttpUtil.StringToDouble(itemEle.attributeValue("betAmountBase")));
							info.put("betAmountBonus", GameHttpUtil.StringToDouble(itemEle.attributeValue("betAmountBonus")));
							info.put("netAmountBase", GameHttpUtil.StringToDouble(itemEle.attributeValue("netAmountBase")));
							info.put("netAmountBonus", GameHttpUtil.StringToDouble(itemEle.attributeValue("netAmountBonus")));
							info.put("slottype", itemEle.attributeValue("slottype"));
							info.put("mainbillno", itemEle.attributeValue("mainbillno"));
							info.put("createtime", df.format(new Date()));
							info.put("Platformflag", agent);
						}
						if(info.get("billNo")!=null){
							list.add(info);
						}
					}
				}			
			}
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取数据库拉取的最大日期
	 * @param max 最大日期
	 * @return 最大日期
	 */
	private static String getMaxDate(String max){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		if(max==null){//如果获取的最大日期为空，刚设置成当前日期减少12个小时			
			Calendar sysCal = Calendar.getInstance();
			sysCal.add(Calendar.HOUR, -12);
			int min = sysCal.get(Calendar.MINUTE);
			if(min % 2 == 0){
				if(min > 6){
					sysCal.add(Calendar.MINUTE, -6);
				}else{
					sysCal.add(Calendar.MINUTE, 00);
				}
				max = sdf.format(sysCal.getTime())+".xml";
			}else{
				if(min > 6){
					sysCal.add(Calendar.MINUTE, -7);
					max = sdf.format(sysCal.getTime())+".xml";
				}else{
					sysCal.add(Calendar.MINUTE, 00);
					max = sdf.format(sysCal.getTime())+".xml";
				}
			}
		}
		return max;
	}
	
	public static void main(String[] a){
//		FtpUtils ftp = new FtpUtils("xml.agingames.com",21,"F67.zhenlong","SBCYARGSXW","/");
//		String max = "201611100000.xml";
//		List<Map<String, Object>> list = getTAGData("xml.agingames.com","XIN",max,ftp);
//		System.out.println(list.size());
		FtpUtils ftp = new FtpUtils("xml.agingames.com",21,"F67.zhenlong","SBCYARGSXW","/");
		String max = "201612200000.xml";
		List<Map<String, Object>> list = getData("xml.agingames.com","AGIN",max,ftp,"");
		System.out.println(list.size());
		System.out.println(getMaxDate(null));
	}
}
