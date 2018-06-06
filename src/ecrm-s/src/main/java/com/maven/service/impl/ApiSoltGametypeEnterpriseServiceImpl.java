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
import com.maven.dao.ApiSoltGametypeEnterpriseDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.ApiSoltGametypeEnterprise;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.ApiSoltGametypeEnterpriseService;

@Service
public class ApiSoltGametypeEnterpriseServiceImpl extends BaseServiceImpl<ApiSoltGametypeEnterprise> implements ApiSoltGametypeEnterpriseService{

	@Autowired
	private ApiSoltGametypeEnterpriseDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<ApiSoltGametypeEnterprise> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<ApiSoltGametypeEnterprise> getClazz() {
		return ApiSoltGametypeEnterprise.class;
	}
	
	public List<ApiSoltGametypeEnterprise> selectTypes(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectTypes(parameter);
	}
	@Override
	public void deleteByEnterprisecode(String enterprisecode) throws Exception {
		activityBetRecordDao.deleteByEnterprisecode(enterprisecode);
	}
	
	@Override
	public List<ApiSoltGametypeEnterprise> selectAdd(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectAdd(parameter);
	}
	@Override
	public int selectAddCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectAddCount(parameter);
	}
	
	
	@Override
	public List<ApiSoltGametypeEnterprise> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecord(parameter);
	}
	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCount(parameter);
	}
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@Override
	public List<ApiSoltGametypeEnterprise> select(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.select(parameter);
	}

	
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(ApiSoltGametypeEnterprise activityBetRecord) throws Exception {
		activityBetRecordDao.addBetRecord(activityBetRecord);
	}

}
