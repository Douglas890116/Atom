package com.maven.entity;

import java.io.Serializable;

public class EmployeeMessage  implements Serializable {

	private static final long serialVersionUID = 3220974632468957017L;

	/** 消息编码 */
	private Integer messagecode;
	
	/** 企业编码*/
	private String enterprisecode;

	/** 品牌编码 */
    private String brandcode;

    /** 发送者编码 */
    private String sendemployeecode;

    /** 发送者账号 */
    private String sendemployeeaccount;

    /** 消息内容编码 */
    private Integer messagetextcode;

    /** 接收者编码 */
    private String acceptemployeecode;

    /** 接收者账号 */
    private String acceptaccount;

    /** 消息类型 */
    private String messagetype;
    
    /** 回复编码 */
    private Integer replaycode;

    /** 阅读状态 */
    private String readstatus;
    
    /* 消息内容对象 */
    private EmployeeMessageText text;
    
    /* 加密字段 */
    private String sign;
    
    public enum Enum_messagetype{
    	系统消息(1,"系统消息"),
    	代理消息(2,"代理消息");
    	public int value;
    	public String desc;
    	private Enum_messagetype(int _value,String _desc){
    		this.value = _value;
    		this.desc = _desc;
    	}
    }
    
    public enum Enum_acceptemployeecode{
    	所有成员(1,"所有成员");
    	public int value;
    	public String desc;
    	private Enum_acceptemployeecode(int _value,String _desc){
    		this.value = _value;
    		this.desc = _desc;
    	}
    }
    
    public enum Enum_readstatus{
    	未阅读(1,"未阅读"),
    	已阅读(2,"已阅读");
    	public int value;
    	public String desc;
    	private Enum_readstatus(int _value,String _desc){
    		this.value = _value;
    		this.desc = _desc;
    	}
    }

    public Integer getMessagecode() {
        return messagecode;
    }

    public void setMessagecode(Integer messagecode) {
        this.messagecode = messagecode;
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

    public String getSendemployeecode() {
        return sendemployeecode;
    }

    public void setSendemployeecode(String sendemployeecode) {
        this.sendemployeecode = sendemployeecode;
    }

    public String getSendemployeeaccount() {
        return sendemployeeaccount;
    }

    public void setSendemployeeaccount(String sendemployeeaccount) {
        this.sendemployeeaccount = sendemployeeaccount;
    }

    public Integer getMessagetextcode() {
        return messagetextcode;
    }

    public void setMessagetextcode(Integer messagetextcode) {
        this.messagetextcode = messagetextcode;
    }

    public String getAcceptemployeecode() {
        return acceptemployeecode;
    }

    public void setAcceptemployeecode(String acceptemployeecode) {
        this.acceptemployeecode = acceptemployeecode;
    }

    public String getAcceptaccount() {
        return acceptaccount;
    }

    public void setAcceptaccount(String acceptaccount) {
        this.acceptaccount = acceptaccount;
    }

    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

    public Integer getReplaycode() {
		return replaycode;
	}

	public void setReplaycode(Integer replaycode) {
		this.replaycode = replaycode;
	}

	public String getReadstatus() {
        return readstatus;
    }

    public void setReadstatus(String readstatus) {
        this.readstatus = readstatus;
    }

	public EmployeeMessageText getText() {
		return text;
	}

	public void setText(EmployeeMessageText text) {
		this.text = text;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}