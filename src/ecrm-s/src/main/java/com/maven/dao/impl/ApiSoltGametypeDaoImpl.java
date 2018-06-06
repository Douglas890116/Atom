package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.ApiSoltGametypeDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ApiSoltGametype;

@Repository
public class ApiSoltGametypeDaoImpl extends BaseDaoImpl<ApiSoltGametype> implements ApiSoltGametypeDao {

	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	public void save(ApiSoltGametype betrecord) throws Exception {
		getSqlSession().insert("ApiSoltGametype.save", betrecord);
	}
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	public void update(ApiSoltGametype betrecord) throws Exception {
		getSqlSession().insert("ApiSoltGametype.updateByPrimaryKey", betrecord);
	}
	
	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public List<ApiSoltGametype> selectAll(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("ApiSoltGametype.selectAll", parameter);
	}
	
	/**
	 * 查询需要打码的记录总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public int selectAllCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("ApiSoltGametype.selectAllCount", parameter);
	}
}
