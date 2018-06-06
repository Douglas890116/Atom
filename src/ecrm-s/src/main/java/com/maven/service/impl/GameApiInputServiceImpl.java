package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.GameApiInputDao;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.GameApiInput;
import com.maven.service.BrandDomainService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.GameApiInputService;
import com.maven.util.AttrCheckout;

@Service
public class GameApiInputServiceImpl extends BaseServiceImpl<GameApiInput> implements GameApiInputService {

	@Autowired
	private GameApiInputDao gameApiInputDao;

	@Autowired
	private EnterpriseGameService enterpriseGameService;
	
	@Autowired
	private EmployeeGamecataloyBonusService bonusService;
	
	@Autowired
	private BrandDomainService brandDomainService;

	@Override
	public BaseDao<GameApiInput> baseDao() {
		return gameApiInputDao;
	}

	@Override
	public Class<GameApiInput> getClazz() {
		return GameApiInput.class;
	}

	@Override
	public List<GameApiInput> getAllGames() throws Exception {
		return super.select(null);
	}

	@Override
	public GameApiInput takeGameAPI(String enterprisecode) throws Exception {
		GameApiInput ai = new GameApiInput();
		ai.setEnterprisecode(enterprisecode);
		return super.selectFirst(ai);
	}

	@Override
	public void addGameInputApi(GameApiInput api, List<EnterpriseGame> games) throws Exception {
		if (api == null) return;
		super.add(AttrCheckout.checkout(api, false,
				new String[] { "platformcode", "enterprisecode", "apiurl", "outputapistatus", "apikey1", "apikey2" }));
		enterpriseGameService.savaEnterpriseGameAccredit(api.getEnterprisecode(), games);
	}

	public void updateGameInputApi(GameApiInput api, List<EnterpriseGame> gamesAll, List<EnterpriseGame> thisAddList) throws Exception {
		if (api == null) return;
		super.update(AttrCheckout.checkout(api, false, new String[] { "apicode", "enterprisecode", "platformcode",
				"apiurl", "outputapistatus", "apikey1", "apikey2" }));
		
		
		enterpriseGameService.savaEnterpriseGameAccredit(api.getEnterprisecode(), gamesAll);
		System.out.println("updateGameInputApi=品牌数据已经处理完毕");
		
		if(thisAddList != null && thisAddList.size() > 0) {
			System.out.println("updateGameInputApi=本次新增游戏："+thisAddList.size());
			
			
			//更改对应所有会员站点域名的洗码设置（如果不更新，新注册进来的会员还是会没有洗码设置）
			brandDomainService.updateDomainGameCategoryBonus(api.getEnterprisecode(), thisAddList);
			System.out.println("updateGameInputApi=新增游戏，所有域名洗码比例已经初始化");
			
			/*数量太大时总是失败
			 * 
			//初始化新加的游戏的会员洗码
			bonusService.updateEmployeeGameCategoryBonus(api.getEnterprisecode(), thisAddList);
			System.out.println("updateGameInputApi=新增游戏，会员洗码比例已经添加完毕");
			*/
		}
		
	}

}
