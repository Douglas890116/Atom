package com.maven.entity;

import java.io.Serializable;

public class EmployeeMappingRole implements Serializable {

	/** 员工编码 */
	private String employeecode;
	/** 角色编码 */
	private String rolecode;

	public String getEmployeecode() {
		return employeecode;
	}

	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public EmployeeMappingRole (){}
	
	public EmployeeMappingRole(String employeecode, String rolecode) {
		this.employeecode = employeecode;
		this.rolecode = rolecode;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o == this) return true;
		if (getClass() != o.getClass()) return false;
		
		EmployeeMappingRole other = (EmployeeMappingRole) o;
		if (this.employeecode == null) {
			if (other.employeecode != null) return false;
		} else if (employeecode.equals(other.employeecode)) {
			return false;
		}
		return true;
	}

	private static final long serialVersionUID = 1L;
}