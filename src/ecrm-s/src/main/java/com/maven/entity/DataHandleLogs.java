package com.maven.entity;

import java.util.Date;

public class DataHandleLogs {
    private String lsh;

    private String gametype;

    private Date lasttime;

    private String dataparams;

    private String datalog;

    private String agentaccount;

    private Integer flag;

    private String remark;

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public String getDataparams() {
        return dataparams;
    }

    public void setDataparams(String dataparams) {
        this.dataparams = dataparams;
    }

    public String getDatalog() {
        return datalog;
    }

    public void setDatalog(String datalog) {
        this.datalog = datalog;
    }

    public String getAgentaccount() {
        return agentaccount;
    }

    public void setAgentaccount(String agentaccount) {
        this.agentaccount = agentaccount;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}