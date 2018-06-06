package com.maven.util;

import java.util.List;

import com.maven.entity.LogLogin;

public class ActivityUtils {
	
	/**
	 * 注册体验金验证字段类
	 * @author king
	 */
	public class ActivityUniqueinfoCheck{
		
		private String loginaccount; //账号
		private String housenumber; //每户
		private String houseaddress; //地址
		private String email; //邮箱
		private String phonenumber; //手机
		private String payment; //支付方式
		private String ip; //IP地址
		
		public String getLoginaccount() {
			return loginaccount;
		}
		public void setLoginaccount(String loginaccount) {
			this.loginaccount = loginaccount;
		}
		public String getHousenumber() {
			return housenumber;
		}
		public void setHousenumber(String housenumber) {
			this.housenumber = housenumber;
		}
		public String getHouseaddress() {
			return houseaddress;
		}
		public void setHouseaddress(String houseaddress) {
			this.houseaddress = houseaddress;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPhonenumber() {
			return phonenumber;
		}
		public void setPhonenumber(String phonenumber) {
			this.phonenumber = phonenumber;
		}
		public String getPayment() {
			return payment;
		}
		public void setPayment(String payment) {
			this.payment = payment;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		
		public ActivityUniqueinfoCheck(){}
		
		public ActivityUniqueinfoCheck(String loginaccount, String housenumber, String houseaddress, String email, 
				String phonenumber, String payment, String ip){
			this.loginaccount = loginaccount;
			this.housenumber = housenumber;
			this.houseaddress = houseaddress;
			this.email = email;
			this.phonenumber = phonenumber;
			this.payment = payment;
			this.ip = ip;
		}
	}
	
	/**
	 * 活动检查返回结果
	 * @author king
	 */
	public class ActivityCheckResult{
		private boolean result;
		private String code;
		private String message;
		
		public boolean getResult() {
			return result;
		}
		public void setResult(boolean result) {
			this.result = result;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
	}
	
	/**
	 * 活动检查返回信息
	 * @author king
	 */
	public enum ActivityCheckMessage{
		GET_SUCCESS("0","领取成功"),
		GET_ALREADY("1","已领取过该活动"),
		GET_USERNOTEXIST("2","用户不存在"),
		
		BRAND_ACTIVITY_NOTEXIST("3","品牌不包含该活动"),
		ACTIVITY_TIMEEND("4","该活动已结束"),
		
		NEVER_DEPOSIT("5","用户从未存款"),
		DEPOSIT_ALREADYBONUS("6","用户最后一笔存款已领取红利"),
		
		NODEPOSIT_OR_NOLOSE("7","用户上月未存款或没有负盈利"),
		
		;
		public String value;
		public String desc;
		
		private ActivityCheckMessage(String value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
	
	/**
	 * 根据登录日志连接所有IP
	 * @param loglogins
	 * @return
	 */
	public static String ipLink(List<LogLogin> loglogins) {
		StringBuffer sbu = new StringBuffer();
		for (LogLogin logLogin : loglogins) {
			sbu.append(logLogin.getLoginip()+",");
		}
		return sbu.toString();
	}
}
