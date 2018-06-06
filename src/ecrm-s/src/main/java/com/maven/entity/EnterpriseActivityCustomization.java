package com.maven.entity;

/**
 * 企业定制活动
 * @author Administrator
 *
 */
	
public class EnterpriseActivityCustomization {
	/**
	 * 企业定制活动编码
	 */
    private Integer ecactivitycode;

    /**
     * 企业编码
     */
    private String enterprisecode;

    /**
     * 活动模板ID
     */
    private Integer activitytemplatecode;

    /**
     * 活动名称
     */
    private String activityname;
    
    /**
     * 不共享活动
     */
    private String unshare;

    /**
     * 活动PC端图片
     */
    private String activityimage;
    /**
     * 活动H5端图片
     */
    private String activityimagehfive;
    /**
     * 数据状态
     */
    private String datastatus;
    
    /**
     * 活动内容
     */
    private String activitycontent;
    /**
     * 活动内容h5用
     */
    private String activitycontenth5;
    
    
    /**
     * 备注，用于存储一些隐藏数据
     */
    private String remark;
    
    private int ord;
    
    /* 关联属性：企业名称 */
    private String enterprisename;
    
    /* 关联属性：活动模板名称 */
    private String activitytemplatename;
    
    /* 唯一加密字段 */
    private String sign;

    public EnterpriseActivityCustomization(){}
    
    public EnterpriseActivityCustomization(String enterprisecode){
    	this.enterprisecode = enterprisecode;
    }
    
    public Integer getEcactivitycode() {
        return ecactivitycode;
    }

    public void setEcactivitycode(Integer ecactivitycode) {
        this.ecactivitycode = ecactivitycode;
    }

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode;
    }

    public Integer getActivitytemplatecode() {
        return activitytemplatecode;
    }

    public void setActivitytemplatecode(Integer activitytemplatecode) {
        this.activitytemplatecode = activitytemplatecode;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }

    public String getUnshare() {
		return unshare;
	}

	public void setUnshare(String unshare) {
		this.unshare = unshare;
	}

	public String getActivityimage() {
        return activityimage;
    }

    public void setActivityimage(String activityimage) {
        this.activityimage = activityimage;
    }

    public String getActivityimagehfive() {
        return activityimagehfive;
    }

    public void setActivityimagehfive(String activityimagehfive) {
        this.activityimagehfive = activityimagehfive;
    }

    public String getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(String datastatus) {
        this.datastatus = datastatus;
    }

    public String getActivitycontent() {
        return activitycontent;
    }

    public void setActivitycontent(String activitycontent) {
        this.activitycontent = activitycontent;
    }

	public String getEnterprisename() {
		return enterprisename;
	}

	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}

	public String getActivitytemplatename() {
		return activitytemplatename;
	}

	public void setActivitytemplatename(String activitytemplatename) {
		this.activitytemplatename = activitytemplatename;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public String getActivitycontenth5() {
		return activitycontenth5;
	}

	public void setActivitycontenth5(String activitycontenth5) {
		this.activitycontenth5 = activitycontenth5;
	}
}