package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityButBonus;
import com.maven.entity.EnterpriseOperatingBrandPay;
import com.maven.entity.LoginWhiteList;

@Repository
public interface EnterpriseOperatingBrandPayDao extends BaseDao<EnterpriseOperatingBrandPay> {

	
	/**
	 * 批量删除
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void deleteSelect(String[] array) throws Exception ;
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void saveEnterpriseOperatingBrandPay(EnterpriseOperatingBrandPay obj) throws Exception ;
		
	
	/**
	 * 修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void updateEnterpriseOperatingBrandPay(EnterpriseOperatingBrandPay obj) throws Exception ;
}
