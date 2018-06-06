/**
 * 
 */
package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.EnterpriseOperatingBrand;

@Repository
public class EnterpriseOperatingBrandDaoImpl extends BaseDaoImpl<EnterpriseOperatingBrand> implements EnterpriseOperatingBrandDao{

	@Override
	public List<EnterpriseOperatingBrand> getEnterpriseBrand(String enterprisecode) {
		return getSqlSession().selectList("EnterpriseOperatingBrand.getEnterpriseBrand", enterprisecode);
	}

}
