package com.maven.cache.factory.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.pull.common.util.api.BaseInterfaceLog;
import com.maven.cache.RemoteCache;
import com.maven.cache.factory.Cache;
import com.maven.config.RedisProperties;
import com.maven.entity.ApiSoltGametype;
import com.maven.entity.Bank;
import com.maven.entity.BankCardsBlacklist;
import com.maven.entity.BanksCardBin;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.ExchangeRate;
import com.maven.entity.Game;
import com.maven.entity.GameApiInput;
import com.maven.entity.GameCategory;
import com.maven.entity.GamePlatformPrefix;
import com.maven.entity.PlatformApiOutput;
import com.maven.entity.ThirdpartyPaymentBank;
import com.maven.entity.ThirdpartyPaymentBank.Enum_enable;
import com.maven.entity.UserMoneyInAndOut;
import com.maven.logger.TLogger;
import com.maven.service.ApiSoltGametypeService;
import com.maven.service.BankCardsBlacklistService;
import com.maven.service.BankService;
import com.maven.service.BanksCardBinService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.ExchangeRateService;
import com.maven.service.GameApiInputService;
import com.maven.service.GameCategoryService;
import com.maven.service.GamePlatformPrefixService;
import com.maven.service.GameService;
import com.maven.service.PlatformApiOutputService;
import com.maven.service.ThirdpartyPaymentBankService;
import com.maven.service.UserMoneyInAndOutService;
import com.maven.util.ObjectSerialize;
import com.maven.utility.SpringContextHolder;

import redis.clients.jedis.Jedis;

public class RedisCache implements Cache {

	private static RedisCache instance;

	/**
	 * 工作流对象
	 */
	private RedisWorkingFlow workflow;

	/**
	 * 银行信息
	 * 
	 * @see Map<银行编码,Bank对象>
	 */
	private final String BANKS = "BANKS_CACHE";
	
	/**
	 * 第三方支付银行集合
	 */
	private final String THIRDPARTYPAYMENT_BANKCOLLECTION="THIRDPARTYPAYMENT_BANKCOLLECTION_CACHE";
	
	/**
	 * 第三方支付银行
	 */
	private final String THIRDPARTYPAYMENT_BANK = "THIRDPARTYPAYMENT_BANK_CACHE";

	/**
	 * 接入游戏平台信息
	 * 
	 * @see Map<游戏编码,Game对象>
	 */
	private final String INPUT_GAME = "INPUT_GAME_CACHE";

	/**
	 * 接入游戏平台游戏种类
	 * 
	 * @see Map<游戏类型,List<GameCategory>对象>
	 */
	private final String INPUT_GAME_CATEGORY = "INPUT_GAME_CATEGORY_CACHE";

	/**
	 * 接入游戏平台游戏种类
	 * 
	 * @see Map<游戏类型_游戏种类类型,GameCategory对象>
	 */
	private final String INPUT_GAME_CATEGORY_CNMKEY = "INPUT_GAME_CATEGORY_CNMKEY_CACHE";

	/**
	 * 接入游戏平台API配置信息
	 * 
	 * @see Map<品牌编码,GameApiInput对象>
	 */
	private final String INPUT_GAME_API_CONFIG = "INPUT_GAME_API_CONFIG_CACHE";

	/**
	 * 会员接入游戏平台游戏账号
	 * 
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象>
	 */
	private final String EMPLOYEE_API_ACCOUT = "EMPLOYEE_API_ACCOUT_CACHE";

	/**
	 * 系统开放接口API配置信息
	 * 
	 * @see Map<品牌编码,PlatformApiOutput对象>
	 */
	private final String OUTPUT_PLATFORM_API_CONFIG = "OUTPUT_PLATFORM_APIPUT_CONFIG_CACHE";
	
	/**
	 * 日投注返水活动配置信息
	 * 
	 * @see Map<绑定活动编码,最新日期>
	 */
	private final String ACTIVITY_DAY_BUT_MONEY = "ACTIVITY_DAY_BUT_MONEY";
	
	
	/**
	 * AV游戏列表
	 * 
	 * @see 
	 */
	private final String AV_GAME_LIST = "AV_GAME_LIST";
	
	
	/**
	 * API游戏接口调用日志KEY
	 * 
	 * @see 
	 */
	private final String API_GAME_INTERFACE_LOG = "API_GAME_INTERFACE_LOGS_KEY";
	
	/**
	 * API游戏接口订单号对照关系
	 * 
	 * @see 
	 */
	private final String API_GAME_INTERFACE_ORDERID = "API_GAME_INTERFACE_ORDERID";
	
	/**
	 * API游戏接口企业与秘钥等参数对照存储
	 * 
	 * @see 
	 */
	private final String API_GAME_INTERFACE_KEYS = "API_GAME_INTERFACE_KEYS";
	
	/**
	 * API游戏接口的电子游戏分类管理
	 * 
	 * @see 
	 */
	private final String API_GAME_INTERFACE_SOLT_TYPE = "API_GAME_INTERFACE_SOLT_TYPE";
	
	/**
	 * 游戏账号
	 */
	private final String EMPLOYEE_API_ACCOUT_DATA = "EMPLOYEE_API_ACCOUT_DATA_CACHE";
	
	/**
	 * 汇率信息
	 */
	private final String EXCHANGE_RATE = "EXCHANGE_RATE";
	
	/**
	 * 平台上分记录
	 */
	private final String GAME_UP_GAMETYPE = "GAME_UP_GAMETYPE";

	/**
	 * 企业对应的游戏平台账号前缀
	 */
	private final String API_PLATFORM_PREFIX = "API_PLATFORM_PREFIX";
	
