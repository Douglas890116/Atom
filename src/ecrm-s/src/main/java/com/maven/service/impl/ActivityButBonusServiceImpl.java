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
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.ActivityButBonus;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.ActivityButBonusService;

@Service
public class ActivityButBonusServiceImpl extends BaseServiceImpl<ActivityButBonus> implements ActivityButBonusService {

	@Autowired
	private ActivityButBonusDao activityButBonusDao;
	
	@Override
	public BaseDao<ActivityButBonus> baseDao() {
		return activityButBonusDao;
	}

	@Override
	public Class<ActivityButBonus> getClazz() {
		return ActivityButBonus.class;
	}
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void saveActivityButBonus(ActivityButBonus obj) throws Exception  {
		activityButBonusDao.add("ActivityButBonus.add", obj);
	}

}
