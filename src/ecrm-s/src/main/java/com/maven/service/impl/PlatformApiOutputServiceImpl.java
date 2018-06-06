package com.maven.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.constant.Constant;
import com.maven.dao.PlatformApiOutputDao;
import com.maven.entity.PlatformApiOutput;
import com.maven.exception.ArgumentValidationException;
import com.maven.service.PlatformApiOutputService;
import com.maven.util.AttrCheckout;
import com.maven.util.RandomString;

@Service
public class PlatformApiOutputServiceImpl extends BaseServiceImpl<PlatformApiOutput>implements PlatformApiOutputService {

	@Autowired 
	private PlatformApiOutputDao platformApiOutputDao;
	
	@Override
	public BaseDao<PlatformApiOutput> baseDao() {
		return platformApiOutputDao;
	}

	@Override
	public Class<PlatformApiOutput> getClazz() {
		return PlatformApiOutput.class;
	}

	@Override
	public PlatformApiOutput takeConfigUseOutputapicode(String outputapicode) throws Exception {
		if(outputapicode==null) throw  new ArgumentValidationException("parameter [ outputapicode ] is null");
		return super.selectFirst(new PlatformApiOutput(null,outputapicode));
	}
	
	@Override
	public PlatformApiOutput takeConfigUseEnterprisecode(String enterprisecode) throws Exception {
		if(enterprisecode==null) throw  new ArgumentValidationException("parameter [ enterprisecode ] is null");
		return super.selectFirst(new PlatformApiOutput(enterprisecode,null));
	}

	@Override
	public int addBrandApiConfig(PlatformApiOutput object) throws Exception {
		AttrCheckout.checkout(object, false, new String[]{"enterprisecode"});
		object.setOutputapicode(RandomString.UUID());
		object.setOutputapistatus(Constant.BooleanByte.YES.byteValue());
		object.setApikey1(RandomString.createRandomString(16));
		object.setApikey2(RandomString.createRandomString(16));
		return super.add(object);
	}

	

}