	/**
	 * 现金网在线登录情况
	 */
	private final String XJWPC_LOGIN_SESSION_H5 = "xjwmb";//这只是前缀
	/**
	 * 现金网在线登录情况
	 */
	private final String XJWPC_LOGIN_SESSION_WEB = "xjwpc";//这只是前缀
	
	/**
	 * 银行卡校验类型KEY
	 */
	private final String BANKS_CARD_BIN_CHECKS = "BANKS_CARD_BIN_CHECKS";
	
	/**
	 * 短信平台KEY
	 */
	private final String SMS_PUBLIC_EMPLOYEECODE = "SMS_PUBLIC_EMPLOYEECODE";
	
	private final String BANK_CARDS_BLACKLIST = "BANK_CARDS_BLACKLIST";
	
	private RedisCache() {
		if(RedisProperties.getProperties("redis.server.reset").equals("true")){
			// 加载工作流流程
			makeWorkflow();
			// 加载银行信息
			makeBanks(null);
			// 加载接入游戏平台信息
			makeGames(null);
			// 加载接入游戏平台游戏种类信息
			makeGameCategorys(null);
			// 加载接入游戏平台游戏种类信息,组合Key
			makeGameCategorysCmnKey(null);
			// 加载接入游戏平台API配置信息
			makeGameApiInputs(null);
			// 加载系统开放接口API配置信息
			makePlatformApiOutputs(null);
			// 加载第三方支付支持银行			
			makeThirdpartyPaymentBanks(null);
			// 加载银行卡校验数据
			makeBankscardbinAll();
			// 加载银行卡黑名单数据
			initBankCardsBlacklist();
			System.out.println("加载系统缓存...");
		}
	}

	public synchronized static RedisCache getInstance() {
		if (instance == null) {
			instance = new RedisCache();
		}
		return instance;
	}

	/**
	 * 获取工作流对象
	 * 
	 * @return WorkingFlow对象
	 */
	public RedisWorkingFlow getWorkflow() {
		return workflow;
	}

	/**
	 * 获取银行信息
	 * 
	 * @param bankcode
	 * @see Map<银行编码,Bank对象>
	 * @return Bank对象
	 */
	public Bank getBank(String bankcode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			Bank object = ObjectSerialize.unserialize(jedis.hget(this.BANKS.getBytes(), bankcode.getBytes()));
			if (object == null) {
				object = makeBanks(bankcode);
			}
			return object;
		}catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 获取第三方支付支持银行
	 * @param __thirdpartypaymenttypecode
	 * @return
	 */
	public List<ThirdpartyPaymentBank> getThirdpartyPaymentBanks(String __thirdpartypaymenttypecode){
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			List<ThirdpartyPaymentBank> __object = ObjectSerialize.unserialize(jedis.hget(this.THIRDPARTYPAYMENT_BANKCOLLECTION.getBytes(), __thirdpartypaymenttypecode.getBytes()));
			if (__object == null) {
				__object = makeThirdpartyPaymentBanks(__thirdpartypaymenttypecode);
			}
			return __object;
		}catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 获取第三方支付支持银行
	 * @param __thirdpartypaymenttypecode
	 * @return
	 */
	public void resetThirdpartyPaymentBanks(){
		makeThirdpartyPaymentBanks(null);
	}
	
	/**
	 * 获取第三方支付支持银行
	 * @param __thirdpartypaymenttypecode
	 * @return
	 */
	public ThirdpartyPaymentBank getThirdpartyPaymentBank(String __thirdpartypaymenttypecode,String __bankcode){
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			String __key = __thirdpartypaymenttypecode+__bankcode;
			ThirdpartyPaymentBank __object = ObjectSerialize.unserialize(jedis.hget(this.THIRDPARTYPAYMENT_BANK.getBytes(), __key.getBytes()));
			if (__object == null) {
				__object = makeThirdpartyPaymentBank(__thirdpartypaymenttypecode,__bankcode);
			}
			return __object;
		}catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 获取银行信息
	 * 
	 * @param bankcode
	 * @see Map<银行编码,Bank对象>
	 * @return Bank对象
	 */
	public List<Bank> getBanks() {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			Map<byte[],byte[]> object = jedis.hgetAll(this.BANKS.getBytes());
			List<Bank> banks = new ArrayList<Bank>(); 
			try {
				if (object != null) {
					for (byte[] b : object.values()) {
						Bank bank = ObjectSerialize.unserialize(b);
							banks.add(bank.clone());
					}
				}
			} catch (Exception e) {}
			Collections.sort(banks);
			return banks;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
		
	}

