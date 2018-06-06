package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.dao.FavourableDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.Favourable;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.FavourableService;
import com.maven.util.StringUtils;

@Service
public class FavourableServiceImpl extends BaseServiceImpl<Favourable> implements FavourableService{

	@Autowired
	private FavourableDao activityBetRecordDao;
	
	
	
	@Override
	public BaseDao<Favourable> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<Favourable> getClazz() {
		return Favourable.class;
	}

	@Override
	public List<Favourable> selectBetRecord(Map<String, Object> parameter) throws Exception {
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
	public void addActivityBetRecord(Favourable activityBetRecord) throws Exception {
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
	public void updateActivityBetRecord(Favourable activityBetRecord) throws Exception {
		activityBetRecordDao.updateBetRecord(activityBetRecord);
		
		
		
		
	}

}
