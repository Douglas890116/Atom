package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.DepositWithdralOrderDelegate;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.TakeDepositRecord;

@Service
public interface DepositWithdralOrderDelegateService {
	
	/**
	 * 添加委托
	 * @param object
	 */
	@DataSource("master")
	public void addDelegate(DepositWithdralOrderDelegate delegate) throws Exception;
	
	/**
	 * 修改委托
	 * @param delegate
	 * @throws Exception
	 */
	@DataSource("master")
	public void updateDelegate(DepositWithdralOrderDelegate delegate) throws Exception ;
	
	/**
	 * 委托处理(工作流审核,对审核中订单进行处理)-逐步审核
	 * @param pass
	 * @param takeDepositRecord
	 * @param delegate
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_handleDeletegate(TakeDepositRecord takeDepositRecord ,DepositWithdralOrderDelegate delegate,EnterpriseEmployee employee) throws Exception;
	
	/**
	 * 委托处理(第三方即时存款)-自动完成
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_autoHandleDeletegate(TakeDepositRecord depositeRecord,DepositWithdralOrderDelegate handles)throws Exception;
	
	/**
	 * 委托处理(拒绝待出款订单)
	 * @param depositeRecord
	 * @param handles
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_refuseDrawAmount(TakeDepositRecord depositeRecord,DepositWithdralOrderDelegate handles) throws Exception;
	
	/**
	 * 查询订单的委托处理详情
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<DepositWithdralOrderDelegate> takeOrdernumberDeletegate(DepositWithdralOrderDelegate object) throws Exception;
	
	
	
	

}
