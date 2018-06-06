package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.EmployeeGamecataloyBonusDao;
import com.maven.dao.GameCategoryDao;
import com.maven.dao.GameDao;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.GameCategory;
import com.maven.exception.ArgumentValidationException;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.AttrCheckout;

@Service
public class EmployeeGamecataloyBonusServiceImpl extends BaseServiceImpl<EmployeeGamecataloyBonus> 
	implements EmployeeGamecataloyBonusService{

	@Autowired
	private EmployeeGamecataloyBonusDao employeeGamecataloyBonusDao;
	
	@Autowired
	private GameCategoryDao gameCategoryDao;
	@Autowired
	private GameDao gameDao;
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	@Override
	public BaseDao<EmployeeGamecataloyBonus> baseDao() {
		return employeeGamecataloyBonusDao;
	}

	@Override
	public Class<EmployeeGamecataloyBonus> getClazz() {
		return EmployeeGamecataloyBonus.class;
	}

	/**
	 * 获取用户的接入平台游戏返点
	 */
	@Override
	public List<EmployeeGamecataloyBonus> takeEmployeeGameCategoryBonus(String employeecode) throws Exception{
		return super.select(new EmployeeGamecataloyBonus(employeecode,null));
	}
	/**
	 * 获取用户的接入平台游戏返点
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, BigDecimal> takeEmployeeGameCategoryBonusMap(String employeecode)
			throws Exception {
		List<EmployeeGamecataloyBonus> list =  super.select(new EmployeeGamecataloyBonus(employeecode,null));
		Map<String,BigDecimal> s = new HashMap<String, BigDecimal>();
		for (EmployeeGamecataloyBonus e : list) {
			s.put(e.getGametype()+"_"+e.getCategorytype(), e.getBonus());
		}
		return s;
	}


	@Override
	public int tc_installEmployeeCategyBonus(List<EmployeeGamecataloyBonus> bonus) throws Exception {
		if(bonus==null || bonus.size()==0)
			throw new ArgumentValidationException("参数错误,用户洗码不能为空");
		AttrCheckout.checkout(bonus, false, new String[]{"employeecode","parentemployeecode","gametype","categorytype","bonus"});
		return super.saveRecordBatch(bonus);
	}
	
	@Override
	public int updateMultiMemberBonus(List<EmployeeGamecataloyBonus> userbonus) throws Exception {
		Map<String,Object> __updateParamPackge = new HashMap<String, Object>();
		//修改返点
		if(userbonus.size()>0){
			AttrCheckout.checkout(userbonus, true, new String[]{"employeecode","gametype","categorytype","bonus"});
			__updateParamPackge.put("updateObject", userbonus);
		}
		
		if(__updateParamPackge.size()>0){
			return employeeGamecataloyBonusDao.updateMultiMemberBonus(__updateParamPackge);
		}
		return 1;
	}
	
	@Override
	public int tc_settingMultiMemberBonus(Map<String, List<EmployeeGamecataloyBonus>> userbonus) throws Exception {
		List<EmployeeGamecataloyBonus> __insert = userbonus.get("__insert");
		List<EmployeeGamecataloyBonus> __updateUp = userbonus.get("__upateUp");
		List<EmployeeGamecataloyBonus> __updateDown = userbonus.get("__upateDown");
		__updateUp.addAll(__updateDown);
		//新增返点
		if(__insert.size()>0){
			this.saveRecordBatch(__insert);
		}
		Map<String,Object> __updateParamPackge = new HashMap<String, Object>();
		//修改返点
		if(__updateUp.size()>0){
			AttrCheckout.checkout(__updateUp, true, new String[]{"employeecode","gametype","categorytype","bonus"});
			__updateParamPackge.put("updateObject", __updateUp);
		}
		if(__updateParamPackge.size()>0){
			employeeGamecataloyBonusDao.updateMultiMemberBonus(__updateParamPackge);
		}
		return 1;
	}
	

	/**
	 * 用户结算配置设置
	 */
	@Override
	public void tc_settingSettleConfig(EnterpriseEmployee ee, Map<String,List<EmployeeGamecataloyBonus>> userbonus) throws Exception {
		List<EmployeeGamecataloyBonus> __insert = userbonus.get("__insert");
		List<EmployeeGamecataloyBonus> __updateUp = userbonus.get("__upateUp");
		List<EmployeeGamecataloyBonus> __updateDown = userbonus.get("__upateDown");
		//新增返点
		if(__insert.size()>0){
			this.saveRecordBatch(__insert);
		}
		Map<String,Object> __updateParamPackge = new HashMap<String, Object>();
		String __teamAgent = "";
		//修改返点
		if(__updateDown.size()>0){
			AttrCheckout.checkout(__updateDown, true, new String[]{"employeecode","gametype","categorytype","bonus"});
			__teamAgent = enterpriseEmployeeService.call_ufnTakeTeamAgent(ee.getEmployeecode());
			__updateParamPackge.put("nextLevel", __teamAgent.split(","));
			__updateParamPackge.put("updateDown", __updateDown);
		}
		if(__updateUp.size()>0){
			AttrCheckout.checkout(__updateUp, true, new String[]{"employeecode","gametype","categorytype","bonus"});
			__updateParamPackge.put("updateFloor", __updateUp);
		}
		if(__updateParamPackge.size()>0){
			employeeGamecataloyBonusDao.updateBounus(__updateParamPackge);
		}
		
		//分红与占成
		if(ee.getDividend()!=null&&ee.getShare()!=null){
			enterpriseEmployeeService.tc_setDividendShare(ee);
		}
	}

	/**
	 * 设置企业号返点
	 */
	@Override
	public int tc_settingEnterpriseBonus(EmployeeGamecataloyBonus object) throws Exception {
		return employeeGamecataloyBonusDao.settingEnterpriseBonus(object);
	}
	
	/**
	 * 查询用户所有游戏的洗码
	 * @param employeecode
	 * @return
	 */
	@Override
	public List<EmployeeGamecataloyBonus> findGameBonus(String employeecode) throws Exception {
		return employeeGamecataloyBonusDao.findGameBonus(employeecode);
	}

	/**
	 * 查找并更新用户的洗码设置
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateEmployeeGameCategoryBonus(String Enterprisecode, List<EnterpriseGame> games) throws Exception {
		Map<String, Object> object = new HashMap<String, Object>();
		object.put("enterprisecode", Enterprisecode);
		List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(object);
		
		List<EmployeeGamecataloyBonus> adddata = new ArrayList<EmployeeGamecataloyBonus>();
		
		List<GameCategory> listGameCategory = gameCategoryDao.selectAll("GameCategory.selectAll", new HashMap<String, Object>());
		Map<String, List<GameCategory>> mapGameCategory = new HashMap<String, List<GameCategory>>();
		for (GameCategory gameCategory : listGameCategory) {
			
			List<GameCategory> temp = mapGameCategory.get(gameCategory.getGametype());
			if(mapGameCategory.containsKey(gameCategory.getGametype())) {
				temp.add(gameCategory);
				mapGameCategory.put(gameCategory.getGametype(), temp);
			} else {
				temp = new ArrayList<GameCategory>();
				temp.add(gameCategory);
				mapGameCategory.put(gameCategory.getGametype(), temp);
			}
		}
		
		//传递进来的games只有企业编码和游戏编码，没有游戏类型
		for (EnterpriseGame game : games) {
			game.setGametype(gameDao.selectByPrimaryKey("Game.selectByPrimaryKey", game.getGamecode()).getGametype());
		}
		
		System.out.println("updateEmployeeGameCategoryBonus=需要处理人数："+list.size());
		System.out.println("updateEmployeeGameCategoryBonus=需要游戏数："+games.size());
		Map<String, Object> object2 = new HashMap<String, Object>();
		int index = 0;
		for (EnterpriseEmployee ee : list) {
			
			System.out.println("当前处理人数："+index);
			for (EnterpriseGame game : games) {
				object2 = new HashMap<String, Object>();
				object2.put("employeecode", ee.getEmployeecode());
				object2.put("gametype", game.getGametype());
				List<EmployeeGamecataloyBonus> data = employeeGamecataloyBonusDao.selectAll("EmployeeGamecataloyBonus.selectAll", object2);
				
				//没有，需要初始化为0
				if(data == null || data.size() == 0) {
					List<GameCategory> temp = mapGameCategory.get(game.getGametype());
					if(temp == null || temp.size() == 0) {
						continue;
					}
					for (GameCategory gameCategory : temp) {
						//adddata.add();
						super.save(new EmployeeGamecataloyBonus(ee.getEmployeecode(), ee.getParentemployeecode(), gameCategory.getGametype(), gameCategory.getCategorytype(), new BigDecimal("0")));
					}
				}
			}
			index ++;
		}
		System.out.println("updateEmployeeGameCategoryBonus=处理完毕");
		/*
		 * 批量插入数据过大，总是失败
		//插入
		if(adddata.size() > 0) {
			super.saveRecordBatch(adddata);
		}
		*/
		
	}
	
	
	/**
	 * 根据会员编码list获取所有游戏的洗码
	 * @param employeecodes
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Map<String, BigDecimal>> findGameBonus(List<String> employeecodes) throws Exception {
		List<EmployeeGamecataloyBonus> egbList = this.employeeGamecataloyBonusDao.findGameBonus(employeecodes);
		Map<String, Map<String, BigDecimal>> egbMap = new HashMap<String, Map<String, BigDecimal>>();
		for (EmployeeGamecataloyBonus egb : egbList) {
			if (egbMap.containsKey(egb.getEmployeecode())) {
				Map<String, BigDecimal> mapinmap = egbMap.get(egb.getEmployeecode());
				mapinmap.put(egb.getGametype()+egb.getCategorytype(), egb.getBonus());
				egbMap.put(egb.getEmployeecode(), mapinmap);
			} else {
				Map<String, BigDecimal> mapinmap = new HashMap<String, BigDecimal>();
				mapinmap.put(egb.getGametype()+egb.getCategorytype(), egb.getBonus());
				egbMap.put(egb.getEmployeecode(), mapinmap);
			}
		}
		return egbMap;
	}

	/**
	 * 根据会员编码list获取所有游戏的洗码，带分隔符
	 * @param employeecodes
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Map<String, BigDecimal>> findGameBonus2(List<String> employeecodes) throws Exception {
		List<EmployeeGamecataloyBonus> egbList = this.employeeGamecataloyBonusDao.findGameBonus(employeecodes);
		Map<String, Map<String, BigDecimal>> egbMap = new HashMap<String, Map<String, BigDecimal>>();
		for (EmployeeGamecataloyBonus egb : egbList) {
			if (egbMap.containsKey(egb.getEmployeecode())) {
				Map<String, BigDecimal> mapinmap = egbMap.get(egb.getEmployeecode());
				mapinmap.put(egb.getGametype()+"_"+egb.getCategorytype(), egb.getBonus());
				egbMap.put(egb.getEmployeecode(), mapinmap);
			} else {
				Map<String, BigDecimal> mapinmap = new HashMap<String, BigDecimal>();
				mapinmap.put(egb.getGametype()+"_"+egb.getCategorytype(), egb.getBonus());
				egbMap.put(egb.getEmployeecode(), mapinmap);
			}
		}
		return egbMap;
	}
}
