package com.maven.cache.factory.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maven.cache.RemoteCache;
import com.maven.cache.SystemCache;
import com.maven.cache.factory.WorkingFlow;
import com.maven.config.SpringContextUtil;
import com.maven.constant.Constant;
import com.maven.entity.WorkingFlowConfiguration;
import com.maven.entity.WorkingFlowConfiguration.Enum_flowtype;
import com.maven.logger.TLogger;
import com.maven.service.WorkingFlowConfigurationService;
import com.maven.util.ObjectSerialize;
import com.maven.utility.SpringContextHolder;

import redis.clients.jedis.Jedis;

public class RedisWorkingFlow implements Serializable,WorkingFlow{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 工作流对象集合 
	 */
	private final String ENTERPRISE_WORKINGFLOW = "ENTERPRISE_WORKINGFLOW_CACHE";
	
	public RedisWorkingFlow(){
		initWorkingFlowMap();
	}
	
	private void initWorkingFlowMap(){
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			jedis.del(this.ENTERPRISE_WORKINGFLOW.getBytes());
			Map<String,List<WorkingFlowConfiguration>> workingflow = new HashMap<String, List<WorkingFlowConfiguration>>();
			WorkingFlowConfigurationService ss = SpringContextHolder.getBean("workingFlowConfigurationServiceImpl");
			WorkingFlowConfiguration wf = new WorkingFlowConfiguration();
			wf.setEnable(Constant.BooleanByte.YES.shortValue());
			wf.setDatastatus((short)Constant.Enum_DataStatus.正常.value);
			List<WorkingFlowConfiguration> list = ss.select(wf);
			if(list!=null && list.size()>0){
				for (WorkingFlowConfiguration w : list) {
					if(workingflow.get(w.getEnterprisecode())==null){
						workingflow.put(w.getEnterprisecode(), new ArrayList<WorkingFlowConfiguration>());
					}
					workingflow.get(w.getEnterprisecode()).add(w);
				}
				Map<byte[],byte[]> object = new HashMap<byte[],byte[]>(); 
				for (String key : workingflow.keySet()) {
					object.put(key.getBytes(), ObjectSerialize.serialize(workingflow.get(key)));
				}
				jedis.hmset(this.ENTERPRISE_WORKINGFLOW.getBytes(), object);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("缓存工作流配置失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		
	}
	/**
	 * 获取第一个流程
	 * @param enterpeisecode 企业编码
	 * @param Enum_flowtype 工作流类型
	 * @param flowthreshold 充值金额
	 * @return
	 */
	public WorkingFlowConfiguration first(String enterprisecode , Enum_flowtype flowtype, BigDecimal flowthreshold)  throws Exception{
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			List<WorkingFlowConfiguration> list = ObjectSerialize.unserialize(jedis.hget(this.ENTERPRISE_WORKINGFLOW.getBytes(), enterprisecode.getBytes()));
			if(list==null || list.size() ==0 || flowtype== null) return null;
			for (WorkingFlowConfiguration w : list) {
				if(w.getFlowtype()!=flowtype.value) {
					continue;
				}
				if(w.getFlowthreshold()<flowthreshold.doubleValue()){
					return w;
				}
			}
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 获取下一个流程
	 * @param enterpeisecode 企业编码
	 * @param Enum_flowtype 工作流类型
	 * @param nowWorkingFlow 当前流程编码
	 * @param flowthreshold 充值金额
	 * @return
	 */
	public WorkingFlowConfiguration next(String enterprisecode ,Enum_flowtype flowtype,  String flowcode , double flowthreshold)  throws Exception{
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			List<WorkingFlowConfiguration> list = ObjectSerialize.unserialize(jedis.hget(this.ENTERPRISE_WORKINGFLOW.getBytes(), enterprisecode.getBytes()));
			if(list==null || list.size()==0 || flowcode==null) return null;
			boolean start = false;
			for (WorkingFlowConfiguration w : list) {
				if(w.getFlowtype()!=flowtype.value) {
					continue;
				}
				if(start && w.getFlowthreshold()<flowthreshold){
					return w;
				}
				if(w.getFlowcode().equals(flowcode)) {
					start = true;
				}
			}
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 重新加载企业工作流
	 * @param enterpeisecode
	 */
	public synchronized void reload(String enterprisecode) throws Exception{
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			WorkingFlowConfigurationService ss = SpringContextHolder.getBean("workingFlowConfigurationServiceImpl");
			WorkingFlowConfiguration wf = new WorkingFlowConfiguration();
			wf.setEnterprisecode(enterprisecode);
			wf.setEnable(Constant.BooleanByte.YES.shortValue());
			wf.setDatastatus((short)Constant.Enum_DataStatus.正常.value);
			List<WorkingFlowConfiguration> list = ss.select(wf);
			jedis.hset(this.ENTERPRISE_WORKINGFLOW.getBytes(), enterprisecode.getBytes(), ObjectSerialize.serialize(list));
		}finally {
			RemoteCache.returnJedis(jedis);
		}
	}
	
	
	public static void main(String[] args) {
		try {
			SpringContextUtil.getApplicationContext();
			SystemCache.getInstance().getWorkflow();
			Jedis jedis =  RemoteCache.getJedis();
			Map<byte[], byte[]> ss = jedis.hgetAll("ENTERPRISE_WORKINGFLOW_CACHE".getBytes());
			for (byte[] sss : ss.keySet()) {
				String s = ObjectSerialize.unserialize(sss);
				System.out.println(s);
			}
			
			
			/*List<WorkingFlowConfiguration> list = ObjectSerialize.unserialize(jedis.hget("ENTERPRISE_WORKINGFLOW_CACHE".getBytes(), "EN0000".getBytes()));
			for (WorkingFlowConfiguration w : list) {
				System.out.println(w.getFlowcode());
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}

	

}