	/**
	 * 获取接入API信息
	 * 
	 * @param gamecode
	 * @see Map<游戏编码,Game对象>
	 * @return Game对象
	 */
	public Game getGame(String gamecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			Game object = ObjectSerialize.unserialize(jedis.hget(this.INPUT_GAME.getBytes(), gamecode.getBytes()));
			if (object == null) {
				object = makeGames(gamecode);
			}
			return object;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 获取接入平台游戏种类信息
	 * 
	 * @param gametype
	 * @see Map<游戏类型,List<GameCategory>对象>
	 * @return List<GameCategory>对象
	 */
	public List<GameCategory> getGameCategory(String gametype) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			List<GameCategory> object = ObjectSerialize
					.unserialize(jedis.hget(this.INPUT_GAME_CATEGORY.getBytes(), gametype.getBytes()));
			if (object == null) {
				object = makeGameCategorys(gametype);
			}
			return object;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 获取接入平台游戏种类信息,通过组合键(gametype_categorytype)
	 * 
	 * @param gcategorytype
	 * @see Map<游戏类型_游戏种类类型,GameCategory对象>
	 * @return GameCategory对象
	 */
	public GameCategory getGameCategoryByCnmKey(String cmnkey) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			GameCategory object = ObjectSerialize
					.unserialize(jedis.hget(this.INPUT_GAME_CATEGORY_CNMKEY.getBytes(), cmnkey.getBytes()));
			if (object == null) {
				object = makeGameCategorysCmnKey(cmnkey);
			}
			return object;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 通过品牌CODE获取游戏接口信息
	 * 
	 * @param brandcode
	 * @return
	 */
	public GameApiInput getGameApiInputMap(String enterprisecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			GameApiInput object = ObjectSerialize
					.unserialize(jedis.hget(this.INPUT_GAME_API_CONFIG.getBytes(), enterprisecode.getBytes()));
			if (object == null) {
				object = makeGameApiInputs(enterprisecode);
			}
			return object;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
		
	}

	/**
	 * 获取用户游戏平台账号
	 * 
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象>
	 * @return List<EmployeeApiAccout>对象
	 */
	public Map<String, EmployeeApiAccout> getEmployeeAllGameAccount(String employeecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			Map<String, EmployeeApiAccout> object = ObjectSerialize
					.unserialize(jedis.hget(this.EMPLOYEE_API_ACCOUT.getBytes(), employeecode.getBytes()));
			if (object == null) {
				object = makeEmployeeApiAccous(employeecode);
			}
			return object;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
		
	}

	/**
	 * 更新用户游戏平台账号信息
	 * 
	 * @param employeecode
	 * @param object
	 */
	public void setEmployeeAllGameAccount(String employeecode, Map<String, EmployeeApiAccout> object) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			jedis.hset(this.EMPLOYEE_API_ACCOUT.getBytes(), employeecode.getBytes(),
					ObjectSerialize.serialize(object));
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
	}
	

	/**
	 * 获取用户某个游戏平台账号
	 * 
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象>
	 * @return EmployeeApiAccout对象
	 */
	public EmployeeApiAccout getEmployeeGameAccount(String employeecode, String gametype) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			Map<String, EmployeeApiAccout> object = ObjectSerialize
					.unserialize(jedis.hget(this.EMPLOYEE_API_ACCOUT.getBytes(), employeecode.getBytes()));
//			if (object == null || object.get(gametype) == null) {
				object = makeEmployeeApiAccous(employeecode);
				if (object == null)
					return null;
//			}
			return object.get(gametype);
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 根据平台编码获取
	 * 
	 * @param brandcode
	 * @see Map<品牌编码,PlatformApiOutput对象>
	 * @return PlatformApiOutput对象
	 */
	public PlatformApiOutput getPlatformApiOutput(String enterprisecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			PlatformApiOutput object = ObjectSerialize
					.unserialize(jedis.hget(this.OUTPUT_PLATFORM_API_CONFIG.getBytes(), enterprisecode.getBytes()));
			if (object == null) {
				object = makePlatformApiOutputs(enterprisecode);
			}
			return object;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}


	/**
	 * 加载工作流对象
	 */
	private void makeWorkflow() {
		workflow = new RedisWorkingFlow();
	}

