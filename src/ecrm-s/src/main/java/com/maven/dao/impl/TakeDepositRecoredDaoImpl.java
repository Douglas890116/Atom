package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.TakeDepositRecoredDao;
import com.maven.entity.TakeDepositRecord;
import com.maven.util.StringUtils;
@Repository
public class TakeDepositRecoredDaoImpl extends BaseDaoImpl<TakeDepositRecord>implements TakeDepositRecoredDao {

	/**
	 * 保存 存取款记录 slave
	 * @param takeDepositRecord
	 */
	public void saveTakeDepositRecord(TakeDepositRecord takeDepositRecord) {
		getSqlSession().insert("TakeDepositRecord.save", takeDepositRecord);
	}
	/**
	 * 查询分页数据
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	public List<TakeDepositRecord> findTakeDepositRecord(Map<String, Object> paramObj) {
		return getSqlSession().selectList("TakeDepositRecord.findTakeDepositRecord", paramObj);
	}
	/**
	 * 统计数据
	 * @param paramObj
	 * @return int
	 */
	public Map<String, Object> countTakeDepositRecord(Map<String, Object> paramObj) {
		return getSqlSession().selectOne("TakeDepositRecord.countTakeDepositRecord",paramObj);
	}
	/**
	 * 根据单条订单号删除记录
	 * @param session
	 * @param request
	 * @return Map<String,String> 
	 */
	public void deleteTakeRepositRecord(String code) {
		getSqlSession().delete("TakeDepositRecord.deleteTakeRepositRecord", code);
	}
	/**
	 * 删除选中的一条或者多条数据
	 * @param request
	 */
	public void deleteSelectAllTakeRepositRecord(String[] array) {
		getSqlSession().delete("TakeDepositRecord.deleteSelectAllTakeRepositRecord", array);
	}
	/**
	 * 存取款记录修改
	 * @param request
	 * @return URL
	 */
	public void updateTakeDepositRecord(TakeDepositRecord takeDepositRecord) {
		getSqlSession().update("TakeDepositRecord.updateTakeDepositRecord",takeDepositRecord);
	}
	
	public int cancelAudit(TakeDepositRecord takeDepositRecord){
		return getSqlSession().update("TakeDepositRecord.cacelAudit",takeDepositRecord);
	}
	
	@Override
	public int updateFlow(TakeDepositRecord takeDepositRecord) {
		return getSqlSession().update("TakeDepositRecord.updateFlow",takeDepositRecord);
	}
	/**
	 * 根据订单号查询
	 * @param request
	 * @return TakeDepositRecord
	 */
	public TakeDepositRecord findOrderNumberTakeDepositRecord(String orderNumber) {
		return getSqlSession().selectOne("TakeDepositRecord.findOrderNumberTakeDepositRecord", orderNumber);
	}
	/**
	 * 审批通过根据订单号修改订单状态以及保存审批提交的图片名称
	 * @param TakeDepositRecord
	 */
	public int updateTakeDepositRecoredStatus(TakeDepositRecord takeDepositRecord) {
		return getSqlSession().update("TakeDepositRecord.updateTakeDepositRecoredStatus", takeDepositRecord);
	}
	/**
	 * 根据员工编码删除所有的存取款记录
	 * @param employeecode
	 * @throws Exception
	 */
	@Override
	public void deleteEmployeeRecord(String[] array) {
		getSqlSession().delete("TakeDepositRecord.deleteEmployeeRecord", array);
	}
	/**
	 * 存取款余额统计查询
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	@Override
	public List<TakeDepositRecord> countDepositTakeRecordDatail(Map<String, Object> paramObj) {
		return getSqlSession().selectList("TakeDepositRecord.countDepositTakeRecordMoney", paramObj);
	}
	/**
	 * 存取款余额记录数量统计
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	@Override
	public int countDepositTakeRecordDatailCount(Map<String, Object> paramObj) {
		return getSqlSession().selectOne("TakeDepositRecord.countDepositTakeRecordMoneyCount", paramObj);
	}
	
	/**
	 * 根据员工编码统计该员工当天取款次数与取款总金额 
	 * @param employeecode
	 * @return TakeDepositRecord
	 */
	@Override
	public TakeDepositRecord takeCountAndTakeTotalAmount(String employeecode) {
		return getSqlSession().selectOne("TakeDepositRecord.takeCountAndTakeTotalAmount", employeecode);
	}
	
