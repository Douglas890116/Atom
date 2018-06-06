package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseThirdpartyPaymentAgument;

@Repository
public interface EnterpriseThirdpartyPaymentAgumentDao extends BaseDao<EnterpriseThirdpartyPaymentAgument>{
	/**
	 * 保存
	 * @param list
	 */
	void save(List<EnterpriseThirdpartyPaymentAgument> list);
	
	/**
	 * 修改 
	 * @param enterpriseThirdpartyPaymentAgument
	 * @throws Exception
	 */
	void update(EnterpriseThirdpartyPaymentAgument enterpriseThirdpartyPaymentAgument);
	
	/**
	 * 关联查询
	 * @param map
	 * @return List<EnterpriseThirdpartyPaymentAgument>
	 */
	List<EnterpriseThirdpartyPaymentAgument> selectUnionAll(Map<String, Object> map);
	
	/**
	 * 保存参数类型修改之后的值
	 * @param enterpriseThirdpartyPaymentAgument
	 */
	void updateEnterpriseThirdpartyPaymentAgument(EnterpriseThirdpartyPaymentAgument enterpriseThirdpartyPaymentAgument);
	
	/**
	 * 获取企业默认的出款银行卡
	 * @param enerprisecode
	 * @return
	 */
	public List<EnterpriseThirdpartyPaymentAgument> selectEDefualPayAccout(String enterprisecode);

}
