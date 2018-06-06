package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.dao.EmployeeMoneyChangeTypeDao;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.service.EmployeeMoneyChangeTypeService;
import com.maven.util.AttrCheckout;
@Service
public class EmployeeMoneyChangeTypeServiceImpl implements EmployeeMoneyChangeTypeService {
	
	@Autowired
	private EmployeeMoneyChangeTypeDao employeeMoneyChangeTypeDaoImpl;
	
	/**
	 * 根据资金类型编码查询
	 * @param moneyChangeTypeCode
	 * @return EmployeeMoneyChangeType
	 */
	@Override
	public EmployeeMoneyChangeType getEmployeeMoneyChangeType(String moneyChangeTypeCode) throws Exception {
		return employeeMoneyChangeTypeDaoImpl.getEmployeeMoneyChangeType(moneyChangeTypeCode);
	}
	
	/**
	 * 根据条件查询
	 * @param map
	 * @return List<EmployeeMoneyChangeType>
	 */
	@Override
	public List<EmployeeMoneyChangeType> findEmployeeMoneyChangeTypeData(Map<String, Object> map) throws Exception {
		return employeeMoneyChangeTypeDaoImpl.findEmployeeMoneyChangeTypeData(map);
	}
	
	/**
	 * 根据条件查询统计
	 * @param map
	 * @return int
	 */
	@Override
	public int findEmployeeMoneyChangeTypeDataCount(Map<String, Object> map) throws Exception {
		return employeeMoneyChangeTypeDaoImpl.findEmployeeMoneyChangeTypeDataCount(map);
	}
	
	/**
	 * 根据编码删除
	 * @param moneyChangeTypeCode
	 */
	@Override
	public void deleteEmployeeMoneyChangeType(String moneyChangeTypeCode) throws Exception {
		employeeMoneyChangeTypeDaoImpl.deleteEmployeeMoneyChangeType(moneyChangeTypeCode);
	}
	
	/**
	 * 根据编码批量删除
	 * @param array
	 */
	@Override
	public void deleteSelectEmployeeMoneyChangeType(String[] array) throws Exception {
		employeeMoneyChangeTypeDaoImpl.deleteSelectEmployeeMoneyChangeType(array);
	}
	
	/**
	 * 保存资金类型
	 * @param employeeMoneyChangeType
	 */
	@Override
	public void saveEmployeeMoneyChangeType(EmployeeMoneyChangeType employeeMoneyChangeType) throws Exception {
		employeeMoneyChangeTypeDaoImpl.saveEmployeeMoneyChangeType(
				AttrCheckout.checkout(employeeMoneyChangeType, false, new String[]{"moneychangetypecode","moneychangetype","moneyinouttype"}));
	}
	
	/**
	 * 修改资金类型
	 * @param employeeMoneyChangeType
	 */
	@Override
	public void updateEmployeeMoneyChangeType(EmployeeMoneyChangeType employeeMoneyChangeType) throws Exception {
		employeeMoneyChangeTypeDaoImpl.updateEmployeeMoneyChangeType(
				AttrCheckout.checkout(employeeMoneyChangeType, false, new String[]{"moneychangetypecode"}));
	}
	/**
	 * 根据活动类别查询对应的活动类型
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EmployeeMoneyChangeType> findEmployeeMoneyChangeType(Map<String, Object> map) throws Exception {
		return employeeMoneyChangeTypeDaoImpl.findEmployeeMoneyChangeType(map);
	}

}
