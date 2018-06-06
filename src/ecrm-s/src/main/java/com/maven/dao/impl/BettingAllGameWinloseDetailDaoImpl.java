package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingAllGameWinloseDetailDao;
import com.maven.entity.BettingAllGameWinloseDetail;

@Repository
public class BettingAllGameWinloseDetailDaoImpl extends BaseDaoImpl<BettingAllGameWinloseDetail>
        implements BettingAllGameWinloseDetailDao {
    /**
     * 分页查询
     */
    @Override
    public List<BettingAllGameWinloseDetail> selectForPage(Map<String, Object> paramObj) {
        return getSqlSession().selectList("BettingAllGameWinloseDetail.selectForPage", paramObj);
    }

    /**
     * 分页查询 记录总数
     * 
     * @param paramObj
     * @throws Exception
     */
    @Override
    public int selectForPageCount(Map<String, Object> paramObj) {
        return getSqlSession().selectOne("BettingAllGameWinloseDetail.selectForPageCount", paramObj);
    }
    
    /**
     * 数据统计之金额统计
     * @param object
     * @return
     */
    @Override
    public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) {
        return getSqlSession().selectOne("BettingAllGameWinloseDetail.takeRecordCountMoney", object);
    }
    
    /**
	 * 日报表统计
	 * @param paramObj
	 * @throws Exception
	 */
    public List<BettingAllGameWinloseDetail> uspUserDayReport(Map<String, Object> paramObj) {
    	return getSqlSession().selectList("BettingAllGameWinloseDetail.uspUserDayReport", paramObj);
    }
    
    /**
	 * 查询汇总数据
	 * @param paramObj
	 * @throws Exception
	 */
    public List<BettingAllGameWinloseDetail> selectGroup(Map<String, Object> paramObj) {
    	return getSqlSession().selectList("BettingAllGameWinloseDetail.selectGroup", paramObj);
    }
    /**
	 * 查询汇总数据
	 * @param paramObj
	 * @throws Exception
	 */
    public List<BettingAllGameWinloseDetail> selectGroup2(Map<String, Object> paramObj) {
    	return getSqlSession().selectList("BettingAllGameWinloseDetail.selectGroup2", paramObj);
    }
    
    /**
	 * 汇总
	 * @param paramObj
	 * @throws Exception
	 */
    public int updateByPatchno(Map<String, Object> paramObj) {
    	return getSqlSession().update("BettingAllGameWinloseDetail.updateByPatchno", paramObj);
    }
    /**
	 * 取消汇总
	 * @param paramObj
	 * @throws Exception
	 */
    public int updateByPatchnoCancel(BettingAllGameWinloseDetail paramObj) {
    	return getSqlSession().update("BettingAllGameWinloseDetail.updateByPatchnoCancel", paramObj);
    }
}