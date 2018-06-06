package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.ApiTagXmlTimerDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.ApiTagXmlTimer;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.ApiTagXmlTimerService;
import com.maven.service.EnterpriseEmployeeService;

@Service
public class ApiTagXmlTimerServiceImpl extends BaseServiceImpl<ApiTagXmlTimer> implements ApiTagXmlTimerService{

	@Autowired
	private ApiTagXmlTimerDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<ApiTagXmlTimer> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<ApiTagXmlTimer> getClazz() {
		return ApiTagXmlTimer.class;
	}

	@Override
	public List<ApiTagXmlTimer> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecord(parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCount(parameter);
	}
	
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(ApiTagXmlTimer activityBetRecord) throws Exception {
		activityBetRecordDao.addBetRecord(activityBetRecord);
	}

	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateActivityBetRecord(ApiTagXmlTimer activityBetRecord) throws Exception {
		activityBetRecordDao.updateBetRecord(activityBetRecord);
	}
}
