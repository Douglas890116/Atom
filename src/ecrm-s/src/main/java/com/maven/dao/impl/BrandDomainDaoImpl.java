package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BrandDomainDao;
import com.maven.entity.BrandDomain;
@Repository
public class BrandDomainDaoImpl extends BaseDaoImpl<BrandDomain> implements BrandDomainDao {
	/**
	 * 保存注册链接
	 * @param registerLink
	 */	
	@Override
	public void saveRegisterLink(BrandDomain registerLink) {
		getSqlSession().insert("BrandDomain.insert", registerLink);
	}
	/**
	 * 更新推广链接信息
	 * @param registerLink
	 */
	@Override
	public void updateRegisterLink(BrandDomain registerLink) {
		getSqlSession().update("BrandDomain.updateByPrimaryKeySelective", registerLink);
	}
	/**
	 * 批量删除推广链接
	 * @param array
	 */
	@Override
	public void deleteSelect(String[] array) {
		getSqlSession().update("BrandDomain.deleteSelect", array);
	}
	/**
	 * 批量删除推广链接
	 * @param array
	 */
	@Override
	public void logicDeleteByEmployeecode(String[] array) {
		getSqlSession().update("BrandDomain.logicDeleteByEmployeecode", array);
	}
	/**
	 * 根据注册链接编码查询
	 * @param registercode
	 * @throws Exception
	 */
	@Override
	public BrandDomain queryRegisterLinkObj(String domainlink) {
		return getSqlSession().selectOne("BrandDomain.selectByPrimaryKey",domainlink);
	}
	
	@Override
	public List<BrandDomain> takeAllAvalibleLink() throws Exception {
		return getSqlSession().selectList("BrandDomain.takeAllAvalibleLink");
	}
	@Override
	public int updateBatch(List<BrandDomain> domains) throws Exception {
		return getSqlSession().update("BrandDomain.updateBatch",domains);
	}
	@Override
	public int deleteMainDoamin(BrandDomain domain) throws Exception {
		return getSqlSession().update("BrandDomain.deleteMainDoamin",domain);
	}
	@Override
	public List<BrandDomain> takeSecondMainDomain(String linktoken) throws Exception {
		return getSqlSession().selectList("BrandDomain.takeSecondMainDomain",linktoken);
	}
	@Override
	public int selectCount(BrandDomain object) throws Exception {
		Long count =  getSqlSession().selectOne("BrandDomain.selectCount", object);
		return count == null?0:count.intValue();
	}
	@Override
	public int selectMaxId() throws Exception {
		return getSqlSession().selectOne("BrandDomain.selectMaxId");
	}

}
