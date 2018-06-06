package com.maven.cache.factory;

import java.math.BigDecimal;

import com.maven.entity.WorkingFlowConfiguration;
import com.maven.entity.WorkingFlowConfiguration.Enum_flowtype;

public interface WorkingFlow {

	/**
	 * 获取第一个流程
	 * @param enterpeisecode 企业编码
	 * @param Enum_flowtype 工作流类型
	 * @param flowthreshold 充值金额
	 * @return
	 */
	public WorkingFlowConfiguration first(String enterpeisecode , Enum_flowtype flowtype, BigDecimal flowthreshold)  throws Exception;
	
	
	/**
	 * 获取下一个流程
	 * @param enterpeisecode 企业编码
	 * @param Enum_flowtype 工作流类型
	 * @param nowWorkingFlow 当前流程编码
	 * @param flowthreshold 充值金额
	 * @return
	 */
	public WorkingFlowConfiguration next(String enterpeisecode ,Enum_flowtype flowtype,  String flowcode , double flowthreshold)  throws Exception;
	
	/**
	 * 重新加载企业工作流
	 * @param enterpeisecode
	 */
	public void reload(String enterpeisecode) throws Exception;
	
	
}
