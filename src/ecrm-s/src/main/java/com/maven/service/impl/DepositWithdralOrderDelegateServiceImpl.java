package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.MoneyHelper;
import com.maven.base.dao.BaseDao;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.dao.DepositWithdralOrderDelegateDao;
import com.maven.entity.Baccarath5Balance;
import com.maven.entity.Baccarath5Rebate;
import com.maven.entity.Baccarath5Rebate.Enum_rebatestatus;
import com.maven.entity.DepositWithdralOrderDelegate;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.DepositWithdralOrderDelegate.Enum_auditresult;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeRelation;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseAgength5Level;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseInformation;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.PaymentType.Enum_PayType;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.entity.WorkingFlowConfiguration;
import com.maven.entity.WorkingFlowConfiguration.Enum_flowtype;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.OrderNewUtil;
import com.maven.game.enums.GameEnum;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.Baccarath5BalanceService;
import com.maven.service.Baccarath5RebateService;
import com.maven.service.DepositWithdralOrderDelegateService;
import com.maven.service.EnterpriseAgength5LevelService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseInformationService;
import com.maven.service.EnterpriseService;
import com.maven.service.EnterpriseThirdpartyPaymentService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.util.AttrCheckout;

@Service
public class DepositWithdralOrderDelegateServiceImpl extends BaseServiceImpl<DepositWithdralOrderDelegate> 
	implements DepositWithdralOrderDelegateService{

	@Autowired
	private DepositWithdralOrderDelegateDao depositWithdralOrderDelegateDao;
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
//	@Autowired
//	private EnterprisePaymentMethodConfigService enterprisePaymentMethodConfigService;
//	@Autowired
//	private EnterpriseThirdpartyPaymentAgumentService enterpriseThirdpartyPaymentAgumentService;
	@Autowired
	private EnterpriseInformationService enterpriseInformationService;
	@Autowired
	private EnterpriseThirdpartyPaymentService enterpriseThirdpartyPaymentService;
	@Autowired
	private ActivityBetRecordService activityBetRecordService;
	@Autowired
	private Baccarath5BalanceService baccarath5BalanceService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private Baccarath5RebateService baccarath5RebateService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private EnterpriseAgength5LevelService businessEmployeeLovelService;
	
	@Override
	public BaseDao<DepositWithdralOrderDelegate> baseDao() {
		return depositWithdralOrderDelegateDao;
	}

	@Override
	public Class<DepositWithdralOrderDelegate> getClazz() {
		return DepositWithdralOrderDelegate.class;
	}

	/**
	 * 添加工作流委托
	 */
	@Override
	public void addDelegate(DepositWithdralOrderDelegate delegate) throws Exception {
		AttrCheckout.checkout(delegate,false,new String[]{"ordernumber","begintime"},new String[]{"flowcode","processduration"});
		super.add(delegate);
	}
	
	public void updateDelegate(DepositWithdralOrderDelegate delegate) throws Exception {
		int status = super.update(AttrCheckout.checkout(delegate,false,
				new String[]{"delegatecode","ordernumber","assignedtocode","assignedtoaccount","endtime","auditresult","auditdesc"},new String[]{"flowcode","imgname"}));
		if(status!=1) throw  new LogicTransactionRollBackException("委托修改异常");
	}
	
	/**
	 * 存取款审核
	 */
	public void tc_handleDeletegate(TakeDepositRecord depositeRecord,DepositWithdralOrderDelegate handles,EnterpriseEmployee employee) throws Exception{
		AttrCheckout.checkout(depositeRecord, false, new String[]{"ordertype","ordernumber","enterprisecode","employeecode","orderamount","delegatecode"},new String[]{"flowcode"});
		AttrCheckout.checkout(handles, false, new String[]{"assignedtocode","assignedtoaccount","auditdesc","auditresult"},new String[]{"imgname"});
		
		Enum_ordertype ordertypee = Enum_ordertype.getOrdertype(depositeRecord.getOrdertype());
		Enum_flowtype flowtype = Enum_flowtype.getFlowType(ordertypee);
		if(flowtype ==null ) 
			throw  new ArgumentValidationException("订单类型错误,未找到对应的帐变或工作流类型");
		// 需要将已经完成的订单失败，所以不判断是否审核中
//		if(depositeRecord.getOrderstatus()!=(byte)Enum_orderstatus.审核中.value)
//			throw  new LogicTransactionRollBackException("订单状态异常,该订单不属于待审核订单");
		
		//更新委托审核信息
		handles.setEndtime(new Date());
		handles.setOrdernumber(depositeRecord.getOrdernumber());
		handles.setDelegatecode(depositeRecord.getDelegatecode());
		this.updateDelegate(handles);
		
		if(ordertypee.value == Enum_ordertype.存款.value){
			this.savingVerify(depositeRecord, handles, flowtype);
		}else if(ordertypee.value == Enum_ordertype.取款.value){
			this.takeVerify(depositeRecord, handles, employee, flowtype);
		}
	}
	
	/**
	 * 第三方支付回调成功后处理的逻辑
	 */
	@Override
	public void tc_autoHandleDeletegate(TakeDepositRecord depositeRecord,DepositWithdralOrderDelegate handles) throws Exception {
		AttrCheckout.checkout(depositeRecord, false, new String[]{"ordertype","ordernumber","enterprisecode","employeecode","orderamount","delegatecode"},new String[]{"flowcode"});
		AttrCheckout.checkout(handles, false, new String[]{"assignedtocode","assignedtoaccount","auditdesc","auditresult"});
		Enum_ordertype ordertypee = Enum_ordertype.getOrdertype(depositeRecord.getOrdertype());
		Enum_flowtype flowtype = Enum_flowtype.getFlowType(ordertypee);
		if(flowtype ==null ) 
			throw  new ArgumentValidationException("订单类型错误,未找到对应的帐变或工作流类型");
		if(depositeRecord.getOrderstatus()!=(byte)Enum_orderstatus.审核中.value)
			throw  new LogicTransactionRollBackException("订单状态异常,该订单不属于待审核订单");
		
		//更新委托审核信息
		handles.setEndtime(new Date());
		handles.setOrdernumber(depositeRecord.getOrdernumber());
		handles.setDelegatecode(depositeRecord.getDelegatecode());
		this.updateDelegate(handles);
		
		if(ordertypee.value == Enum_ordertype.存款.value){
			if(handles.getAuditresult()==Enum_auditresult.通过.value.byteValue()){
				if(depositeRecord.getOrderstatus()==(byte)Enum_orderstatus.审核通过.value)
					throw  new LogicTransactionRollBackException("订单已通过审核,请勿重复操作");
				//修改订单状态为完成
				TakeDepositRecord record = new TakeDepositRecord();
				record.setOrdernumber(depositeRecord.getOrdernumber());
				record.setOrderstatus((byte)TakeDepositRecord.Enum_orderstatus.审核通过.value);
				record.setHandleovertime(new Date());
				int status = takeDepositRecoredService.updateFlow(record);
				if(status!=1) throw  new LogicTransactionRollBackException("订单状态异常,非法操作");
				//将资金存入用户账户
				enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(depositeRecord.getOrdernumber(),depositeRecord.getEmployeecode(), depositeRecord.getOrderamount(),
						new EmployeeMoneyChangeType(Enum_moneychangetype.存款.value,Enum_moneychangetype.存款.desc,Enum_moneyinouttype.进账),"操作人:"+handles.getAssignedtoaccount());
				//更新企业账户余额
				updateEnterpriseAcoutBanlance(depositeRecord);
				
				
				/*************************处理信用代理营销系统业务**************************/
				updateEmployeeAgentLevel(depositeRecord);
				/*************************处理信用代理营销系统业务**************************/
			}
		}else if(ordertypee.value == Enum_ordertype.取款.value){
			
		}
	}
	
	@Override
	public void tc_refuseDrawAmount(TakeDepositRecord depositeRecord,DepositWithdralOrderDelegate handles) throws Exception {
		if(depositeRecord.getOrderstatus()!=(byte)Enum_orderstatus.待出款.value)
			throw  new LogicTransactionRollBackException("订单状态异常,该订单不属于待出款订单");
		if(depositeRecord.getOrderstatus()== ((byte)TakeDepositRecord.Enum_orderstatus.拒绝.value))
			throw  new LogicTransactionRollBackException("订单已被拒绝,请勿重复操作");
		//更新委托审核信息
		handles.setEndtime(new Date());
		handles.setOrdernumber(depositeRecord.getOrdernumber());
		handles.setDelegatecode(depositeRecord.getDelegatecode());
		handles.setAuditresult(Enum_auditresult.拒绝.value.byteValue());
		this.updateDelegate(handles);
		//修改订单状态为拒绝
		TakeDepositRecord record = new TakeDepositRecord();
		record.setOrdernumber(depositeRecord.getOrdernumber());
		record.setOrderstatus((byte)TakeDepositRecord.Enum_orderstatus.拒绝.value);
		takeDepositRecoredService.updateFlow(record);
		//返还扣除资金
		enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(
				depositeRecord.getOrdernumber(),depositeRecord.getEmployeecode(), 
				depositeRecord.getOrderamount().negate(),new EmployeeMoneyChangeType(Enum_moneychangetype.取款拒绝.value,
						Enum_moneychangetype.取款拒绝.desc,Enum_moneyinouttype.进账),"审核人:"+handles.getAssignedtoaccount());
	}
	
	/**
	 * 根据订单号查看订单委托处理详情
	 */
	@Override
	public List<DepositWithdralOrderDelegate> takeOrdernumberDeletegate(DepositWithdralOrderDelegate object) throws Exception {
		return super.select(AttrCheckout.checkout(object,true,new String[]{"ordernumber"}));
	}

	/**
	 * 取款审核
	 * @param depositeRecord
	 * @param handles
	 * @param employee
	 * @param flowtype
	 * @throws Exception
	 */
	private void takeVerify(TakeDepositRecord depositeRecord, DepositWithdralOrderDelegate handles,
			EnterpriseEmployee employee, Enum_flowtype flowtype) throws Exception {
		if(handles.getAuditresult()==Enum_auditresult.通过.value.byteValue()){
			if(depositeRecord.getOrderstatus()==(byte)Enum_orderstatus.审核通过.value)
				throw  new LogicTransactionRollBackException("订单已通过审核,请勿重复操作");
			//获取下一个流程
			WorkingFlowConfiguration wf = SystemCache.getInstance().getWorkflow().next(
					depositeRecord.getEnterprisecode(),flowtype,depositeRecord.getFlowcode(),depositeRecord.getOrderamount().negate().doubleValue());
			TakeDepositRecord record = new TakeDepositRecord();
			if(wf != null){
				//初始化流程委托
				DepositWithdralOrderDelegate ndelegate = new DepositWithdralOrderDelegate(depositeRecord.getOrdernumber(), wf.getFlowcode(),  wf.getHandletime()); 
				addDelegate(ndelegate);
				//更新订单工作流程、重置当前审核人
				record.setOrdernumber(depositeRecord.getOrdernumber());
				record.setFlowcode(wf.getFlowcode());
				record.setDelegatecode(ndelegate.getDelegatecode());
				record.setHandleemployee("");
				takeDepositRecoredService.updateFlow(record);
			}else{
//				orderPay(depositeRecord, employee);
				// 流程错误改为修改订单状态
				waitOutMoney(depositeRecord);
			}
		}else if(handles.getAuditresult()==Enum_auditresult.拒绝.value.byteValue()){
			if(depositeRecord.getOrderstatus()==
					((byte)TakeDepositRecord.Enum_orderstatus.拒绝.value))
				throw  new LogicTransactionRollBackException("订单已被拒绝,请勿重复操作");
			//修改订单状态为拒绝
			TakeDepositRecord record = new TakeDepositRecord();
			record.setOrdernumber(depositeRecord.getOrdernumber());
			record.setOrderstatus((byte)TakeDepositRecord.Enum_orderstatus.拒绝.value);
			takeDepositRecoredService.updateFlow(record);
			//返还扣除资金
			enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(
					depositeRecord.getOrdernumber(),depositeRecord.getEmployeecode(), 
					depositeRecord.getOrderamount().negate(),new EmployeeMoneyChangeType(Enum_moneychangetype.取款拒绝.value,
							Enum_moneychangetype.取款拒绝.desc,Enum_moneyinouttype.进账),"审核人:"+handles.getAssignedtoaccount());
		}else if(handles.getAuditresult()==Enum_auditresult.驳回.value.byteValue()){
			if(depositeRecord.getOrderstatus()==
					((byte)TakeDepositRecord.Enum_orderstatus.驳回.value))
				throw  new LogicTransactionRollBackException("订单已被驳回,请勿重复操作");
			//修改订单状态为驳回
			TakeDepositRecord record = new TakeDepositRecord();
			record.setOrdernumber(depositeRecord.getOrdernumber());
			record.setOrderstatus((byte)TakeDepositRecord.Enum_orderstatus.驳回.value);
			takeDepositRecoredService.updateFlow(record);
			//返还扣除资金
			enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(depositeRecord.getOrdernumber(),
					depositeRecord.getEmployeecode(), depositeRecord.getOrderamount().negate(),
					new EmployeeMoneyChangeType(Enum_moneychangetype.取款驳回.value,
							Enum_moneychangetype.取款驳回.desc,Enum_moneyinouttype.进账),"审核人:"+handles.getAssignedtoaccount());
		}else{
			throw  new ArgumentValidationException("操作类型错误,禁止执行资金操作");
		}
	}

//	private void orderPay(TakeDepositRecord __depositeRecord, EnterpriseEmployee employee)
//			throws Exception, ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
//			IntrospectionException, InvocationTargetException {
//		//读取出款配置
//		EnterprisePaymentMethodConfig paymentMethodConfig = enterprisePaymentMethodConfigService.queryByCode(employee.getEnterprisecode());
//		//判断出款方式
//		if(paymentMethodConfig == null || !Enum_paymentMethod.系统自动出款.value.equals(paymentMethodConfig.getWithdralway())){
//			waitOutMoney(__depositeRecord);
//			return;
//		}
//		//判断是否超过自动出款上限
//		if(__depositeRecord.getOrderamount().abs().compareTo(paymentMethodConfig.getUpperlimit()) == 1){
//			waitOutMoney(__depositeRecord);
//			return;
//		}
//		//判断是否有默认的快捷支付
//		Map<String,Object>  __agument = enterpriseThirdpartyPaymentAgumentService.takeEDefualtPayAgument(employee.getEnterprisecode());
//		String __thirdpartypaymenttypecode = String.valueOf(__agument.get("thirdpartypaymenttypecode"));
//		if(StringUtils.isBlank(__thirdpartypaymenttypecode)){
//			waitOutMoney(__depositeRecord);
//			return;
//		}
//		//判断第三方银行是否支持该银行卡出款
//		ThirdpartyPaymentBank __thirdpartyPaymentBank = SystemCache.getInstance().getThirdpartyPaymentBank(
//				__thirdpartypaymenttypecode, __depositeRecord.getEmployeepaymentbank());
//		if(__thirdpartyPaymentBank==null){
//			waitOutMoney(__depositeRecord);
//			return;
//		}
//		//进行支付
//		PayResult result = PayHelper.Pay(__agument, __depositeRecord, __thirdpartyPaymentBank);
//		//根据支付结果进行处理
//		if(result.getResult()){
//			//记录出款
//			DepositWithdralOrderDelegate ndelegate = new DepositWithdralOrderDelegate(__depositeRecord.getOrdernumber(), Constant.ATKGZLBM,  90);
//			ndelegate.setEndtime(new Date());
//			ndelegate.setAuditresult(Enum_auditresult.通过.value.byteValue());
//			ndelegate.setAuditdesc(result.getPaytype());
//			addDelegate(ndelegate);
//			//修改订单状态
//			TakeDepositRecord takeRecord = new TakeDepositRecord();
//			takeRecord.setOrdernumber(__depositeRecord.getOrdernumber());
//			takeRecord.setOrderstatus((byte)Enum_orderstatus.审核通过.value);
//			takeRecord.setDelegatecode(ndelegate.getDelegatecode());
//			takeRecord.setHandleovertime(new Date());
//			takeDepositRecoredService.tc_updateTakeDepositRecord(takeRecord);
//		}else{
//			waitOutMoney(__depositeRecord);
//		}
//	}


	/**
	 * 标识注单为待出款状态
	 * @param depositeRecord
	 * @throws Exception
	 */
	private void waitOutMoney(TakeDepositRecord depositeRecord) throws Exception {
		//写入待出款流程委托
		DepositWithdralOrderDelegate ndelegate = new DepositWithdralOrderDelegate(depositeRecord.getOrdernumber(), Constant.DCKGZLBM,  120);
		addDelegate(ndelegate);
		//更新订单工作流程、重置当前审核人
		TakeDepositRecord record = new TakeDepositRecord();
		record.setOrderstatus((byte)TakeDepositRecord.Enum_orderstatus.待出款.value);
		record.setOrdernumber(depositeRecord.getOrdernumber());
		record.setDelegatecode(ndelegate.getDelegatecode());
		//自定义的待出款流程编码
		record.setFlowcode(Constant.DCKGZLBM);
		record.setHandleemployee("");
		takeDepositRecoredService.updateFlow(record);
	}
	
	/**
	 * 存款审核流程
	 * @param depositeRecord
	 * @param handles
	 * @param flowtype
	 * @throws Exception
	 */
	private void savingVerify(TakeDepositRecord depositeRecord, DepositWithdralOrderDelegate handles,
			Enum_flowtype flowtype) throws Exception {
		if(handles.getAuditresult()==Enum_auditresult.通过.value.byteValue()){
			if(depositeRecord.getOrderstatus()==(byte)Enum_orderstatus.审核通过.value)
				throw  new LogicTransactionRollBackException("订单已通过审核,请勿重复操作");
			//获取下一个流程
			WorkingFlowConfiguration wf = SystemCache.getInstance().getWorkflow().next(
					depositeRecord.getEnterprisecode(),flowtype,depositeRecord.getFlowcode(),depositeRecord.getOrderamount().negate().doubleValue());
			if(wf != null){
				//初始化流程委托
				DepositWithdralOrderDelegate ndelegate = new DepositWithdralOrderDelegate(depositeRecord.getOrdernumber(), wf.getFlowcode(),  wf.getHandletime()); 
				addDelegate(ndelegate);
				//更新订单工作流程、重置当前审核人
				TakeDepositRecord record = new TakeDepositRecord();
				record.setOrdernumber(depositeRecord.getOrdernumber());
				record.setFlowcode(wf.getFlowcode());
				record.setDelegatecode(ndelegate.getDelegatecode());
				record.setHandleemployee("");
				takeDepositRecoredService.updateFlow(record);
			}else{
				//修改订单状态为完成
				TakeDepositRecord record = new TakeDepositRecord();
				record.setOrdernumber(depositeRecord.getOrdernumber());
				record.setOrderstatus((byte)TakeDepositRecord.Enum_orderstatus.审核通过.value);
				record.setHandleovertime(new Date());
				int status = takeDepositRecoredService.updateFlow(record);
				if(status!=1) throw  new LogicTransactionRollBackException("订单状态异常,非法操作");
				//将资金存入用户账户
				enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(depositeRecord.getOrdernumber(),depositeRecord.getEmployeecode(), depositeRecord.getOrderamount(),
						new EmployeeMoneyChangeType(Enum_moneychangetype.存款.value,Enum_moneychangetype.存款.desc,Enum_moneyinouttype.进账),"审核人:"+handles.getAssignedtoaccount()+"");
				//更新企业账户余额
				updateEnterpriseAcoutBanlance(depositeRecord);
				//TODO 手工存款加入默认打码
				this.activityBetRecordService.addDepositBetRecord(depositeRecord);
				
				// 充值返利活动处理
				takeDepositRecoredService.saveingVerify(depositeRecord, handles);
				
				
				/*************************处理信用代理营销系统业务**************************/
				updateEmployeeAgentLevel(depositeRecord);
				/*************************处理信用代理营销系统业务**************************/
				
			}
		}else if(handles.getAuditresult()==Enum_auditresult.拒绝.value.byteValue()){
			if(depositeRecord.getOrderstatus()==
					((byte)TakeDepositRecord.Enum_orderstatus.拒绝.value))
				throw  new LogicTransactionRollBackException("订单已被拒绝,请勿重复操作");
			//修改订单状态为拒绝
			TakeDepositRecord record = new TakeDepositRecord();
			record.setOrdernumber(depositeRecord.getOrdernumber());
			record.setOrderstatus((byte)TakeDepositRecord.Enum_orderstatus.拒绝.value);
			takeDepositRecoredService.updateFlow(record);
		}else if(handles.getAuditresult()==Enum_auditresult.驳回.value.byteValue()){
			if(depositeRecord.getOrderstatus()==
					((byte)TakeDepositRecord.Enum_orderstatus.驳回.value))
				throw  new LogicTransactionRollBackException("订单已被驳回,请勿重复操作");
			//修改订单状态为驳回
			TakeDepositRecord record = new TakeDepositRecord();
			record.setOrdernumber(depositeRecord.getOrdernumber());
			record.setOrderstatus((byte)TakeDepositRecord.Enum_orderstatus.驳回.value);
			takeDepositRecoredService.updateFlow(record);
		}else{
			throw  new ArgumentValidationException("操作类型错误,禁止执行资金操作");
		}
	}
	
	

	private void updateEnterpriseAcoutBanlance(TakeDepositRecord depositeRecord) throws Exception {
		if(depositeRecord.getPaymenttypecode().equals(Enum_PayType.银行卡转账.value)){
			EnterpriseInformation __ei = new EnterpriseInformation();
			__ei.setEnterprisecode(depositeRecord.getEnterprisecode());
			__ei.setOpenningaccount(depositeRecord.getEnterprisepaymentaccount());
			__ei.setCurrentbalance(depositeRecord.getOrderamount());
			enterpriseInformationService.updateCurrentBalance(__ei);
		}else if(depositeRecord.getPaymenttypecode().equals(Enum_PayType.第三方即时支付.value)){
			EnterpriseThirdpartyPayment __etpt = new EnterpriseThirdpartyPayment();
			__etpt.setEnterprisecode(depositeRecord.getEnterprisecode());
			__etpt.setEnterprisethirdpartycode(depositeRecord.getEnterprisepaymentaccount());
			__etpt.setCurrentbalance(depositeRecord.getOrderamount());
			enterpriseThirdpartyPaymentService.updateCurrentBalance(__etpt);
		}
	}

	/**
	 * 处理信用代理营销系统业务
	 * jason20170914
	 * 
	 * 1、应将代理充值金额从资金账户减去。（资金账户在该项目的作用是存储积分）；
	 * 2、应将代理充值金额按当前等比元宝比例进行元宝账户增加（代理如需使用元宝，需要在业务系统手动进行按比例将元宝兑换为积分。积分存储于资金账户表）；
	 * 3、应执行返现逻辑。记录返现记录，可能有多条。返现记录保存后标记为待发放状态；
	 * 4、应执行代理升级逻辑。可能升级的人员有多个。
	 * 
	 * @param depositeRecord
	 * @throws Exception
	 */
	private void updateEmployeeAgentLevel(TakeDepositRecord depositeRecord) throws Exception {
		//只处理存款订单状态为该种标志的
		Enum_ordertype ordertypee = Enum_ordertype.getOrdertype(depositeRecord.getOrdertype());
		String favourableid = depositeRecord.getFavourableid();
		if( ordertypee.value == Enum_ordertype.存款.value && favourableid != null && favourableid.toUpperCase().equals("CREDIT_AGENT_H5") ) {
			
			String patchno = OrderNewUtil.getPatchno();
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(depositeRecord.getEnterprisecode());
			double rate2 = enterprise.getRate2();//返回真钱和元宝之间的兑换比例
			
			//1=扣减账户
			enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(patchno, depositeRecord.getEmployeecode(), depositeRecord.getOrderamount().negate(),
					new EmployeeMoneyChangeType(Enum_moneychangetype.冲负.value,Enum_moneychangetype.冲负.desc,Enum_moneyinouttype.出账),"系统");
			
			//2=增加元宝余额
			double money = depositeRecord.getOrderamount().doubleValue() / rate2;
			baccarath5BalanceService.updateBalance(depositeRecord.getEmployeecode(), money);
			
			//3=增加返现记录（可能涉及到1-5人要返现）
			
			/******首先需要检查此代理当前处于第几级，然后找到全部的上级，依据条件进行返利（类似于六级分销）******/
			String ordernumber = depositeRecord.getOrdernumber();
			double orderamount = depositeRecord.getOrderamount().doubleValue();
			EnterpriseEmployee __ee = enterpriseEmployeeService.takeEmployeeByCode(depositeRecord.getEmployeecode());
			Baccarath5Balance __eeb = baccarath5BalanceService.selectByPrimaryKey(depositeRecord.getEmployeecode());
			String loginaccount = __ee.getLoginaccount();
			String enterprisecode = __ee.getEnterprisecode();
			String brandcode = __ee.getBrandcode();
			String currenlevel = __eeb.getLevelcode().toString();
			List<Baccarath5Rebate> __list = new ArrayList<Baccarath5Rebate>();
			Map<String, EnterpriseAgength5Level> mapLevels = getMapLevels(enterprisecode);
			
			
			
			String upperlevers = enterpriseEmployeeService.call_UfnTakeRecursionUpperLevel(depositeRecord.getEmployeecode());
			List<EnterpriseEmployee> __listTeamAgent = enterpriseEmployeeService.findEmployeeByCodes(upperlevers.split(","));
			int count = 0;
			for (EnterpriseEmployee item : __listTeamAgent) {
				if(item.getEmployeecode().equals(__ee.getEmployeecode())) {
					continue;
				}
				if(__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)||
						__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)) {
					continue;
				}
				//先写死，最多返现七级
				if(count >= 7) {
					break;
				}
				
				//查等级和对应的返现比例
				Baccarath5Balance __eeb_ = baccarath5BalanceService.selectByPrimaryKey(item.getEmployeecode());
				if(__eeb_ == null) {
					continue;
				}
				String employeecurrenlevel = __eeb_.getLevelcode().toString();
				EnterpriseAgength5Level level = mapLevels.get(employeecurrenlevel);
				double rebate  = level.getUpperlevelRate().doubleValue();
				double rebate2 = level.getUpperlevelRate2().doubleValue();
				System.out.println("返现调试：代理"+__eeb_.getEmployeecode()+" 当前等级"+employeecurrenlevel+" 直线上级返现比例是："+rebate+" 间接上级返现比例是："+rebate2);
				double rebatemoney = MoneyHelper.moneyFormatDouble(orderamount * rebate);
				
				__list.add(new Baccarath5Rebate(
						patchno, ordernumber, loginaccount, money, orderamount, currenlevel, 
						item.getEmployeecode(), item.getParentemployeecode(), enterprisecode, brandcode, employeecurrenlevel, rebate, rebatemoney, Enum_rebatestatus.待处理.value, null));
				
				count ++;
			}
			baccarath5RebateService.saveRecordBatch(__list);
			
			//4=处理升级逻辑（可能有多人升级）
			//可参考VIP等级制度表的设计
		} 
		
		/**************HY现金网需要自动上分******************/
		else if( ordertypee.value == Enum_ordertype.存款.value && favourableid != null && favourableid.toUpperCase().equals("HY_XJW_H5") ) {
			
			EmployeeApiAccout ea = SystemCache.getInstance().getEmployeeGameAccount(depositeRecord.getEmployeecode(), GameEnum.Enum_GameType.环球.gametype);
			// 进行游戏上分
			new APIServiceNew(depositeRecord.getEnterprisecode()).upIntegralGame(new BigDecimal(0), depositeRecord.getOrderamount().intValue(), GameEnum.Enum_GameType.环球.gametype, ea, depositeRecord.getOrdernumber());
			
		}
	}

	private Map<String, EnterpriseAgength5Level> getMapLevels(String enterprisecode) {
		Map<String, EnterpriseAgength5Level> result = new HashMap<String, EnterpriseAgength5Level>();
		try {
			Map<String, Object> paramsObj = new HashMap<String, Object>();
			paramsObj.put("enterprisecode", enterprisecode);
			List<EnterpriseAgength5Level> __listLevel = businessEmployeeLovelService.takelevelQuery(paramsObj);
			for (EnterpriseAgength5Level data : __listLevel) {
				result.put(data.getOrd().toString(), data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
