package com.maven.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityTemplateDao;
import com.maven.dao.EnterpriseActivityCustomizationDao;
import com.maven.dao.EnterpriseBrandActivityDao;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseBrandActivity.ApiBrandParameter;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.util.ActivityUtils.ActivityCheckMessage;
import com.maven.util.AttrCheckout;

@Service
public class EnterpriseBrandActivityServiceImpl extends BaseServiceImpl<EnterpriseBrandActivity> implements EnterpriseBrandActivityService{

	@Autowired
	private EnterpriseBrandActivityDao enterpriseBrandActivityDao;
	@SuppressWarnings("unused")
	@Autowired
	private ActivityTemplateDao activityTemplateDao;
	@SuppressWarnings("unused")
	@Autowired
	private EnterpriseActivityCustomizationDao enterpriseActivityCustomizationDao;
	
	@Override
	public BaseDao<EnterpriseBrandActivity> baseDao() {
		return enterpriseBrandActivityDao;
	}

	@Override
	public Class<EnterpriseBrandActivity> getClazz() {
		return EnterpriseBrandActivity.class;
	}

	@Override
	public int saveEnterpriseActivity(EnterpriseBrandActivity activity) throws Exception {
		return super.add(AttrCheckout.checkout(activity, false, new String[]{"enterprisecode","ecactivitycode","begintime","endtime"}));
	}

	@Override
	public int editEnterpriseActivity(EnterpriseBrandActivity activity) throws Exception {
		return super.update(AttrCheckout.checkout(activity, false, new String[]{"enterprisebrandactivitycode"}));
	}

	@Override
	public int deleteEnterpriseActivity(List<String> activitycodes) throws Exception {
		return enterpriseBrandActivityDao.deleteBatch(activitycodes);
	}

	@Override
	public EnterpriseBrandActivity checkEnterpriseBrandActivity(Integer brandactivitycode) throws Exception{
		//规则 1根据品牌关联活动主键获取品牌活动 2检查活动是否有效
		//1
		EnterpriseBrandActivity eba = enterpriseBrandActivityDao.selectByPrimaryKey("EnterpriseBrandActivity.selectByPrimaryKey", brandactivitycode.toString());
		//2
		if (eba == null){
			/*acresult.setResult(false);
			acresult.setCode(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.value);
			acresult.setMessage(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
			return null;*/
			throw new LogicTransactionRollBackException(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
		}
		if (eba.getEndtime().before(new Date())){
			/*acresult.setResult(false);
			acresult.setCode(ActivityCheckMessage.ACTIVITY_TIMEEND.value);
			acresult.setMessage(ActivityCheckMessage.ACTIVITY_TIMEEND.desc);
			return null;*/
			throw new LogicTransactionRollBackException(ActivityCheckMessage.ACTIVITY_TIMEEND.desc);
		}
		return eba;
	}

	@Override
	public Map<String, Object> selectActivityAgument(int __enterprisebrandactivitycode) {
		List<Map<String,Object>>  __activityAgument = enterpriseBrandActivityDao.selectActivityAgument(__enterprisebrandactivitycode);
		Map<String,Object> __augmentMapping = new HashMap<String, Object>();
		for (Map<String, Object> __augment : __activityAgument) {
			if (__augment == null || __augment.isEmpty()){
				continue;
			}
			__augmentMapping.put(String.valueOf(__augment.get("agementname")), __augment.get("agementvalue"));
		}
		return __augmentMapping;
	}

	@Override
	public List<Map<String, Object>> selectActivityByBrand(String brandcode) throws Exception {
		List<Map<String, Object>> brandactivitys = enterpriseBrandActivityDao.selectActivityByBrand(brandcode);
		for (Map<String, Object> map : brandactivitys) {
			Integer code = Integer.valueOf(map.get("enterprisebrandactivitycode").toString());
			/*活动参数列表*/
			List<Map<String,Object>>  __activityAgument = enterpriseBrandActivityDao.selectActivityAgument(code);
			List<ApiBrandParameter> brandparameters = new ArrayList<EnterpriseBrandActivity.ApiBrandParameter>();
			for (Map<String, Object> __augment : __activityAgument) {
				if (__augment == null || __augment.isEmpty()){
					continue;
				}
				ApiBrandParameter abp = new EnterpriseBrandActivity().new ApiBrandParameter();
				abp.setAgementname(String.valueOf(__augment.get("agementname")));
				abp.setAgementvalue(String.valueOf(__augment.get("agementvalue")));
				abp.setAgementdesc(String.valueOf(__augment.get("agementdesc")));
				brandparameters.add(abp);
			}
			
			map.put("parameters", brandparameters);
			map.put("begintime", map.get("begintime").toString());
			map.put("endtime", map.get("endtime").toString());
			map.remove("ecactivitycode");
		}
		return brandactivitys;
	}
}
