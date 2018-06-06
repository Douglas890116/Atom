package com.maven.cache.factory.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maven.cache.factory.WorkingFlow;
import com.maven.constant.Constant;
import com.maven.dao.WorkingFlowConfigurationDao;
import com.maven.entity.WorkingFlowConfiguration;
import com.maven.entity.WorkingFlowConfiguration.Enum_flowtype;
import com.maven.utility.SpringContextHolder;

public class LocalWorkingFlow implements Serializable,WorkingFlow {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 工作流对象集合 
	 */
	private Map<String,List<WorkingFlowConfiguration>> workingflow;
	
	public LocalWorkingFlow(){
		initWorkingFlowMap();
	}
	
	private void initWorkingFlowMap(){
		try {
			workingflow = new HashMap<String, List<WorkingFlowConfiguration>>();
			WorkingFlowConfigurationDao ss = SpringContextHolder.getBean("workingFlowConfigurationDaoImpl");
			List<WorkingFlowConfiguration> list = ss.takeAll();
			for (WorkingFlowConfiguration w : list) {
				if(workingflow.get(w.getBrandcode())==null){
					workingflow.put(w.getBrandcode(), new ArrayList<WorkingFlowConfiguration>());
				}
				workingflow.get(w.getBrandcode()).add(w);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("缓存工作流配置失败");
		}
		
	}
	/**
	 * 获取第一个流程
	 * @param enterpeisecode 企业编码
	 * @param Enum_flowtype 工作流类型
	 * @param flowthreshold 充值金额
	 * @return
	 */
	public WorkingFlowConfiguration first(String brandcode , Enum_flowtype flowtype, BigDecimal flowthreshold)  throws Exception{
		List<WorkingFlowConfiguration> list = workingflow.get(brandcode);
		if(list==null || list.size() ==0 || flowtype== null) return new WorkingFlowConfiguration();
		for (WorkingFlowConfiguration w : list) {
			if(w.getFlowtype()!=flowtype.value) continue;
			if(w.getFlowthreshold()<flowthreshold.doubleValue()){
				return w;
			}
		}
		return new WorkingFlowConfiguration();
	}
	
	/**
	 * 获取下一个流程
	 * @param enterpeisecode 企业编码
	 * @param Enum_flowtype 工作流类型
	 * @param nowWorkingFlow 当前流程编码
	 * @param flowthreshold 充值金额
	 * @return
	 */
	public WorkingFlowConfiguration next(String enterpeisecode ,Enum_flowtype flowtype,  String flowcode , double flowthreshold)  throws Exception{
		List<WorkingFlowConfiguration> list = workingflow.get(enterpeisecode);
		if(list==null || list.size()==0 || flowcode==null) return null;
		boolean start = false;
		for (WorkingFlowConfiguration w : list) {
			if(w.getFlowtype()!=flowtype.value) continue;
			if(start && w.getFlowthreshold()<flowthreshold){
				return w;
			}
			if(w.getFlowcode().equals(flowcode)) start = true;
		}
		return null;
	}
	
	/**
	 * 重新加载企业工作流
	 * @param enterpeisecode
	 */
	public synchronized void reload(String enterpeisecode) throws Exception{
		WorkingFlowConfigurationDao ss = SpringContextHolder.getBean("workingFlowConfigurationDaoImpl");
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("enterpeisecode", enterpeisecode);
		object.put("status", Constant.BooleanByte.YES);
		List<WorkingFlowConfiguration> list = ss.selectAll("selectAll", object);
		workingflow.put(enterpeisecode,list);
	}

}
