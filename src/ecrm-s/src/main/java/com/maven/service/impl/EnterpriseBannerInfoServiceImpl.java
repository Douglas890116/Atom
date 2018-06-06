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
import com.maven.dao.EnterpriseBannerInfoDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseBannerInfo;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseBannerInfoService;
import com.maven.utility.ClassUtil;

@Service
public class EnterpriseBannerInfoServiceImpl extends BaseServiceImpl<EnterpriseBannerInfo> implements EnterpriseBannerInfoService {

	@Autowired
	private EnterpriseBannerInfoDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<EnterpriseBannerInfo> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<EnterpriseBannerInfo> getClazz() {
		return EnterpriseBannerInfo.class;
	}

	@Override
	public List<EnterpriseBannerInfo> selectBetRecord(Map<String, Object> parameter) throws Exception {
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
	public void addActivityBetRecord(EnterpriseBannerInfo activityBetRecord) throws Exception {
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
	public void updateBetRecord(EnterpriseBannerInfo activityBetRecord) throws Exception {
		activityBetRecordDao.updateBetRecord(activityBetRecord);
	}
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	public void deleteBetRecord(List<String> list) throws Exception {
		activityBetRecordDao.deleteBetRecord(list);
	}
}
