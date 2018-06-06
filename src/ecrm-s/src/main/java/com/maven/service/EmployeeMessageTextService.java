package com.maven.service;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EmployeeMessageText;

@Service
public interface EmployeeMessageTextService {
	
	/**
	 * 添加消息文本
	 * @param text
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int addMessageText(EmployeeMessageText text)throws Exception;

}
