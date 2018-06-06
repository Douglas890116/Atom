package com.maven.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.dao.PermissionMenuDao;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.PermissionMenu;
import com.maven.entity.PermissionMenu.Enum_language;
import com.maven.service.PermissionMenuService;
import com.maven.util.AttrCheckout;
import com.maven.util.JSONUnit;

@Service
public class PermissionMenuServiceImpl extends BaseServiceImpl<PermissionMenu> implements PermissionMenuService{

	@Autowired
	private PermissionMenuDao permissionMenuDao;
	
	@Override
	public BaseDao<PermissionMenu> baseDao() {
		return permissionMenuDao;
	}

	@Override
	public Class<PermissionMenu> getClazz() {
		return PermissionMenu.class;
	}
	
	@Override
	public int addMenu(Map<String,Object> object) throws Exception {
		permissionMenuDao.updateSort(object);
		super.add(object);
		return 1;
	}

	@Override
	public void deleteMenu(Map<String, Object> object) throws Exception{
		super.logicDelete(object);
	}
	
	@Override
	public Map<String, PermissionMenu> takeMenus() throws Exception {
		PermissionMenu _object = new PermissionMenu();
		_object.setMenustatus(PermissionMenu.Enum_menustatus.启用.value);
		_object.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
		List<PermissionMenu> _ss = super.select(_object);
		Map<String,PermissionMenu> employeeMenu = new HashMap<String, PermissionMenu>();
		for (PermissionMenu _pm : _ss) {
			employeeMenu.put(_pm.getMenucode(), _pm);
		}
		return employeeMenu;
	}
	
	@Override
	public Map<String,PermissionMenu> takeUserMenus(Map<String,PermissionMenu> allMenus,Map<String,EmployeeMappingMenu> uMenusMapping) throws Exception {
		Map<String,PermissionMenu> employeeMenu = new HashMap<String, PermissionMenu>();
		for (EmployeeMappingMenu pm : uMenusMapping.values()) {
			PermissionMenu m = allMenus.get(pm.getMenucode());
			if(m !=null){
				employeeMenu.put(pm.getMenucode(), m.clone());
			}
		}
		return employeeMenu;
	}
	
	@Override
	public String[] takeFormatEmployeeMenu(String basePath,Collection<PermissionMenu> menus, String language) throws Exception {
		List<PermissionMenu> list =new ArrayList<PermissionMenu>(menus);
		Collections.sort(list); 
		return formateIndexMenu(basePath, new ArrayList<PermissionMenu>(list), language);
	}
	
	@Override
	public String  takeFormatMenu(List<PermissionMenu> menus){
		return createMenu(menus);
	}
	
	private String[] formateIndexMenu(String basePath,List<PermissionMenu> list, String language) {
		String[] menuLevel = new String[2];
		StringBuffer fistLevelMenu = new StringBuffer();
		List<PermissionMenu> menus = new ArrayList<PermissionMenu>();
		fistLevelMenu.append("[");
		for (PermissionMenu pmm : list) {
			if(pmm.getMenulevel()==1){
				if (Enum_language.英文.value.equals(language)) {
					fistLevelMenu.append("{'menuname':'"+pmm.getEnglishname()+"'},");
				} else {
					fistLevelMenu.append("{'menuname':'"+pmm.getMenuname()+"'},");
				}
			}
			if(pmm.getMenulevel() <=3 ){
				menus.add(pmm);
			}
		}
		fistLevelMenu.append("]");
	    StringBuffer buf = new StringBuffer();
	    buf.append("[");
	    for (PermissionMenu main : menus) {
	    	if(main.getMenulevel()==1){
		        buf.append("{").append("'id': '" + main.getMenucode() + "',");
		        buf.append("'menu': [");
		        PermissionMenu homeMenu = null;
		        for (PermissionMenu firstMenu : menus) {
		          if(main.getMenucode().equals(firstMenu.getParentmenucode())){
						if (Enum_language.英文.value.equals(language)) {
							buf.append("{").append("'text': '" + firstMenu.getEnglishname() + "','items':[");
						} else {
							buf.append("{").append("'text': '" + firstMenu.getMenuname() + "','items':[");
						}
		            for (PermissionMenu secondMenu : menus) {
		              if(firstMenu.getMenucode().equals(secondMenu.getParentmenucode())){
		            	String url = secondMenu.getMenuurl();
		            	if(StringUtils.isNotBlank(url)){
		            		if(!url.toLowerCase().startsWith("http://")
		            				&&!url.toLowerCase().startsWith("https://")){
		            			url = basePath + url;
		            		}else if(url.toLowerCase().startsWith("http://o-api")){
		            			url = url.replace("http://o-api", SystemConstant.getProperties("api.server"));
		            		}else if(url.toLowerCase().startsWith("http://o-data")){
		            			url = url.replace("http://o-data", SystemConstant.getProperties("data.server"));
		            		}
		            	}
		            	if(homeMenu==null){
		            		homeMenu = secondMenu;
		            	}
		                buf.append("{");
		                buf.append("'id': '" + secondMenu.getMenucode() + "',");
						if (Enum_language.英文.value.equals(language)) {
							buf.append("'text': '" + secondMenu.getEnglishname() + "',");
						} else {
							buf.append("'text': '" + secondMenu.getMenuname() + "',");
						}
		                buf.append("'href': '" + url);
		                buf.append("'},");
		              }
		            }
		            buf.append("]},");
		          }
		        }
		        buf.append("],'homePage':'"+homeMenu.getMenucode()+"'},");
	    	}
	    }
	    buf.append("]");
	    menuLevel[0]=fistLevelMenu.toString();
	    menuLevel[1]=buf.toString();
	    return menuLevel;
	}
	
	private String createMenu(List<PermissionMenu> list){
		Map<String,List<PermissionMenu>> mConll = new HashMap<String, List<PermissionMenu>>(); 
		for (PermissionMenu p : list) {
			if(mConll.get(p.getParentmenucode())==null){
				List<PermissionMenu>  items = new ArrayList<PermissionMenu>();
				mConll.put(p.getParentmenucode(), items);
			}
			mConll.get(p.getParentmenucode()).add(p);
			if(p.getMenulevel()<=1) p.setExpanded(true);
		}
		for (PermissionMenu pm : list) {
			pm.setChildren(mConll.get(pm.getMenucode()));
			
		}
		return JSONUnit.getJSONString(mConll.get("MN0000"));
	}

	@Override
	public int updateMenu(PermissionMenu menu) throws Exception {
		AttrCheckout.checkout(menu, false, new String[]{"menucode"});
		permissionMenuDao.updateSort(menu);
		return super.update(menu);
	}

	@Override
	public int logicDelete(Object menucodes) throws Exception {
		return super.logicDelete(menucodes);
	}

	

	
	
	

	


}
