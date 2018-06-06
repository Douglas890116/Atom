package com.maven.cache.factory;

import java.util.List;
import java.util.Map;

import com.hy.pull.common.util.api.BaseInterfaceLog;
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

public interface Cache{
	
	/**
	 * 获取工作流对象
	 * @return WorkingFlow对象
	 */
	public WorkingFlow getWorkflow();
	
	
	/**
	 * 获取银行信息
	 * @param bankcode
	 * @see Map<银行编码,Bank对象>
	 * @return Bank对象
	 */
	public Bank getBank(String bankcode);
	
	/**
	 * 获取第三方支付支持银行集合
	 * @param __thirdpartypaymenttypecode
	 * @return
	 */
	public  List<ThirdpartyPaymentBank> getThirdpartyPaymentBanks(String thirdpartypaymenttypecode);
	
	/**
	 * 更新第三方支付支持银行集合
	 * @param thirdpartypaymenttypecode
	 * @return
	 */
	public  void resetThirdpartyPaymentBanks();
	
	/**
	 * 获取第三方支持银行信息
	 * @param __thirdpartypaymenttypecode
	 * @param __bankcode
	 * @return
	 */
	public ThirdpartyPaymentBank getThirdpartyPaymentBank(String __thirdpartypaymenttypecode,String __bankcode);
	
	/**
	 * 获取所有银行信息
	 * @return
	 */
	public List<Bank> getBanks();
	
	/**
	 * 获取接入游戏平台信息
	 * @param gamecode
	 * @see Map<游戏编码,Game对象> 
	 * @return Game对象
	 */
	public Game getGame(String gamecode);
	
	/**
	 * 获取接入平台游戏种类信息
	 * @param gametype
	 * @see Map<游戏类型,List<GameCategory>对象>
	 * @return List<GameCategory>对象
	 */
	public List<GameCategory> getGameCategory(String gametype);
	
	/**
	 * 获取接入平台游戏种类信息,通过组合键(gametype_categorytype)
	 * @param gcategorytype
	 * @see Map<游戏类型_游戏种类类型,GameCategory对象>
	 * @return GameCategory对象
	 */
	public GameCategory getGameCategoryByCnmKey(String cmnkey);
	
	/**
	 * 通过品牌CODE获取游戏接口信息
	 * @param brandcode
	 * @return
	 */
	public GameApiInput getGameApiInputMap(String brandcode);
	
	/**
	 * 获取用户游戏平台账号
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象> 
	 * @return List<EmployeeApiAccout>对象
	 */
	public Map<String,EmployeeApiAccout> getEmployeeAllGameAccount(String employeecode);
	
	/**
	 * 更新用户游戏平台账号信息
	 * @param employeecode 用户编码
	 * @param object Map<String,EmployeeApiAccout> 游戏平台账号 
	 */
	public void setEmployeeAllGameAccount(String employeecode,Map<String,EmployeeApiAccout> object);
	
	/**
	 * 获取用户某个游戏平台账号
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象> 
	 * @return EmployeeApiAccout对象
	 */
	public EmployeeApiAccout getEmployeeGameAccount(String employeecode,String gametype);
	
	/**
	 * 根据平台编码获取
	 * @param brandcode
	 * @see Map<品牌编码,PlatformApiOutput对象>
	 * @return PlatformApiOutput对象
	 */
	public PlatformApiOutput getPlatformApiOutput(String enterprisecode);
	

	/**
	 * 获取日投注返水高级版已执行标志
	 */
	public String getActivityRunFlag(String enterprisebrandactivitycode);
	
	/**
	 * 存储日投注返水高级版已执行标志
	 */
	public boolean setActivityRunFlag(String enterprisebrandactivitycode, String yyyyMMdd);
	
	/**
	 * 获取AV游戏列表List<Map>
	 */
	public List<Map<String, String>> getAvGameList();
	
	/**
	 * 保存AV游戏列表List<Map>
	 */
	public void setAvGameList(List<Map<String, String>> list);
	
	/**
	 * 保存游戏API接口调用日志
	 * @param data
	 */
	public void addBaseInterfaceLog(BaseInterfaceLog data) ;
	
	/**
	 * 分页读取游戏API接口调用日志
	 * @param data
	 */
	public List<BaseInterfaceLog> pageBaseInterfaceLog(int page,int pageSize) ;
	
	/**
	 * 获取-API游戏接口上下分交易单号对照map
	 * @param clientOrderid 客户端单号，统一的19位
	 */
	public String getAPIOrderid(String clientOrderid) ;
	
