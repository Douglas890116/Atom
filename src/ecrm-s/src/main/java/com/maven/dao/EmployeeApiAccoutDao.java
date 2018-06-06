package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EmployeeApiAccout;

@Repository
public interface EmployeeApiAccoutDao extends BaseDao<EmployeeApiAccout>{
	/**
	 * 关联游戏表查询用户游戏账号
	 * @param eaa
	 * @return
	 */
	List<EmployeeApiAccout> selectUnionGName(EmployeeApiAccout eaa);

	/**
	 * 禁用选中的一条或者多条数据
	 * @param String[] array
	 */
	void tc_disableSelectEmployee(String[] array) throws Exception;
	/**
	 * 启用选中的一条或者多条数据
	 * @param String[] array
	 */
	void tc_activateSelectEmployee(String[] array) throws Exception;
}
