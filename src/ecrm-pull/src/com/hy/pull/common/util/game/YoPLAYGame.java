package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.FtpUtils;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

/**
 * 拉取Yoplay游戏数据工具类
 * 
 * 该游戏与AG极度类似
 * 
 * 创建日期：2016-10-11
 * @author temdy
 */
public class YoPLAYGame {	
	
	private static Logger logger = LogManager.getLogger(YoPLAYGame.class.getName());
	/**
	 * 获取TAG游戏数据列表
	 * @param agent 代理帐号
	 * @param dir 数据文件目录
	 * @param max 上次拉取最大值
	 * @param ftp ftp服务器对象
	 * @param code 代理编码
	 * @return 数据列表
	 */
	public static List<Map<String, Object>> getTAGData(String agent, String dir, String max, FtpUtils ftp,String code) {
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			max = getMaxDate(max);
			//System.out.println(max);
//			System.out.println(agent+"==========================="+dir);
			String p1 = "/".concat(dir).concat("/").concat(max.substring(0, 8)).concat("/");//游戏数据路径
			String p2 = "/".concat(dir).concat("/lostAndfound/").concat(max.substring(0, 8)).concat("/");//游戏补单数据路径
			//System.out.println(p1);
			//System.out.println(p2);
			
			
			//第1步拉取正常数据
			if(ftp.ftpClient.changeWorkingDirectory(p1) ) {
				
//				System.out.println(agent+"===========================p1changeWorkingDirectory="+p1);
				List<String> ls1 = ftp.getFileList(p1);
//				System.out.println(agent+"===========================p1getFileList");
				//System.out.println(ls1);
				int size1 = ls1.size();
				if(size1 > 0){
					int index = ls1.indexOf(max);
					max = ls1.get(size1-1);//获取最大值
					ftp.setMax(max);
					if(index > 0){
						ls1 = ls1.subList(index, size1);//重新获取文件列表
					}
//					System.out.println(agent+"===========================p1getTAGData-1");
					List list2 = getTAGData(code,ftp,ls1);
					if(list2 == null) {
						return null;
					}
					list.addAll(list2);
//					System.out.println(agent+"===========================p1getTAGData-2");
				}
				
			}
			
			/********/
			//第2步拉取补单数据
			if(ftp.ftpClient.changeWorkingDirectory(p2) ) {
				
//				System.out.println(agent+"===========================p2changeWorkingDirectory="+p2);
				List<String> ls2 = ftp.getFileList(p2);
//				System.out.println(agent+"===========================p2getFileList");
				int size2 = ls2.size();
				if(size2 > 0){
//					System.out.println(agent+"===========================p2getTAGData-1");
					List list2 = getTAGData(code,ftp,ls2);
					if(list2 == null) {
						return null;
					}
					list.addAll(list2);
//					System.out.println(agent+"===========================p2getTAGData-2");
				}
				
			}
			
			
			return list;
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_YoPLAY, dir, ex.getMessage(), agent, Enum_flag.异常.value);
			return null;
		}
	}
	
	/**
	 * 获取TAG游戏数据列表
	 * @param agent 代理帐号
	 * @param ftp ftp服务器对象
	 * @param ls 文件列表
	 * @return 数据列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getTAGData(String agent, FtpUtils ftp,List<String> ls) {
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
						
						if ( !"BR".equals(dataType)) {//投注记录
							continue;
						}
						
						info.put("billNo", itemEle.attributeValue("billNo"));
						info.put("playerName", itemEle.attributeValue("playerName"));
						info.put("agentCode", itemEle.attributeValue("agentCode"));
						info.put("gameCode", itemEle.attributeValue("gameCode"));
						info.put("gameType", itemEle.attributeValue("gameType"));
						info.put("netAmount", itemEle.attributeValue("netAmount"));
						bettime = itemEle.attributeValue("betTime");
						if (!"".equals(GameHttpUtil.StringToSpace(bettime))) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(df.parse(itemEle.attributeValue("betTime")));
							cal.add(Calendar.HOUR, 12);
							bettime = df.format(cal.getTime());
						}
						info.put("betTime", bettime);
						info.put("betAmount", itemEle.attributeValue("betAmount"));
						info.put("validBetAmount", itemEle.attributeValue("validBetAmount"));
						info.put("flag", itemEle.attributeValue("flag"));
						info.put("playType", itemEle.attributeValue("playType"));
						info.put("currency", itemEle.attributeValue("currency"));
						info.put("tableCode", itemEle.attributeValue("tableCode"));
						info.put("loginIP", itemEle.attributeValue("loginIP"));
						info.put("recalcuTime", itemEle.attributeValue("recalcuTime"));
						info.put("platformType", itemEle.attributeValue("platformType"));
						info.put("remark", itemEle.attributeValue("remark"));
						info.put("round", itemEle.attributeValue("round"));
						info.put("slottype", itemEle.attributeValue("slottype"));
						info.put("result", itemEle.attributeValue("result"));
						info.put("mainbillno", itemEle.attributeValue("mainbillno"));
						info.put("beforeCredit", itemEle.attributeValue("beforeCredit"));
						info.put("deviceType", itemEle.attributeValue("deviceType"));
						info.put("betAmountBase", itemEle.attributeValue("betAmountBase"));
						info.put("betAmountBonus", itemEle.attributeValue("betAmountBonus"));
						info.put("netAmountBase", itemEle.attributeValue("netAmountBase"));
						info.put("netAmountBonus", itemEle.attributeValue("netAmountBonus"));
						
						
						info.put("createtime", df.format(new Date()));
						info.put("betmoney", info.get("betAmount"));
						info.put("netmoney", info.get("netAmount"));
						info.put("validmoney", info.get("validBetAmount"));
						
						list.add(info);
						
					}
				}			
			}
			return list;
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_YoPLAY, agent, ex.getMessage(), agent, Enum_flag.异常.value);
			return null;
		}
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
		List<Map<String, Object>> list = getTAGData("xml.agingames.com","AGIN",max,ftp,"");
		System.out.println(list.size());
		System.out.println(getMaxDate(null));
	}
}
