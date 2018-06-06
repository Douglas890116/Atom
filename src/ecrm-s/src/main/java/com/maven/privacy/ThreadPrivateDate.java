package com.maven.privacy;

/**
 * 私有数据过滤控制开关
 * @author Administrator
 *
 */
public class ThreadPrivateDate {
	
	private static ThreadLocal<PrivacyEntity> isaccessmethod = new ThreadLocal<PrivacyEntity>();
	
	private static ThreadLocal<Boolean> userprivatedata = new ThreadLocal<Boolean>();

	public static PrivacyEntity getIsaccessmethod() {
		return isaccessmethod.get();
	}

	public static void setIsaccessmethod(PrivacyEntity accessmethod) {
		isaccessmethod.set(accessmethod); 
	}

	public static Boolean getUserprivatedata() {
		return userprivatedata.get();
	}

	public static void setUserprivatedata(Boolean privateDate) {
		userprivatedata.set(privateDate);
	} 
	

}
