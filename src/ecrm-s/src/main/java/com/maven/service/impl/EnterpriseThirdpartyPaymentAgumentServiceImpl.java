package com.maven.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EnterpriseThirdpartyPaymentAgumentDao;
import com.maven.entity.EnterpriseThirdpartyPaymentAgument;
import com.maven.service.EnterpriseThirdpartyPaymentAgumentService;
@Service
public class EnterpriseThirdpartyPaymentAgumentServiceImpl extends BaseServiceImpl<EnterpriseThirdpartyPaymentAgument> 
	implements EnterpriseThirdpartyPaymentAgumentService {
	
	@Autowired
	private EnterpriseThirdpartyPaymentAgumentDao enterpriseThirdpartyPaymentAgumentDao;
	
	/**
	 * 保存
	 */
	@Override
	public void tc_save(List<EnterpriseThirdpartyPaymentAgument> list) throws Exception {
		enterpriseThirdpartyPaymentAgumentDao.save(list);
	}
	
	/**
	 * 修改 
	 * @param enterpriseThirdpartyPaymentAgument
	 * @throws Exception
	 */
	@Override
	public void tc_update(EnterpriseThirdpartyPaymentAgument enterpriseThirdpartyPaymentAgument) throws Exception {
		enterpriseThirdpartyPaymentAgumentDao.update(enterpriseThirdpartyPaymentAgument);
	}
	
	
	/**
	 *  保存参数类型修改之后的值
	 *  @param enterpriseThirdpartyPaymentAgument
	 */
	@Override
	public void updateEnterpriseThirdpartyPaymentAgument(EnterpriseThirdpartyPaymentAgument enterpriseThirdpartyPaymentAgument) throws Exception {
		enterpriseThirdpartyPaymentAgumentDao.updateEnterpriseThirdpartyPaymentAgument(enterpriseThirdpartyPaymentAgument);
	}

	@Override
	public BaseDao<EnterpriseThirdpartyPaymentAgument> baseDao() {
		return enterpriseThirdpartyPaymentAgumentDao;
	}

	@Override
	public Class<EnterpriseThirdpartyPaymentAgument> getClazz() {
		return EnterpriseThirdpartyPaymentAgument.class;
	}

	@Override
	public List<EnterpriseThirdpartyPaymentAgument> selectUnionAll(Map<String, Object> object) throws Exception {
		return enterpriseThirdpartyPaymentAgumentDao.selectUnionAll(object);
	}

	@Override
	public Map<String, Object> takeEnterprisePayAgument(String enterprisethirdpartycode) throws Exception{
		EnterpriseThirdpartyPaymentAgument __etpa = new EnterpriseThirdpartyPaymentAgument();
		__etpa.setEnterprisethirdpartycode(enterprisethirdpartycode);
		List<EnterpriseThirdpartyPaymentAgument> __aguments = super.select(__etpa);
		Map<String,Object> __agumentsMap = new HashMap<String, Object>();
		for (EnterpriseThirdpartyPaymentAgument __et : __aguments) {
			__agumentsMap.put(__et.getArgumentfiled(), __et.getAgumentvalue());
		}
		return __agumentsMap;
	}
	
	public Map<String,Object> takeEDefualtPayAgument(String enterprisecode) throws Exception{
		List<EnterpriseThirdpartyPaymentAgument> __aguments =  enterpriseThirdpartyPaymentAgumentDao.selectEDefualPayAccout(enterprisecode);
		Map<String,Object> __agumentsMap = new HashMap<String, Object>();
		if(__aguments.size()>0){
			__agumentsMap.put("thirdpartypaymenttypecode", __aguments.get(0).getThirdpartypaymenttypecode());
			for (EnterpriseThirdpartyPaymentAgument __et : __aguments) {
				__agumentsMap.put(__et.getArgumentfiled(), __et.getAgumentvalue());
			}
		}
		
		return __agumentsMap;
	}

}
