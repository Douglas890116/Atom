package com.maven.privacy;

/**
 * 私有数据权限控制对象
 * @author Administrator
 *
 */
public class PrivacyEntity {

	public Boolean isAccessMethod;
	
	public String[] accessFileds;
	
	public PrivacyEntity(Boolean isAccessMethod,String[] accessFileds){
		this.isAccessMethod = isAccessMethod;
		this.accessFileds = accessFileds;
	}

	public void setIsAccessMethod(Boolean isAccessMethod) {
		this.isAccessMethod = isAccessMethod;
	}

	public void setAccessFileds(String[] accessFileds) {
		this.accessFileds = accessFileds;
	}
	
	
	
}
