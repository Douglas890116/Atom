package com.maven.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityRaffleRecordDao;
import com.maven.entity.ActivityRaffleRecord;
import com.maven.entity.ActivityRaffleRecord.ApiRaffleTimePrize;
import com.maven.service.ActivityRaffleRecordService;
import com.maven.util.AttrCheckout;
import com.maven.util.DateUtils;

@Service
public class ActivityRaffleRecordServiceImpl extends BaseServiceImpl<ActivityRaffleRecord> implements ActivityRaffleRecordService {

	@Autowired
	private ActivityRaffleRecordDao activityRaffleRecordDao;
	
	
	@Override
	public int addRaffleRecord(ActivityRaffleRecord __rafflerecord) throws Exception {
		return super.add(AttrCheckout.checkout(__rafflerecord, false, new String[]{"employeecode","parentemployeecode","loginaccount","reffletime","reffleprizes"}));
	}

	@Override
	public BaseDao<ActivityRaffleRecord> baseDao() {
		return activityRaffleRecordDao;
	}

	@Override
	public Class<ActivityRaffleRecord> getClazz() {
		return ActivityRaffleRecord.class;
	}

	@Override
	public List<ActivityRaffleRecord> selectRaffleRecord(Map<String, Object> parameter) throws Exception {
		return activityRaffleRecordDao.selectRaffleRecord(parameter);
	}

	@Override
	public Map<String, Object> getRaffleRecordJson(String employeecode, String startraffletime, String endraffletime)
			throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("employeecode", employeecode);
		parameter.put("startraffletime", startraffletime);
		parameter.put("endraffletime", endraffletime);
		List<ActivityRaffleRecord> rafflerecord_list = this.selectRaffleRecord(parameter); //已抽奖列表
		Map<String, Object> json_map = new HashMap<String, Object>();
		List<ApiRaffleTimePrize> json_list = new ArrayList<ApiRaffleTimePrize>(); //抽奖对象列表
		for (ActivityRaffleRecord arr : rafflerecord_list) {
			if (!json_map.containsKey("employeecode")){
				json_map.put("employeecode", arr.getEmployeecode());
			}
			ApiRaffleTimePrize timeprizes = new ActivityRaffleRecord().new ApiRaffleTimePrize(); //具体抽奖对象
			timeprizes.setRaffletime(DateUtils.FormatTimeToStandarStringTime(arr.getReffletime()));
			timeprizes.setRaffleprizes(arr.getReffleprizes());
			json_list.add(timeprizes);
		}
		json_map.put("record", json_list);
		return json_map;
	}
	
}
