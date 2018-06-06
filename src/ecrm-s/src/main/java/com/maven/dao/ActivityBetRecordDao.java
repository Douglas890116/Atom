package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityBetRecord;

@Repository
public interface ActivityBetRecordDao extends BaseDao<ActivityBetRecord>{
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	void addBetRecord(ActivityBetRecord betrecord) throws Exception;
	
	/**
	 * 按条件删除
	 * @param betrecord
	 * @throws Exception
	 */
	void deleteByConditions(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	List<ActivityBetRecord> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查询需要打码的记录总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	int selectBetRecordCount(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查询需要打码的记录总数之金额统计
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception;
}
