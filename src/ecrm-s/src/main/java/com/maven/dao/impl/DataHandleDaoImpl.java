package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.DataHandleDao;
import com.maven.dao.DataHandleMaintenanceDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.DataHandle;
import com.maven.entity.DataHandleMaintenance;

@Repository
public class DataHandleDaoImpl extends BaseDaoImpl<DataHandle> implements DataHandleDao{

	@Override
	public void addBetRecord(DataHandle betrecord) throws Exception {
		getSqlSession().insert("DataHandle.insert", betrecord);
	}
	
	@Override
	public void updateBetRecord(DataHandle betrecord) throws Exception {
		getSqlSession().insert("DataHandle.updateByPrimaryKeySelective", betrecord);
	}

	@Override
	public List<DataHandle> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("DataHandle.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("DataHandle.selectBetRecordCount", parameter);
	}

}
