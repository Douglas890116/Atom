package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.EmployeeApiAccoutDao;
import com.maven.dao.EmployeeApiAccoutPasswordJobDao;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;
import com.maven.service.EmployeeApiAccoutPasswordJobService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.util.AttrCheckout;

@Service
public class EmployeeApiAccoutPasswordJobServiceImpl extends BaseServiceImpl<EmployeeApiAccoutPasswordJob> implements EmployeeApiAccoutPasswordJobService{

	@Autowired
	private EmployeeApiAccoutPasswordJobDao accoutPasswordJobDao;
	
	@Override
	public BaseDao<EmployeeApiAccoutPasswordJob> baseDao() {
		return accoutPasswordJobDao;
	}

	@Override
	public Class<EmployeeApiAccoutPasswordJob> getClazz() {
		return EmployeeApiAccoutPasswordJob.class;
	}


	/**
	 * 创建游戏平台账户
	 * @param object 必须参数 "apicode","employeecode","gameaccount","gamepassword"
	 * @return
	 * @throws Exception
	 */
	public int add(EmployeeApiAccoutPasswordJob object) throws Exception {
		return super.add(object);
	}
	
	/**
	 * 修改密码
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int updatePassword(EmployeeApiAccoutPasswordJob object) throws Exception {
		return super.update(object);
	}
	
	public List<EmployeeApiAccoutPasswordJob> findList(EmployeeApiAccoutPasswordJob object) throws Exception {
		return super.select(object);
	}
}
