package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EnterpriseBrandNoticDao;
import com.maven.entity.EnterpriseBrandNotic;
import com.maven.service.EnterpriseBrandNoticService;
import com.maven.util.AttrCheckout;

@Service
public class EnterpriseBrandNoticServiceImpl extends BaseServiceImpl<EnterpriseBrandNotic> implements EnterpriseBrandNoticService{

	@Autowired
	private EnterpriseBrandNoticDao enterpriseBrandNoticDao;
	
	@Override
	public BaseDao<EnterpriseBrandNotic> baseDao() {
		return enterpriseBrandNoticDao;
	}

	@Override
	public Class<EnterpriseBrandNotic> getClazz() {
		return EnterpriseBrandNotic.class;
	}

	@Override
	public int addNotic(Map<String, Object> object)  throws Exception{
		return super.add(AttrCheckout.checkout(object,false, new String[]{"enterprisecode","title","content","begintime","endtime"}));
	}

	@Override
	public int eidtNotic(Map<String, Object> object)  throws Exception{
		return super.update(AttrCheckout.checkout(object, false, new String[]{"noticcode"}));
	}

	@Override
	public List<EnterpriseBrandNotic> takeNotic(Map<String, Object> object)  throws Exception{
		return super.selectAll(object);
	}

	@Override
	public int takeNoticCount(Map<String, Object> object) throws Exception {
		return super.selectAllCount(object);
	}

	@Override
	public EnterpriseBrandNotic takeNoticByCode(String noticcode) throws Exception {
		return super.selectByPrimaryKey(noticcode);
	}
	
	@Override
	public int deleteNotic(Map<String,Object> object)throws Exception{
		return enterpriseBrandNoticDao.logicDelete(AttrCheckout.checkout(object, true, new String[]{"noticcode"}));
	}

	@Override
	public List<EnterpriseBrandNotic> takeUserNotic(EnterpriseBrandNotic notic) throws Exception {
		return enterpriseBrandNoticDao.showUserNotic(AttrCheckout.checkout(notic, false, new String[]{"enterprisecode"}));
	}
	

}
