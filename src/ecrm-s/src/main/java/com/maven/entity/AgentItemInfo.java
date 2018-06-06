package com.maven.entity;

import java.io.Serializable;

public class AgentItemInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键. */
	private Integer lsh;

	/** 企业编码. */
	private String enterprisecode;

	/** 品牌编码. */
	private String brandcode;

	/** 栏目图标地址. */
	private String iconpath;

	/** 栏目标题. */
	private String title;

	/** 栏目内容. */
	private String content;

	public AgentItemInfo() {
		super();
	}

	public Integer getLsh() {
		return lsh;
	}

	public void setLsh(Integer lsh) {
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

	public String getIconpath() {
		return iconpath;
	}

	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brandcode == null) ? 0 : brandcode.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((enterprisecode == null) ? 0 : enterprisecode.hashCode());
		result = prime * result + ((iconpath == null) ? 0 : iconpath.hashCode());
		result = prime * result + ((lsh == null) ? 0 : lsh.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentItemInfo other = (AgentItemInfo) obj;
		if (brandcode == null) {
			if (other.brandcode != null)
				return false;
		} else if (!brandcode.equals(other.brandcode))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (enterprisecode == null) {
			if (other.enterprisecode != null)
				return false;
		} else if (!enterprisecode.equals(other.enterprisecode))
			return false;
		if (iconpath == null) {
			if (other.iconpath != null)
				return false;
		} else if (!iconpath.equals(other.iconpath))
			return false;
		if (lsh == null) {
			if (other.lsh != null)
				return false;
		} else if (!lsh.equals(other.lsh))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}