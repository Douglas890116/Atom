package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseInformationQrcode;
import com.maven.entity.TakeDepositRecord;

@Service
public interface EnterpriseInformationQrcodeService extends BaseServcie<EnterpriseInformationQrcode>{
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	@DataSource("master")
	void addBetRecord(EnterpriseInformationQrcode betrecord) throws Exception;
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	@DataSource("master")
	void updateBetRecord(EnterpriseInformationQrcode betrecord) throws Exception;
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	@DataSource("master")
	void deleteBetRecord(Integer lsh) throws Exception;
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void deleteBetRecord(List<String> list) throws Exception;
	
	
	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<EnterpriseInformationQrcode> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查询需要打码的记录总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	int selectBetRecordCount(Map<String, Object> parameter) throws Exception;
}
