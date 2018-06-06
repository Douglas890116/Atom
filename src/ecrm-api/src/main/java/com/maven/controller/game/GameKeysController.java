package com.maven.controller.game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.Base64;
import com.hy.pull.common.util.api.AVGameAPI;
import com.hy.pull.common.util.api.BaseInterfaceLog;
import com.hy.pull.common.util.api.GameAPI;
import com.hy.pull.common.util.game.av.AVUtil;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.ApiSoltGametypeEnterprise;
import com.maven.entity.DepositWithdralOrderDelegate;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.Enterprise;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.DepositWithdralOrderDelegate.Enum_auditresult;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.enums.GameBigType;
import com.maven.game.enums.GameEnum;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.game.enums.GameEnum.Enum_deviceType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.payment.jft.JFTSave;
import com.maven.service.ApiSoltGametypeEnterpriseService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.StringUtils;
import com.maven.utils.AESUtil;
import com.maven.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * API游戏接口及key管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/apigame")
public class GameKeysController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(GameKeysController.class.getName(), OutputManager.LOG_USER_MESSAGE);
	
	@Autowired
	private GameService gameService;
	@Autowired
	private EnterpriseGameService enterpriseGameService;
	@Autowired
	private EnterpriseService enterpriseService;
	private static List<Enterprise> listEnterprise = null;
	@Autowired
	private ApiSoltGametypeEnterpriseService apiSoltGametypeEnterpriseService;
	@Autowired
	private EmployeeApiAccoutService employeeApiAccoutService;
	
	/**
	 * 电子游戏类型对应的页面
	 */
	private static Map<String, String> types = new HashMap<String, String>(){{
		this.put("BBINGame", "bbin");
		this.put("TAGGame", "ag");
		this.put("PTGame", "pt");
		this.put("MGGame", "mg");
		this.put("TTGGame", "ttg");
		this.put("PNGGame", "png");
		this.put("AVGame", "av");
		this.put("SAGame", "sa");
		this.put("IDNGame", "idn");
	}};
	
	/**
	 * 
	 * @param employeecode=员工编码
	 * @param enterprisecode=企业编码
	 *  
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model){
		String employeecode = request.getParameter("employeecode");//必传
		String enterprisecode = request.getParameter("enterprisecode");//必传
		
		String biggametype = request.getParameter("biggametype");//游戏平台类型
		String category = request.getParameter("category");//分类
		String cname = request.getParameter("cname");//名称检索
		
		request.getSession().setAttribute("enterprisecode", enterprisecode);
		
		if(biggametype != null ) {
			EmployeeApiAccout __eaa = SystemCache.getInstance().getEmployeeGameAccount(employeecode, biggametype);
			if(__eaa != null) {
				request.getSession().setAttribute("username", __eaa.getGameaccount());
				request.getSession().setAttribute("password", __eaa.getGamepassword());
				request.getSession().setAttribute("employeecode", employeecode);
			}
			
		}
		
		
		List<ApiSoltGametypeEnterprise> data = new ArrayList<ApiSoltGametypeEnterprise>();
		
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("enterprisecode", enterprisecode);
			
			List<ApiSoltGametypeEnterprise> listType = null;
			
			if(request.getSession().getAttribute("listType") == null){
				listType = apiSoltGametypeEnterpriseService.selectTypes(params);
				request.getSession().setAttribute("listType", listType);
			} else {
				listType = (List)request.getSession().getAttribute("listType");
			}
			
			if(biggametype == null) {
				biggametype = listType.get(0).getBiggametype();
			}
			
			
			//查询第一个tab的数据集
			ApiSoltGametypeEnterprise temp = new ApiSoltGametypeEnterprise();
			temp.setBiggametype(biggametype);
			temp.setEnterprisecode(enterprisecode);
			if(cname != null && cname.trim().length() > 0) {
				temp.setCnname(cname);
			}
			temp.setStatus(Integer.valueOf(ApiSoltGametypeEnterprise.Enum_status.启用.value));
			
			List<ApiSoltGametypeEnterprise> list = apiSoltGametypeEnterpriseService.select(temp);
			//过滤分类
			if(category != null) {
				category = AESUtil.decrypt(category);
				for (ApiSoltGametypeEnterprise item : list) {
					if( item.getStype().contains(category)) {
						data.add(item);
					}
				}
			} else {
				data.addAll(list);
			}
			
			model.addAttribute("data", data);
			model.addAttribute("category", category);
			model.addAttribute("cname", cname);
			
			return "/game/"+types.get(biggametype);
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "/game/index";
	}
	
	@RequestMapping("/png")
	public String png(HttpServletRequest request,Model model){
		String employeecode = request.getSession().getAttribute("employeecode").toString();
		String enterprisecode = request.getSession().getAttribute("enterprisecode").toString();
		String username = request.getSession().getAttribute("username").toString();
		String password = request.getSession().getAttribute("password").toString();
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		String gamecode = String.valueOf(__parames.get("gamecode"));
		
		gamecode = gamecode.substring(0, gamecode.indexOf("."));
		
		// 获取游戏账号
		JSONObject object = JSONObject.fromObject( 
				GameAPI.login(username, password, "PNGGame", "DZ", enterprisecode, GameEnum.Enum_deviceType.电脑.code, gamecode, employeecode, "Y", null) 
				);
		return "redirect:"+object.getString("info"); 
	}
	
	@RequestMapping("/mg")
	public String mg(HttpServletRequest request,Model model){
		String employeecode = request.getSession().getAttribute("employeecode").toString();
		String enterprisecode = request.getSession().getAttribute("enterprisecode").toString();
		String username = request.getSession().getAttribute("username").toString();
		String password = request.getSession().getAttribute("password").toString();
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		String gamecode = String.valueOf(__parames.get("gamecode"));
		
		gamecode = gamecode.substring(0, gamecode.indexOf("."));
		
		// 获取游戏账号
		JSONObject object = JSONObject.fromObject( 
				GameAPI.login(username, password, "PNGGame", "DZ", enterprisecode, GameEnum.Enum_deviceType.电脑.code, gamecode, employeecode, "Y", null) 
				);
		return "redirect:"+object.getString("info"); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * eBET游戏API校验/回调
	 * 
	 */
	@RequestMapping("/eBETCallback")
	@ResponseBody
	public String eBETCallback(HttpServletRequest request){
		JSONObject result = new JSONObject();
		try{
			
//			{"password":"","signature":"FGQpUPke2oPqqgrZZzNCgGg3r8tvQL++8FYlGWDz+jlxT1H0EtNoJnDbgnTaMiibSZiW2rkrlr9I6tZ+SNNzUTktr5I1C6bPb9CDavgQsP7v3s0xXwirByX9cdfiVVKAY9FCOctElMuPArCGWYoE08G/WVVnBDdnVI3fk9DQC2g=",
//			"ip":"180.232.108.150","eventType":4,"cmd":"RegisterOrLoginReq","accessToken":"ddc268b2-edce-4496-9743-3ac7f51aa224",
//			"channelId":201,"username":"usroeqhtyu3r5fe9ccr8dy1mkp30w5","timestamp":1492415404}
			
			InputStream reader = request.getInputStream();// 读取HTTPinputstream
			InputStreamReader isr = new InputStreamReader(reader);
			BufferedReader br = new BufferedReader(isr);
			// 用来读取stream//把stream转换成jsonstring
			StringBuilder sb = new StringBuilder();
			String jsonString = "";
			while ((jsonString = br.readLine()) != null) {
				sb.append(jsonString);
			}
			System.err.println("eBETCallback========="+sb);
			
			
//			请求参数：
//			cmd string 是 值为RegisterOrLoginReq
//			eventType int 是 4 4－Token登入
//			channelId int 是 －1 渠道ID
//			username string 否 用户登入名（全小写）
//			password string 否 用户登入密码
//			signature string 是 签名(字串拼接):timestamp+accessToken （转成RSA：请参考）
//			timestamp int 是 1447722613 当前时间(Unixtime)
//			ip string 是 127.0.0.1 玩家IP
//			accessToken string 是 之前渠道系统返回的accessToken值
			
			try {
				if(listEnterprise == null) {
					listEnterprise = enterpriseService.selectAll(new HashMap<String, Object>());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JSONObject jsonObject = JSONObject.fromObject(sb.toString());
			if(jsonObject.getString("cmd").equals("UserInfo")) {
				result.put("status", "200");
				return result.toString();
			}
			
			
			
//			返回json格式的以下数据：
//			status int 是 200 状态码：
//				200－成功
//				4026－验证失败
//				4027－IP无访问权限
//				401－用户名或是密码不对
//				410－无效的token
//				505－渠道服务器在维护中
//			subChannelId int 是 来自指渠道管理员创建的子渠道ID，没有就填0.
//			accessToken string 是 1001 返回对应的accessToken
//			username string 是 登入的用户名
			
			String accessToken = jsonObject.getString("accessToken");
			String username = jsonObject.getString("username");
			String password = jsonObject.getString("password");
			
			EmployeeApiAccout apiAccout = new EmployeeApiAccout();
			apiAccout.setGameaccount(username);
			apiAccout = employeeApiAccoutService.selectFirst(apiAccout);
			if(apiAccout == null) {
				System.err.println("eBETCallback=========不能根据游戏账号查找到会员信息："+username);
				result.put("status", "401");
				return result.toString();
			}
			
			
			String enterprisecode = apiAccout.getEnterprisecode();
			String channelId = jsonObject.getString("channelId");
			String subChannelId = "0";
			
			Map<String, String> keydata = new HashMap<String, String>();
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(apiAccout.getEnterprisecode(), "EBETGame", keydata);
			if(keydata != null && keydata.size() > 0 && keydata.get("channelid") != null && keydata.get("channelid").equals(channelId)) {
				subChannelId = keydata.get("subchannelid");
			}
			
			if( subChannelId == null || subChannelId.equals("0")) {
				System.err.println("eBETCallback=========未能查找到子渠道号。总渠道号："+channelId);
				result.put("status", "4026");
				return result.toString();
			}

			if(password!= null && !password.equals("") && !apiAccout.getGamepassword().equals(password)) {
				System.err.println("eBETCallback=========密码错误.正确密码是："+apiAccout.getGamepassword());
				result.put("status", "401");
				return result.toString();
			}
			
			APIServiceNew apiServiceNew = new APIServiceNew(enterprisecode);
			String resultx = apiServiceNew.play("EBETGame", GameBigType.SX, apiAccout.getEmployeecode(), null, Enum_deviceType.电脑.code, "", new HashMap<String, String>());
			
			JSONObject object = JSONObject.fromObject(resultx);
			//操作成功
			if(object.getString("code").equals("0")) {
				log.Error("eBETCallback=========已为用户上分完毕."+apiAccout.getLoginaccount());
			} else {
				log.Error("eBETCallback=========上分处理失败："+object.getString("info")+"===="+apiAccout.getLoginaccount());
			}
			
			result.put("status", "200");
			result.put("subChannelId", subChannelId);
			result.put("accessToken", accessToken);
			result.put("username", username);
			log.Error("eBETCallback=========返回响应结果："+result);
			
			br.close();
			isr.close();
			reader.close();
			
			return result.toString();
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
		}catch(LogicTransactionRollBackException e){
			log.Error(e.getMessage(), e);
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		
		result.put("status", "4026");
		return result.toString();
	}
	
	
	/**
	 * 沙龙游戏API校验
	 * 
	 * 例如RegUserlnfo，贵司需提供贵司自订的校验钥(checkkey)字串(最长32字元)。我们会把这个校验钥以checkkey为参数名称，用GET方式调用贵司提供的认证网址，
	 * 例如www.yourcompanyname.com/verify.php?checkkey=your_own_checkkey。贵司需要校验该校验钥是否正确，并在正确时回传纯文字“checkkeyok"，失败时回传“checkkeyfailed"。
	 */
	@RequestMapping("/SACallback")
	@ResponseBody
	public String SACallback(HttpServletRequest request){
		try{
			
			String checkkey = request.getParameter("checkkey");
			
			System.err.println("SACallback========="+checkkey);
			
			return "checkkeyok";
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
		}catch(LogicTransactionRollBackException e){
			log.Error(e.getMessage(), e);
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "checkkeyfailed";
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
