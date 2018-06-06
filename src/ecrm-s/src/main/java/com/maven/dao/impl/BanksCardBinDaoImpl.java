package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.BanksCardBinDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.BanksCardBin;

@Repository
public class BanksCardBinDaoImpl extends BaseDaoImpl<BanksCardBin> implements BanksCardBinDao{

	@Override
	public void addBetRecord(BanksCardBin betrecord) throws Exception {
		getSqlSession().insert("BanksCardBin.insertSelective", betrecord);
	}

	@Override
	public List<BanksCardBin> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("BanksCardBin.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("BanksCardBin.selectBetRecordCount", parameter);
	}

}
