package com.maven.payment.th;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.maven.payment.th.utils.AppConstants;
import com.maven.payment.th.utils.DateUtils;
import com.maven.payment.th.utils.KeyValue;
import com.maven.payment.th.utils.KeyValues;
import com.maven.payment.th.utils.StringUtils;
import com.maven.payment.th.utils.URLUtils;

public class THSave{

	/**
	 * 字符集编码，取值UTF-8或者GBK
	 */
	private static  String CHARSET = "UTF-8"; 
	/**
	 * 支付网关地址
	 */
    private  String url;
    /**
     * 商户号
     */
    private  String merchantCode ;
    /**
     * 私钥key
     */
    private  String merchantKey ;
    /**
     * 通知当前支付是否成功的URL
     */
    private  String callbackUrl ;
    /**
     * 页面跳转到商户页面的URL
     */
    private  String pageToUserUrl ;
    /**
     * 支付方式,目前暂只支持网银支付，取值为1
     */
    private  String payType = "1"; 
    /**
     * 提交请求网站的域名，防钓鱼 
     */
    private  String referer ;
    /**
     * 回传参数
     */
    private String returnParams;
    
    
    /**
     * 获取字符编码
     * @return
     */
    public String getCHARSET() {
		return CHARSET;
	}

    /**
     * 设置字符编码
     */
	public void setCHARSET(String cHARSET) {
		CHARSET = cHARSET;
	}

	public THSave(String url,String merchantCode,String merchantKey, String callbackUrl,String pageToUserUrl,String referer,String returnParams){
    	this.url = url;
    	this.merchantCode = merchantCode;
    	this.merchantKey = merchantKey;
    	this.callbackUrl = callbackUrl;
    	this.pageToUserUrl = pageToUserUrl;
    	this.referer = referer;
    	this.returnParams = returnParams;
    }

    /**
     * 
     * @param bankCode 银行编码
     * @param orderNo 订单号
     * @param orderAmount 订单金额
     * @param customerPhone 客户电话
     * @param receiveAddr 收款地址
     * @param productName 产品名称
     * @param productNum  产品数量
     * @param customerIp  客户IP
     * @param returnParams 回传参数
     * @return
     */
    public  String getUrl(String bankCode,String orderNo,String orderAmount
    		,String customerPhone,String receiveAddr,String productName,String productNum,String customerIp)
    {
        if (StringUtils.isNullOrEmpty(orderNo))
        {
            throw new RuntimeException("请求的参数订单号为空");
        }
        if (StringUtils.isNullOrEmpty(orderAmount))
        {
            throw new RuntimeException("请求的参数订单金额为空");
        }

        String currentDate = DateUtils.format(new Date());

        KeyValues kvs = new KeyValues();
        kvs.add(new KeyValue(AppConstants.INPUT_CHARSET, CHARSET));
        kvs.add(new KeyValue(AppConstants.NOTIFY_URL, callbackUrl));
        kvs.add(new KeyValue(AppConstants.RETURN_URL, pageToUserUrl));
        kvs.add(new KeyValue(AppConstants.PAY_TYPE, payType));
        kvs.add(new KeyValue(AppConstants.BANK_CODE, bankCode));
        kvs.add(new KeyValue(AppConstants.MERCHANT_CODE, merchantCode));
        kvs.add(new KeyValue(AppConstants.ORDER_NO, orderNo));
        kvs.add(new KeyValue(AppConstants.ORDER_AMOUNT, orderAmount));
        kvs.add(new KeyValue(AppConstants.ORDER_TIME, currentDate));
        kvs.add(new KeyValue(AppConstants.PRODUCT_NAME, productName));
        kvs.add(new KeyValue(AppConstants.PRODUCT_NUM, productNum));
        kvs.add(new KeyValue(AppConstants.REQ_REFERER, referer));
        kvs.add(new KeyValue(AppConstants.CUSTOMER_IP, customerIp));
        kvs.add(new KeyValue(AppConstants.CUSTOMER_PHONE, customerPhone));
        kvs.add(new KeyValue(AppConstants.RECEIVE_ADDRESS, receiveAddr));
        kvs.add(new KeyValue(AppConstants.RETURN_PARAMS, returnParams));
        String sign = kvs.sign(merchantKey, CHARSET);

        StringBuilder sb = new StringBuilder();
        sb.append(url);
        URLUtils.appendParam(sb, AppConstants.INPUT_CHARSET, CHARSET, false);
        URLUtils.appendParam(sb, AppConstants.NOTIFY_URL, callbackUrl);
        URLUtils.appendParam(sb, AppConstants.RETURN_URL, pageToUserUrl);
        URLUtils.appendParam(sb, AppConstants.PAY_TYPE, payType);
        URLUtils.appendParam(sb, AppConstants.BANK_CODE, bankCode);
        URLUtils.appendParam(sb, AppConstants.MERCHANT_CODE, merchantCode);
        URLUtils.appendParam(sb, AppConstants.ORDER_NO, orderNo);
        URLUtils.appendParam(sb, AppConstants.ORDER_AMOUNT, orderAmount);
        URLUtils.appendParam(sb, AppConstants.ORDER_TIME, currentDate);
        URLUtils.appendParam(sb, AppConstants.PRODUCT_NAME, productName);
        URLUtils.appendParam(sb, AppConstants.PRODUCT_NUM, productNum);
        URLUtils.appendParam(sb, AppConstants.REQ_REFERER, referer);
        URLUtils.appendParam(sb, AppConstants.CUSTOMER_IP, customerIp);
        URLUtils.appendParam(sb, AppConstants.CUSTOMER_PHONE, customerPhone);
        URLUtils.appendParam(sb, AppConstants.RECEIVE_ADDRESS, receiveAddr);
        URLUtils.appendParam(sb, AppConstants.RETURN_PARAMS, returnParams);
        URLUtils.appendParam(sb, AppConstants.SIGN, sign);
        return sb.toString();
    }

