package com.hy.pull.common.util.game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.FtpUtil;
import com.hy.pull.common.util.ZipUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JDBGame {
	private static Logger logger = LogManager.getLogger(JDBGame.class.getName());

	private String apiUrl;
	private String parent;
	private String dc;
	private String key;
	private String iv;

	public JDBGame(){}
	
	public JDBGame(String apiUrl, String parent, String dc, String key, String iv) {
		this.apiUrl = apiUrl;
		this.parent = parent;
		this.key = key;
		this.iv = iv;
		this.dc = dc;
	}

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");// 设置日期格式

	public List<Map<String, Object>> getRecord(String startTime, String endTime) {
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			// 抓取时间段超过2小时则直接返回
			Date start = sdf.parse(startTime);
			long hours = (new Date().getTime() - start.getTime()) / (1000 * 60 * 60);
			if (hours > 2) return list;
			
			Map<String, Object> entity = new HashMap<String, Object>();
			entity.put("starttime", startTime);
			entity.put("endtime", endTime);
			entity.put("action", 29);
			entity.put("ts", System.currentTimeMillis());
			entity.put("parent", parent);

			JSONObject result = encryptAndPost(apiUrl, entity, false);

			System.out.println("JDB获取结果entity=" + entity);
			System.out.println("JDB获取结果result=" + result);

			if (result != null) {
				if (result.containsKey("status") && result.getString("status").equals("0000")
						&& result.getJSONArray("data") != null) {
					JSONArray data = result.getJSONArray("data");
					if (data != null && data.size() > 0) {
						Map<String, Object> map = null;
						JSONObject jsonObject = null;
						for (int i = 0; i < data.size(); i++) {
							jsonObject = data.getJSONObject(i);
							map = new HashMap<String, Object>();
							map.put("seqNo", jsonObject.get("seqNo"));
							map.put("playerId", jsonObject.get("playerId"));
							map.put("mtype", jsonObject.get("mtype"));
							map.put("gameDate", jsonObject.getString("gameDate"));
							map.put("bet", jsonObject.get("bet"));
							map.put("gambleBet", jsonObject.get("gambleBet"));
							map.put("win", jsonObject.get("win"));
							map.put("total", jsonObject.get("total"));
							map.put("currency", jsonObject.get("currency"));
							map.put("denom", jsonObject.get("denom"));
							map.put("lastModifyTime", jsonObject.getString("lastModifyTime"));
							map.put("playerIp", jsonObject.get("playerIp"));
							map.put("clientType", jsonObject.get("clientType"));
							map.put("gType", jsonObject.get("gType"));
							map.put("hasGamble", jsonObject.get("hasGamble"));
							map.put("hasBonusGame", jsonObject.get("hasBonusGame"));
							map.put("hasFreegame", jsonObject.get("hasFreegame"));
							map.put("roomType", jsonObject.get("roomType"));
							map.put("systemTakeWin", jsonObject.get("systemTakeWin"));
							map.put("jackpot", jsonObject.get("jackpot"));
							map.put("jackpotContribute", jsonObject.get("jackpotContribute"));
							map.put("beforeBalance", jsonObject.get("beforeBalance"));
							map.put("afterBalance", jsonObject.get("afterBalance"));

							// 以下是自定义字段
							map.put("createtime", new Date());
							map.put("bettime", sdf.parse(map.get("gameDate").toString()));
							map.put("betmoney", Math.abs(Double.valueOf(map.get("bet").toString())));//投注额是负数
							map.put("netmoney", map.get("total"));

							list.add(map);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析本地文件
	 * @param fileList
	 * @return
	 */
	public List<Map<String, Object>> fileAnalysis(List<String> fileList) {
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			
			String content = null;
			JSONArray jsonArray = null;
			JSONObject jsonObject = null;
			Map<String, Object> map = null;
			for (String file : fileList) {
				content = ZipUtil.readZipFile(file);
				if (StringUtils.isNotBlank(content) && !content.equals("[]")) {
					jsonArray = JSONArray.fromObject(content);
					for (int i = 0; i < jsonArray.size(); i++) {
						jsonObject = jsonArray.getJSONObject(i);
						map = new HashMap<String, Object>();
						map.put("seqNo", jsonObject.get("seqNo"));
						map.put("playerId", jsonObject.get("playerId"));
						map.put("mtype", jsonObject.get("mtype"));
						map.put("gameDate", jsonObject.getString("gameDate"));
						map.put("bet", jsonObject.get("bet"));
						map.put("gambleBet", jsonObject.get("gambleBet"));
						map.put("win", jsonObject.get("win"));
						map.put("total", jsonObject.get("total"));
						map.put("currency", jsonObject.get("currency"));
						map.put("denom", jsonObject.get("denom"));
						map.put("lastModifyTime", jsonObject.getString("lastModifyTime"));
						map.put("playerIp", jsonObject.get("playerIp"));
						map.put("clientType", jsonObject.get("clientType"));
						map.put("gType", jsonObject.get("gType"));
						map.put("hasGamble", jsonObject.get("hasGamble"));
						map.put("hasBonusGame", jsonObject.get("hasBonusGame"));
						map.put("hasFreegame", jsonObject.get("hasFreegame"));
						map.put("roomType", jsonObject.get("roomType"));
						map.put("systemTakeWin", jsonObject.get("systemTakeWin"));
						map.put("jackpot", jsonObject.get("jackpot"));
						map.put("jackpotContribute", jsonObject.get("jackpotContribute"));
						map.put("beforeBalance", jsonObject.get("beforeBalance"));
						map.put("afterBalance", jsonObject.get("afterBalance"));

						// 以下是自定义字段
						map.put("createtime", new Date());
						map.put("bettime", sdf.parse(map.get("gameDate").toString()));
						map.put("betmoney", map.get("bet"));
						map.put("netmoney", map.get("total"));

						list.add(map);
					}
				}
			}
			return list;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取ftp上的数据 每次下载一整天的文件
	 * 
	 * @return
	 */
	public List<Map<String, Object>> downloadFtpZips(String ip, int port, String ftp_name, String ftp_pswd, String folder, String localDir) {
		FtpUtil ftp = new FtpUtil(ip, port, ftp_name, ftp_pswd);
		try {
			if (ftp.open()) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = null;
				String mainFolder = localDir + "/" + folder;
				List<String> folders = ftp.getFolderList("/");
				List<String> files = null;
				for (String gameFolder : folders) {
					String localFolder = mainFolder + "/" + gameFolder;
					String ftpFolder = gameFolder + "/" + folder;

					files = ftp.getFileList(ftpFolder);
					if (files != null && files.size() > 0) {
						new File(localFolder).mkdirs();
						for (String file : files) {
							String ftpFile = ftpFolder + "/" + file;
							String localFile = localFolder + "/" + file;

							if (ftp.get(ftpFile, localFile)) {
								map = new HashMap<String, Object>();
								map.put("lsh", Encrypt.MD5(localFile));
								map.put("parent", ftp_name);
								map.put("date", folder);
								map.put("file", localFile);
								map.put("times", System.currentTimeMillis());
								map.put("status", 0);
								list.add(map);
							} else {
								logger.error("下载[" + ftpFile + "]至[" + localFile + "]!失!败!");
							}
						}
						System.out.println("下载["+ftpFolder+"]里的文件至["+localFolder+"]完成!");
					}

				}
				return list;
			} else {
				logger.error("FTP地址 " + ip +":" + port + "，" + ftp_name + "/" + ftp_pswd + "。【链接失败】");
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				ftp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 加密并POST提交
	 * 
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

		PostMethod post = new PostMethod(url);
		post.setRequestBody(parametersBody);
		post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);

		if (zipResponse)
			post.setRequestHeader("Accept-Encoding", "gzip");

		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(post);
		InputStream is = post.getResponseBodyAsStream();

		String result = IOUtils.toString(is, post.getResponseCharSet());
		JSONObject jsonResult = JSONObject.fromObject(result);
		if (statusCode != HttpStatus.SC_OK) {
			logger.error("Access Error: " + statusCode);
			logger.error("Error Message: " + jsonResult);
		}
		return jsonResult;
	}

	/**
	 * AES 加密
	 * 
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