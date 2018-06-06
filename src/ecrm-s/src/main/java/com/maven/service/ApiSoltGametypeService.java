package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ApiSoltGametype;
import com.maven.entity.TakeDepositRecord;

@Service
public interface ApiSoltGametypeService extends BaseServcie<ApiSoltGametype>{
	
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	@DataSource("master")
	void saveApiSoltGametype(ApiSoltGametype betrecord) throws Exception;
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	@DataSource("master")
	void updateApiSoltGametype(ApiSoltGametype betrecord) throws Exception;
	
	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<ApiSoltGametype> selectAll(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查询需要打码的记录总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	int selectAllCount(Map<String, Object> parameter) throws Exception;
}
