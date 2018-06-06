package com.maven.dao.impl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseEmployeeInformationDao;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.logger.TLogger;
import com.maven.privacy.PrivateDataHandle;
import com.maven.privacy.ThreadPrivateDate;
@Repository
public class EnterpriseEmployeeInformationDaoImpl extends BaseDaoImpl<EnterpriseEmployeeInformation> 
	implements EnterpriseEmployeeInformationDao {

	
	/**
	 * 保存用户银行信息
	 */
	public int saveEnterpriseEmployeeInformation(EnterpriseEmployeeInformation enterpriseEmployeeInformation) {
		return getSqlSession().insert("EnterpriseEmployeeInformation.save", enterpriseEmployeeInformation);
	}
	/**
	 * 分页查询
	 * @param obj
	 * @return List<EnterpriseEmployeeInformation>
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public List<EnterpriseEmployeeInformation> findEmployeeInfo(Map<String, Object> obj) throws Exception{
		List<EnterpriseEmployeeInformation> datas = getSqlSession().selectList("EnterpriseEmployeeInformation.selectAll", obj);
		long date = System.currentTimeMillis();
		if(ThreadPrivateDate.getIsaccessmethod()!=null&&ThreadPrivateDate.getIsaccessmethod().isAccessMethod
				&&ThreadPrivateDate.getUserprivatedata()==null){
			for (EnterpriseEmployeeInformation e : datas) {
				PrivateDataHandle.handle(e, ThreadPrivateDate.getIsaccessmethod().accessFileds);
			}
		}
		TLogger.getLogger().Info("私有数据权限控制耗时:"+(System.currentTimeMillis()-date));
		return datas;
	}
	/**
	 * 查询统计
	 * @param obj
	 * @return int
	 */
	public int findEmployeeInfoCount(Map<String, Object> obj) {
		return getSqlSession().selectOne("EnterpriseEmployeeInformation.selectAllCount", obj);
	}
	/**
	 * 银行信息单条记录删除
	 * @param request
	 */
	public void deleteBankInfo(String code) {
		getSqlSession().delete("EnterpriseEmployeeInformation.deleteBankInfo", code);
	}
	/**
	 * 删除选中的一条或者多条数据
	 * @param request
	 */
	public void deleteSelectBankInfo(String[] array) {
		getSqlSession().delete("EnterpriseEmployeeInformation.deleteSelectBankInfo", array);
	}
	/**
	 * 查询单个实体对象
	 * @param informationcode
	 * @return EnterpriseEmployeeInformation
	 */
	public EnterpriseEmployeeInformation findOneBankInfo(String informationcode) {
		return getSqlSession().selectOne("EnterpriseEmployeeInformation.findOneBankInfo", informationcode);
	}
	/**
	 * 更新用户银行信息
	 * @param enterpriseEmployeeInformation
	 */
	public int updateEnterpriseEmployeeInformation(EnterpriseEmployeeInformation enterpriseEmployeeInformation) {
		return getSqlSession().update("EnterpriseEmployeeInformation.updateBankInfo", enterpriseEmployeeInformation);
	}
	/**
	 * 根据员工编码删除所有的银行信息
	 * @param employeecode
	 * @throws RuntimeException
	 */
	@Override
	public void deleteEmployeeBankInfo(String employeecode) {
		getSqlSession().delete("EnterpriseEmployeeInformation.deleteEmployeeBankInfo", employeecode);
	}
	/**
	 * 批量删除不同用户的银行信息
	 * @param array
	 */
	@Override
	public void deleteSelectEmployeeBankInfo(String[] array) {
		getSqlSession().delete("EnterpriseEmployeeInformation.deleteManyEmployeeBankInfo", array);
	}
	/**
	 * 根据银行卡查询单个实体对象
	 * @param paymentaccount
	 * @return EnterpriseEmployeeInformation
	 */
	@Override
	public EnterpriseEmployeeInformation findEnterpriseEmployeeInformation(String enterprisecode,String paymentaccount) {
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("enterprisecode", enterprisecode);
		object.put("paymentaccount", paymentaccount);
		return getSqlSession().selectOne("EnterpriseEmployeeInformation.findEnterpriseEmployeeInformation", object);
	}
	/**
	 * 查询当前登录用户的信息
	 * @param loginEmployee
	 * @return
	 */
	@Override
	public EnterpriseEmployeeInformation queryAccountData(EnterpriseEmployee loginEmployee) {
		return getSqlSession().selectOne("EnterpriseEmployeeInformation.queryAccountData", loginEmployee);
	}
	
	/**
	 * 根据开户名称与银行卡查询是否存在
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	@Override
	public EnterpriseEmployeeInformation checkBankCardIsNoExist(Map<String, Object> paramter) {
		return getSqlSession().selectOne("EnterpriseEmployeeInformation.select", paramter);
	}
	
	/**
	 * 删除用户的银行卡
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteEmployeeBankCard(String employeecode ,String informationcode) {
		EnterpriseEmployeeInformation __eei = new EnterpriseEmployeeInformation();
		__eei.setEmployeecode(employeecode);
		__eei.setInformationcode(informationcode);
		return getSqlSession().update("EnterpriseEmployeeInformation.deleteEmployeeBankCard", __eei);
	}
	
	/**
	 * 逻辑批量删除不同用户的银行信息
	 * @param array
	 */
	@Override
	public void logicDeleteSelectEmployeeBankInfo(String[] array) {
		getSqlSession().delete("EnterpriseEmployeeInformation.logicDeleteManyEmployeeBankInfo", array);
	}
	
	
}
