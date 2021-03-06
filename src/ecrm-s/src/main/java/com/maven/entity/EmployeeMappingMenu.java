package com.maven.entity;

import java.io.Serializable;

/**
 * Model class of employee_mapping_permission_group.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class EmployeeMappingMenu implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 员工编码. */
	private String employeecode;

	/** 菜单编码. */
	private String menucode;
	
	/**
	 * Constructor.
	 */
	public EmployeeMappingMenu() {
	}
	
	public EmployeeMappingMenu(String employeecode,String menucode) {
		this.employeecode = employeecode;
		this.menucode = menucode;
	}

	/**
	 * Set the employeecode.
	 * 
	 * @param employeecode
	 *            employeecode
	 */
	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}

	/**
	 * Get the employeecode.
	 * 
	 * @return employeecode
	 */
	public String getEmployeecode() {
		return this.employeecode;
	}

	


	public String getMenucode() {
		return menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EmployeeMappingMenu other = (EmployeeMappingMenu) obj;
		if (employeecode == null) {
			if (other.employeecode != null) {
				return false;
			}
		} else if (!employeecode.equals(other.employeecode)) {
			return false;
		}
		return true;
	}

}
