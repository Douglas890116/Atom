package com.maven.payment.hr.df;

/**
 * 华仁代付订单信息
 * 
 * @author klay
 *
 */
public class HRDFOrderConfig {
    // 商户订单编号
    private String v_oid;
    // 代付总金额
    private String v_amount;
    // 收款人姓名
    private String v_payeename;
    // 收款人卡号
    private String v_payeecard;
    // 账户开户省
    private String v_accountprovince;
    // 账户卡开户市
    private String v_accountcity;
    // 开户行名称
    private String v_bankname;
    // 银行编码
    private String v_bankno;
    // 订单产生日期
    private String v_ymd;

    public HRDFOrderConfig(String v_oid, String v_amount, String v_payeename, String v_payeecard,
            String v_accountprovince, String v_accountcity, String v_bankname, String v_bankno, String v_ymd) {
        this.v_oid = v_oid;
        this.v_amount = v_amount;
        this.v_payeename = v_payeename;
        this.v_payeecard = v_payeecard;
        this.v_accountprovince = v_accountprovince;
        this.v_accountcity = v_accountcity;
        this.v_bankname = v_bankname;
        this.v_bankno = v_bankno;
        this.v_ymd = v_ymd;
    }

    public HRDFOrderConfig() {
    }

    public String getV_oid() {
        return v_oid;
    }

    public String getV_amount() {
        return v_amount;
    }

    public String getV_payeename() {
        return v_payeename;
    }

    public String getV_payeecard() {
        return v_payeecard;
    }

    public String getV_accountprovince() {
        return v_accountprovince;
    }

    public String getV_accountcity() {
        return v_accountcity;
    }

    public String getV_bankname() {
        return v_bankname;
    }

    public String getV_bankno() {
        return v_bankno;
    }

    public String getV_ymd() {
        return v_ymd;
    }

    public void setV_oid(String v_oid) {
        this.v_oid = v_oid;
    }

    public void setV_amount(String v_amount) {
        this.v_amount = v_amount;
    }

    public void setV_payeename(String v_payeename) {
        this.v_payeename = v_payeename;
    }

    public void setV_payeecard(String v_payeecard) {
        this.v_payeecard = v_payeecard;
    }

    public void setV_accountprovince(String v_accountprovince) {
        this.v_accountprovince = v_accountprovince;
    }

    public void setV_accountcity(String v_accountcity) {
        this.v_accountcity = v_accountcity;
    }

    public void setV_bankname(String v_bankname) {
        this.v_bankname = v_bankname;
    }

    public void setV_bankno(String v_bankno) {
        this.v_bankno = v_bankno;
    }

    public void setV_ymd(String v_ymd) {
        this.v_ymd = v_ymd;
    }
}