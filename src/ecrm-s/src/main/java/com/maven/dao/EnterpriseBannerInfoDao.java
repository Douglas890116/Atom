package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseBannerInfo;

@Repository
public interface EnterpriseBannerInfoDao extends BaseDao<EnterpriseBannerInfo>{
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	void addBetRecord(EnterpriseBannerInfo betrecord) throws Exception;
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	void updateBetRecord(EnterpriseBannerInfo betrecord) throws Exception;
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	void deleteBetRecord(List<String> list) throws Exception;
	
	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	List<EnterpriseBannerInfo> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查询需要打码的记录总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	int selectBetRecordCount(Map<String, Object> parameter) throws Exception;
	
}
