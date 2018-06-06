package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.BettingAllGameWinloseDetail;
import com.maven.entity.TakeDepositRecord;

@Service
public interface BettingAllGameWinloseDetailService {
	/**
	 * 分页查询
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("slave")
	List<BettingAllGameWinloseDetail> selectForPage(Map<String, Object> paramObj) throws Exception;
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("slave")
	int selectForPageCount(Map<String, Object> paramObj) throws Exception;	
   /**
     * 数据统计之金额统计
     * @param object
     * @return
     */
    @DataSource("slave")
    public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception;
    
    /**
     * 日报表统计
     * @param paramObj
     * @return
     * @throws Exception
     */
    @DataSource("slave")
	List<BettingAllGameWinloseDetail> call_uspUserDayReport(Map<String, Object> paramObj)throws Exception;
}