package com.maven.dao;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.Enterprise;

@Repository
public interface EnterpriseDao extends BaseDao<Enterprise>{

	/**
	 * 删除企业时,调用该方法验证企业是否可以删除
	 * @param enterprisecode
	 * @return
	 * @throws Exception
	 */
	boolean validateDeleteEnterprise(String enterprisecode);


}
