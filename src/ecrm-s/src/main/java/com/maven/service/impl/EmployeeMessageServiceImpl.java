package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EmployeeMessageDao;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EmployeeMessageText;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EmployeeMessageTextService;
import com.maven.util.AttrCheckout;

@Service
public class EmployeeMessageServiceImpl extends BaseServiceImpl<EmployeeMessage> implements EmployeeMessageService{

	@Autowired
	private EmployeeMessageDao employeeMassageDao;
	
	@Autowired
	private EmployeeMessageTextService employeeMessageTextService;
	
	@Override
	public BaseDao<EmployeeMessage> baseDao() {
		return employeeMassageDao;
	}

	@Override
	public Class<EmployeeMessage> getClazz() {
		return EmployeeMessage.class;
	}

	
	@Override
	public int tc_sendMessage(List<EmployeeMessage> message, EmployeeMessageText text) throws Exception {
		AttrCheckout.checkout(message, false, new String[]{"enterprisecode","sendemployeecode","sendemployeeaccount","acceptemployeecode","acceptaccount","messagetype","readstatus"});
		//写入消息内容
		employeeMessageTextService.addMessageText(text);
		//填充主键
		for (EmployeeMessage e : message) {
			e.setMessagetextcode(text.getMessagetextcode());
		}
		//批量写入消息
		super.saveRecordBatch(message);
		return 1;
	}

	@Override
	public int tc_delMessage(Integer messagecode) throws Exception {
		return super.delete(messagecode);
	}

	@Override
	public int tc_updateMStatus(Integer messagecode) throws Exception {
		EmployeeMessage __m = new EmployeeMessage();
		__m.setMessagecode(messagecode);
		__m.setReadstatus(String.valueOf(Enum_readstatus.已阅读.value));
		return super.update(__m);
	}

	
	
	

}
