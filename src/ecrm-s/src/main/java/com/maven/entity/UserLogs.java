package com.maven.entity;

import java.util.Date;
import java.util.UUID;

public class UserLogs {
    private String lsh;

    private String enterprisecode;

    private String employeecode;

    private String loginaccount;

    private String operatype;

    private String content;

    private Date createtime;

    private String operaer;

    private String remark;
    
    public UserLogs() {}
    public UserLogs(String enterprisecode,String employeecode,String loginaccount,Enum_operatype operatype,String content,String operaer,String remark) {
    	
    	this.lsh = UUID.randomUUID().toString();
    	this.createtime = new Date();
    	
    	this.enterprisecode = enterprisecode;
    	this.employeecode = employeecode;
    	this.loginaccount = loginaccount;
    	this.operatype = operatype.value;
    	this.content = content;
    	this.operaer = operaer;
    	this.remark = remark;
    }

    
    public enum Enum_operatype{
    	存取款业务("存取款业务","存取款业务"),
    	游戏信息业务("游戏信息业务","游戏信息业务"),
    	公司信息业务("公司信息业务","公司信息业务"),
    	品牌信息业务("品牌信息业务","品牌信息业务"),
    	IP白名单业务("IP白名单业务","IP白名单业务"),
    	用户资料业务("用户信息业务","用户信息业务"),//同一个
    	用户信息业务("用户信息业务","用户信息业务"),//同一个
    	银行卡业务("银行卡业务","银行卡业务"),
    	快捷支付业务("快捷支付业务","快捷支付业务"),
    	二维码收款业务("二维码收款业务","二维码收款业务"),
    	红利信息业务("红利信息业务","红利信息业务"),
    	优惠流水业务("优惠流水业务","优惠流水业务"),
    	冲正冲负业务("冲正冲负业务","冲正冲负业务"),
    	活动充值业务("活动充值业务","活动充值业务"),
    	运营公告业务("运营公告业务","运营公告业务"),
    	站点管理业务("站点管理业务","站点管理业务"),
    	站点模板业务("站点模板业务","站点模板业务"),
    	结算管理业务("结算管理业务","结算管理业务"),
    	流程管理业务("流程管理业务","流程管理业务"),
    	客服专线业务("客服专线业务","客服专线业务"),
    	活动管理业务("活动管理业务","活动管理业务"),
    	VIP信息业务("VIP信息业务","VIP信息业务"),
    	隐私数据业务("隐私数据业务","隐私数据业务"),
    	信用代理转账("信用代理转账","信用代理转账"),
    	;
		public String value;
		public String desc;
		
		private Enum_operatype(String value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
    
    
    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode;
    }

    public String getEmployeecode() {
        return employeecode;
    }

    public void setEmployeecode(String employeecode) {
        this.employeecode = employeecode;
    }

    public String getLoginaccount() {
        return loginaccount;
    }

    public void setLoginaccount(String loginaccount) {
        this.loginaccount = loginaccount;
    }

    public String getOperatype() {
        return operatype;
    }

    public void setOperatype(String operatype) {
        this.operatype = operatype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getOperaer() {
        return operaer;
    }

    public void setOperaer(String operaer) {
        this.operaer = operaer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}