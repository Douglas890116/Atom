package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EnterpriseOperatingBrandGame;

@Service
public interface EnterpriseOperatingBrandGameService {
	
	/**
	 * 开放品牌拥有的游戏
	 * @see brandcode   品牌编码
	 * @see List<EnterpriseOperatingBrandGame> object  对象必须参数 "brandcode","apicode"
	 * @return 
	 */
	@DataSource("master")
	public int tc_brandGameAccredit(String brandcode,List<EnterpriseOperatingBrandGame> object) throws Exception;
	
	@DataSource("master")
	public int addListData(String brandcode,List<EnterpriseOperatingBrandGame> object) throws Exception;
	/**
	 * 获取品牌下的游戏
	 * @param object 必须参数 "brandcode"
	 * @return
	 */
	@DataSource("slave")
	public List<EnterpriseOperatingBrandGame> takeBrandGames(EnterpriseOperatingBrandGame object) throws Exception;
	
	/**
	 * 更新
	 */
	@DataSource("master")
	public int update(EnterpriseOperatingBrandGame data) throws Exception ;
	
	/**
	 * 批量更新数据
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int updateListData(List<EnterpriseOperatingBrandGame> object) throws Exception ;
	
	/**
	 * 删除企业取消授权的API游戏
	 * @param enterprisecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int deleteEnterpriseGame(String enterprisecode)throws Exception;
	
	@DataSource("master")
	public int deleteGamecode(String gamecode) throws Exception;
	
}
