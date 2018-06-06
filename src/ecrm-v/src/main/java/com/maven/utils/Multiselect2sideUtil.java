package com.maven.utils;

public class Multiselect2sideUtil {


    private String employeecode;
    
	private String loginaccount;//会员账号
    private boolean selected;
    
    public Multiselect2sideUtil(String employeecode, String loginaccount, boolean selected) {
		super();
		this.employeecode = employeecode;
		this.loginaccount = loginaccount;
		this.selected = selected;
	}
    
	public String getEmployeecode() {
		return employeecode;
	}
	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}
	public String getLoginaccount() {
		return loginaccount;
	}
	public void setLoginaccount(String loginaccount) {
		this.loginaccount = loginaccount;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
