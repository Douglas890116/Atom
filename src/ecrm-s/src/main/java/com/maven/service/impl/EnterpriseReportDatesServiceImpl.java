package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.dao.EnterpriseReportDatesDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseReportDates;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseReportDatesService;

@Service
public class EnterpriseReportDatesServiceImpl extends BaseServiceImpl<EnterpriseReportDates> implements EnterpriseReportDatesService{

	@Autowired
	private EnterpriseReportDatesDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<EnterpriseReportDates> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<EnterpriseReportDates> getClazz() {
		return EnterpriseReportDates.class;
	}

	@Override
	public List<EnterpriseReportDates> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecord(parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCount(parameter);
	}
	
	@Override
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCountMoney(parameter);
	}
	
	/**
	 * 用户存款排名报表查询
	 * @param paramObj
	 * @return
	 */
	public List<EnterpriseReportDates> call_userDepositTakeReport(Map<String, Object> paramObj)throws Exception {
		return activityBetRecordDao.userDepositTakeReport(paramObj);
	}
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(EnterpriseReportDates activityBetRecord) throws Exception {
		activityBetRecordDao.addBetRecord(activityBetRecord);
	}
	/**
	 * 批量新增
	 * @param betrecord
	 * @throws Exception
	 */
	@Override
	public void saveBatchRecord(List<EnterpriseReportDates> list) throws Exception {
		activityBetRecordDao.saveBatchRecord(list);
	}

}
