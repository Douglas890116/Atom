package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.LoginWhiteListDao;
import com.maven.entity.LoginWhiteList;
import com.maven.service.LoginWhiteListService;

@Service
public class LoginWhiteListServiceImpl extends BaseServiceImpl<LoginWhiteList> implements LoginWhiteListService {

	@Autowired
	private LoginWhiteListDao loginWhiteListDao;
	
	@Override
	public BaseDao<LoginWhiteList> baseDao() {
		return loginWhiteListDao;
	}

	@Override
	public Class<LoginWhiteList> getClazz() {
		return LoginWhiteList.class;
	}

	/**
	 * 查询指定企业下面的全部白名单
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public List<LoginWhiteList> queryByEnterprisecode(Map<String, Object> object) throws Exception {
		return loginWhiteListDao.queryByEnterprisecode(object);
	}
	
	/**
	 * 批量删除
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void deleteSelectIp(String[] array) throws Exception {
		loginWhiteListDao.deleteSelectIp(array);
	}

	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void saveLoginWhiteList(LoginWhiteList obj) throws Exception {
		loginWhiteListDao.saveLoginWhiteList(obj);
	}
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void updateLoginWhiteList(LoginWhiteList obj) throws Exception {
		loginWhiteListDao.updateLoginWhiteList(obj);
	}
}
