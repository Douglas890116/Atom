package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.DataHandleMaintenanceDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.DataHandleMaintenance;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.DataHandleMaintenanceService;
import com.maven.service.EnterpriseEmployeeService;

@Service
public class DataHandleMaintenanceServiceImpl extends BaseServiceImpl<DataHandleMaintenance> implements DataHandleMaintenanceService{

	@Autowired
	private DataHandleMaintenanceDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<DataHandleMaintenance> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<DataHandleMaintenance> getClazz() {
		return DataHandleMaintenance.class;
	}

	@Override
	public List<DataHandleMaintenance> selectBetRecord(Map<String, Object> parameter) throws Exception {
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
	public void addActivityBetRecord(DataHandleMaintenance activityBetRecord) throws Exception {
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
	public void updateActivityBetRecord(DataHandleMaintenance activityBetRecord) throws Exception {
		activityBetRecordDao.updateBetRecord(activityBetRecord);
	}
}
