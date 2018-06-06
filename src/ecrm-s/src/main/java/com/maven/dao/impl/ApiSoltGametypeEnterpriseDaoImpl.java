package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.ApiSoltGametypeEnterpriseDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ApiSoltGametypeEnterprise;

@Repository
public class ApiSoltGametypeEnterpriseDaoImpl extends BaseDaoImpl<ApiSoltGametypeEnterprise> implements ApiSoltGametypeEnterpriseDao{

	public List<ApiSoltGametypeEnterprise> selectTypes(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("ApiSoltGametypeEnterprise.selectAllType", parameter);
	}
	
	@Override
	public void deleteByEnterprisecode(String enterprisecode) throws Exception {
		getSqlSession().delete("ApiSoltGametypeEnterprise.deleteByEnterprisecode", enterprisecode);
	}
	
	@Override
	public void addBetRecord(ApiSoltGametypeEnterprise betrecord) throws Exception {
		getSqlSession().insert("ApiSoltGametypeEnterprise.insertSelective", betrecord);
	}

	@Override
	public List<ApiSoltGametypeEnterprise> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("ApiSoltGametypeEnterprise.selectAll", parameter);
	}
	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("ApiSoltGametypeEnterprise.selectAllCount", parameter);
	}
	
	@Override
	public List<ApiSoltGametypeEnterprise> selectAdd(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("ApiSoltGametypeEnterprise.selectAdd", parameter);
	}
	@Override
	public int selectAddCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("ApiSoltGametypeEnterprise.selectAddCount", parameter);
	}

	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public List<ApiSoltGametypeEnterprise> select(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("ApiSoltGametypeEnterprise.select", parameter);
	}
	
	
	

}
