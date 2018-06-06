package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.entity.TakeDepositRecord;
@Repository
public interface TakeDepositRecoredDao extends BaseDao<TakeDepositRecord>{
	/**
	 * 保存 存取款记录 slave
	 * @param takeDepositRecord
	 */
	void saveTakeDepositRecord(TakeDepositRecord takeDepositRecord);
	/**
	 * 查询分页数据
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	List<TakeDepositRecord> findTakeDepositRecord(Map<String, Object> paramObj);
	/**
	 * 统计数据
	 * @param paramObj
	 * @return int
	 */
	Map<String, Object> countTakeDepositRecord(Map<String, Object> paramObj);
	/**
	 * 根据单条订单号删除记录
	 * @param session
	 * @param request
	 * @return Map<String,String> 
	 */
	void deleteTakeRepositRecord(String code);
	/**
	 * 删除选中的一条或者多条数据
	 * @param request
	 */
	void deleteSelectAllTakeRepositRecord(String[] array);
	/**
	 * 存取款记录修改
	 * @param request
	 * @return URL
	 */
	void updateTakeDepositRecord(TakeDepositRecord takeDepositRecord);
	/**
	 * 根据订单号查询
	 * @param request
	 * @return TakeDepositRecord
	 */
	TakeDepositRecord findOrderNumberTakeDepositRecord(String orderNumber);
	/**
	 * 审批通过根据订单号修改订单状态以及保存审批提交的图片名称
	 * @param TakeDepositRecord
	 */
	int updateTakeDepositRecoredStatus(TakeDepositRecord takeDepositRecord);
	
	/**
	 * 撤回订单审核者
	 * @param takeDepositRecord
	 */
	public int cancelAudit(TakeDepositRecord takeDepositRecord);
	
	/**
	 * 修改订单工作流相关信息
	 * @param takeDepositRecord
	 * @return
	 */
	int updateFlow(TakeDepositRecord takeDepositRecord);
	/**
	 * 根据员工编码删除所有的存取款记录
	 * @param employeecode
	 * @throws Exception
	 */
	void deleteEmployeeRecord(String[] array);
	/**
	 * 存取款余额统计查询
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	List<TakeDepositRecord> countDepositTakeRecordDatail(Map<String, Object> paramObj);
	/**
	 * 存取款余额记录数量统计
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	int countDepositTakeRecordDatailCount(Map<String, Object> paramObj);
	/**
	 * 根据员工编码统计该员工当天取款次数与取款总金额 
	 * @param employeecode
	 * @return
	 */
	TakeDepositRecord takeCountAndTakeTotalAmount(String employeecode);
	/**
	 * 订单处理时间区域统计
	 * @param paramObj
	 */
	List<TakeDepositRecord> businessHandleCount(Map<String, Object> paramObj);
	/**
	 * 用户存款排名报表查询
	 * @param paramObj
	 * @return
	 */
	List<TakeDepositRecord> employeeDepositRankingReportCount(Map<String, Object> paramObj);
	
	/**
	 * 用户取款排名报表查询
	 * @param paramObj
	 * @return
	 */
	List<TakeDepositRecord> queryEmployeeWithdrawalsRankingReportCount(Map<String, Object> paramObj);
	
	/**
	 * 取款次数排名
	 * @param paramObj
	 * @return
	 */
	List<TakeDepositRecord> queryEmployeeWithdraNumberRanking(Map<String, Object> paramObj);
	
	/**
	 *用户存取款统计 
	 * @param paramObj
	 * @return
	 */
	List<TakeDepositRecord> userDepositTakeCount(Map<String, Object> paramObj);
	
	/**
	 * 用户提存比分析统计
	 */
	List<TakeDepositRecord> userDepositTakeRate(Map<String, Object> paramObj);
	
	/**
	 * 用户输赢概率统计分析
	 */
	List<TakeDepositRecord> userGameWinLoseRate(Map<String, Object> paramObj);
	
	/**
	 * 统计用户当天或历史累计存款总额
	 * @param employeecode
	 * @param flag 0=昨天 1=当天 2=历史累计
	 * @return
	 * @throws Exception
	 */
	double userDepositTakeMoney(Map<String, Object> paramObj) ;
	
	/**
	 * 批量修改订单审核人
	 * @param object
	 * @return
	 */
	int updateHandEmployee(Map<String, Object> object);
	
	/**
	 * 根据订单号数组查询多条记录
	 * @param orders
	 * @return
	 */
	List<TakeDepositRecord> findMutilOrdersByOrdernumber(String[] orders);
	
	/**
	 * 根据用户编码、类型、状态获取最后一条记录
	 * @param loginaccount
	 * @return
	 */
	TakeDepositRecord getLastByParames(Map<String, Object> parame);
	
	/**
	 * 一段时间内金额交易总数
	 * @param loginaccount
	 * @return
	 */
	TakeDepositRecord selectSumAmount(Map<String, Object> parame);
	
	/**
	 * 存储过程,总计-取款稽查
	 */
	List<TakeDepositRecord> userTakemoneyInspectNew(Map<String, Object> paramObj) throws Exception ;
	
	/**
	 * 存储过程,总计-取款稽查
	 */
	Map<String, Object> userAllMoney(Map<String, Object> paramObj) throws Exception ;
	
	
	/**
	 * 查询会员的存取款相关汇总信息
	 */
	Map<String, Object> userAllInfoMoney(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 查询会员的游戏和优惠和洗码等所有金额相关的数据
	 */
	List<TakeDepositRecord> userAllInfoGame(Map<String, Object> paramObj) throws Exception;
}
