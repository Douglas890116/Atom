package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.LogOperationDetailDao;
import com.maven.entity.LogOperationDetail;

@Repository
public class LogOperationDetailDaoImpl extends BaseDaoImpl<LogOperationDetail> implements LogOperationDetailDao{
	

	/**
	 * 查询操作日记详情
	 * @param map
	 * @return
	 */
	@Override
	public List<LogOperationDetail> findOperatingLogDetail(Map<String, Object> map) {
		return getSqlSession().selectList("LogOperationDetail.findOperatingLogDetail", map);
	}

}
