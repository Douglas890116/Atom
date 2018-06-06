package com.maven.entity;

import java.util.UUID;

import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.util.RandomString;

public class EmployeeMoneyChangeType {
	/** 帐变类型编码 */
    private String moneychangetypecode;

    /** 帐变类型 */
    private String moneychangetype;
    
    /** 帐变类型类别 */
    private String moneychangetypeclassify;

	/** 进出帐类型 */
    private Byte moneyinouttype;

    /** 数据状态 */
    private String datastatus;
    
    public EmployeeMoneyChangeType(){}
    
    public EmployeeMoneyChangeType(String moneychangetypecode,Enum_moneyinouttype  moneyinouttype){
    	this.moneychangetypecode = moneychangetypecode;
    	this.moneyinouttype = (byte)moneyinouttype.value;
    }
    
    public EmployeeMoneyChangeType(String moneychangetypecode,String moneychangetype,Enum_moneyinouttype  moneyinouttype){
    	this.moneychangetypecode = moneychangetypecode;
    	this.moneychangetype = moneychangetype;
    	this.moneyinouttype = (byte)moneyinouttype.value;
    }
    
    public enum Enum_moneyinouttype{
		进账(1),//用户账户余额增加
		出账(2);//用户账户余额减少
		public int value;
		private Enum_moneyinouttype(int value) {
        	this.value = value;
        }
		public int getValue() {
				return value;
		}
	}
    public enum moneychangetypeCategory{
    	常规("1"),//用户账户余额增加
    	活动("2");//用户账户余额减少
    	public String value;
    	private moneychangetypeCategory(String value) {
    		this.value = value;
    	}
    	public String getValue() {
    		return value;
    	}
    }
    
    /**
     * 冲正冲负备注描述
     * @author Administrator
     *
     */
    public enum Enum_moneyaddtype{
    	存款冲正("1","存款冲正"),
    	存款冲负("2","存款冲负"),
    	取款冲正("3","取款冲正"),
    	取款冲负("4","取款冲负"),
    	优惠活动冲正("5","优惠活动冲正"),
    	优惠活动冲负("6","优惠活动冲负"),
    	其他("7","其他")
    	;
    	public String desc;
    	public String value;
    	private Enum_moneyaddtype(String value, String desc) {
    		this.value = value;
    		this.desc = desc;
    	}
    	public String getDesc(){
        	return this.desc;
        }
    	public String getValue() {
    		return value;
    	}
    }
    
    public enum Enum_moneychangetype{
		存款("8D37FD20D92043FA8D856590F0DFED0F","存款"),
		取款("8CD5E45210A44F3287CF2C0D7C61703E","取款"),
		取款驳回("7DF02AB6F0854BF0831D5C4EA6646B3C","取款驳回"),
		取款拒绝("312302DD636E498286C1028FE80153DD","取款拒绝"),
		游戏上分("AF0B2F04CCA64E3197F047402FEE5832","游戏上分"),
		上分失败返还("577C286638534926B93C14489BB5C5C7","上分失败返还"),
		游戏下分("2BC2CB7FDD7B4720906B56812E075F94","游戏下分"),
		冲正("1A53AEC4179E4E46AEE7EE752C16E3B1","冲正"),
		冲负("831F252CEAE94DD0A740260EE151A437","冲负"),
		
//		以下几个活动统称为[优惠活动]
		
//		体验红包("836E5DFAE46C477DAE15A523A06D6E7C","体验红包"),
//		笔笔存返利("6EF182A8358C4F8C92FF8B814D4DA782","笔笔存返利"),
//		输值月月返("F8ED21848E3E480CB8281CA9A20B2993","输值月月返"),
//		签到抽奖("88C2DE5DBC644E94A81A876456809C2C","签到抽奖"),
		
		优惠活动("D6B0C11A0AC44EBBB1538BE69B004811","优惠活动"),
		
		洗码日结("B1FF4C4ADC9C423C8D0344DDFD7DC279","洗码日结"),
		洗码周结("6B06E77AA855454EB5ADF60B6CC37787","洗码周结"),
		周结校验补发("56F82CC5B5DC4FFABBFD62F82CACA891","周结校验补发"),
		洗码冲减("C22A505981D8490781026BC899F3C692","洗码冲减"),
		
		转出金额("5FFD22A671B5440DB46D5E3E7D36422F","转出金额"),
		转入金额("E229B46CE9864EB8AE35D7C51CFEC04C","转入金额"),
		
//		元宝兑换业务
		积分兑换("9389569F3D9D4A3D81E80968F2D228E4","积分兑换"),
		;
		public String value;
		public String desc;
        private Enum_moneychangetype(String value,String desc) {
        	this.value = value;
            this.desc = desc;
        }
        public String getDesc(String s){
        	return s+this.desc;
        }
        public String getValue() {
			return value;
		}
        
        public static Enum_moneychangetype getMoneyInOutComment(Object object){
        	if(object instanceof Enum_ordertype){
        		Enum_ordertype e = (Enum_ordertype)object;
        		if(e.value==Enum_ordertype.取款.value) return Enum_moneychangetype.取款;
        		if(e.value==Enum_ordertype.存款.value) return Enum_moneychangetype.存款;
        	}
        	return null;
        }
	}
    
    public String getMoneychangetypeclassify() {
		return moneychangetypeclassify;
	}

	public void setMoneychangetypeclassify(String moneychangetypeclassify) {
		this.moneychangetypeclassify = moneychangetypeclassify;
	}
    
    public String getMoneychangetypecode() {
        return moneychangetypecode;
    }

    public void setMoneychangetypecode(String moneychangetypecode) {
        this.moneychangetypecode = moneychangetypecode;
    }

    public String getMoneychangetype() {
        return moneychangetype;
    }

    public void setMoneychangetype(String moneychangetype) {
        this.moneychangetype = moneychangetype;
    }

    public Byte getMoneyinouttype() {
        return moneyinouttype;
    }

    public void setMoneyinouttype(Byte moneyinouttype) {
        this.moneyinouttype = moneyinouttype;
    }

    public String getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(String datastatus) {
        this.datastatus = datastatus;
    }
    
    public static void main(String[] args) {
//    	System.out.println(RandomString.UUID());
    	System.out.println("20171004093600000".length());
	}
}