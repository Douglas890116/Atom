package com.hy.pull.common.util.game;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QWPGame {
	private static Logger logger = LogManager.getLogger(QWPGame.class.getName());

	private String apiUrl;
	private String agentName;
	private String authorization;

	public QWPGame(String apiUrl, String agentName, String agentPassword) {
		this.apiUrl = apiUrl;
		this.agentName = agentName;
		this.authorization = getAuthorization(apiUrl.concat("/token"), agentName, agentPassword, "api");
	}

	public List<Map<String, Object>> getRecord(String startTime, String endTime, int currentPage, int pageSize) {
		try {
			StringBuffer url = new StringBuffer(this.apiUrl);
			url.append("/api/tickets/game-record");
			url.append("?startTime=").append(URLEncoder.encode(startTime, "UTF-8"));
			url.append("&endTime=").append(URLEncoder.encode(endTime, "UTF-8"));
			url.append("&currentPage=").append(currentPage);
			url.append("&pageSize=").append(pageSize);
			
			System.out.println("请求URL="+url);

			String result = doGetSubmit(url.toString());
			System.out.println("请求result="+result);
			
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

			if (StringUtils.isNotBlank(result)) {
				JSONObject jsonObject = JSONObject.fromObject(result);
				// 数据总量
				int totalCount = jsonObject.getInt("Total");
				System.out.println("请求totalCount="+totalCount);
				
				if (jsonObject != null && jsonObject.getJSONArray("Rows") != null) {
					JSONArray dataList = jsonObject.getJSONArray("Rows");
					Map<String, Object> resultMap = null;
					JSONObject data = null;
					for (int i = 0; i < dataList.size(); i++) {
						data = dataList.getJSONObject(i);
						resultMap = new HashMap<String, Object>();
						resultMap.put("Account", data.get("Account"));
						resultMap.put("KindId", data.get("KindId"));
						resultMap.put("ServerId", data.get("ServerId"));// 没有
						resultMap.put("RoomName", data.get("RoomName"));
						resultMap.put("StartTime", data.get("StartTime"));
						resultMap.put("EndTime", data.get("EndTime"));
						resultMap.put("RecordTime", data.get("RecordTime"));
						resultMap.put("Score", data.get("Score"));
						resultMap.put("TurnScore", data.get("TurnScore"));
						resultMap.put("Revenue", data.get("Revenue"));
						resultMap.put("TurnNumber", data.get("TurnNumber"));
						resultMap.put("CardData", data.get("CardData"));
						resultList.add(resultMap);
					}
				}
//				int totalPage = totalCount / pageSize;
//				if (totalCount % pageSize > 0) totalPage++;
//				//如果总页数大于当前页, 则进行递归查询下一页的数据
//				if (totalPage > currentPage) {
//					List<Map<String, Object>> extraList = getRecord(startTime, endTime, ++currentPage, pageSize);
//					if (extraList != null && extraList.size() > 0) resultList.addAll(extraList);
//				}
				return resultList;
			} else {
				LogUtil.addListLog(LogUtil.HANDLE_QWP, url.toString(), result, agentName, Enum_flag.异常.value);
			}
		} catch (HttpException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发起GET请求
	 * 
	 * @param url
	 * @param params
	 * @param contentType
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	private String doGetSubmit(String url) throws HttpException, IOException {
		GetMethod get = new GetMethod(url);
		get.setRequestHeader("Content-Type", "application/json");
		get.setRequestHeader("Authorization", this.authorization);
		get.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000 * 10);
		get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);

		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(get);
		if (statusCode != HttpStatus.SC_OK)
			return "request fail : " + statusCode;
		int contentLength = (int) get.getResponseContentLength();
		byte[] buffer = new byte[contentLength];
		for (int i = 0; i < contentLength;) {
			int readLength = get.getResponseBodyAsStream().read(buffer, i, contentLength - i);
			if (readLength == -1)
				break;
			i += readLength;
		}
		return new String(buffer, CHARSET);
	}

	/**
	 * 获取接口令牌
	 * 
	 * @return
	 */
	private String getAuthorization(String apiUrl, String name, String password, String usertype) {
		try {
			NameValuePair[] parametersBody = new NameValuePair[4];
			parametersBody[0] = new NameValuePair("username", name);
			parametersBody[1] = new NameValuePair("password", password);
			parametersBody[2] = new NameValuePair("userType", usertype);
			parametersBody[3] = new NameValuePair("grant_type", "password");

			PostMethod post = new PostMethod(apiUrl);
			post.setRequestBody(parametersBody);
			post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000 * 10);
			post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);

			HttpClient httpClient = new HttpClient();
			int statusCode = httpClient.executeMethod(post);
			if (statusCode != HttpStatus.SC_OK)
				return "status：" + statusCode;
			int contentLength = (int) post.getResponseContentLength();
			byte[] buffer = new byte[contentLength];
			for (int i = 0; i < contentLength;) {
				int readLength = post.getResponseBodyAsStream().read(buffer, i, contentLength - i);
				if (readLength == -1)
					break;
				i += readLength;
			}
			String result = new String(buffer, CHARSET);
			JSONObject jsonObject = JSONObject.fromObject(result);
			String tokenType = jsonObject.getString("token_type");
			String accessToken = jsonObject.getString("access_token");
			String authorization = tokenType + " " + accessToken;
			return authorization;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";
	}

	private static final String CHARSET = "UTF-8";
}