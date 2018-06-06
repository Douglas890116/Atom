package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingAllDayDao;
import com.maven.dao.IntegralSettingDao;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay.Enum_status;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EmployeeMoneyChangeType.moneychangetypeCategory;
import com.maven.entity.IntegralSetting;
import com.maven.service.BettingAllDayService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.IntegralSettingService;
import com.maven.util.RandomString;

@Service
public class IntegralSettingServiceImpl extends BaseServiceImpl<IntegralSetting> implements IntegralSettingService{

	@Autowired
	private IntegralSettingDao integralSettingDao;
	
	
	@Override
	public BaseDao<IntegralSetting> baseDao() {
		// TODO Auto-generated method stub
		return integralSettingDao;
	}

	@Override
	public Class<IntegralSetting> getClazz() {
		// TODO Auto-generated method stub
		return IntegralSetting.class;
	}

	public int saveRecordBatch(List<IntegralSetting> list){
		return integralSettingDao.saveRecordBatch("IntegralSetting.saveRecordBatch", list);
	}
	
}
