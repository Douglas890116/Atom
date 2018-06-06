/**
 * 
 */
package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EnterpriseOperatingBrand;

@Service
public interface EnterpriseOperatingBrandService extends BaseServcie<EnterpriseOperatingBrand>{
	
	/**
	 * 根据主键获取品牌
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	EnterpriseOperatingBrand takeBrandByPrimaryKey(String brandcode)  throws Exception;
	
	/**
	 * 根据企业号获取品牌
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<EnterpriseOperatingBrand> getEnterpriseBrand(String enterprisecode)  throws Exception;
	
	/**
	 * 获取所有品牌
	 * @return List<EnterpriseOperatingBrand>
	 */
	@DataSource("slave")
	List<EnterpriseOperatingBrand> getAllBrand(Map<String,Object> object) throws Exception;
	
	/**
	 * 获取品牌总数
	 * @return int
	 */
	@DataSource("slave")
	int getAllBrandCount(Map<String,Object> object) throws Exception;
	
	/**
	 * 创建品牌
	 * @param brand  EnterpriseOperatingBrand
	 * @param Employee EnterpriseOperatingBrand
	 * @param games List<EnterpriseOperatingBrandGame>
	 * @param api GameApiInput
	 * @param domain BrandDomain
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_createBrand(EnterpriseOperatingBrand brand)throws Exception;
	
	/**
	 * 修改品牌信息
	 * @param brand
	 * @throws Exception
	 */
	@DataSource("master")
	void updateBrand(EnterpriseOperatingBrand brand)throws Exception;
	
	/**
	 * 修改品牌默认打码倍数
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int updateDefualtChip(EnterpriseOperatingBrand brand)throws Exception;
	
	/**
	 * 删除品牌
	 * @param brandcodes
	 * @throws Exception
	 */
	@DataSource("master")
	void deleteBrand(List<String> brandcodes)throws Exception;
}
