package com.hy.pull.common.util.api;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;

import com.hy.pull.common.util.MapUtil;
import com.maven.util.RandomString;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JDB168GameAPI implements BaseInterface {
	
	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:00");
		System.out.println(dateFormat.format(new Date()));
		String API_URL = "http://api.jdb1688.net/apiRequest.do"; 
		String PARENT  = "xbs";
		String IV  = "4yscetx5gnsuuavk";
		String KEY = "w73nxygercy9qdju";
		String SITE = "HQ";
		String DC = "HQ";
		
		JDB168GameAPI api = new JDB168GameAPI(API_URL, API_URL, PARENT, DC, SITE, IV, KEY);
		
		String uid = RandomString.createRandomString(19);
		String name = RandomString.createRandomString(16);
		
//		String tryId = "try";
		String dipositNo = "TEST".concat(RandomString.UUID().substring(12));
		String withdraNo = "TEST".concat(RandomString.UUID().substring(12));
		
		String starttime = "27-10-2017 09:50:00";
		String endtime = "27-10-2017 10:00:00";
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("uid", uid);
		entity.put("name", name);
		entity.put("serialNo", dipositNo);
		entity.put("amount", 20);
//		entity.put("starttime", starttime);
//		entity.put("endtime", endtime);
		
		String result = null;
		result = (String) api.createAccount(entity);
		System.out.println("请求结果：" + result);
		
		result = (String) api.deposit(entity);
		System.out.println("请求结果：" + result);
		
		result = (String) api.login(entity);
		System.out.println("请求结果：" + result);
		
//		String result = (String) api.getRecord(entity);
//		System.out.println("请求结果：" + result);
	}
	
	private String baseAPIUrl;
	private String gameAPIUrl;
	private String parent;
	private String dc;
	private String site;
	private String iv;
	private String key;
	
	public JDB168GameAPI(String baseAPIUrl, String gameAPIUrl, String parent, String dc, String site, String iv, String key) {
		this.baseAPIUrl = baseAPIUrl;
		this.gameAPIUrl = gameAPIUrl;
		this.parent = parent;
		this.dc = dc;
		this.site = site;
		this.iv = iv;
		this.key = key;
	}

	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString());
		try {
			if (MapUtil.isNulls(entity, "uid,name")) {
				entity.put("action", 12);
				entity.put("ts", System.currentTimeMillis());
				entity.put("parent", parent);
				
//				JSONObject result = encryptAndGet(baseAPIUrl, entity, false);
				JSONObject result = encryptAndPost(baseAPIUrl, entity, false);
				if (result != null) {
					if (result.containsKey("status") && result.getString("status").equals("0000")) {
						return Enum_MSG.成功.message("玩家创建成功!");
					} else {
						return Enum_MSG.失败.message(result);
					}
				} else {
					return Enum_MSG.接口调用错误.message("请求失败, 请稍后再试!");
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}

	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString());
		try {
			if (MapUtil.isNull(entity, "uid")) {
				entity.put("action", 15);
				entity.put("ts", System.currentTimeMillis());
				entity.put("parent", parent);
				
				JSONObject result = encryptAndPost(baseAPIUrl, entity, false);
				if (result != null) {
					if (result.containsKey("status") && result.getString("status").equals("0000")
							&& result.getJSONArray("data") != null) {
						JSONArray data = result.getJSONArray("data");
						JSONObject info = data.getJSONObject(0);
						Double balance = info.getDouble("balance");
						return Enum_MSG.成功.message(balance.toString());
					} else {
						return Enum_MSG.失败.message(result);
					}
				} else {
					return Enum_MSG.接口调用错误.message("请求失败, 请稍后再试!");
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}

	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString());
		try {
			if (MapUtil.isNulls(entity, "uid,serialNo,amount")) {
				int amount = Integer.valueOf(entity.get("amount").toString());
				if (amount > 0) amount = -(amount);
				entity.put("action", 19);
				entity.put("ts", System.currentTimeMillis());
				entity.put("parent", parent);
				entity.put("amount", amount);
				
				JSONObject result = encryptAndPost(baseAPIUrl, entity, false);
				if (result != null) {
					if (result.containsKey("status") && result.getString("status").equals("0000")) {
						return Enum_MSG.成功.message("下分成功!");
					} else {
						return Enum_MSG.失败.message(result);
					}
				} else {
					return Enum_MSG.接口调用错误.message("请求失败, 请稍后再试!");
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}

	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString());
		try {
			if (MapUtil.isNulls(entity, "uid,serialNo,amount")) {
				int amount = Integer.valueOf(entity.get("amount").toString());
				if (amount < 0) amount = -(amount);
				entity.put("action", 19);
				entity.put("ts", System.currentTimeMillis());
				entity.put("parent", parent);
				entity.put("amount", amount);
				
				JSONObject result = encryptAndPost(baseAPIUrl, entity, false);
				if (result != null) {
					if (result.containsKey("status") && result.getString("status").equals("0000")) {
						return Enum_MSG.成功.message("上分成功!");
					} else {
						return Enum_MSG.失败.message(result);
					}
				} else {
					return Enum_MSG.接口调用错误.message("请求失败, 请稍后再试!");
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object getRecord(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getRecord", entity.toString());
		try {
			if (MapUtil.isNulls(entity, "starttime,endtime")) {
				entity.put("action", 29);
				entity.put("ts", System.currentTimeMillis());
				entity.put("parent", parent);
				
				JSONObject result = encryptAndPost(baseAPIUrl, entity, false);
				if (result != null) {
					if (result.containsKey("status") && result.getString("status").equals("0000")
							&& result.getJSONArray("data") != null) {
						JSONArray data = result.getJSONArray("data");
						List<Map<String, Object>> returnData = new ArrayList<Map<String,Object>>();
						if (data.size() > 0) {
							for (int i = 0; i < data.size(); i++) {
								JSONObject json = data.getJSONObject(i);
								Map<String, Object> map = new HashMap<String, Object>();
								Iterator<String> iterator = json.keys();
								while(iterator.hasNext()) {
									String key = iterator.next();
									if (json.get(key) != null) {
										map.put(key, json.get(key));
									}
								}
								returnData.add(map);
							}
						}
						return Enum_MSG.成功.message(returnData);
					} else {
						return Enum_MSG.失败.message(result);
					}
				} else {
					return Enum_MSG.接口调用错误.message("请求失败, 请稍后再试!");
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}

	@Override
	public Object updateInfo(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString());
		try {
			if (MapUtil.isNulls(entity, "uid,name")) {
				entity.put("action", 13);
				entity.put("ts", System.currentTimeMillis());
				entity.put("parent", parent);
				
				JSONObject result = encryptAndPost(baseAPIUrl, entity, false);
				if (result != null) {
					if (result.containsKey("status") && result.getString("status").equals("0000")) {
						return Enum_MSG.成功.message("玩家更新成功!");
					} else {
						return Enum_MSG.失败.message(result);
					}
				} else {
					return Enum_MSG.接口调用错误.message("请求失败, 请稍后再试!");
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString());
		try {
			if (MapUtil.isNull(entity, "serialNo")) {
				entity.put("action", 28);
				entity.put("ts", System.currentTimeMillis());
				entity.put("parent", parent);
				
				JSONObject result = encryptAndPost(baseAPIUrl, entity, false);
				if (result != null) {
					if (result.containsKey("status") && result.getString("status").equals("0000")) {
						JSONArray data = result.getJSONArray("data");
						List<Map<String, Object>> returnData = new ArrayList<Map<String,Object>>();
						if (data.size() > 0) {
							for (int i = 0; i < data.size(); i++) {
								JSONObject json = data.getJSONObject(i);
								Map<String, Object> map = new HashMap<String, Object>();
								Iterator<String> iterator = json.keys();
								while(iterator.hasNext()) {
									String key = iterator.next();
									if (json.get(key) != null) {
										map.put(key, json.get(key));
									}
								}
								returnData.add(map);
							}
						}
						return Enum_MSG.成功.message(returnData);
					} else {
						return Enum_MSG.失败.message(result);
					}
				} else {
					return Enum_MSG.接口调用错误.message("请求失败, 请稍后再试!");
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}

	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString());
		try {
			if (MapUtil.isNull(entity, "uid")) {
				if (entity.get("uid").equals("try")) {
					entity.put("action", 47);
				} else {
					entity.put("action", 11);
				}
				entity.put("ts", System.currentTimeMillis());
				if (entity.get("lang") == null) entity.put("lang", "ch");
				
				JSONObject result = encryptAndPost(baseAPIUrl, entity, false);
				if (result != null) {
					if (result.containsKey("status") && result.getString("status").equals("0000")) {
						String path = result.getString("path");
						return Enum_MSG.成功.message(path);
					} else {
						return Enum_MSG.失败.message(result);
					}
				} else {
					return Enum_MSG.接口调用错误.message("请求失败, 请稍后再试!");
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}

	@Override
	public Object isOnLine(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "isOnLine", entity.toString());
		try {
			if (MapUtil.isNull(entity, "uid")) {
				String uid = entity.get("uid").toString();
				entity.clear();
				entity.put("action", 16);
				entity.put("ts", System.currentTimeMillis());
				
				JSONObject result = encryptAndPost(baseAPIUrl, entity, false);
				if (result != null) {
					if (result.containsKey("status") && result.getString("status").equals("0000")) {
						JSONArray data = result.getJSONArray("data");
						if (data != null && data.size() > 0) {
							for (int i = 0; i < data.size(); i++) {
								JSONObject online = data.getJSONObject(i);
								if (uid.equals(online.getString("uid")))
									return Enum_MSG.成功.message(true);
							}
						}
						return Enum_MSG.成功.message(false);
					} else {
						return Enum_MSG.失败.message(result);
					}
				} else {
					return Enum_MSG.接口调用错误.message("请求失败, 请稍后再试!");
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}
	/**
	 * 加密并POST提交
	 * @param v
	 * @param zipResponse
	 * @return
	 * @throws Exception
	 */
	private JSONObject encryptAndPost(String url, Map<String, Object> params, boolean zipResponse) throws Exception {
		NameValuePair[] parametersBody = new NameValuePair[2];
		parametersBody[0] = new NameValuePair("dc", dc);
		if (params != null && params.size() > 0) {
			JSONObject jsonData = JSONObject.fromObject(params);
			String x = encrypt(jsonData.toString());
			parametersBody[1] = new NameValuePair("x", x);
		}
		
		System.out.println("请求地址："+url);
		System.out.println("请求参数："+params);
		PostMethod post = new PostMethod(url);
		post.setRequestBody(parametersBody);
		post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);
		
		if (zipResponse) post.setRequestHeader("Accept-Encoding", "gzip");
		
		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(post);
		InputStream is = post.getResponseBodyAsStream();
		String result = IOUtils.toString(is);
		System.out.println("请求结果：" + result);
		JSONObject jsonResult = JSONObject.fromObject(result);
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Access Error: " + statusCode);
			System.err.println("Error Message: " + jsonResult);
		}
		return jsonResult;
	}
	
	/**
	 * AES 加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private String encrypt(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		int blockSize = cipher.getBlockSize();
		byte[] dataBytes = data.getBytes("UTF-8");
		int plainTextLength = dataBytes.length;
		if (plainTextLength % blockSize != 0) {
			plainTextLength = plainTextLength + (blockSize - plainTextLength % blockSize);
		}
		byte[] plaintext = new byte[plainTextLength];
		System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
		SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
		byte[] encrypted = cipher.doFinal(plaintext);
		return Base64.encodeBase64URLSafeString(encrypted);
	}
	
	private static final String CHARSET = "UTF-8";
}