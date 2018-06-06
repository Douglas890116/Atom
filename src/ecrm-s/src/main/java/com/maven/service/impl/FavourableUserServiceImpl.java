package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.dao.FavourableUserDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.FavourableUser;
import com.maven.entity.TakeDepositRecord;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.FavourableUserService;

@Service
public class FavourableUserServiceImpl extends BaseServiceImpl<FavourableUser> implements FavourableUserService{

	@Autowired
	private FavourableUserDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<FavourableUser> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<FavourableUser> getClazz() {
		return FavourableUser.class;
	}

	@Override
	public List<FavourableUser> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecord(parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCount(parameter);
	}
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	public void addBatchData(String enterprisecode, String favourableid, String[] users) throws Exception {
		activityBetRecordDao.delete("FavourableUser.deleteByFavourableid", favourableid);//先全部删除改组数据
		
		List<FavourableUser> list = new ArrayList<FavourableUser>();
		for (String employeecode : users) {
			FavourableUser user = new FavourableUser();
			user.setEmployeecode(employeecode);
			user.setEnterprisecode(enterprisecode);
			user.setFavourableid(favourableid);
			user.setLsh(UUID.randomUUID().toString());
			list.add(user);
		}
		activityBetRecordDao.saveRecordBatch("FavourableUser.saveRecordBatch", list);
	}
	
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(FavourableUser activityBetRecord) throws Exception {
		activityBetRecordDao.addBetRecord(activityBetRecord);
	}

	@Override
	public void deleteActivityBetRecord(String lsh) throws Exception {
		activityBetRecordDao.deleteBetRecord(lsh);
	}
}
