package com.maven.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityTemplateDao;
import com.maven.dao.EnterpriseActivityCustomizationDao;
import com.maven.entity.ActivityTemplate;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.util.ActivityUtils.ActivityCheckMessage;
import com.maven.util.ActivityUtils.ActivityCheckResult;
import com.maven.util.AttrCheckout;

@Service
public class EnterpriseActivityCustomizationServiceImpl extends BaseServiceImpl<EnterpriseActivityCustomization> 
    implements EnterpriseActivityCustomizationService{

    @Autowired
    private EnterpriseActivityCustomizationDao enterpriseActivityCustomizationDao;
    @Autowired
    private ActivityTemplateDao activityTemplateDao;
    

    @Override
    public BaseDao<EnterpriseActivityCustomization> baseDao() {
        return enterpriseActivityCustomizationDao;
    }

    @Override
    public Class<EnterpriseActivityCustomization> getClazz() {
        return EnterpriseActivityCustomization.class;
    }

    @Override
    public int saveCActivity(EnterpriseActivityCustomization activity) throws Exception {
        return super.add(AttrCheckout.checkout(activity,false,new String[]{"enterprisecode","activitytemplatecode","activityname","activityimage","activitycontent"}));
    }

    @Override
    public int updateCActivity(EnterpriseActivityCustomization activity) throws Exception {
        return super.update(AttrCheckout.checkout(activity, false, new String[]{"ecactivitycode"}));
    }

    @Override
    public int tc_logicDelete(List<String> ids) throws Exception {
        return super.logicDelete(ids);
    }

    @Override
    public List<EnterpriseActivityCustomization> checkEnterpriseActivity(String enterprisecode, String activitytemplatename, ActivityCheckResult acresult) throws Exception {
        ActivityTemplate at = activityTemplateDao.getByName(activitytemplatename);
        if (at == null || StringUtils.isEmpty(at.getName())){
            acresult.setResult(false);
            acresult.setCode(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.value);
            acresult.setMessage(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
            return null;
        }
        List<EnterpriseActivityCustomization> eac_list = enterpriseActivityCustomizationDao.checkEnterpriseActivity(enterprisecode, at.getActivitycode());
        if (eac_list == null){
            acresult.setResult(false);
            acresult.setCode(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.value);
            acresult.setMessage(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
            return null;
        }
        return eac_list;
    }

    @Override
    public EnterpriseActivityCustomization selectByEnterprisebrandactivitycode(String __enterprisebrandactivitycode)
            throws Exception {
        return enterpriseActivityCustomizationDao.selectByEnterprisebrandactivitycode(__enterprisebrandactivitycode);
    }

    @Override
    public List<EnterpriseActivityCustomization> selectAllByEnterprisecode(String enterprisecode) throws Exception {
        return enterpriseActivityCustomizationDao.selectAllByEnterprisecode(enterprisecode);
    }

}