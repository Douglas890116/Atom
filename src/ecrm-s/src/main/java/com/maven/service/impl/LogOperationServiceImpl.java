package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.LogOperationDao;
import com.maven.entity.LogOperation;
import com.maven.entity.LogOperationDetail;
import com.maven.service.LogOperationDetailService;
import com.maven.service.LogOperationService;
import com.maven.util.AttrCheckout;

@Service
public class LogOperationServiceImpl extends BaseServiceImpl<LogOperation> implements LogOperationService{
	
	@Autowired
	private LogOperationDao logOperationDao;
	@Autowired
	private LogOperationDetailService logOperationDetailService;
	
	@Override
	public BaseDao<LogOperation> baseDao() {
		return logOperationDao;
	}

	@Override
	public Class<LogOperation> getClazz() {
		return LogOperation.class;
	}

	@Override
	public int saveLog(LogOperation log,List<LogOperationDetail> logDetail) throws Exception {
		super.add(AttrCheckout.checkout(log, false, 
				new String[]{"tablename","servicename","visiteurl","oprationtype","oprationtime","employeecode","parentemployeecode","oprationperson"}));
		for (LogOperationDetail logOperationDetail : logDetail) {
			logOperationDetail.setLogcode(log.getLogcode());
		}
		logOperationDetailService.saveBatchLog(logDetail);
		return 1;
	}
	
	/**
	 * 查询操作日志
	 * @param map
	 * @return
	 */
	@Override
	public List<LogOperation> findOperatingLog(Map<String, Object> map) throws Exception {
		return logOperationDao.findOperatingLog(map);
	}
	
	/**
	 * 统计查询日志
	 * @param map
	 * @return
	 */
	@Override
	public int findOperatingLogCount(Map<String, Object> map) throws Exception {
		return logOperationDao.findOperatingLogCount(map);
	}


}
