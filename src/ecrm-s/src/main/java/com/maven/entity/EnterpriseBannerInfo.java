package com.maven.entity;

public class EnterpriseBannerInfo {
    private Integer lsh;

    private String bannername;
    
    private String bannertype;//banner类型：PC，H5

    private String imgpath;

    private String linkpath;

    private Integer ord;

    private String brandcode;
    //非字段
    private String brandname;

    private String enterprisecode;
    /**
     * banner类型
     * PC - PC端使用
     * H5 - H5端使用
     * new1 - 新版PC端使用
     * @author klay
     *
     */
    public enum Enum_BannerType {
    	PC("PC"),
    	H5("H5"),
    	new1("new1");
    	public String value;
    	private Enum_BannerType(String value) {
    		this.value = value;
    	}
    }

    public Integer getLsh() {
        return lsh;
    }

    public void setLsh(Integer lsh) {
        this.lsh = lsh;
    }

    public String getBannername() {
        return bannername;
    }

    public void setBannername(String bannername) {
        this.bannername = bannername;
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

    public String getLinkpath() {
        return linkpath;
    }

    public void setLinkpath(String linkpath) {
        this.linkpath = linkpath;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getBrandcode() {
        return brandcode;
    }

    public void setBrandcode(String brandcode) {
        this.brandcode = brandcode;
    }

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode;
    }

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
}