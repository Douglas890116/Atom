package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EmployeeMoneyChange;

@Repository
public interface EmployeeMoneyChangeDao extends BaseDao<EmployeeMoneyChange>{

	List<EmployeeMoneyChange> findAccountChange(Map<String, Object> object);

	Map<String, Object> findAccountChangeCount(Map<String, Object> object);
}
