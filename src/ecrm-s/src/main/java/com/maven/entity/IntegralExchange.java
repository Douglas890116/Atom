package com.maven.entity;

import java.math.BigDecimal;
import java.util.Date;

public class IntegralExchange {
    private String lsh;

    private String enterprisecode;

    private String brandcode;

    private String employeecode;

    private String loginaccount;

    private String integralcode;

    private BigDecimal amount;

    private String patchno;

    private Date createtime;

    private String exchangedesc;

    private Integer status;

    private String checkaccount;

    private Date checktime;

    private String checkdesc;

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode;
    }

    public String getBrandcode() {
        return brandcode;
    }

    public void setBrandcode(String brandcode) {
        this.brandcode = brandcode;
    }

    public String getEmployeecode() {
        return employeecode;
    }

    public void setEmployeecode(String employeecode) {
        this.employeecode = employeecode;
    }

    public String getLoginaccount() {
        return loginaccount;
    }

    public void setLoginaccount(String loginaccount) {
        this.loginaccount = loginaccount;
    }

    public String getIntegralcode() {
        return integralcode;
    }

    public void setIntegralcode(String integralcode) {
        this.integralcode = integralcode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPatchno() {
        return patchno;
    }

    public void setPatchno(String patchno) {
        this.patchno = patchno;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getExchangedesc() {
        return exchangedesc;
    }

    public void setExchangedesc(String exchangedesc) {
        this.exchangedesc = exchangedesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCheckaccount() {
        return checkaccount;
    }

    public void setCheckaccount(String checkaccount) {
        this.checkaccount = checkaccount;
    }

    public Date getChecktime() {
        return checktime;
    }

    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }

    public String getCheckdesc() {
        return checkdesc;
    }

    public void setCheckdesc(String checkdesc) {
        this.checkdesc = checkdesc;
    }
}