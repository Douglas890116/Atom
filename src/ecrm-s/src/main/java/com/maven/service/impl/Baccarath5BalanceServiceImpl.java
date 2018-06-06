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
import com.maven.dao.Baccarath5BalanceDao;
import com.maven.dao.Baccarath5ExchangeDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.Baccarath5Balance;
import com.maven.entity.Baccarath5Exchange;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.Baccarath5BalanceService;
import com.maven.service.Baccarath5ExchangeService;
import com.maven.service.EnterpriseEmployeeService;

@Service
public class Baccarath5BalanceServiceImpl extends BaseServiceImpl<Baccarath5Balance> implements Baccarath5BalanceService {

	@Autowired
	private Baccarath5BalanceDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<Baccarath5Balance> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<Baccarath5Balance> getClazz() {
		return Baccarath5Balance.class;
	}

	@Override
	public List<Baccarath5Balance> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecord(parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCount(parameter);
	}
	
	@Override
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCountMoney(parameter);
	}
	
	/**
	 * 更新余额
	 * @param employeecode
	 * @param money 本次更新的余额（可能是正数，也可能是负数）
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateBalance(String employeecode, double money) throws Exception {
		
		int a = activityBetRecordDao.update("Baccarath5Balance.update", new Baccarath5Balance(employeecode, money));
		return a;
	}
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(Baccarath5Balance activityBetRecord) throws Exception {
		activityBetRecordDao.addBetRecord(activityBetRecord);
	}

}
