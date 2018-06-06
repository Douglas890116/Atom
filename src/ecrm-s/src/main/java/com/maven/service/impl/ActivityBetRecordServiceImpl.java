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
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseEmployeeService;

@Service
public class ActivityBetRecordServiceImpl extends BaseServiceImpl<ActivityBetRecord> implements ActivityBetRecordService{

	/*@Autowired
	private TakeDepositRecoredDao takeDepositRecoredDao;
	
	@Autowired
	private EnterpriseActivityCustomizationSettingService enterpriseActivityCustomizationSettingService;
	@Autowired
	private LogLoginDao logLoginDao;
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	@Autowired
	private EnterpriseEmployeeInformationDao enterpriseEmployeeInformationDao;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;*/
	@Autowired
	private ActivityBetRecordDao activityBetRecordDao;
	@Autowired
	private EnterpriseOperatingBrandDao enterpriseOperatingBrandDao;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	
	@Override
	public BaseDao<ActivityBetRecord> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<ActivityBetRecord> getClazz() {
		return ActivityBetRecord.class;
	}

	@Override
	public List<ActivityBetRecord> selectBetRecord(Map<String, Object> parameter) throws Exception {
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
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(ActivityBetRecord activityBetRecord) throws Exception {
		activityBetRecordDao.addBetRecord(activityBetRecord);
	}
	
	/**
	 * 按条件删除
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	public void deleteByConditions(Map<String, Object> parameter) throws Exception {
		activityBetRecordDao.deleteByConditions(parameter);
	}

	@Override
	public void addDepositBetRecord(TakeDepositRecord depositrecord) throws Exception {
		EnterpriseOperatingBrand branddefualtchip = enterpriseOperatingBrandDao.selectByPrimaryKey("EnterpriseOperatingBrand.selectByPrimaryKey", depositrecord.getBrandcode());
		BigDecimal chip = branddefualtchip==null?ActivityBetRecord.ifnodefaultchip:branddefualtchip.getDefualtchip();
		
		
		
		ActivityBetRecord addrecord = new ActivityBetRecord();
		BigDecimal bet = depositrecord.getOrderamount().multiply(chip);
		addrecord.setMustbet(bet.doubleValue());
		addrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
		addrecord.setEcactivitycode(Enum_ecactivitycode.存款流水.value);
		addrecord.setCreatetime(new Date());
		addrecord.setEmployeecode(depositrecord.getEmployeecode());
		addrecord.setLoginaccount(depositrecord.getLoginaccount());
		addrecord.setRecharge(depositrecord.getOrderamount().doubleValue());//充值金额
		
		EnterpriseEmployee enterpriseEmployee = enterpriseEmployeeService.takeEmployeeByCode(depositrecord.getEmployeecode());
		addrecord.setEnterprisecode(enterpriseEmployee.getEnterprisecode());//企业编码
		addrecord.setBrandcode(enterpriseEmployee.getBrandcode());//品牌编码
		addrecord.setParentemployeeaccount(enterpriseEmployee.getParentemployeeaccount());//上级账号
		addrecord.setParentemployeecode(enterpriseEmployee.getParentemployeecode());//上级编码
		this.activityBetRecordDao.addBetRecord(addrecord);
	}

}
