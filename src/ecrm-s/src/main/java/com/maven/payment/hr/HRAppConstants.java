package com.maven.payment.hr;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 华仁支付 请求参数
 * 
 * @author klay
 *
 */
public class HRAppConstants {
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
    
    /**
     * // 商户订单格式：订单日期-商户编号-订单流水号
     * @return
     */
    public static String getOderNumber(String merno) {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat time = new SimpleDateFormat("HHmmss");
        Date now = new Date();
        StringBuffer sb = new StringBuffer();
        sb.append(date.format(now)).append("-"+merno+"-").append(time.format(now));
        return sb.toString();
    }

    /** 支付接口参数 **/
    // 协议包编码
    public static final String p1_v_pagecode = "v_pagecode";
    // 商户编码：初始单上所填商户编号为准
    public static final String p1_v_mid = "v_mid";
    // 商户订单号：格式【日期-商户编号-商户流水号，例如20100101-888-12345，流水号可为一组也可以用“-”间隔成几组】
    public static final String p1_v_oid = "v_oid";
    // 收货人姓名：建议用商户编码替代
    public static final String p1_v_rcvname = "v_rcvname";
    // 收货人地址：建议用商户编码替代
    public static final String p1_v_rcvaddr = "v_rcvaddr";
    // 收货人电话：建议用商户编码替代
    public static final String p1_v_rcvtel = "v_rcvtel";
    // 商品名称
    public static final String p1_v_goodsname = "v_goodsname";
    // 交易商品描述
    public static final String p1_v_goodsdescription = "v_goodsdescription";
    // 收货人邮政编码
    public static final String p1_v_rcvpost = "v_rcvpost";
    // 收货人QQ
    public static final String p1_v_qq = "v_qq";
    // 订单总金额
    public static final String p1_v_amount = "v_amount";
    // 订单产生日期
    public static final String p1_v_ymd = "v_ymd";
    // 商户配货状态
    public static final String p1_v_orderstatus = "v_orderstatus";
    // 订货人姓名
    public static final String p1_v_ordername = "v_ordername";
    // 银行编码
    public static final String p1_v_bankno = "v_bankno";
    // 支付币种
    public static final String p1_v_moneytype = "v_moneytype";
    // 返回商户页面
    public static final String p1_v_url = "v_url";
    // 同步返回商户页面地址
    public static final String p1_v_noticeurl = "v_noticeurl";
    // 订单数字指纹
    public static final String p1_v_md5info = "v_md5info";

    /** 支付结果返回接口 **/
    // 协议包编码
    public static final String p2_v_pagecode = "v_pagecode";
    // 商户编号
    public static final String p2_v_mid = "v_mid";
    // 订单编号
    public static final String p2_v_oid = "v_oid";
    // 平台订单编号
    public static final String p2_v_orderid = "v_orderid";
    // 银行编号
    public static final String p2_v_bankno = "v_bankno";
    // 交易结果
    public static final String p2_v_result = "v_result";
    // 提交金额
    public static final String p2_v_value = "v_value";
    // 实际金额
    public static final String p2_v_realvalue = "v_realvalue";
    // 客户QQ
    public static final String p2_v_qq = "v_qq";
    // 客户电话
    public static final String p2_v_telephone = "v_telephone";
    // 商品名称
    public static final String p2_v_goodsname = "v_goodsname";
    // 商品描述
    public static final String p2_v_goodsdescription = "v_goodsdescription";
    // 商品描述
    public static final String p2_v_extmsg = "v_extmsg";
    // 结果描述
    public static final String p2_v_resultmsg = "v_resultmsg";
    // 签名串
    public static final String p2_v_sign = "v_sign";
}