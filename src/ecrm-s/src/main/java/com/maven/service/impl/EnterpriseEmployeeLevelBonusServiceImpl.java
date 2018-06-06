package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseBannerInfoDao;
import com.maven.dao.EnterpriseEmployeeLevelBonusDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseBannerInfo;
import com.maven.entity.EnterpriseEmployeeLevelBonus;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseBannerInfoService;
import com.maven.service.EnterpriseEmployeeLevelBonusService;
import com.maven.utility.ClassUtil;

@Service
public class EnterpriseEmployeeLevelBonusServiceImpl extends BaseServiceImpl<EnterpriseEmployeeLevelBonus> implements EnterpriseEmployeeLevelBonusService {

	@Autowired
	private EnterpriseEmployeeLevelBonusDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<EnterpriseEmployeeLevelBonus> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<EnterpriseEmployeeLevelBonus> getClazz() {
		return EnterpriseEmployeeLevelBonus.class;
	}

	@Override
	public List<EnterpriseEmployeeLevelBonus> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecord(parameter);
	}
	@Override
	public Map<String,BigDecimal> takeEmployeeLevelBonusMap(String employeelevelcode) throws Exception {
		Map<String,BigDecimal> result = new HashMap<String, BigDecimal>();
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("employeelevelcode", employeelevelcode);
		List<EnterpriseEmployeeLevelBonus> list = activityBetRecordDao.selectBetRecord(parameter);
		for (EnterpriseEmployeeLevelBonus enterpriseEmployeeLevelBonus : list) {
			result.put(enterpriseEmployeeLevelBonus.getGametype()+"_"+enterpriseEmployeeLevelBonus.getCategorytype(), enterpriseEmployeeLevelBonus.getBonus());
		}
		return result;
	}
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(EnterpriseEmployeeLevelBonus activityBetRecord) throws Exception {
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
	public void updateBetRecord(EnterpriseEmployeeLevelBonus activityBetRecord) throws Exception {
		activityBetRecordDao.updateBetRecord(activityBetRecord);
	}
	
}
