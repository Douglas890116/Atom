package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EnterpriseDao;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PlatformApiOutput;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.service.PlatformApiOutputService;
import com.maven.util.AttrCheckout;

@Service
public class EnterpriseServiceImpl extends BaseServiceImpl<Enterprise> implements EnterpriseService{

	@Autowired
	private EnterpriseDao enterpriseDao;
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	@Autowired
	private PlatformApiOutputService platformApiOutputService;
	
	@Override
	public BaseDao<Enterprise> baseDao() {
		return enterpriseDao;
	}

	@Override
	public Class<Enterprise> getClazz() {
		return Enterprise.class;
	}

	@Override
	public int tc_CreateEnterprise(Enterprise e , EnterpriseEmployee ee) throws Exception{
		AttrCheckout.checkout(e, false, new String[]{"enterprisename"});
		//创建企业
		super.save(e);
		//创建会员
		ee.setEnterprisecode(e.getEnterprisecode());
		enterpriseEmployeeService.tc_saveEnterprise(ee);
		//授权接口访问权限
		platformApiOutputService.addBrandApiConfig(new PlatformApiOutput(e.getEnterprisecode(),null));
		return 1;
	}

	@Override
	public int update(Enterprise e) throws Exception {
		return super.update(e);
	}

	@Override
	public int tc_logicDelete(List<String> enterprisecoe) throws Exception {
		return super.logicDelete(enterprisecoe);
	}
	
	/**
	 * 删除企业时,调用该方法验证企业是否可以删除
	 * @param enterprisecode
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean getValidateDeleteEnterprise(String enterprisecode) throws Exception {
		return enterpriseDao.validateDeleteEnterprise(enterprisecode);
	}



}
