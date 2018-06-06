package com.maven.controller;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.PlatformApiOutput;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.TLogger;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;
import com.maven.util.Encrypt;
import com.maven.util.RandomString;
import com.maven.utils.StringUtil;

/**
 * @author tonny
 */
public abstract class BaseController {

	private static HttpServletRequest request;

	private static final String key1 = RandomString.createRandomString(8);

	private static final String key2 = RandomString.createRandomString(8);

	public abstract LoggerManager getLogger();

	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;

	// 获取国际化信息
	protected static String getMsg(HttpServletRequest request, String msgName) {
		RequestContext requestContext = new RequestContext(request);
		return requestContext.getMessage(msgName);
	}

	protected static HttpServletRequest getRequest() {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 获取项目根目录
	 * 
	 * @return
	 */
	protected static String getBasePath() {
		HttpServletRequest request = getRequest();
		StringBuffer fullurl = new StringBuffer();
		try {
			fullurl = request.getRequestURL();
			fullurl.delete(fullurl.indexOf(request.getRequestURI()), fullurl.length());
			fullurl.append(request.getContextPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fullurl.toString();
	}

	/**
	 * 加密字符串
	 * 
	 * @param value
	 * @param sessionID
	 * @return
	 */
	protected String encryptString(String value, String sessionID) {
		return Encrypt.MD5(value + key1 + sessionID + key2) + "_" + value;
	}

	/**
	 * 加密集合对象的多个字段
	 * 
	 * @param list
	 *            返回数据
	 * @param primaryKey
	 *            主键字段名
	 */
	protected void encryptSign(List<?> list, String sessionID, String... ss) {
		for (Object o : list) {
			try {
				for (String s : ss) {
					s = s.substring(0, 1).toUpperCase() + s.substring(1);
					Method method = o.getClass().getMethod("get" + s);
					String value = (String) method.invoke(o);
					Method method_sign = o.getClass().getMethod("set" + s, String.class);
					method_sign.invoke(o, Encrypt.MD5(value + key1 + sessionID + key2) + "_" + value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 加密集合对象的多个字段 (可映射不同字段)
	 * 
	 * @param list
	 *            返回数据
	 * @param primaryKey
	 *            主键字段名
	 */
	protected void encryptSignTarget(List<?> list, String sessionID, Map<String, String> ss) {
		for (Object o : list) {
			try {
				for (String s : ss.keySet()) {
					String smethod = s.substring(0, 1).toUpperCase() + s.substring(1);
					Method method = o.getClass().getMethod("get" + smethod);
					String value = String.valueOf(method.invoke(o));
					String tmethod = ss.get(s);
					tmethod = tmethod.substring(0, 1).toUpperCase() + tmethod.substring(1);
					Method method_sign = o.getClass().getMethod("set" + tmethod, String.class);
					method_sign.invoke(o, Encrypt.MD5(value + key1 + sessionID + key2) + "_" + value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param o
	 *            加密单个对象多个字段
	 * @param sessionID
	 * @param ss
	 */
	protected void encryptSingleSignTarget(Object o, String sessionID, Map<String, String> ss) {
		try {
			for (String s : ss.keySet()) {
				String smethod = s.substring(0, 1).toUpperCase() + s.substring(1);
				Method method = o.getClass().getMethod("get" + smethod);
				String value = String.valueOf(method.invoke(o));
				String tmethod = ss.get(s);
				tmethod = tmethod.substring(0, 1).toUpperCase() + tmethod.substring(1);
				Method method_sign = o.getClass().getMethod("set" + tmethod, String.class);
				method_sign.invoke(o, Encrypt.MD5(value + key1 + sessionID + key2) + "_" + value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解密加密字段
	 * 
	 * @param sign
	 *            加密的唯一标志
	 * @param primaryKey
	 *            主键值
	 * @return boolean值，返回true可以继续执行
	 */
	protected boolean decodeSign(String sign, String sessionID) {
		if (StringUtils.isBlank(sign))
			return false;
		String[] ss = sign.split("_");
		if (ss.length != 2)
			return false;
		return Encrypt.MD5(ss[1] + key1 + sessionID + key2).equals(ss[0]);
	}

	/**
	 * 解密加密字段
	 * 
	 * @param sign
	 *            加密的唯一标志
	 * @param primaryKey
	 *            主键值
	 * @return boolean值，返回true可以继续执行
	 */
	@SuppressWarnings("unused")
	protected boolean decodeSign(String[] sign, String sessionID) {
		for (int j = 0; j < sign.length; j++) {
			if (StringUtils.isBlank(sign[j]))
				return false;
			String[] temp = sign[j].split("_");
			if (temp.length != 2)
				return false;
			return Encrypt.MD5(temp[1] + key1 + sessionID + key2).equals(temp[0]);
		}
		return false;
	}

	/**
	 * 将传入参数封装成Map
	 * 
	 * @param request
	 * @return
	 */
	protected Map<String, Object> getRequestParamters(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		Enumeration<?> obj = request.getParameterNames();
		while (obj.hasMoreElements()) {
			String key = (String) obj.nextElement();
			String value = request.getParameter(key);
			if (StringUtils.isNotBlank(value)) {
				params.put(key, value.trim());
			}
		}
//		System.out.println(StringUtil.getStryyyyMMddHHmmss() + "=API请求参数：" + params);

		/***************
		 * 打印接口请求头信息****************** Enumeration<String> headerNames =
		 * request.getHeaderNames(); while (headerNames.hasMoreElements()) {
		 * 
		 * String headerName = headerNames.nextElement(); String headerValues = "";
		 * Enumeration<String> headers = request.getHeaders(headerName); while
		 * (headers.hasMoreElements()) { String headerValue = headers.nextElement();
		 * headerValues = headerValues.concat(" ").concat(headerValue); }
		 * System.out.println("API请求头："+headerName+"="+headerValues); } 打印接口请求头信息
		 ******************/

		return params;
	}

	/**
	 * 将request，以数据流方式读取，返回字符串
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	protected String getRequestString(HttpServletRequest request) throws IOException {
		// 通过数据流方式直接读取
		InputStreamReader isr = new InputStreamReader(request.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		String temp;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		isr.close();
		return sb.toString();
	}

	/**
	 * 获取参数
	 * 
	 * @param request
	 * @param cls
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 * @throws IntrospectionException
	 */
	protected <T> T getRequestParamters(HttpServletRequest request, Class<T> cls)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ParseException, IntrospectionException {
		Map<String, Object> object = this.getRequestParamters(request);
		return BeanToMapUtil.convertMap(object, cls);
	}

	/**
	 * 返回分页数据 Map格式
	 * 
	 * @param rows
	 * @param rowCount
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> formatPagaMap(List<?> rows, int rowCount) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", rows);
		data.put("results", rowCount);
		return data;
	}

	protected Map<String, Object> packJSON(int status, Object message) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("status", status);
		json.put("message", message);
		return json;
	}

	/**
	 * API接口解密
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> apiDecode(Map<String, Object> object) throws Exception {
		AttrCheckout.checkout(object, true, new String[] { "enterprisecode", "signature", "params" });
		String enterprisecode = String.valueOf(object.get("enterprisecode"));
		PlatformApiOutput key = SystemCache.getInstance().getPlatformApiOutput(enterprisecode);
		if (key == null) {
			throw new ArgumentValidationException(Enum_MSG.企业API许可为空.desc);
		}
//		System.out.println("AES-->" + key.getApikey1() + "****MD5--->" + key.getApikey2());
		String encryptParams = String.valueOf(object.get("params"));
		String params = Encrypt.AESDecrypt(encryptParams, key.getApikey1(), false);
		String signature = String.valueOf(object.get("signature"));
		String p = params + key.getApikey2();
		String signatureconfirm = Encrypt.MD5(p);
		if (!signature.equals(signatureconfirm)) {
			throw new ArgumentValidationException(Enum_MSG.解密失败.desc);
		}
		TLogger.getLogger().Debug("解密参数:" + params);
		String[] ss = params.split("&");
		Map<String, Object> decode = new HashMap<String, Object>();
		for (String s : ss) {
			String[] sl = s.split("=");
			if (sl.length == 2) {
				decode.put(sl[0], sl[1]);
			} else {
				decode.put(sl[0], null);
			}
		}
		decode.put("enterprisecode", enterprisecode);
		return decode;
	}

	public static void main(String[] args) {
		try {
			/*
			 * {signature=6ad98487c9846ecea83ce55653aceb40, enterprisecode=EN003A,
			 * params=uAqCMSUjWjSbx13C4c5KABBgn6j3gZ192Mzz8wOlcRcgmWg6tEMfOCYVugJfjYUd4g6+
			 * mWoX6e5jVDW2zrIJrQ==}
			 */
			String aesKey = "7Tz0fh0LJYHBp8qR";
			String md5Key = "Mm1WabXtjIgfUUPB";

			/**** 加密 ****/
			String params = "phoneno=13631223451&ip=192.168.1.1";
			Map<String, String> returnObject = new HashMap<String, String>();
			returnObject.put("params", Encrypt.AESEncrypt(params, "QGXbUntu1XZI7L9K"));
			returnObject.put("signature", Encrypt.MD5(params + "Lzs75DJsDGucGj1S"));
			System.out.println(returnObject);

			// http://api.hyzonghe.net/User/getVerifycode?signature=510ddd55f8efcbe7cad881a076ea0cf2&params=uvJ52%2FV9S8yAFmuhVjaMmLKr%2BIvg%2FjQ8CGRa4F2Ge0HjrImtiGQ9k4HAfVm9EHlc&enterprisecode=EN003A
			// {signature=2fe2c069c769fcfce76e58e8c1e2dd77,
			// params=FvuSlvAAraC1ED2k7T12UuSyvGagI9IITcPB%2FluDbS%2Fp%2BUkWawcmOIxLGrYDy9Wk}

			/**** 解密 ****/
			String encryptParams = "uAqCMSUjWjSbx13C4c5KABBgn6j3gZ192Mzz8wOlcRcgmWg6tEMfOCYVugJfjYUd4g6+mWoX6e5jVDW2zrIJrQ==";
			params = Encrypt.AESDecrypt(encryptParams, aesKey, false);
			String signature = "6ad98487c9846ecea83ce55653aceb40";
			String p = params + md5Key;
			String signatureconfirm = Encrypt.MD5(p);
			if (!signature.equals(signatureconfirm)) {
				throw new ArgumentValidationException(Enum_MSG.解密失败.desc);
			}
			String[] ss = params.split("&");
			Map<String, Object> decode = new HashMap<String, Object>();
			for (String s : ss) {
				String[] sl = s.split("=");
				if (sl.length == 2) {
					decode.put(sl[0], sl[1]);
				} else {
					decode.put(sl[0], null);
				}
			}
			System.out.println(decode);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 固定通信解密
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> fixedCommunicationDecode(String md5Key, String aesKey, Map<String, Object> object)
			throws Exception {
		AttrCheckout.checkout(object, true, new String[] { "signature", "params" });
		String encryptParams = String.valueOf(object.get("params"));
		String params = Encrypt.AESDecrypt(encryptParams, aesKey, false);
		String signature = String.valueOf(object.get("signature"));
		String p = params + md5Key;
		String signatureconfirm = Encrypt.MD5(p);
		if (!signature.equals(signatureconfirm)) {
			throw new ArgumentValidationException(Enum_MSG.解密失败.desc);
		}
		String[] ss = params.split("&");
		Map<String, Object> decode = new HashMap<String, Object>();
		for (String s : ss) {
			String[] sl = s.split("=");
			if (sl.length == 2) {
				decode.put(sl[0], sl[1]);
			} else {
				decode.put(sl[0], null);
			}
		}
		return decode;
	}

	/**
	 * 用户身份验证
	 * 
	 * @param ee
	 * @return
	 */
	protected EnterpriseEmployee menberAuthentication(EnterpriseEmployee ee) {
		if (ee == null)
			throw new LogicTransactionRollBackException(Enum_MSG.账号或密码错误.desc);
		if (!ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value))
			throw new LogicTransactionRollBackException(Enum_MSG.非会员用户.desc);
		return ee;
	}

	/**
	 * 用户信息验证
	 * 
	 * @param ee
	 * @return
	 */
	protected EnterpriseEmployee agentAuthentication(EnterpriseEmployee ee) {
		if (ee == null)
			throw new LogicTransactionRollBackException(Enum_MSG.用户不存在.desc);
		if (ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value))
			throw new LogicTransactionRollBackException(Enum_MSG.非代理用户.desc);
		return ee;
	}

	/**
	 * 用户数据权限控制(用户，用户银行卡，游戏数据，存取款订单)
	 * 
	 * @param ee
	 * @param params
	 *            Map对象或者单实体对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected void dataLimits(EnterpriseEmployee ee, Object params, HttpSession session) throws Exception {
		String employeecodes = "";
		Map<String, Object> object = (Map<String, Object>) params;
		if (ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)) {
			String __shareholdercode = (String) session.getAttribute(Constant.USER_SHAREHOLDER);
			if (__shareholdercode == null) {
				__shareholdercode = enterpriseEmployeeService.call_UfnTakeShareholder(ee.getParentemployeecode());
				session.setAttribute(Constant.USER_SHAREHOLDER, __shareholdercode);
			}
			employeecodes = enterpriseEmployeeService.call_ufnTakeTeamAgent(__shareholdercode);
		} else {
			employeecodes = enterpriseEmployeeService.call_ufnTakeTeamAgent(ee.getEmployeecode());
			object.put("teamLeaderCode", ee.getEmployeecode());
		}
		if (params instanceof Map) {
			object.put("teamCodes", employeecodes.split(","));
		} else {
			Method methodSet = params.getClass().getDeclaredMethod("setTeamCodes", new Class[] { String.class });
			methodSet.invoke(params, new Object[] { employeecodes.split(",") });
			Method methodSet2 = params.getClass().getDeclaredMethod("teamLeaderCode", new Class[] { String.class });
			methodSet2.invoke(params, new Object[] { employeecodes.split(",") });
		}
	}

	/**
	 * 用户密码加密
	 * 
	 * @param str
	 * @return String
	 */
	public String password_MD5(String str) {
		StringBuffer md5StrBuff = new StringBuffer();
		try {
			MessageDigest messageDigest = null;
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
			byte[] byteArray = messageDigest.digest();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
				else
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
			return md5StrBuff.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return md5StrBuff.toString();
	}
}
