package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.DepositWithdralOrderDelegateDao;
import com.maven.entity.DepositWithdralOrderDelegate;

@Repository
public class DepositWithdralOrderDelegateDaoImpl extends BaseDaoImpl<DepositWithdralOrderDelegate> 
	implements DepositWithdralOrderDelegateDao{

	@Override
	public int updateStatus(Map<String, Object> object) {
		return getSqlSession().update("DepositWithdralOrderDelegate.updateStatus",object);
	}

}
