package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityButBonusDao;
import com.maven.dao.EnterpriseOperatingBrandPayDao;
import com.maven.dao.LoginWhiteListDao;
import com.maven.entity.ActivityButBonus;
import com.maven.entity.EnterpriseOperatingBrandPay;
import com.maven.entity.LoginWhiteList;

@Repository
public class EnterpriseOperatingBrandPayDaoImpl extends BaseDaoImpl<EnterpriseOperatingBrandPay> implements EnterpriseOperatingBrandPayDao {

	/**
	 * 批量删除
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void deleteSelect(String[] array) throws Exception {
		getSqlSession().delete("EnterpriseOperatingBrandPay.deleteSelectIp",array);
	}
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void saveEnterpriseOperatingBrandPay(EnterpriseOperatingBrandPay obj) throws Exception {
		getSqlSession().insert("EnterpriseOperatingBrandPay.add", obj);
	}
		
	
	/**
	 * 修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void updateEnterpriseOperatingBrandPay(EnterpriseOperatingBrandPay obj) throws Exception {
		getSqlSession().update("EnterpriseOperatingBrandPay.update",obj);
	}
}
