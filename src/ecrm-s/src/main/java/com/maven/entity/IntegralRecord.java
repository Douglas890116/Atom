package com.maven.entity;

import java.math.BigDecimal;
import java.util.Date;

public class IntegralRecord {
    private String lsh;

    private String enterprisecode;

    private String brandcode;

    private String employeecode;

    private String loginaccount;

    private String gametype;

    private String gamebigtype;

    private String platformid;

    private BigDecimal betmoney;

    private BigDecimal rebatemoney;

    private BigDecimal m2iRate;

    private BigDecimal amount;

    private Integer status;

    private Date createtime;

    private Date exchangetime;

    private Integer exchangetype;

    private String exchangedesc;

    private String remark;

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

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public String getGamebigtype() {
        return gamebigtype;
    }

    public void setGamebigtype(String gamebigtype) {
        this.gamebigtype = gamebigtype;
    }

    public String getPlatformid() {
        return platformid;
    }

    public void setPlatformid(String platformid) {
        this.platformid = platformid;
    }

    public BigDecimal getBetmoney() {
        return betmoney;
    }

    public void setBetmoney(BigDecimal betmoney) {
        this.betmoney = betmoney;
    }

    public BigDecimal getRebatemoney() {
        return rebatemoney;
    }

    public void setRebatemoney(BigDecimal rebatemoney) {
        this.rebatemoney = rebatemoney;
    }

    public BigDecimal getM2iRate() {
        return m2iRate;
    }

    public void setM2iRate(BigDecimal m2iRate) {
        this.m2iRate = m2iRate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getExchangetime() {
        return exchangetime;
    }

    public void setExchangetime(Date exchangetime) {
        this.exchangetime = exchangetime;
    }

    public Integer getExchangetype() {
        return exchangetype;
    }

    public void setExchangetype(Integer exchangetype) {
        this.exchangetype = exchangetype;
    }

    public String getExchangedesc() {
        return exchangedesc;
    }

    public void setExchangedesc(String exchangedesc) {
        this.exchangedesc = exchangedesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}