package com.maven.entity;

import java.util.Date;

public class ApiSoltGametypeEnterprise {
    private Integer lsh;

    private String enterprisecode;

    private String biggametype;

    private Integer gametypeId;

    private Integer status;

    /////////////////////////以下字段属于视图字段
    /** enname. */
	private String enname;
	/** cnname. */
	private String cnname;
	/** trname. */
	private String trname;
	/** japname. */
	private String japname;
	/** krname. */
	private String krname;
	/** stype. */
	private String stype;
	/** category. */
	private String category;
	/** category2. */
	private String category2;
	/** category3. */
	private String category3;
	/** imagename. */
	private String imagename;
	/** gamecodeweb. */
	private String gamecodeweb;
	/** gamecodeh5. */
	private String gamecodeh5;
	/** isweb. */
	private String isweb;//0=支持 1=不支持
	/** ish5. */
	private String ish5;//0=支持 1=不支持
	/** remark. */
	private String remark;
	private String ord;//排序号，升幂
	
	
	
	
    public enum Enum_status{
		启用("1","启用"),
		禁用("2","禁用");
		public String value;
		public String desc;
		
		private Enum_status(String value,String desc){
			this.value=value;
			this.desc=desc;
		}
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

    public String getBiggametype() {
        return biggametype;
    }

    public void setBiggametype(String biggametype) {
        this.biggametype = biggametype;
    }

    public Integer getGametypeId() {
        return gametypeId;
    }

    public void setGametypeId(Integer gametypeId) {
        this.gametypeId = gametypeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getTrname() {
		return trname;
	}

	public void setTrname(String trname) {
		this.trname = trname;
	}

	public String getJapname() {
		return japname;
	}

	public void setJapname(String japname) {
		this.japname = japname;
	}

	public String getKrname() {
		return krname;
	}

	public void setKrname(String krname) {
		this.krname = krname;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getCategory3() {
		return category3;
	}

	public void setCategory3(String category3) {
		this.category3 = category3;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public String getGamecodeweb() {
		return gamecodeweb;
	}

	public void setGamecodeweb(String gamecodeweb) {
		this.gamecodeweb = gamecodeweb;
	}

	public String getGamecodeh5() {
		return gamecodeh5;
	}

	public void setGamecodeh5(String gamecodeh5) {
		this.gamecodeh5 = gamecodeh5;
	}

	public String getIsweb() {
		return isweb;
	}

	public void setIsweb(String isweb) {
		this.isweb = isweb;
	}

	public String getIsh5() {
		return ish5;
	}

	public void setIsh5(String ish5) {
		this.ish5 = ish5;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}
}