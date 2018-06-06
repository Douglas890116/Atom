package com.maven.service;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EmployeeAvailable;

@Service
public interface EmployeeAvailableService {
	
	/**
	 * 新增一条用户有效时间数据
	 * @param ea
	 * @return
	 */
	@DataSource("master")
	boolean insertEmployeeAvailable(EmployeeAvailable ea);
	
	/**
	 * 删除一条用户有效时间数据
	 * @param employeecode
	 * @return
	 */
	@DataSource("master")
	boolean deleteEmployeeAvailable(String employeecode);
}
