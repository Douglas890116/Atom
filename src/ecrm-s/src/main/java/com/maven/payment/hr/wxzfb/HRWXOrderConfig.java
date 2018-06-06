package com.maven.payment.hr.wxzfb;
/**
 * 华仁微信支付宝订单参数
 * @author klay
 *
 */
public class HRWXOrderConfig {
    // 商户订单编号
    private String v_oid;
    // 收货人姓名-建议使用商户编号的值代替。
    private String v_rcvname;
    // 收货人地址-建议使用商户编号的值代替。
    private String v_rcvaddr;
    // 收货人电话-建议使用商户编号的值代替。
    private String v_rcvtel;
    // 商品名称
    private String v_goodsname;
    // 交易商品描述
    private String v_goodsdescription;
    // 收货人邮政编码-建议使用商户编号的值代替。
    private String v_rcvpost;
    // 收货人QQ
    private String v_qq;
    // 订单总金额
    private String v_amount;
    // 订单产生日期
    private String v_ymd;
    // 订货人姓名-建议使用商户编号的值代替。
    private String v_ordername;
    // 接入方式
    private String v_app;
    // 支付币种
    private String v_moneytype;
    
    public HRWXOrderConfig(String v_oid, String v_rcvname, String v_rcvaddr, String v_rcvtel, String v_goodsname, String v_goodsdescription, 
            String v_rcvpost, String v_qq, String v_amount, String v_ymd, String v_ordername, String v_app, String v_moneytype) {
            this.v_oid = v_oid;
            this.v_rcvname = v_rcvname;
            this.v_rcvaddr = v_rcvaddr;
            this.v_rcvtel = v_rcvtel;
            this.v_goodsname = v_goodsname;
            this.v_goodsdescription = v_goodsdescription;
            this.v_rcvpost = v_rcvpost;
            this.v_qq = v_qq;
            this.v_amount = v_amount;
            this.v_ymd = v_ymd;
            this.v_ordername = v_ordername;
            this.v_app = v_app;
            this.v_moneytype = v_moneytype;
        }
    
    public String getV_oid() {
        return v_oid;
    }

    public String getV_rcvname() {
        return v_rcvname;
    }

    public String getV_rcvaddr() {
        return v_rcvaddr;
    }

    public String getV_rcvtel() {
        return v_rcvtel;
    }

    public String getV_goodsname() {
        return v_goodsname;
    }

    public String getV_goodsdescription() {
        return v_goodsdescription;
    }

    public String getV_rcvpost() {
        return v_rcvpost;
    }

    public String getV_qq() {
        return v_qq;
    }

    public String getV_amount() {
        return v_amount;
    }

    public String getV_ymd() {
        return v_ymd;
    }

    public String getV_ordername() {
        return v_ordername;
    }

    public String getV_app() {
        return v_app;
    }

    public String getV_moneytype() {
        return v_moneytype;
    }

    public void setV_oid(String v_oid) {
        this.v_oid = v_oid;
    }

    public void setV_rcvname(String v_rcvname) {
        this.v_rcvname = v_rcvname;
    }

    public void setV_rcvaddr(String v_rcvaddr) {
        this.v_rcvaddr = v_rcvaddr;
    }

    public void setV_rcvtel(String v_rcvtel) {
        this.v_rcvtel = v_rcvtel;
    }

    public void setV_goodsname(String v_goodsname) {
        this.v_goodsname = v_goodsname;
    }

    public void setV_goodsdescription(String v_goodsdescription) {
        this.v_goodsdescription = v_goodsdescription;
    }

    public void setV_rcvpost(String v_rcvpost) {
        this.v_rcvpost = v_rcvpost;
    }

    public void setV_qq(String v_qq) {
        this.v_qq = v_qq;
    }

    public void setV_amount(String v_amount) {
        this.v_amount = v_amount;
    }

    public void setV_ymd(String v_ymd) {
        this.v_ymd = v_ymd;
    }

    public void setV_ordername(String v_ordername) {
        this.v_ordername = v_ordername;
    }

    public void setV_app(String v_app) {
        this.v_app = v_app;
    }

    public void setV_moneytype(String v_moneytype) {
        this.v_moneytype = v_moneytype;
    }
    public HRWXOrderConfig(){}
}