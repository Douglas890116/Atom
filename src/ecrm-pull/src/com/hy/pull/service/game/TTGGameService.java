package com.hy.pull.service.game;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.TTGGame;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.mapper.TtgGameinfoMapper;
import com.hy.pull.service.BaseService;
import com.hy.pull.service.BaseService.Enum_flag;

/**
 * TTG游戏服务类
 * 创建日期 2016-12-13
 * @author temdy
 */
@Service
public class TTGGameService extends BaseService {
	
	Logger logger = LogManager.getLogger(TAGGameService.class.getName());
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private TtgGameinfoMapper ttgGameinfoMapper;
	
	@Autowired
	private DataHandleMapper dataHandleMapper;
	@Autowired
	private DataHandleLogsMapper dataHandleLogsMapper;
	
	/**
	 * 按条件拉取数据的方法
	 * @param entity 条件集合 {GAME_ID:游戏编号,PROXY_ID:代理编号,BEGIN_DATE:开始日期,END_DATE:结束日期,transactionType:交易类型,transactionSubType:子交易类型}
	 * @return 数据行数
	 */
	@Override
	public Integer pullData(Map<String, Object> entity) throws Exception {
		
		/**********************获取上次拉取的最大值***********************/
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_TTG);
		dataHandle.put("lastnum", "0");
		String updatetime = dataHandle.get("updatetime").toString();
		
		
		
