package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.SSLClient;
import com.hy.pull.common.util.game.ttg.TTGUtil;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

/**
 * 拉取TTG游戏数据工具类（时间间隔最长6小时）
 * 创建日期：2016-12-12
 * @author temdy
 */
public class TTGGame {
	private static Logger logger = LogManager.getLogger(TTGGame.class.getName());
	/**
	 * 获取TTG游戏数据
	 * @param apiUrl 接口URL
	 * @param agent 代理帐号
	 * @param param 参数列表
	 * @param code 代理编码
	 * @return 数据列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getData(String apiUrl,String agent,Map<String,Object> param,String code){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {	
			String xmlData = getXMLString(param);
			String result = GameHttpUtil.sendXMLDataByPost(apiUrl, xmlData);
//			System.out.println("TTG的xmlData="+xmlData);
//			System.out.println("TTG的result="+result);
			
			if(result != null){
				Document xmlDoc = DocumentHelper.parseText(result);
				Element root = xmlDoc.getRootElement();
				String requestId = root.attributeValue("requestId");			
				String totalRecords = root.attributeValue("totalRecords") == null ? "0" : root.attributeValue("totalRecords");
				int total = Integer.parseInt(totalRecords);
				if(total > 0){
					List<Element> es = root.element("details").elements("transaction");
					Element transaction = null;
					Element player = null;
					Element detail = null;
					Map<String, Object> entity = null;
					String transactionType = param.get("transactionType").toString();
					int size = es.size();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
					for(int i=0;i<size;i++){
						transaction = es.get(i);
						player = transaction.element("player");
						detail = transaction.element("detail");
						entity = new HashMap<String, Object>();
						entity.put("requestId",requestId);
						entity.put("partnerId",player.attributeValue("partnerId"));
						entity.put("playerId",player.attributeValue("playerId"));
						entity.put("amount",detail.attributeValue("amount"));
						entity.put("handId",detail.attributeValue("handId"));
						entity.put("transactionType",transactionType);
						entity.put("transactionSubType",detail.attributeValue("transactionSubType"));
						entity.put("currency",detail.attributeValue("currency"));
						entity.put("game",detail.attributeValue("game"));
						entity.put("transactionDate",detail.attributeValue("transactionDate"));
						entity.put("transactionId",detail.attributeValue("transactionId"));
						entity.put("createtime", sdf.format(new Date()));
						entity.put("Platformflag", code);
						list.add(entity);
					}
				}
			} else {
				return null;
			}
		}catch(Exception ex){
			logger.error(ex);
			ex.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_TTG, apiUrl, ex.getMessage(), agent, Enum_flag.异常.value);
			return null;
		}
		return list;
	}
	
	
	/**
	 * 获取请求xml格式数据
	 * @param param 参数列表
	 * startDate 开始日期[YYYYMMDD]
	 * startDateHour 开始小时
	 * startDateMinute 开始分钟
	 * startDate 开始日期[YYYYMMDD]
	 * endDate 结束日期[YYYYMMDD]
	 * endtDateHour 开始小时
	 * endDateMinute 开始分钟
	 * currency 货币类型
	 * partnerId 合作ID
	 * includeSubPartner 是否包含子合作ID
	 * transactionType 交易类型[transaction type name](1. Game 2. MoneyTransfer 3. ManualAdjustment 4. Bonus)
	 * transactionSubType 子交易类型[transaction subtype name]
	    1. Game
			a. Wager
			b. Resolve
		2. MoneyTransfer
			a. ChipTransfer
			b. Visa
			c. MasterCard
			d. WithdrawalRequest
			e. WithdrawalRejection
			f. DepositRejection
			g. BrokerTransfer
		3. ManualAdjustment
			a. GameAdjustment
			b. Cash
			c. Cheque
			d. Wire
		4. Bonus
			a. Bonus
			b. BonusAdjustment
			c. BonusReversed
	 * gameName 游戏名称[TTG game name]
	 * gameSuite 游戏房间名称[TTG game suite name]
	 * gameDeviceType 游戏设备类型名称[TTG game device type name]
	 * gameProfile [TTG game profile name]
	 * @return
	 */
	public static String getXMLString(Map<String,Object> param){
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 设置日期格式
		sb.append("<searchdetail requestId=\""+sdf.format(new Date())+"\">");//yyyyMMddHHmmssSSS流水号
		sb.append("	<daterange startDate=\""+param.get("startDate").toString()+"\" startDateHour=\""+param.get("startDateHour").toString()+"\" startDateMinute=\""+param.get("startDateMinute").toString()+"\" endDate=\""+param.get("endDate").toString()+"\" endDateHour=\""+param.get("endDateHour").toString()+"\" endDateMinute=\""+param.get("endDateMinute").toString()+"\" />");
		sb.append(" <account currency=\""+param.get("currency").toString()+"\" />");
		sb.append(" <partner partnerId=\""+param.get("partnerId").toString()+"\" includeSubPartner=\""+param.get("includeSubPartner").toString()+"\" />");
		sb.append("	<transaction transactionType=\""+param.get("transactionType").toString()+"\" transactionSubType=\""+param.get("transactionSubType").toString()+"\" />");		
		sb.append("</searchdetail>");
		return sb.toString();
	}

	public static void main(String[] args) throws DocumentException {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("currency", "CNY");
		entity.put("startDate", "20170109");
		entity.put("startDateHour", "13");
		entity.put("startDateMinute", "45");
		entity.put("endDate", "20170109");
		entity.put("endDateHour", "14");
		entity.put("endDateMinute", "45");
		entity.put("partnerId", "HYHTTG");
		entity.put("includeSubPartner", "Y");
		entity.put("transactionType", "Game");
		entity.put("transactionSubType", "Wager");
		String apiUrl = "https://ams8-api.ttms.co:8443/dataservice/datafeed/transaction/current";
		String agent = "HYHTTG";
		
		
		System.out.println(getXMLString(entity));
		//调用测试
		System.out.println(getData(apiUrl,agent,entity,""));
	}
}