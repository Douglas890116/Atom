/**
 * 
 */
package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseOperatingBrand;

@Repository
public interface EnterpriseOperatingBrandDao extends BaseDao<EnterpriseOperatingBrand>{

	List<EnterpriseOperatingBrand> getEnterpriseBrand(String enterprisecode);

}
