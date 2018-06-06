package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseInformationQrcodeDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseInformationQrcode;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseInformationQrcodeService;

@Service
public class EnterpriseInformationQrcodeServiceImpl extends BaseServiceImpl<EnterpriseInformationQrcode> implements EnterpriseInformationQrcodeService{

	@Autowired
	private EnterpriseInformationQrcodeDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<EnterpriseInformationQrcode> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<EnterpriseInformationQrcode> getClazz() {
		return EnterpriseInformationQrcode.class;
	}

	@Override
	public List<EnterpriseInformationQrcode> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecord(parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCount(parameter);
	}
	
	@Override
	public void addBetRecord(EnterpriseInformationQrcode activityBetRecord) throws Exception {
		activityBetRecordDao.addBetRecord(activityBetRecord);
	}

	@Override
	public void updateBetRecord(EnterpriseInformationQrcode activityBetRecord) throws Exception {
		activityBetRecordDao.updateBetRecord(activityBetRecord);
	}
	@Override
	public void deleteBetRecord(Integer lsh) throws Exception {
		activityBetRecordDao.deleteBetRecord(lsh);
	}
	
	public void deleteBetRecord(List<String> list) throws Exception {
		activityBetRecordDao.deleteBetRecord(list);
	}
	

}
