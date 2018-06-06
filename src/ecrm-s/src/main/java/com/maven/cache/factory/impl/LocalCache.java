package com.maven.cache.factory.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.pull.common.util.api.BaseInterfaceLog;
import com.maven.cache.factory.Cache;
import com.maven.entity.Bank;
import com.maven.entity.BankCardsBlacklist;
import com.maven.entity.BanksCardBin;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.Game;
import com.maven.entity.GameApiInput;
import com.maven.entity.GameCategory;
import com.maven.entity.PlatformApiOutput;
import com.maven.entity.ThirdpartyPaymentBank;
import com.maven.entity.UserMoneyInAndOut;
import com.maven.service.BankService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.GameApiInputService;
import com.maven.service.GameCategoryService;
import com.maven.service.GameService;
import com.maven.service.PlatformApiOutputService;
import com.maven.utility.SpringContextHolder;

public  class LocalCache implements Cache{
	
	private static LocalCache instance;
	
	/**
	 * 工作流对象
	 */
	private LocalWorkingFlow workflow;
	
	/**
	 * 银行信息
	 * @see Map<银行编码,Bank对象>
	 */
	private Map<String,Bank> bankMap = new HashMap<String, Bank>();
	
	/**
	 * 游戏平台
	 */
	private Map<String,Game> gameMap = new HashMap<String, Game>();
	
	/**
	 * 平台游戏种类
	 */
	private Map<String,List<GameCategory>> gameCategorysMapList = new HashMap<String, List<GameCategory>>();
	
	/**
	 * 平台游戏种类
	 */
	private Map<String,GameCategory> gameCategorysMap = new HashMap<String, GameCategory>();
	
	/**
	 * 游戏接口信息
	 */
	private Map<String,GameApiInput> gameApiInputMap = new HashMap<String, GameApiInput>();
	
	/**
	 * 用户接入平台游戏账号
	 */
	private Map<String,Map<String,EmployeeApiAccout>> employeeApiAccoutMapList = new HashMap<String, Map<String,EmployeeApiAccout>>();
	
	/**
	 * 输出接口配置
	 */
	private Map<String,PlatformApiOutput> platformApiOutputMap = new HashMap<String, PlatformApiOutput>();
	
	private LocalCache(){
		//加载工作流流程 
		makeWorkflow();
		//加载银行信息
		makeBanks();
		//加载接入平台信息
		makeGames();
		//加载接入平台游戏种类信息
		makeGameCategorys();
		//加载接入平台接口信息
		makeGameApiInputMap();
		//加载平台输出接口信息
		makePlatformApiOutputMap();
	}
	
	public  synchronized static LocalCache getInstance(){
		if(instance==null){
			instance = new LocalCache();
		}
		return instance;
	}
	
	/**
	 * 获取工作流对象
	 * @return
	 */
	public LocalWorkingFlow getWorkflow() {
		return workflow;
	}
	
	/**
	 * 
	 * @param bankcode
	 * @return
	 */
	public Bank getBank(String bankcode){
		return bankMap.get(bankcode);
	}
	

	/**
	 * 获取接入游戏平台信息
	 * @param gamecode 游戏编码
	 * @return
	 */
	public Game getGame(String gamecode) {
		return gameMap.get(gamecode);
	}
	
	/**
	 * 获取接入平台游戏种类信息
	 * @param gametype
	 * @return
	 */
	public List<GameCategory> getGameCategory(String gametype){
		return gameCategorysMapList.get(gametype);
	}
	
	/**
	 * 通过组合唯一(gametype_categorytype)键获取GameCategory
	 * @param gcategorytype
	 * @return
	 */
	public GameCategory getGameCategoryByCnmKey(String gcategorytype){
		return gameCategorysMap.get(gcategorytype);
	}
	
