package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.DataSource;
import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseBannerInfoDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseBannerInfo;

@Repository
public class EnterpriseBannerInfoDaoImpl extends BaseDaoImpl<EnterpriseBannerInfo> implements EnterpriseBannerInfoDao {

	@Override
	public void addBetRecord(EnterpriseBannerInfo betrecord) throws Exception {
		getSqlSession().insert("EnterpriseBannerInfo.insertSelective", betrecord);
	}

	@Override
	public void updateBetRecord(EnterpriseBannerInfo betrecord) throws Exception {
		getSqlSession().insert("EnterpriseBannerInfo.updateByPrimaryKeySelective", betrecord);
	}
	
	@Override
	public List<EnterpriseBannerInfo> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("EnterpriseBannerInfo.selectAll", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("EnterpriseBannerInfo.selectAllCount", parameter);
	}
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	public void deleteBetRecord(List<String> list) throws Exception {
		getSqlSession().update("EnterpriseBannerInfo.deleteByPrimaryKey", list);
	}

}
