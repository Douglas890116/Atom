package com.maven.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityRaffleSigninDao;
import com.maven.entity.ActivityRaffleSignin;
import com.maven.service.ActivityRaffleSigninService;
import com.maven.util.AttrCheckout;
import com.maven.util.DateUtils;

@Service
public class ActivityRaffleSigninServiceImpl 
	extends BaseServiceImpl<ActivityRaffleSignin> implements ActivityRaffleSigninService{

	@Autowired
	private ActivityRaffleSigninDao activityRaffleSigninDao;
	
	@Override
	public BaseDao<ActivityRaffleSignin> baseDao() {
		return activityRaffleSigninDao;
	}

	@Override
	public Class<ActivityRaffleSignin> getClazz() {
		return ActivityRaffleSignin.class;
	}

	@Override
	public int tc_raffleSignIn(Map<String, Object> __object)throws Exception {
		return Integer.parseInt(activityRaffleSigninDao.usp_raffle_signin(
				AttrCheckout.checkout(__object, false, new String[]{"employeecode","enterprisebrandactivitycode"})));
	}

	@Override
	public List<ActivityRaffleSignin> selectSigninRecord(Map<String, Object> parameter) throws Exception {
		return activityRaffleSigninDao.selectSigninRecord(parameter);
	}

	@Override
	public Map<String, Object> getSigninRecordJson(String employeecode, String startsignintime, String endsignintime) throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("employeecode", employeecode);
		parameter.put("startsignintime", startsignintime);
		parameter.put("endsignintime", endsignintime);
		
		List<ActivityRaffleSignin> signinrecords = this.selectSigninRecord(parameter); //获取会员时间内所有签到记录
		Map<String, Object> json_map = new HashMap<String, Object>();//组装返回值专用map
		//List<String> signintime_list = new ArrayList<String>();//签到天数list
		StringBuffer signintime_str = new StringBuffer(); 
		for (ActivityRaffleSignin ars : signinrecords) {
			if (!json_map.containsKey("employeecode")){
				json_map.put("employeecode", ars.getEmployeecode());
			}
			signintime_str.append(Integer.parseInt(DateUtils.FormatTimeToddString(ars.getSignintime()))+",");
		}
		if (StringUtils.isEmpty(signintime_str.toString())) {
			json_map.put("record", signintime_str.toString());
		} else {
			json_map.put("record", signintime_str.substring(0, signintime_str.length()-1).toString());
		}
		
		return json_map;
	}

}
