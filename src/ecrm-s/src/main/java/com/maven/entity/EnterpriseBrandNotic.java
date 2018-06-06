package com.maven.entity;

import java.util.Date;

public class EnterpriseBrandNotic {
	/**
	 * 公告编码
	 */
    private String noticcode;
    
    /**
     * 企业编码
     */
    private String enterprisecode;

    /**
     * 品牌编码
     */
    private String brandcode;

    /**
     * 公告类型
     */
    private String notictype;
    
    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告开始时间
     */
    private Date begintime;
    
    /**
     * 创建时间
     */
    private Date createtime;
    
    /**
     * 数据状态
     */
    private String datastatus;
    
    /* 企业名称 */
    private String enterprisename;
    
    /* 品牌名称 */
    private String brandname;
    
    /* 查询条件:开始条数*/
	private Integer start;
	/* 查询条件:查询条数 */
	private Integer limit;

    /**
     * 公告结束时间
     */
    private Date endtime;

    public String getNoticcode() {
        return noticcode;
    }

    public void setNoticcode(String noticcode) {
        this.noticcode = noticcode;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

	public String getNotictype() {
		return notictype;
	}

	public void setNotictype(String notictype) {
		this.notictype = notictype;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getEnterprisename() {
		return enterprisename;
	}

	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getDatastatus() {
		return datastatus;
	}

	public void setDatastatus(String datastatus) {
		this.datastatus = datastatus;
	}
}