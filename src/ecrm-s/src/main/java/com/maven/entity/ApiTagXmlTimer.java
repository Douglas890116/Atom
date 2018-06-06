package com.maven.entity;

public class ApiTagXmlTimer {
	
    
    private String agentcode;
    private String platformtype;
    private String xmltype;
    private String updatetime;
    
    //视图
    private String remark;

    public String getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(String agentcode) {
        this.agentcode = agentcode;
    }

    public String getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(String platformtype) {
        this.platformtype = platformtype;
    }

    public String getXmltype() {
        return xmltype;
    }

    public void setXmltype(String xmltype) {
        this.xmltype = xmltype;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}