package com.maven.entity;

import java.io.Serializable;

import com.maven.entity.PermissionMenu.Enum_menustatus;

public class PermissionRole implements Serializable,Comparable<PermissionRole>,Cloneable{
	/** 角色编码 */
	private String rolecode;
	/** 角色名称 */
	private String rolename;
	/** 企业编码 */
	private String enterprisecode;
	/** 数据状态 */
	private int datastatus;
	/** 权限 */
	private String permissions;
	/** 角色状态，用于标识用时是否拥有，初始禁用 */
	private Integer rolestatus = Enum_menustatus.禁用.value;
	/** 加密签名 */
	private String sign;
	
	public String getRolecode() {
		return rolecode;
	}
	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getEnterprisecode() {
		return enterprisecode;
	}
	public void setEnterprisecode(String enterprisecode) {
		this.enterprisecode = enterprisecode;
	}
	public int getDatastatus() {
		return datastatus;
	}
	public void setDatastatus(int datastatus) {
		this.datastatus = datastatus;
	}
	public String getPermissions() {
		return permissions;
	}
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	public Integer getRolestatus() {
		return rolestatus;
	}
	public void setRolestatus(Integer rolestatus) {
		this.rolestatus = rolestatus;
	}

	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	private static final long serialVersionUID = 1L;
	@Override
	public int compareTo(PermissionRole o) {
		if(o == null) return 1;
		int result = 0;
		if(rolecode != null) {
			if(o.rolecode == null) return 1;
			result += rolecode.compareTo(o.rolecode);
			if(result != 0) return 1;
		}
		if(permissions != null) {
			if(o.permissions == null) return 1;
			result += permissions.compareTo(o.permissions);
			if(result != 0) return 1;
		}
		return result;
	}
	
	@Override
	public PermissionRole clone() throws CloneNotSupportedException {
		return (PermissionRole)super.clone();
	} 
}