package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessageText;

@Service
public interface EmployeeMessageService extends BaseServcie<EmployeeMessage>{
	
	
	/**
	 * 发送消息
	 * @param message 消息
	 * @param text 消息内容
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_sendMessage(List<EmployeeMessage> message,EmployeeMessageText text)throws Exception;
	
	/**
	 * 删除消息
	 * @param messagecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_delMessage(Integer messagecode)throws Exception;
	
	/**
	 * 修改消息的阅读状态
	 * @param messagecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_updateMStatus(Integer messagecode)throws Exception;
}