	/**
	 * 加载银行信息
	 */
	private Bank makeBanks(String bankcode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			BankService service = SpringContextHolder.getBean("bankServiceImpl");
			if (bankcode == null) {
				jedis.del(this.BANKS.getBytes());
				List<Bank> list = service.select(null);
				if (list != null && list.size() > 0) {
					Map<byte[], byte[]> object = new HashMap<byte[], byte[]>();
					for (Bank b : list) {
						object.put(b.getBankcode().getBytes(), ObjectSerialize.serialize(b));
					}
					jedis.hmset(this.BANKS.getBytes(), object);
				}
			} else {
				Bank bank = service.getBankInfo(bankcode);
				if (bank != null)
					jedis.hset(this.BANKS.getBytes(), bankcode.getBytes(), ObjectSerialize.serialize(bank));
				return bank;
			}
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("缓存银行信息失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	
	
	/**
	 * 加载第三方支付支持银行
	 * @param thirdpartyPaymenttypecode
	 * @return
	 */
	private List<ThirdpartyPaymentBank> makeThirdpartyPaymentBanks(String __thirdpartyPaymenttypecode){
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			ThirdpartyPaymentBankService service = SpringContextHolder.getBean("thirdpartyPaymentBankServiceImpl");
			if (__thirdpartyPaymenttypecode == null) {
				jedis.del(this.THIRDPARTYPAYMENT_BANKCOLLECTION.getBytes());
				jedis.del(this.THIRDPARTYPAYMENT_BANK.getBytes());
				ThirdpartyPaymentBank __tpb = new ThirdpartyPaymentBank();
				__tpb.setEnable(Enum_enable.启用.value);
				List<ThirdpartyPaymentBank> list = service.select(__tpb);
				if (list != null && list.size() > 0) {
					Map<String,List<ThirdpartyPaymentBank>> __thbs = new HashMap<String, List<ThirdpartyPaymentBank>>();
					Map<byte[], byte[]> __thb = new HashMap<byte[], byte[]>();
					Map<byte[], byte[]> __thbsobject = new HashMap<byte[], byte[]>();
					for (ThirdpartyPaymentBank _bk : list) {
						if(__thbs.get(_bk.getThirdpartypaymenttypecode())==null){
							__thbs.put(_bk.getThirdpartypaymenttypecode(), new ArrayList<ThirdpartyPaymentBank>());
						}
						__thbs.get(_bk.getThirdpartypaymenttypecode()).add(_bk);
						__thb.put((_bk.getThirdpartypaymenttypecode()+_bk.getBankcode()).getBytes(), ObjectSerialize.serialize(_bk));
					}
					for (String __key : __thbs.keySet()) {
						__thbsobject.put(__key.getBytes(), ObjectSerialize.serialize(__thbs.get(__key)));
					}
					jedis.hmset(this.THIRDPARTYPAYMENT_BANKCOLLECTION.getBytes(), __thbsobject);
					jedis.hmset(this.THIRDPARTYPAYMENT_BANK.getBytes(), __thb);
				}
			} else {
				ThirdpartyPaymentBank __tpb = new ThirdpartyPaymentBank();
				__tpb.setEnable(Enum_enable.启用.value);
				__tpb.setThirdpartypaymenttypecode(__thirdpartyPaymenttypecode);
				List<ThirdpartyPaymentBank> __list = service.select(__tpb);
				if (__list != null)
					jedis.hset(this.THIRDPARTYPAYMENT_BANKCOLLECTION.getBytes(), __thirdpartyPaymenttypecode.getBytes(), ObjectSerialize.serialize(__list));
				return __list;
			}
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("缓存第三方支付支持银行失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 加载第三方支付支持银行
	 * @param thirdpartyPaymenttypecode
	 * @return
	 */
	private ThirdpartyPaymentBank makeThirdpartyPaymentBank(String __thirdpartyPaymenttypecode,String __bankcode){
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			ThirdpartyPaymentBankService service = SpringContextHolder.getBean("thirdpartyPaymentBankServiceImpl");
			ThirdpartyPaymentBank __tpb = new ThirdpartyPaymentBank();
			__tpb.setThirdpartypaymenttypecode(__thirdpartyPaymenttypecode);
			__tpb.setBankcode(__bankcode);
			List<ThirdpartyPaymentBank> __list = service.select(__tpb);
			if (__list != null&&__list.size()>0)
				jedis.hset(this.THIRDPARTYPAYMENT_BANK.getBytes(), __thirdpartyPaymenttypecode.getBytes(), ObjectSerialize.serialize(__list.get(0)));
			return __list.get(0);
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("缓存第三方支付支持银行失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 加载接入游戏平台信息
	 */
	private Game makeGames(String gamecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			GameService service = SpringContextHolder.getBean("gameServiceImpl");
			if (gamecode == null) {
				jedis.del(this.INPUT_GAME.getBytes());
				Map<byte[], byte[]> object = new HashMap<byte[], byte[]>();
				List<Game> list = service.getAllGame();
				if (list != null && list.size() > 0) {
					for (Game g : list) {
						object.put(g.getGamecode().getBytes(), ObjectSerialize.serialize(g));
					}
					jedis.hmset(this.INPUT_GAME.getBytes(), object);
				}

			} else {
				Game game = service.getGameUseCode(gamecode);
				if (game != null)
					jedis.hset(this.INPUT_GAME.getBytes(), gamecode.getBytes(),
							ObjectSerialize.serialize(game));
				return game;
			}
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("缓存接入平台信息失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 加载接入游戏平台游戏种类信息
	 */
	private List<GameCategory> makeGameCategorys(String gametype) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			GameCategoryService service = SpringContextHolder.getBean("gameCategoryServiceImpl");
			if (gametype == null) {
				jedis.del(this.INPUT_GAME_CATEGORY.getBytes());
				Map<byte[], byte[]> object = new HashMap<byte[], byte[]>();
				List<GameCategory> list = service.takeAllGameCategory();
				if (list != null && list.size() > 0) {
					Map<String, List<GameCategory>> gameCategorysMapList = new HashMap<String, List<GameCategory>>();
					for (GameCategory gg : list) {
						if (gameCategorysMapList.get(gg.getGametype()) == null) {
							gameCategorysMapList.put(gg.getGametype(), new ArrayList<GameCategory>());
						}
						gameCategorysMapList.get(gg.getGametype()).add(gg);
					}
					for (String key : gameCategorysMapList.keySet()) {
						object.put(key.getBytes(), ObjectSerialize.serialize(gameCategorysMapList.get(key)));
					}
					jedis.hmset(this.INPUT_GAME_CATEGORY.getBytes(), object);
					return null;
				}
			} else {
				List<GameCategory> list = service.takeGategoryUseGameType(gametype);
				if (list != null && list.size() > 0)
					jedis.hset(this.INPUT_GAME_CATEGORY.getBytes(), gametype.getBytes(),
							ObjectSerialize.serialize(list));
				return list;
			}
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("缓存接入平台游戏种类信息失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 加载接入游戏平台游戏种类信息，使用组合键作为Key
	 */
	private GameCategory makeGameCategorysCmnKey(String CmnKey) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			GameCategoryService service = SpringContextHolder.getBean("gameCategoryServiceImpl");
			if (CmnKey == null) {
				jedis.del(this.INPUT_GAME_CATEGORY_CNMKEY.getBytes());
				List<GameCategory> list = service.takeAllGameCategory();
				if (list != null && list.size() > 0) {
					Map<byte[], byte[]> object = new HashMap<byte[], byte[]>();
					for (GameCategory gc : list) {
						String key = gc.getGametype() + "_" + gc.getCategorytype();
						object.put(key.getBytes(), ObjectSerialize.serialize(gc));
					}
					jedis.hmset(this.INPUT_GAME_CATEGORY_CNMKEY.getBytes(), object);
				}
			} else {
				String[] keys = CmnKey.split("_");
				if (keys.length != 2)
					return null;
				GameCategory gc = service.takegategoryUseCmnKey(keys[0], keys[1]);
				if (gc != null)
					jedis.hset(this.INPUT_GAME_CATEGORY_CNMKEY.getBytes(), CmnKey.getBytes(),
							ObjectSerialize.serialize(gc));
				return gc;
			}
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("缓存接入平台游戏种类信息失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 加载接入游戏平台API配置信息
	 */
	private GameApiInput makeGameApiInputs(String enterprisecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			GameApiInputService service = SpringContextHolder.getBean("gameApiInputServiceImpl");
			if (enterprisecode == null) {
				jedis.del(this.INPUT_GAME_API_CONFIG.getBytes());
				List<GameApiInput> list = service.getAllGames();
				if (list != null && list.size() > 0) {
					Map<byte[], byte[]> object = new HashMap<byte[], byte[]>();
					for (GameApiInput e : list) {
						object.put(e.getEnterprisecode().getBytes(), ObjectSerialize.serialize(e));
					}
					jedis.hmset(this.INPUT_GAME_API_CONFIG.getBytes(), object);
				}
			} else {
				GameApiInput gai = service.takeGameAPI(enterprisecode);
				if (gai != null)
					jedis.hset(this.INPUT_GAME_API_CONFIG.getBytes(), enterprisecode.getBytes(),
							ObjectSerialize.serialize(gai));
				return gai;
			}
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("加载游戏接口信息失败...");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 加载用户接入游戏账号信息
	 */
	private Map<String, EmployeeApiAccout> makeEmployeeApiAccous(String employeecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			EmployeeApiAccoutService service = SpringContextHolder.getBean("employeeApiAccoutServiceImpl");
			List<EmployeeApiAccout> list = service.getAllEmployeeApiAccout(employeecode);
//			System.out.println(employeecode+"加载游戏账号:"+(list == null ? 0 : list.size()));
			Map<String, EmployeeApiAccout> employeeApiAccoutMap = null;
			if (list != null && list.size() > 0) {
				employeeApiAccoutMap = new HashMap<String, EmployeeApiAccout>();
				for (EmployeeApiAccout e : list) {
					employeeApiAccoutMap.put(e.getGametype(), e);
//					System.out.println(employeecode+"="+e.getGametype()+" ="+e.getGameaccount());
				}
				jedis.hset(this.EMPLOYEE_API_ACCOUT.getBytes(), employeecode.getBytes(),
						ObjectSerialize.serialize(employeeApiAccoutMap));
			}
			return employeeApiAccoutMap;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("加载用户游戏账号失败...");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 加载系统开放API配置信息
	 */
	private PlatformApiOutput makePlatformApiOutputs(String enterprisecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			PlatformApiOutputService service = SpringContextHolder.getBean("platformApiOutputServiceImpl");
			if (enterprisecode == null) {
				jedis.del(this.OUTPUT_PLATFORM_API_CONFIG.getBytes());
				List<PlatformApiOutput> list = service.select(null);
				if (list != null && list.size() > 0) {
					Map<byte[], byte[]> object = new HashMap<byte[], byte[]>();
					for (PlatformApiOutput p : list) {
						object.put(p.getEnterprisecode().getBytes(), ObjectSerialize.serialize(p));
					}
					jedis.hmset(this.OUTPUT_PLATFORM_API_CONFIG.getBytes(), object);
				}
			} else {
				PlatformApiOutput pfao = service.takeConfigUseEnterprisecode(enterprisecode);
				if (pfao != null)
					jedis.hset(this.OUTPUT_PLATFORM_API_CONFIG.getBytes(), enterprisecode.getBytes(),
							ObjectSerialize.serialize(pfao));
				return pfao;
			}
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("加载输出接口配置信息失败...");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 获取日投注返水高级版已执行标志
	 */
	public String getActivityRunFlag(String enterprisebrandactivitycode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			return jedis.hget(this.ACTIVITY_DAY_BUT_MONEY, enterprisebrandactivitycode) ;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("获取日投注返水高级版已执行标志失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 存储日投注返水高级版已执行标志
	 */
	public boolean setActivityRunFlag(String enterprisebrandactivitycode, String yyyyMMdd) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			jedis.hset(this.ACTIVITY_DAY_BUT_MONEY, enterprisebrandactivitycode, yyyyMMdd);
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("存储日投注返水高级版已执行标志失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return true;
	}
	
	
	
	/**
	 * 获取AV游戏列表List<Map>
	 */
	public List<Map<String, String>> getAvGameList() {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			return ObjectSerialize.unserialize( jedis.hget(this.AV_GAME_LIST.getBytes() , this.AV_GAME_LIST.getBytes())  ) ;
					
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("存储日投注返水高级版已执行标志失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 保存AV游戏列表List<Map>
	 */
	public void setAvGameList(List<Map<String, String>> list) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			jedis.hset(this.AV_GAME_LIST.getBytes(), this.AV_GAME_LIST.getBytes(), ObjectSerialize.serialize(list));
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("存储日投注返水高级版已执行标志失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
	}
	
	
	/**
	 * 保存游戏API接口调用日志
	 * @param data
	 */
	public void addBaseInterfaceLog(BaseInterfaceLog data) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
//			jedis.expire(data.getLodId().getBytes(), data.getLodId().getBytes() , 10000);
//			jedis.expire(key, seconds)
			
			jedis.lpush(this.API_GAME_INTERFACE_LOG.getBytes(),  ObjectSerialize.serialize(data));
			
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
	}

	/**
	 * 分页读取游戏API接口调用日志
	 * @param data
	 */
	public List<BaseInterfaceLog> pageBaseInterfaceLog(int page,int pageSize) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
//			System.out.println(jedis.llen(this.API_GAME_INTERFACE_LOG.getBytes()) + "个记录");
//			System.out.println(jedis.del(this.API_GAME_INTERFACE_LOG.getBytes()) + "个记录已清除");
			
			if( page < 1 ) {
				page = 1;
			}
			int start = (page - 1) * pageSize;
			int end   = start + pageSize;
			
			List<byte[]> list = jedis.lrange(this.API_GAME_INTERFACE_LOG.getBytes(), start, end);
			
			List<BaseInterfaceLog> listData = new ArrayList<BaseInterfaceLog>();
			for (byte[] bytes : list) {
				listData.add((BaseInterfaceLog)ObjectSerialize.unserialize(bytes));
			}
			
			return listData;
		} catch (Exception e) {
			e.printStackTrace();
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	/***
	 * 获取-API游戏接口上下分交易单号对照map
	 * 
	 * @param clientOrderid 客户端单号，统一的19位
	 */
	public String getAPIOrderid(String clientOrderid) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			return jedis.hget(this.API_GAME_INTERFACE_ORDERID, clientOrderid);
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 保存-API游戏接口上下分交易单号对照map
	 * @param clientOrderid 客户端单号，统一的19位
	 * @param apiOrderid 本接口单号，不定于20位数的
	 */
	public void setAPIClientOrderid(String clientOrderid, String apiOrderid) {
		
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			jedis.hset(this.API_GAME_INTERFACE_ORDERID, clientOrderid, apiOrderid);
			
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
	}
	
	/**
	 * 保存-API游戏接口各接口秘钥、key、地址等map
	 * 
	 * @param enterprisecode_gametype 企业编码_游戏类型
	 * @param data 本企业本游戏类型的对照数据
	 */
	public void setAPIEnterpriseKyes(String enterprisecode, String gametype ,  Map<String, String> data) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			String fullkey = this.API_GAME_INTERFACE_KEYS + "_" + enterprisecode + "_" + gametype;
			
			jedis.hmset(fullkey, data);
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
	}
	
	/**
	 * 获取-API游戏接口各接口秘钥、key、地址等map
	 * 
	 * @param enterprisecode_gametype 企业编码_游戏类型
	 */
	public Map<String, String> getAPIEnterpriseKyes(String enterprisecode, String gametype ,  Map<String, String> data) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			String fullkey = this.API_GAME_INTERFACE_KEYS + "_" + enterprisecode + "_" + gametype;
			
			data = jedis.hgetAll(fullkey);
//			
//			Iterator<String> iterator = data.keySet().iterator();
//			while(iterator.hasNext()){    
//			     String key = iterator.next().toString();
//			     List<String> result = jedis.hmget(fullkey, key);
//			     data.put(key, result.get(0));
//			}   
			
			return data;
			
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return data;
	}
	
	/**
	 * 获取API游戏接口的电子游戏分类管理（取消）<Map>
	 */
	public List<Map<String, String>> getApiSoltGametypeList(String gametype) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			
			if(jedis.hget(this.API_GAME_INTERFACE_SOLT_TYPE.getBytes() , gametype.getBytes()) == null) {//缓存中还没有数据
				ApiSoltGametypeService service = SpringContextHolder.getBean("apiSoltGametypeServiceImpl");
				List<ApiSoltGametype> list = service.select(null);
				
				for (ApiSoltGametype apiSoltGametype : list) {
					
				}
			} else {
				
				return ObjectSerialize.unserialize( jedis.hget(this.API_GAME_INTERFACE_SOLT_TYPE.getBytes() , gametype.getBytes())  ) ;
			}
			
			
					
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("存储日投注返水高级版已执行标志失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 保存API游戏接口的电子游戏分类管理（取消）<Map>
	 */
	public void setApiSoltGametypeList(String gametype, List<Map<String, String>> list) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			jedis.hset(this.API_GAME_INTERFACE_SOLT_TYPE.getBytes(), gametype.getBytes(), ObjectSerialize.serialize(list));
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("存储日投注返水高级版已执行标志失败");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
	}
	
	
	
	/**
	 * 获取用户游戏账号信息（根据企业、游戏账号）
	 * @param brandcode
	 * @param gameaccount
	 * @param gametype
	 * @return
	 */
	public EmployeeApiAccout getEmployeeApiAccount(String enterprisecode, String gameaccount, String gametype) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			String key = new StringBuffer().append(enterprisecode).append(gameaccount).append(gametype).toString();
			EmployeeApiAccout account = ObjectSerialize
					.unserialize(jedis.hget(this.EMPLOYEE_API_ACCOUT_DATA.getBytes(), key.getBytes()));
			if (account == null) {
				account = makeEmployeeApiAccount(enterprisecode,gameaccount,gametype);
			}
			return account;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}

	/**
	 * 缓存EmployeeApiAccout数据表
	 */
	private EmployeeApiAccout makeEmployeeApiAccount(String enterprisecode, String gameaccount, String gametype) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			EmployeeApiAccoutService service = SpringContextHolder.getBean("employeeApiAccoutServiceImpl");
			
			String key = enterprisecode+gameaccount+gametype;
			EmployeeApiAccout eaix  = ObjectSerialize.unserialize( jedis.hget(this.EMPLOYEE_API_ACCOUT_DATA.getBytes(), key.getBytes()) );
			
			if(eaix == null) {
				
				EmployeeApiAccout temp = new EmployeeApiAccout();
				temp.setGametype(gametype);
				temp.setEnterprisecode(enterprisecode);
				
				List<EmployeeApiAccout> accounts = service.getEmployeeApiAccoutByObject(temp);
				Map<byte[], byte[]> object = new HashMap<byte[], byte[]>();
				for (EmployeeApiAccout eai : accounts) {
					String keytemp = eai.getEnterprisecode()+eai.getGameaccount()+eai.getGametype();
					object.put(keytemp.getBytes(), ObjectSerialize.serialize(eai));
					
					if(eai.getGameaccount().equals(gameaccount)) {
						eaix = eai;
					}
				}
				
				jedis.hmset(this.EMPLOYEE_API_ACCOUT_DATA.getBytes(), object);
				
				return eaix;
				
			} else {
				return eaix;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	
	
	/**
	 * 保存-汇率信息
	 * 
	 * @param data 本企业本游戏类型的对照数据
	 */
	public void setExchangeRateAll(Map<String, String> data) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			String fullkey = this.EXCHANGE_RATE;
			
			jedis.hmset(fullkey, data);
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
	}
	/**
	 * 获取-汇率信息
	 * 
	 * @param enterprisecode_gametype 企业编码_游戏类型
	 */
	public Double getExchangeRateAll(String currencycode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			String fullkey = this.EXCHANGE_RATE;
			
			Map<String, String> data = jedis.hgetAll(fullkey);
			if(data == null) {
				ExchangeRateService service = SpringContextHolder.getBean("exchangeRateServiceImpl");
				Map<String,Object> object = new HashMap<String, Object>();
				List<ExchangeRate> list = service.getExchangeRateByCondition(object);
				
				data = new HashMap<String, String>();
				for (ExchangeRate exchangeRate : list) {
					data.put(exchangeRate.getCurrencycode(), exchangeRate.getExchangerate().toString());
				}
			}
			
			return Double.valueOf(data.get(currencycode));
			
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return 1.0;
	}
	
	
	/**
	 * 加载用户接入游戏账号信息
	 */
	private Map<String, UserMoneyInAndOut> makeEmployeeUserMoneyInAndOut(String employeecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			UserMoneyInAndOutService service = SpringContextHolder.getBean("userMoneyInAndOutServiceImpl");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("employeecode", employeecode);
			params.put("opreatetype", UserMoneyInAndOut.Enum_opreatetype.上分.code);
			params.put("isdown", "1");
			List<UserMoneyInAndOut> list = service.findMoneyInAndOut(params);
//			
			Map<String, UserMoneyInAndOut> employeeApiAccoutMap = null;
			if (list != null && list.size() > 0) {
				
				employeeApiAccoutMap = new HashMap<String, UserMoneyInAndOut>();
				for (UserMoneyInAndOut e : list) {
					employeeApiAccoutMap.put(e.getGametype(), e);
//					System.out.println(employeecode+"="+e.getGametype()+" ="+e.getGameaccount());
				}
				jedis.hset(this.GAME_UP_GAMETYPE.getBytes(), employeecode.getBytes(),ObjectSerialize.serialize(employeeApiAccoutMap));
						
			}
			return employeeApiAccoutMap;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			System.out.println("加载用户游戏上分未完成记录失败...");
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
	/**
	 * 获取用户所有的上分游戏类型数据
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象> 
	 * @return List<EmployeeApiAccout>对象
	 */
	public Map<String,UserMoneyInAndOut> getEmployeeAllGameUP(String employeecode) {
		
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			Map<String, UserMoneyInAndOut> object = ObjectSerialize.unserialize(jedis.hget(this.GAME_UP_GAMETYPE.getBytes(), employeecode.getBytes()));
					
//			if (object == null) {//暂时都全部查询一遍，后期等所有记录都正常后再放开
				object = makeEmployeeUserMoneyInAndOut(employeecode);
//			}
			if(object == null) {
				object = new HashMap<String, UserMoneyInAndOut>();
			}
			return object;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return new HashMap<String, UserMoneyInAndOut>();
	}
	
	
	/**
	 * 设置用户所有的上分游戏类型数据
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象> 
	 * @return List<EmployeeApiAccout>对象
	 */
	public Map<String,UserMoneyInAndOut> addEmployeeAllGameUP(String employeecode, UserMoneyInAndOut mio) {
		Jedis jedis  = null;
		Map<String,UserMoneyInAndOut> data = null;
		try {
			jedis = RemoteCache.getJedis();
			
			data = getEmployeeAllGameUP(employeecode);
			if(data == null) {
				data = new HashMap<String, UserMoneyInAndOut>();
			}
			data.put(mio.getGametype(), mio);//加入
			
			jedis.hset(this.GAME_UP_GAMETYPE.getBytes(), employeecode.getBytes(),ObjectSerialize.serialize(data));
					
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return data;
	}
	
	/**
	 * 移除用户所有的上分游戏类型数据
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象> 
	 * @return List<EmployeeApiAccout>对象
	 */
	public Map<String,UserMoneyInAndOut> removeEmployeeAllGameUP(String employeecode, UserMoneyInAndOut mio) {
		Jedis jedis  = null;
		Map<String,UserMoneyInAndOut> data = null;
		try {
			jedis = RemoteCache.getJedis();
			
			data = getEmployeeAllGameUP(employeecode);
			if(data == null) {
				data = new HashMap<String, UserMoneyInAndOut>();
			}
			data.remove(mio.getGametype());//移除
			
			jedis.hset(this.GAME_UP_GAMETYPE.getBytes(), employeecode.getBytes(),ObjectSerialize.serialize(data));
					
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		if(data == null) {
			data = new HashMap<String, UserMoneyInAndOut>();
		}
		return data;
	}
	
	/**
	 * 获取-企业游戏平台账号前缀
	 * @param enterprisecode_gametype
	 */
	public String getPlatformPrefix(String _enterprisecode , String _gametype) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			String PlatformPrefix = jedis.hget(this.API_PLATFORM_PREFIX, _enterprisecode + _gametype);
			if(PlatformPrefix == null) {
				
				GamePlatformPrefixService gamePlatformPrefixService  = SpringContextHolder.getBean("gamePlatformPrefixServiceImpl");
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("enterprisecode", _enterprisecode) ;
				params.put("gamePlatform", _gametype) ;
				List<GamePlatformPrefix> listGamePlatformPrefix = gamePlatformPrefixService.selectAll(params);//查整个前缀配置库
				for (GamePlatformPrefix gamePlatformPrefix : listGamePlatformPrefix) {
					
					String key = gamePlatformPrefix.getEnterprisecode() + gamePlatformPrefix.getGamePlatform();
					String value = gamePlatformPrefix.getPrefixcode();
					
					jedis.hset(this.API_PLATFORM_PREFIX, key, value);//存储
				}
				//再次查询
				PlatformPrefix = jedis.hget(this.API_PLATFORM_PREFIX, _enterprisecode + _gametype);
			}
			
			return PlatformPrefix;
			
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return "";
	}
	
	/**
	 * 保存-企业游戏平台账号前缀
	 * 
	 * @param enterprisecode_gametype 
	 * @param prefix 
	 */
	public void setPlatformPrefix(String _enterprisecode, String _gametype, String prefix) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			jedis.hset(this.API_PLATFORM_PREFIX, _enterprisecode + _gametype, prefix);
			
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
	}
	
	
	/**
	 * 获取-会员在线情况
	 * @param enterprisecode_gametype
	 */
	public Map<String, String> getEmployeeOnlineAllWEB(String _enterprisecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			//2
			String fullkey = this.XJWPC_LOGIN_SESSION_WEB+"_"+_enterprisecode;
			Map<String, String> data = jedis.hgetAll(fullkey);
			return data;
			
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		
		return null;
	}
	/**
	 * 获取-会员在线情况
	 * @param enterprisecode_gametype
	 */
	public Map<String, String> getEmployeeOnlineAllH5(String _enterprisecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			//2
			String fullkey = this.XJWPC_LOGIN_SESSION_H5+"_"+_enterprisecode;
			Map<String, String> data = jedis.hgetAll(fullkey);
			return data;
			
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		
		return null;
	}
	
	/**
	 * 获取所有的银行卡校验类型类型
	 * 
	 * @see Map<前缀,BanksCardBin对象> 
	 * @return 
	 */
	public Map<String,BanksCardBin> getBankscardbinAll() {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			Map<String, BanksCardBin> object = ObjectSerialize.unserialize(jedis.hget(this.BANKS_CARD_BIN_CHECKS.getBytes(), BANKS_CARD_BIN_CHECKS.getBytes()));
					
			return object;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return new HashMap<String, BanksCardBin>();
	}
	/**
	 * 初始化
	 * @return
	 */
	public Map<String,BanksCardBin> makeBankscardbinAll() {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
				
			BanksCardBinService service = SpringContextHolder.getBean("banksCardBinServiceImpl");
			
			Map<String, Object> params = new HashMap<String, Object>();
			List<BanksCardBin> list = service.selectBetRecord(params);

			Map<String, BanksCardBin> employeeApiAccoutMap = null;
			if (list != null && list.size() > 0) {
				
				employeeApiAccoutMap = new HashMap<String, BanksCardBin>();
				for (BanksCardBin e : list) {
					employeeApiAccoutMap.put(e.getBankprefix(), e);
//						System.out.println(employeecode+"="+e.getGametype()+" ="+e.getGameaccount());
				}
				jedis.hset(this.BANKS_CARD_BIN_CHECKS.getBytes(), BANKS_CARD_BIN_CHECKS.getBytes(),ObjectSerialize.serialize(employeeApiAccoutMap));
				
			}
			
			return employeeApiAccoutMap;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return new HashMap<String, BanksCardBin>();
	}
	
	
	/**
	 * 获取-某会员的验证码
	 * @param enterprisecode_gametype
	 */
	public String getSMScode(String __employeecode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			return jedis.get(this.SMS_PUBLIC_EMPLOYEECODE + "_" + __employeecode);
			
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return "";
	}
	
	/**
	 * 保存-某会员的验证码
	 * 
	 * 360分钟内保持有效=6小时
	 */
	public void setSMScode(String __employeecode, String __smscode) {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			
			jedis.setex(this.SMS_PUBLIC_EMPLOYEECODE + "_" + __employeecode, 60 * 60 * 6, __smscode); //秒数
			
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			RemoteCache.returnJedis(jedis);
		}
	}
	/**
	 * 加载银行卡黑名单信息
	 */
	@Override
	public void initBankCardsBlacklist() {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
				
			BankCardsBlacklistService service = SpringContextHolder.getBean("bankCardsBlacklistServiceImpl");
			
			Map<String, Object> params = new HashMap<String, Object>();
			List<BankCardsBlacklist> list = service.selectAll(params);

			if (list != null && list.size() > 0) {
				jedis.hset(this.BANK_CARDS_BLACKLIST.getBytes(), BANK_CARDS_BLACKLIST.getBytes(),ObjectSerialize.serialize(list));
			}
			
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
	}
	/**
	 * 查询相关信息是否存在于黑名单中
	 */
	@Override
	public List<BankCardsBlacklist> getBankCardsBlacklist() {
		Jedis jedis  = null;
		try {
			jedis = RemoteCache.getJedis();
			List<BankCardsBlacklist> list = ObjectSerialize.unserialize(jedis.hget(this.BANK_CARDS_BLACKLIST.getBytes(), BANK_CARDS_BLACKLIST.getBytes()));
			if (list != null && list.size() > 0) return list;
		} catch (Exception e) {
			TLogger.getLogger().Error(e.getMessage(), e);
		}finally {
			RemoteCache.returnJedis(jedis);
		}
		return null;
	}
}