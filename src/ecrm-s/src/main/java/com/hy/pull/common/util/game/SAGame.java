package com.hy.pull.common.util.game;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.hy.pull.common.util.game.sa.MD5Encoder;

/**
 * 拉取沙龙游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class SAGame {

	/**
	 * 拉取沙龙游戏数据的方法
	 * @param apiUrl 请求接口URL
	 * @param agent 代理帐号
	 * @param key 用户KEY
	 * @param desKey 密钥字符串
	 * @param md5Key md5密钥
	 * @param stardate 开始日期
	 * @param enddate 结束日期
	 * @return 返回数据列表
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map<String,Object>> getSAData(String apiUrl,String agent,String key,String desKey,String md5Key, String stardate, String enddate) {
		try{			
			if(apiUrl==null){
				apiUrl = "http://api.sa-gaming.net/api/api.aspx";
			}		
			String method = "GetAllBetDetailsForTimeIntervalDV";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String Time = sdf.format(new Date());
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fromTime = GameHttpUtil.getDate();;
			if(stardate != null){
				fromTime = stardate;
			}
			String toTime = sdf.format(new Date());
			if(enddate != null){
				toTime = enddate;
			}
			String QS = "method="+method+"&Key="+key+"&Time="+Time+"&Checkkey="+agent+"&FromTime="+fromTime+"&ToTime="+toTime; 
			//System.out.println(QS);
			String q = DeEnCode.encrypt(QS, desKey);
			q=URLEncoder.encode(q,"GBK");
			String  a  = (QS+md5Key+Time+key);
			String s = MD5Encoder.encode(a);
			StringBuffer params = new StringBuffer();
			params.append("q=").append(q);
			params.append("&s=").append(s);
			//System.out.println(apiUrl+"?"+params.toString());
			// 调用接口
			String result = GameHttpUtil.sendPost(apiUrl+"?", params.toString());
			//System.out.println(result);
			if(result != null){
				Document doc = DocumentHelper.parseText(result); //将字符串转为XML
				Element rootElt = doc.getRootElement(); // 获取根节点
				String msg = rootElt.element("ErrorMsgId").getText();
				if ("0".equals(msg)) {
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					Element BetDetailList = rootElt.element("BetDetailList"); //获取根节点
					Iterator iter = BetDetailList.elementIterator("BetDetail"); //获取根节点下的子节点BetDetail
					Map<String, Object> entity = null;
					while (iter.hasNext()) {
						entity = new HashMap<String, Object>();
						Element itemEle = (Element) iter.next();
						entity.put("BetTime", itemEle.elementText("BetTime"));
						entity.put("PayoutTime", itemEle.elementText("PayoutTime"));
						entity.put("Username", itemEle.elementText("Username"));
						entity.put("HostID", itemEle.elementText("HostID"));
						entity.put("GameID", itemEle.elementText("GameID"));
						entity.put("Round", itemEle.elementText("Round"));
						entity.put("Set", itemEle.elementText("Set"));
						entity.put("BetID", itemEle.elementText("BetID"));
						entity.put("BetAmount", itemEle.elementText("BetAmount"));
						entity.put("ResultAmount", itemEle.elementText("ResultAmount"));
						entity.put("GameType", itemEle.elementText("GameType"));
						entity.put("BetType", itemEle.elementText("BetType"));
						entity.put("BetSource", itemEle.elementText("BetSource"));
						entity.put("State", itemEle.elementText("State"));
						entity.put("Detail", itemEle.elementText("Detail"));
						entity.put("createtime", sdf.format(new Date()));
						entity.put("Platformflag", agent);
						list.add(entity);
					}
					return list;
				} 
			}
		}catch(UnsupportedEncodingException ex){
			ex.printStackTrace();
		}catch(DocumentException ex){
			System.out.println("xml格式转换异常："+ ex);
		}		
		return null;
	}
	
	public static void main(String[] ag){
		System.out.println(getSAData("http://api.sa-gaming.net/api/api.aspx","hygjgame20168888","32C3DD24BF3A4EDDB59240CCBA7E3975","g9G16nTs","GgaIMaiNNtg", "2016-10-20 16:36:00", "2016-10-25 16:36:00"));
		//System.out.println(getSAData("http://api.eval.sa-gaming.net/api/api.aspx","hygjgame20168888","13CFB2D28F804318BE3BEBFE9887D992","g9G16nTs","GgaIMaiNNtg", "2016-10-20 16:36:00", "2016-10-25 16:36:00"));
	}
}
