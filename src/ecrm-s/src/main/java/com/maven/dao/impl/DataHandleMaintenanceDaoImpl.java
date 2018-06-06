package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.DataHandleMaintenanceDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.DataHandleMaintenance;

@Repository
public class DataHandleMaintenanceDaoImpl extends BaseDaoImpl<DataHandleMaintenance> implements DataHandleMaintenanceDao{

	@Override
	public void addBetRecord(DataHandleMaintenance betrecord) throws Exception {
		getSqlSession().insert("DataHandleMaintenance.insert", betrecord);
	}
	
	@Override
	public void updateBetRecord(DataHandleMaintenance betrecord) throws Exception {
		getSqlSession().insert("DataHandleMaintenance.updateByPrimaryKeySelective", betrecord);
	}

	@Override
	public List<DataHandleMaintenance> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("DataHandleMaintenance.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("DataHandleMaintenance.selectBetRecordCount", parameter);
	}

}
