package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.EnterpriseThirdpartyPaymentAgumentDao;
import com.maven.dao.EnterpriseThirdpartyPaymentDao;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.EnterpriseThirdpartyPaymentAgument;
import com.maven.service.EnterpriseThirdpartyPaymentService;
import com.maven.util.AttrCheckout;
import com.maven.utility.ClassUtil;
@Service
public class EnterpriseThirdpartyPaymentServiceImpl extends BaseServiceImpl<EnterpriseThirdpartyPayment> 
	implements EnterpriseThirdpartyPaymentService {
	
	@Autowired
	private EnterpriseThirdpartyPaymentDao enterpriseThirdpartyPaymentDao;
	@Autowired
	private EnterpriseThirdpartyPaymentAgumentDao enterpriseThirdpartyPaymentAgumentDao;
	
	/**
	 * 根据条件查询所有
	 * @param map
	 * @return
	 */
	@Override
	public List<EnterpriseThirdpartyPayment> findAll(Map<String, Object> map) throws Exception {
		return enterpriseThirdpartyPaymentDao.findAll(map);
	}
	
	/**
	 * 根据条件查询统计所有
	 * @param map
	 * @return
	 */
	@Override
	public int findCountAll(Map<String, Object> map) throws Exception {
		return enterpriseThirdpartyPaymentDao.findCountAll(map);
	}
	
	/**
	 * 单条记录删除
	 * @param md5value
	 */
	@Override
	public void tc_delete(String enterpriseThirdpartyCode) throws Exception {
		enterpriseThirdpartyPaymentDao.delete(enterpriseThirdpartyCode);
	}

	/**
	 * 批量删除
	 * @param array
	 */
	@Override
	public void tc_deleteSelect(String[] array) throws Exception {
		enterpriseThirdpartyPaymentDao.deleteSelect(array);
	}

	/**
	 * 启用与禁用方法
	 * @param request
	 * @return
	 */
	@Override
	public void tc_enableDisable(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment) throws Exception {
		enterpriseThirdpartyPaymentDao.enableDisable(enterpriseThirdpartyPayment);
	}
	
	/**
	 * 保存
	 * @param EnterpriseThirdpartyPayment
	 * @return
	 * */
	@Override
	public void tc_save(EnterpriseThirdpartyPayment __ETPayment,List<EnterpriseThirdpartyPaymentAgument> __ETPaymentAguments) throws Exception {
		//获取主键编码
		String __Enterprisethirdpartycode =  enterpriseThirdpartyPaymentDao.takePrimariyKey();
		//调用保存属性值方法 
		__ETPayment.setEnterprisethirdpartycode(__Enterprisethirdpartycode);
		enterpriseThirdpartyPaymentDao.save(__ETPayment);
		for (EnterpriseThirdpartyPaymentAgument __agument : __ETPaymentAguments) {
			__agument.setEnterprisethirdpartycode(__Enterprisethirdpartycode);
		}
		//调用参数值保存方法
		enterpriseThirdpartyPaymentAgumentDao.save(__ETPaymentAguments);
	}
	
	/**
	 * 修改
	 * @param enterpriseThirdpartyPayment
	 */
	public void update(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment)throws Exception {
		enterpriseThirdpartyPaymentDao.update(ClassUtil.getMapId(getClazz(), new Throwable()), enterpriseThirdpartyPayment);
	}
	
	/**
	 * 设置公司默认出款卡
	 * @param enterpriseThirdpartyPayment
	 */
	@Override
	public void tc_update(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment) throws Exception {
		enterpriseThirdpartyPaymentDao.update(enterpriseThirdpartyPayment);
	}

	@Override
	public List<EnterpriseThirdpartyPayment> takeEnterpriseThirdpartypayment(String enterprisecode)throws Exception  {
		return enterpriseThirdpartyPaymentDao.takeEnterpriseThirdpartypayment(enterprisecode);
	}

	@Override
	public BaseDao<EnterpriseThirdpartyPayment> baseDao() {
		return enterpriseThirdpartyPaymentDao;
	}

	@Override
	public Class<EnterpriseThirdpartyPayment> getClazz() {
		return EnterpriseThirdpartyPayment.class;
	}

	@Override
	public void updateCurrentBalance(EnterpriseThirdpartyPayment etp) throws Exception {
		enterpriseThirdpartyPaymentDao.updateCurrentBalance(AttrCheckout.checkout(etp, false, new String[]{"enterprisethirdpartycode","enterprisecode","currentbalance"}));
	}


}
