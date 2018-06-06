package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.MoneyHelper;
import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.Baccarath5RebateDao;
import com.maven.dao.BettingAllAgentDao;
import com.maven.dao.BettingAllDayDao;
import com.maven.dao.BettingAllDayDao2;
import com.maven.dao.BettingAllGameWinloseDetailDao;
import com.maven.dao.BettingAllMemberDao;
import com.maven.dao.BettingPlanDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.Baccarath5Rebate;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllGameWinloseDetail;
import com.maven.entity.BettingAllMember;
import com.maven.entity.BettingAllPlan;
import com.maven.entity.BettingAllDay.Enum_status;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeRelation;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EmployeeMoneyChangeType.moneychangetypeCategory;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.ReportDailyAgent;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.BettingAllAgent;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.Baccarath5RebateService;
import com.maven.service.BettingAllDayService;
import com.maven.service.BettingAllDayService2;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.AttrCheckout;
import com.maven.util.RandomString;

@Service
public class Baccarath5RebateServiceImpl extends BaseServiceImpl<Baccarath5Rebate> implements Baccarath5RebateService{

	private static List<String> ALL_AGENTTYPE = new ArrayList<String>(){{this.add(EnterpriseEmployeeType.Type.代理.value);this.add(EnterpriseEmployeeType.Type.信用代理.value);}};
	
	@Autowired
	private Baccarath5RebateDao BettingAllDayDao;
	
	@Override
	public BaseDao<Baccarath5Rebate> baseDao() {
		return BettingAllDayDao;
	}

	@Override
	public Class<Baccarath5Rebate> getClazz() {
		return Baccarath5Rebate.class;
	}

	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return BettingAllDayDao.takeRecordCountMoney(object);
	}
	
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	@Override
	public int saveRecordBatch(List<Baccarath5Rebate> list) {
		return BettingAllDayDao.saveRecordBatch(list);
	}
	
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 * @throws Exception
	 */
	@Override
	public void updateByPrimary(Baccarath5Rebate bettingAllDay) throws Exception {
		this.BettingAllDayDao.updateByPrimary(bettingAllDay);
	}

	/**
	 * 分页查询
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public List<Baccarath5Rebate> selectForPage(Map<String, Object> paramObj) throws Exception {
		return this.BettingAllDayDao.selectForPage(paramObj);
	}
	
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public int selectForPageCount(Map<String, Object> paramObj) throws Exception {
		return this.BettingAllDayDao.selectForPageCount(paramObj);
	}

	/**
	 * 根据参数查询
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public List<Baccarath5Rebate> select(Map<String, Object> paramObj) throws Exception {
		return this.BettingAllDayDao.select(paramObj);
	}

	

}
