package com.maven.payment.mb;

import java.io.ByteArrayOutputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import sun.net.www.protocol.http.HttpURLConnection;

public class Mobo360Merchant {

	private Mobo360Merchant() {

	}

	/**
	 * 发送请求到支付网关并接受回复
	 * 
	 * @param paramsStr
	 *            请求参数字符串
	 * @param serverUrl
	 *            支付网关地址
	 * @return
	 * @throws Exception
	 */
	public static String transact(String paramsStr, String serverUrl)
			throws Exception {

		if (StringUtils.isBlank(serverUrl) || StringUtils.isBlank(paramsStr)) {
			throw new NullPointerException("请求地址或请求数据为空!");
		}

		myX509TrustManager xtm = new myX509TrustManager();
		myHostnameVerifier hnv = new myHostnameVerifier();
		ByteArrayOutputStream respData = new ByteArrayOutputStream();

		byte[] b = new byte[8192];
		String result = "";
		try {
			SSLContext sslContext = null;
			try {
				sslContext = SSLContext.getInstance("TLS");
				X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
				sslContext.init(null, xtmArray,
						new java.security.SecureRandom());
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}

			if (sslContext != null) {
				HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
						.getSocketFactory());
			}
			HttpsURLConnection.setDefaultHostnameVerifier(hnv);

			// 匹配http或者https请求
			URLConnection conn = null;
			if (serverUrl.toLowerCase().startsWith("https")) {
				HttpsURLConnection httpsUrlConn = (HttpsURLConnection) (new URL(
						serverUrl)).openConnection();
				httpsUrlConn.setRequestMethod("POST");
				conn = httpsUrlConn;
			} else {
				HttpURLConnection httpUrlConn = (HttpURLConnection) (new URL(
						serverUrl)).openConnection();
				httpUrlConn.setRequestMethod("POST");
				conn = httpUrlConn;
			}

			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn
					.setRequestProperty(
							"User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.1) Gecko/20061204 Firefox/2.0.0.3");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("connection", "close");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.getOutputStream().write(paramsStr.getBytes("utf-8"));
			conn.getOutputStream().flush();

			int len = 0;
			try {
				while (true) {
					len = conn.getInputStream().read(b);
					if (len <= 0) {
						conn.getInputStream().close();
						break;
					}
					respData.write(b, 0, len);
				}
			} catch (SocketTimeoutException ee) {
				throw new RuntimeException("读取响应数据出错！" + ee.getMessage());
			}

			// 返回给商户的数据
			result = respData.toString("utf-8");
			if (StringUtils.isBlank(result)) {
				throw new RuntimeException("返回参数错误！");
			}
		} catch (Exception e) {
			throw new RuntimeException("发送POST请求出现异常！" + e.getMessage());
		}

