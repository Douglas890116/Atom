package com.maven.entity;

import java.io.Serializable;
import java.util.Date;

public class EmployeeMessageText  implements Serializable {
	
	private static final long serialVersionUID = -5484688130394368236L;

	/** 消息内容编码 */
    private Integer messagetextcode;

    /** 消息内容 */
    private String content;

    /** 发送时间 */
    private Date sendtime;

    /** 数据状态 */
    private String datastatus;

    public Integer getMessagetextcode() {
        return messagetextcode;
    }

    public void setMessagetextcode(Integer messagetextcode) {
        this.messagetextcode = messagetextcode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public String getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(String datastatus) {
        this.datastatus = datastatus;
    }
}