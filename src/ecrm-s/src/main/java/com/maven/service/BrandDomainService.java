package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.AgentSiteContact;
import com.maven.entity.BrandDomain;
import com.maven.entity.EnterpriseGame;

@Service
public interface BrandDomainService extends BaseServcie<BrandDomain>{
	/**
	 * 保存推广域名
	 * @param registerLink
	 */
	@DataSource("master")
	public abstract void tc_save(BrandDomain domain) throws Exception;
	
	/**
	 * 保存代理域名和联系方式
	 * @param domain
	 * @throws Exception
	 */
	@DataSource("master")
	public abstract void tc_saveAgentDomainAndContact(BrandDomain domain,AgentSiteContact contact) throws Exception;
	
	/**
	 * 创建代理默认域名
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public String tc_createAgentDefualtDomain(BrandDomain domain)throws Exception;
	/**
	 * 更新推广链接信息
	 * @param registerLink
	 */
	@DataSource("master")
	public abstract void tc_updateRegisterLink(BrandDomain registerLink) throws Exception;
	/**
	 * 批量删除推广链接
	 * @param array
	 */
	@DataSource("master")
	public abstract void tc_deleteSelect(String[] array) throws Exception;
	/**
	 * 根据employeecode批量删除推广链接
	 * @param array
	 */
	@DataSource("master")
	public abstract void tc_logicDeleteByEmployeecode(String[] array) throws Exception;
	/**
	 * 根据DomainLike链接编码查询
	 * @param registercode
	 * @throws Exception
	 */
	@DataSource("slave")
	public abstract BrandDomain queryByDomainLink(String domain) throws Exception;
	
	
	/**
	 * 获取所有的推广域名，通过品牌编码分类, 仅供接口使用
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public Map<String,List<String>> takeAllExpandDomain() throws Exception;
	
	/**
	 * 获取品牌主域名
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<BrandDomain> takeBrandDomain(BrandDomain object) throws Exception;
	
	/**
	 * 统计品牌主域名总数
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public int takeBrandDomainCount(BrandDomain object) throws Exception;
	
	/**
	 * 检测域名是否已经被绑定
	 * @param domainLink
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public int queryDetectionDomainLinkExit(String domainLink) throws Exception;
	
	/**
	 * 根据域名类型设置企业代理站点与会员站点的默认域名
	 * @param domain  "domaincode","enterprisecode","domaintype"
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_SetttingDefualtDomain(BrandDomain domain) throws Exception;
	
	/**
	 * 删除主域名
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_DeleteMainDomain(BrandDomain domain) throws Exception;
	
	/**
	 * 获取企业默认会员站点域名
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public BrandDomain takeEDefualtUSiteDomain(String enterprisecode) throws Exception;
	
	/**
	 * 获取企业默认代理站点域名
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public BrandDomain takeEDefualtASiteDomain(String enterprisecode) throws Exception;
	
	/**
	 * 根据key获取使用了主域名的推广域名信息
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public BrandDomain takeSecondMainDomain(String linktoken) throws Exception;
	
	/**
	 * 获取默认域名前缀
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public String takeDefualtDomainPrefix() throws Exception;
	
	/**
	 * 批量删除域名
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_BatchLogicDelete(List<BrandDomain> list) throws Exception;
	
	/**
	 * 更新站点的洗码设置
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public void updateDomainGameCategoryBonus(String Enterprisecode, List<EnterpriseGame> games) throws Exception;

}
