package com.maven.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Constant;
import com.maven.entity.BrandDomain;
import com.maven.entity.BrandDomain.Enum_copyright;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployee.Enum_employeestatus;
import com.maven.entity.EnterpriseEmployee.Enum_onlinestatus;
import com.maven.game.APIServiceUtil;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
import com.maven.entity.GameApiInput;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BrandDomainService;
import com.maven.service.EmployeeMappingMenuService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameApiInputService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;
import com.maven.util.BeanToMapUtil;
import com.maven.util.Encrypt;


/**
 * 快速创建企业帐号信息
 * 创建日期:2016-09-29
 * @author temdy
 *
 */
@Controller
@RequestMapping("/enterprise")
public class FastCreateEnterpriseController extends BaseController {

	private LoggerManager log = LoggerManager.getLogger(FastCreateEnterpriseController.class.getName(),OutputManager.LOG_ACTIVITYBETRECORD);
	
	@Autowired
	private EnterpriseService enterpriseService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GameApiInputService gameApiInputService;
	
	@Autowired
	private BrandDomainService brandDomainService;
	
	/** 员工权限映射 */
	@Autowired
	private EmployeeMappingMenuService employeeMappingMenuService;
	
	private final String CEO = "MN00AP,MN00AN,MN00AM,MN00AL,MN00AE,MN00AD,MN00AC,MN00AB,MN00AA,MN00A9,MN00A8,MN00A7,MN00A6,MN00A5,MN00A0,MN009Q,MN009M,MN009K,MN009J,MN009I,MN009H,MN009G,MN009F,MN009E,MN009D,MN009C,MN009B,MN0099,MN0098,MN0097,MN0095,MN0094,MN008X,MN008W,MN008V,MN008S,MN008R,MN008Q,MN008P,MN008O,MN008E,MN008D,MN008C,MN008B,MN008A,MN0089,MN0082,MN007Z,MN007Y,MN007X,MN007W,MN007V,MN007U,MN007S,MN007P,MN007O,MN007I,MN007H,MN007G,MN007F,MN007E,MN007B,MN007A,MN0079,MN0078,MN0077,MN006J,MN006I,MN006H,MN006C,MN006A,MN0069,MN0068,MN0067,MN0060,MN005Z,MN005Y,MN005X,MN005P,MN005N,MN005M,MN005L,MN005K,MN005J,MN005I,MN005H,MN005G,MN005E,MN005D,MN005C,MN005B,MN0059,MN0058,MN0055,MN0054,MN0053,MN0051,MN004Z,MN004Y,MN004X,MN004W,MN004Q,MN004O,MN004L,MN004J,MN004H,MN004G,MN004F,MN004E,MN004D,MN004B,MN0048,MN0046,MN0045,MN0044,MN0042,MN0041,MN0040,MN003Y,MN003X,MN003W,MN003V,MN003U,MN003T,MN003S,MN003R,MN003Q,MN003P,MN003M,MN003L,MN003K,MN003J,MN003I,MN003H,MN003G,MN003F,MN003E,MN003D,MN003C,MN003B,MN003A,MN0039,MN0038,MN0037,MN0036,MN0035,MN002T,MN002S,MN002R,MN002Q,MN002B,MN0029,MN0023,MN0022,MN001Z,MN001Y,MN001U,MN001T,MN001S,MN001R,MN001Q,MN001P,MN001N,MN001M,MN001L,MN001K,MN001J,MN001E,MN001A,MN0019,MN0011,MN000Z,MN000T,MN000P,MN000O,MN000N,MN000L,MN000G,MN000F,MN000E,MN000C,MN0006,MN0005,MN0003,MN0002,MN0001,MN00DU";
	
	
	/**
	 * 跳转到快速创建企业帐号信息页面
	 * 创建日期:2016-09-29
	 * @param request 请求对象
	 * @return 跳转页面
	 */
	@RequestMapping("/toFastCreate")
	public String toFastCreate(HttpServletRequest request){
		try {
			//获取所有游戏列表
			List<Game> _all_game = gameService.getAllGame();
			request.setAttribute("games", _all_game);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/enterprise/fastCreate";
	}
	
	/**
	 * 保存快速创建企业帐号信息页面
	 * 创建日期:2016-09-29
	 * @param entity 表单参数集合
	 * @param request 请求对象
	 * @param session 会话对象
	 * @return
	 */
	@RequestMapping("/saveFastCreate")
	@ResponseBody
	public Object saveFastCreate(@RequestParam Map<String, Object> object,HttpServletRequest request, HttpSession session){
		try {	
			/*创建企业帐号信息开始*/
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			if(!ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)){
				return this.packJSON(Constant.BooleanByte.NO, "您不是企业号，无法创建企业");
			}
			EnterpriseEmployee employee = BeanToMapUtil.convertMap(object, EnterpriseEmployee.class);
			employee.setParentemployeecode(ee.getEmployeecode());
			employee.setParentemployeeaccount(ee.getLoginaccount());
			employee.setEmployeetypecode(EnterpriseEmployeeType.Type.企业号.value);
			employee.setOnlinestatus(Byte.valueOf(Enum_onlinestatus.离线.value));
			employee.setEmployeestatus(Enum_employeestatus.启用.value.byteValue());
			employee.setLoginpassword2(APIServiceUtil.encrypt(employee.getLoginpassword(), employee));//对原始密码进行加密
			employee.setLoginpassword(Encrypt.MD5(employee.getLoginpassword()));
			
			Enterprise enterprise = BeanToMapUtil.convertMap(object, Enterprise.class);			
			int rows = enterpriseService.tc_CreateEnterprise(enterprise,employee);
			/*创建企业帐号信息结束*/
			if(rows > 0){
				/*添加证书开始*/
				String enterpriseCode = enterprise.getEnterprisecode();//获取企业编码
				String employeecode = employee.getEmployeecode();//获取员工编号
				System.out.println("用户编号："+employeecode);
				GameApiInput _input_api = this.getRequestParamters(request, GameApiInput.class);
				String[] _arr_enterprisegames = request.getParameterValues("enterprisegames");
				_input_api.setEnterprisecode(enterpriseCode);
				_input_api.setOutputapistatus(Constant.BooleanByte.YES.byteValue());
				List<EnterpriseGame> _etgames = extractEnterpriseGames(_arr_enterprisegames,enterpriseCode);
				gameApiInputService.addGameInputApi(_input_api,_etgames);
				/*添加证书结束*/
				/*绑定企业域名开始*/
				if(object.get("memberUrl") != null){//如果会员站点域名不为空,则绑定会员站点域名
					addBrandDomain(1,object.get("memberUrl").toString(),enterpriseCode);
				}				
				if(object.get("proxyUrl") != null){//如果代理站点域名不为空,则绑定代理站点域名
					addBrandDomain(5,object.get("proxyUrl").toString(),enterpriseCode);
				}
				/*绑定企业域名结束*/
				
				/*分配权限开始*/
				List<EmployeeMappingMenu> list = new ArrayList<EmployeeMappingMenu>();

				String[] menus = CEO.split(",");
				for (String s : menus) {
					list.add(new EmployeeMappingMenu(employeecode,s));					
				}				
				employeeMappingMenuService.tc_Authorization(employeecode, list);
				/*分配权限结束*/
			}else{
				return this.packJSON(Constant.BooleanByte.NO, "企业已创建失败");
			}
			return this.packJSON(Constant.BooleanByte.YES, "企业帐号信息创建成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, "快速创建企业帐号失败");
		}
	}
	
	/**
	 * 添加企业域名绑定
	 * @param type 域名类型（1、会员站点，5、代理站点）
	 * @param domainlink 域名链接
	 * @param enterpriseCode 企业编码
	 */
	private void addBrandDomain(Integer type, String domainlink, String enterpriseCode){
		BrandDomain domain = new BrandDomain();
		domain.setDomainlink(domainlink);
		domain.setDomaintype(type.byteValue());
		domain.setEnterprisecode(enterpriseCode);
		domain.setBrandcode("");
		domain.setEmployeecode("");
		domain.setParentemployeecode("");
		domain.setEmployeetype("");
		domain.setDividend(new BigDecimal(0.0));
		domain.setShare(new BigDecimal(0.0));
		domain.setBonus("...");
		domain.setCopyright(Byte.valueOf(Enum_copyright.公共.value));
		domain.setIsdefualt(Constant.BooleanByte.NO.byteValue());
		domain.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
		domain.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
		try {
			brandDomainService.tc_save(domain);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 提取企业游戏列表
	 * @param _arr_enterprisegames 企业游戏编号列表
	 * @param enterprisecode 企业编码
	 * @return 游戏列表
	 */
	private List<EnterpriseGame> extractEnterpriseGames(String[] _arr_enterprisegames,String enterprisecode) {
		if(_arr_enterprisegames==null || _arr_enterprisegames.length==0) return null;
		List<EnterpriseGame> _list_enterprisegames = new ArrayList<EnterpriseGame>();
		for (String e : _arr_enterprisegames) {
			_list_enterprisegames.add(new EnterpriseGame(null, enterprisecode, e));
		}
		return _list_enterprisegames;
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
