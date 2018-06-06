package com.maven.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.dao.BrandDomainDao;
import com.maven.dao.GameCategoryDao;
import com.maven.dao.GameDao;
import com.maven.entity.AgentSiteContact;
import com.maven.entity.BrandDomain;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.GameCategory;
import com.maven.entity.BrandDomain.Enum_domaintype;
import com.maven.entity.BrandDomain.Enum_linkstatus;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.TLogger;
import com.maven.service.AgentSiteContactService;
import com.maven.service.BrandDomainService;
import com.maven.util.AttrCheckout;

@Service
public class BrandDomainServiceImpl extends BaseServiceImpl<BrandDomain>
	implements BrandDomainService {
	@Autowired
	private BrandDomainDao brandDomainDao;
	
	@Autowired
	private AgentSiteContactService agentSiteContactService;
	
	@Autowired
	private GameDao gameDao;
	@Autowired
	private GameCategoryDao gameCategoryDao;
	
	@Override
	public BaseDao<BrandDomain> baseDao() {
		return brandDomainDao;
	}

	@Override
	public Class<BrandDomain> getClazz() {
		return BrandDomain.class;
	}

	public void tc_save(BrandDomain domain)throws Exception {
		int count = queryDetectionDomainLinkExit(domain.getDomainlink());
		if(count>0) throw new LogicTransactionRollBackException(Enum_MSG.域名已绑定.desc);
		brandDomainDao.saveRegisterLink(AttrCheckout.checkout(domain, false, 
				new String[]{"domainlink","enterprisecode","employeecode","parentemployeecode","employeetype","dividend","share","bonus","domaintype","isdefualt","copyright","linkstatus","datastatus"}));
	}
	
	
	@Override
	public void tc_saveAgentDomainAndContact(BrandDomain domain,AgentSiteContact contact) throws Exception {
		AttrCheckout.checkout(domain, false, 
				new String[]{"domainlink","enterprisecode","employeecode","parentemployeecode","employeetype","dividend","share","bonus","domaintype","isdefualt","copyright","linkstatus","datastatus"});
		int count = queryDetectionDomainLinkExit(domain.getDomainlink());
		if(count>0) {
			throw new LogicTransactionRollBackException(Enum_MSG.域名已绑定.desc);
		} else {
			
		}
		
		super.save(domain);
		contact.setDomaincode(domain.getDomaincode());
		agentSiteContactService.saveContact(contact);
	}


	@Override
	public void tc_updateRegisterLink(BrandDomain registerLink) throws Exception{
		brandDomainDao.updateRegisterLink(AttrCheckout.checkout(registerLink,false, new String[]{"domaincode"}));
	}

	@Override
	public void tc_deleteSelect(String[] array)throws Exception {
		brandDomainDao.deleteSelect(array);
	}
	@Override
	public void tc_logicDeleteByEmployeecode(String[] array)throws Exception {
		brandDomainDao.logicDeleteByEmployeecode(array);
	}
	@Override
	public BrandDomain queryByDomainLink(String domainlink) throws Exception {
		BrandDomain param = new BrandDomain();
		param.setDomainlink(domainlink);
//		System.err.println("domainlink="+domainlink);
		List<BrandDomain> BrandDomains = super.select(AttrCheckout.checkout(param, false, new String[]{"domainlink"}));
//		System.err.println("domainlink size="+BrandDomains.size());
		if(BrandDomains.size()==1){
			return BrandDomains.get(0);
		}
		return null;
	}
	
	@Override
	public Map<String, List<String>> takeAllExpandDomain() throws Exception {
		List<BrandDomain> links = brandDomainDao.takeAllAvalibleLink();
		Map<String,List<String>> categoryLink = new HashMap<String, List<String>>();
		for (BrandDomain e : links) {
			if(categoryLink.get(e.getBrandcode())==null){
				categoryLink.put(e.getBrandcode(), new ArrayList<String>());
			}
			categoryLink.get(e.getBrandcode()).add(e.getDomainlink());
		}
		return categoryLink;
	}

	
	@Override
	public List<BrandDomain> takeBrandDomain(BrandDomain object) throws Exception {
		return super.selectAll(object);
	}

	@Override
	public int takeBrandDomainCount(BrandDomain object) throws Exception {
		return super.selectAllCount(object);
	}
	
	@Override
	public int queryDetectionDomainLinkExit(String domainLink)throws Exception{
		BrandDomain _bdomain = new BrandDomain();
		_bdomain.setDomainlink(domainLink);
		_bdomain.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
		
		return  brandDomainDao.selectCount(_bdomain);
	}

	@Override
	public void tc_SetttingDefualtDomain(BrandDomain domain) throws Exception {
		AttrCheckout.checkout(domain, false, new String[]{"domaincode","enterprisecode","domaintype"});
		BrandDomain object = new BrandDomain();
		object.setEnterprisecode(domain.getEnterprisecode());
		object.setDomaintype(domain.getDomaintype());
		object.setIsdefualt(Constant.BooleanByte.YES.byteValue());
		BrandDomain  beforeDefualt =  super.selectOne(object);
		List<BrandDomain> domains = new ArrayList<BrandDomain>();
		if(beforeDefualt!=null){
			beforeDefualt.setIsdefualt(Constant.BooleanByte.NO.byteValue());
			domains.add(AttrCheckout.checkout(beforeDefualt, true, new String[]{"domaincode","isdefualt"}));
		} 
		domain.setIsdefualt(Constant.BooleanByte.YES.byteValue());
		domains.add(AttrCheckout.checkout(domain, true, new String[]{"domaincode","isdefualt"}));
		brandDomainDao.updateBatch(domains);
	}

	@Override
	public void tc_DeleteMainDomain(BrandDomain domain) throws Exception {
		AttrCheckout.checkout(domain,false,new String[]{"domaincode","enterprisecode"});
		brandDomainDao.deleteMainDoamin(domain);
	}

	@Override
	public BrandDomain takeEDefualtUSiteDomain(String enterprisecode)throws Exception{
		BrandDomain object = new BrandDomain();
		object.setEnterprisecode(enterprisecode);
		object.setDomaintype(Enum_domaintype.企业域名_会员站点.value.byteValue());
		object.setIsdefualt(Constant.BooleanByte.YES.byteValue());
		object.setLinkstatus(Enum_linkstatus.启用.value);
		List<BrandDomain> domains = super.select(object);
		return domains.size()>0?domains.get(0):null;
	}
	
	@Override
	public BrandDomain takeEDefualtASiteDomain(String enterprisecode) throws Exception {
		BrandDomain object = new BrandDomain();
		object.setEnterprisecode(enterprisecode);
		object.setDomaintype(Enum_domaintype.企业域名_代理站点.value.byteValue());
		object.setIsdefualt(Constant.BooleanByte.YES.byteValue());
		object.setLinkstatus(Enum_linkstatus.启用.value);
		List<BrandDomain> domains = super.select(object);
		return domains.size()>0?domains.get(0):null;
	}

	@Override
	public BrandDomain takeSecondMainDomain(String linktoken) throws Exception {
		List<BrandDomain> list = brandDomainDao.takeSecondMainDomain(linktoken);
		if(list.size()>1||list.size()==0) throw new LogicTransactionRollBackException(Enum_MSG.域名未注册.desc);
		return list.get(0);
	}

	@Override
	public String takeDefualtDomainPrefix() throws Exception {
		int maxId = brandDomainDao.selectMaxId();
		String priex = Long.toString(maxId, 36);
		if(priex.length()<4){
			String s = "";
			for(int i=0;i<4-priex.length();i++){
				s+="0";
			}
			priex = s+priex;
		}
		return priex;
	}
	
	public static void main(String[] args) {
		int maxId = 1059;
		String priex = Long.toString(maxId, 36);
		if(priex.length()<4){
			String s = "";
			for(int i=0;i<4-priex.length();i++){
				s+="0";
			}
			priex = s+priex;
		}
		System.out.println(priex);
	}

	@Override
	public String tc_createAgentDefualtDomain(BrandDomain domain) throws Exception {
		try {
			BrandDomain __defualt = this.takeEDefualtUSiteDomain(domain.getEnterprisecode());
			if(__defualt==null) return null;
			String __priex = this.takeDefualtDomainPrefix();
			domain.setDomainlink(__priex+"."+__defualt.getDomainlink());
			this.tc_save(domain);
			return domain.getDomainlink();
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public int tc_BatchLogicDelete(List<BrandDomain> list) throws Exception {
		return super.updateBatch(AttrCheckout.checkout(list, false, new String[]{"domaincode"}));
	}

	/**
	 * 更新站点的洗码设置
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateDomainGameCategoryBonus(String enterprisecode, List<EnterpriseGame> games) throws Exception {
		//传递进来的games只有企业编码和游戏编码，没有游戏类型
		Map<String, String> gametypes = new HashMap<String, String>();
		for (EnterpriseGame game : games) {
			gametypes.put(gameDao.selectByPrimaryKey("Game.selectByPrimaryKey", game.getGamecode()).getGametype(), game.getGamecode());
		}
		
		List<GameCategory> listGameCategory = gameCategoryDao.selectAll("GameCategory.selectAll", new HashMap<String, Object>());
		Map<String, String> mapGameCategory = new HashMap<String, String>();
		for (GameCategory gameCategory : listGameCategory) {
			if(gametypes.containsKey(gameCategory.getGametype()) ) {
				mapGameCategory.put(gameCategory.getGametype().concat("_").concat(gameCategory.getCategorytype()), null);
			}
		}
		
		BrandDomain object = new BrandDomain();
		object.setEnterprisecode(enterprisecode);
		List<BrandDomain> domains = super.select(object);
		
		List<BrandDomain> __update_domains = new ArrayList<BrandDomain>();
		
		for (BrandDomain brandDomain : domains) {
			
			String bonus = brandDomain.getBonus();
			if(bonus != null && bonus.length() > 3) {
				System.out.println("原洗码："+bonus);
				for (String key : mapGameCategory.keySet()) {  
					
					//如果没有则需要往后面累加
					//W88Game_SX:0.008|W88Game_DZ:0.004|NHQGame_SX:0.008|BBINGame_CP:0.0|BBINGame_SX:0.008|BBINGame_TY:0.008|BBINGame_DZ:0.004|TAGGame_SX:0.008|TAGGame_DZ:0.004|GBGame_CP:0.0
					if(bonus.indexOf(key) < 0 ) {
						bonus += "|"+key+":0";
					}
				}  
				System.out.println("新洗码："+bonus);
				__update_domains.add(new BrandDomain(brandDomain.getDomaincode(), bonus));
			}
		}
		
		//批量更新
		if(__update_domains.size() > 0) {
			super.updateBatch(__update_domains);
		}
		
		
	}


}
