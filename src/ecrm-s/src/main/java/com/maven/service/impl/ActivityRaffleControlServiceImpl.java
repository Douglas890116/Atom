package com.maven.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.ActivityRaffleControlDao;
import com.maven.dao.EnterpriseActivityPayDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityRaffleControl;
import com.maven.entity.ActivityRaffleRecord;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.UserLogs;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.service.ActivityRaffleControlService;
import com.maven.service.ActivityRaffleRecordService;
import com.maven.service.ActivityRaffleSigninService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.UserLogsService;
import com.maven.util.ActivityUtils.ActivityCheckMessage;
import com.maven.util.JSONUnit;
import com.maven.util.RandomString;
import com.maven.util.StringUtils;

@Service
public class ActivityRaffleControlServiceImpl 
	extends BaseServiceImpl<ActivityRaffleControl>  implements ActivityRaffleControlService{

	private final static String PRIZE_LIST = "PRIZE_LIST";//奖项列表（重要。其他参数均按此参数顺序配置）
	private final static String MONEY_RAND = "MONEY_RAND";//充值范围、中奖几率及奖项
	private final static String PRIZE_NUM = "PRIZE_NUM";//各奖项允许中奖次数等设置0无限制
	private final static String MONEY_PRE = "MONEY_PRE";//充值金额满足多少时可以获得抽奖机会
	private final static String IS_PRIZE_NUM_GET = "IS_PRIZE_NUM_GET";//获取抽奖次数是否可以以充值金额平均（1是 其他否）
	private final static String LSBS = "LSBS";//提款需流水倍数
	
	@Autowired
	private ActivityRaffleControlDao activityRaffleControlDao;
	
	@Autowired
	private ActivityRaffleSigninService activityRaffleSigninService;
	
	@Autowired
	private ActivityRaffleRecordService activityRaffleRecordService;
	
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;

	@Autowired
	private ActivityBetRecordDao activityBetRecordDao;
	
	@Autowired
	private EnterpriseActivityPayDao enterpriseActivityPayDao;
	@Autowired
	 private UserLogsService userLogsService;
	
	@Override
	public BaseDao<ActivityRaffleControl> baseDao() {
		return activityRaffleControlDao;
	}

	@Override
	public Class<ActivityRaffleControl> getClazz() {
		return ActivityRaffleControl.class;
	}
	
	@Override
	public int addRaffleRecord(ActivityRaffleControl __rafflerecord) throws Exception {
		return super.add(__rafflerecord);
	}
	

	private static double runRaffle(String __prizeOrOdds) {
		Map<String, Object> __object = JSONUnit.getMapFromJson(__prizeOrOdds);
		List<Double> __prizeList = new ArrayList<Double>();
		for (String __key : __object.keySet()) {
			double __odds = Double.parseDouble(String.valueOf(__object.get(__key)));
			if(__odds*1000>=1){
				int __ot = (int)(__odds*1000);
				for (int i=0;i<__ot;i++) {
					__prizeList.add(Double.parseDouble(__key));
				}
			}
		}
		for(int i=__prizeList.size();i<=1000;i++){
			__prizeList.add(0.0);
		}
		Collections.shuffle(__prizeList);
		int __size = new Random().nextInt(__prizeList.size());
		return __prizeList.get(__size);
	}

	/**
	 * 充值抽奖算法
	 * @param __prizeOrOdds 概率及奖品参数
	 * @return
	 */
	private static String runRaffleRecharge(String __prizeOrOdds) {
		Map<String, Object> __object = JSONUnit.getMapFromJson(__prizeOrOdds);
		List<String> __prizeList = new ArrayList<String>();
		for (String __key : __object.keySet()) {
			double __odds = Double.parseDouble(String.valueOf(__object.get(__key)));//得到概率
			if(__odds > 0 ) {
				int __ot = (int)(__odds*100);
				for (int i=0;i<__ot;i++) {
					__prizeList.add(__key);
					//System.out.println(__key);
				}
			}
		}
		for(int i=__prizeList.size();i<=100;i++){
			__prizeList.add("0");
		}
		Collections.shuffle(__prizeList);
		int __size = new Random().nextInt(__prizeList.size());
		return __prizeList.get(__size);
	}
	
	public static void main(String[] args) {
		//8:0.7,18:0.1,28:0.1,38:0.1,58:0,68:0,88:0,98:0,138:0,168:0,188:0
    	String __prizeOrodds = String.valueOf("8:0.1,18:0.1,28:0.1,38:0.1,Apple Phone 7: 0.1, 58:0,68:0,88:0,98:0,138:0,168:0,188:0");
    	for (int i = 0; i < 1; i++) {
    		String __prize = runRaffleRecharge("{"+__prizeOrodds+"}");
        	System.out.println(i+"==="+__prize);
		}
	}
	
	private static double runRaffleDay(String __prizeOrOdds) {
		Map<String, Object> __object = JSONUnit.getMapFromJson(__prizeOrOdds);
		List<String> __prizeList = new ArrayList<String>();
		for (String __key : __object.keySet()) {
			double __odds = Double.parseDouble(String.valueOf(__object.get(__key)));//得到概率
			if(__odds > 0 ) {
				int __ot = (int)(__odds*100);
				for (int i=0;i<__ot;i++) {
					__prizeList.add(__key);
					//System.out.println(__key);
				}
			}
		}
		for(int i=__prizeList.size();i<=100;i++){
			__prizeList.add("0");
		}
		Collections.shuffle(__prizeList);
		int __size = new Random().nextInt(__prizeList.size());
		return Double.valueOf(__prizeList.get(__size));
	}
	
	/**
	 * 每日充值享受抽奖（只负责根据剩余的抽奖次数完成抽奖逻辑。获得抽奖次数应在存款完毕后进行校验和更新次数）
	 * @param __employeecode
	 * @param __enterprisebrandcodeactivitycode
	 * @return
	 */
	@Override
	public Map<String,Object> cz_raffle_day(String __employeecode,int __enterprisebrandactivitycode, Map<String,Object> parames )throws Exception {
		// 
		String fingerprintcode = parames.get("fingerprintcode").toString();
		String loginip = parames.get("loginip").toString();
		
		Map<String,Object> __returnAument = new HashMap<String, Object>();
		
		ActivityRaffleControl __raffleControl = new ActivityRaffleControl();
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("employeecode", __employeecode);
		object.put("createdate", StringUtils.getDate());
		object.put("ecactivitycode", __enterprisebrandactivitycode);
		List<ActivityRaffleControl> listActivityRaffleControl = super.selectAll(object);
		__raffleControl = listActivityRaffleControl.size() > 0 ? listActivityRaffleControl.get(0) : null;
		
		
		// 检查充值是否满足抽奖条件
		if(__raffleControl == null) {
			__returnAument.put("status", "fail");
			__returnAument.put("message", "您今天没有获得抽奖机会哦！快去充值吧");
			__returnAument.put("isallowraffle", false);
			return __returnAument;
		}
		if(__raffleControl.getAvailabletimes() <= 0) {
			__returnAument.put("status", "fail");
			__returnAument.put("message", "您今天没有更多抽奖机会了！");
			__returnAument.put("isallowraffle", false);
			return __returnAument;
		}
		
		//二次校验是否同IP
		object = new HashMap<String, Object>();
		if(!fingerprintcode.equals("") && fingerprintcode.length() == 32) {
			object.put("fingerprintcode",  fingerprintcode);
		}
		object.put("loginip", loginip );
		object.put("createdate", StringUtils.getDate());
		object.put("ecactivitycode", __enterprisebrandactivitycode);
		if(activityRaffleRecordService.selectAllCount(object) > 0) {
			__returnAument.put("status", "fail");
			__returnAument.put("message", "相同IP不能重复抽奖！");
			__returnAument.put("isallowraffle", false);
			return __returnAument;
		}
		
		// 抽奖流程
		if(__raffleControl!=null&&__raffleControl.getAvailabletimes()>0){
			Calendar calendar = Calendar.getInstance();  
		    calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    // 是否超过有效期
		    if(__raffleControl.getDonatedate().before(calendar.getTime())){
		    	__returnAument.put("status", "fail");
				__returnAument.put("message", "抽奖失败，您的抽奖机会为"+new SimpleDateFormat("yyyy-MM-dd").format(__raffleControl.getDonatedate())+"日赠送,已超过有效时间");
				__returnAument.put("isallowraffle", false);
		    }else{
		    	Map<String,Object> __activityAgument = enterpriseBrandActivityService.selectActivityAgument(__enterprisebrandactivitycode);
		    	
		    	String REDBAG_RATE =String.valueOf(__activityAgument.get("REDBAG_RATE"));// 红包大小及概率（必填） 
				String LSBS =String.valueOf(__activityAgument.get("LSBS"));//流水倍数（可为0）
				String DAY_COUNT =String.valueOf(__activityAgument.get("DAY_COUNT"));//每日抽奖总人数（为0不限制）
				String START_MONEY =String.valueOf(__activityAgument.get("START_MONEY"));//最低充值金额（为0不限制）
				
				LSBS = com.maven.util.StringUtils.nullString(LSBS);
				if(LSBS.equals("")) {
					LSBS = "0";
				}
				
		    	//	获取实际参与抽奖的概率参数
		    	double __prize = runRaffleDay("{"+REDBAG_RATE+"}");
		    	
		    	if(__raffleControl.getAvailabletimes()-1<=0){
					__returnAument.put("isallowraffle", false);
				}else{
					__returnAument.put("isallowraffle", true);
				}
		    	
		    	//	没有抽到具体的奖项
		    	if(__prize == 0) {
		    		//更新抽奖次数
					activityRaffleControlDao.updateRaffleTime(new ActivityRaffleControl(__raffleControl.getRafflecontrolcode(),1,1));
					
	    			__returnAument.put("status", "fail");
	    			__returnAument.put("message", "您什么都没有抽到。要再接再厉哦！");
	    			return __returnAument;
		    	}
		    	
		    	
		    	
		    	// 得到奖项列表
		    	__returnAument.put("status", "success");
				__returnAument.put("message", "恭喜你,抽到了"+__prize+"元彩金！");
				__returnAument.put("prize", __prize);
				
				
				EnterpriseEmployee __employee = enterpriseEmployeeService.takeEmployeeByCode(__employeecode);
				//	抽奖记录
				activityRaffleRecordService.addRaffleRecord(
						new ActivityRaffleRecord(__employee.getEmployeecode(),__employee.getParentemployeecode(),__employee.getLoginaccount(), String.valueOf(__prize), fingerprintcode, loginip, __enterprisebrandactivitycode)
						);
				//	更新抽奖次数
				activityRaffleControlDao.updateRaffleTime(new ActivityRaffleControl(__raffleControl.getRafflecontrolcode(),1,1));
				//	写入帐变记录
				if(__prize > 0 ) {
					enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(), __employeecode, new BigDecimal(__prize), 
							new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc, Enum_moneyinouttype.进账), 
							"操作人:"+__employee.getLoginaccount()  + " 活动：每日充值享受抽奖");
				}
				//	增加打码记录（有流水倍数要求及获奖奖品是彩金时）
				double betMultiple = Double.valueOf(LSBS);
				EnterpriseBrandActivity brandActivity = null;
				if(betMultiple > 0 && __prize > 0 ) {
					brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(__enterprisebrandactivitycode);
					if (brandActivity == null){
						throw new LogicTransactionRollBackException(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
					}
					
					ActivityBetRecord betrecord = new ActivityBetRecord();
					betrecord.setEmployeecode(__employee.getEmployeecode());
					betrecord.setEcactivitycode(brandActivity.getEcactivitycode());
					betrecord.setMustbet( (Double.valueOf(__prize) + amount) * betMultiple);
					betrecord.setAlreadybet(0.0);
					betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
					betrecord.setCreatetime(new Date());
					betrecord.setLoginaccount(__employee.getLoginaccount());
					betrecord.setRecharge(Double.valueOf(__prize));//充值金额
					
					betrecord.setEnterprisecode(__employee.getEnterprisecode());//企业编码
					betrecord.setBrandcode(__employee.getBrandcode());//品牌编码
					betrecord.setParentemployeeaccount(__employee.getParentemployeeaccount());//上级账号
					betrecord.setParentemployeecode(__employee.getParentemployeecode());//上级编码
					activityBetRecordDao.addBetRecord(betrecord);
				}
				
				//增加活动红利支付审核记录
				if(__prize > 0 ) {
					String acname = null;
					if(brandActivity == null) {
						brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(__enterprisebrandactivitycode);
						EnterpriseActivityCustomization item = enterpriseActivityCustomizationService.selectByEnterprisebrandactivitycode(__enterprisebrandactivitycode+"");
						acname = item.getActivityname();
					}
					Date now = new Date();
					EnterpriseActivityPay activityPay = new EnterpriseActivityPay();
					activityPay.setBrandcode(__employee.getBrandcode());
					activityPay.setEmployeecode(__employee.getEmployeecode());
					activityPay.setEnterprisecode(__employee.getEnterprisecode());
					activityPay.setLoginaccount(__employee.getLoginaccount());
					activityPay.setParentemployeecode(__employee.getParentemployeecode());
					activityPay.setAcname(acname);//brandActivity.getActivityname()
					activityPay.setEcactivitycode(brandActivity.getEcactivitycode());
					activityPay.setAuditer("");
					activityPay.setAuditremark("活动：每日充值享受抽奖，自动发放");
					activityPay.setAudittime(now);
					activityPay.setCreatetime(now);
					activityPay.setPayer("");
					activityPay.setPaymoneyaudit(Double.valueOf(__prize));
					activityPay.setPaymoneyreal(Double.valueOf(__prize));
					activityPay.setPaystatus(EnterpriseActivityPay.Enum_paystatus.已支付.value);
					activityPay.setPaytyime(now);
					activityPay.setPaytype(EnterpriseActivityPay.Enum_paytype.自动发放.value);
					activityPay.setDesc("");
					activityPay.setLsbs(betMultiple+"");
					enterpriseActivityPayDao.addBetRecord(activityPay);
				}
				
				userLogsService.addActivityBetRecord(new UserLogs(__employee.getEnterprisecode(), __employee.getEmployeecode(), __employee.getLoginaccount(), 
						UserLogs.Enum_operatype.红利信息业务, "每日充值享受抽奖 获得"+__prize, null, null));
				
		    }
		} else {
			__returnAument.put("status", "fail");
			__returnAument.put("message", "您当前没有满足抽奖条件");
			__returnAument.put("isallowraffle", false);
		}
		
		return __returnAument;
	}
	
	/**
	 * 充值与抽奖
	 * @param __employeecode
	 * @param __enterprisebrandcodeactivitycode
	 * @return
	 */
	@Override
	public Map<String,Object> cz_raffle(String __employeecode,int __enterprisebrandactivitycode, Map<String,Object> parames )throws Exception {
		String fingerprintcode = String.valueOf(parames.get("fingerprintcode"));
		String loginip = String.valueOf(parames.get("loginip"));
		
		// 判断是抽奖还是签到
		ActivityRaffleControl __raffleControl = new ActivityRaffleControl();
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("employeecode", __employeecode);
		object.put("ecactivitycode", __enterprisebrandactivitycode);
		List<ActivityRaffleControl> listActivityRaffleControl = super.selectAll(object);
		__raffleControl = listActivityRaffleControl.size() > 0 ? listActivityRaffleControl.get(0) : null;
		
		Map<String,Object> __returnAument = new HashMap<String, Object>();
		
		
		// 检查充值是否满足抽奖条件
		if(__raffleControl == null || __raffleControl.getAvailabletimes() <= 0) {
			__raffleControl = checkActivityRaffleControl(__employeecode, __enterprisebrandactivitycode, parames);
		}
		
		// 抽奖流程
		if(__raffleControl!=null&&__raffleControl.getAvailabletimes()>0){
			Calendar calendar = Calendar.getInstance();  
		    calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    // 是否超过有效期
		    if(__raffleControl.getDonatedate().before(calendar.getTime())){
		    	__returnAument.put("status", "fail");
				__returnAument.put("message", "抽奖失败，您的抽奖机会为"+new SimpleDateFormat("yyyy-MM-dd").format(__raffleControl.getDonatedate())+"日赠送,已超过有效时间");
				__returnAument.put("isallowraffle", false);
		    }else{
		    	Map<String,Object> __activityAgument = enterpriseBrandActivityService.selectActivityAgument(__enterprisebrandactivitycode);
		    	//	获取实际参与抽奖的概率参数
		    	String __prizeOrodds = getPrizeOrder(__activityAgument, amount);
		    	
		    	String __prize = runRaffleRecharge(__prizeOrodds);
		    	
		    	if(__raffleControl.getAvailabletimes()-1<=0){
					__returnAument.put("isallowraffle", false);
				}else{
					__returnAument.put("isallowraffle", true);
				}
		    	
		    	//	没有抽到具体的奖项
		    	if(__prize.equals("0")) {
		    		//更新抽奖次数
					activityRaffleControlDao.updateRaffleTime(new ActivityRaffleControl(__raffleControl.getRafflecontrolcode(),1,1));
					
	    			__returnAument.put("status", "fail");
	    			__returnAument.put("message", "您什么都没有抽到。要再接再厉哦！");
	    			return __returnAument;
		    	}
		    	
		    	//	检查是否已有数量上限
		    	Map<String, Object> prize_num_map = JSONUnit.getMapFromJson("{"+String.valueOf(__activityAgument.get(PRIZE_NUM))+"}");
		    	int __prizenum = Integer.valueOf(String.valueOf(prize_num_map.get(__prize)));
		    	
		    	if(__prizenum > 0) {
		    		Map<String,Object> parameter = new HashMap<String, Object>(); 
		    		parameter.put("startreffletime", calendar.getTime());
		    		parameter.put("endreffletime", new Date());
		    		parameter.put("reffleprizes", __prize);
		    		parameter.put("employeecode", __employeecode);
		    		int count = activityRaffleRecordService.selectAllCount(parameter);
		    		
		    		if(count >= __prizenum) {//还没到达上限
		    			
		    			//更新抽奖次数
						activityRaffleControlDao.updateRaffleTime(new ActivityRaffleControl(__raffleControl.getRafflecontrolcode(),1,1));
						
		    			__returnAument.put("status", "fail");
		    			__returnAument.put("message", "您什么都没有抽到。要再接再厉哦！");
		    			return __returnAument;
		    		}
		    	}
		    	
		    	String __prize_str = "";
		    	if(StringUtils.isNumberFloat(__prize)) {
		    		__prize_str = __prize+"元彩金";
		    	}
		    	
		    	// 得到奖项列表
		    	List<String> prizeList = StringUtils.getListByString(__activityAgument.get(PRIZE_LIST).toString());
		    	
		    	__returnAument.put("status", "success");
				__returnAument.put("message", "恭喜你,抽到了"+__prize_str+"！");
				__returnAument.put("prize", __prize);
				__returnAument.put("prizeIndex", prizeList.indexOf(__prize));//奖品所在顺序位置，以后台参数配置顺序为准
				__returnAument.put("prizeList", __activityAgument.get(PRIZE_LIST).toString());//奖品列表
				
				
				EnterpriseEmployee __employee = enterpriseEmployeeService.takeEmployeeByCode(__employeecode);
				//	抽奖记录
				activityRaffleRecordService.addRaffleRecord(
						new ActivityRaffleRecord(__employee.getEmployeecode(),__employee.getParentemployeecode(),
						__employee.getLoginaccount(),String.valueOf(__prize), fingerprintcode, loginip, __enterprisebrandactivitycode));
				//	更新抽奖次数
				activityRaffleControlDao.updateRaffleTime(new ActivityRaffleControl(__raffleControl.getRafflecontrolcode(),1,1));
				//	写入帐变记录
				if(StringUtils.isNumberFloat(__prize)) {
					enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(), __employeecode, new BigDecimal(__prize), 
							new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc, Enum_moneyinouttype.进账), 
							"操作人:"+__employee.getLoginaccount()  + " 活动：存款抽奖");
				}
				//	增加打码记录（有流水倍数要求及获奖奖品是彩金时）
				double betMultiple = Double.valueOf(__activityAgument.get(LSBS).toString());
				EnterpriseBrandActivity brandActivity = null;
				if(betMultiple > 0 && StringUtils.isNumberFloat(__prize)) {
					brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(__enterprisebrandactivitycode);
					if (brandActivity == null){
						throw new LogicTransactionRollBackException(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
					}
					
					ActivityBetRecord betrecord = new ActivityBetRecord();
					betrecord.setEmployeecode(__employee.getEmployeecode());
					betrecord.setEcactivitycode(brandActivity.getEcactivitycode());
					betrecord.setMustbet( (Double.valueOf(__prize) + amount) * betMultiple);
					betrecord.setAlreadybet(0.0);
					betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
					betrecord.setCreatetime(new Date());
					betrecord.setLoginaccount(__employee.getLoginaccount());
					betrecord.setRecharge(Double.valueOf(__prize));//充值金额
					
					betrecord.setEnterprisecode(__employee.getEnterprisecode());//企业编码
					betrecord.setBrandcode(__employee.getBrandcode());//品牌编码
					betrecord.setParentemployeeaccount(__employee.getParentemployeeaccount());//上级账号
					betrecord.setParentemployeecode(__employee.getParentemployeecode());//上级编码
					activityBetRecordDao.addBetRecord(betrecord);
				}
				
				//增加活动红利支付审核记录
				if(StringUtils.isNumberFloat(__prize)) {
					if(brandActivity == null) {
						brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(__enterprisebrandactivitycode);
					}
					Date now = new Date();
					EnterpriseActivityPay activityPay = new EnterpriseActivityPay();
					activityPay.setBrandcode(__employee.getBrandcode());
					activityPay.setEmployeecode(__employee.getEmployeecode());
					activityPay.setEnterprisecode(__employee.getEnterprisecode());
					activityPay.setLoginaccount(__employee.getLoginaccount());
					activityPay.setParentemployeecode(__employee.getParentemployeecode());
					activityPay.setAcname(brandActivity.getActivityname());
					activityPay.setEcactivitycode(brandActivity.getEcactivitycode());
					activityPay.setAuditer("");
					activityPay.setAuditremark("活动：存款抽奖获得彩金，自动发放");
					activityPay.setAudittime(now);
					activityPay.setCreatetime(now);
					activityPay.setPayer("");
					activityPay.setPaymoneyaudit(Double.valueOf(__prize));
					activityPay.setPaymoneyreal(Double.valueOf(__prize));
					activityPay.setPaystatus(EnterpriseActivityPay.Enum_paystatus.已支付.value);
					activityPay.setPaytyime(now);
					activityPay.setPaytype(EnterpriseActivityPay.Enum_paytype.自动发放.value);
					activityPay.setDesc("");
					activityPay.setLsbs(betMultiple+"");
					enterpriseActivityPayDao.addBetRecord(activityPay);
				}
				
				userLogsService.addActivityBetRecord(new UserLogs(__employee.getEnterprisecode(), __employee.getEmployeecode(), __employee.getLoginaccount(), 
						UserLogs.Enum_operatype.红利信息业务, "充值抽奖获得"+__prize, null, null));
				
		    }
		} else {
			__returnAument.put("status", "fail");
			__returnAument.put("message", "您当前没有满足抽奖条件");
			__returnAument.put("isallowraffle", false);
		}
		
		return __returnAument;
	}
	
	
	private String getPrizeOrder(Map<String, Object> __activityAgument , double recharge) {
		String __prizeOrodds = String.valueOf(__activityAgument.get(MONEY_RAND));//根据充值金额大小决定抽奖概率
		Map<String, Object> __object = JSONUnit.getMapFromJson("{"+__prizeOrodds+"}");
		
		String min_key = "";
		List<String> list = new ArrayList<String>();
		for (String __key : __object.keySet()) {
			list.add(__key);
		}
		Collections.sort(list);
		min_key = list.get(0);
		
    	// 如果可以根据充值金额来平均抽奖的机会次数，则只取最小的抽奖概率比例，否则，取对应的
    	if(String.valueOf(__activityAgument.get(IS_PRIZE_NUM_GET)).equals("1")) {
    		
    		return __object.get(min_key).toString();
    		
    	} else {	//	不管充值多少都只算一次的
    		
    		for (String __key : __object.keySet()) {
    			double start = Double.valueOf(__key.split("-")[0]);
    			double end   = Double.valueOf(__key.split("-")[1]);
    			if(recharge >= start && recharge<= end) {
    				//在这个范围内
    				min_key = __key;
    				break;
    			}
    		}
    		return __object.get(min_key).toString();
    	}
	}
	private double amount = 0;
	private ActivityRaffleControl saveActivityRaffleControl (
			final String __employeecode, 
			final int __enterprisebrandactivitycode, 
			Map<String,Object> parames, 
			Date lastcreatetime, 
			ActivityRaffleControl raffleControl,
			EnterpriseEmployee employee) throws Exception {
		
		amount = 0;
		
		//查找今天为止的存款通过记录
		parames.put("employeecode", __employeecode);
		parames.put("orderdate_begin", lastcreatetime);
		parames.put("orderdate_end", new Date());
		parames.put("ordertype", Enum_ordertype.存款.value);
		parames.put("orderstatus", Enum_orderstatus.审核通过.value);
		List<TakeDepositRecord> list = takeDepositRecoredService.selectAll(parames);
		if(list == null || list.size() == 0) {
			return null;
		}
		//累加
		for (TakeDepositRecord takeDepositRecord : list) {
			amount += takeDepositRecord.getOrderamount().doubleValue();
		}
		
		
		/****查找该企业该活动的详细参数设置****/
		Map<String,Object> __activityAgument = enterpriseBrandActivityService.selectActivityAgument(__enterprisebrandactivitycode);
		
		/***************开始检查业务**************/
		if(amount >= Double.parseDouble(__activityAgument.get(MONEY_PRE).toString())) {//充值金额满足多少时可以获得抽奖机会
			int count = 1;
			if(__activityAgument.get(IS_PRIZE_NUM_GET).equals("1")) {//获取抽奖次数是否可以以充值金额平均（1是 其他否）
				count = (new Double(amount / Double.parseDouble(__activityAgument.get(MONEY_PRE).toString()))).intValue();
			}
			
			
			//新增可抽奖记录
			if(raffleControl == null) {
				raffleControl = new ActivityRaffleControl();
				raffleControl.setAvailabletimes(count);
				raffleControl.setDonatedate(new Date());
				raffleControl.setEmployeecode(employee.getEmployeecode());
				raffleControl.setParentemployeecode(employee.getParentemployeecode());
				raffleControl.setFinishtimes(0);
				raffleControl.setEcactivitycode(__enterprisebrandactivitycode);
				raffleControl.setLoginaccount(employee.getLoginaccount());
				super.add(raffleControl);
				
			} else {//修改 累加次数
				raffleControl.setAvailabletimes(raffleControl.getAvailabletimes() + count);
				raffleControl.setDonatedate(new Date());
				activityRaffleControlDao.updateActivityRaffleControl(raffleControl);
			}
			return raffleControl;
		} else {
			return null;
		}
	}
	
	private ActivityRaffleControl checkActivityRaffleControl(
			final String __employeecode, 
			final int __enterprisebrandactivitycode, 
			Map<String,Object> parames) throws Exception {
		
		EnterpriseEmployee employee = enterpriseEmployeeService.takeEmployeeByCode(parames.get("employeecode").toString());
		
		//先检查当日是否已经添加过
		ActivityRaffleControl raffleControl = null;
		Date lastcreatetime = null;
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("employeecode", employee.getEmployeecode());
		object.put("loginaccount", employee.getLoginaccount());
		object.put("ecactivitycode", __enterprisebrandactivitycode);
//		object.put("donatedate", new Date());//因为该表的主键是员工编号，即最多只能有一条记录，所以只能累加次数，不能累加记录
		List<ActivityRaffleControl> listActivityRaffleControl = super.selectAll(object);
		if(listActivityRaffleControl != null && listActivityRaffleControl.size() > 0) {
			raffleControl = listActivityRaffleControl.get(0);
			lastcreatetime = raffleControl.getDonatedate();
			
			return saveActivityRaffleControl(__employeecode, __enterprisebrandactivitycode, parames, lastcreatetime, raffleControl, employee);
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			
			return saveActivityRaffleControl(__employeecode, __enterprisebrandactivitycode, parames, calendar.getTime(), null, employee);
		}
		
	}
	
	
	
	@SuppressWarnings("serial")
	@Override
	public Map<String, Object> tc_raffle(final String __employeecode, final int __enterprisebrandactivitycode, Map<String,Object> parames) throws Exception{
		String fingerprintcode = String.valueOf(parames.get("fingerprintcode"));
		String loginip = String.valueOf(parames.get("loginip"));
		
		// 判断是抽奖还是签到
		ActivityRaffleControl __raffleControl = new ActivityRaffleControl();
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("employeecode", __employeecode);
		object.put("ecactivitycode", __enterprisebrandactivitycode);
		List<ActivityRaffleControl> listActivityRaffleControl = super.selectAll(object);
		__raffleControl = listActivityRaffleControl.size() > 0 ? listActivityRaffleControl.get(0) : null;
		
		Map<String,Object> __returnAument = new HashMap<String, Object>();
		// 抽奖流程
		if(__raffleControl!=null&&__raffleControl.getAvailabletimes()>0){
			Calendar calendar = Calendar.getInstance();  
		    calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    // 是否超过有效期
		    if(__raffleControl.getDonatedate().before(calendar.getTime())){
		    	__returnAument.put("status", "fail");
				__returnAument.put("message", "抽奖失败，您的抽奖机会为"+new SimpleDateFormat("yyyy-MM-dd").format(__raffleControl.getDonatedate())+"日赠送,已超过有效时间");
				__returnAument.put("isallowraffle", false);
		    }else{
		    	Map<String,Object> __activityAgument = enterpriseBrandActivityService.selectActivityAgument(__enterprisebrandactivitycode);
		    	
		    	String __prizeOrodds =String.valueOf(__activityAgument.get(ActivityRaffleControlService.JJJLYS));
		    	double __prize = runRaffle("{"+__prizeOrodds+"}");
		    	__returnAument.put("status", "success");
				__returnAument.put("message", "恭喜你,抽到了"+__prize+"元幸运彩金！");
				if(__raffleControl.getAvailabletimes()-1<=0){
					__returnAument.put("isallowraffle", false);
				}else{
					__returnAument.put("isallowraffle", true);
				}
				EnterpriseEmployee __employee = enterpriseEmployeeService.takeEmployeeByCode(__employeecode);
				//抽奖记录
				activityRaffleRecordService.addRaffleRecord(
						new ActivityRaffleRecord(__employee.getEmployeecode(),__employee.getParentemployeecode(),
								__employee.getLoginaccount(),String.valueOf(__prize), fingerprintcode, loginip, __enterprisebrandactivitycode));
				//更新抽奖次数
				activityRaffleControlDao.updateRaffleTime(new ActivityRaffleControl(__raffleControl.getRafflecontrolcode(),1,1));
				//写入帐变记录
				enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(), __employeecode, new BigDecimal(__prize), 
						new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc, Enum_moneyinouttype.进账), 
						"操作人:"+__employee.getLoginaccount() +" 活动：签到抽奖");
				
				
				//增加活动红利支付审核记录
				if(__prize > 0 ) {
					EnterpriseBrandActivity brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(__enterprisebrandactivitycode);
					Date now = new Date();
					EnterpriseActivityPay activityPay = new EnterpriseActivityPay();
					activityPay.setBrandcode(__employee.getBrandcode());
					activityPay.setEmployeecode(__employee.getEmployeecode());
					activityPay.setEnterprisecode(__employee.getEnterprisecode());
					activityPay.setLoginaccount(__employee.getLoginaccount());
					activityPay.setParentemployeecode(__employee.getParentemployeecode());
					activityPay.setAcname(brandActivity.getActivityname());
					activityPay.setEcactivitycode(brandActivity.getEcactivitycode());
					activityPay.setAuditer("");
					activityPay.setAuditremark("活动：签到抽奖获得彩金，自动发放");
					activityPay.setAudittime(now);
					activityPay.setCreatetime(now);
					activityPay.setPayer("");
					activityPay.setPaymoneyaudit(Double.valueOf(__prize));
					activityPay.setPaymoneyreal(Double.valueOf(__prize));
					activityPay.setPaystatus(EnterpriseActivityPay.Enum_paystatus.已支付.value);
					activityPay.setPaytyime(now);
					activityPay.setPaytype(EnterpriseActivityPay.Enum_paytype.自动发放.value);
					activityPay.setDesc("");
					activityPay.setLsbs("1");
					enterpriseActivityPayDao.addBetRecord(activityPay);
				}
				
				userLogsService.addActivityBetRecord(new UserLogs(__employee.getEnterprisecode(), __employee.getEmployeecode(), __employee.getLoginaccount(), 
						UserLogs.Enum_operatype.红利信息业务, "签到抽奖获得"+__prize, null, null));
				
		    }
		}else{		// 签到流程
			int signstate = activityRaffleSigninService.tc_raffleSignIn(new HashMap<String, Object>(){
			{
				this.put("employeecode", __employeecode);
				this.put("enterprisebrandactivitycode", __enterprisebrandactivitycode);
			}});
			if(signstate<0){
				__returnAument.put("status", "fail");
				__returnAument.put("message", "签到失败，请确认是否完成充值与流水，确认后请稍等几分钟");
				__returnAument.put("isallowraffle", false);
			}else if(signstate==0){
				__returnAument.put("status", "success");
				__returnAument.put("message", "签到成功");
				__returnAument.put("isallowraffle", false);
			}else if(signstate>0){
				__returnAument.put("status", "success");
				__returnAument.put("message", "您当前有 "+signstate+" 次抽奖机会");
				__returnAument.put("isallowraffle", true);
			}
		}
		return __returnAument;
	}

}
