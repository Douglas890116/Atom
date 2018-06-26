package com.hy.pull.common.util.game.bbin;

import java.util.HashMap;
import java.util.Map;

/**
 * 波音游戏工具类
 * 创建日期：2016-12-23
 * @author temdy 
 */
public class BBINUtil {
	
	/**
	 * 获取游戏错误消息
	 * @param key
	 * @return
	 */
	public static String getErrorCode(String key){
		Map<String,String> entity = new HashMap<String,String>();
		entity.put("10002", "Insufficient Account Balance 余额不足");
		entity.put("10003", "Transfer Failed 转帐失败");
		entity.put("10005", "Check Limit Error 额度检查错误");
		entity.put("10006", "remit must be positive integer or floating point remit需为正整数");
		entity.put("10007", "The serial number of transaction is digits and it cannot be blank or 0. 交易序号是数字型态且不得为空或0");
		entity.put("10008", "remit cannot be smaller or equal to 0 remit不能小于等于0");
		entity.put("10009", "newcredit cannot be smaller than 0 newcredit不能小于0");
		entity.put("10010", "credit cannot be smaller than 0 credit不能小于0");
		entity.put("10011", "The format of transfer is wrong.(remit,credit,newcredit)转帐格式错误");
		entity.put("10012", "action must be IN or OUT action必须是IN或OUT");
		entity.put("10013", "The column of action is wrong action格式错误");
		entity.put("10014", "The serial number of transaction is too long. (int19).交易序号长度过长(int 19)");
		entity.put("10015", "The transfer is running, please wait API忙碌中");
		entity.put("11000", "Repeat Transfer 重复转帐");
		entity.put("11100", "Transfer Successful 转帐成功");
		entity.put("21000", "Add account failed. 帐号新增失败");
		entity.put("21001", "The account is repeated. 帐号重复");
		entity.put("21100", "Add account successful. 帐号新增成功");
		entity.put("22000", "User hasn't Login. 使用者未登入");
		entity.put("22001", "User Logout 使用者登出");
		entity.put("22002", "The user is not exist. 使用者不存在");
		entity.put("22003", "The user is not under this Agent. 使用者不存在此代理底下");
		entity.put("22004", "Username is not in the Member's Level. username非会员阶层");
		entity.put("22006", "Upper Level is not exist. 上层不存在");
		entity.put("22011", "The member is not exist. 会员不存在");
		entity.put("23000", "Hierarchy Error 体系错误");
		entity.put("23100", "Cease User ID successful 停用帐号成功");
		entity.put("23101", "Activate User ID successful 启用帐号成功");
		entity.put("23103", "Cease User ID failed 停用帐号失败");
		entity.put("23104", "Activate User ID failed 启用帐号失败");
		entity.put("23105", "Change Password successful 变更密码成功");
		entity.put("23106", "Change Password failed 变更密码失败");
		entity.put("25000", "Agent Login Successful 管端登入成功");
		entity.put("25001", "Password is failed. 密码验证失败");
		entity.put("25002", "ID is failed. (the first letter is wrong) 帐号新增失败(第一个英文字母错误)");
		entity.put("25003", "The Upper Level hasn't Login. 上层未登入");
		entity.put("25004", "The Upper Level is in the different system. 上层层级错误");
		entity.put("25005", "The Upper Level ID is Error. 上层层级id验证错误");
		entity.put("25100", "Add Agent ID successful 代理帐号新增成功");
		entity.put("30001", "Successful 设定成功");
		entity.put("30002", "Failed 设定失败");
		entity.put("40001", "The owner is not exist. 厅不存在");
		entity.put("40002", "The member is ceased. 会员已经停用");
		entity.put("40003", "The agent is ceased. 上层已经停用");
		entity.put("40005", "Level of the agent is wrong. 上层阶层错误");
		entity.put("40006", "User ID is failed. 帐号验证失败");
		entity.put("40007", "Length of the password is wrong. 密码长度错误");
		entity.put("40008", "API is not open. 接口未开放");
		entity.put("40009", "Exceed the limit. 超过限制笔数");
		entity.put("40010", "Start Date Error 开始日期验证错误");
		entity.put("40011", "End Date Error 结束日期验证错误");
		entity.put("40012", "Start Time Error 开始时间验证错误");
		entity.put("40013", "End Time Error 结束时间验证错误");
		entity.put("40014", "Date Error 日期验证错误");
		entity.put("40015", "Length of the account is too long. 帐号长度过长");
		entity.put("44000", "Key error key验证错误");
		entity.put("44001", "The parameters are not complete. 参数未带齐");
		entity.put("44002", "Don't have authority. 无权限");
		entity.put("44003", "Now the API is busy, please wait. API忙碌中(请等待前一笔Result 回传后，再送新的Request。)");
		entity.put("44004", "Now the API is busy, please wait. API忙碌中");
		entity.put("45000", "The game is not exist. 桌号不存在");
		entity.put("45003", "The Live game is not exist. 视讯游戏不存在");
		entity.put("45005", "The Casino game is not exist. 机率游戏不存在");
		entity.put("45006", "GameType Error 桌号不存在");
		entity.put("44444", "System is in maintenance 系统维护中");
		entity.put("44445", "Game is in maintenance 系统维护中");
		entity.put("44900", "IP is not accepted. IP不被允许");
		entity.put("47002", "Parameter action is wrong. 参数action错误");
		entity.put("47003", "Parameter is wrong. 參數錯誤");
		entity.put("50001", "Successful 成功");
		entity.put("50002", "Fail 失败");
		entity.put("50003", "HallID Error 未选取厅主");
		entity.put("99999", "Login Successful 登入成功");
		return entity.get(key);
	}
	
