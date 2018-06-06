package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseThirdpartyPaymentAgumentDao;
import com.maven.entity.EnterpriseThirdpartyPaymentAgument;

@Repository
public class EnterpriseThirdpartyPaymentAgumentDaoImpl extends BaseDaoImpl<EnterpriseThirdpartyPaymentAgument> 
	implements EnterpriseThirdpartyPaymentAgumentDao {
	
	/**
	 * 保存
	 */
	@Override
	public void save(List<EnterpriseThirdpartyPaymentAgument> list) {
		getSqlSession().insert("EnterpriseThirdpartyPaymentAgument.batchAdd", list);
	}
	
	/**
	 * 修改 
	 * @param enterpriseThirdpartyPaymentAgument
	 * @throws Exception
	 */
	@Override
	public void update(EnterpriseThirdpartyPaymentAgument enterpriseThirdpartyPaymentAgument) {
		getSqlSession().update("EnterpriseThirdpartyPaymentAgument.updateByPrimaryKeySelective",enterpriseThirdpartyPaymentAgument);
	}
	
	/**
	 * 关联查询
	 * @param object
	 * @return List<EnterpriseThirdpartyPaymentAgument>
	 */
	@Override
	public List<EnterpriseThirdpartyPaymentAgument> selectUnionAll(Map<String, Object> object) {
		return getSqlSession().selectList("EnterpriseThirdpartyPaymentAgument.selectUnionAll", object);
	}
	
	/**
	 * 保存参数类型修改之后的值
	 * @param EnterpriseThirdpartyPaymentAgument
	 */
	@Override
	public void updateEnterpriseThirdpartyPaymentAgument(EnterpriseThirdpartyPaymentAgument enterpriseThirdpartyPaymentAgument) {
		getSqlSession().update("EnterpriseThirdpartyPaymentAgument.updateByPrimaryKeySelective", enterpriseThirdpartyPaymentAgument);
	}

	@Override
	public List<EnterpriseThirdpartyPaymentAgument> selectEDefualPayAccout(String enterprisecode) {
		return getSqlSession().selectList("EnterpriseThirdpartyPaymentAgument.selectEnterpriseDefualtPayMent",enterprisecode);
	}
}
