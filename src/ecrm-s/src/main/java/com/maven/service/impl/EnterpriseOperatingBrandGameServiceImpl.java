package com.maven.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.EnterpriseOperatingBrandGameDao;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.service.EnterpriseOperatingBrandGameService;
import com.maven.util.AttrCheckout;

@Service
public class EnterpriseOperatingBrandGameServiceImpl extends BaseServiceImpl<EnterpriseOperatingBrandGame> implements EnterpriseOperatingBrandGameService{

	@Autowired
	private EnterpriseOperatingBrandGameDao enterpriseOperatingBrandGameDao; 
	
	@Override
	public BaseDao<EnterpriseOperatingBrandGame> baseDao() {
		return enterpriseOperatingBrandGameDao;
	}

	@Override
	public Class<EnterpriseOperatingBrandGame> getClazz() {
		return EnterpriseOperatingBrandGame.class;
	}

	/**
	 * 授权品牌开放游戏
	 */
	@Override
	public int addListData(String brandcode,List<EnterpriseOperatingBrandGame> object) throws Exception {
		if(object.size()>0){
			return super.saveRecordBatch(object);
		}
		return 0;
	}
	
	/**
	 * 批量更新数据
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int updateListData(List<EnterpriseOperatingBrandGame> object) throws Exception {
		return enterpriseOperatingBrandGameDao.updateListData(object);
	}
	
	/**
	 * 更新
	 */
	public int update(EnterpriseOperatingBrandGame data) throws Exception  {
		return super.update(data);
	}
	
	/**
	 * 授权品牌开放游戏
	 */
	@Override
	public int tc_brandGameAccredit(String brandcode,List<EnterpriseOperatingBrandGame> object) throws Exception {
//		AttrCheckout.checkout(object,false, new String[]{"brandcode","gamecode"});
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("brandcode", brandcode);
		/**/
		super.delete(params);
		if(object.size()>0){
			super.saveRecordBatch(object);
		}
		
		
		return 1;
	}

	/**
	 * 获取品牌下所有游戏
	 */
	@Override
	public List<EnterpriseOperatingBrandGame> takeBrandGames(EnterpriseOperatingBrandGame object) throws Exception {
		AttrCheckout.checkout(object,false, new String[]{"brandcode"});
		return super.select(object);
	}

	@Override
	public int deleteEnterpriseGame(String enterprisecode) throws Exception {
		return enterpriseOperatingBrandGameDao.deleteEnterpriseGame(enterprisecode);
	}
	@Override
	public int deleteGamecode(String gamecode) throws Exception {
		return enterpriseOperatingBrandGameDao.deleteGamecode(gamecode);
	}
	

}
