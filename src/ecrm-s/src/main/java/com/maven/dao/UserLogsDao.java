package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.UserLogs;

@Repository
public interface UserLogsDao extends BaseDao<UserLogs>{
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	void addBetRecord(UserLogs betrecord) throws Exception;
	/**
	 * 批量增加打码记录
	 * @param betRecordList
	 * @throws Exception
	 */
	void batchAddBetRecord(List<UserLogs> betRecordList) throws Exception;
	
	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	List<UserLogs> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查询需要打码的记录总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	int selectBetRecordCount(Map<String, Object> parameter) throws Exception;
	
}
