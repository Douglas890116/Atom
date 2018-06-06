package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;

@Service
public interface EmployeeApiAccoutPasswordJobService {
	
	/**
	 * 创建游戏平台账户
	 * @param object 必须参数 "apicode","employeecode","gameaccount","gamepassword"
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int add(EmployeeApiAccoutPasswordJob object) throws Exception;
	
	/**
	 * 修改密码
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int updatePassword(EmployeeApiAccoutPasswordJob object) throws Exception ;
	
	@DataSource("slave")
	public List<EmployeeApiAccoutPasswordJob> findList(EmployeeApiAccoutPasswordJob object) throws Exception ;
}
