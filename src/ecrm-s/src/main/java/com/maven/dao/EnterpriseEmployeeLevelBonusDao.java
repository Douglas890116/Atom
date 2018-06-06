package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseBannerInfo;
import com.maven.entity.EnterpriseEmployeeLevelBonus;

@Repository
public interface EnterpriseEmployeeLevelBonusDao extends BaseDao<EnterpriseEmployeeLevelBonus>{
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	void addBetRecord(EnterpriseEmployeeLevelBonus betrecord) throws Exception;
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	void updateBetRecord(EnterpriseEmployeeLevelBonus betrecord) throws Exception;
	
	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	List<EnterpriseEmployeeLevelBonus> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	
}
