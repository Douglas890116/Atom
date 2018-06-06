package com.hy.pull.common.util.api;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口操作日志
 * 
 * 记录到redis库
 * 
 * @author Administrator
 *
 */
public class BaseInterfaceLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String lodId;//时间戳
	private String gameType;//游戏类型
	private String funCode;//方法代码
	private String params;//入参内容
	private String content;//错误消息内容
	private Date createDate;//日志时间
	
	public BaseInterfaceLog(String gameType, String funCode, String params, String content) {
		this.lodId = BaseInterfaceLogUtil.getLogid();
		this.gameType = gameType;
		this.funCode = funCode;
		this.params = params;
		this.content = content;
		this.createDate = new Date();
	}
	
	public BaseInterfaceLog(String lodId, String gameType, String funCode, String params, String content, Date createDate) {
		this.lodId = lodId;
		this.gameType = gameType;
		this.funCode = funCode;
		this.params = params;
		this.content = content;
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("lodId").append("=").append(this.lodId).append("; ");
		buffer.append("gameType").append("=").append(this.gameType).append("; ");
		buffer.append("funCode").append("=").append(this.funCode).append("; ");
		buffer.append("params").append("=").append(this.params).append("; ");
		buffer.append("content").append("=").append(this.content).append("; ");
		buffer.append("createDate").append("=").append(this.createDate.toLocaleString()).append("; ");
		return buffer.toString();
	}
	
	
	public String getLodId() {
		return lodId;
	}
	public void setLodId(String lodId) {
		this.lodId = lodId;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public String getFunCode() {
		return funCode;
	}
	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	
	
}
