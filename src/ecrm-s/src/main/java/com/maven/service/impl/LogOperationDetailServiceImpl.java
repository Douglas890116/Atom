package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.LogOperationDetailDao;
import com.maven.entity.LogOperationDetail;
import com.maven.service.LogOperationDetailService;
import com.maven.util.AttrCheckout;

@Service
public class LogOperationDetailServiceImpl extends BaseServiceImpl<LogOperationDetail> implements LogOperationDetailService{

	@Autowired
	private LogOperationDetailDao logOperationDetailDao;
	
	@Override
	public BaseDao<LogOperationDetail> baseDao() {
		return logOperationDetailDao;
	}

	@Override
	public Class<LogOperationDetail> getClazz() {
		return LogOperationDetail.class;
	}

	@Override
	public int saveBatchLog(List<LogOperationDetail> object) throws Exception {
		return super.saveRecordBatch(AttrCheckout.checkout(object, false, new String[]{"logcode"}));
	}
	
	/**
	 * 查询操作日记详情
	 * @param map
	 * @return
	 */
	@Override
	public List<LogOperationDetail> findOperatingLogDetail(Map<String, Object> map) {
		return logOperationDetailDao.findOperatingLogDetail(map);
	}

}