    public static boolean validPageNotify(HttpServletRequest req,String merchantKey)
    {
        String merchantCode = req.getParameter(AppConstants.MERCHANT_CODE);
        String notifyType = req.getParameter(AppConstants.NOTIFY_TYPE);
        String orderNo = req.getParameter(AppConstants.ORDER_NO);
        String orderAmount = req.getParameter(AppConstants.ORDER_AMOUNT);
        String orderTime = req.getParameter(AppConstants.ORDER_TIME);
        String returnParams = req.getParameter(AppConstants.RETURN_PARAMS);
        String tradeNo = req.getParameter(AppConstants.TRADE_NO);
        String tradeTime = req.getParameter(AppConstants.TRADE_TIME);
        String tradeStatus = req.getParameter(AppConstants.TRADE_STATUS);
        String sign = req.getParameter(AppConstants.SIGN);
        KeyValues kvs = new KeyValues();

        kvs.add(new KeyValue(AppConstants.MERCHANT_CODE, merchantCode));
        kvs.add(new KeyValue(AppConstants.NOTIFY_TYPE, notifyType));
        kvs.add(new KeyValue(AppConstants.ORDER_NO, orderNo));
        kvs.add(new KeyValue(AppConstants.ORDER_AMOUNT, orderAmount));
        kvs.add(new KeyValue(AppConstants.ORDER_TIME, orderTime));
        kvs.add(new KeyValue(AppConstants.RETURN_PARAMS, returnParams));
        kvs.add(new KeyValue(AppConstants.TRADE_NO, tradeNo));
        kvs.add(new KeyValue(AppConstants.TRADE_TIME, tradeTime));
        kvs.add(new KeyValue(AppConstants.TRADE_STATUS, tradeStatus));
        String thizSign = kvs.sign(merchantKey, CHARSET);
        if (thizSign.equalsIgnoreCase(sign))
            return true;
        else
            return false;
    }

}
