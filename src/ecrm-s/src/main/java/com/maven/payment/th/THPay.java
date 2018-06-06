package com.maven.payment.th;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.th.frdemo.KeyValues;
import com.maven.payment.th.frdemo.ParamNames;
import com.maven.payment.th.utils.AppConstants.InputCharset;

public class THPay {

	 /**
     * 商户号
     */
    private  String merchantCode ;
    /**
     * 私钥key
     */
    private  String merchantKey ;
    /**
     * 接口URL
     */
    private String url;
    /**
     * 字符编码
     */
    private String inputCharset = InputCharset.UTF8;
    

	public THPay(String merchantCode,String merchantKey,String url){
    	this.merchantCode = merchantCode;
    	this.merchantKey = merchantKey;
    	this.url = url;
    }
	
	/**
	 * 设置字符编码
	 * @return
	 */
	 public String getInputCharset() {
		return inputCharset;
	}
	 
	 /**
	 * 获取字符编码
	 * @return
	 */
	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}
    
    /**
     * 
     * @param bankAccount 账户姓名
     * @param bankCardNo 银行卡号
     * @param amount 取款金额
     * @param bankCode 银行编码
     * @return
     * @throws Exception
     */
    public String remit(String bankAccount,String bankCardNo,String amount,String bankCode) throws Exception{
    	if(StringUtils.isEmpty(bankAccount)){
    		throw new RuntimeException("请求的用户姓名为空"); 
    	}
    	if(StringUtils.isEmpty(bankCardNo)){
    		throw new RuntimeException("请求的银行卡号为空");
    	}
    	if(StringUtils.isEmpty(amount)){
    		throw new RuntimeException("请求的取款金额为空");
    	}
        Map<String, String> params = new HashMap<String, String>();
        params.put(ParamNames.INPUT_CHARSET, inputCharset);
        params.put(ParamNames.MERCHANT_CODE, merchantCode);
        params.put(ParamNames.BANK_ACCOUNT, bankAccount);
        params.put(ParamNames.BANK_CARD_NO, bankCardNo);
        params.put(ParamNames.BANK_CODE, bankCode);
        params.put(ParamNames.AMOUNT, amount);
        params.put(ParamNames.MERCHANT_ORDER, String.valueOf(new java.util.Random().nextInt(100000000)));
        String sign = KeyValues.create()
                .add(ParamNames.INPUT_CHARSET, params.get(ParamNames.INPUT_CHARSET))
                .add(ParamNames.MERCHANT_CODE, params.get(ParamNames.MERCHANT_CODE))
                .add(ParamNames.BANK_ACCOUNT, params.get(ParamNames.BANK_ACCOUNT))
                .add(ParamNames.BANK_CARD_NO, params.get(ParamNames.BANK_CARD_NO))
                .add(ParamNames.BANK_CODE, params.get(ParamNames.BANK_CODE))
                .add(ParamNames.AMOUNT, params.get(ParamNames.AMOUNT))
                .add(ParamNames.MERCHANT_ORDER, params.get(ParamNames.MERCHANT_ORDER))
                .sign(merchantKey);
        params.put(ParamNames.SIGN, sign);
        params.put(ParamNames.BANK_ACCOUNT, URLEncoder.encode(bankAccount, inputCharset));
        StringBuilder sb = new StringBuilder();
        sb.append(url)
                .append("?").append(ParamNames.INPUT_CHARSET).append("={").append(ParamNames.INPUT_CHARSET).append("}")
                .append("&").append(ParamNames.MERCHANT_CODE).append("={").append(ParamNames.MERCHANT_CODE).append("}")
                .append("&").append(ParamNames.BANK_ACCOUNT).append("={").append(ParamNames.BANK_ACCOUNT).append("}")
                .append("&").append(ParamNames.BANK_CARD_NO).append("={").append(ParamNames.BANK_CARD_NO).append("}")
                .append("&").append(ParamNames.BANK_CODE).append("={").append(ParamNames.BANK_CODE).append("}")
                .append("&").append(ParamNames.AMOUNT).append("={").append(ParamNames.AMOUNT).append("}")
                .append("&").append(ParamNames.MERCHANT_ORDER).append("={").append(ParamNames.MERCHANT_ORDER).append("}")
                .append("&").append(ParamNames.SIGN).append("={").append(ParamNames.SIGN).append("}");
        String url = sb.toString();
        String result = sendGet(url, params);
        //System.out.println("request result is " + ("success".equals(result) ? "结算成功" : result));
        return result;
    }
    
    private String sendGet(String url, Map<String, String> params) {
        String result = "";
        BufferedReader in = null;
        try {
            Set<Map.Entry<String, String>> sets = params.entrySet();
            for (Map.Entry<String, String> entry : sets) {
                String k = entry.getKey();
                String v = entry.getValue();
                url = url.replace("{" + k + "}", v);
            }
            System.out.println("request url is " + url);
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),inputCharset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
