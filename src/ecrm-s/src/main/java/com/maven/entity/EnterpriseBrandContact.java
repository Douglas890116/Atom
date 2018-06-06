package com.maven.entity;

public class EnterpriseBrandContact {
    private String contactcode;
    
    private String enterprisecode;

    private String brandcode;
    
    private String brandname;

    private String contacttitle;
    
    private String contacttype;

    private String content;

    private String contenttype;

    private String enable;

    private String datastatus;
    
    private String sign;

    public String getContactcode() {
        return contactcode;
    }

    public void setContactcode(String contactcode) {
        this.contactcode = contactcode;
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

    public String getContacttitle() {
		return contacttitle;
	}

	public void setContacttitle(String contacttitle) {
		this.contacttitle = contacttitle;
	}

	public String getContacttype() {
        return contacttype;
    }

    public void setContacttype(String contacttype) {
        this.contacttype = contacttype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(String datastatus) {
        this.datastatus = datastatus;
    }

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brandcode == null) ? 0 : brandcode.hashCode());
		result = prime * result + ((brandname == null) ? 0 : brandname.hashCode());
		result = prime * result + ((contactcode == null) ? 0 : contactcode.hashCode());
		result = prime * result + ((contacttype == null) ? 0 : contacttype.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((contenttype == null) ? 0 : contenttype.hashCode());
		result = prime * result + ((datastatus == null) ? 0 : datastatus.hashCode());
		result = prime * result + ((enable == null) ? 0 : enable.hashCode());
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
		EnterpriseBrandContact other = (EnterpriseBrandContact) obj;
		if (brandcode == null) {
			if (other.brandcode != null)
				return false;
		} else if (!brandcode.equals(other.brandcode))
			return false;
		if (brandname == null) {
			if (other.brandname != null)
				return false;
		} else if (!brandname.equals(other.brandname))
			return false;
		if (contactcode == null) {
			if (other.contactcode != null)
				return false;
		} else if (!contactcode.equals(other.contactcode))
			return false;
		if (contacttype == null) {
			if (other.contacttype != null)
				return false;
		} else if (!contacttype.equals(other.contacttype))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (contenttype == null) {
			if (other.contenttype != null)
				return false;
		} else if (!contenttype.equals(other.contenttype))
			return false;
		if (datastatus == null) {
			if (other.datastatus != null)
				return false;
		} else if (!datastatus.equals(other.datastatus))
			return false;
		if (enable == null) {
			if (other.enable != null)
				return false;
		} else if (!enable.equals(other.enable))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("EnterpriseBrandContact");
		sb.append("[");
		sb.append("contactcode=").append(contactcode).append(",");
		sb.append("brandcode=").append(brandcode).append(",");
		sb.append("brandname=").append(brandname).append(",");
		sb.append("contacttitle=").append(contacttitle).append(",");
		sb.append("contacttype=").append(contacttype).append(",");
		sb.append("content=").append(content).append(",");
		sb.append("contenttype=").append(contenttype).append(",");
		sb.append("enable=").append(enable).append(",");
		sb.append("datastatus=").append(datastatus);
		sb.append("]");
		
		return sb.toString();
	}
    
}