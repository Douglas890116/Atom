package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.BettingAllGameWinloseDetail;

@Repository
public interface BettingAllGameWinloseDetailDao extends BaseDao<BettingAllGameWinloseDetail>{
	/**
	 * 分页查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	List<BettingAllGameWinloseDetail> selectForPage(Map<String, Object> paramObj);
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	int selectForPageCount(Map<String, Object> paramObj);
	
	   /**
     * 数据统计之金额统计
     * @param object
     * @return
     */
    Map<String, Object> takeRecordCountMoney(Map<String, Object> object);
    
    /**
	 * 日报表统计
	 * @param paramObj
	 * @throws Exception
	 */
    List<BettingAllGameWinloseDetail> uspUserDayReport(Map<String, Object> paramObj);
    
    /**
	 * 查询汇总数据
	 * @param paramObj
	 * @throws Exception
	 */
    List<BettingAllGameWinloseDetail> selectGroup(Map<String, Object> paramObj);
    
    /**
	 * 查询汇总数据
	 * @param paramObj
	 * @throws Exception
	 */
    List<BettingAllGameWinloseDetail> selectGroup2(Map<String, Object> paramObj);
    
    /**
	 * 汇总
	 * @param paramObj
	 * @throws Exception
	 */
    int updateByPatchno(Map<String, Object> paramObj);
    /**
	 * 取消汇总
	 * @param paramObj
	 * @throws Exception
	 */
    int updateByPatchnoCancel(BettingAllGameWinloseDetail paramObj);
}