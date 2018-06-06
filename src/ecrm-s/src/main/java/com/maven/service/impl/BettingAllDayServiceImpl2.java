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
import com.maven.dao.BettingAllAgentDao;
import com.maven.dao.BettingAllDayDao;
import com.maven.dao.BettingAllDayDao2;
import com.maven.dao.BettingAllGameWinloseDetailDao;
import com.maven.dao.BettingAllMemberDao;
import com.maven.dao.BettingPlanDao;
import com.maven.entity.ActivityBetRecord;
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
import com.maven.service.BettingAllDayService;
import com.maven.service.BettingAllDayService2;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.AttrCheckout;
import com.maven.util.RandomString;

@Service
public class BettingAllDayServiceImpl2 extends BaseServiceImpl<BettingAllDay2> implements BettingAllDayService2{

	private static List<String> ALL_AGENTTYPE = new ArrayList<String>(){{this.add(EnterpriseEmployeeType.Type.代理.value);this.add(EnterpriseEmployeeType.Type.信用代理.value);}};
	
	@Autowired
	private BettingAllDayDao2 BettingAllDayDao;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private BettingAllGameWinloseDetailDao allGameWinloseDetailDao;
	@Autowired
	private BettingPlanDao bettingPlanDao;
	@Autowired
	private BettingAllMemberDao bettingAllMemberDao;
	@Autowired
	private BettingAllAgentDao bettingAllAgentDao;
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	
	@Override
	public BaseDao<BettingAllDay2> baseDao() {
		return BettingAllDayDao;
	}