	/**
	 * 通过品牌CODE获取游戏接口信息
	 * @param brandcode
	 * @return
	 */
	public GameApiInput getGameApiInputMap(String brandcode){
		GameApiInput object = gameApiInputMap.get(brandcode);
		if(object==null){
			makeGameApiInputMap(brandcode);
			object = gameApiInputMap.get(brandcode);
		}
		return object;
	}
	
	/**
	 * 获取用户某个接入游戏账号
	 * @param employeecode
	 * @return
	 */
	public EmployeeApiAccout getEmployeeGameAccount(String employeecode,String gametype){
		Map<String,EmployeeApiAccout> object = employeeApiAccoutMapList.get(employeecode);
		if(object==null || object.get(gametype)==null){
			makeEmployeeApiAccoutList(employeecode);
			object = employeeApiAccoutMapList.get(employeecode);
			if(object==null) return null;
		}
		return object.get(gametype);
	} 
	
	/**
	 * 获取用户所有接入游戏账号
	 * @param employeecode
	 * @return
	 */
	public Map<String,EmployeeApiAccout> getEmployeeAllGameAccount(String employeecode){
		Map<String,EmployeeApiAccout> object = employeeApiAccoutMapList.get(employeecode);
		if(object==null){
			makeEmployeeApiAccoutList(employeecode);
			object = employeeApiAccoutMapList.get(employeecode);
		}
		return object;
	}
	
	/**
	 * 根据平台编码获取
	 * @param brandcode
	 * @return
	 */
	public PlatformApiOutput getPlatformApiOutput(String brandcode){
		PlatformApiOutput object = platformApiOutputMap.get(brandcode);
		if(object==null){
			makePlatformApiOutputMap(brandcode);
			object =  platformApiOutputMap.get(brandcode);
		}
		return object;
	}

	/**
	 * 加载工作流对象到系统缓存
	 */
	private void makeWorkflow(){
		workflow = new LocalWorkingFlow();
	}
	
