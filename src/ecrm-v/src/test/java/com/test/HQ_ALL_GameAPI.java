/*package com.tans.core.game.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.tans.core.entity.MemberGameInfo;
import com.tans.core.entity.view.OperateParas;
import com.tans.core.game.GameAPI;
import com.tans.core.game.api.error.HQAllMgsEnums;
import com.tans.core.util.DeEnCode;
import com.tans.core.util.HttpUtil;
import com.tans.core.util.MD5Encrypt;
import com.tans.core.util.StringUtil;

import net.sf.json.JSONObject;

public class HQ_ALL_GameAPI implements GameAPI {
	private static final Logger logger = Logger.getLogger(HQ_ALL_GameAPI.class);
	private static final Integer FOR_NUMBER = 3;
	private static final String URL_WEB = "web";
	private static final String URL_INFO = "info";

	public Map<String, Object> play(OperateParas operateParas) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer paramBuff = new StringBuffer();
		String sendurl = new String();
		paramBuff.append("method=play");
		paramBuff.append("!username=").append(operateParas.getGameAccount());
		paramBuff.append("!password=").append(operateParas.getGamePassword());
//		logger.info("参数信息： " + paramBuff.toString());
		sendurl = this.composeUrl(paramBuff.toString(), operateParas, URL_WEB);
//		logger.info("URL 加密结果： " + sendurl);
		String res = HttpUtil.get(sendurl.toString());
		if (StringUtils.isNotBlank(res) && res.indexOf("code") == -1) {
			map.put(HQAllMgsEnums.SUCCESS.getCode(), res);
//			logger.info("获取GameURL成功： " + operateParas);
		} else {
			map.put(HQAllMgsEnums.FAIL.getCode(), res);
			logger.error("获取GameURL失败：" + res + " : " + operateParas);
		}
		return map;
	}

	public Map<String, Object> createPlayer(OperateParas operateParas) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		MemberGameInfo memberGameInfo = null;
		StringBuffer paramBuff = new StringBuffer();
		String sendurl = new String();
		paramBuff.append("method=createUser");
		paramBuff.append("!username=").append(operateParas.getGameAccount());
		paramBuff.append("!password=").append(operateParas.getGamePassword());
		paramBuff.append("!usercode=").append(operateParas.getMember().getLoginName());
		paramBuff.append("!parentcode=").append(operateParas.getMember().getLoginName());
		paramBuff.append("!usertype=agent");
		if("HQ_XCP".equals(operateParas.getGameItems().getItemCode())){
			paramBuff.append("!userpoint=5");
		}
		logger.info("参数信息： " + paramBuff.toString());
		sendurl = this.composeUrl(paramBuff.toString(), operateParas, URL_WEB);
		logger.info("URL 加密结果： " + sendurl);
		String res = HttpUtil.get(sendurl.toString());
		JSONObject obj = JSONObject.fromObject(res);
		if (HQAllMgsEnums.SUCCESS.getCode().equals(obj.get("code"))) {
			memberGameInfo = new MemberGameInfo();
			memberGameInfo.setMemberId(operateParas.getMember().getId());
			memberGameInfo.setGameAccount(operateParas.getGameAccount());
			memberGameInfo.setGamePassword(operateParas.getGamePassword());
			memberGameInfo.setSysDicItems(operateParas.getGameItems());
			memberGameInfo.setGameApiName(operateParas.getGameApiName());
			memberGameInfo.setMoneyStatus(false);
			map.put(HQAllMgsEnums.SUCCESS.getCode(), memberGameInfo);
			logger.info("创建成功： " + operateParas);
		} else if (HQAllMgsEnums.USERNAME_EXIST_ERROR.getCode().equals(obj.get("code"))) {
			operateParas.setGameAccount(operateParas.getGameAccount() + StringUtil.getChar(2));
			if (operateParas.getCount() < FOR_NUMBER) {
				operateParas.setCount(operateParas.getCount() + 1);
				return this.createPlayer(operateParas);
			} else {
				map.put(HQAllMgsEnums.FAIL.getCode(), HQAllMgsEnums.FAIL.getValue());
				logger.error("创建失败：" + res + " : " + operateParas);
			}
		} else {
			String errorMsg = HQAllMgsEnums.getByCode(obj.get("code").toString());
			if (StringUtils.isBlank(errorMsg)) {
				errorMsg = obj.get("info").toString();
			}
			map.put(HQAllMgsEnums.FAIL.getCode(), errorMsg);
			logger.error("创建失败：" + res + " : " + operateParas);
		}
		return map;
	}

	public Map<String, Object> getBalance(OperateParas operateParas) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Double balance = 0D;
		StringBuffer paramBuff = new StringBuffer();
		String sendurl = new String();
		paramBuff.append("method=balance");
		paramBuff.append("!username=").append(operateParas.getGameAccount());
		paramBuff.append("!password=").append(operateParas.getGamePassword());
//		logger.info("参数信息： " + paramBuff.toString());
		sendurl = this.composeUrl(paramBuff.toString(), operateParas, URL_WEB);//"web"  "info"
//		logger.info("URL 加密结果： " + sendurl);
		String res = HttpUtil.get(sendurl.toString());
		JSONObject obj = JSONObject.fromObject(res);
		if (HQAllMgsEnums.SUCCESS.getCode().equals(obj.get("code"))) {
			balance = obj.getDouble("info");
			map.put(HQAllMgsEnums.SUCCESS.getCode(), balance);
//			logger.info("查询成功： " + balance + " : " + operateParas);
		} else if (HQAllMgsEnums.USER_NOT_EXIST_ERROR.getCode().equals(obj.get("code"))) {
			map.put(HQAllMgsEnums.USER_NOT_EXIST_ERROR.getCode(), HQAllMgsEnums.USER_NOT_EXIST_ERROR.getValue());
			logger.error("查询失败：" + res + " : " + operateParas);
		} else if (!HQAllMgsEnums.FAIL.getCode().equals(obj.get("code"))) {
			String errorMsg = HQAllMgsEnums.getByCode(obj.get("code").toString());
			if (StringUtils.isBlank(errorMsg)) {
				errorMsg = obj.get("info").toString();
			}
			map.put(HQAllMgsEnums.FAIL.getCode(), errorMsg);
			logger.error("查询失败：" + res + " : " + operateParas);
		}
		return map;
	}

	public Map<String, Object> depositPlayer(OperateParas operateParas) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer paramBuff = new StringBuffer();
		String sendurl = new String();
		paramBuff.append("method=deposit");
		paramBuff.append("!ordernum=").append(operateParas.getOrderId());
		paramBuff.append("!username=").append(operateParas.getGameAccount());
		paramBuff.append("!password=").append(operateParas.getGamePassword());
		paramBuff.append("!money=").append((int) operateParas.getMoney());
		logger.info("参数信息： " + paramBuff.toString());
		sendurl = this.composeUrl(paramBuff.toString(), operateParas, URL_WEB);
		logger.info("URL 加密结果： " + sendurl);
		String res = HttpUtil.get(sendurl.toString());
		JSONObject obj = JSONObject.fromObject(res);
		if (HQAllMgsEnums.SUCCESS.getCode().equals(obj.get("code"))) {
			map.put(HQAllMgsEnums.SUCCESS.getCode(), HQAllMgsEnums.SUCCESS.getValue());
			logger.info("上分成功： " + obj + " : " + operateParas);
		} else {
			String errorMsg = HQAllMgsEnums.getByCode(obj.get("code").toString());
			if (StringUtils.isBlank(errorMsg)) {
				errorMsg = obj.get("info").toString();
			}
			map.put(HQAllMgsEnums.FAIL.getCode(), errorMsg);
			logger.error("上分失败：" + res + " : " + operateParas);
		}
		return map;
	}

	public Map<String, Object> withdrawPlayer(OperateParas operateParas) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer paramBuff = new StringBuffer();
		String sendurl = new String();
		paramBuff.append("method=withdraw");
		paramBuff.append("!ordernum=").append(operateParas.getOrderId());
		paramBuff.append("!username=").append(operateParas.getGameAccount());
		paramBuff.append("!password=").append(operateParas.getGamePassword());
		paramBuff.append("!money=").append((int) operateParas.getMoney());
		logger.info("参数信息： " + paramBuff.toString());
		sendurl = this.composeUrl(paramBuff.toString(), operateParas, "web");
		logger.info("URL 加密结果： " + sendurl);
		String res = HttpUtil.get(sendurl.toString());
		JSONObject obj = JSONObject.fromObject(res);
		if (HQAllMgsEnums.SUCCESS.getCode().equals(obj.get("code"))) {
			map.put(HQAllMgsEnums.SUCCESS.getCode(), HQAllMgsEnums.SUCCESS.getValue());
			logger.info("下分成功： " + obj + " : " + operateParas);
		} else {
			String errorMsg = HQAllMgsEnums.getByCode(obj.get("code").toString());
			if (StringUtils.isBlank(errorMsg)) {
				errorMsg = obj.get("info").toString();
			}
			map.put(HQAllMgsEnums.FAIL.getCode(), errorMsg);
			logger.error("下分失败：" + res + " : " + operateParas);
		}
		return map;
	}

	public Map<String, Object> getData(OperateParas operateParas) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer paramBuff = new StringBuffer();
		String sendurl = new String();
		paramBuff.append("begintime=").append(operateParas.getBeginTime());
		paramBuff.append("!endtime=").append(operateParas.getEndTime());
		paramBuff.append("!pagesize=").append(operateParas.getPageSize());
		paramBuff.append("!page=").append((int) operateParas.getPageId());
//		logger.info("参数信息： " + paramBuff.toString());
		sendurl = this.composeUrl(paramBuff.toString(), operateParas, URL_INFO);
//		logger.info("URL 加密结果： " + sendurl);
		String res = HttpUtil.get(sendurl.toString());
		JSONObject obj = JSONObject.fromObject(res);
		if (Integer.valueOf(obj.get("total").toString()) >= 0) {
			map.put(HQAllMgsEnums.SUCCESS.getCode(), obj);
//			logger.info("获取记录成功： " + obj + " : " + operateParas);
		} else {
			String errorMsg = HQAllMgsEnums.getByCode(obj.get("code").toString());
			if (StringUtils.isBlank(errorMsg)) {
				errorMsg = obj.get("info").toString();
			}
			map.put(HQAllMgsEnums.FAIL.getCode(), errorMsg);
			logger.error("获取记录失败：" + res + " : " + operateParas);
		}
		return map;
	}

	private String composeUrl(String paramBuff, OperateParas operateParas, String urlType) {
		StringBuffer sendurl = new StringBuffer();
		String params = DeEnCode.encrypt(paramBuff, operateParas.getSetMap().get("DESKEY"));
		String key = MD5Encrypt.MD5Encode(paramBuff + operateParas.getSetMap().get("MD5KEY"));
		sendurl.append(operateParas.getSetMap().get("URL")).append(urlType);
		sendurl.append("?params=").append(params);
		sendurl.append("&key=").append(key);
		sendurl.append("&platform=").append(operateParas.getSetMap().get("PLATFORM"));
		sendurl.append("&type=").append(operateParas.getGameItems().getItemValue());
		return sendurl.toString();
	}

}*/