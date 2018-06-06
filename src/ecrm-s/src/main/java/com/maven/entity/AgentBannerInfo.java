package com.maven.entity;

import java.io.Serializable;

public class AgentBannerInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 主键. */
	private Integer lsh;

	/** 企业编码. */
	private String enterprisecode;

	/** 品牌编码. */
	private String brandcode;

	/** banner类型:PC/H5. */
	private String bannertype;
	
	/** banner图片地址. */
	private String imgpath;

	public AgentBannerInfo() {
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

	public String getBannertype() {
		return bannertype;
	}

	public void setBannertype(String bannertype) {
		this.bannertype = bannertype;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bannertype == null) ? 0 : bannertype.hashCode());
		result = prime * result + ((brandcode == null) ? 0 : brandcode.hashCode());
		result = prime * result + ((enterprisecode == null) ? 0 : enterprisecode.hashCode());
		result = prime * result + ((imgpath == null) ? 0 : imgpath.hashCode());
		result = prime * result + ((lsh == null) ? 0 : lsh.hashCode());
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
		AgentBannerInfo other = (AgentBannerInfo) obj;
		if (bannertype == null) {
			if (other.bannertype != null)
				return false;
		} else if (!bannertype.equals(other.bannertype))
			return false;
		if (brandcode == null) {
			if (other.brandcode != null)
				return false;
		} else if (!brandcode.equals(other.brandcode))
			return false;
		if (enterprisecode == null) {
			if (other.enterprisecode != null)
				return false;
		} else if (!enterprisecode.equals(other.enterprisecode))
			return false;
		if (imgpath == null) {
			if (other.imgpath != null)
				return false;
		} else if (!imgpath.equals(other.imgpath))
			return false;
		if (lsh == null) {
			if (other.lsh != null)
				return false;
		} else if (!lsh.equals(other.lsh))
			return false;
		return true;
	}
	
}