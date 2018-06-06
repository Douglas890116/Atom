/**
 * 
 */
package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.util.AttrCheckout;

@Service
public class EnterpriseOperatingBrandServiceImpl extends BaseServiceImpl<EnterpriseOperatingBrand> 
	implements EnterpriseOperatingBrandService{

	@Autowired
	private EnterpriseOperatingBrandDao enterpriseOperatingBrandDao;
	
	@Override
	public BaseDao<EnterpriseOperatingBrand> baseDao() {
		return enterpriseOperatingBrandDao;
	}

	@Override
	public Class<EnterpriseOperatingBrand> getClazz() {
		return EnterpriseOperatingBrand.class;
	}

	/**
	 * 获取所有品牌
	 */
	@Override
	public List<EnterpriseOperatingBrand> getAllBrand(Map<String,Object> object) throws Exception{
		return super.selectAll(object);
	}
	
	/**
	 * 获取品牌总数
	 * @throws Exception 
	 */
	@Override
	public int getAllBrandCount(Map<String, Object> object) throws Exception {
		return super.selectAllCount(object);
	}
	
	@Override
	public void tc_createBrand(EnterpriseOperatingBrand brand) throws Exception {
		brand = AttrCheckout.checkout(brand, false, new String[]{"enterprisecode","brandname","logopath","webtemplatecode"});
		super.add(brand);
	}


	@Override
	public EnterpriseOperatingBrand takeBrandByPrimaryKey(String brandcode) throws Exception {
		return super.selectByPrimaryKey(brandcode);
	}

	@Override
	public void updateBrand(EnterpriseOperatingBrand brand) throws Exception {
		this.update(AttrCheckout.checkout(brand, false, new String[]{"brandcode"}));
	}

	@Override
	public void deleteBrand(List<String> brandcodes) throws Exception {
		this.logicDelete(brandcodes);
	}
	
	
	@Override
	public List<EnterpriseOperatingBrand> getEnterpriseBrand(String enterprisecode) throws Exception {
		return enterpriseOperatingBrandDao.getEnterpriseBrand(enterprisecode);
	}

	@Override
	public int updateDefualtChip(EnterpriseOperatingBrand brand) throws Exception {
		return this.update(AttrCheckout.checkout(brand, false, new String[]{"brandcode","defualtchip"}));
	}

	

}
