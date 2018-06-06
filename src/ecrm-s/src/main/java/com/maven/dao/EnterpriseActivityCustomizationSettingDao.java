package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseActivityCustomizationSetting;

@Repository
public interface EnterpriseActivityCustomizationSettingDao extends BaseDao<EnterpriseActivityCustomizationSetting>{
	/**
	 * 通过活动编码删除参数
	 * @param eCActivitycode
	 * @return
	 */
	int deleteByECActivitycode(Integer eCActivitycode);
	
	/**
	 * 根据企业活动定制编码与活动模板参数编码查询企业设置的参数值
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<EnterpriseActivityCustomizationSetting> selectSettingByTwoCode(Map<String, Object> map) throws Exception; 
}
