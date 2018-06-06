package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeInformation;
/**
 * 用户银行信息Dao
 * @author Ethan
 *
 */
@Repository
public interface EnterpriseEmployeeInformationDao extends BaseDao<EnterpriseEmployeeInformation>{
	/**
	 * 保存用户银行信息
	 * @param enterpriseEmployeeInformation
	 */
	int saveEnterpriseEmployeeInformation(EnterpriseEmployeeInformation enterpriseEmployeeInformation);
	/**
	 * 分页查询
	 * @param obj
	 * @return List<EnterpriseEmployeeInformation>
	 */
	List<EnterpriseEmployeeInformation> findEmployeeInfo(Map<String, Object> obj) throws Exception;
	/**
	 * 查询统计
	 * @param obj
	 * @return int
	 */
	int findEmployeeInfoCount(Map<String, Object> obj);
	/**
	 * 银行信息单条记录删除
	 * @param request
	 */
	void deleteBankInfo(String code);
	/**
	 * 删除选中的一条或者多条数据
	 * @param request
	 */
	void deleteSelectBankInfo(String[] array);
	/**
	 * 查询单个实体对象
	 * @param informationcode
	 * @return EnterpriseEmployeeInformation
	 */
	EnterpriseEmployeeInformation findOneBankInfo(String informationcode);
	/**
	 * 更新用户银行信息
	 * @param enterpriseEmployeeInformation
	 * @return 
	 */
	int updateEnterpriseEmployeeInformation(EnterpriseEmployeeInformation enterpriseEmployeeInformation);
	/**
	 * 根据员工编码删除所有的银行信息
	 * @param employeecode
	 * @throws RuntimeException
	 */
	void deleteEmployeeBankInfo(String employeecode);
	/**
	 * 批量删除不同用户的银行信息
	 * @param array
	 */
	void deleteSelectEmployeeBankInfo(String[] array);
	/**
	 * 根据企业与银行卡号查询：唯一
	 * @param paymentaccount
	 * @return EnterpriseEmployeeInformation
	 */
	EnterpriseEmployeeInformation findEnterpriseEmployeeInformation(String enterprisecode,String paymentaccount);
	/**
	 * 查询当前登录用户的信息
	 * @param loginEmployee
	 * @return
	 */
	EnterpriseEmployeeInformation queryAccountData(EnterpriseEmployee loginEmployee);
	/**
	 * 根据开户名称与银行卡查询是否存在
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	EnterpriseEmployeeInformation checkBankCardIsNoExist(Map<String, Object> paramter);
	
	/**
	 * 删除用户的银行卡
	 * @param employeecode
	 * @param informationcode
	 * @return
	 */
	int deleteEmployeeBankCard(String employeecode ,String informationcode);
	
	/**
	 * 批量逻辑删除不同用户的银行信息
	 * @param array
	 */
	void logicDeleteSelectEmployeeBankInfo(String[] array);
}
