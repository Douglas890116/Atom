package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseInformationQrcodeDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseInformationQrcode;

@Repository
public class EnterpriseInformationQrcodeDaoImpl extends BaseDaoImpl<EnterpriseInformationQrcode> implements EnterpriseInformationQrcodeDao{

	@Override
	public void addBetRecord(EnterpriseInformationQrcode betrecord) throws Exception {
		getSqlSession().insert("EnterpriseInformationQrcode.insertSelective", betrecord);
	}
	
	@Override
	public void updateBetRecord(EnterpriseInformationQrcode betrecord) throws Exception {
		getSqlSession().insert("EnterpriseInformationQrcode.updateByPrimaryKeySelective", betrecord);
	}
	
	@Override
	public void deleteBetRecord(Integer lsh) throws Exception {
		getSqlSession().insert("EnterpriseInformationQrcode.deleteByPrimaryKey", lsh);
	}
	
	public void deleteBetRecord(List<String> list) throws Exception {
		getSqlSession().insert("EnterpriseInformationQrcode.deleteBetRecord", list);
	}

	@Override
	public List<EnterpriseInformationQrcode> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("EnterpriseInformationQrcode.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("EnterpriseInformationQrcode.selectBetRecordCount", parameter);
	}

}
