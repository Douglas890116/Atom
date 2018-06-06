package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.DepositWithdralOrderDelegate;
import com.maven.entity.TakeDepositRecord;
@Service
public interface TakeDepositRecoredService extends BaseServcie<TakeDepositRecord>{
	
	/**
	 * 订单信息修改
	 * @param depositRecord
	 * @throws Exception
	 */
	@DataSource("master")
	public void updateTakeDepositRecord(TakeDepositRecord depositRecord) throws Exception;
	
	/**
	 * 充值返利活动处理流程
	 * @param depositeRecord
	 * @param handles
	 * @param flowtype
	 * @throws Exception
	 */
	@DataSource("master")
	public void saveingVerify(TakeDepositRecord depositeRecord, DepositWithdralOrderDelegate handles) throws Exception ;
	
	/**
	 * 保存存款订单
	 * @param takeDepositRecord
	 */
	@DataSource("master")
	void tc_save_money(TakeDepositRecord takeDepositRecord) throws Exception;
	
	/**
	 * 修改存款订单
	 * @param takeDepositRecord
	 */
	@DataSource("master")
	void tc_update_saveorder(TakeDepositRecord takeDepositRecord) throws Exception;
	
	/**
	 * 保存取款订单
	 * @param takeDepositRecord
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_take_money(TakeDepositRecord takeDepositRecord) throws Exception;
	
	/**
	 * 修改取款订单
	 * @param takeDepositRecord
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_update_takeorder(TakeDepositRecord takeDepositRecord) throws Exception;
	/**
	 * 查询分页数据
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	@DataSource("slave")
	List<TakeDepositRecord> findTakeDepositRecord(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 统计数据
	 * @param paramObj
	 * @return int
	 */
	@DataSource("slave")
	Map<String, Object> findcountTakeDepositRecord(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 根据单条订单号删除记录
	 * @param session
	 * @param request
	 * @return Map<String,String> 
	 */
	@DataSource("master")
	void tc_deleteTakeRepositRecord(String code) throws Exception;
	/**
	 * 删除选中的一条或者多条数据
	 * @param request
	 */
	@DataSource("master")
	void tc_deleteSelectAllTakeRepositRecord(String[] array) throws Exception;
	/**
	 * 存取款记录修改
	 * @param request
	 * @return URL
	 */
	@DataSource("master")
	void tc_updateTakeDepositRecord(TakeDepositRecord takeDepositRecord) throws Exception;
	
	/**
	 * 修改订单工作流相关信息
	 * @param record
	 * @throws Exception
	 */
	@DataSource("master")
	int updateFlow(TakeDepositRecord record)throws Exception;
	/**
	 * 根据订单号查询
	 * @param request
	 * @return TakeDepositRecord
	 */
	@DataSource("slave")
	TakeDepositRecord findOrderNumberTakeDepositRecord(String orderNumber);
	/**
	 * 审批通过根据订单号修改订单状态以及保存审批提交的图片名称
	 * @param takeDepositRecord
	 */
	@DataSource("master")
	int tc_updateTakeDepositRecoredStatus(TakeDepositRecord takeDepositRecord) throws Exception;
	/**
	 * 根据员工编码删除所有的存取款记录
	 * @param employeecode
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_deleteEmployeeRecord(String[] employeecodeArray)throws Exception;
	/**
	 * 存取款余额统计查询
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	@DataSource("slave")
	List<TakeDepositRecord> findCountDepositTakeRecordDatail(Map<String, Object> paramObj) throws Exception;
	/**
	 * 存取款余额记录数量统计
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	@DataSource("slave")
	int findCountDepositTakeRecordDatailCount(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 统计用户当天或历史累计存款总额
	 * @param employeecode
	 * @param flag 0=昨天 1=当天 2=历史累计
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	double call_userDepositTakeMoney(String employeecode, int flag) throws Exception;
	
	/**
	 * 根据员工编码统计该员工当天取款次数与取款总金额 
	 * @param employeecode
	 * @return
	 */
	@DataSource("slave")
	TakeDepositRecord takeCountAndTakeTotalAmount(String employeecode) throws Exception;
	
	/**
	 * 订单处理时间统计报表查询
	 * @param paramObj
	 */
	List<TakeDepositRecord> call_businessHandleCount(Map<String, Object> paramObj)throws Exception;
	/**
	 * 用户存款排名报表查询
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	List<TakeDepositRecord> call_employeeDepositRankingReportCount(Map<String, Object> paramObj)throws Exception;
	/**
	 * 用户取款排名报表查询
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	List<TakeDepositRecord> call_employeeWithdrawalsRankingReportCount(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 取款次数排名
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	List<TakeDepositRecord> call_employeeWithdraNumberRanking(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 用户存取款统计报表
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	List<TakeDepositRecord> call_userDepositTakeCount(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 用户提存比分析统计
	 */
	@DataSource("slave")
	List<TakeDepositRecord> call_userDepositTakeRate(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 用户输赢概率统计分析
	 */
	@DataSource("slave")
	List<TakeDepositRecord> call_userGameWinLoseRate(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 撤回订单审核者
	 * @param takeDepositRecord
	 */
	@DataSource("master")
	int tc_cancelAudit(TakeDepositRecord takeDepositRecord) throws Exception;
	
	/**
	 * 批量修改订单审核人
	 * @param object
	 * @return
	 */
	@DataSource("master")
	int updateHandEmployee(Map<String, Object> object) throws Exception;
	
	/**
	 * 根据订单号数组查询多条记录
	 * @param orders
	 * @return
	 */
	@DataSource("master")
	List<TakeDepositRecord> findMutilOrdersByOrdernumber(String[] orders);
	
	/**
	 * 存储过程,总计-取款稽查
	 */
	@DataSource("slave")
	List<TakeDepositRecord> call_userTakemoneyInspectNew(Map<String, Object> paramObj) throws Exception;
	/**
	 * 存储过程,总计-取款稽查
	 */
	@DataSource("slave")
	Map<String, Object> call_userAllMoney(Map<String, Object> paramObj) throws Exception;
	
	
	/**
	 * 查询会员的存取款相关汇总信息
	 */
	@DataSource("slave")
	Map<String, Object> call_userAllInfoMoney(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 查询会员的游戏和优惠和洗码等所有金额相关的数据
	 */
	@DataSource("slave")
	List<TakeDepositRecord> call_userAllInfoGame(Map<String, Object> paramObj) throws Exception;
	
}
