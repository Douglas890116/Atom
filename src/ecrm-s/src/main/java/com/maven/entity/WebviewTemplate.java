package com.maven.entity;

public class WebviewTemplate {
    private String webtemplatecode;

    private String templatename;

    private String sign;

    private String sign1;
    
	private String templatetype;
	
	private String sitetype;

	
    public String getSitetype() {
		return sitetype;
	}

	public void setSitetype(String sitetype) {
		this.sitetype = sitetype;
	}

	public String getWebtemplatecode() {
        return webtemplatecode;
    }

    public void setWebtemplatecode(String webtemplatecode) {
        this.webtemplatecode = webtemplatecode;
    }

    public String getTemplatename() {
        return templatename;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTemplatetype() {
        return templatetype;
    }

    public void setTemplatetype(String templatetype) {
        this.templatetype = templatetype;
    }
    public String getSign1() {
		return sign1;
	}

	public void setSign1(String sign1) {
		this.sign1 = sign1;
	}
}