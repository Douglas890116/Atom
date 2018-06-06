package com.maven.dao;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EmployeeAvailable;

@Repository
public interface EmployeeAvailableDao extends BaseDao<EmployeeAvailable>{
	
	/**
	 * 新增一条用户有效时间记录
	 * @return
	 */
	int insertEmployeeAvailable(EmployeeAvailable ea);
	
	/**
	 * 删除一条用户有效时间记录
	 * @return
	 */
	int deleteEmployeeAvailable(String employeecode);
	
}
