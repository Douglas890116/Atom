package com.maven.entity;

import java.io.Serializable;

public class EnterpriseWebview implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键,自增长
	 */
    private Integer ewvcode;

    /**
     * 企业编码
     */
    private String enterprisecode;

    /**
     * web模板编码
     */
    private String webtemplatecode;
    
    /**
     * 模板归属 1网站 2代理
     */
    private String sitetype;
    
    /* 关联属性:模板名称 */
    private String templatename;
    
    
    public EnterpriseWebview(){}
    
    
    public EnterpriseWebview(String enterprisecode,String sitetype){
    	this.enterprisecode = enterprisecode;
    	this.sitetype = sitetype;
    }
    
    public enum Enum_sitetype{
		会员站点("1","会员站点"),
		代理站点("2","代理站点");
		public String value;
		public String desc;
		private Enum_sitetype(String value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}

    public Integer getEwvcode() {
        return ewvcode;
    }

    public void setEwvcode(Integer ewvcode) {
        this.ewvcode = ewvcode;
    }

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode;
    }

    public String getWebtemplatecode() {
        return webtemplatecode;
    }

    public void setWebtemplatecode(String webtemplatecode) {
        this.webtemplatecode = webtemplatecode;
    }

	public String getSitetype() {
		return sitetype;
	}

	public void setSitetype(String sitetype) {
		this.sitetype = sitetype;
	}

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}
    
}