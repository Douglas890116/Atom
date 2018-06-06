package com.maven.entity;

public class DataHandle {
    private String handlecode;

    private String handledesc;

    private String updatetime;

    private String lasttime;

    private Integer lastnum;

    private Integer daynum;

    private Integer allnum;
    
    private Integer intervalnum;
    
    private String remark;
    
    private String gametype;
    
    /**
     * 视图：
     */
    private int flag = 0;//正常

    public String getHandlecode() {
        return handlecode;
    }

    public void setHandlecode(String handlecode) {
        this.handlecode = handlecode;
    }

    public String getHandledesc() {
        return handledesc;
    }

    public void setHandledesc(String handledesc) {
        this.handledesc = handledesc;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public Integer getLastnum() {
        return lastnum;
    }

    public void setLastnum(Integer lastnum) {
        this.lastnum = lastnum;
    }

    public Integer getDaynum() {
        return daynum;
    }

    public void setDaynum(Integer daynum) {
        this.daynum = daynum;
    }

    public Integer getAllnum() {
        return allnum;
    }

    public void setAllnum(Integer allnum) {
        this.allnum = allnum;
    }

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Integer getIntervalnum() {
		return intervalnum;
	}

	public void setIntervalnum(Integer intervalnum) {
		this.intervalnum = intervalnum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGametype() {
		return gametype;
	}

	public void setGametype(String gametype) {
		this.gametype = gametype;
	}
}