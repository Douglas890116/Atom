package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseEmployeeRegeditLog;

@Repository
public interface EnterpriseEmployeeRegeditLogDao extends BaseDao<EnterpriseEmployeeRegeditLog>{
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	void addBetRecord(EnterpriseEmployeeRegeditLog betrecord) throws Exception;
	
	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	List<EnterpriseEmployeeRegeditLog> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查询需要打码的记录总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	int selectBetRecordCount(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 统计分析
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	List<EnterpriseEmployeeRegeditLog> selectBetRecordGroup(Map<String, Object> parameter) throws Exception;
	
	int selectBetRecordGroupCount(Map<String, Object> parameter) throws Exception;
}
