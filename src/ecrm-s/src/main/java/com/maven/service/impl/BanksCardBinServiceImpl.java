package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BanksCardBinDao;
import com.maven.entity.BanksCardBin;
import com.maven.service.BanksCardBinService;

@Service
public class BanksCardBinServiceImpl extends BaseServiceImpl<BanksCardBin> implements BanksCardBinService{

	@Autowired
	private BanksCardBinDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<BanksCardBin> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<BanksCardBin> getClazz() {
		return BanksCardBin.class;
	}

	@Override
	public List<BanksCardBin> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecord(parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCount(parameter);
	}
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(BanksCardBin activityBetRecord) throws Exception {
		activityBetRecordDao.addBetRecord(activityBetRecord);
	}

}
