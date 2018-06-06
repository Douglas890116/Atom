package com.maven.entity;

import java.util.Date;

public class BettingAllPlan {
	
	private Integer lsh;
    private String patchno;

    private String enterprisecode;

    private Integer totalCount;

    private Double totalBetmoney;

    private Double totalValidbet;

    private Double totalNetmoney;

    private Integer betday;

    private String operater;

    private Date operaterTime;

    private Integer status;
    
    
    public BettingAllPlan() {
		super();
	}
    public BettingAllPlan(String patchno, String enterprisecode, Integer totalCount, Double totalBetmoney,
			Double totalValidbet, Double totalNetmoney, Integer betday, String operater, Date operaterTime,
			Integer status) {
		super();
		this.patchno = patchno;
		this.enterprisecode = enterprisecode;
		this.totalCount = totalCount;
		this.totalBetmoney = totalBetmoney;
		this.totalValidbet = totalValidbet;
		this.totalNetmoney = totalNetmoney;
		this.betday = betday;
		this.operater = operater;
		this.operaterTime = operaterTime;
		this.status = status;
	}

	public enum Enum_status{
		已汇总(0,"已汇总"),
		财务已核准(2,"财务已核准"),
		已支付(3,"已支付");
		public Integer value;
		public String desc;
		
		private Enum_status(Integer value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}

    public String getPatchno() {
        return patchno;
    }

    public void setPatchno(String patchno) {
        this.patchno = patchno;
    }

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Double getTotalBetmoney() {
        return totalBetmoney;
    }

    public void setTotalBetmoney(Double totalBetmoney) {
        this.totalBetmoney = totalBetmoney;
    }

    public Double getTotalValidbet() {
        return totalValidbet;
    }

    public void setTotalValidbet(Double totalValidbet) {
        this.totalValidbet = totalValidbet;
    }

    public Double getTotalNetmoney() {
        return totalNetmoney;
    }

    public void setTotalNetmoney(Double totalNetmoney) {
        this.totalNetmoney = totalNetmoney;
    }

    public Integer getBetday() {
        return betday;
    }

    public void setBetday(Integer betday) {
        this.betday = betday;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    public Date getOperaterTime() {
        return operaterTime;
    }

    public void setOperaterTime(Date operaterTime) {
        this.operaterTime = operaterTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
	public Integer getLsh() {
		return lsh;
	}
	public void setLsh(Integer lsh) {
		this.lsh = lsh;
	}
}