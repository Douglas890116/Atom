package com.maven.game.enums;

import com.maven.entity.EnterpriseEmployeeType;

public class GameEnum {
	
	
	public enum Enum_GameType{
		东方AG("AGGame","AG","" ,"aog",true,"OAG"),	/******停止使用********/
		祥瑞("XCPGame","祥瑞","" ,"xcp",false,"CP"), /******停止使用********/
		沙巴("IBCGame","沙巴","" ,"sb",true,"IBC"), /******停止使用********/
		
		波音("BBINGame","BBIN波音","" ,"bbn",false,"BBIN"),
		AG("TAGGame","AG亚游","" ,"tag",false,"TAG"),
		AV老虎机("AVGame","AV老虎机","" ,"av",false,"AV"),//15个长度
		沙龙("SAGame","SA沙龙","" ,"sa",false,"SA"),
		TTG("TTGGame","TTG老虎机","" ,"ttg",false,"TTG"),
		PNG("PNGGame","PNG老虎机","" ,"png",false,"PNG"),
		DZDY("DZDYGame","德州电子游戏","" ,"dz",false,"DZDZ"),
		SGS("SGSGame","申博","" ,"sgs",false,"SGS"),
		M88("M88Game","M88体育","" ,"m88",false,"M88"),//17个长度
		HAB("HABGame","HB哈巴电子","" ,"hb",false,"HAB"),
		QTtech("QTGame","QTtech电子","" ,"qt",false,"QT"),
		GB彩票("GBGame","GB彩票","" ,"gb",false,"GB"),
		EBet("EBETGame","eBET真人","" ,"ebt",false,"EBET"),//暂时没有找到修改密码的api
		TGPlayer("TGPGame","TGPlayer申博","" ,"tgp",false,"TGP"),
		eIBCGame("eIBCGame","IBC沙巴体育","" ,"eibc",false,"EIBC"),//沙巴体育
		
		MG("MGGame","MG游戏","" ,"mg",false,"MG"),
		
		环球("NHQGame","HY真人","" ,"hq",true,"HY"),
		洲际("ZJGame","洲际","" ,"zj",true,"ZJ"),
		PT("PTGame","PT游戏","" ,"pt",true,"PT"),
		东方("OGGame","OG东方真人","" ,"og",true,"OG"),
		棋牌("QPGame","帝王棋牌","" ,"qp",true,"QP"),//15个长度
		GG("GGGame","GG捕鱼","" ,"gg",true,"GG"),//15个长度
		IDN("IDNGame","IDN扑克","" ,"idn",true,"IDN"),//15个长度
		GGPoker("GGPGame","GG扑克","" ,"ggp",true,"GGP"),//GG扑克
		WIN88("W88Game","PT游戏","" ,"w88",true,"W88"),
		
		YoPLAY("YoPLAYGame","电子娱乐","" ,"yo",false,"YoPLAY"),
		
		去玩棋牌("QWPGame", "去玩棋牌", "", "qwp", true, "QWP"),//去玩棋牌
		IM体育("IMGame", "IM体育", "", "im", true, "IM"),//IM体育
		JDB168("JDBGame", "加多宝老虎机", "", "jdb", false, "JDB");//JDB168
		
		;
		public String gametype;
		public String name;
		public String prefix;
		public String suffix;
		public boolean updatepassword;//是否支持密码修改
		public String bettingcode;//投注明细表的平台简写
		
        private Enum_GameType(String gametype,String name,String prefix,String suffix,boolean updatepassword,String bettingcode) {
            this.gametype = gametype;
            this.name = name;
            this.prefix = prefix;
            this.suffix = suffix;
            this.updatepassword = updatepassword;
            this.bettingcode = bettingcode;
        }
        
        public static boolean validate(String gametype){
        	for (Enum_GameType eg : Enum_GameType.values()) {
				if(gametype.equals(eg.gametype))
					return true;
			}
        	return false;
        }
        
        public static String getname(String gametype){
        	for (Enum_GameType eg : Enum_GameType.values()) {
				if(gametype.equals(eg.gametype)) {
					return eg.name;
				}
			}
        	return gametype;
        }
        
        public static String getnameByBettingcode(String bettingcode){
        	for (Enum_GameType eg : Enum_GameType.values()) {
				if(bettingcode.equals(eg.bettingcode)) {
					return eg.name;
				}
			}
        	return bettingcode;
        }
        
        public static Enum_GameType getByBettingcode(String bettingcode){
        	for (Enum_GameType eg : Enum_GameType.values()) {
				if(bettingcode.equals(eg.bettingcode)) {
					return eg;
				}
			}
        	return null;
        }
        public static Enum_GameType getByGametype(String gametype){
        	for (Enum_GameType eg : Enum_GameType.values()) {
				if(gametype.equals(eg.gametype)) {
					return eg;
				}
			}
        	return null;
        }
	}
	public enum Enum_usertype{
		代理("agent"),
		会员("user"),
		测试("test");
		public String value;
        private Enum_usertype(String _value) {
            this.value = _value;
        }
        public String getValue() {  
            return value;  
        }  
        
        public static String userType(String typecode){
        	if(typecode.equals(EnterpriseEmployeeType.Type.会员.value)){
        		return 会员.value;
        	}else if(typecode.equals("-1")){
        		return 测试.value;
        	}else{
        		return 代理.value;
        	}
        }
	}
	
	public enum Enum_deviceType{
		电脑("0"),
		手机("1");
		public String code;
		private Enum_deviceType(String code){
			this.code = code;
		}
	}

}
