package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseReportDatesDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseReportDates;
import com.maven.entity.TakeDepositRecord;

@Repository
public class EnterpriseReportDatesDaoImpl extends BaseDaoImpl<EnterpriseReportDates> implements EnterpriseReportDatesDao{

	@Override
	public void addBetRecord(EnterpriseReportDates betrecord) throws Exception {
		getSqlSession().insert("EnterpriseReportDates.insertSelective", betrecord);
	}
	
	/**
	 * 批量新增
	 * @param betrecord
	 * @throws Exception
	 */
	@Override
	public void saveBatchRecord(List<EnterpriseReportDates> list) throws Exception {
		getSqlSession().insert("EnterpriseReportDates.saveRecordBatch", list);
	}

	@Override
	public List<EnterpriseReportDates> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("EnterpriseReportDates.selectAll", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("EnterpriseReportDates.selectAllCount", parameter);
	}

	@Override
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("EnterpriseReportDates.takeRecordCountMoney", parameter);
	}
	
	/**
	 * 存储过程,总计-取款稽查
	 */
	public List<EnterpriseReportDates> userDepositTakeReport(Map<String, Object> paramObj) throws Exception {
		return getSqlSession().selectList("EnterpriseReportDates.userDepositTakeReport", paramObj);
	}
}
