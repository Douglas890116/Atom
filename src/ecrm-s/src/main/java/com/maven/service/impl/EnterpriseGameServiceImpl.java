package com.maven.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.EnterpriseGameDao;
import com.maven.entity.ApiSoltGametype;
import com.maven.entity.ApiSoltGametypeEnterprise;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.Game;
import com.maven.service.ApiSoltGametypeEnterpriseService;
import com.maven.service.ApiSoltGametypeService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseOperatingBrandGameService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.GameService;
import com.maven.util.AttrCheckout;

@Service
public class EnterpriseGameServiceImpl extends BaseServiceImpl<EnterpriseGame> implements EnterpriseGameService{

	@Autowired
	private EnterpriseGameDao enterpriseGameDao;
	
	@Autowired
	private EnterpriseOperatingBrandGameService enterpriseOperatingBrandGameService;
	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;
	@Autowired
	private ApiSoltGametypeEnterpriseService apiSoltGametypeEnterpriseService;
	@Autowired
	private ApiSoltGametypeService apiSoltGametypeService;
	@Autowired
	private GameService gameService;
	
	@Override
	public BaseDao<EnterpriseGame> baseDao() {
		return enterpriseGameDao;
	}

	@Override
	public Class<EnterpriseGame> getClazz() {
		return EnterpriseGame.class;
	}
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseGame> selectAll(Map<String,Object> params)throws Exception {
		return enterpriseGameDao.selectAll("EnterpriseGame.selectAll", params);
	}
	
	/**
	 * 统计总数
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int selectAllCount(Map<String,Object> object) throws Exception {
		return enterpriseGameDao.selectAllCount("EnterpriseGame.selectAllCount", object);
	}

	@Override
	public void savaEnterpriseGameAccredit(String enterprisecode, List<EnterpriseGame> enterprisegames) throws Exception {
		AttrCheckout.checkout(enterprisegames, false, new String[]{"enterprisecode","gamecode"});
		super.delete(enterprisecode);
		if(enterprisegames!=null && enterprisegames.size()!=0){
			super.saveRecordBatch(enterprisegames);
		}
		
		
		/*********更新企业品牌游戏数据**********/
		List<EnterpriseOperatingBrand> listbrand = enterpriseOperatingBrandService.getEnterpriseBrand(enterprisecode);
		for (EnterpriseOperatingBrand enterpriseOperatingBrand : listbrand) {
			
			String brandcode = enterpriseOperatingBrand.getBrandcode();
			List<EnterpriseOperatingBrandGame> list = new ArrayList<EnterpriseOperatingBrandGame>();
			for (EnterpriseGame enterpriseGame : enterprisegames) {
				list.add(new EnterpriseOperatingBrandGame(brandcode, enterpriseGame.getGamecode(), EnterpriseOperatingBrandGame.Enum_flag.正常.value));
			}
			
			enterpriseOperatingBrandGameService.tc_brandGameAccredit(brandcode, list);
		}
		
		
		
		
		
		//操作企业的老虎机（此逻辑暂时先取消。因为目前该表还没有真正用起来.jason20170620）
		/****
		
		
		//先清除该企业的游戏配置数据
		apiSoltGametypeEnterpriseService.deleteByEnterprisecode(enterprisecode);
		
		//重新批量添加
		for (EnterpriseGame enterpriseGame : enterprisegames) {
			Game game = gameService.getGame(enterpriseGame.getGamecode());
			
			ApiSoltGametype temp = new ApiSoltGametype();
			temp.setBiggametype(game.getGametype());
			temp.setIsweb("0");//
			List<ApiSoltGametype> list = apiSoltGametypeService.select(temp);
			
			for (ApiSoltGametype apiSoltGametype : list) {
				
				ApiSoltGametypeEnterprise item = new ApiSoltGametypeEnterprise();
				item.setBiggametype(apiSoltGametype.getBiggametype());
				item.setEnterprisecode(enterprisecode);
				item.setGametypeId(apiSoltGametype.getLsh());
				item.setStatus(Integer.valueOf(ApiSoltGametypeEnterprise.Enum_status.启用.value));
				apiSoltGametypeEnterpriseService.addActivityBetRecord(item);
				
			}
		}
		*/
	}

	@Override
	public Map<String,EnterpriseGame> takeEnterpriseGame(String enterprisecode) throws Exception {
		List<EnterpriseGame> _list_enterprisegames =  super.select(new EnterpriseGame(null,enterprisecode,null));
		Map<String,EnterpriseGame> _map_enterprisegames = new HashMap<String, EnterpriseGame>();
		for (EnterpriseGame e : _list_enterprisegames) {
			_map_enterprisegames.put(e.getGamecode(), e);
		}
		return _map_enterprisegames;
	}

}
