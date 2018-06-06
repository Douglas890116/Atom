package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.DataHandleDao;
import com.maven.dao.DataHandleLogsDao;
import com.maven.dao.DataHandleMaintenanceDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.DataHandle;
import com.maven.entity.DataHandleLogs;
import com.maven.entity.DataHandleMaintenance;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.DataHandleLogsService;
import com.maven.service.DataHandleMaintenanceService;
import com.maven.service.DataHandleService;
import com.maven.service.EnterpriseEmployeeService;

@Service
public class DataHandleLogsServiceImpl extends BaseServiceImpl<DataHandleLogs> implements DataHandleLogsService{

	@Autowired
	private DataHandleLogsDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<DataHandleLogs> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<DataHandleLogs> getClazz() {
		return DataHandleLogs.class;
	}

	@Override
	public List<DataHandleLogs> selectBetRecord(Map<String, Object> parameter) throws Exception {
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
	public void addActivityBetRecord(DataHandleLogs activityBetRecord) throws Exception {
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
	public void updateActivityBetRecord(DataHandleLogs activityBetRecord) throws Exception {
		activityBetRecordDao.updateBetRecord(activityBetRecord);
	}
}
