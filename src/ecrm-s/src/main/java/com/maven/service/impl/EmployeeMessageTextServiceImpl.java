package com.maven.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.constant.Constant;
import com.maven.dao.EmployeeMessageTextDao;
import com.maven.entity.EmployeeMessageText;
import com.maven.service.EmployeeMessageTextService;
import com.maven.util.AttrCheckout;

@Service
public class EmployeeMessageTextServiceImpl extends BaseServiceImpl<EmployeeMessageText> implements EmployeeMessageTextService{

	@Autowired
	private EmployeeMessageTextDao employeeMassageTextDao;
	
	@Override
	public BaseDao<EmployeeMessageText> baseDao() {
		return employeeMassageTextDao;
	}

	@Override
	public Class<EmployeeMessageText> getClazz() {
		return EmployeeMessageText.class;
	}

	@Override
	public int addMessageText(EmployeeMessageText text)throws Exception{
		AttrCheckout.checkout(text, false, new String[]{"content"});
		text.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
		return super.add(text);
	}

}
