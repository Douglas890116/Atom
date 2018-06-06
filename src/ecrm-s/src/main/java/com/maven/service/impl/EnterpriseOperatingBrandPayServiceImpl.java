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
import com.maven.dao.ActivityButBonusDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.dao.EnterpriseOperatingBrandPayDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.ActivityButBonus;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseOperatingBrandPay;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.ActivityButBonusService;
import com.maven.service.EnterpriseOperatingBrandPayService;

@Service
public class EnterpriseOperatingBrandPayServiceImpl extends BaseServiceImpl<EnterpriseOperatingBrandPay> implements EnterpriseOperatingBrandPayService {

	@Autowired
	private EnterpriseOperatingBrandPayDao enterpriseOperatingBrandPayDao;
	
	@Override
	public BaseDao<EnterpriseOperatingBrandPay> baseDao() {
		return enterpriseOperatingBrandPayDao;
	}

	@Override
	public Class<EnterpriseOperatingBrandPay> getClazz() {
		return EnterpriseOperatingBrandPay.class;
	}
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void saveEnterpriseOperatingBrandPay(EnterpriseOperatingBrandPay obj) throws Exception  {
		enterpriseOperatingBrandPayDao.saveEnterpriseOperatingBrandPay(obj);
	}

	/**
	 * 修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void updateEnterpriseOperatingBrandPay(EnterpriseOperatingBrandPay obj) throws Exception  {
		enterpriseOperatingBrandPayDao.updateEnterpriseOperatingBrandPay(obj);
	}
	
	/**
	 * 删除
	 */
	public void deleteSelect(String[] array) throws Exception {
		enterpriseOperatingBrandPayDao.deleteSelect(array);
	}
}