	/**
	 * 订单处理时间区域统计
	 * @param paramObj
	 */
	@Override
	public List<TakeDepositRecord> businessHandleCount(Map<String, Object> paramObj) {
		return getSqlSession().selectList("TakeDepositRecord.businessHandleCount", paramObj);
	}
	
	/**
	 * 用户存款排名报表查询
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<TakeDepositRecord> employeeDepositRankingReportCount(Map<String, Object> paramObj) {
		return getSqlSession().selectList("TakeDepositRecord.employeeDepositRankingReportCount", paramObj);
	}
	
	/**
	 * 用户取款排名报表查询
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<TakeDepositRecord> queryEmployeeWithdrawalsRankingReportCount(Map<String, Object> paramObj) {
		return getSqlSession().selectList("TakeDepositRecord.queryEmployeeWithdrawalsRankingReportCount", paramObj);
	}
	
	/**
	 *取款次数排名查询
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<TakeDepositRecord> queryEmployeeWithdraNumberRanking(Map<String, Object> paramObj){
		return getSqlSession().selectList("TakeDepositRecord.queryEmployeeWithdraNumberRanking", paramObj);
	}
	
	/**
	 * 用户存取款统计 
	 */
	@Override
	public List<TakeDepositRecord> userDepositTakeCount(Map<String, Object> paramObj) {
		return getSqlSession().selectList("TakeDepositRecord.userDepositTakeCount", paramObj);
	}
	
	/**
	 * 用户提存比分析统计
	 */
	@Override
	public List<TakeDepositRecord> userDepositTakeRate(Map<String, Object> paramObj) {
		return getSqlSession().selectList("TakeDepositRecord.userDepositTakeRate", paramObj);
	}
	
	/**
	 * 用户输赢概率统计分析
	 */
	@Override
	public List<TakeDepositRecord> userGameWinLoseRate(Map<String, Object> paramObj) {
		return getSqlSession().selectList("TakeDepositRecord.userGameWinLoseRate", paramObj);
	}
	
	/**
	 * 统计用户当天或历史累计存款总额
	 * @param employeecode
	 * @param flag 0=昨天 1=当天 2=历史累计
	 * @return
	 * @throws Exception
	 */
	public double userDepositTakeMoney(Map<String, Object> paramObj) {
		
		TakeDepositRecord takeDepositRecord = getSqlSession().selectOne("TakeDepositRecord.userDepositTakeMoney", paramObj);
		Object totalMoney = takeDepositRecord.getAllDepositMoney();
		if(totalMoney == null) {
			return 0.0;
		} else {
			return Double.valueOf(totalMoney.toString());
		}
	}
	
	@Override
	public int updateHandEmployee(Map<String, Object> object) {
		return getSqlSession().update("TakeDepositRecord.updateBatchHandleemployee", object);
	}
	
	@Override
	public List<TakeDepositRecord> findMutilOrdersByOrdernumber(String[] orders) {
		return getSqlSession().selectList("TakeDepositRecord.findMutilOrdersByOrdernumber", orders);
	}
	
	@Override
	public TakeDepositRecord getLastByParames(Map<String, Object> parames) {
		return getSqlSession().selectOne("TakeDepositRecord.selectLastByParames", parames);
	}
	@Override
	public TakeDepositRecord selectSumAmount(Map<String, Object> parame) {
		return getSqlSession().selectOne("TakeDepositRecord.selectSumAmount", parame);
	}
	
	/**
	 * 存储过程,总计-取款稽查
	 */
	public List<TakeDepositRecord> userTakemoneyInspectNew(Map<String, Object> paramObj) throws Exception {
		return getSqlSession().selectList("TakeDepositRecord.userTakemoneyInspectNew", paramObj);
	}
	/**
	 * 存储过程,总计-取款稽查
	 */
	public Map<String, Object> userAllMoney(Map<String, Object> paramObj) throws Exception {
		return getSqlSession().selectOne("TakeDepositRecord.userAllTotalMoney", paramObj);
	}
	
	/**
	 * 查询会员的存取款相关汇总信息
	 */
	public Map<String, Object> userAllInfoMoney(Map<String, Object> paramObj) throws Exception {
		return getSqlSession().selectOne("TakeDepositRecord.userAllInfoMoney", paramObj);
	}
	
	/**
	 * 查询会员的游戏和优惠和洗码等所有金额相关的数据
	 */
	public List<TakeDepositRecord> userAllInfoGame(Map<String, Object> paramObj) throws Exception {
		return getSqlSession().selectList("TakeDepositRecord.userAllInfoGame", paramObj);
	}
}
