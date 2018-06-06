package com.maven.dao.impl;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EmployeeMessageDao;
import com.maven.entity.EmployeeMessage;

@Repository
public class EmployeeMessageDaoImpl extends BaseDaoImpl<EmployeeMessage> implements EmployeeMessageDao{

}
