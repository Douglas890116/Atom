package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EnterpriseBrandActivity;

@Service
public interface EnterpriseBrandActivityService extends BaseServcie<EnterpriseBrandActivity>{
	
	/**
	 * 保存企业活动
	 * @param activity
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int saveEnterpriseActivity(EnterpriseBrandActivity activity)throws Exception;
	
	/**
	 * 修改企业活动
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int editEnterpriseActivity(EnterpriseBrandActivity activity)throws Exception;
	
	/**
	 * 批量删除企业活动
	 * @param activitysettingcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int deleteEnterpriseActivity(List<String> activitycodes) throws Exception;
	
	/**
	 * 根据品牌编码、模板名称查找活动
	 * @param code
	 * @param templatename
	 * @param acresult
	 * @return
	 */
	@DataSource("slave")
	public EnterpriseBrandActivity checkEnterpriseBrandActivity(Integer brandactivitycode) throws Exception;
	
	/**
	 * 获取企业游戏参数
	 * @param __enterprisebrandactivitycode
	 * @return
	 */
	@DataSource("slave")
	public Map<String,Object> selectActivityAgument(int __enterprisebrandactivitycode);
	
	/**
	 * 根据品牌编码获取所有活动
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectActivityByBrand(String brandcode) throws Exception;
}