	/**
	 * 获取游戏名称
	 * @param key
	 * @return
	 */
	public static String getGameName(String key){
		Map<String,String> entity = new HashMap<String,String>();
		//BB体育游戏
		entity.put("BK", "篮球");
		entity.put("BS", "棒球");
		entity.put("F1", "其他");
		entity.put("FB", "美式足球");
		entity.put("FT", "足球");
		entity.put("IH", "冰球");
		entity.put("SP", "冠军赛");
		entity.put("TN", "网球");
		entity.put("CB", "混合过关");
		//彩票游戏	
		entity.put("LT", "六合彩");
		entity.put("BJ3D", "3D彩");
		entity.put("PL3D", "排列三");
		entity.put("BBPK", "BB PK3");
		entity.put("BB3D", "BB3D");
		entity.put("BBKN", "BB快乐彩");
		entity.put("BBRB", "BB滚球王");
		entity.put("SH3D", "上海时时乐");
		entity.put("CQSC", "重庆时时彩");
		entity.put("TJSC", "天津时时彩");
		entity.put("JXSC", "江西时时彩");
		entity.put("XJSC", "新疆时时彩");
		entity.put("MTPK", "幸运飞艇");
		entity.put("CQSF", "重庆幸运农场");
		entity.put("GXSF", "广西十分彩");
		entity.put("TJSF", "天津十分彩");
		entity.put("BJPK", "北京PK拾");
		entity.put("BJKN", "北京快乐8");
		entity.put("CAKN", "加拿大卑斯");
		entity.put("GDE5", " 广东11选5");
		entity.put("JXE5", "江西11选5");
		entity.put("SDE5", "山东十一运夺金");
		entity.put("CQWC", "重庆百变王牌");
		entity.put("JLQ3", "吉林快3");
		entity.put("JSQ3", "江苏快3");
		entity.put("AHQ3", "安徽快3");
		//3D厅
		entity.put("15006", "3D玉蒲团");
		entity.put("15016", "厨王争霸");
		entity.put("15018", "激情243");
		entity.put("15019", "倩女幽魂");
		entity.put("15021", "全民狗仔");
		entity.put("15022", "怒火领空");
		entity.put("15024", "2014世足赛");
		//视讯游戏
		entity.put("3001", "百家乐");
		entity.put("3002", "二八杠");
		entity.put("3003", "龙虎斗");
		entity.put("3005", "三公");
		entity.put("3006", "温州牌九");
		entity.put("3007", "轮盘");
		entity.put("3008", "骰宝");
		entity.put("3010", "德州扑克");
		entity.put("3011", "色碟");
		entity.put("3012", "牛牛");
		entity.put("3014", "无限21点");
		entity.put("3015", "番摊");
		//机率游戏
		entity.put("5005", "惑星战记");
		entity.put("5006", "Starburst");
		entity.put("5007", "激爆水果盘");
		entity.put("5008", "猴子爬树");
		entity.put("5009", "金刚爬楼");
		entity.put("5011", "西游记");
		entity.put("5012", "外星争霸");
		entity.put("5013", "传统");
		entity.put("5014", "丛林");
		entity.put("5015", "FIFA2010");
		entity.put("5016", "史前丛林冒险");
		entity.put("5017", "星际大战");
		entity.put("5018", "齐天大圣");
		entity.put("5019", "水果乐园");
		entity.put("5020", "热带风情");
		entity.put("5025", "法海斗白蛇");
		entity.put("5026", "2012 伦敦奥运");
		entity.put("5027", "功夫龙");
		entity.put("5028", "中秋月光派对");
		entity.put("5029", "圣诞派对");
		entity.put("5030", "幸运财神");
		entity.put("5034", "王牌5PK");
		entity.put("5035", "加勒比扑克");
		entity.put("5039", "鱼虾蟹");
		entity.put("5040", "百搭二王");
		entity.put("5041", "7PK");
		entity.put("5042", "异星战场");
		entity.put("5047", "尸乐园");
		entity.put("5048", "特务危机");
		entity.put("5049", "玉蒲团");
		entity.put("5050", "战火佳人");
		entity.put("5057", "明星97");
		entity.put("5058", "疯狂水果盘");
		entity.put("5059", "马戏团");
		entity.put("5060", "动物奇观五");
		entity.put("5061", "超级7");
		entity.put("5062", "龙在囧途");
		entity.put("5063", "水果拉霸");
		entity.put("5064", "扑克拉霸");
		entity.put("5065", "筒子拉霸");
		entity.put("5066", "足球拉霸");
		entity.put("5070", "黄金大转轮");
		entity.put("5073", "百家乐大转轮");
		entity.put("5076", "数字大转轮");
		entity.put("5077", "水果大转轮");
		entity.put("5078", "象棋大转轮");
		entity.put("5079", "3D数字大转轮");
		entity.put("5080", "乐透转轮");
		entity.put("5083", "钻石列车");
		entity.put("5084", "圣兽传说");
		entity.put("5086", "海底派对");
		entity.put("5088", "斗大");
		entity.put("5089", "红狗");
		entity.put("5091", "三国拉霸");
		entity.put("5092", "封神榜");
		entity.put("5093", "金瓶梅");
		entity.put("5094", "金瓶梅2");
		entity.put("5095", "斗鸡");
		entity.put("5101", "欧式轮盘");
		entity.put("5102", "美式轮盘");
		entity.put("5103", "彩金轮盘");
		entity.put("5104", "法式轮盘");
		entity.put("5106", "三国");
		entity.put("5115", "经典21点");
		entity.put("5116", "西班牙21点");
		entity.put("5117", "维加斯21点");
		entity.put("5118", "奖金21点");
		entity.put("5131", "皇家德州扑克");
		entity.put("5201", "火焰山");
		entity.put("5202", "月光宝盒");
		entity.put("5203", "爱你一万年");
		entity.put("5204", "2014 FIFA");
		entity.put("5401", "天山侠侣传");
		entity.put("5402", "夜市人生");
		entity.put("5403", "七剑传说");
		entity.put("5404", "沙滩排球");
		entity.put("5405", "暗器之王");
		entity.put("5406", "神舟27");
		entity.put("5407", "大红帽与小野狼");
		entity.put("5601", "秘境冒险");
		entity.put("5701", "连连看");
		entity.put("5703", "发达啰");
		entity.put("5704", "斗牛");
		entity.put("5705", "聚宝盆");
		entity.put("5706", "浓情巧克力");
		entity.put("5707", "金钱豹");
		entity.put("5801", "海豚世界");
		entity.put("5802", "阿基里斯");
		entity.put("5803", "阿兹特克宝藏");
		entity.put("5804", "大明星");
		entity.put("5805", "凯萨帝国");
		entity.put("5806", "奇幻花园");
		entity.put("5808", "浪人武士");
		entity.put("5809", "空战英豪");
		entity.put("5810", "航海时代");
		entity.put("5811", "狂欢夜");
		entity.put("5821", "国际足球");
		entity.put("5823", "发大财");
		entity.put("5824", "恶龙传说");
		entity.put("5825", "金莲");
		entity.put("5826", "金矿工");
		entity.put("5827", "老船长");
		entity.put("5828", "霸王龙");
		entity.put("5831", "高球之旅");
		entity.put("5832", "高速卡车");
		entity.put("5833", "沉默武士");
		entity.put("5835", "喜福牛年");
		entity.put("5836", "龙卷风");
		entity.put("5837", "喜福猴年");
		entity.put("5901", "连环夺宝");
		entity.put("5902", "糖果派对");
		entity.put("5903", "秦皇秘宝");
		entity.put("5888", "JACKPOT");
		return entity.get(key);
	}
	
	/**
	 * 获取彩票类型列表
	 * @param key
	 * @return
	 */
	public static String[] getCPList(){
		return new String[] { "LT","BJ3D","PL3D","BBPK","BB3D","BBKN","BBRB","SH3D","CQSC","TJSC","JXSC","XJSC","MTPK","CQSF","GXSF","TJSF","BJPK","BJKN","CAKN","GDE5","JXE5","SDE5","CQWC","JLQ3","JSQ3","AHQ3"};
	}
	
	public static void main(String[] arg){
		System.out.println(getCPList().length);
	}
}
