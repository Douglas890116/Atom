package com.maven.entity;

import java.util.Date;

public class Favourable {
    private String lsh;

    private String favourablename;

    private Integer isonce;

    private String enterprisecode;

    private Double lsbs;

    private Date starttime;

    private Date endtime;

    private Date createtime;

    private Integer isdeault;

    private Integer status;
    
    public enum Enum_status{
    	启用(0,"启用"),
    	禁用(1,"禁用");
		public int value;
		public String desc;
		
		private Enum_status(int value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
    
    public enum Enum_isdeault{
    	非默认(0,"非默认"),
    	默认(1,"默认");
		public int value;
		public String desc;
		
		private Enum_isdeault(int value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}

    
    public enum Enum_isonce{
    	一次(0,"一次"),
    	多次(1,"多次");
		public int value;
		public String desc;
		
		private Enum_isonce(int value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getFavourablename() {
        return favourablename;
    }

    public void setFavourablename(String favourablename) {
        this.favourablename = favourablename;
    }

    public Integer getIsonce() {
        return isonce;
    }

    public void setIsonce(Integer isonce) {
        this.isonce = isonce;
    }

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode;
    }

    public Double getLsbs() {
        return lsbs;
    }

    public void setLsbs(Double lsbs) {
        this.lsbs = lsbs;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getIsdeault() {
        return isdeault;
    }

    public void setIsdeault(Integer isdeault) {
        this.isdeault = isdeault;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}