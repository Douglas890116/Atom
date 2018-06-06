package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.BettingAllGameWinloseDetailDao;
import com.maven.entity.BettingAllGameWinloseDetail;
import com.maven.service.BettingAllGameWinloseDetailService;

@Service
public class BettingAllGameWinloseDetailServiceImpl extends BaseServiceImpl<BettingAllGameWinloseDetail> implements BettingAllGameWinloseDetailService{

	@Autowired
	private BettingAllGameWinloseDetailDao dao;
	
	@Override
	public BaseDao<BettingAllGameWinloseDetail> baseDao() {
		return dao;
	}

	@Override
	public Class<BettingAllGameWinloseDetail> getClazz() {
		return BettingAllGameWinloseDetail.class;
	}
	/**
	 * 分页查询
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public List<BettingAllGameWinloseDetail> selectForPage(Map<String, Object> paramObj) throws Exception {
		return this.dao.selectForPage(paramObj);
	}
	
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public int selectForPageCount(Map<String, Object> paramObj) throws Exception {
		return this.dao.selectForPageCount(paramObj);
	}
	   /**
     * 数据统计之金额统计
     * @param object
     * @return
     */
    @Override
    public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
        return dao.takeRecordCountMoney(object);
    }
    
    /**
     * 日报表统计
     * @param paramObj
     * @return
     * @throws Exception
     */
    public List<BettingAllGameWinloseDetail> call_uspUserDayReport(Map<String, Object> paramObj)throws Exception {
    	return dao.uspUserDayReport(paramObj);
    }
}