package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.BettingAllAgentDao;
import com.maven.dao.BettingAllDayDao;
import com.maven.dao.BettingAllDayDao2;
import com.maven.dao.BettingAllGameWinloseDetailDao;
import com.maven.dao.BettingAllMemberDao;
import com.maven.dao.BettingPlanDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.BettingAllAgent;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllGameWinloseDetail;
import com.maven.entity.BettingAllMember;
import com.maven.entity.BettingAllPlan;
import com.maven.entity.BettingAllDay.Enum_status;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EmployeeMoneyChangeType.moneychangetypeCategory;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.BettingAllAgentService;
import com.maven.service.BettingAllDayService;
import com.maven.service.BettingAllDayService2;
import com.maven.service.BettingAllMemberService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.AttrCheckout;
import com.maven.util.RandomString;

@Service
public class BettingAllMemberServiceImpl extends BaseServiceImpl<BettingAllMember> implements BettingAllMemberService{

	@Autowired
	private BettingAllMemberDao BettingAllDayDao;
	
	@Override
	public BaseDao<BettingAllMember> baseDao() {
		return BettingAllDayDao;
	}

	@Override
	public Class<BettingAllMember> getClazz() {
		return BettingAllMember.class;
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
	 * 根据主键更新
	 * @param bettingAllDay
	 * @throws Exception
	 */
	@Override
	public void updateByPrimary(BettingAllMember bettingAllDay) throws Exception {
		this.BettingAllDayDao.updateByPrimary(bettingAllDay);
	}

	/**
	 * 分页查询
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public List<BettingAllMember> selectForPage(Map<String, Object> paramObj) throws Exception {
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
	public List<BettingAllMember> select(Map<String, Object> paramObj) throws Exception {
		return this.BettingAllDayDao.select(paramObj);
	}


}