		int count = 0;//累计执行总数量
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为TAG游戏
			entity.put("GAME_ID", "12");//设置所属游戏编号
		}//获取TAG游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();
		if(size > 0){
			String apiUrl = null;//接口URL
			String agent = null;//代理名称
			String requestId = null;//客户端唯一请求ID[client unique request Id]
			String partnerId = null;//合作ID
			String startDate = null; //开始日期[YYYYMMDD]
			String startDateHour = null; //开始小时
			String startDateMinute = null; //开始分钟
			String endDate = null; //结束日期[YYYYMMDD]
			String endtDateHour = null; //结束小时
			String endDateMinute = null; //结束分钟
			String currency = null; //货币类型
			String includeSubPartner = null; //是否包含子合作ID
			String transactionType = null; //交易类型
			String transactionSubType = null; //子交易类型
			String code = null;//代理编码
			if(entity.get("BEGIN_DATE") != null){
				String bd = entity.get("BEGIN_DATE").toString();
				String bds[] = bd.split(" ");
				startDate = bds[0].replace("-", "");
				String bdsq[] = bds[1].split(":");
				startDateHour = bdsq[0];
				startDateMinute = bdsq[1];
			}else{
				String bd = GameHttpUtil.getDate();
				String bds[] = bd.split(" ");
				startDate = bds[0].replace("-", "");
				String bdsq[] = bds[1].split(":");
				startDateHour = bdsq[0];
				startDateMinute = bdsq[1];
			}
			if(entity.get("END_DATE") != null){
				String bd = entity.get("END_DATE").toString();
				String bds[] = bd.split(" ");
				endDate = bds[0].replace("-", "");
				String bdsq[] = bds[1].split(":");
				endtDateHour = bdsq[0];
				endDateMinute = bdsq[1];
			}else{
				SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				String bd = t.format(new Date());
				String bds[] = bd.split(" ");
				endDate = bds[0].replace("-", "");
				String bdsq[] = bds[1].split(":");
				endtDateHour = bdsq[0];
				endDateMinute = bdsq[1];
			}
			if(entity.get("transactionType") != null){
				transactionType = entity.get("transactionType").toString();
			}
			if(entity.get("includeSubPartner") != null){
				includeSubPartner = entity.get("includeSubPartner").toString();
			}else{
				includeSubPartner = "Y";
			}
			if(entity.get("currency") != null){
				currency = entity.get("currency").toString();
			}else{
				currency = "CNY";
			}
			
			//间隔分钟数（不得超过240）
			int intervalnum = 240;
			intervalnum = Integer.valueOf(dataHandle.get("intervalnum").toString());
			
			
			/***********************设定开始结束时间，每次只拉取25分钟内的数据*****************************/
			String stardate = DateUtil.parse(DateUtil.add(DateUtil.parse(updatetime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, this.backMinute), "yyyy-MM-dd HH:mm:ss");//
			String enddate = DateUtil.parse(DateUtil.add(DateUtil.parse(stardate, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, intervalnum), "yyyy-MM-dd HH:mm:ss");//
			if(DateUtil.parse(enddate, "yyyy-MM-dd HH:mm:ss").getTime() > new Date().getTime()) {
				//最后时间不能超过当前时间
				enddate = DateUtil.parse(new Date(), "yyyy-MM-dd HH:mm:ss");
			}
			String bds[] = stardate.split(" ");
			startDate = bds[0].replace("-", "");
			String bdsq[] = bds[1].split(":");
			startDateHour = bdsq[0];
			startDateMinute = bdsq[1];
			
			bds = enddate.split(" ");
			endDate = bds[0].replace("-", "");
			bdsq = bds[1].split(":");
			endtDateHour = bdsq[0];
			endDateMinute = bdsq[1];
			
			
			
			int len = 0;
			int klen = 0;
			int mlen = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("startDate", startDate);
			map.put("startDateHour", startDateHour);
			map.put("startDateMinute", startDateMinute);
			map.put("endDate", endDate);
			map.put("endDateHour", endtDateHour);
			map.put("endDateMinute", endDateMinute);
			map.put("currency", currency);
			map.put("includeSubPartner", includeSubPartner);
			List<Map<String,Object>> data = null;
			//String[] ts = null;
			String[] sts = null;
			
			boolean flag = true;
			
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				apiUrl = entity.get("PROXY_API_URL").toString();
				agent = entity.get("PROXY_NAME").toString();
				requestId = entity.get("PROXY_MD5_KEY").toString();
				partnerId = entity.get("PROXY_NAME").toString();
				code = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();
				map.put("requestId", requestId);
				map.put("partnerId", partnerId);
				map.put("includeSubPartner", includeSubPartner);
				transactionType = "Game";
				map.put("transactionType", transactionType);
				sts = getSubType(transactionType);
				len = sts.length;
				if(len > 0){
					for(int j=0;j<len;j++){
						transactionSubType = sts[j];
						map.put("transactionSubType", transactionSubType);
						data = TTGGame.getData(apiUrl,agent,map,code);
						if(data != null){
							klen = data.size();
							count += klen;
							if(klen > 0){//如果有数据就入库
								ttgGameinfoMapper.batchInsert(data);//批量入库
								
								dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + len);
								dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + len);
							}
							
							//dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_TTG, null, len+"个", agent, Enum_flag.正常.value));
							
							
						} else {
							//为null表示出现异常						
							flag = false;
							
							dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_TTG, null, "返回NULL数据", agent, Enum_flag.异常.value));
							
							
							break;
						}
					}
				}
			}
			
			
			/***************更新配置管理库****************/
			if(flag) {
				dataHandle.put("updatetime", enddate);
				dataHandle.put("lasttime", DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
				dataHandleMapper.update(dataHandle);
			}
			/***************更新配置管理库****************/
		}
		return count;
	}

	
	/**
	 * 获取子交易类型列表
	 * @param transactionType 交易类型
	 * @return 子交易类型列表
	 */
	public String[] getTransactionType(){
		return new String[]{"Game","MoneyTransfer","ManualAdjustment","Bonus"};
	}
	
	/**
	 * 获取子交易类型列表
	 * @param transactionType 交易类型
	 * @return 子交易类型列表
	 */
	public String[] getSubType(String transactionType){
		String[] subs = null;
		if(transactionType.equals("Game")){
			subs = new String[]{"Wager","Resolve"};
		}else if(transactionType.equals("MoneyTransfer")){
			subs = new String[]{"ChipTransfer","Visa","MasterCard","WithdrawalRequest","WithdrawalRejection","DepositRejection","BrokerTransfer"};
		}else if(transactionType.equals("ManualAdjustment")){
			subs = new String[]{"GameAdjustment","Cash","Cheque","Wire"};
		}else if(transactionType.equals("Bonus")){
			subs = new String[]{"Bonus","BonusAdjustment","BonusReversed"};
		}
		return subs;
	}
}