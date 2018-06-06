package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseBrandActivity;

@Repository
public interface EnterpriseBrandActivityDao extends BaseDao<EnterpriseBrandActivity>{

	/**
	 * 批量删除企业运营活动
	 * @param activitysettingcode
	 * @return
	 * @throws Exception
	 */
	public int deleteBatch(List<String> activitycodes) throws Exception;
	
	/**
	 * 查询活动参数
	 * @param __enterprisebrandactivitycode
	 * @return
	 */
	List<Map<String,Object>> selectActivityAgument(int __enterprisebrandactivitycode);
	
	/**
	 * 根据品牌编码查询所有活动
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectActivityByBrand(String brandcode) throws Exception;
	
}
