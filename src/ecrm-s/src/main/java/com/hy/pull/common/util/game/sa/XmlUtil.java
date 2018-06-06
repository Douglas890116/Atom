package com.hy.pull.common.util.game.sa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * XML解析工具类
 * @author Administrator
 *
 */
public class XmlUtil {

	
	
	
	/**
	 * 解析AV老虎机游戏列表获取接口，返回list
	 * @param xmlStr
	 * @return
	 */
	public static List<Map<String, String>> getQueryParamsAVListGame(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		
		List<Map<String, String>> listGame = new ArrayList<Map<String,String>>();
		
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    

			Element root = doc.getRootElement();
			
			List<Element> list = root.selectNodes("//record");
			
			for (Element element : list) {
				Map<String, String> data = new HashMap<String, String>();
				Iterator<Element> iterator = element.elementIterator();
				while (iterator.hasNext()) {
					Element element2 = (Element) iterator.next();
					data.put(element2.getName(), element2.getStringValue());
				}
				listGame.add(data);
			}
			return listGame;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 针对AV老虎机的解析，返回多个record节点
	 * @param xmlStr
	 * @return
	 */
	public static List<Map<String, String>> getQueryParamsAVList(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    
			Element root = doc.getRootElement();
			
			Node totalCountNode = root.selectSingleNode("//BBTECH/response/pageInfo/totalCount");
//			System.out.println(totalCountNode.getStringValue());
			
			if(Integer.parseInt(totalCountNode.getStringValue()) > 0) {
				List<Element> list = root.selectNodes("//record");
				List<Map<String, String>> listData = new ArrayList<Map<String,String>>();
				
				
				for (Element element : list) {
					Map<String, String> data = new HashMap<String, String>();
					
					Iterator<Element> iterator = element.elementIterator();
					while (iterator.hasNext()) {
						Element element2 = (Element) iterator.next();
						data.put(element2.getName(), element2.getStringValue());
//						System.out.println(element2.getName() +"-"+ element2.getStringValue());
					}
					listData.add(data);
				}
				return listData;
			}
			return null;
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 针对AV老虎机的解析，直达最后一个节点
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, String> getQueryParamsAV(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    

			Element root = doc.getRootElement();
			
			List<Element> list = root.selectNodes("//record");
			Map<String, String> data = new HashMap<String, String>();
			for (Element element : list) {
				Iterator<Element> iterator = element.elementIterator();
				while (iterator.hasNext()) {
					Element element2 = (Element) iterator.next();
					data.put(element2.getName(), element2.getStringValue());
//					System.out.println(element2.getName() +"-"+ element2.getStringValue());
				}
				
			}
			return data;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 只有一个节点的情况
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, String> getQueryParamsRoot(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    

			//得到当前查询代码，即接口方法
			/**
			Node qryCode = doc.selectSingleNode("//ExInfo/standardInfo/Field[@FieldName='QRY_CODE']");
			System.out.println(qryCode.getStringValue());
			**/
			Element root = doc.getRootElement();
			
			Map<String, String> data = new HashMap<String, String>();
			data.put(root.getName(), root.getStringValue());
			return data;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 解析TAG返回结果
	 * 
	 * 示例：<result info="0" msg=""/>
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, String> getQueryParamsByTAG(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    

			//得到当前查询代码，即接口方法
			/**
			Node qryCode = doc.selectSingleNode("//ExInfo/standardInfo/Field[@FieldName='QRY_CODE']");
			System.out.println(qryCode.getStringValue());
			**/
			Element root = doc.getRootElement();
			
			List<Attribute> list = root.attributes();
			Map<String, String> data = new HashMap<String, String>();
			for (Attribute element2 : list) {
				data.put(element2.getName(), element2.getStringValue());
				//System.out.println(element2.getName() +"-"+ element2.getStringValue());
			}
			return data;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析TTG返回结果。单节点，多属性
	 * 
	 * 示例：<result info="0" msg=""/>
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, String> getQueryParamsByTTG(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    

			//得到当前查询代码，即接口方法
			/**
			Node qryCode = doc.selectSingleNode("//ExInfo/standardInfo/Field[@FieldName='QRY_CODE']");
			System.out.println(qryCode.getStringValue());
			**/
			Element root = doc.getRootElement();
			
			List<Attribute> list = root.attributes();
			Map<String, String> data = new HashMap<String, String>();
			for (Attribute element2 : list) {
				data.put(element2.getName(), element2.getStringValue());
				//System.out.println(element2.getName() +"-"+ element2.getStringValue());
			}
			return data;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解析TTG返回错误结果。单节点，多属性
	 * 
	 * 示例：<result info="0" msg=""/>
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, String> getQueryParamsByTTGerror1(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Map<String, String> data = new HashMap<String, String>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    

			//得到当前查询代码，即接口方法
			Element root = doc.getRootElement();
			List<Element> list2 = root.selectNodes("//error");
			
			for (Element element : list2) {
				
				List<Attribute> list = element.attributes();
				
				for (Attribute element2 : list) {
					data.put(element2.getName(), element2.getStringValue());
					//System.out.println(element2.getName() +"-"+ element2.getStringValue());
				}
				
				
			}
			return data;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解析TTG返回错误结果。单节点，多属性
	 * 
	 * 示例：<result info="0" msg=""/>
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, String> getQueryParamsByTTGerror(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Map<String, String> data = new HashMap<String, String>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    

			//得到当前查询代码，即接口方法
			Element root = doc.getRootElement();
			List<Element> list2 = root.selectNodes("//gametoken/error");
			
			for (Element element : list2) {
				
				List<Attribute> list = element.attributes();
				
				for (Attribute element2 : list) {
					data.put(element2.getName(), element2.getStringValue());
					//System.out.println(element2.getName() +"-"+ element2.getStringValue());
				}
				
				
			}
			return data;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 针对MG，获取用户余额的
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, String> getQueryParamsMGbalance(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    
			Element root = doc.getRootElement();
			
			List<Element> list2 = root.selectNodes("//mbrapi-account-resp/wallets/account-wallet");
			
			for (Element element : list2) {
				
				List<Attribute> list = element.attributes();
				
				for (Attribute element2 : list) {
					data.put(element2.getName(), element2.getStringValue());
					//System.out.println(element2.getName() +"-"+ element2.getStringValue());
				}
				
				
			}
			return data;
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解析MG返回结果。单节点，多属性
	 * 
	 * 示例：<result info="0" msg=""/>
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, String> getQueryParamsByMG(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    

			//得到当前查询代码，即接口方法
			/**
			Node qryCode = doc.selectSingleNode("//ExInfo/standardInfo/Field[@FieldName='QRY_CODE']");
			System.out.println(qryCode.getStringValue());
			**/
			Element root = doc.getRootElement();
			
			List<Attribute> list = root.attributes();
			Map<String, String> data = new HashMap<String, String>();
			for (Attribute element2 : list) {
				data.put(element2.getName(), element2.getStringValue());
				//System.out.println(element2.getName() +"-"+ element2.getStringValue());
			}
			return data;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, String> getQueryParams(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    

			//得到当前查询代码，即接口方法
			/**
			Node qryCode = doc.selectSingleNode("//ExInfo/standardInfo/Field[@FieldName='QRY_CODE']");
			System.out.println(qryCode.getStringValue());
			**/
			Element root = doc.getRootElement();
			
			List<Element> list = root.elements();
			Map<String, String> data = new HashMap<String, String>();
			for (Element element2 : list) {
				data.put(element2.getName(), element2.getStringValue());
				//System.out.println(element2.getName() +"-"+ element2.getStringValue());
			}
			return data;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getXMLMsg(String code,String info) {
		StringBuffer msg = new StringBuffer();
		msg.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		msg.append("<root>");
		msg.append("<response><errcode>"+code+"</errcode><errmsg>"+info+"</errmsg></response>");
		msg.append("</root>");
		return msg.toString();
	}
	
	public static String getXMLMsg(int code,String info) {
		StringBuffer msg = new StringBuffer();
		msg.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		msg.append("<root>");
		msg.append("<response><errcode>"+code+"</errcode><errmsg>"+info+"</errmsg></response>");
		msg.append("</root>");
		return msg.toString();
	}

}
