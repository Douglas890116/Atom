package com.maven.payment.hr.df;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 华仁代付 请求参数
 * @author klay
 *
 */
public class HRDFAppConstants {
	
	/**
     * // 商户订单格式：订单日期-商户编号-订单流水号
     * @return
     */
    public static String getOderNumber() {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat time = new SimpleDateFormat("HHmmss");
        Date now = new Date();
        StringBuffer sb = new StringBuffer();
        sb.append(date.format(now)).append("-110102-").append(time.format(now));
        return sb.toString();
    }
    /** 支付接口参数 **/
    // 协议包编码
    public static final String p1_v_pagecode = "v_pagecode";
    // 商户编码：初始单上所填商户编号为准
    public static final String p1_v_mid = "v_mid";
    // 商户订单号：格式【日期-商户编号-商户流水号，例如20100101-888-12345，流水号可为一组也可以用“-”间隔成几组】
    public static final String p1_v_oid = "v_oid";
    // 订单总金额
    public static final String p1_v_amount = "v_amount";
    // 收款人姓名
    public static final String p1_v_payeename = "v_payeename";
    // 收款人卡号
    public static final String p1_v_payeecard = "v_payeecard";
    // 银行卡开户省
    public static final String p1_v_accountprovince = "v_accountprovince";
    // 银行卡开户市
    public static final String p1_v_accountcity = "v_accountcity";
    // 开户行名称
    public static final String p1_v_bankname = "v_bankname";
    // 银行代码
    public static final String p1_v_bankno = "v_bankno";
    // 出款时间
    public static final String p1_v_ymd = "v_ymd";
    // 返回商户页面
    public static final String p1_v_url = "v_url";
    // 订单数字指纹
    public static final String p1_v_md5info = "v_md5info";
    
    /** 支付结果返回接口  **/
    // 协议包编码
    public static final String p2_v_pagecode = "v_pagecode";
    // 商户编号
    public static final String p2_v_mid = "v_mid";
    // 订单编号
    public static final String p2_v_oid = "v_oid";
    // 平台订单编号
    public static final String p2_v_orderid = "v_orderid";
    // 代付金额
    public static final String p2_v_amount = "v_amount";
    // 结果申请状态
    public static final String p2_v_status = "v_status";
    // 签名串
    public static final String p2_v_sign = "v_sign";
}