package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.DataHandleDao;
import com.maven.dao.DataHandleLogsDao;
import com.maven.dao.DataHandleMaintenanceDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.DataHandle;
import com.maven.entity.DataHandleLogs;
import com.maven.entity.DataHandleMaintenance;

@Repository
public class DataHandleLogsDaoImpl extends BaseDaoImpl<DataHandleLogs> implements DataHandleLogsDao{

	@Override
	public void addBetRecord(DataHandleLogs betrecord) throws Exception {
		getSqlSession().insert("DataHandleLogs.insert", betrecord);
	}
	
	@Override
	public void updateBetRecord(DataHandleLogs betrecord) throws Exception {
		getSqlSession().insert("DataHandleLogs.updateByPrimaryKeySelective", betrecord);
	}

	@Override
	public List<DataHandleLogs> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("DataHandleLogs.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("DataHandleLogs.selectBetRecordCount", parameter);
	}

}
