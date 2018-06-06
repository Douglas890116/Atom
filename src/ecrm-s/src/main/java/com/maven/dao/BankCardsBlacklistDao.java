package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.BankCardsBlacklist;

@Repository
public interface BankCardsBlacklistDao extends BaseDao<BankCardsBlacklist> {
	/**
	 * 增
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	int add(BankCardsBlacklist obj) throws Exception;
	/**
	 * 删
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int delete(Integer lsh) throws Exception;
	/**
	 * 改
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int update(BankCardsBlacklist obj) throws Exception;
	/**
	 * 查 - 单个
	 * @param lsh
	 * @return
	 * @throws Exception
	 */
	BankCardsBlacklist selectByPrimaryKey(Integer lsh)  throws Exception;
	/**
	 * 查 - 集合
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<BankCardsBlacklist> selectAll(Map<String, Object> params) throws Exception;
	/**
	 * 查 - 总数
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int selectAllCount(Map<String, Object> params) throws Exception;
}