		// 验签返回数据后返回支付平台回复数据
		checkResult(result);
		return result;
	}

	/**
	 * 如果数据被篡改 则抛出异常
	 * 
	 * @param result
	 * @throws Exception
	 */
	private static void checkResult(String result) throws Exception {

		if (StringUtils.isBlank(result)) {
			throw new NullPointerException("返回数据为空!");
		}

		try {
			Document resultDOM = DocumentHelper.parseText(result);
			Element root = resultDOM.getRootElement();
			String responseData = root.element("respData").asXML();
			String signMsg = root.element("signMsg").getStringValue();

			if (StringUtils.isBlank(responseData)
					|| StringUtils.isBlank(signMsg)) {
				throw new Exception("解析返回验签或原数据错误！");
			}

			if (!Mobo360SignUtil.verifyData(signMsg, responseData)) {
				throw new Exception("系统效验返回数据失败！");
			}

		} catch (DocumentException e) {
			throw new RuntimeException("xml解析错误：" + e);
		}
	}

	/**
	 * 将由支付请求参数构成的map转换成支付串，并对参数做合法验证
	 * 
	 * @param paramsMap
	 *            由支付请求参数构成的map
	 * @return
	 * @throws Exception
	 */
	public static String generatePayRequest(Map<String, String> paramsMap)
			throws Exception {

		// 验证输入数据合法性
		if (!paramsMap.containsKey("apiName")
				|| StringUtils.isBlank(paramsMap.get("apiName"))) {
			throw new Exception("apiName不能为空");
		}
		if (!paramsMap.containsKey("apiVersion")
				|| StringUtils.isBlank(paramsMap.get("apiVersion"))) {
			throw new Exception("apiVersion不能为空");
		}
		if (!paramsMap.containsKey("platformID")
				|| StringUtils.isBlank(paramsMap.get("platformID"))) {
			throw new Exception("platformID不能为空");
		}
		if (!paramsMap.containsKey("merchNo") || StringUtils.isBlank("merchNo")) {
			throw new Exception("merchNo不能为空");
		}
		if (!paramsMap.containsKey("orderNo") || StringUtils.isBlank("orderNo")) {
			throw new Exception("orderNo不能为空");
		}
		if (!paramsMap.containsKey("tradeDate")
				|| StringUtils.isBlank("tradeDate")) {
			throw new Exception("tradeDate不能为空");
		}
		if (!paramsMap.containsKey("amt") || StringUtils.isBlank("amt")) {
			throw new Exception("amt不能为空");
		}
		if (!paramsMap.containsKey("merchUrl")
				|| StringUtils.isBlank("merchUrl")) {
			throw new Exception("merchUrl不能为空");
		}
		if (!paramsMap.containsKey("merchParam")) {
			throw new Exception("merchParam可以为空，但必须存在！");
		}
		if (!paramsMap.containsKey("tradeSummary")
				|| StringUtils.isBlank("tradeSummary")) {
			throw new Exception("tradeSummary不能为空");
		}

		// 输入数据组织成字符串
		String paramsStr = String
				.format(
						"apiName=%s&apiVersion=%s&platformID=%s&merchNo=%s&orderNo=%s&tradeDate=%s&amt=%s&merchUrl=%s&merchParam=%s&tradeSummary=%s",
						paramsMap.get("apiName"), paramsMap.get("apiVersion"),
						paramsMap.get("platformID"), paramsMap.get("merchNo"),
						paramsMap.get("orderNo"), paramsMap.get("tradeDate"),
						paramsMap.get("amt"), paramsMap.get("merchUrl"),
						paramsMap.get("merchParam"), paramsMap
								.get("tradeSummary"));

		return paramsStr;
	}

	/**
	 * 将由查询请求参数组成的map组织成字符串，并对参数做合法性验证
	 * 
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public static String generateQueryRequest(Map<String, String> paramsMap)
			throws Exception {
		if (!paramsMap.containsKey("apiName")
				|| StringUtils.isBlank(paramsMap.get("apiName"))) {
			throw new Exception("apiName不能为空");
		}
		if (!paramsMap.containsKey("apiVersion")
				|| StringUtils.isBlank(paramsMap.get("apiVersion"))) {
			throw new Exception("apiVersion不能为空");
		}
		if (!paramsMap.containsKey("platformID")
				|| StringUtils.isBlank(paramsMap.get("platformID"))) {
			throw new Exception("platformID不能为空");
		}
		if (!paramsMap.containsKey("merchNo")
				|| StringUtils.isBlank(paramsMap.get("merchNo"))) {
			throw new Exception("merchNo不能为空");
		}
		if (!paramsMap.containsKey("orderNo")
				|| StringUtils.isBlank(paramsMap.get("orderNo"))) {
			throw new Exception("orderNo不能为空");
		}
		if (!paramsMap.containsKey("tradeDate")
				|| StringUtils.isBlank(paramsMap.get("tradeDate"))) {
			throw new Exception("tradeDate不能为空");
		}
		if (!paramsMap.containsKey("amt")
				|| StringUtils.isBlank(paramsMap.get("amt"))) {
			throw new Exception("amt不能为空");
		}

		String resultStr = String
				.format(
						"apiName=%s&apiVersion=%s&platformID=%s&merchNo=%s&orderNo=%s&tradeDate=%s&amt=%s",
						paramsMap.get("apiName"), paramsMap.get("apiVersion"),
						paramsMap.get("platformID"), paramsMap.get("merchNo"),
						paramsMap.get("orderNo"), paramsMap.get("tradeDate"),
						paramsMap.get("amt"));

		return resultStr;
	}

	public static String generateRefundRequest(Map<String, String> paramsMap)
			throws Exception {
		if (!paramsMap.containsKey("apiName")
				|| StringUtils.isBlank(paramsMap.get("apiName"))) {
			throw new Exception("apiName不能为空");
		}
		if (!paramsMap.containsKey("apiVersion")
				|| StringUtils.isBlank(paramsMap.get("apiVersion"))) {
			throw new Exception("apiVersion不能为空");
		}
		if (!paramsMap.containsKey("platformID")
				|| StringUtils.isBlank(paramsMap.get("platformID"))) {
			throw new Exception("platformID不能为空");
		}
		if (!paramsMap.containsKey("merchNo")
				|| StringUtils.isBlank(paramsMap.get("merchNo"))) {
			throw new Exception("merchNo不能为空");
		}
		if (!paramsMap.containsKey("orderNo")
				|| StringUtils.isBlank(paramsMap.get("orderNo"))) {
			throw new Exception("orderNo不能为空");
		}
		if (!paramsMap.containsKey("tradeDate")
				|| StringUtils.isBlank(paramsMap.get("tradeDate"))) {
			throw new Exception("tradeDate不能为空");
		}
		if (!paramsMap.containsKey("amt")
				|| StringUtils.isBlank(paramsMap.get("amt"))) {
			throw new Exception("amt不能为空");
		}
		if (!paramsMap.containsKey("tradeSummary")
				|| StringUtils.isBlank(paramsMap.get("tradeSummary"))) {
			throw new Exception("tradeSummary不能为空");
		}

		String resultStr = String
				.format(
						"apiName=%s&apiVersion=%s&platformID=%s&merchNo=%s&orderNo=%s&tradeDate=%s&amt=%s&tradeSummary=%s",
						paramsMap.get("apiName"), paramsMap.get("apiVersion"),
						paramsMap.get("platformID"), paramsMap.get("merchNo"),
						paramsMap.get("orderNo"), paramsMap.get("tradeDate"),
						paramsMap.get("amt"), paramsMap.get("tradeSummary"));
		return resultStr;
	}

}

class myX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType) {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}

class myHostnameVerifier implements HostnameVerifier {

	public boolean verify(String hostname, SSLSession session) {
		return true;
	}

}