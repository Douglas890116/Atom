package com.maven.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.DepositWithdralOrderDelegate;

@Repository
public interface DepositWithdralOrderDelegateDao extends BaseDao<DepositWithdralOrderDelegate>{
	
	public int updateStatus(Map<String,Object> object);

}
