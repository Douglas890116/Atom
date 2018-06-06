package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseEmployeeRegeditLogDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseEmployeeRegeditLog;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseEmployeeRegeditLogService;

@Service
public class EnterpriseEmployeeRegeditLogServiceImpl extends BaseServiceImpl<EnterpriseEmployeeRegeditLog> implements EnterpriseEmployeeRegeditLogService{

	@Autowired
	private EnterpriseEmployeeRegeditLogDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<EnterpriseEmployeeRegeditLog> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<EnterpriseEmployeeRegeditLog> getClazz() {
		return EnterpriseEmployeeRegeditLog.class;
	}

	@Override
	public List<EnterpriseEmployeeRegeditLog> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecord(parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCount(parameter);
	}
	
	@Override
	public List<EnterpriseEmployeeRegeditLog> selectBetRecordGroup(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordGroup(parameter);
	}
	@Override
	public int selectBetRecordGroupCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordGroupCount(parameter);
	}
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(EnterpriseEmployeeRegeditLog activityBetRecord) throws Exception {
		activityBetRecordDao.addBetRecord(activityBetRecord);
	}


}
