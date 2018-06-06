package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.DataSource;
import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseBannerInfoDao;
import com.maven.dao.EnterpriseEmployeeLevelBonusDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseBannerInfo;
import com.maven.entity.EnterpriseEmployeeLevelBonus;

@Repository
public class EnterpriseEmployeeLevelBonusDaoImpl extends BaseDaoImpl<EnterpriseEmployeeLevelBonus> implements EnterpriseEmployeeLevelBonusDao {

	@Override
	public void addBetRecord(EnterpriseEmployeeLevelBonus betrecord) throws Exception {
		getSqlSession().insert("EnterpriseEmployeeLevelBonus.insert", betrecord);
	}

	@Override
	public void updateBetRecord(EnterpriseEmployeeLevelBonus betrecord) throws Exception {
		getSqlSession().insert("EnterpriseEmployeeLevelBonus.updateByPrimaryKeySelective", betrecord);
	}
	
	@Override
	public List<EnterpriseEmployeeLevelBonus> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("EnterpriseEmployeeLevelBonus.select", parameter);
	}


}