	/**
	 * 加载银行信息
	 */
	private void makeBanks(){
		try {
			BankService service = SpringContextHolder.getBean("bankServiceImpl");
			List<Bank> list = service.select(null);
			for (Bank b : list) {
				bankMap.put(b.getBankcode(), b);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("缓存银行信息失败");
		}
	}
	
	/**
	 * 加载接入平台信息
	 */
	private void makeGames(){
		try {
			GameService service = SpringContextHolder.getBean("gameServiceImpl");
			List<Game> list = service.getAllGame();
			for (Game g : list) {
				gameMap.put(g.getGamecode(), g);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("缓存接入平台信息失败");
		}
		
	}
	/**
	 * 加载接入平台游戏种类信息
	 */
	private void makeGameCategorys(){
		try {
			GameCategoryService service = SpringContextHolder.getBean("gameCategoryServiceImpl");
			List<GameCategory> list = service.takeAllGameCategory();
			for (GameCategory gg : list) {
				if(gameCategorysMapList.get(gg.getGametype())==null){
					gameCategorysMapList.put(gg.getGametype(), new ArrayList<GameCategory>());
				}
				gameCategorysMapList.get(gg.getGametype()).add(gg);
				gameCategorysMap.put(gg.getGametype()+"_"+gg.getCategorytype(), gg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("缓存接入平台游戏种类信息失败");
		}
	}
	/**
	 * 加载游戏开放接口信息
	 */
	private void makeGameApiInputMap(String... enterprisecode){
		try {
			GameApiInputService service = SpringContextHolder.getBean("gameApiInputServiceImpl");
			if(enterprisecode==null||enterprisecode.length==0){
				gameApiInputMap = new HashMap<String, GameApiInput>();
				List<GameApiInput> list = service.getAllGames();
				for (GameApiInput e : list) {
					gameApiInputMap.put(e.getEnterprisecode(), e);
				}
			}else{
				GameApiInput e = service.takeGameAPI(enterprisecode[0]);
				gameApiInputMap.put(e.getEnterprisecode(), e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("加载游戏接口信息失败...");
		}
		
	}
	/**
	 * 加载用户接入游戏账号信息
	 * @return
	 */
	private void makeEmployeeApiAccoutList(String employeecode){
		try {
			EmployeeApiAccoutService service = SpringContextHolder.getBean("employeeApiAccoutServiceImpl");
			List<EmployeeApiAccout> list = service.getAllEmployeeApiAccout(employeecode);
			for (EmployeeApiAccout e : list) {
				if(employeeApiAccoutMapList.get(employeecode)==null){
					employeeApiAccoutMapList.put(employeecode, new HashMap<String, EmployeeApiAccout>());
				}
				employeeApiAccoutMapList.get(employeecode).put(e.getGametype(), e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("加载用户游戏账号失败...");
		}
	}
	
	/**
	 * 加载输出接口配置信息
	 */
	private void makePlatformApiOutputMap(String... enterprisecode){
		try {
			PlatformApiOutputService service = SpringContextHolder.getBean("platformApiOutputServiceImpl");
			if(enterprisecode.length==0){
				List<PlatformApiOutput> list = service.select(null);
				for (PlatformApiOutput p : list) {
					platformApiOutputMap.put(p.getEnterprisecode(), p);
				}
			}else{
				PlatformApiOutput p = service.takeConfigUseEnterprisecode(enterprisecode[0]);
				platformApiOutputMap.put(p.getEnterprisecode(), p);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("加载输出接口配置信息失败...");
		}
	}

	@Override
	public void setEmployeeAllGameAccount(String employeecode, Map<String, EmployeeApiAccout> object) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Bank> getBanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ThirdpartyPaymentBank> getThirdpartyPaymentBanks(String thirdpartypaymenttypecode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThirdpartyPaymentBank getThirdpartyPaymentBank(String __thirdpartypaymenttypecode, String __bankcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetThirdpartyPaymentBanks() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 存储日投注返水高级版已执行标志
	 */
	public boolean setActivityRunFlag(String enterprisebrandactivitycode, String yyyyMMdd) {
		return true;
	}
	
	/**
	 * 获取日投注返水高级版已执行标志
	 */
	public String getActivityRunFlag(String enterprisebrandactivitycode) {
		return null;
	}
	
	/**
	 * 获取AV游戏列表List<Map>
	 */
	public List<Map<String, String>> getAvGameList() {
		return null;
	}
	
	/**
	 * 保存AV游戏列表List<Map>
	 */
	public void setAvGameList(List<Map<String, String>> list) {
		//
	}
	
	/**
	 * 保存游戏API接口调用日志
	 * @param data
	 */
	public void addBaseInterfaceLog(BaseInterfaceLog data) {
		//
	}
	
	/**
	 * 分页读取游戏API接口调用日志
	 * @param data
	 */
	public List<BaseInterfaceLog> pageBaseInterfaceLog(int page,int pageSize) {
		//
		return null;
	}
	
	/**
	 * 获取-API游戏接口上下分交易单号对照map
	 * @param clientOrderid 客户端单号，统一的19位
	 */
	public String getAPIOrderid(String clientOrderid)  {
		return null;
		
	}
	/**
	 * 保存-API游戏接口上下分交易单号对照map
	 * 
	 * @param clientOrderid 客户端单号，统一的19位
	 * @param apiOrderid 本接口单号，不定于20位数的
	 */
	public void setAPIClientOrderid(String clientOrderid, String apiOrderid) {
		//
	}
	
	/**
	 * 保存-API游戏接口各接口秘钥、key、地址等map
	 * 
	 * @param enterprisecode_gametype 企业编码_游戏类型
	 * @param data 本企业本游戏类型的对照数据
	 */
	public void setAPIEnterpriseKyes(String enterprisecode, String gametype ,  Map<String, String> data) {
		//
	}
	/**
	 * 获取-API游戏接口各接口秘钥、key、地址等map
	 * 
	 * @param enterprisecode_gametype 企业编码_游戏类型
	 */
	public Map<String, String> getAPIEnterpriseKyes(String enterprisecode, String gametype ,  Map<String, String> data) {
		return null;
	}
	
	/**
	 * 获取API游戏接口的电子游戏分类管理<Map>
	 */
	public List<Map<String, String>> getApiSoltGametypeList(String gametype) {
		return null;
	}
	/**
	 * 保存API游戏接口的电子游戏分类管理<Map>
	 */
	public void setApiSoltGametypeList(String gametype, List<Map<String, String>> list) {
		//
	}
	
	/**
	 * 保存-汇率信息
	 * 
	 * @param data 本企业本游戏类型的对照数据
	 */
	public void setExchangeRateAll(Map<String, String> data) {
		//
	}
	/**
	 * 获取-汇率信息
	 * 
	 * @param enterprisecode_gametype 企业编码_游戏类型
	 */
	public Double getExchangeRateAll(String currencycode) {
		return 1.0;
	}
	
	/**
	 * 获取用户所有的上分游戏类型数据
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象> 
	 * @return List<EmployeeApiAccout>对象
	 */
	public Map<String,UserMoneyInAndOut> getEmployeeAllGameUP(String employeecode) {
		return null;
	}
	/**
	 * 加入用户所有的上分游戏类型数据
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象> 
	 * @return List<EmployeeApiAccout>对象
	 */
	public Map<String,UserMoneyInAndOut> addEmployeeAllGameUP(String employeecode, UserMoneyInAndOut mio) {
		return null;
	}
	
	/**
	 * 移除用户所有的上分游戏类型数据
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象> 
	 * @return List<EmployeeApiAccout>对象
	 */
	public Map<String,UserMoneyInAndOut> removeEmployeeAllGameUP(String employeecode, UserMoneyInAndOut mio) {
		return null;
	}
	
	/**
	 * 获取-企业游戏平台账号前缀
	 * @param enterprisecode_gametype
	 */
	public String getPlatformPrefix(String _enterprisecode , String _gametype)  {
		return null;
	}
	
	/**
	 * 保存-企业游戏平台账号前缀
	 * 
	 * @param enterprisecode_gametype 
	 * @param prefix 
	 */
	public void setPlatformPrefix(String _enterprisecode, String _gametype, String prefix) {
		
	}
	
	/**
	 * 获取-会员在线情况
	 * @param enterprisecode_gametype
	 */
	public String getEmployeeOnlineStatus(String __employeecode){
		return null;
	}
	/**
	 * 获取-会员在线情况
	 * @param enterprisecode_gametype
	 */
	public Map<String, String> getEmployeeOnlineAllWEB(String _enterprisecode) {
		return null;
	}
	/**
	 * 获取-会员在线情况
	 * @param enterprisecode_gametype
	 */
	public Map<String, String> getEmployeeOnlineAllH5(String _enterprisecode) {
		return null;
	}
	
	/**
	 * 获取所有的银行卡校验类型类型
	 * 
	 * @see Map<前缀,BanksCardBin对象> 
	 * @return 
	 */
	public Map<String,BanksCardBin> getBankscardbinAll() {
		return null;
	}
	public Map<String,BanksCardBin> makeBankscardbinAll() {
		return null;
	}
	
	/**
	 * 获取-某会员的验证码
	 * @param enterprisecode_gametype
	 */
	public String getSMScode(String __employeecode) {
		return null;
	}
	/**
	 * 保存-某会员的验证码
	 * 
	 * 30分钟内保持有效
	 */
	public void setSMScode(String __employeecode, String __smscode) {
		//
	}

	@Override
	public void initBankCardsBlacklist() {
		// 
	}

	@Override
	public List<BankCardsBlacklist> getBankCardsBlacklist() {
		return null;
	}
}
