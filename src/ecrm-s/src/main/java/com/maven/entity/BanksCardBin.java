package com.maven.entity;

import java.io.Serializable;

public class BanksCardBin implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private Integer lsh;

    private String bankprefix;

    private String bankcode;

    private String bankname;

    private Integer banklen;

    private String remark;

    public Integer getLsh() {
        return lsh;
    }

    public void setLsh(Integer lsh) {
        this.lsh = lsh;
    }

    public String getBankprefix() {
        return bankprefix;
    }

    public void setBankprefix(String bankprefix) {
        this.bankprefix = bankprefix;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public Integer getBanklen() {
        return banklen;
    }

    public void setBanklen(Integer banklen) {
        this.banklen = banklen;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}