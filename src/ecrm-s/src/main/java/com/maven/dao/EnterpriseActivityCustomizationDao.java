package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseActivityCustomization;

@Repository
public interface EnterpriseActivityCustomizationDao extends BaseDao<EnterpriseActivityCustomization>{
    
    /**
     * 根据企业编码、活动模板查询企业是否有该活动
     * @param enterprisecode
     * @param activitytemplatecode
     * @return
     */
    List<EnterpriseActivityCustomization> checkEnterpriseActivity(String enterprisecode, int activitytemplatecode) throws Exception;
    
    /**
     * 通过企业活动编码查询企业自定义活动
     * @return
     * @throws Exception
     */
    EnterpriseActivityCustomization selectByEnterprisebrandactivitycode(String __enterprisebrandactivitycode) throws Exception;
    
    /**
     * 根据企业编码查找所有活动
     * @param enterprisecode
     * @return
     * @throws Exception
     */
    List<EnterpriseActivityCustomization> selectAllByEnterprisecode(String enterprisecode) throws Exception;
}
