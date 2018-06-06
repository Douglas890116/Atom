package com.maven.entity;

import java.util.Date;

public class DataHandleMaintenance {
    private String gametype;

    private Integer flag;

    private Date lasttime;

    private String remark;
    
    //视图属性
    private String gamename;

    public enum Enum_flag{
    	正常(1,"正常"),
    	维护(2,"维护"),
    	禁用(3,"禁用");
    	public Integer value;
    	public String desc;
    	private Enum_flag(Integer _value,String _desc){
    		this.value = _value;
    		this.desc = _desc;
    	}
    }
    
    
    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
}