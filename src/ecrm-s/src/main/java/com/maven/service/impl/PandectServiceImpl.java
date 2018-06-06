package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.config.SpringContextUtil;
import com.maven.dao.PandectDao;
import com.maven.service.PandectService;
import com.maven.util.AttrCheckout;

@Service
public class PandectServiceImpl implements PandectService{
	
	@Autowired
	private PandectDao pandectDao; 

	@Override
	public List<Map<String, Object>> usp_performanceForBrand(Map<String, Object> object) throws Exception {
		return pandectDao.usp_performanceForBrand(AttrCheckout.checkout(object, false, new String[]{"enterprisereport","enterprisecode","employeecode","begintime","endtime"}));
	}


	@Override
	public List<Map<String, Object>> usp_useractivity(Map<String, Object> object) throws Exception {
		return pandectDao.usp_useractivity(AttrCheckout.checkout(object, false, new String[]{"enterprisereport","employeecode"}));
	}


	@Override
	public BigDecimal usp_balance(Map<String, Object> object) throws Exception {
		return pandectDao.usp_balance(AttrCheckout.checkout(object, false, new String[]{"enterprisereport","employeecode"}));
	}


	@Override
	public BigDecimal usp_takeLoseWin(Map<String, Object> object) throws Exception {
		return pandectDao.usp_takeLoseWin(AttrCheckout.checkout(object, false, new String[]{"enterprisereport","employeecode","begintime","endtime"}));
	}


	@Override
	public BigDecimal usp_savemoney(Map<String, Object> object) throws Exception {
		return pandectDao.usp_savemoney(AttrCheckout.checkout(object, false, new String[]{"enterprisereport","employeecode","begintime","endtime"}));
	}


	@Override
	public BigDecimal usp_takemoney(Map<String, Object> object) throws Exception {
		return pandectDao.usp_takemoney(AttrCheckout.checkout(object, false, new String[]{"enterprisereport","employeecode","begintime","endtime"}));
	}


	@Override
	public List<Map<String, Object>> usp_performance(Map<String, Object> object) throws Exception {
		return pandectDao.usp_performance(AttrCheckout.checkout(object, false, new String[]{"enterprisereport","employeecode","begintime","endtime"}));
	}
	
	@Override
	public Map<String, Object> usp_takemoney_inspect(Map<String, Object> object)  throws Exception {
		return pandectDao.usp_takemoney_inspect(AttrCheckout.checkout(object, false, new String[]{"ordernumber"}));
	}

	public static void main(String[] args) {
		PandectService service = SpringContextUtil.getBean("pandectServiceImpl");
		
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("ordernumber", "A4544AAF10D24ABD9BB6D293E624BFE6");
		try {
			Map<String, Object> map = service.usp_takemoney_inspect(object);
				for (String key : map.keySet()) {
					System.out.println(key+":"+map.get(key));
				}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}




}
