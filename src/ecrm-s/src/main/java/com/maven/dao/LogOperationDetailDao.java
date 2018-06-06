package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.LogOperationDetail;

@Repository
public interface LogOperationDetailDao extends BaseDao<LogOperationDetail> {


	/**
	 * 查询操作日记详情
	 * @param map
	 * @return
	 */
	List<LogOperationDetail> findOperatingLogDetail(Map<String, Object> map);

}
