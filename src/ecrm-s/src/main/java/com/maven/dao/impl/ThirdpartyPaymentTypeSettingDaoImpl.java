package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ThirdpartyPaymentTypeSettingDao;
import com.maven.entity.ThirdpartyPaymentTypeSetting;
@Repository
public class ThirdpartyPaymentTypeSettingDaoImpl extends BaseDaoImpl<ThirdpartyPaymentTypeSetting> 
	implements ThirdpartyPaymentTypeSettingDao {
	
	/**
	 * 根据支付类型编码查询所对应的属性值
	 * @param thirdpartyPaymentTypeCode
	 * @return
	 */
	@Override
	public List<ThirdpartyPaymentTypeSetting> takeThirdpartyPaymentTypeSetting(String thirdpartyPaymentTypeCode) {
		return getSqlSession().selectList("ThirdpartyPaymentTypeSetting.loadThirdpartyPaymentTypeSetting", thirdpartyPaymentTypeCode);
	}

}
