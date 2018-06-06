package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.ApiSoltGametypeDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ApiSoltGametype;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.ApiSoltGametypeService;

@Service
public class ApiSoltGametypeServiceImpl extends BaseServiceImpl<ApiSoltGametype> implements ApiSoltGametypeService {

	@Autowired
	private ApiSoltGametypeDao apiSoltGametypeDao;
	
	
	@Override
	public BaseDao<ApiSoltGametype> baseDao() {
		return apiSoltGametypeDao;
	}

	@Override
	public Class<ApiSoltGametype> getClazz() {
		return ApiSoltGametype.class;
	}


	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	public void saveApiSoltGametype(ApiSoltGametype betrecord) throws Exception {
		apiSoltGametypeDao.save(betrecord);
	}
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	@Override
	public void updateApiSoltGametype(ApiSoltGametype betrecord) throws Exception {
		apiSoltGametypeDao.update(betrecord);
	}
	
	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ApiSoltGametype> selectAll(Map<String, Object> parameter) throws Exception {
		return apiSoltGametypeDao.selectAll(parameter);
	}
	
	/**
	 * 查询需要打码的记录总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@Override
	public int selectAllCount(Map<String, Object> parameter) throws Exception {
		return apiSoltGametypeDao.selectAllCount(parameter);
	}

}