	/**
	 * 保存-API游戏接口上下分交易单号对照map
	 * 
	 * @param clientOrderid 客户端单号，统一的19位
	 * @param apiOrderid 本接口单号，不定于20位数的
	 */
	public void setAPIClientOrderid(String clientOrderid, String apiOrderid) ;
	
	
	/**
	 * 保存-API游戏接口各接口秘钥、key、地址等map
	 * 
	 * @param enterprisecode_gametype 企业编码_游戏类型
	 * @param data 本企业本游戏类型的对照数据
	 */
	public void setAPIEnterpriseKyes(String enterprisecode, String gametype ,  Map<String, String> data) ;
	/**
	 * 获取-API游戏接口各接口秘钥、key、地址等map
	 * 
	 * @param enterprisecode_gametype 企业编码_游戏类型
	 */
	public Map<String, String> getAPIEnterpriseKyes(String enterprisecode, String gametype ,  Map<String, String> data) ;
	
	/**
	 * 获取API游戏接口的电子游戏分类管理<Map>
	 */
	public List<Map<String, String>> getApiSoltGametypeList(String gametype) ;
	/**
	 * 保存API游戏接口的电子游戏分类管理<Map>
	 */
	public void setApiSoltGametypeList(String gametype, List<Map<String, String>> list) ;
	
	
	/**
	 * 保存-汇率信息
	 * 
	 * @param data 本企业本游戏类型的对照数据
	 */
	public void setExchangeRateAll(Map<String, String> data) ;
	/**
	 * 获取-汇率信息
	 * 
	 * USD=美元
	 * 
	 * @param enterprisecode_gametype 企业编码_游戏类型
	 */
	public Double getExchangeRateAll(String currencycode) ;
	
	
	/**
	 * 获取用户所有的上分游戏类型数据
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象> 
	 * @return List<EmployeeApiAccout>对象
	 */
	public Map<String,UserMoneyInAndOut> getEmployeeAllGameUP(String employeecode);
	
	/**
	 * 加入用户所有的上分游戏类型数据
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象> 
	 * @return List<EmployeeApiAccout>对象
	 */
	public Map<String,UserMoneyInAndOut> addEmployeeAllGameUP(String employeecode, UserMoneyInAndOut mio);
	
	/**
	 * 移除用户所有的上分游戏类型数据
	 * @param employeecode
	 * @see Map<游戏类型,List<EmployeeApiAccout>对象> 
	 * @return List<EmployeeApiAccout>对象
	 */
	public Map<String,UserMoneyInAndOut> removeEmployeeAllGameUP(String employeecode, UserMoneyInAndOut mio);
	
	
	/**
	 * 获取-企业游戏平台账号前缀
	 * @param enterprisecode_gametype
	 */
	public String getPlatformPrefix(String _enterprisecode , String _gametype) ;
	
	/**
	 * 保存-企业游戏平台账号前缀
	 * 
	 * @param enterprisecode_gametype 
	 * @param prefix 
	 */
	public void setPlatformPrefix(String _enterprisecode, String _gametype, String prefix) ;
	
	/**
	 * 获取-会员在线情况
	 * @param enterprisecode_gametype
	 */
	public Map<String, String> getEmployeeOnlineAllWEB(String _enterprisecode);
	/**
	 * 获取-会员在线情况
	 * @param enterprisecode_gametype
	 */
	public Map<String, String> getEmployeeOnlineAllH5(String _enterprisecode);
	
	/**
	 * 获取所有的银行卡校验类型类型
	 * 
	 * @see Map<前缀,BanksCardBin对象> 
	 * @return 
	 */
	public Map<String,BanksCardBin> getBankscardbinAll();
	/**
	 * 初始化所有的银行卡校验类型类型
	 * 
	 * @see Map<前缀,BanksCardBin对象> 
	 * @return 
	 */
	public Map<String,BanksCardBin> makeBankscardbinAll();
	
	/**
	 * 获取-某会员的验证码
	 * @param enterprisecode_gametype
	 */
	public String getSMScode(String __employeecode);
	/**
	 * 保存-某会员的验证码
	 * 
	 * 30分钟内保持有效
	 */
	public void setSMScode(String __employeecode, String __smscode);
	
	/**
	 * 加载银行卡黑名单信息
	 * @param list
	 */
	public void initBankCardsBlacklist();
	/**
	 * 查询相关信息是否存在于黑名单中
	 * @param info
	 * @return
	 */
	public List<BankCardsBlacklist> getBankCardsBlacklist();
}
