package com.maven.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.PrivateDataAccessDao;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PrivateDataAccess;
import com.maven.service.PrivateDataAccessService;
import com.maven.util.AttrCheckout;

@Service
public class PrivateDataAccessServiceImpl extends BaseServiceImpl<PrivateDataAccess> implements PrivateDataAccessService{

	@Autowired
	private PrivateDataAccessDao privateDataAccessDao;
	
	@Override
	public BaseDao<PrivateDataAccess> baseDao() {
		return privateDataAccessDao;
	}

	@Override
	public Class<PrivateDataAccess> getClazz() {
		return PrivateDataAccess.class;
	}

	@Override
	public int addPriveDateAccess(List<PrivateDataAccess> objects) throws Exception{
		return super.saveRecordBatch(AttrCheckout.checkout(objects, false, new String[]{"enterprisecode","employeecode"}));
	}

	@Override
	public int deleteListAccess(List<String> ids) throws Exception{
		return super.delete(ids);
	}

	@Override
	public int deleteAccess(String id) throws Exception{
		List<String> objects = new ArrayList<String>();
		objects.add(id);
		return super.delete(objects);
	}

	@Override
	public List<PrivateDataAccess> selectAccredit(Map<String,Object> object) {
		return privateDataAccessDao.selectAccredit(object);
	}

	@Override
	public int selectAccreditCount(Map<String,Object> object) {
		return privateDataAccessDao.selectAccreditCount(object);
	}
	@Override
	public boolean checkAuthority(EnterpriseEmployee employee) {
		Map<String, String> object = new HashMap<String, String>();
		object.put("enterprisecode", employee.getEnterprisecode());
		object.put("employeecode", employee.getEmployeecode());
		return privateDataAccessDao.checkAuthority(object) > 0;
	}
}
