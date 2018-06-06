package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.BrandDomain;
@Repository
public interface BrandDomainDao extends BaseDao<BrandDomain> {
	
	/**
	 * 保存注册链接
	 * @param registerLink
	 */
	public abstract void saveRegisterLink(BrandDomain registerLink);
	/**
	 * 更新推广链接信息
	 * @param registerLink
	 */
	public abstract void updateRegisterLink(BrandDomain registerLink);
	/**
	 * 批量删除推广链接
	 * @param array
	 */
	public abstract void deleteSelect(String[] array);
	/**
	 * 根据employeecode批量删除推广链接
	 * @param array
	 */
	public abstract void logicDeleteByEmployeecode(String[] array);
	/**
	 * 根据注册链接编码查询
	 * @param registercode
	 * @throws Exception
	 */
	public abstract BrandDomain queryRegisterLinkObj(String domainlink);
	
	/**
	 * 获取所有推广链接
	 * @return
	 * @throws Exception
	 */
	public List<BrandDomain> takeAllAvalibleLink() throws Exception;
	
	/**
	 * 设置默认域名
	 * @param domains
	 * @return
	 * @throws Exception
	 */
	public int updateBatch(List<BrandDomain> domains) throws Exception;
	
	/**
	 * 删除主域名
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int deleteMainDoamin(BrandDomain domain) throws Exception;
	
	/**
	 * 根据key获取使用了主域名的推广域名信息
	 * @param linktoken
	 * @return
	 */
	public List<BrandDomain> takeSecondMainDomain(String linktoken) throws Exception;
	
	/**
	 * 精确查询数据量统计
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int selectCount(BrandDomain object)throws Exception;
	
	/**
	 * 查询最大ID
	 * @return
	 * @throws Exception
	 */
	public int selectMaxId()throws Exception;
	
	
}