	@Override
	public Class<BettingAllDay2> getClazz() {
		return BettingAllDay2.class;
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
	public void updateByPrimary(BettingAllDay2 bettingAllDay) throws Exception {
		this.BettingAllDayDao.updateByPrimary(bettingAllDay);
	}

	/**
	 * 分页查询
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public List<BettingAllDay2> selectForPage(Map<String, Object> paramObj) throws Exception {
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
	public List<BettingAllDay2> select(Map<String, Object> paramObj) throws Exception {
		return this.BettingAllDayDao.select(paramObj);
	}

	/**
	 * 生成计划
	 * @param bettingAllDay
	 * @throws Exception
	 */
	@Override
	public int updateDoPlan(String planDate,String newPatchNo) throws Exception {
		
		/*******************1= 先汇总所有明细数据到汇总表*******************/
		Map<String, Object> paramObj = new HashMap<String, Object>();
		paramObj.put("bettime", planDate);
		List<BettingAllGameWinloseDetail> list = allGameWinloseDetailDao.selectGroup(paramObj);
		Map<String, EnterpriseEmployee> mapUser = new HashMap<String, EnterpriseEmployee>();
		
		
		Date addTime = new Date();
		EnterpriseEmployee __ee = null;
		List<BettingAllDay2> ___listData = new ArrayList<BettingAllDay2>();
		String enterprisecode, brandcode, employeecode, parentemployeecode = null;
		
		for (BettingAllGameWinloseDetail data : list) {
			
			//将投注额、输赢额、有效投注额全为0的数据排除掉（GG扑克有很多这种没用的数据）
			if(data.getBetmoney() == 0 && data.getNetmoney() == 0 && data.getValidbet() == 0) {
				//
				continue;
			} else {
				
				employeecode = data.getEmployeecode();
				if(mapUser.containsKey(employeecode)) {
					__ee = mapUser.get(employeecode);
				} else {
					__ee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
				}
				enterprisecode = __ee.getEnterprisecode();
				brandcode = __ee.getBrandcode();
				parentemployeecode = __ee.getParentemployeecode();
				
				___listData.add(new BettingAllDay2(enterprisecode, brandcode, employeecode, parentemployeecode, 
						__ee.getLoginaccount(), data.getGametype(), data.getGamebigtype(), null, Integer.valueOf(planDate), 
						data.getBetmoney(),data.getNetmoney(), data.getValidbet(), addTime, newPatchNo));
				
			}
			
		}
		if(___listData.size() > 0) {
			BettingAllDayDao.saveRecordBatch(___listData);
		}
		System.out.println("updateDoPlan=生成汇总记录数"+___listData.size());
		
		
		/*******************2= 将汇总的明细数据标记批次号*******************/
		paramObj.clear();
		paramObj.put("bettime", planDate);
		paramObj.put("patchno", newPatchNo);
		int count = allGameWinloseDetailDao.updateByPatchno(paramObj);
		System.out.println("updateDoPlan=回写单号数"+count);
		
		
		/*******************3= 保存支付计划
		double totalBetmoney = 0, totalValidMoney = 0, totalNetMoney = 0;
		int totalCount = 0;
		List<BettingAllPlan> ___listAddPlan = new ArrayList<BettingAllPlan>();
		List<BettingAllGameWinloseDetail> __list2 = allGameWinloseDetailDao.selectGroup2(params);
		for (BettingAllGameWinloseDetail data : __list2) {
			totalBetmoney += data.getBetmoney();
			totalValidMoney += data.getValidbet();
			totalNetMoney += data.getNetmoney();
			totalCount += 1;
			
			___listAddPlan.add(new BettingAllPlan(patchNo, data.getEnterprisecode(), totalCount, totalBetmoney,
				totalValidMoney, totalNetMoney, Integer.valueOf(betday), operater, addTime, BettingAllPlan.Enum_status.已汇总.value));
		}
		if(___listAddPlan.size() > 0) {
			bettingPlanDao.saveRecordBatch(___listAddPlan);
		}
		*******************/
		return 0;
	}
	
	/**
	 * 取消计划（已汇总）
	 * @param bettingAllDay
	 * @throws Exception
	 */
	public int updateCancelPlan(BettingAllPlan plan, String operater) throws Exception {
		
		/*******************1= 先删除汇总数据表*******************/
		BettingAllDay2 param = new BettingAllDay2();
		param.setEnterprisecode( plan.getEnterprisecode());
		param.setPatchno( plan.getPatchno());
		BettingAllDayDao.deletePatchno(param);
		
		
		/*******************2= 重置该计划对应的明细记录*******************/
		BettingAllGameWinloseDetail params = new BettingAllGameWinloseDetail();
		params.setEnterprisecode( plan.getEnterprisecode());
		params.setPatchno( plan.getPatchno());
		allGameWinloseDetailDao.updateByPatchnoCancel(params);
		
		
		/*******************3= 删除（只是已汇总的数据）*******************/
		plan.setStatus(BettingAllPlan.Enum_status.已汇总.value);//
		bettingPlanDao.deleteByPatchnoCancel(plan);
		
		return 0;
	}
	
	/**
	 * 财务核准计划
	 * @param bettingAllDay
	 * @throws Exception
	 */
	public int updateDoPlanCaiwu(BettingAllPlan plan, String operater) throws Exception {
		
		//源数据
		Map<String, Object> paramObj = new HashMap<String, Object>();
		paramObj.put("enterprisecode", plan.getEnterprisecode());
		paramObj.put("patchno", plan.getPatchno());
		List<BettingAllDay2> list = BettingAllDayDao.selectForPage(paramObj);
		
		Date createtime = new Date();
		
		/*******************1= 生成会员洗码数据*******************/
		//先查会员对应的洗码比例
		List<String> set_epcode = new ArrayList<String>();
		for (BettingAllDay2 bad : list) {
			if (!set_epcode.contains(bad.getEmployeecode())) {
				set_epcode.add(bad.getEmployeecode());
			}
		}
		if (set_epcode.isEmpty()) {
			System.out.println("企业"+plan.getEnterprisecode()+"投注用户为空");
		} else {
			
			Map<String, Map<String, BigDecimal>> egbmap = employeeGamecataloyBonusService.findGameBonus(set_epcode); //根据所有用户查询用户打码比例
			List<BettingAllMember> __listBettingAllMember = new ArrayList<BettingAllMember>();
			for (BettingAllDay2 data : list) {
				
				String enterprisecode = data.getEnterprisecode();
				String brandcode = data.getBrandcode();
				String employeecode = data.getEmployeecode();
				String parentemployeecode = data.getParentemployeecode();
				String loginaccount = data.getUserName();
				String gametype = data.getGamePlatform();
				String gamebigtype = data.getGameBigType();
				Integer betday = data.getBetDay();
				Double betmoney = data.getBetMoney();
				Double netmoney = data.getNetMoney();
				Double validmoney = data.getValidMoney();
				String patchno = data.getPatchno();
				double rate = 0;
				double amount = 0;
				
				Map<String, BigDecimal> ratios = egbmap.get(employeecode);
				if(ratios != null) {
					rate = ratios.get(data.getGamePlatform()+data.getGameBigType()).doubleValue();
					rate = MoneyHelper.moneyFormatDouble4(rate);
				} 
				//有效投注额*比例
				amount = MoneyHelper.moneyFormatDouble(validmoney * rate);
				
				__listBettingAllMember.add(
						new BettingAllMember(enterprisecode, brandcode, employeecode, parentemployeecode,
						loginaccount, gametype, gamebigtype, betday, betmoney, netmoney,
						validmoney, createtime, patchno, rate, amount, BettingAllMember.Enum_status.已生成.value));
			}
			if(__listBettingAllMember.size() > 0) {
				bettingAllMemberDao.saveRecordBatch(__listBettingAllMember);
			}
		}
		
		
		
		/*******************2= 生成代理洗码数据*******************/
		//先查所有代理的洗码
		List<String> agentId = new ArrayList<String>(); //获取所有代理code
		for (BettingAllDay2 data : list) {
			if (!agentId.contains(data.getParentemployeecode())) {
				agentId.add(data.getParentemployeecode());
			}
		}
		if (agentId.isEmpty()) {
			System.out.println("企业"+plan.getEnterprisecode()+"下无代理团队有投注");
		} else {
			
			Map<String, Map<String, BigDecimal>> egbmap = employeeGamecataloyBonusService.findGameBonus(agentId); //根据所有用户查询用户打码比例
			Map<String, BettingAllAgent> insertMap = new HashMap<String, BettingAllAgent>();
			for (String employeecode : agentId) {
				EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
				List<EmployeeRelation> teamList = enterpriseEmployeeService.call_uspTakeTeamAgent(ee.getEmployeecode());//获得该代理的所有会员
				if (teamList == null || teamList.isEmpty()) {
					continue;
				}
				List<String> now_agent_id = new ArrayList<String>();
				for (EmployeeRelation er : teamList) {
					now_agent_id.add(er.getEmployeecode());
				}
				
				paramObj.clear();
				paramObj.put("enterprisecode", plan.getEnterprisecode());
				paramObj.put("patchno", plan.getPatchno());
				paramObj.put("parentemployeecodes", now_agent_id);
				List<BettingAllDay2> bads = BettingAllDayDao.select(paramObj);//获得该代理所有会员的投注情况
				for (BettingAllDay2 data : bads) {
					String nowkey = ee.getEmployeecode()+data.getGamePlatform()+data.getGameBigType();
					Map<String, BigDecimal> ratios = egbmap.get(ee.getEmployeecode());
					String enterprisecode = data.getEnterprisecode();
					String brandcode = data.getBrandcode();
					String parentemployeecode = data.getParentemployeecode();
					String loginaccount = data.getUserName();
					String gametype = data.getGamePlatform();
					String gamebigtype = data.getGameBigType();
					Integer betday = data.getBetDay();
					Double betmoney = data.getBetMoney();
					Double netmoney = data.getNetMoney();
					Double validmoney = data.getValidMoney();
					String patchno = data.getPatchno();
					double rate = 0;
					double amount = 0;
					
					if(ratios != null) {
						rate = ratios.get(data.getGamePlatform()+data.getGameBigType()).doubleValue();
						rate = MoneyHelper.moneyFormatDouble4(rate);
					} 
					//有效投注额*比例
					amount = MoneyHelper.moneyFormatDouble(validmoney * rate);
					
					BettingAllAgent rda;
					if (insertMap.containsKey(nowkey)) {//累加
						rda = insertMap.get(nowkey);
						rda.setBetmoney(rda.getBetmoney() + betmoney);
						rda.setNetmoney(rda.getNetmoney() + netmoney);
						rda.setValidmoney(rda.getValidmoney() + validmoney);
						rda.setAmount(rda.getAmount() + amount);
					} else {
						rda = new BettingAllAgent(enterprisecode, brandcode, employeecode, parentemployeecode,
								loginaccount, gametype, gamebigtype, betday, betmoney, netmoney,
								validmoney, createtime, patchno, rate, amount, BettingAllAgent.Enum_status.已支付.value);
						insertMap.put(nowkey, rda);
					}
				}
			}
			
			List<BettingAllAgent> __listBettingAllAgent = new ArrayList<BettingAllAgent>(insertMap.values());
			if (__listBettingAllAgent != null && __listBettingAllAgent.size() > 0) {
				bettingAllAgentDao.saveRecordBatch(__listBettingAllAgent);
			}
			
		}
		
		
		/*******************3= 更新计划状态*******************/
		plan.setStatus(BettingAllPlan.Enum_status.财务已核准.value);//
		bettingPlanDao.updateByPatchnoCancel(plan);
		
		return 0;
	}
	/**
	 * 取消计划（已核准）
	 * @param bettingAllDay
	 * @throws Exception
	 */
	public int updateCancelPlanCaiwu(BettingAllPlan plan, String operater) throws Exception {
		
		/*******************1= 先删除汇总数据表*******************/
		BettingAllDay2 param = new BettingAllDay2();
		param.setEnterprisecode( plan.getEnterprisecode());
		param.setPatchno( plan.getPatchno());
		BettingAllDayDao.deletePatchno(param);
		
		
		/*******************2= 重置该计划对应的明细记录*******************/
		BettingAllGameWinloseDetail params = new BettingAllGameWinloseDetail();
		params.setEnterprisecode( plan.getEnterprisecode());
		params.setPatchno( plan.getPatchno());
		allGameWinloseDetailDao.updateByPatchnoCancel(params);
		
		/*******************3= 更新计划状态*******************/
		plan.setStatus(BettingAllPlan.Enum_status.已汇总.value);//
		bettingPlanDao.updateByPatchnoCancel(plan);
		
		return 0;
	}

}
