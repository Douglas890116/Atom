package com.maven.controller.member;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.Token;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.DepositWithdralOrderDelegate;
import com.maven.entity.DepositWithdralOrderDelegate.Enum_auditresult;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.EnterpriseInformationQrcode;
import com.maven.entity.EnterpriseOperatingBrandPay;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.EnterpriseThirdpartyPaymentAgument;
import com.maven.entity.PaymentType.Enum_PayType;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.entity.ThirdpartyPaymentBank;
import com.maven.entity.ThirdpartyPaymentType.Enum_ThirdpartyPaymentType;
import com.maven.entity.UserLogs;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.payment.PayInterface;
import com.maven.payment.ap.AstropayAppConstants.Astro_PayType;
import com.maven.payment.ap.AstropayMerchantConfig;
import com.maven.payment.ap.AstropayOrderConfig;
import com.maven.payment.ap.AstropayPayMent;
import com.maven.payment.bl.BLAppConstants;
import com.maven.payment.bl.BLAppConstants.BL_PayType;
import com.maven.payment.bl.BLMerchantConfig;
import com.maven.payment.bl.BLOrderConfig;
import com.maven.payment.bl.BLPayMent;
import com.maven.payment.bl.BLSave;
import com.maven.payment.ch.CHMerchantConfig;
import com.maven.payment.ch.CHOrderConfig;
import com.maven.payment.ch.CHPayMent;
import com.maven.payment.ch.CHPayUtil;
import com.maven.payment.dd.DDAppConstants;
import com.maven.payment.dd.DDMerchantConfig;
import com.maven.payment.dd.DDOrderConfig;
import com.maven.payment.dd.DDPayMent;
import com.maven.payment.dd.DDSave;
import com.maven.payment.duo.DuoAppConstants;
import com.maven.payment.duo.DuoMerchantConfig;
import com.maven.payment.duo.DuoOrderConfig;
import com.maven.payment.duo.DuoPayMent;
import com.maven.payment.duo.DuoUtil;
import com.maven.payment.ek.EKAppConstants;
import com.maven.payment.ek.EKMerchantConfig;
import com.maven.payment.ek.EKOrderConfig;
import com.maven.payment.ek.EKPayMent;
import com.maven.payment.ek.EKSave;
import com.maven.payment.fb.FBAppConstants;
import com.maven.payment.fb.FBMerchantConfig;
import com.maven.payment.fb.FBOrderConfig;
import com.maven.payment.fb.FBPayMent;
import com.maven.payment.fb.FBSave;
import com.maven.payment.gst.GSTAppConstants;
import com.maven.payment.gst.GSTAppConstants.GST_PayType;
import com.maven.payment.gst.GSTMerchantConfig;
import com.maven.payment.gst.GSTOrderConfig;
import com.maven.payment.gst.GSTPayMent;
import com.maven.payment.gst.GSTSave;
import com.maven.payment.hc.HCMerchantConfig;
import com.maven.payment.hc.HCOrderConfig;
import com.maven.payment.hc.HCPayMent;
import com.maven.payment.hc.HCpayUtil;
import com.maven.payment.hr.HRAppConstants;
import com.maven.payment.hr.HRMerchantConfig;
import com.maven.payment.hr.HROrderConfig;
import com.maven.payment.hr.HRPayMent;
import com.maven.payment.hr.HRSave;
import com.maven.payment.hr.RSAClient;
import com.maven.payment.hr.wxzfb.HRWXMerchantConfig;
import com.maven.payment.hr.wxzfb.HRWXOrderConfig;
import com.maven.payment.hr.wxzfb.HRWXPayMent;
import com.maven.payment.hr.wxzfb.HRWXSave;
import com.maven.payment.jeanpay.JEANPAYMerchantConfig;
import com.maven.payment.jeanpay.JEANPAYOrderConfig;
import com.maven.payment.jeanpay.JEANPAYPayMent;
import com.maven.payment.jeanpay.JEANPAYpayUtil;
import com.maven.payment.jft.JFTMerchantConfig;
import com.maven.payment.jft.JFTOrderConfig;
import com.maven.payment.jft.JFTPayMent;
import com.maven.payment.jft.JFTSave;
import com.maven.payment.jh.JHAppConstants;
import com.maven.payment.jh.JHMerchantConfig;
import com.maven.payment.jh.JHOrderConfig;
import com.maven.payment.jh.JHPayMent;
import com.maven.payment.jh.JHSave;
import com.maven.payment.jh.h5.JHH5AppConstants;
import com.maven.payment.jh.h5.JHH5MerchantConfig;
import com.maven.payment.jh.h5.JHH5OrderConfig;
import com.maven.payment.jh.h5.JHH5PayMent;
import com.maven.payment.jh.h5.JHH5Save;
import com.maven.payment.jh.quick.JHQuickPayAppConstants;
import com.maven.payment.jh.quick.JHQuickPayMerchantConfig;
import com.maven.payment.jh.quick.JHQuickPayOrderConfig;
import com.maven.payment.jh.quick.JHQuickPayPayMent;
import com.maven.payment.jh.quick.JHQuickPaySave;
import com.maven.payment.jh2.JH2AppConstants;
import com.maven.payment.jh2.JH2MerchantConfig;
import com.maven.payment.jh2.JH2OrderConfig;
import com.maven.payment.jh2.JH2PayMent;
import com.maven.payment.jh2.JH2Util;
import com.maven.payment.jpay.JMerchantConfig;
import com.maven.payment.jpay.JOrderConfig;
import com.maven.payment.jpay.JPayMent;
import com.maven.payment.jpay.JPaySignUtil;
import com.maven.payment.kk.KKAppConstants;
import com.maven.payment.kk.KKAppConstants.KK_ProductType;
import com.maven.payment.kk.KKMerchantConfig;
import com.maven.payment.kk.KKOrderConfig;
import com.maven.payment.kk.KKPayMent;
import com.maven.payment.kk.KKSave;
import com.maven.payment.leying.LEYINGMerchantConfig;
import com.maven.payment.leying.LEYINGOrder;
import com.maven.payment.leying.LEYINGOrderConfig;
import com.maven.payment.leying.LEYINGPayMent;
import com.maven.payment.leying.LEYINGSaveCallBack;
import com.maven.payment.lf.LfMerchantConfig;
import com.maven.payment.lf.LfOrderConfig;
import com.maven.payment.lf.LfPayMent;
import com.maven.payment.lf.LfPaySignUtil;
import com.maven.payment.lfalipay.LfAliPayMerchantConfig;
import com.maven.payment.lfalipay.LfAliPayOrderConfig;
import com.maven.payment.lfalipay.LfAliPayPayMent;
import com.maven.payment.lfqqpay.LfQQPayMerchantConfig;
import com.maven.payment.lfqqpay.LfQQPayOrderConfig;
import com.maven.payment.lfqqpay.LfQQPayPayMent;
import com.maven.payment.lfwx.LfwxMerchantConfig;
import com.maven.payment.lfwx.LfwxOrderConfig;
import com.maven.payment.lfwx.LfwxPayMent;
import com.maven.payment.lx.LeAppConstants;
import com.maven.payment.lx.LeMerchantConfig;
import com.maven.payment.lx.LeOrderConfig;
import com.maven.payment.lx.LePayMent;
import com.maven.payment.lx.LeSave;
import com.maven.payment.mb.MBMerchantConfig;
import com.maven.payment.mb.MBOrderConfig;
import com.maven.payment.mb.MBPayMent;
import com.maven.payment.mb.MBpayUtil;
import com.maven.payment.mf.MFAppConstants;
import com.maven.payment.mf.MFMerchantConfig;
import com.maven.payment.mf.MFOrderConfig;
import com.maven.payment.mf.MFPayMent;
import com.maven.payment.mf.MFSave;
import com.maven.payment.nf.NfMerchantConfig;
import com.maven.payment.nf.NfOrderConfig;
import com.maven.payment.nf.NfPayAppConstants;
import com.maven.payment.nf.NfPayMent;
import com.maven.payment.nf.NfPaySignUtil;
import com.maven.payment.nf.wxzfb.NfWXMerchantConfig;
import com.maven.payment.nf.wxzfb.NfWXOrderConfig;
import com.maven.payment.nf.wxzfb.NfWXPayMent;
import com.maven.payment.rf.RFAppConstants;
import com.maven.payment.rf.RFAppConstants.RF_AppType;
import com.maven.payment.rf.RFMerchantConfig;
import com.maven.payment.rf.RFOrderConfig;
import com.maven.payment.rf.RFPayMent;
import com.maven.payment.rf.RFSave;
import com.maven.payment.ry.RYAppConstants;
import com.maven.payment.ry.RYAppConstants.RY_PayType;
import com.maven.payment.ry.RYMerchantConfig;
import com.maven.payment.ry.RYOrderConfig;
import com.maven.payment.ry.RYPayMent;
import com.maven.payment.ry.RYSave;
import com.maven.payment.sdp2p.SDP2PMerchantConfig;
import com.maven.payment.sdp2p.SDP2POrderConfig;
import com.maven.payment.sdp2p.SDP2PPaySignUtil;
import com.maven.payment.sdwx.SDWXMerchantConfig;
import com.maven.payment.sdwx.SDWXOrderConfig;
import com.maven.payment.sdwx.SDWXPayAppConstants;
import com.maven.payment.sdwx.SDWXPayMent;
import com.maven.payment.sdwx.SDWXPaySignUtil;
import com.maven.payment.th.THMerchantConfig;
import com.maven.payment.th.THMerchantConfig.Enum_payType;
import com.maven.payment.th.THOrderConfig;
import com.maven.payment.th.THPayMent;
import com.maven.payment.th.THSave;
import com.maven.payment.wft.WFTAppConstants;
import com.maven.payment.wft.WFTMerchantConfig;
import com.maven.payment.wft.WFTOrderConfig;
import com.maven.payment.wft.WFTPayMent;
import com.maven.payment.wft.WFTSave;
import com.maven.payment.xbei.XBAppConstants;
import com.maven.payment.xbei.XBMerchantConfig;
import com.maven.payment.xbei.XBOrderConfig;
import com.maven.payment.xbei.XBPayMent;
import com.maven.payment.xbei.XBSave;
import com.maven.payment.xf.XFAppConstants;
import com.maven.payment.xf.XFAppConstants.XF_PayType;
import com.maven.payment.xf.XFMerchantConfig;
import com.maven.payment.xf.XFOrderConfig;
import com.maven.payment.xf.XFPayMent;
import com.maven.payment.xf.XFSave;
import com.maven.payment.xft.XFTAppConstants;
import com.maven.payment.xft.XFTMerchantConfig;
import com.maven.payment.xft.XFTOrderConfig;
import com.maven.payment.xft.XFTPayMent;
import com.maven.payment.xft.XFTUtil;
import com.maven.payment.xingfu.XingFAppConstants;
import com.maven.payment.xingfu.XingFMerchantConfig;
import com.maven.payment.xingfu.XingFOrderConfig;
import com.maven.payment.xingfu.XingFPayMent;
import com.maven.payment.xingfu.XingFSave;
import com.maven.payment.yb.YbMerchantConfig;
import com.maven.payment.yb.YbOrderConfig;
import com.maven.payment.yb.YbPayMent;
import com.maven.payment.yee.YEEMerchantConfig;
import com.maven.payment.yee.YEEOrderConfig;
import com.maven.payment.yee.YEEPayMent;
import com.maven.payment.yee.YEESave;
import com.maven.payment.yom.YOMMerchantConfig;
import com.maven.payment.yom.YOMOrderConfig;
import com.maven.payment.yom.YOMPayMent;
import com.maven.payment.yom.YOMSave;
import com.maven.payment.ys.SignUtils;
import com.maven.payment.ys.alipay.YSALIPayAppConstants;
import com.maven.payment.ys.alipay.YSALIPayMerchantConfig;
import com.maven.payment.ys.alipay.YSALIPayOrderConfig;
import com.maven.payment.ys.alipay.YSALIPayPayMent;
import com.maven.payment.ys.alipay.YSALIPaySave;
import com.maven.payment.yunsheng.YSAppConstants;
import com.maven.payment.yunsheng.YSMerchantConfig;
import com.maven.payment.yunsheng.YSOrderConfig;
import com.maven.payment.yunsheng.YSPayMent;
import com.maven.payment.yunsheng.YSpayUtil;
import com.maven.payment.zb.ZBAppConstants;
import com.maven.payment.zb.ZBMerchantConfig;
import com.maven.payment.zb.ZBOrderConfig;
import com.maven.payment.zb.ZBPayMent;
import com.maven.payment.zb.ZBSave;
import com.maven.payment.zf.ZfMerchantConfig;
import com.maven.payment.zf.ZfOrderConfig;
import com.maven.payment.zf.ZfPayMent;
import com.maven.payment.zf.ZfPaySignUtil;
import com.maven.payment.zft.ZFTAppConstants;
import com.maven.payment.zft.ZFTAppConstants.ZFT_PayType;
import com.maven.payment.zft.ZFTMerchantConfig;
import com.maven.payment.zft.ZFTOrderConfig;
import com.maven.payment.zft.ZFTPayMent;
import com.maven.payment.zft.ZFTSave;
import com.maven.payment.zfwx.ZfWXMerchantConfig;
import com.maven.payment.zfwx.ZfWXOrderConfig;
import com.maven.payment.zfwx.ZfWXPayMent;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.DepositWithdralOrderDelegateService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseInformationQrcodeService;
import com.maven.service.EnterpriseOperatingBrandPayService;
import com.maven.service.EnterpriseThirdpartyPaymentAgumentService;
import com.maven.service.EnterpriseThirdpartyPaymentService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.ThirdpartyPaymentBankService;
import com.maven.service.UserLogsService;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;
import com.maven.util.DateUtils;
import com.maven.util.JSONUnit;
import com.maven.util.RandomString;
import com.maven.util.WebInfoHandle;
import com.maven.utils.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/TPayment")
public class PaymentThirdpartyController extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(PaymentThirdpartyController.class.getName(),
			OutputManager.LOG_USER_THIRDPARTYPAYMENT);
	/**
	 * 快捷支付
	 */
	@Autowired
	private EnterpriseThirdpartyPaymentService enterpriseThirdpartyPaymentService;

	/** 第三方支付编码与秘钥 */
	@Autowired
	private EnterpriseThirdpartyPaymentAgumentService enterpriseThirdpartyPaymentAgumentService;

	/** 用户信息 */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;

	/** 存取款记录 */
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	@Autowired
	private DepositWithdralOrderDelegateService depositWithdralOrderDelegateService;

	@Autowired
	private ThirdpartyPaymentBankService thirdpartyPaymentBankService;
	@Autowired
	private ActivityBetRecordService activityBetRecordService;
	@Autowired
	private EnterpriseOperatingBrandPayService enterpriseOperatingBrandPayService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private EnterpriseInformationQrcodeService enterpriseInformationQrcodeService;

	/**
	 * 获取企业收款二维码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/EQrcodes", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String enterpriseQrcodePayment(HttpServletRequest request) {
		try {
			Map<String, Object> __object = super.apiDecode(super.getRequestParamters(request));

			List<EnterpriseInformationQrcode> list = enterpriseInformationQrcodeService.selectBetRecord(__object);

			return Enum_MSG.成功.message(list);
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	/**
	 * 获取企业第三方支付
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/EThirdpartys", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String enterpriseThirdpartyPayment(HttpServletRequest request) {
		try {
			Map<String, Object> __object = super.apiDecode(super.getRequestParamters(request));
			String __enterprisecode = String.valueOf(__object.get("enterprisecode"));
			String type = String.valueOf(__object.get("type"));
			EnterpriseThirdpartyPayment __etp = new EnterpriseThirdpartyPayment();
			__etp.setEnterprisecode(__enterprisecode);
			if (EnterpriseThirdpartyPayment.Enum_type.H5.value.equals(type)) {
				__etp.setH5status(EnterpriseThirdpartyPayment.Enum_status.启用.value);
			} else {
				__etp.setStatus(EnterpriseThirdpartyPayment.Enum_status.启用.value);
			}
			List<EnterpriseThirdpartyPayment> __list = enterpriseThirdpartyPaymentService.select(__etp);
			Iterator<EnterpriseThirdpartyPayment> __data = __list.iterator();
			LinkedHashMap<String, EnterpriseThirdpartyPayment> __minbalance = new LinkedHashMap<String, EnterpriseThirdpartyPayment>();
			while (__data.hasNext()) {
				EnterpriseThirdpartyPayment __e = __data.next();

				/************* 修改后代码jason ***************/
				__minbalance.put(__e.getEnterprisethirdpartycode(), __e);

			}
			List<Map<String, Object>> __returnObject = new ArrayList<Map<String, Object>>();

			String __brandcode = null;
			if (StringUtil.getString(__object.get("brandcode")).length() > 0) {
				__brandcode = String.valueOf(__object.get("brandcode"));
			}
			TakeDepositRecord takeDepositRecord = new TakeDepositRecord();
			takeDepositRecord.setBrandcode(__brandcode);
			takeDepositRecord.setEnterprisecode(__enterprisecode);

			String paycallbackurl = getBasePath();// 默认返回请求当前域名

			for (EnterpriseThirdpartyPayment __ep : __minbalance.values()) {

				/************* 注意：该支付方式不是存款接口，是提款、出款/付款接口，所以不能返回 *************/
				if (__ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD付款.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.极付出款.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐付出款.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.华仁代付.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.通汇支付.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.喜发代付.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.点点代付.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.Astropay出款.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.聚合出款.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.支付通代付.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.锐付出款.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.银盛代付.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.星付代付.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.旺付通代付.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.多多代付.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.信付通代付.value)
						|| __ep.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇代付.value)) {
					continue;
				}

				if (__ep.getCallbackurl() == null || __ep.getCallbackurl().equals("")) {
					__ep.setCallbackurl(paycallbackurl);// 返回支付域名
				}
				__ep.setPaycallbackurl(paycallbackurl);

				// 业务处理（1、名字替换为企业自定义的显示名称 2、支持的银行列表过滤掉禁用状态的）
				__ep.setThirdpartypaymenttypename(__ep.getDisplayname());

				boolean isbanks = __ep.getIsbanks();
				boolean isweixin = __ep.getIsweixin();
				boolean iszhifubao = __ep.getIszhifubao();
				List<ThirdpartyPaymentBank> listBanks = SystemCache.getInstance()
						.getThirdpartyPaymentBanks(__ep.getThirdpartypaymenttypecode());
				List<ThirdpartyPaymentBank> listRemove = new ArrayList<ThirdpartyPaymentBank>();

				for (ThirdpartyPaymentBank thirdpartyPaymentBank : listBanks) {
					// B019=微信
					// B020=支付宝
					// 其他为银行卡
					String paymenttypebankcode = thirdpartyPaymentBank.getBankcode();
					if (paymenttypebankcode.equals("B019")) {
						if (isweixin == false) {// 禁用微信时
							listRemove.add(thirdpartyPaymentBank);
						}
					} else if (paymenttypebankcode.equals("B020")) {
						if (iszhifubao == false) {// 禁用支付宝时
							listRemove.add(thirdpartyPaymentBank);
						}
					} else {
						if (isbanks == false) {// 禁用银行卡时
							listRemove.add(thirdpartyPaymentBank);
						}
					}
				}
				if (listRemove.size() > 0) {
					listBanks.removeAll(listRemove);
				}
				__ep.setBanks(listBanks);

				__returnObject.add(AttrCheckout.checkout(BeanToMapUtil.convertBean(__ep, true), true,
						new String[] { "enterprisethirdpartycode", "thirdpartypaymenttypecode",
								"thirdpartypaymenttypename", "paycallbackurl", "callbackurl", "banks", "minmoney",
								"maxmoney" }));
			}
			return Enum_MSG.成功.message(__returnObject);
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	/**
	 * 获取第三方支付对应的银行与编码
	 * 
	 * @param request
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/TPayMentBank", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String thirdpartyMentBank(HttpServletRequest request) {
		try {
			String __thirdpartypaymenttypecode = request.getParameter("thirdpartypaymenttypecode");
			if (StringUtils.isNotBlank(__thirdpartypaymenttypecode)) {
				List<ThirdpartyPaymentBank> __paymentbanks = thirdpartyPaymentBankService
						.takeThirdpartyPaymentBank(__thirdpartypaymenttypecode);
				return Enum_MSG.成功.message(__paymentbanks);
			} else {
				return Enum_MSG.参数错误.message("支付类型编号为空");
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	/**
	 * 回调后执行充值返利活动处理
	 * 
	 * （每一个回调函数都应该在正常处理完毕后调用此方法）
	 * 
	 * jason
	 * 
	 * @param depositeRecord
	 * @param handles
	 * @throws Exception
	 */
	private void saveingActivityVerify(TakeDepositRecord depositeRecord, DepositWithdralOrderDelegate handles)
			throws Exception {

		takeDepositRecoredService.saveingVerify(depositeRecord, handles);

		/************************* 处理H5百家乐独立版的HY真人更新元宝余额 **************************/
		if (depositeRecord.getFavourableid() != null && depositeRecord.getFavourableid().equals("H5BJL")) {
			// int result =
			// baccarath5BalanceService.updateBalance(depositeRecord.getEmployeecode(),
			// depositeRecord.getOrderamount().doubleValue());//应该传入正数，余额增加
		}
		/************************* 处理H5百家乐独立版的HY真人更新元宝余额 **************************/

		// 将订单号加入完成订单Map中
		addCompletedOrdernumber(depositeRecord.getOrdernumber());
	}

	/**
	 * 支付完成后经该路径返回一个提醒页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/success", produces = "text/html;charset=UTF-8")
	public String success(HttpServletRequest request, Model model, HttpEntity<byte[]> requestEntity) {
		return "/payment/success";
	}

	/**
	 * 第三方即时存款
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ESaving", produces = "text/html;charset=UTF-8")
	public String eSaving(HttpServletRequest request, Model model, HttpEntity<byte[]> requestEntity,
			HttpServletResponse response) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false,
					new String[] { "enterprisecode", "brandcode", "employeecode", "orderamount",
							"enterprisethirdpartycode", "traceip" },
					new String[] { "paymenttypebankcode", "bankcode" });

			BigDecimal ordermount = new BigDecimal(String.valueOf(object.get("orderamount")));
			if (ordermount.doubleValue() <= 0) {
				model.addAttribute("message", Enum_MSG.金额异常.desc);
				return Constant.PAGE_LOGICVALIDATEFIAL;
			}
			// EnterpriseEmployee ee =
			// super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);

			/************** 信用代理用户不得存取款 **************/
			EnterpriseEmployee parentEmployee = enterpriseEmployeeService
					.takeEmployeeByCode(ee.getParentemployeecode());
			if (parentEmployee != null && parentEmployee.getEmployeetypecode().equals(Type.信用代理.value)) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.信用代用户不得存取款.desc);
			}

			EnterpriseThirdpartyPayment __etp = new EnterpriseThirdpartyPayment();
			__etp.setEnterprisecode(ee.getEnterprisecode());

			// 获取请求类型，PC / H5
			String type = String.valueOf(object.get("type"));
			if (EnterpriseThirdpartyPayment.Enum_type.H5.value.equals(type)) {
				__etp.setH5status(EnterpriseThirdpartyPayment.Enum_status.启用.value);
			} else {
				__etp.setStatus(EnterpriseThirdpartyPayment.Enum_status.启用.value);
			}
			__etp.setEnterprisethirdpartycode(String.valueOf(object.get("enterprisethirdpartycode")));
			List<EnterpriseThirdpartyPayment> __list = enterpriseThirdpartyPaymentService.select(__etp);
			if (__list == null || __list.size() != 1) {
				model.addAttribute("message", Enum_MSG.即时支付账号不存在.desc);
				return Constant.PAGE_LOGICVALIDATEFIAL;
			}
			__etp = __list.get(0);

			if (__etp.getMinmoney().doubleValue() > 0) {
				if (ordermount.doubleValue() < __etp.getMinmoney().doubleValue()) {
					model.addAttribute("message", "充值金额不能低于最小限额" + __etp.getMinmoney().intValue() + "元");
					return Constant.PAGE_LOGICVALIDATEFIAL;
				}
			}
			if (__etp.getMaxmoney().doubleValue() > 0) {
				if (ordermount.doubleValue() > __etp.getMaxmoney().doubleValue()) {
					model.addAttribute("message", "充值金额不能高于最大限额" + __etp.getMaxmoney().intValue() + "元");
					return Constant.PAGE_LOGICVALIDATEFIAL;
				}
			}

			if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.Astropay支付.value)) {
				Set<String> params = object.keySet();
				for (String param : params) {
					model.addAttribute(param, object.get(param));
				}
				model.addAttribute("username", ee.getLoginaccount());
				if (WebInfoHandle.checkAgentIsMobile(request)) {
					return "/payment/astropayH5";
				} else {
					return "/payment/astropay";
				}
			}

			TakeDepositRecord takeDepositRecord = BeanToMapUtil.convertMap(object, TakeDepositRecord.class);
			takeDepositRecord.setOrdernumber(RandomString.UUID());// 32位数的订单号

			/************* 新贝支付只能支持30位数的单号[8位会员编码 + 13位数时间戳 + 9位随机数] ***************/
			if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.新贝网关.value) || // 新贝支付
					__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.新贝微信.value) || // 新贝支付
					__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.新贝支付宝.value)) { // 新贝支付

				takeDepositRecord.setOrdernumber(XBAppConstants.getOrderNo(ee.getEmployeecode()));

			}
			/************* SD支付只能支持24位数的单号[8位会员编码 + 13位数时间戳 + 3位随机数] ***************/
			else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD银联.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD微信.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD支付宝.value)) {

				takeDepositRecord.setOrdernumber(SDWXPayAppConstants.getOrderNo(ee.getEmployeecode()));
			}
			/************* 牛付支付只能支持30位数的单号[8位会员编码 + 13位数时间戳 + 9位随机数] ***************/
			else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.牛付网关.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.牛付微信支付宝.value)) {

				takeDepositRecord.setOrdernumber(NfPayAppConstants.getOrderNo(ee.getEmployeecode()));
			}
			/*************
			 * 华仁支付接口订单号格式[订单日期-商户编号-订单流水号，yyyyMMdd-#####-#####]
			 ***************/
			else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.华仁网银.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.华仁微信支付宝.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.华仁代付.value)) {
				takeDepositRecord.setOrdernumber(HRAppConstants.getOderNumber());
			}
			/************* 云盛支付只能支持30位数的单号[8位会员编码 + 13位数时间戳 + 9位随机数] ***************/
			else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.云盛支付.value)) {

				takeDepositRecord.setOrdernumber(YSAppConstants.getOrderNo(ee.getEmployeecode()));
			}
			/************* 锐付支付支持30位或30位一下的订单号 并且要在单号前加上前缀 ***************/
			else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.锐付网银.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.锐付微信支付宝.value)) {
				takeDepositRecord.setOrdernumber(getRFOrderNo(__etp));
			}
			/************* 可可支付支持最高30位的订单号 ***************/
			else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.可可网银.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.可可微信支付宝.value)) {
				takeDepositRecord.setOrdernumber(KKAppConstants.getOrderNo());
			}
			/************* 众宝支付支持最高30位的订单号 ***************/
			else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.众宝支付.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.众宝支付H5.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.众宝微信支付宝.value)) {
				takeDepositRecord.setOrdernumber(ZBAppConstants.getOrderNo());
			}
			/************* 亿卡云支付支持最高30位的订单号 ***************/
			else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.亿卡云网银.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.亿卡云微信支付宝.value)) {
				takeDepositRecord.setOrdernumber(ZBAppConstants.getOrderNo());
			}
			/************* 聚合支付支持最高18位的订单号 ***************/
			else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.聚合支付.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.聚合快捷支付.value)) {
				takeDepositRecord.setOrdernumber(JHAppConstants.getOrderNo());
			}
			/************* 银盛支付前8为要当天的日期 ***************/
			else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.银盛支付宝H5.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.银盛代付.value)) {
				takeDepositRecord.setOrdernumber(SignUtils.getOrderNo());
			}
			/************* 神奇支付订单号最高30位 ***************/
			else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇支付.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇扫码.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇京东.value)
					|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇银联.value)) {
				takeDepositRecord.setOrdernumber(JH2Util.getOrderNo());
			}
			takeDepositRecord.setParentemployeecode(ee.getParentemployeecode());
			takeDepositRecord.setPaymenttypecode(Enum_PayType.第三方即时支付.value);
			takeDepositRecord.setOrderamount(ordermount);
			takeDepositRecord.setEnterprisepaymentname(__etp.getThirdpartypaymenttypename());
			takeDepositRecord.setEnterprisepaymentaccount(__etp.getEnterprisethirdpartycode());
			takeDepositRecord.setEnterprisepaymentbank(__etp.getThirdpartypaymenttypecode());
			takeDepositRecord.setEmployeepaymentname("");
			takeDepositRecord.setEmployeepaymentaccount("");
			takeDepositRecord.setEmployeepaymentbank(
					String.valueOf(object.get("bankcode") == null ? "" : object.get("bankcode")));
			takeDepositRecord.setOrdertype((byte) Enum_ordertype.存款.value);
			takeDepositRecord.setOrderstatus((byte) Enum_orderstatus.审核中.value);
			takeDepositRecord.setOrdercreater("会员" + ee.getDisplayalias());
			takeDepositRecord.setOrdercomment("自动存款");
			takeDepositRecord.setOrderdate(new Date());
			if (takeDepositRecord.getFavourableid() != null) {
				if (takeDepositRecord.getFavourableid().trim().length() <= 0) {
					takeDepositRecord.setFavourableid(null);
				}
			}
			takeDepositRecord.setLoginaccount(ee.getLoginaccount());

			if (StringUtil.getString(object.get("H5BJL")).toUpperCase().equals("Y")) {
				takeDepositRecord.setFavourableid("H5BJL");
			}

			log.Error("object=" + object);

			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(),
					ee.getLoginaccount(), UserLogs.Enum_operatype.存取款业务,
					__etp.getThirdpartypaymenttypecode() + "存款" + ordermount + "元", null, null));

			String __url = excutethirdpartpayment(request, object, ordermount, __etp, takeDepositRecord, model);

			if (StringUtils.isNotBlank(__url)) {
				takeDepositRecoredService.tc_save_money(takeDepositRecord);

				/************************* 处理H5百家乐独立版的HY真人游戏账号注册业务 **************************/
				// **********直接返回支付地址，不能做重定向
				if (StringUtil.getString(object.get("H5BJL")).toUpperCase().equals("Y")) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print(Enum_MSG.成功.message(__url));
					return null;
				}
				/************************* 处理H5百家乐独立版的HY真人游戏账号注册业务 **************************/

				/************************* 处理信用代理营销系统业务 **************************/
				// **********直接返回支付地址，不能做重定向
				if (StringUtil.getString(takeDepositRecord.getFavourableid()).toUpperCase().equals("CREDIT_AGENT_H5")) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print(Enum_MSG.成功.message(__url));
					return null;
				}
				/************************* 处理信用代理营销系统业务 **************************/

				model.addAttribute("url", __url);

				// SD支付、乐信付
				// 只能是直接带参数的跳转, 乐信付也直接跳转
				if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD银联.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD微信.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD支付宝.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐信付PC收银台.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐信付H5收银台.value)
						|| (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.可可微信支付宝.value)
								&& WebInfoHandle.checkAgentIsMobile(request))
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.星付H5.value)) {
					return "redirect:" + __url;
				} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD网银P2P.value)) {
					return __url;
				} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐付微信.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐付支付宝.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐付QQ钱包.value)) {
					model.addAttribute("takeDepositRecord", takeDepositRecord);
					return "/payment/jump";
				} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.牛付微信支付宝.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.智付微信支付宝.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.极付微信支付宝.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.可可微信支付宝.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.聚合支付.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.点点支付.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.付呗支付.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.支付通微信支付宝.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.星付扫码.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.信付通扫码.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.信付通京东.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇扫码.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇京东.value)
						|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇银联.value)) {
					// 直接展示图片的都在这里处理
					model.addAttribute("takeDepositRecord", takeDepositRecord);
					if (takeDepositRecord.getEmployeepaymentbank().equals("B019")) {
						model.addAttribute("sname", "[微信]");
					} else if (takeDepositRecord.getEmployeepaymentbank().equals("B020")) {
						model.addAttribute("sname", "[支付宝]");
					} else if (takeDepositRecord.getEmployeepaymentbank().equals("B034")) {
						model.addAttribute("sname", "[手机QQ]");
					} else if (takeDepositRecord.getEmployeepaymentbank().equals("B035")) {
						model.addAttribute("sname", "[京东APP]");
					} else if (takeDepositRecord.getEmployeepaymentbank().equals("B036")) {
						model.addAttribute("sname", "[银联APP]");
					} else {
						model.addAttribute("sname", "[微信]或[支付宝]或[QQ]");
					}
					return "/payment/show_qrcode";
				}

				return "/payment/thpaymentjump";
			} else {
				return "/error/nosupport";
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			model.addAttribute("message", e.getMessage());
			return Constant.PAGE_ERROR;
		} catch (LogicTransactionRollBackException e) {
			model.addAttribute("message", e.getMessage());
			return Constant.PAGE_LOGICVALIDATEFIAL;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			model.addAttribute("message", e.getMessage());
			return Constant.PAGE_ERROR;
		}
	}

	/**
	 * 执行第三方支付
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String excutethirdpartpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord, Model model)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {
		if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.通汇支付.value)) {
			return thpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.通汇_微信.value)) {
			return thpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.通汇_支付宝.value)) {
			return thpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.易宝支付.value)) {
			return yeepayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐盈支付.value)) {
			return leyingpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.优付支付.value)) {
			return yompayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.JPAY支付.value)) {
			return jpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.银宝支付.value)) {
			return ybpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐付支付.value)) {
			return lfpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐付微信.value)) {// 乐付微信
			return lfwxpayment(request, object, ordermount, __etp, takeDepositRecord, model);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.新贝网关.value)) {// 新贝支付
			return xbpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.新贝微信.value)) {// 新贝支付
			return xbpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.新贝支付宝.value)) {// 新贝支付
			return xbpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.国盛通.value)) {// 国盛通
			return gstpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.捷付通.value)) {// 捷付通
			return jftpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.智付网银.value)) {// 智付网银
			return zftpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.智付微信支付宝.value)) {// 智付微信支付宝
			return zfwxtpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD微信.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD支付宝.value)) {// SD的微信和支付宝
			return sdpayment2(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD银联.value)) {// SD银联
			return sdpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.SD网银P2P.value)) {// 网银支付
			return sdp2ppayment(request, object, ordermount, __etp, takeDepositRecord, model);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.牛付网关.value)) {// 牛付网关
			return nfpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.牛付微信支付宝.value)) {// 牛付微信支付宝
			return nfwxpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.极付网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.极付快捷支付.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.极付微信支付宝.value)) {// 极付
			return jfpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.摩宝支付.value)) {// 摩宝支付
			return mbpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.华仁网银.value)) {
			return HRPaymant(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.华仁微信支付宝.value)) {
			return HRWXPaymant(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.众宝支付.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.众宝支付H5.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.众宝微信支付宝.value)) {
			return ZBPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.云盛支付.value)) {
			return yspayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.汇潮支付.value)) {
			return hcpayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.秒付网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.秒付微信.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.秒付支付宝.value)) {
			return MFPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐信付PC收银台.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐信付H5收银台.value)) {
			return LePayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.如意网关支付.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.如意微信支付宝.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.如意京东钱包.value)) {
			return RYPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐付支付宝.value)) {
			return LfAliPayPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.乐付QQ钱包.value)) {
			return LfQQPayPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.锐付网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.锐付微信支付宝.value)) {
			return RFPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.宝立付网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.宝立付微信支付宝.value)) {
			return BLPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.可可网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.可可微信支付宝.value)) {
			return KKPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.喜发网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.喜发微信支付宝.value)) {
			return XFPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.旺付通网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.旺付通微信支付宝.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.旺付通银联扫码.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.旺付通京东钱包.value)) {
			return WFTPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.亿卡云网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.亿卡云微信支付宝.value)) {
			return EKPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.聚合支付.value)) {
			return JHPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.点点支付.value)) {
			return DDPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.聚合快捷支付.value)) {
			return JHQuickPayPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.付呗支付.value)) {
			return FBPayPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.支付通网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.支付通微信支付宝.value)) {
			return ZFTWebPayPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.银盛支付宝H5.value)) {
			return YSALIPayPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.聚合收银台.value)) {
			return JHH5PayPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.星付网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.星付扫码.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.星付银联扫码.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.星付H5.value)) {
			return XingFPayPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.多多网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.多多扫码.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.多多京东.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.多多WAP.value)) {
			return DuoPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.信付通网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.信付通扫码.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.信付通京东.value)) {
			return XFTPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.畅汇网银.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.畅汇微信支付宝.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.畅汇快捷支付.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.畅汇QQ钱包.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.畅汇畅码支付.value)) {
			return CHPayment(request, object, ordermount, __etp, takeDepositRecord);
		} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇支付.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇扫码.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇京东.value)
				|| __etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇银联.value)) {
			return JH2Payment(request, object, ordermount, __etp, takeDepositRecord);
		}
		return null;
	}

	/**
	 * 通汇支付回调
	 */
	@ResponseBody
	@Token("order_no")
	@RequestMapping("/THESCallback")
	public String thESCallback(HttpServletRequest request) {
		try {

			// 支付状态
			String status = request.getParameter("trade_status");
			// 订单号
			String orderNumber = request.getParameter("order_no");

			addCallbackLog(getRequestParamters(request), "通汇支付", orderNumber, status);

			if ("success".equalsIgnoreCase(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);
				log.Info("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Info("订单号:" + orderNumber + "	获取账户解密参数...");
						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						THMerchantConfig __thmerchant = BeanToMapUtil.convertMap(__params, THMerchantConfig.class);
						// 验证参数合法性

						if (THSave.validPageNotify(request, __thmerchant.getMerchantKey())) {

							log.Info("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("通汇存款回调");
							handles.setAssignedtocode(Enum_ThirdpartyPaymentType.通汇支付.value);
							handles.setAssignedtoaccount("通汇回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Info("订单号:" + orderNumber + "	订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success";
						} else {
							log.Error("通汇回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Info("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Info("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("通汇回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 易宝回调
	 */
	@ResponseBody
	@Token("orderID")
	@RequestMapping("/LeYingCallback")
	public String leyingCallback(HttpServletRequest request) {
		try {
			// 支付状态
			String status = request.getParameter("stateCode");
			// 订单号
			String orderNumber = request.getParameter("orderID");
			addCallbackLog(getRequestParamters(request), "乐盈支付", orderNumber, status);
			if (LEYINGSaveCallBack.Enum_stateCode.处理成功.code.equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);
				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");
						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						LEYINGMerchantConfig __leyingmerchant = BeanToMapUtil.convertMap(__params,
								LEYINGMerchantConfig.class);
						// 验证参数合法性
						if (LEYINGSaveCallBack.verifyCallback(request, __leyingmerchant.getMd5key())) {
							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("乐盈存款回调");
							handles.setAssignedtocode(Enum_ThirdpartyPaymentType.乐盈支付.value);
							handles.setAssignedtoaccount("乐盈回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "200";
						} else {
							log.Error("乐盈回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "200";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("易宝回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 获取项目根目录
	 * 
	 * @return
	 */
	private String getRootPath(HttpServletRequest request, TakeDepositRecord takeDepositRecord) {
		String apiurl = getBasePath();// 默认
		try {

			// 登录时返回该品牌下（如有品牌）或该企业配置的自定义api接口域名
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("enterprisecode", takeDepositRecord.getEnterprisecode());
			params.put("brandcode", takeDepositRecord.getBrandcode());
			params.put("datastatus", EnterpriseOperatingBrandPay.Enum_Datastatus.有效.value);
			List<EnterpriseOperatingBrandPay> list = enterpriseOperatingBrandPayService.selectAll(params);

			if (list != null && list.size() > 0) {
				apiurl = list.get(0).getPaycallbackurl();
			}
			log.Info("支付域名：" + takeDepositRecord.getEnterprisecode() + " " + apiurl);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiurl;
	}

	/**
	 * 通汇存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String thpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {
		PayInterface<THMerchantConfig, THOrderConfig> __thPay = new THPayMent<THMerchantConfig, THOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		THMerchantConfig __thmerchant = BeanToMapUtil.convertMap(__agument, THMerchantConfig.class);
		__thmerchant.setPayType(Enum_payType.网银支付.value);
		__thmerchant.setReferer(request.getServerName());
		__thmerchant.setCallbackUrl(getRootPath(request, takeDepositRecord) + "/TPayment/THESCallback");

		THOrderConfig __thorder = new THOrderConfig();
		__thorder.setBankCode(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		__thorder.setOrderNo(takeDepositRecord.getOrdernumber());
		__thorder.setOrderAmount(ordermount.toString());
		__thorder.setCustomerIp(request.getServerName());

		return __thPay.save(__thmerchant, __thorder);
	}

	/**
	 * 易宝存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String yeepayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {
		PayInterface<YEEMerchantConfig, YEEOrderConfig> __yeePay = new YEEPayMent<YEEMerchantConfig, YEEOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		YEEMerchantConfig __yeemerchant = BeanToMapUtil.convertMap(__agument, YEEMerchantConfig.class);
		/*
		 * __yeemerchant.setSaveUrl(__agument.get("saveUrl").toString());
		 * __yeemerchant.setP1_MerId(__agument.get("p1_MerId").toString());
		 * __yeemerchant.setKeyValue(__agument.get("keyValue").toString());
		 */
		__yeemerchant.setP8_Url(getRootPath(request, takeDepositRecord) + "/TPayment/YeeCallback");

		YEEOrderConfig __yeeorder = new YEEOrderConfig();
		__yeeorder.setPd_FrpId(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		__yeeorder.setP2_Order(takeDepositRecord.getOrdernumber());
		__yeeorder.setP3_Amt(ordermount.toString());

		return __yeePay.save(__yeemerchant, __yeeorder);
	}

	/**
	 * 易宝存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String leyingpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {
		PayInterface<LEYINGMerchantConfig, LEYINGOrderConfig> __leyingPay = new LEYINGPayMent<LEYINGMerchantConfig, LEYINGOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		LEYINGMerchantConfig __leyingmerchant = BeanToMapUtil.convertMap(__agument, LEYINGMerchantConfig.class);
		__leyingmerchant.setReturnUrl("");
		__leyingmerchant.setNoticeUrl(getRootPath(request, takeDepositRecord) + "/TPayment/LeYingCallback");

		LEYINGOrderConfig __leyingorder = new LEYINGOrderConfig();
		__leyingorder.setSerialID(RandomString.UUID());
		__leyingorder.setSubmitTime(DateUtils.FormatDateToString(new Date(), "yyyyMMddHHmmss"));
		__leyingorder.setOrderDetails(new LEYINGOrder(takeDepositRecord.getOrdernumber(),
				ordermount.multiply(new BigDecimal("100")).toString()).toString());
		__leyingorder.setTotalAmount(ordermount.multiply(new BigDecimal("100")).toBigInteger().toString());

		return __leyingPay.save(__leyingmerchant, __leyingorder);
	}

	/**
	 * 易宝回调
	 */
	@ResponseBody
	@Token("r6_Order")
	@RequestMapping("/YeeCallback")
	public String yeeCallback(HttpServletRequest request) {
		try {
			// 支付状态
			String status = request.getParameter("r1_Code");
			// 订单号
			String orderNumber = request.getParameter("r6_Order");

			addCallbackLog(getRequestParamters(request), "易宝支付", orderNumber, status);

			if ("1".equalsIgnoreCase(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);
				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");
						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						YEEMerchantConfig __yeemerchant = BeanToMapUtil.convertMap(__params, YEEMerchantConfig.class);
						// 验证参数合法性
						if (YEESave.getParameterBeforeVerifyCallback(request, __yeemerchant.getKeyValue())) {
							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("易宝存款回调");
							handles.setAssignedtocode(Enum_ThirdpartyPaymentType.易宝支付.value);
							handles.setAssignedtoaccount("易宝回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success";
						} else {
							log.Error("易宝回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
						// 存款加入默认打码
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("易宝回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 优付存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String yompayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		PayInterface<YOMMerchantConfig, YOMOrderConfig> __yomPay = new YOMPayMent<YOMMerchantConfig, YOMOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		YOMMerchantConfig __yommerchant = new YOMMerchantConfig();

		__yommerchant.setMerKey(__agument.get("MER_KEY").toString());
		__yommerchant.setMerNo(__agument.get("MER_NO").toString());
		__yommerchant.setPayUrl(__agument.get("PAY_URL").toString());
		__yommerchant.setRefererUrl(getRootPath(request, takeDepositRecord));
		__yommerchant.setReturnUrl("");// 无
		__yommerchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/YomPayCallback");

		YOMOrderConfig __yomorder = new YOMOrderConfig();
		__yomorder.setBankCode(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));// 可选
		__yomorder.setOrderAmount(ordermount.toString());
		__yomorder.setProductName("A");// 随便写的
		__yomorder.setProductNum("1");// 随便写的
		__yomorder.setCustomerIp(request.getServerName());
		__yomorder.setCustomerAddress("B");// 随便写的
		__yomorder.setCustomerPhone("13631223000");// 必须要遵循格式，也不能不填写
		// __yomorder.setInputCharset("UTF-8");可不填写，有默认值
		__yomorder.setOrderNo(takeDepositRecord.getOrdernumber());
		__yomorder.setReturnParams("1");// 回调参数集合

		return __yomPay.save(__yommerchant, __yomorder);
	}

	/**
	 * 优付回调
	 */
	@ResponseBody
	@Token("order_no")
	@RequestMapping("/YomPayCallback")
	public String YomPayCallback(HttpServletRequest request) {
		try {

			// {order_no=9D0164E86E3445B48469AD0859E9433D, mer_no=10982, return_params=1,
			// trade_time=1475575186, order_amount=1.000, trade_status=1,
			// sign=1aec94a305a95273fbe63efeb5c373a5, trade_no=10982161004863716}
			// 支付状态
			String status = request.getParameter("trade_status");
			// 订单号
			String orderNumber = request.getParameter("order_no");

			addCallbackLog(getRequestParamters(request), "优付支付", orderNumber, status);

			if ("1".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						// YOMMerchantConfig __yommerchant = BeanToMapUtil.convertMap(__params,
						// YOMMerchantConfig.class);

						// 验证参数合法性
						if (YOMSave.checkResponseSign(request, __params.get("MER_KEY").toString())) {

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("优付存款回调");
							handles.setAssignedtocode(Enum_ThirdpartyPaymentType.优付支付.value);
							handles.setAssignedtoaccount("优付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success";// 成功时必须返回此success字段
						} else {

							log.Error("优付回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("优付回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * JPAY存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String jpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		PayInterface<JMerchantConfig, JOrderConfig> __yomPay = new JPayMent<JMerchantConfig, JOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		JMerchantConfig __jpaymerchant = new JMerchantConfig();

		__jpaymerchant.setMerKey(__agument.get("MER_KEY").toString());
		__jpaymerchant.setMerNo(__agument.get("MER_NO").toString());
		__jpaymerchant.setPayUrl(__agument.get("PAY_URL").toString());
		__jpaymerchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");//
		__jpaymerchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/JPayCallback");

		JOrderConfig __jpayorder = new JOrderConfig();
		__jpayorder.setPay_type(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));// 必填
		__jpayorder.setAgent_bill_id(takeDepositRecord.getOrdernumber());
		__jpayorder.setPay_amt(ordermount.doubleValue());
		// __jpayorder.setGoods_name(goods_name);可不填写，有默认值
		// __jpayorder.setRemark(remark);可不填写，有默认值

		return __yomPay.save(__jpaymerchant, __jpayorder);
	}

	/**
	 * JPAY回调
	 */
	@ResponseBody
	@Token("agent_bill_id")
	@RequestMapping("/JPayCallback")
	public String JPayCallback(HttpServletRequest request) {
		try {

			// {c_code=813743, res=1, pay_amt=100,
			// agent_bill_id=13B743FB45CA408C80CDC23B1DE0464A,
			// sign=9feb2026018606036f788b9417973a2d, pay_type=30,
			// jpay_bill_id=43161014163556541099}
			// res 必填 支付状态1成功其他失败
			// c_code 必填 渠道编号
			// pay_type 必填 22支付宝30微信
			// pay_amt 必填 金额单位分
			// jpay_bill_id 必填 商品名称
			// agent_bill_id 必填 订单号
			// sign 必填 MD5签名结果
			//
			// sign=md5(c_code=1234567&pay_type=30&pay_amt=100&jpay_bill_id=23423423&agent_bill_id=20100225132210&key=CC08C5E3E69F4E6B81DC0B)

			// 支付状态
			String status = request.getParameter("res");
			// 订单号
			String orderNumber = request.getParameter("agent_bill_id");

			addCallbackLog(getRequestParamters(request), "JPAY支付", orderNumber, status);

			if ("1".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());

						// 验证参数合法性
						if (JPaySignUtil.checkResponseSign(request, __params.get("MER_KEY").toString())) {

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("JPAY存款回调");
							handles.setAssignedtocode(Enum_ThirdpartyPaymentType.JPAY支付.value);
							handles.setAssignedtoaccount("JPAY回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "ok";// 成功时必须返回此ok字段

						} else {

							log.Error("JPAY回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "ok";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("JPAY回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 银宝存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String ybpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		PayInterface<YbMerchantConfig, YbOrderConfig> __yomPay = new YbPayMent<YbMerchantConfig, YbOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		YbMerchantConfig __jpaymerchant = new YbMerchantConfig();

		__jpaymerchant.setMerNo(__agument.get("partner").toString());
		__jpaymerchant.setMerKey(__agument.get("keyValue").toString());
		__jpaymerchant.setPayUrl(__agument.get("payUrl").toString());
		__jpaymerchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/YbPayCallback");

		YbOrderConfig __jpayorder = new YbOrderConfig();
		__jpayorder.setOrdernumber(takeDepositRecord.getOrdernumber());
		__jpayorder.setPaymoney(ordermount.doubleValue());
		__jpayorder.setAttach(takeDepositRecord.getEmployeecode());
		__jpayorder.setBanktype(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));

		return __yomPay.save(__jpaymerchant, __jpayorder);
	}

	/**
	 * 银宝回调
	 */
	@ResponseBody
	@Token("ordernumber")
	@RequestMapping("/YbPayCallback")
	public String YbPayCallback(HttpServletRequest request) {
		try {
			System.out
					.println("============1=====================================YbPayCallback========================="
							+ getRequestParamters(request));
			System.err
					.println("============1=====================================YbPayCallback========================="
							+ getRequestParamters(request));

			// 商户ID partner Y 商户id,由银宝商务分配
			// 商户订单号 ordernumber y 上行过程中商户系统传入的ordernumber
			// 订单结果 orderstatus Y 1:支付成功，非1为支付失败
			// 订单金额 paymoney Y 单位元（人民币）
			// 银宝商务订单号 sysnumber N 此次交易中银宝商务接口系统内的订单ID
			// 备注信息 attach N 备注信息，上行中attach原样返回
			// MD5签名 sign N 32位小写MD5签名值，GB2312编码

			// 支付状态
			String status = request.getParameter("orderstatus");
			// 订单号
			String orderNumber = request.getParameter("ordernumber");

			addCallbackLog(getRequestParamters(request), "银宝支付", orderNumber, status);

			if ("1".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());

						// 验证参数合法性
						if (JPaySignUtil.checkResponseSign(request, __params.get("keyValue").toString())) {

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("银宝存款回调");
							handles.setAssignedtocode(Enum_ThirdpartyPaymentType.银宝支付.value);
							handles.setAssignedtoaccount("银宝回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "ok";// 成功时必须返回此ok字段

						} else {

							log.Error("银宝回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "ok";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("银宝回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 乐付存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String lfpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		PayInterface<LfMerchantConfig, LfOrderConfig> __yomPay = new LfPayMent<LfMerchantConfig, LfOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		LfMerchantConfig __jpaymerchant = new LfMerchantConfig();

		__jpaymerchant.setMerNo(__agument.get("MER_NO").toString());
		__jpaymerchant.setMerKey(__agument.get("MEK_PRI_KEY").toString());
		__jpaymerchant.setMerPubKey(__agument.get("MEK_PUB_KEY").toString());
		__jpaymerchant.setMd5Key(__agument.get("MD5_KEY").toString());
		__jpaymerchant.setPubKey(__agument.get("PUBLIC_KEY").toString());
		__jpaymerchant.setPayUrl(__agument.get("payUrl").toString());
		__jpaymerchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/LfPayCallback");

		LfOrderConfig __jpayorder = new LfOrderConfig();
		__jpayorder.setOrdernumber(takeDepositRecord.getOrdernumber());
		__jpayorder.setPaymoney(ordermount.doubleValue());
		__jpayorder.setRequestTime(LfPaySignUtil.getCurrentTime());
		__jpayorder.setTransip(request.getServerName());
		__jpayorder.setBankCode(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));

		return __yomPay.save(__jpaymerchant, __jpayorder);
	}

	/**
	 * 乐付微信存款存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String lfwxpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord, Model model)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

		PayInterface<LfwxMerchantConfig, LfwxOrderConfig> __yyePay = new LfwxPayMent<LfwxMerchantConfig, LfwxOrderConfig>();
		LfwxMerchantConfig merchant = new LfwxMerchantConfig();
		merchant.setMerNo(__agument.get("MER_NO").toString());// 商户号
		merchant.setMerKey(__agument.get("MEK_PRI_KEY").toString());
		merchant.setMerPubKey(__agument.get("MEK_PUB_KEY").toString());
		merchant.setPubKey(__agument.get("PUBLIC_KEY").toString());
		merchant.setPayUrl(__agument.get("payUrl").toString());
		merchant.setReturn_url(getRootPath(request, takeDepositRecord) + "/TPayment/LfPayCallback");

		LfwxOrderConfig __yeeorder = new LfwxOrderConfig();
		__yeeorder.setOrdernumber(takeDepositRecord.getOrdernumber());
		__yeeorder.setPaymoney(Double.valueOf(ordermount.doubleValue()));
		__yeeorder.setTransip(request.getServerName());

		String url = __yyePay.save(merchant, __yeeorder);
		return url;

	}

	/**
	 * 乐付回调
	 */
	@ResponseBody
	@Token("out_trade_no")
	@RequestMapping("/LfPayCallback")
	public String LfPayCallback(HttpServletRequest request) {
		try {
			// {out_trade_no=EC7182497BC14B868ECFCA6EF8222865, request_time=2016-11-23
			// 09:00:32, input_charset=utf-8,
			// sign=K6WDXgZdxod%2BYknb8BBX8uW5DSN6790nr7VpWXsKH56ssKeZmwPEE4xte%2FiSfNKG83lviSWtIeHy%2FsM7IVYhPIIkSpM5k6lgrH064O9MsblSGwfYkJakk1Gkp1RZLWGSVhMiv%2BwIY4PhnU7p6QEvjg0yJvPml2Hd9oH5T%2BKiQRA%3D,
			// sign_type=SHA1WithRSA,
			// content=ZPXySIBnkYi6gLOaQalvfUfG477hcGvdFctuNB9MqwvZYu%2B8INsS2ppCNYERz0imHXlSeKZvFg0ovNaF1Y1jX9B8W8bg86be5%2FoCTejY6jHmy5wPhb9ZtoD5l5nNaRKaoQ701vkfzpcuO81pJfOPMXqTKUm%2FRWW4k95Ovi6v7RxCgFOrFLERH1QPdcgF1zclQwL9XCCLZTZWEka92uJ06tr9dsE8GCVq8Svmzokpe2YnZcBVMdXlGH1LwRxhZ8hXfmbYN0Z17fHe4%2FjfnH7fVvvPcs6V2UTdB6oQBMrsuQgEcVP%2B30A0TOHk0C9cLdFCn99MyuFF%2FzOuNjMD8N8f5g%2FvMGXzyYUxBnr%2BXRuKgL1pQGD%2F1ZYsDbCi0aC1an%2BEua%2FEMD4KEKQoeQeNsRsnbG%2BgtPBzacBqZubImgAMkaZsGyARaFNbJyVrS7ef1HpZgLptvZ3wTW%2FgcgAwAvgPXPBgbeAT5uTj2%2FcMAv1n%2Bpak%2F9Uh5GP8VzsLrK22YndG,
			// status=1}
			System.out
					.println("============1=====================================LfPayCallback========================="
							+ getRequestParamters(request));
			System.err
					.println("============1=====================================LfPayCallback========================="
							+ getRequestParamters(request));

			// 商户ID partner Y 商户id,由银宝商务分配
			// 商户订单号 ordernumber y 上行过程中商户系统传入的ordernumber
			// 订单结果 orderstatus Y 1:支付成功，非1为支付失败
			// 订单金额 paymoney Y 单位元（人民币）
			// 银宝商务订单号 sysnumber N 此次交易中银宝商务接口系统内的订单ID
			// 备注信息 attach N 备注信息，上行中attach原样返回
			// MD5签名 sign N 32位小写MD5签名值，GB2312编码

			// 支付状态
			String status = request.getParameter("status");//
			// 订单号
			String orderNumber = request.getParameter("out_trade_no");

			addCallbackLog(getRequestParamters(request), "乐付支付", orderNumber, status);

			if ("1".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());

						// 验证参数合法性
						if (LfPaySignUtil.checkResponseSign(request, __params)) {

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("乐付回调");
							handles.setAssignedtocode(Enum_ThirdpartyPaymentType.乐付支付.value);
							handles.setAssignedtoaccount("乐付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success";// 成功时必须返回此success字段

						} else {

							log.Error("乐付回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("乐付回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 新贝存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String xbpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		// String RequestURL = request.getHeader("Referer");

		PayInterface<XBMerchantConfig, XBOrderConfig> __yomPay = new XBPayMent<XBMerchantConfig, XBOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		XBMerchantConfig __jpaymerchant = new XBMerchantConfig();

		__jpaymerchant.setMerKey(__agument.get("MEK_PRI_KEY").toString());
		__jpaymerchant.setMerNo(__agument.get("MER_NO").toString());
		__jpaymerchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/XbPayCallback");
		__jpaymerchant.setPayUrl(__agument.get("payUrl").toString());
		__jpaymerchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");// 支付完成后页面跳转地址

		XBOrderConfig __jpayorder = new XBOrderConfig();
		__jpayorder.setBankCode(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		__jpayorder.setOrderAmount(ordermount.doubleValue() + "");
		__jpayorder.setTradeIp(takeDepositRecord.getTraceip());
		__jpayorder.setOrderNo(takeDepositRecord.getOrdernumber());
		__jpayorder.setOrderDate(LfPaySignUtil.getCurrentTime());

		return __yomPay.save(__jpaymerchant, __jpayorder);
	}

	/**
	 * 新贝回调
	 */
	@ResponseBody
	@Token("OrderId")
	@RequestMapping("/XbPayCallback")
	public String XbPayCallback(HttpServletRequest request) {
		try {
			// {out_trade_no=EC7182497BC14B868ECFCA6EF8222865, request_time=2016-11-23
			// 09:00:32, input_charset=utf-8,
			// sign=K6WDXgZdxod%2BYknb8BBX8uW5DSN6790nr7VpWXsKH56ssKeZmwPEE4xte%2FiSfNKG83lviSWtIeHy%2FsM7IVYhPIIkSpM5k6lgrH064O9MsblSGwfYkJakk1Gkp1RZLWGSVhMiv%2BwIY4PhnU7p6QEvjg0yJvPml2Hd9oH5T%2BKiQRA%3D,
			// sign_type=SHA1WithRSA,
			// content=ZPXySIBnkYi6gLOaQalvfUfG477hcGvdFctuNB9MqwvZYu%2B8INsS2ppCNYERz0imHXlSeKZvFg0ovNaF1Y1jX9B8W8bg86be5%2FoCTejY6jHmy5wPhb9ZtoD5l5nNaRKaoQ701vkfzpcuO81pJfOPMXqTKUm%2FRWW4k95Ovi6v7RxCgFOrFLERH1QPdcgF1zclQwL9XCCLZTZWEka92uJ06tr9dsE8GCVq8Svmzokpe2YnZcBVMdXlGH1LwRxhZ8hXfmbYN0Z17fHe4%2FjfnH7fVvvPcs6V2UTdB6oQBMrsuQgEcVP%2B30A0TOHk0C9cLdFCn99MyuFF%2FzOuNjMD8N8f5g%2FvMGXzyYUxBnr%2BXRuKgL1pQGD%2F1ZYsDbCi0aC1an%2BEua%2FEMD4KEKQoeQeNsRsnbG%2BgtPBzacBqZubImgAMkaZsGyARaFNbJyVrS7ef1HpZgLptvZ3wTW%2FgcgAwAvgPXPBgbeAT5uTj2%2FcMAv1n%2Bpak%2F9Uh5GP8VzsLrK22YndG,
			// status=1}

			// Version 网关版本号 4 使用网关的版本号 否
			// State 处理结果 4 处理后的结果编码 否
			// Message 处理结果信息 256 处理后的结果信息 可空
			// OrderId 商户订单号 30 商户自己业务逻辑的订单

			// 支付状态 8888 提交成功/充值成功
			String status = request.getParameter("State");//
			// 订单号
			String orderNumber = request.getParameter("OrderId");

			addCallbackLog(getRequestParamters(request), "新贝支付", orderNumber, status);

			if ("8888".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());

						// 验证参数合法性
						if (XBSave.checkResponseSign(request, __params.get("MEK_PRI_KEY").toString())) {

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("新贝网银回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("新贝回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "OK";// 成功时必须返回此success字段

						} else {

							log.Error("新贝回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "OK";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("新贝回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 国盛通存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String gstpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		String RequestURL = request.getHeader("Referer");

		PayInterface<GSTMerchantConfig, GSTOrderConfig> __yomPay = new GSTPayMent<GSTMerchantConfig, GSTOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		GSTMerchantConfig merchant = new GSTMerchantConfig();

		merchant.setMerKey(__agument.get("MEK_PRI_KEY").toString());
		merchant.setMerNo(__agument.get("MER_NO").toString());
		merchant.setPayUrl(__agument.get("payUrl").toString());
		merchant.setRefererUrl(RequestURL);
		merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");
		merchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/GstPayCallback");

		GSTOrderConfig order = new GSTOrderConfig();
		order.setBankCode(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		order.setOrderAmount(ordermount.doubleValue() + "");
		order.setCustomerIp(takeDepositRecord.getTraceip());
		order.setOrderNo(takeDepositRecord.getOrdernumber());

		/*************
		 * 国盛通需要paytype参数，分别 1：网银支付 2：微信 3：支付宝
		 ***************************************************************************/
		if (order.getBankCode().equals("WEIXIN")) {
			order.setPaytype("2");
		} else if (order.getBankCode().equals("ZHIFUBAO")) {
			order.setPaytype("3");
		} else {
			order.setPaytype("1");
		}

		return __yomPay.save(merchant, order);
	}

	/**
	 * 国盛通回调
	 */
	@ResponseBody
	@Token("order_no")
	@RequestMapping("/GstPayCallback")
	public String GstPayCallback(HttpServletRequest request) {
		try {
			// {out_trade_no=EC7182497BC14B868ECFCA6EF8222865, request_time=2016-11-23
			// 09:00:32, input_charset=utf-8,
			// sign=K6WDXgZdxod%2BYknb8BBX8uW5DSN6790nr7VpWXsKH56ssKeZmwPEE4xte%2FiSfNKG83lviSWtIeHy%2FsM7IVYhPIIkSpM5k6lgrH064O9MsblSGwfYkJakk1Gkp1RZLWGSVhMiv%2BwIY4PhnU7p6QEvjg0yJvPml2Hd9oH5T%2BKiQRA%3D,
			// sign_type=SHA1WithRSA,
			// content=ZPXySIBnkYi6gLOaQalvfUfG477hcGvdFctuNB9MqwvZYu%2B8INsS2ppCNYERz0imHXlSeKZvFg0ovNaF1Y1jX9B8W8bg86be5%2FoCTejY6jHmy5wPhb9ZtoD5l5nNaRKaoQ701vkfzpcuO81pJfOPMXqTKUm%2FRWW4k95Ovi6v7RxCgFOrFLERH1QPdcgF1zclQwL9XCCLZTZWEka92uJ06tr9dsE8GCVq8Svmzokpe2YnZcBVMdXlGH1LwRxhZ8hXfmbYN0Z17fHe4%2FjfnH7fVvvPcs6V2UTdB6oQBMrsuQgEcVP%2B30A0TOHk0C9cLdFCn99MyuFF%2FzOuNjMD8N8f5g%2FvMGXzyYUxBnr%2BXRuKgL1pQGD%2F1ZYsDbCi0aC1an%2BEua%2FEMD4KEKQoeQeNsRsnbG%2BgtPBzacBqZubImgAMkaZsGyARaFNbJyVrS7ef1HpZgLptvZ3wTW%2FgcgAwAvgPXPBgbeAT5uTj2%2FcMAv1n%2Bpak%2F9Uh5GP8VzsLrK22YndG,
			// status=1}
			System.out
					.println("============1=====================================GstPayCallback========================="
							+ getRequestParamters(request));
			System.err
					.println("============1=====================================GstPayCallback========================="
							+ getRequestParamters(request));

			// success 交易成功
			// failed 交易失败
			// paying 交易中

			// 支付状态
			String status = request.getParameter("trade_status");//
			// 订单号
			String orderNumber = request.getParameter("order_no");

			addCallbackLog(getRequestParamters(request), "国盛通支付", orderNumber, status);

			if ("success".equals(status.toLowerCase())) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());

						// 验证参数合法性
						if (GSTSave.checkResponseSign(request, __params.get("MEK_PRI_KEY").toString())) {
							String payTypeName = "国盛通";
							String payTypeCode = request.getParameter(GSTAppConstants.p2_return_params);
							if (payTypeCode != null) {
								if (payTypeCode.equals(GST_PayType.网银支付.value))
									payTypeName += "网银支付";
								if (payTypeCode.equals(GST_PayType.微信支付.value))
									payTypeName += "微信支付";
								if (payTypeCode.equals(GST_PayType.支付宝支付.value))
									payTypeName += "支付宝支付";
							}

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc(payTypeName + "回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount(payTypeName + "回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success";// 成功时必须返回此success字段

						} else {

							log.Error("国盛通回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("国盛通回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 捷付通存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String jftpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		// String RequestURL = StringUtil.getURLDomain( request.getHeader("Referer") );

		PayInterface<JFTMerchantConfig, JFTOrderConfig> __yomPay = new JFTPayMent<JFTMerchantConfig, JFTOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		JFTMerchantConfig __jpaymerchant = new JFTMerchantConfig();

		__jpaymerchant.setMerKey(__agument.get("MEK_PRI_KEY").toString());
		__jpaymerchant.setMerNo(__agument.get("MER_NO").toString());
		__jpaymerchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/JftPayCallback");
		__jpaymerchant.setPayUrl(__agument.get("payUrl").toString());
		__jpaymerchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");// 支付完成后页面跳转地址

		JFTOrderConfig __jpayorder = new JFTOrderConfig();
		__jpayorder.setBankCode(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		__jpayorder.setOrderAmount(ordermount.doubleValue() + "");
		__jpayorder.setCustomerIp(takeDepositRecord.getTraceip());
		__jpayorder.setOrderNo(takeDepositRecord.getOrdernumber());

		return __yomPay.save(__jpaymerchant, __jpayorder);
	}

	/**
	 * 捷付通回调
	 */
	@ResponseBody
	@Token("OrdId")
	@RequestMapping("/JftPayCallback")
	public String JftPayCallback(HttpServletRequest request) {
		try {

			// OrdId=DD20815466154265A8F48D3EAE56EF3C, OrdAmt=2.00, _input_charset=utf-8,
			// SignInfo=95b25f83d21dbb5953832ea2dea9e8d9, MerId=5900696,
			// OrdNo=DTDP2017022010423378739075, ResultCode=success002, SignType=MD5,
			// Remark=remark
			// success002表示成功

			// 支付状态
			String status = request.getParameter("ResultCode");//
			// 订单号
			String orderNumber = request.getParameter("OrdId");

			addCallbackLog(getRequestParamters(request), "捷付通支付", orderNumber, status);

			if ("success002".equals(status.toLowerCase())) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());

						// 验证参数合法性
						if (JFTSave.checkResponseSign(request, __params.get("MEK_PRI_KEY").toString())) {

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("捷付通回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("捷付通回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success|9999";// 成功时必须返回此success字段

						} else {

							log.Error("捷付通回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "success|9999";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("捷付通回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 智付网银 存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String zftpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		// String RequestURL = StringUtil.getURLDomain( request.getHeader("Referer") );

		PayInterface<ZfMerchantConfig, ZfOrderConfig> __yomPay = new ZfPayMent<ZfMerchantConfig, ZfOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		ZfMerchantConfig __jpaymerchant = new ZfMerchantConfig();

		__jpaymerchant.setMerNo(__agument.get("MER_NO").toString());
		__jpaymerchant.setMerKey(__agument.get("MEK_PRI_KEY").toString());
		__jpaymerchant.setMerPubKey(__agument.get("MEK_PUB_KEY").toString());
		__jpaymerchant.setPubKey(__agument.get("PUBLIC_KEY").toString());
		__jpaymerchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/ZfPayCallback");
		__jpaymerchant.setPayUrl(__agument.get("payUrl").toString());
		__jpaymerchant.setPageUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");// 支付完成后页面跳转地址

		ZfOrderConfig __jpayorder = new ZfOrderConfig();
		__jpayorder.setBankCode(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		__jpayorder.setOrdernumber(takeDepositRecord.getOrdernumber());
		__jpayorder.setPaymoney(takeDepositRecord.getOrderamount().doubleValue());

		return __yomPay.save(__jpaymerchant, __jpayorder);
	}

	/**
	 * 智付微信支付宝 存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String zfwxtpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		// String RequestURL = StringUtil.getURLDomain( request.getHeader("Referer") );

		PayInterface<ZfWXMerchantConfig, ZfWXOrderConfig> __yyePay = new ZfWXPayMent<ZfWXMerchantConfig, ZfWXOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		ZfWXMerchantConfig __jpaymerchant = new ZfWXMerchantConfig();

		__jpaymerchant.setMerNo(__agument.get("MER_NO").toString());
		__jpaymerchant.setMerKey(__agument.get("MEK_PRI_KEY").toString());
		__jpaymerchant.setMerPubKey(__agument.get("MEK_PUB_KEY").toString());
		__jpaymerchant.setPubKey(__agument.get("PUBLIC_KEY").toString());
		__jpaymerchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/ZfPayCallback");
		__jpaymerchant.setPayUrl(__agument.get("payUrl").toString());

		ZfWXOrderConfig __jpayorder = new ZfWXOrderConfig();
		__jpayorder.setBankCode(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		__jpayorder.setOrdernumber(takeDepositRecord.getOrdernumber());
		__jpayorder.setPaymoney(takeDepositRecord.getOrderamount().doubleValue());
		__jpayorder.setTransip("192.168.1.0");

		return __yyePay.save(__jpaymerchant, __jpayorder);
	}

	/**
	 * 智付网银回调
	 */
	@ResponseBody
	@Token("order_no")
	@RequestMapping("/ZfPayCallback")
	public String ZfPayCallback(HttpServletRequest request) {
		try {
			System.out
					.println("============1=====================================ZfPayCallback========================="
							+ getRequestParamters(request));
			System.err
					.println("============1=====================================ZfPayCallback========================="
							+ getRequestParamters(request));

			// OrdId=DD20815466154265A8F48D3EAE56EF3C, OrdAmt=2.00, _input_charset=utf-8,
			// SignInfo=95b25f83d21dbb5953832ea2dea9e8d9, MerId=5900696,
			// OrdNo=DTDP2017022010423378739075, ResultCode=success002, SignType=MD5,
			// Remark=remark
			// success002表示成功

			// 支付状态
			String status = request.getParameter("trade_status");//
			// 订单号
			String orderNumber = request.getParameter("order_no");

			addCallbackLog(getRequestParamters(request), "智付网银", orderNumber, status);

			if ("SUCCESS".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());

						// 验证参数合法性
						if (ZfPaySignUtil.checkResponseSign(request, __params)) {

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("智付网银回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("智付网银回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "SUCCESS";// 成功时必须返回此success字段

						} else {

							log.Error("智付网银回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "SUCCESS";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("智付网银回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * SD网银 存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String sdpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		String RequestURL = StringUtil.getURLDomain(request.getHeader("Referer"));
		RequestURL = RequestURL + "?s=" + System.currentTimeMillis();

		PayInterface<SDWXMerchantConfig, SDWXOrderConfig> __yomPay = new SDWXPayMent<SDWXMerchantConfig, SDWXOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

		boolean IsMobile = WebInfoHandle.checkAgentIsMobile(request);
		String paytype = "6006";
		if (IsMobile) {
			paytype = SDWXPayAppConstants.Enum_PaymentType.手机.value;
		} else {
			paytype = SDWXPayAppConstants.Enum_PaymentType.手机无插件.value;
		}

		SDWXMerchantConfig merchant = new SDWXMerchantConfig(__agument.get("MER_NO").toString(),
				getRootPath(request, takeDepositRecord) + "/TPayment/SDPayCallback",
				getRootPath(request, takeDepositRecord) + "/TPayment/success",

				String.valueOf(__agument.get("payUrl6006")), String.valueOf(__agument.get("payUrl6009")),
				String.valueOf(__agument.get("payUrl6010")), __agument.get("key1").toString(),
				__agument.get("key2").toString(), __agument.get("md5key").toString());

		SDWXOrderConfig __yeeorder = new SDWXOrderConfig(takeDepositRecord.getOrdernumber(),
				takeDepositRecord.getEmployeecode(), takeDepositRecord.getOrderamount().toString(), null, paytype);

		return __yomPay.save(merchant, __yeeorder);
	}

	/**
	 * SD微信、支付宝 存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String sdpayment2(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		String RequestURL = StringUtil.getURLDomain(request.getHeader("Referer"));
		RequestURL = RequestURL + "?s=" + System.currentTimeMillis();

		PayInterface<SDWXMerchantConfig, SDWXOrderConfig> __yomPay = new SDWXPayMent<SDWXMerchantConfig, SDWXOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

		SDWXMerchantConfig merchant = new SDWXMerchantConfig(__agument.get("MER_NO").toString(),
				getRootPath(request, takeDepositRecord) + "/TPayment/SDPayCallback",
				getRootPath(request, takeDepositRecord) + "/TPayment/success",

				String.valueOf(__agument.get("payUrl6006")), "", "", __agument.get("key1").toString(),
				__agument.get("key2").toString(), __agument.get("md5key").toString());

		SDWXOrderConfig __yeeorder = new SDWXOrderConfig(takeDepositRecord.getOrdernumber(),
				takeDepositRecord.getEmployeecode(), takeDepositRecord.getOrderamount().toString(), null, "6006");

		return __yomPay.save(merchant, __yeeorder);
	}

	/**
	 * SD支付回调
	 */
	private static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";

	@SuppressWarnings("unchecked")
	@RequestMapping("/SDPayCallback")
	@ResponseBody
	public String SDPayCallback(HttpServletRequest request) {
		try {
			System.out
					.println("============1=====================================SDPayCallback========================="
							+ getRequestParamters(request));
			System.err
					.println("============1=====================================SDPayCallback========================="
							+ getRequestParamters(request));

			// {res=4zWjqNN9KqLYry2stg73evIWlI7D++o3hPLLxxElUbMe+Umb/05xLF1W2/C+/CH7OO7YbhvqHVwMcbWB1mcJxtJbtjJHhzGJA2jOtQ4gpE7byz7hivsvuTC1tNWFdAJPOuolMaIc/LsO/DMj6J8gkNL+DMjwqWPan2ZqAQAyDjZtFHIZCg2BdbSeXlu2MR8Kz7xh+bVVoslc9CLeFonVxu8mLpWCJ0F8q0oU5evJw2oDVhhj25y4pSMzG+oTilt9Op+p+0CzCrXV+54miZ/g6N6uSIBu72J0OvjLID9khiH7LzzvbeGm14ChHJTi36Owp9+xmy76+y+iO0cnccBY2bQV2UMnLNvP8U8gSvtPAbNAXEmoOMy8P1LWK8zjmto1g+IJN4kmK7b6lWC6lhLfV+bS4u8oqEr76J6TpTkkTy6gz+Xy5MhU8WIhgnt4LHd8o6OKLDYf6qZB4f+5A2WArQnfEcumVzG5rcIiOebFujg=,
			// pid=EG9783}

			// <message>
			// <cmd>6007</cmd>
			// <merchantid>CS9904</merchantid>
			// <order>635474128841458461</order>
			// <username>test1001</username>
			// <money>1.00</money>
			// <unit>1</unit>
			// <time>2014-09-27 11:08:31</time>
			// <call>server</call>
			// <result>1</result> //等待: 0, 完成: 1, 失败: 2
			// <remark>Test Pay</remark>
			// </message>

			String pid = request.getParameter("pid");// 商户号

			addCallbackLog(getRequestParamters(request), "SD出款", pid, "Unknown");

			EnterpriseThirdpartyPaymentAgument agument = new EnterpriseThirdpartyPaymentAgument();
			agument.setAgumentvalue(pid);// 根据商户号查找
			agument = enterpriseThirdpartyPaymentAgumentService.selectFirst(agument);
			if (agument != null) {

				String enterprisethirdpartycode = agument.getEnterprisethirdpartycode();
				Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
						.takeEnterprisePayAgument(enterprisethirdpartycode);

				Map<String, Object> result = SDWXPaySignUtil.checkResponseSign(request, __params);
				if (Boolean.valueOf(result.get("verify").toString())) {

					Map<String, String> data = (Map<String, String>) result.get("data");
					String orderNumber = data.get("order");
					String status = data.get("result");// 等待: 0, 完成: 1, 失败: 2
					if (status.equals("1")) {

						// 根据支付成功返回的订单号查询
						TakeDepositRecord __record = takeDepositRecoredService
								.findOrderNumberTakeDepositRecord(orderNumber);

						log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
						if (__record != null) {

							StringBuffer xmldata = new StringBuffer();
							xmldata.append(XML_HEADER);
							xmldata.append("<message>");
							xmldata.append("<cmd>").append("60071").append("</cmd>");
							xmldata.append("<merchantid>").append(pid).append("</merchantid>");
							xmldata.append("<order>").append(orderNumber).append("</language>");
							xmldata.append("<username>").append(data.get("username").toString()).append("</username>");
							xmldata.append("<result>").append("100").append("</money>");
							xmldata.append("</message>");

							String xml = xmldata.toString();

							if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {

								log.Debug("订单号:" + orderNumber + "	解密校验通过...");

								DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
								handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
								handles.setAuditdesc("SD支付回调");
								handles.setAssignedtocode(__record.getEnterprisepaymentbank());
								handles.setAssignedtoaccount("SD支付回调");
								depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
								log.Debug("订单号:" + orderNumber + "	订单审核成功");
								// 存款加入默认打码
								this.activityBetRecordService.addDepositBetRecord(__record);

								/************************ 回调后执行充值返利活动处理 ************************/
								saveingActivityVerify(__record, handles);

								// return "SUCCESS";//成功时必须返回此success字段
								return xml;
							} else {
								log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
								// return "SUCCESS";
								return xml;
							}

						} else {
							log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
						}
					} else {
						log.Error("SD回调失败，支付状态为:" + status);
					}
				} else {
					log.Error("SD回调延签失败，商户号:" + pid);
				}

			} else {
				log.Error("SD回调处理失败，无法根据商户号查找到支付配置参数信息。商户号:" + pid);
			}

		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * SD出款接口回调
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping("/SDPayoutCallback")
	public String SDPayoutCallback(HttpServletRequest request) {
		try {
			System.out.println(
					"============1=====================================SDPayoutCallback========================="
							+ getRequestParamters(request));
			System.err.println(
					"============1=====================================SDPayoutCallback========================="
							+ getRequestParamters(request));

			/***********************************************
			 * 
			 * 注意：SD的出款和通汇的出款一样，都不处理成功之后的回调。都只处理提交申请时成功的状态，当即更新状态
			 * 
			 ************************************************/
			if (true) {
				String returnstr = "<span id=\"resultLable\">Success</span>";
				return returnstr;// 成功时必须返回此success字段
			}

			Map<String, String> data = SDWXPaySignUtil.payoutCallback(request, getRequestParamters(request));// 解密得到参数数据

			if (data != null) {

				String orderNumber = data.get("SerialNumber");// 订单号
				String RecordsState = data.get("RecordsState");// 讯息状态(0未处理，1正在处理，2成功，3失败，4其他)

				addCallbackLog(getRequestParamters(request), "SD出款", orderNumber, RecordsState);

				if (RecordsState.equals("2")) {// 付款成功

					log.Error("SD出款接口回调处理成功，单号：" + orderNumber);
					return "Success";// 成功时必须返回此success字段

					/***********************************************
					 * 注意：SD的出款和通汇的出款一样，都不处理成功之后的回调。都只处理提交申请时成功的状态，当即更新状态
					 * 
					 * 
					 * 
					 * //根据支付成功返回的订单号查询 TakeDepositRecord __record =
					 * takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);
					 * 
					 * log.Debug("订单号:"+orderNumber+" 查询取款记录:"+__record); if(__record!=null){
					 * 
					 * if(__record.getOrderstatus()==((byte)Enum_orderstatus.审核中.value)){
					 * 
					 * log.Debug("订单号:"+orderNumber+" 解密校验通过...");
					 * 
					 * log.Error("SD出款接口回调处理成功，单号："+orderNumber);
					 * 
					 * return returnstr;//成功时必须返回此success字段 }else{ log.Debug("订单号:"+orderNumber+"
					 * 该笔订单已审核,重复请求无效"); return returnstr; }
					 * 
					 * }else{ log.Debug("订单号:"+orderNumber+" 该笔订单不存在"); }
					 ***************************/
				} else {
					log.Error("SD出款接口回调处理失败，因状态为:" + RecordsState);
				}

			} else {
				log.Error("SD出款接口回调处理失败，无法进行解密操作。得到data值为空");
			}

		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * SD P2P 存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String sdp2ppayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord, Model model)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		AttrCheckout.checkout(object, false, new String[] { "paymenttypebankcode" });

		if (object.get("paymenttypebankcode") == null) {
			model.addAttribute("message", "获取银行卡信息错误：未选择付款银行");
			return "/error/error";
		}

		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

		// 获取客户站点的请求URL地址
		String RequestURL = StringUtil.getURLDomain(request.getHeader("Referer"));
		RequestURL = RequestURL + "?s=" + System.currentTimeMillis();

		SDP2PMerchantConfig merchantConfig = new SDP2PMerchantConfig();
		merchantConfig.setKey1(__agument.get("Key1").toString());
		merchantConfig.setKey2(__agument.get("Key2").toString());
		merchantConfig.setLoginAccount(__agument.get("LoginAccount").toString());
		merchantConfig.setWebServiceUrl(__agument.get("WebServiceUrl").toString());

		SDP2POrderConfig orderConfig = new SDP2POrderConfig();
		orderConfig.setBank(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		orderConfig.setMoney(ordermount.toString());
		orderConfig.setOrderNo(takeDepositRecord.getOrdernumber());
		orderConfig.setUserName(takeDepositRecord.getLoginaccount());
		orderConfig.setsPlayersId(takeDepositRecord.getLoginaccount());

		Map<String, String> result = SDP2PPaySignUtil.SubmitRequest(merchantConfig, orderConfig);
		if (result.containsKey("error")) {
			model.addAttribute("message", result.get("error"));
			return "/error/error";
		}

		// 修改订单
		String ePrice = result.get("ePrice").toString();// 这是实际要求客人付款的金额，小数点
		takeDepositRecord.setOrderamount(new BigDecimal(ePrice));
		takeDepositRecord.setOrdercomment("SD的P2P转账要求客人付款的包含小数点金额");
		takeDepositRecoredService.updateTakeDepositRecord(takeDepositRecord);

		model.addAttribute("result", result);
		model.addAttribute("takeDepositRecord", takeDepositRecord);

		return "/payment/show_p2p";
	}

	/**
	 * SD的P2P回调
	 */
	@ResponseBody
	@RequestMapping("/SDP2PCallback")
	public String SDP2PCallback(HttpServletRequest request) {
		try {
			System.out
					.println("============1=====================================SDP2PCallback========================="
							+ getRequestParamters(request));
			System.err
					.println("============1=====================================SDP2PCallback========================="
							+ getRequestParamters(request));

			String HiddenField1 = request.getParameter("HiddenField1");

			// 金蛋的SD网银P2P存款配置主键
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument("EP00003N");
			System.out
					.println("============2=====================================SDP2PCallback========================="
							+ __agument);

			Map<String, String> result = SDP2PPaySignUtil.DecryResponse(HiddenField1, __agument.get("Key1").toString(),
					__agument.get("Key2").toString());

			// <t_savingApply><id>7033793</id><storeOrderId>328EE05ACA784E4792921E022B59C5F3</storeOrderId><sBankAccount></sBankAccount><sBank1>icbc</sBank1><sBank2></sBank2><sName>kaixin0909</sName><sPrice>115.00</sPrice><sPlayersId>kaixin0909</sPlayersId><eBank>ICBC</eBank><eBank2>深圳公明支行</eBank2><eName>周志刚</eName><eBankAccount>6212264000071671206</eBankAccount><ePrice>115.39</ePrice><ePoundage>0.00</ePoundage><eProvince>UYx3TM0zmVah6ZUkWvCAA5pZtnhbtjK1fyeZsDK3vBEfqgtARKQ2YBb8gVbbnQXo</eProvince><ecity>UfHguW31W4NbcdxL6j3c1zV810xnfMEOSlfkOxnRVb+1UDiz9vcT1Q==</ecity><storeId>411</storeId><storename>RH00411906</storename><state>1</state><matchingDate>2017-05-10
			// 17:51:06</matchingDate><date>2017-05-10
			// 17:43:22</date><SendOrNot>0</SendOrNot><SendTimes>3</SendTimes><Approach>2</Approach><matchingInfoId>5001956</matchingInfoId><Fees>0.00000</Fees><ip>52.175.30.174</ip><CompareMode>1</CompareMode><pushTime>2017-05-10
			// 17:43:22</pushTime><email>11@gg.com</email></t_savingApply>

			// 支付状态
			String status = result.get("state");// (等待: 0; 成功: 1; 失败: 2)
			// 订单号
			String orderNumber = result.get("storeOrderId");

			String returnstr = "<span id=\"resultLable\">Success</span>";

			addCallbackLog(getRequestParamters(request), "SD-P2P", orderNumber, status);
			if ("1".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {

						log.Error("订单号:" + orderNumber + "	解密校验通过...");
						DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
						handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
						handles.setAuditdesc("SD P2P网银回调");
						handles.setAssignedtocode(__record.getEnterprisepaymentbank());
						handles.setAssignedtoaccount("SD P2P网银回调");
						depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
						log.Debug("订单号:" + orderNumber + "	订单审核成功");
						// 存款加入默认打码
						this.activityBetRecordService.addDepositBetRecord(__record);

						/************************ 回调后执行充值返利活动处理 ************************/
						saveingActivityVerify(__record, handles);

						return returnstr;// 成功时必须返回此success字段

					} else {
						log.Error("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return returnstr;
					}
				} else {
					log.Error("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("SD P2P网银回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 牛付网关存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String nfpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		// String RequestURL = StringUtil.getURLDomain( request.getHeader("Referer") );

		PayInterface<NfMerchantConfig, NfOrderConfig> __yyePay = new NfPayMent<NfMerchantConfig, NfOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		NfMerchantConfig merchant = new NfMerchantConfig();
		merchant.setMerNo(__agument.get("MER_NO").toString());
		merchant.setMerKey(__agument.get("MEK_KEY").toString());

		merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/NfPayCallback");
		merchant.setPageUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");// 支付完成后页面跳转地址
		merchant.setPayUrl(__agument.get("payUrl").toString());

		NfOrderConfig __yeeorder = new NfOrderConfig();
		__yeeorder.setAmount(ordermount.doubleValue());
		__yeeorder.setBankId(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		// __yeeorder.setCreditType(creditType);默认
		__yeeorder.setOrderId(takeDepositRecord.getOrdernumber());

		// 00:WAP手机银行支付模式
		// 01:网银支付模式
		// 02:委托银行扣款模式
		// 03:客户端集成银行客户端程序模式
		// 04:客户端调用银行WAP页面支付模式
		// 05:手机网页支付模式

		boolean IsMobile = WebInfoHandle.checkAgentIsMobile(request);
		if (IsMobile) {
			__yeeorder.setPayMode("00");
		} else {
			__yeeorder.setPayMode("01");
		}

		return __yyePay.save(merchant, __yeeorder);
	}

	/**
	 * 牛付回调
	 */
	@ResponseBody
	@Token("orderId")
	@RequestMapping("/NfPayCallback")
	public String NfPayCallback(HttpServletRequest request) {
		try {
			System.out
					.println("============1=====================================NfPayCallback========================="
							+ getRequestParamters(request));
			System.err
					.println("============1=====================================NfPayCallback========================="
							+ getRequestParamters(request));

			// 支付状态 S ：成功 F ： 失败 U ： 交易不确定
			String status = request.getParameter("result");//
			// 订单号
			String orderNumber = request.getParameter("orderId");

			addCallbackLog(getRequestParamters(request), "牛付支付", orderNumber, status);

			if ("S".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());

						// 验证参数合法性
						if (NfPaySignUtil.checkResponseSign(request, __params)) {
							/**/
							// 牛付的实际付款金额可能与当初的金额不一样，是个随机数
							String amount = request.getParameter("amount");
							if (__record.getOrderamount().doubleValue() > Double.valueOf(amount)) {
								__record.setOrdercomment(
										"充值" + __record.getOrderamount().toString() + "但实收" + amount + "元");
								__record.setOrderamount(new BigDecimal(amount));
								takeDepositRecoredService.updateTakeDepositRecord(__record);
							}

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("牛付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("牛付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success|9999";// 成功时必须返回此success字段

						} else {

							log.Error("牛付回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "success|9999";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("牛付回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 牛付微信和支付宝存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String nfwxpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		// String RequestURL = StringUtil.getURLDomain( request.getHeader("Referer") );

		PayInterface<NfWXMerchantConfig, NfWXOrderConfig> __yyePay = new NfWXPayMent<NfWXMerchantConfig, NfWXOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		NfWXMerchantConfig merchant = new NfWXMerchantConfig();
		merchant.setMerNo(__agument.get("MER_NO").toString());
		merchant.setMerKey(__agument.get("MEK_KEY").toString());

		merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/NfPayCallback");
		merchant.setPageUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");// 支付完成后页面跳转地址
		merchant.setPayUrl(__agument.get("payUrl").toString());

		NfWXOrderConfig __yeeorder = new NfWXOrderConfig();
		__yeeorder.setAmount(ordermount.doubleValue());
		__yeeorder.setBankId(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		// __yeeorder.setCreditType(creditType);默认
		__yeeorder.setOrderId(takeDepositRecord.getOrdernumber());

		// 09:扫码支付
		// 10:公众号支付
		// 10: 微信app
		// 10:支付宝wap
		// 10：微信公众号

		// 微信扫码：WECHAT
		// 支付宝扫码：ALIPAY
		// 微信app：WECHATAPP
		// 微信公众号：WECHATPUBLIC
		// 支付宝wap: ALIPAYWAP

		boolean IsMobile = WebInfoHandle.checkAgentIsMobile(request);
		if (IsMobile) {
			if (String.valueOf(object.get("paymenttypebankcode")).equals("ALIPAYWAP")) {
				__yeeorder.setPayMode("10");
			} else {
				__yeeorder.setPayMode("09");
			}
		} else {
			__yeeorder.setPayMode("09");
		}

		return __yyePay.save(merchant, __yeeorder);
	}

	/**
	 * 极付存款
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String jfpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		// String RequestURL = StringUtil.getURLDomain( request.getHeader("Referer") );

		PayInterface<JEANPAYMerchantConfig, JEANPAYOrderConfig> __yyePay = new JEANPAYPayMent<JEANPAYMerchantConfig, JEANPAYOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		JEANPAYMerchantConfig merchant = new JEANPAYMerchantConfig();
		merchant.setMerNo(__agument.get("MER_NO").toString());
		merchant.setMerKey(__agument.get("MER_KEY").toString());

		merchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/JfPayCallback");
		merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");// 支付完成后页面跳转地址
		merchant.setPayUrl(__agument.get("payUrl").toString());

		JEANPAYOrderConfig __yeeorder = new JEANPAYOrderConfig();
		__yeeorder.setOrderAmount(ordermount.doubleValue() + "");
		__yeeorder.setBankCode(String.valueOf(
				object.get("paymenttypebankcode") == null ? "QPAY_UNIONPAY" : object.get("paymenttypebankcode")));
		__yeeorder.setOrderNo(takeDepositRecord.getOrdernumber());
		// PTY_ONLINE_PAY为单笔订单支付接口；
		// PTY_TRADE_QUERY为单笔订单查询接口；
		// PTY_PAYMENT_TO_CARD为提现到银行卡接口；
		// PTY_PAYMENT_QUERY为提现订单查询接口；
		// PTY_ACCOUNT_BALANCE为账户余额查询接口；
		__yeeorder.setServiceName("PTY_ONLINE_PAY");

		// BANK_PAY=网银支付，QUICK_PAY=快捷支付，WXPAY=微信扫码支付，ALIPAY=支付宝扫码支付
		__yeeorder.setPaytype("BANK_PAY");

		if (__yeeorder.getBankCode().equals("QPAY_UNIONPAY")) {//
			__yeeorder.setPaytype("QUICK_PAY");
		} else if (__yeeorder.getBankCode().equals("WEIXIN_QRCODE")) {//
			__yeeorder.setPaytype("WXPAY");
		} else if (__yeeorder.getBankCode().equals("ALIPAY_QRCODE")) {//
			__yeeorder.setPaytype("ALIPAY");
		}

		return __yyePay.save(merchant, __yeeorder);
	}

	/**
	 * 极付回调
	 */
	@ResponseBody
	@Token("out_trade_no")
	@RequestMapping("/JfPayCallback")
	public String JfPayCallback(HttpServletRequest request) {
		try {
			System.out
					.println("============1=====================================JfPayCallback========================="
							+ getRequestParamters(request));
			System.err
					.println("============1=====================================JfPayCallback========================="
							+ getRequestParamters(request));

			// 订单交易状态：TRADE_FAILURE=失败、TRADE_PROCESSING=交易处理中、TRADE_SUCCESS=成功
			// 付款状态码：TRANSFER_PAY_FAILURE - 提现失败， TRANSFER_HAS_PAID - 提现已提交&&银行处理中，
			// TRANSFER_PAY_SUCCESS - 提现成功并到卡
			String status = request.getParameter("trade_status");//
			// 订单号
			String orderNumber = request.getParameter("out_trade_no");

			addCallbackLog(getRequestParamters(request), "极付支付", orderNumber, status);
			if ("TRADE_SUCCESS".equals(status) || "TRANSFER_PAY_SUCCESS".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());

						// 验证参数合法性
						if (JEANPAYpayUtil.checkResponseSign(request, __params.get("MER_KEY").toString())) {

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("极付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("极付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");

							// 这里的回调是提款和存款并存的
							if (__record.getOrdertype().toString()
									.equals(TakeDepositRecord.Enum_ordertype.存款.value + "")) {
								this.activityBetRecordService.addDepositBetRecord(__record);

								/************************ 回调后执行充值返利活动处理 ************************/
								saveingActivityVerify(__record, handles);
							}

							return "SUCCESS";// 成功时必须返回此SUCCESS字段

						} else {

							log.Error("极付回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");
						return "SUCCESS";
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("极付回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 华仁网银支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String HRPaymant(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		PayInterface<HRMerchantConfig, HROrderConfig> __hrPay = new HRPayMent<HRMerchantConfig, HROrderConfig>();
		HRMerchantConfig __merchant = new HRMerchantConfig();
		HROrderConfig __order = new HROrderConfig();
		String bankNo = (object.get("paymenttypebankcode") == null) ? ""
				: (object.get("paymenttypebankcode").toString());
		try {
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
			String merNo = __agument.get("merchantCode").toString();
			__merchant.setMerNo(merNo);
			__merchant.setPayUrl(__agument.get("payUrl").toString());
			__merchant.setHRRsaPublicKey(__agument.get("HRRsaPublicKey").toString());
			__merchant.setMd5Key(__agument.get("md5Key").toString());
			__merchant.setMerRsaPrivateKey(__agument.get("merPrivateKey").toString());
			__merchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");
			__merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/HRPayCallback");

			__order.setV_oid(takeDepositRecord.getOrdernumber());
			__order.setV_rcvname(merNo);
			__order.setV_rcvaddr(merNo);
			__order.setV_rcvtel(merNo);
			__order.setV_goodsname("Goods");// 必填参数，随便写
			__order.setV_goodsdescription("GoodsDescription");// 必填参数，随便写
			__order.setV_rcvpost(merNo);
			__order.setV_qq(merNo);
			__order.setV_amount(ordermount.doubleValue() + "");
			__order.setV_ordername(merNo);
			__order.setV_bankno(bankNo);
			__order.setV_moneytype("0");// 货币类型：0-人民币，1-美元

			return __hrPay.save(__merchant, __order);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 华仁网银支付接口回调
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/HRPayCallback", method = RequestMethod.POST)
	public String HRPayCallback(HttpServletRequest request) {
		JSONObject result = new JSONObject();// 一个json对象
		result.put("result", "error");
		try {
			System.out.println("===============================HRPayCallback=========================");
			System.out.println("===============================HRPayCallback=========================");
			// 此处需要想办法获取到私钥，此方法暂用，以后有好办法请以参数的方式传私钥
			String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJVN0NU4VrLRNaqUpeGKAKpuGa97CW4Uxmd+wBppqh2ciROI4b5zxhYvrXm1BrE1T3NcC5JNJWfkDycomYRCznBtLclhJM7AK0utdYusyvxbgvuQULzVt3b/DqZIchKw6tDprUJVRACQiS03X9NO4SlFiFHhLs+hLTUcYzLWmKoDAgMBAAECgYAtB7kydbmAWSTse8TED1FAFdDdYihn8RAd6taZoMDUCYA2ShR70oMt8ddKW9TW4ZNC4cIDsAzWFqyTTOVwRI3qWGxlxkJl5EnFu4hcWXFBZT9aQ4pZOMXcpZAadkk3lrintaNPsZXhqObHpxoFDkzQpPpr9AIu9VAAfP49ZW1DoQJBANGTtTHFt9/Qup97yb/u67SRCPFA2Pf1ensPUIcmhI90oVHSYbwsc6jLqXRTyRCm/ZnnM9T5jmRu1anc8rwUQ/sCQQC2YEK5brucahUQzD9TFAfdTlMMAJ+SPH1QJ9jYbkRVa4i/a/FzBR4BCrl8hGjWQQXNhc2w2JzqLZpqbugdfMuZAkAQhFCabJeyNvQOT6Y1zzGaWHfY86Bl4l3Vxv40uI9n8uwn06nKN8KhwfNH7LaC7nY8I+GM3mIffjCuo3Ap7HrzAkAVGs6d5tKPJzeI2hn54zeFxKqXmPreUWGvBO1zHk+KEwegHz2xscXnGPaeEjSPlra1Mea7sFV4RA66glsaDncBAkBO16bjDVgfCDGjEM+mzAERDuxSYWnoJJoYtzv+i6CeFqgaq96RoYVqo+RjgNumiSeTVr1JzXhBPsQooRJP7rJy";

			// 通过数据流方式直接读取 获取传过来的json字符串
			InputStreamReader isr = new InputStreamReader(request.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String temp;
			while ((temp = br.readLine()) != null)
				sb.append(temp);
			br.close();
			isr.close();
			String data = sb.toString();
			System.out.println("华仁网银支付回调加密原文\n" + data);
			String params = RSAClient.decode(data, privateKey);
			params = URLDecoder.decode(params, "utf-8");// encode转码
			// 校验参数的正确性
			if (HRSave.checkResponseSign(params)) {
				if (params.startsWith("[") && params.endsWith("]"))
					params = params.substring(1, params.length() - 1);
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap = JSONUnit.getMapFromJsonNew(params);
				String orderNumber = dataMap.get("v_oid");
				String orderStatus = dataMap.get("v_result");
				if ("2000".equals(orderStatus)) {
					TakeDepositRecord __record = takeDepositRecoredService
							.findOrderNumberTakeDepositRecord(orderNumber);
					if (null != __record) {
						if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("华仁网银支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("华仁网银支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "  订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);
							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							result.put("result", "ok");

						} else {
							log.Error("华仁网银支付订单【" + orderNumber + "】已审核通过！");

							result.put("result", "ok");
						}
					} else {
						log.Error("华仁网银支付订单【" + orderNumber + "】不存在！");
					}
				} else {
					log.Error("华仁网银支付结果未成功，状态：" + ("2001".equals(orderStatus) ? "失败" : "等待支付") + "，结果描述："
							+ dataMap.get("v_resultmsg"));
				}
			} else {
				log.Error("华仁网银支付订单参数校验失败！");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "[" + result.toString() + "]";
	}

	/**
	 * 华仁微信支付宝支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String HRWXPaymant(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		PayInterface<HRWXMerchantConfig, HRWXOrderConfig> __hrwxPay = new HRWXPayMent<HRWXMerchantConfig, HRWXOrderConfig>();
		HRWXMerchantConfig __merchant = new HRWXMerchantConfig();
		HRWXOrderConfig __order = new HRWXOrderConfig();
		try {
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
			String merNo = __agument.get("merchantCode").toString();
			String payBank = takeDepositRecord.getEmployeepaymentbank();
			__merchant.setMerNo(merNo);
			__merchant.setPayUrl(__agument.get("payUrl").toString());
			__merchant.setHRRsaPublicKey(__agument.get("HRRsaPublicKey").toString());
			__merchant.setMd5Key(__agument.get("md5Key").toString());
			__merchant.setMerRsaPrivateKey(__agument.get("merPrivateKey").toString());
			__merchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");
			__merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/HRWXPayCallback");

			__order.setV_oid(takeDepositRecord.getOrdernumber());
			__order.setV_rcvname(merNo);
			__order.setV_rcvaddr(merNo);
			__order.setV_rcvtel(merNo);
			__order.setV_goodsname("Goods");// 必填参数，随便写
			__order.setV_goodsdescription("GoodsDescription");// 必填参数，随便写
			__order.setV_rcvpost(merNo);
			__order.setV_qq(merNo);
			__order.setV_amount(ordermount.doubleValue() + "");
			__order.setV_ordername(merNo);
			__order.setV_moneytype("0");// 货币类型：0-人民币，1-美元
			// 手机端传过来则直接传二维码
			if (payBank.equals("WEIXIN_QRCODE") || payBank.equals("ALIPAY_QRCODE"))
				__order.setV_app("app");

			return __hrwxPay.save(__merchant, __order);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 华仁微信支付宝支付接口回调
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/HRWXPayCallback", method = RequestMethod.POST)
	public String HRWXPayCallback(HttpServletRequest request) {
		JSONObject result = new JSONObject();// 一个json对象
		result.put("result", "error");
		try {
			System.out.println("===============================HRWXPayCallback=========================");
			System.err.println("===============================HRWXPayCallback=========================");
			// 此处需要想办法获取到私钥，此方法暂用，以后有好办法请以参数的方式传私钥
			String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJVN0NU4VrLRNaqUpeGKAKpuGa97CW4Uxmd+wBppqh2ciROI4b5zxhYvrXm1BrE1T3NcC5JNJWfkDycomYRCznBtLclhJM7AK0utdYusyvxbgvuQULzVt3b/DqZIchKw6tDprUJVRACQiS03X9NO4SlFiFHhLs+hLTUcYzLWmKoDAgMBAAECgYAtB7kydbmAWSTse8TED1FAFdDdYihn8RAd6taZoMDUCYA2ShR70oMt8ddKW9TW4ZNC4cIDsAzWFqyTTOVwRI3qWGxlxkJl5EnFu4hcWXFBZT9aQ4pZOMXcpZAadkk3lrintaNPsZXhqObHpxoFDkzQpPpr9AIu9VAAfP49ZW1DoQJBANGTtTHFt9/Qup97yb/u67SRCPFA2Pf1ensPUIcmhI90oVHSYbwsc6jLqXRTyRCm/ZnnM9T5jmRu1anc8rwUQ/sCQQC2YEK5brucahUQzD9TFAfdTlMMAJ+SPH1QJ9jYbkRVa4i/a/FzBR4BCrl8hGjWQQXNhc2w2JzqLZpqbugdfMuZAkAQhFCabJeyNvQOT6Y1zzGaWHfY86Bl4l3Vxv40uI9n8uwn06nKN8KhwfNH7LaC7nY8I+GM3mIffjCuo3Ap7HrzAkAVGs6d5tKPJzeI2hn54zeFxKqXmPreUWGvBO1zHk+KEwegHz2xscXnGPaeEjSPlra1Mea7sFV4RA66glsaDncBAkBO16bjDVgfCDGjEM+mzAERDuxSYWnoJJoYtzv+i6CeFqgaq96RoYVqo+RjgNumiSeTVr1JzXhBPsQooRJP7rJy";

			// 通过数据流方式直接读取 获取传过来的json字符串
			InputStreamReader isr = new InputStreamReader(request.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String temp;
			while ((temp = br.readLine()) != null)
				sb.append(temp);
			br.close();
			isr.close();
			String data = sb.toString();
			System.out.println("华仁微信支付回调加密原文\n" + data);
			String params = RSAClient.decode(data, privateKey);
			params = URLDecoder.decode(params, "utf-8");// encode转码
			// 校验参数的正确性，华仁接口做过改动有时候微信支付的回调 需要用网银的验签
			if (HRWXSave.checkResponseSign(params) || HRSave.checkResponseSign(params)) {
				if (params.startsWith("[") && params.endsWith("]"))
					params = params.substring(1, params.length() - 1);
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap = JSONUnit.getMapFromJsonNew(params);
				String orderNumber = dataMap.get("v_oid");
				String orderStatus = dataMap.get("v_result");
				if ("2000".equals(orderStatus)) {
					TakeDepositRecord __record = takeDepositRecoredService
							.findOrderNumberTakeDepositRecord(orderNumber);
					if (null != __record) {
						if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("华仁微信支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("华仁微信支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "  订单审核成功");
							// 存款加入默认打码
							this.activityBetRecordService.addDepositBetRecord(__record);

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							result.put("result", "ok");
						} else {
							log.Error("华仁微信支付订单【" + orderNumber + "】已审核通过！");

							result.put("result", "ok");
						}
					} else {
						log.Error("华仁微信支付订单【" + orderNumber + "】不存在！");
					}
				} else {
					log.Error("华仁微信支付结果未成功，状态：" + ("2001".equals(orderStatus) ? "失败" : "等待支付") + "，结果描述："
							+ dataMap.get("v_resultmsg"));
				}
			} else {
				log.Error("华仁微信支付订单参数校验失败！");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "[" + result.toString() + "]";
	}

	/**
	 * 摩宝支付
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String mbpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		// String RequestURL = StringUtil.getURLDomain( request.getHeader("Referer") );

		PayInterface<MBMerchantConfig, MBOrderConfig> __yyePay = new MBPayMent<MBMerchantConfig, MBOrderConfig>();
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		MBMerchantConfig merchant = new MBMerchantConfig();
		merchant.setMerNo(__agument.get("MER_NO").toString());
		merchant.setMerKey(__agument.get("MER_KEY").toString());
		/**
		 * 
		 * 不进行签名，根据选择的支付方式直接对应页面。不输入或选择支付方式不存在则认为是该商户所拥有的全部方式。 1.网银 2.一键支付 3非银行支付 4
		 * 支付宝扫描 5微信扫码
		 */
		merchant.setChoosePayType(__agument.get("ChoosePayType").toString().trim());// 参数里面配置的

		merchant.setPlatformID(__agument.get("platformID").toString());
		merchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/MBPayCallback");
		merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");// 支付完成后页面跳转地址
		merchant.setPayUrl(__agument.get("payUrl").toString());

		MBOrderConfig __yeeorder = new MBOrderConfig();
		__yeeorder.setOrderAmount(ordermount.doubleValue() + "");
		__yeeorder.setBankCode(String.valueOf(
				object.get("paymenttypebankcode") == null ? "QPAY_UNIONPAY" : object.get("paymenttypebankcode")));
		__yeeorder.setOrderNo(takeDepositRecord.getOrdernumber());

		return __yyePay.save(merchant, __yeeorder);
	}

	/**
	 * 摩宝回调
	 */
	@ResponseBody
	@Token("orderNo")
	@RequestMapping("/MBPayCallback")
	public String MBPayCallback(HttpServletRequest request) {
		try {
			System.err
					.println("============1=====================================MBPayCallback========================="
							+ getRequestParamters(request));

			// 0 未支付，1 成功，2失败
			String status = request.getParameter("orderStatus");//
			// 订单号
			String orderNumber = request.getParameter("orderNo");

			String notifyType = request.getParameter("notifyType");// 0页面跳转，商户给用户展示商品购买成功的页面。1服务器，商户需回写应答。该字段不参与签名、验签

			addCallbackLog(getRequestParamters(request), "摩宝支付", orderNumber, status);

			if ("1".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String MER_KEY = __params.get("MER_KEY").toString();

						// 验证参数合法性
						if (MBpayUtil.checkResponseSign(request, MER_KEY)) {

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("摩宝回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("摩宝回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							if (notifyType != null && notifyType.equals("1")) {
								return "SUCCESS";// 成功时必须返回此SUCCESS字段
							} else {
								return "<p><h1>支付已完成。请关闭本页</h1></p>";
							}

						} else {

							log.Error("摩宝回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");

						if (notifyType != null && notifyType.equals("1")) {
							return "SUCCESS";// 成功时必须返回此SUCCESS字段
						} else {
							return "<p><h1>支付已完成。请关闭本页</h1></p>";
						}
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("摩宝回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 云盛支付
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String yspayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		// String RequestURL = StringUtil.getURLDomain( request.getHeader("Referer") );

		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

		PayInterface<YSMerchantConfig, YSOrderConfig> __yyePay = new YSPayMent<YSMerchantConfig, YSOrderConfig>();
		YSMerchantConfig merchant = new YSMerchantConfig();
		merchant.setPayUrl(__agument.get("payUrl").toString());
		merchant.setMerNo(__agument.get("MER_NO").toString());
		merchant.setMerKey(__agument.get("MER_KEY").toString());
		merchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/YSPayCallback");
		merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

		YSOrderConfig __yeeorder = new YSOrderConfig();
		__yeeorder.setBankCode(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		__yeeorder.setOrderAmount(ordermount.doubleValue() + "");
		__yeeorder.setOrderNo(takeDepositRecord.getOrdernumber());
		__yeeorder.setPayType(__agument.get("payType").toString());// 1网银,2支付宝，3微信，4 QQ钱包

		return __yyePay.save(merchant, __yeeorder);
	}

	/**
	 * 云盛回调
	 */
	@ResponseBody
	@Token("billno")
	@RequestMapping("/YSPayCallback")
	public String YSPayCallback(HttpServletRequest request) {
		try {
			System.err
					.println("============1=====================================YSPayCallback========================="
							+ getRequestParamters(request));

			// Success：支付成功；Failure：支付失败
			String status = request.getParameter("success");//
			// 订单号
			String orderNumber = request.getParameter("billno");

			addCallbackLog(getRequestParamters(request), "云盛支付", orderNumber, status);

			if ("Success".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String MER_KEY = __params.get("MER_KEY").toString();

						// 验证参数合法性
						if (YSpayUtil.checkResponseSign(request, MER_KEY)) {

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("云盛回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("云盛回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "ok";// 成功时必须返回此SUCCESS字段

						} else {

							log.Error("云盛回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");

						return "ok";// 成功时必须返回此SUCCESS字段
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("云盛回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	/**
	 * 汇潮支付
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private String hcpayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {

		// 获取客户站点的请求URL地址
		// String RequestURL = StringUtil.getURLDomain( request.getHeader("Referer") );

		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

		PayInterface<HCMerchantConfig, HCOrderConfig> __yyePay = new HCPayMent<HCMerchantConfig, HCOrderConfig>();
		HCMerchantConfig merchant = new HCMerchantConfig();
		merchant.setPayUrl(__agument.get("payUrl").toString());
		merchant.setMerNo(__agument.get("MER_NO").toString());
		merchant.setMerKey(__agument.get("MER_KEY").toString());
		merchant.setNotiUrl(getRootPath(request, takeDepositRecord) + "/TPayment/HCPayCallback");
		merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

		HCOrderConfig __yeeorder = new HCOrderConfig();
		__yeeorder.setBankCode(
				String.valueOf(object.get("paymenttypebankcode") == null ? "" : object.get("paymenttypebankcode")));
		__yeeorder.setOrderAmount(ordermount.doubleValue() + "");
		__yeeorder.setOrderNo(takeDepositRecord.getOrdernumber());

		// B2CCredit B2C信用卡
		// B2CDebit B2C借记卡
		// noCard 银联快捷支付 ======建议使用
		// quickPay 快捷支付 ======建议使用
		// B2B 企业网银支付 ======该类型不建议使用
		__yeeorder.setPaytype(__agument.get("payType").toString());

		return __yyePay.save(merchant, __yeeorder);
	}

	/**
	 * 汇潮回调
	 */
	@ResponseBody
	@Token("BillNo")
	@RequestMapping("/HCPayCallback")
	public String HCPayCallback(HttpServletRequest request) {
		try {
			System.err
					.println("============1=====================================HCPayCallback========================="
							+ getRequestParamters(request));

			// 88=成功
			String status = request.getParameter("Succeed");//
			// 订单号
			String orderNumber = request.getParameter("BillNo");

			addCallbackLog(getRequestParamters(request), "汇潮支付", orderNumber, status);

			if ("88".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber);

				log.Debug("订单号:" + orderNumber + "	查询充值记录:" + __record);
				if (__record != null) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:" + orderNumber + "	获取账户解密参数...");

						Map<String, Object> __params = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String MER_KEY = __params.get("MER_KEY").toString();

						// 验证参数合法性
						if (HCpayUtil.checkResponseSign(request, MER_KEY)) {

							log.Debug("订单号:" + orderNumber + "	解密校验通过...");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("汇潮回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("汇潮回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:" + orderNumber + "	订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "ok";// 成功时必须返回此SUCCESS字段

						} else {

							log.Error("汇潮回调订单:" + orderNumber + "	参数验证不合法");
							return "error";
						}
					} else {
						log.Debug("订单号:" + orderNumber + "	该笔订单已审核,重复请求无效");

						return "ok";// 成功时必须返回此SUCCESS字段
					}
				} else {
					log.Debug("订单号:" + orderNumber + "	该笔订单不存在");
				}
			} else {
				log.Error("汇潮回调订单:" + orderNumber + "	失败，状态:" + status);
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "error";
	}

	public String MFPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<MFMerchantConfig, MFOrderConfig> __mfPay = new MFPayMent<MFMerchantConfig, MFOrderConfig>();
			MFMerchantConfig __merchant = new MFMerchantConfig();
			MFOrderConfig __order = new MFOrderConfig();
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
			__merchant.setMerId(__agument.get("MerId").toString());
			__merchant.setMerInfo("API");
			__merchant.setMerKey(__agument.get("MerKey").toString());
			__merchant.setNeedResponse("1");// 固定值1
			__merchant.setPayUrl(__agument.get("payUrl").toString());
			__merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/MFPayCallback");

			__order.setAmount(ordermount.doubleValue() + "");
			__order.setBankNo(bankNo);
			__order.setCurrency("CNY");// 固定值CNY
			__order.setGoodsCategory("goodsCategory");
			__order.setGoodsDesc("goodsDescription");
			__order.setGoodsName("goodsName");
			__order.setOrderId(takeDepositRecord.getOrdernumber());

			return __mfPay.save(__merchant, __order);
		} catch (Exception e) {
			log.Error("秒付接口报错", e);
		}
		return null;
	}

	@ResponseBody
	@Token(MFAppConstants.r6_Order)
	@RequestMapping("/MFPayCallback")
	public String MFPaymentCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================MFPayCallback==================================================\n"
							+ params);
			// 获取支付结果
			String status = params.get(MFAppConstants.r1_Code).toString();
			// 获取订单ID
			String orderId = params.get(MFAppConstants.r6_Order).toString();
			addCallbackLog(params, "秒付支付", orderId, status);
			if ("1".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");

						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String merKey = map.get("MerKey").toString();

						if (MFSave.checkReponseSign(params, merKey)) {
							log.Debug("签名校验通过！");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("秒付支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("秒付支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success";// 成功时必须返回此SUCCESS字段

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("秒付回调报错", e);
		}
		return "error";
	}

	/**
	 * 乐信付支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String LePayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<LeMerchantConfig, LeOrderConfig> payment = new LePayMent<LeMerchantConfig, LeOrderConfig>();
			LeMerchantConfig __merchant = new LeMerchantConfig();
			LeOrderConfig __order = new LeOrderConfig();
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
			__merchant.setMchId(__agument.get("mchId").toString());
			__merchant.setAppId(__agument.get("appId").toString());
			__merchant.setKeyStorePath(__agument.get("keyStorePath").toString());
			__merchant.setCertificatePath(__agument.get("certificatePath").toString());
			__merchant.setKeyStorePassword(__agument.get("keyStorePassword").toString());
			__merchant.setPayUrl(__agument.get("payUrl").toString());
			__merchant.setShortUrl(__agument.get("shortUrl").toString());
			__merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

			// 乐信付接口要求金额参数单位为“分”，不能带小数点
			__order.setAmount(ordermount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString());
			__order.setBuyerId(takeDepositRecord.getEmployeecode());
			__order.setDeviceId("L897554536354");
			__order.setDeviceIp(takeDepositRecord.getTraceip());
			__order.setOutTradeNo(takeDepositRecord.getOrdernumber());
			__order.setPayTypeCode(bankNo);
			__order.setSummary("prodect");
			__order.setSummaryDetail("detail");
			__order.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

			return payment.save(__merchant, __order);
		} catch (Exception e) {
			log.Error("乐信付接口报错", e);
		}
		return null;
	}

	/**
	 * 乐信付回调接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(LeAppConstants.callback_outTradeNo)
	@RequestMapping("/LePayCallback")
	public String LePaymentCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================LePayCallback==================================================\n"
							+ params);
			// 获取支付结果，乐信付的回调只要产生回调就说明支付成功
			// 获取订单ID
			String orderId = params.get(LeAppConstants.callback_outTradeNo).toString();
			addCallbackLog(params, "乐信支付", orderId, "success");
			// 根据支付成功返回的订单号查询
			TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
			log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
			if (null != __record) {
				if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
					log.Debug("订单号:【" + orderId + "】获取账户解密参数...");

					Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
							.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
					String keyStorePath = map.get("keyStorePath").toString();
					String certificatePath = map.get("certificatePath").toString();
					String keyStorePassword = map.get("keyStorePassword").toString();

					if (LeSave.checkReponseSign(params, keyStorePath, certificatePath, keyStorePassword)) {
						log.Debug("签名校验通过！");
						DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

						handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
						handles.setAuditdesc("乐信付回调");
						handles.setAssignedtocode(__record.getEnterprisepaymentbank());
						handles.setAssignedtoaccount("乐信付回调");
						depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
						log.Debug("订单号:【" + orderId + "】订单审核成功");

						/************************ 回调后执行充值返利活动处理 ************************/
						saveingActivityVerify(__record, handles);

						return "success";// 成功时必须返回此SUCCESS字段

					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
					return "success";
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单不存在");
			}
		} catch (Exception e) {
			log.Error("乐信付回调报错", e);
		}
		return "fail";
	}

	/**
	 * 如意支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String RYPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<RYMerchantConfig, RYOrderConfig> payment = new RYPayMent<RYMerchantConfig, RYOrderConfig>();
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			RYMerchantConfig __merchant = new RYMerchantConfig();
			__merchant.setMerId(__agument.get("merId").toString());
			__merchant.setMerKey(__agument.get("merKey").toString());
			__merchant.setPayUrl(__agument.get("payUrl").toString());
			__merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/RYPayCallback");
			__merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

			RYOrderConfig __order = new RYOrderConfig();
			__order.setBankCode(bankNo);
			__order.setMerOrdAmt(new DecimalFormat("0.00").format(ordermount));
			__order.setMerOrdId(takeDepositRecord.getOrdernumber());
			// 根据参数设置支付类型
			// remark记录支付类型，方便回调时区分
			if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.如意网关支付.value)) {
				__order.setPayType(RY_PayType.网关支付.value);
				__order.setRemark(RY_PayType.网关支付.value);
			} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.如意微信支付宝.value)) {
				if (takeDepositRecord.getEmployeepaymentbank().equals("B019")) {
					__order.setPayType(RY_PayType.微信扫码支付.value);
					__order.setRemark(RY_PayType.微信扫码支付.value);
				} else if (takeDepositRecord.getEmployeepaymentbank().equals("B020")) {
					__order.setPayType(RY_PayType.支付宝扫码支付.value);
					__order.setRemark(RY_PayType.支付宝扫码支付.value);
				} else if (takeDepositRecord.getEmployeepaymentbank().equals("B034")) {
					__order.setPayType(RY_PayType.QQ钱包扫码支付.value);
					__order.setRemark(RY_PayType.QQ钱包扫码支付.value);
				}
			} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.如意京东钱包.value)) {
				__order.setPayType(RY_PayType.JD钱包扫码支付.value);
				__order.setRemark(RY_PayType.JD钱包扫码支付.value);
			} else {
				__order.setPayType(RY_PayType.快捷支付.value);
				__order.setRemark(RY_PayType.快捷支付.value);
			}

			return payment.save(__merchant, __order);
		} catch (Exception e) {
			log.Error("如意支付接口报错", e);
		}
		return null;
	}

	/**
	 * 如意付回调接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(RYAppConstants.callback_merOrdId)
	@RequestMapping("/RYPayCallback")
	public String RYPaymentCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================RYPayCallback==================================================\n"
							+ params);
			// 获取支付结果，
			String status = params.get(RYAppConstants.callback_tradeStatus).toString();
			// 获取订单ID
			String orderId = params.get(RYAppConstants.callback_merOrdId).toString();
			addCallbackLog(params, "如意支付", orderId, status);
			if (status.equals("success002")) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						String callback_remark = params.get(RYAppConstants.callback_remark).toString();
						String callbackPayType = "如意网关支付支付回调";
						if (StringUtils.isNotBlank(callback_remark)) {
							if (callback_remark.equals(RY_PayType.网关支付.value))
								callbackPayType = "如意网关支付回调";
							if (callback_remark.equals(RY_PayType.快捷支付.value))
								callbackPayType = "如意快捷支付回调";
							if (callback_remark.equals(RY_PayType.微信扫码支付.value))
								callbackPayType = "如意微信扫码支付回调";
							if (callback_remark.equals(RY_PayType.支付宝扫码支付.value))
								callbackPayType = "如意支付宝扫码支付回调";
							if (callback_remark.equals(RY_PayType.QQ钱包扫码支付.value))
								callbackPayType = "如意QQ钱包扫码支付回调";
							if (callback_remark.equals(RY_PayType.JD钱包扫码支付.value))
								callbackPayType = "如意京东钱包扫码支付回调";
						}
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String merKey = map.get("merKey").toString();

						if (RYSave.checkReponseParams(params, merKey)) {
							log.Debug("签名校验通过！");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc(callbackPayType);
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount(callbackPayType);
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "stopnotify";// 成功时必须返回此stopnotify字段

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "stopnotify";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("如意支付回调报错", e);
		}
		return "fail";
	}

	/**
	 * 乐付支付宝接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String LfAliPayPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			PayInterface<LfAliPayMerchantConfig, LfAliPayOrderConfig> payment = new LfAliPayPayMent<LfAliPayMerchantConfig, LfAliPayOrderConfig>();
			// 商户信息
			LfAliPayMerchantConfig merchant = new LfAliPayMerchantConfig();
			merchant.setMerNo(__agument.get("merNo").toString());
			merchant.setPublicKey(__agument.get("PUBLIC_KEY").toString());
			merchant.setMerPublicKey(__agument.get("MEK_PUB_KEY").toString());
			merchant.setMerPrivateKey(__agument.get("MEK_PRI_KEY").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/LfPayCallback");

			// 订单信息
			LfAliPayOrderConfig order = new LfAliPayOrderConfig();
			order.setAmount(ordermount.doubleValue() + "");
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			order.setSubject("Recharge");
			order.setSubBody("VirtualCurrency");
			order.setRemark("GoldEggRemark");

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("乐付支付宝接口报错", e);
		}
		return null;
	}

	/**
	 * 乐付QQ钱包接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String LfQQPayPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			PayInterface<LfQQPayMerchantConfig, LfQQPayOrderConfig> payment = new LfQQPayPayMent<LfQQPayMerchantConfig, LfQQPayOrderConfig>();
			// 商户信息
			LfQQPayMerchantConfig merchant = new LfQQPayMerchantConfig();
			merchant.setMerNo(__agument.get("merNo").toString());
			merchant.setPublicKey(__agument.get("PUBLIC_KEY").toString());
			merchant.setMerPublicKey(__agument.get("MEK_PUB_KEY").toString());
			merchant.setMerPrivateKey(__agument.get("MEK_PRI_KEY").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/LfPayCallback");

			// 订单信息
			LfQQPayOrderConfig order = new LfQQPayOrderConfig();
			order.setAmount(ordermount.doubleValue() + "");
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			order.setSubject("Recharge");
			order.setSubBody("VirtualCurrency");
			order.setRemark("GoldEggRemark");

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("乐付QQ钱包接口报错", e);
		}
		return null;
	}

	/**
	 * 锐付支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String RFPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());
			PayInterface<RFMerchantConfig, RFOrderConfig> payment = new RFPayMent<RFMerchantConfig, RFOrderConfig>();
			// 商户信息
			RFMerchantConfig merchant = new RFMerchantConfig();
			merchant.setPartyId(__agument.get("partyId").toString());
			merchant.setAccountId(__agument.get("accountId").toString());
			merchant.setGoods(__agument.get("goods").toString());
			merchant.setEncodeType(__agument.get("encodeType").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setCheckUrl(getRootPath(request, takeDepositRecord) + "/TPayment/RFPayCallback");
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

			// 订单信息
			RFOrderConfig order = new RFOrderConfig();
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(ordermount.doubleValue() + "");
			if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.锐付网银.value)) {
				order.setAppType(RF_AppType.网银支付.value);
			} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.锐付微信支付宝.value)) {
				if (takeDepositRecord.getEmployeepaymentbank().equals("B019")) {
					order.setAppType(RF_AppType.微信支付.value);
				} else if (takeDepositRecord.getEmployeepaymentbank().equals("B020")) {
					order.setAppType(RF_AppType.支付宝支付.value);
				} else if (takeDepositRecord.getEmployeepaymentbank().equals("B034")) {
					order.setAppType(RF_AppType.QQ钱包支付.value);
				}
			}
			order.setBank(bankNo);
			order.setCardType("01");// 01-RMB debit card 02-Credit card

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("锐付支付接口报错", e);
		}
		return null;
	}

	/**
	 * 锐付回调
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(RFAppConstants.callback_orderNo)
	@RequestMapping("/RFPayCallback")
	public String RFPaymentCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================RFPayCallback==================================================\n"
							+ params);
			// 获取支付结果
			String status = params.get(RFAppConstants.callback_succ).toString();
			// 获取订单ID
			String orderId = params.get(RFAppConstants.callback_orderNo).toString();
			addCallbackLog(params, "锐付支付", orderId, status);
			if ("Y".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");

						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String md5Key = map.get("md5Key").toString();

						if (RFSave.cheakCallbackSign(params, md5Key)) {
							log.Debug("签名校验通过！");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("锐付支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("锐付支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "checkok";// 成功时必须返回此SUCCESS字段

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "checkok";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("锐付回调报错", e);
		}
		return "error";
	}

	/**
	 * 宝立付支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String BLPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
			PayInterface<BLMerchantConfig, BLOrderConfig> payment = new BLPayMent<BLMerchantConfig, BLOrderConfig>();
			// 商户信息
			BLMerchantConfig merchant = new BLMerchantConfig();
			merchant.setMerCode(__agument.get("merCode").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/BLPayCallback");
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

			// 订单信息
			BLOrderConfig order = new BLOrderConfig();
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			// 订单金额不能有小数点，默认最后两位为小数位
			order.setAmount(ordermount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString());
			order.setCurrency("CNY");// 币种目前只支持CNY
			order.setOrderTime(BLSave.getOrderTime());
			order.setProductName("Recharge");
			order.setOrderDesc("Description");

			if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.宝立付网银.value)) {
				order.setPayType(BL_PayType.网银支付.value);
			} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.宝立付微信支付宝.value)) {
				if (takeDepositRecord.getEmployeepaymentbank().equals("B019")) {
					order.setPayType(BL_PayType.微信支付.value);
				} else if (takeDepositRecord.getEmployeepaymentbank().equals("B020")) {
					order.setPayType(BL_PayType.支付宝支付.value);
				} else if (takeDepositRecord.getEmployeepaymentbank().equals("B034")) {
					order.setPayType(BL_PayType.QQ钱包支付.value);
				}
			}

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("宝立付接口报错", e);
		}
		return null;
	}

	/**
	 * 宝立付回调
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/BLPayCallback")
	public String BLPaymentCallback(HttpServletRequest request) {
		try {
			Map<String, String> params = BLSave.callbackUtil(request);
			System.err.println(
					"==================================================BLPayCallback==================================================\n"
							+ params);

			// 获取支付结果
			String status = params.get(BLAppConstants.rep_paymentState);
			// 获取订单ID
			String orderId = params.get(BLAppConstants.rep_orderNo);
			addCallbackLog2(params, "宝立付支付", orderId, status);
			if ("00".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");

						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String md5Key = map.get("md5Key").toString();

						if (BLSave.checkCallbackSign(params, md5Key)) {
							log.Debug("签名校验通过！");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("宝立付支付回调：" + params.get(BLAppConstants.rep_payType));
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("宝立付支付回调" + params.get(BLAppConstants.rep_payType));
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success";// 成功时必须返回此success字段

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
				log.Debug("错误代码：" + params.get(BLAppConstants.rep_errorCode) + "  错误信息："
						+ params.get(BLAppConstants.rep_errorMessage));
			}
		} catch (Exception e) {
			log.Error("宝立付回调报错", e);
		}
		return "error";
	}

	/**
	 * 可可支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	private String KKPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
			PayInterface<KKMerchantConfig, KKOrderConfig> payment = new KKPayMent<KKMerchantConfig, KKOrderConfig>();
			// 商户信息
			KKMerchantConfig merchant = new KKMerchantConfig();
			merchant.setPayKey(__agument.get("payKey").toString());
			merchant.setProductType(__agument.get("payType").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/KKPayCallback");
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

			// 商户微信公众号参数
			merchant.setWXappid(__agument.get("appid").toString());
			merchant.setWXappSecret(__agument.get("appSecret").toString());
			merchant.setWXcallbackUrl(__agument.get("callbackUrl").toString());
			merchant.setWXcodeUrl(__agument.get("codeUrl").toString());
			merchant.setWXopenIdUrl(__agument.get("openIdUrl").toString());

			// 订单信息
			KKOrderConfig order = new KKOrderConfig();
			order.setOutTradeNo(takeDepositRecord.getOrdernumber());
			order.setOrderPrice(ordermount.doubleValue() + "");
			order.setOrderTime(KKSave.getOrderTime());
			order.setProductName("Name");
			order.setOrderIp(takeDepositRecord.getTraceip());

			if (KK_ProductType.微信D0公众号支付.value.equals(merchant.getProductType())
					|| KK_ProductType.微信T0公众号支付.value.equals(merchant.getProductType())
					|| KK_ProductType.微信T1公众号支付.value.equals(merchant.getProductType())) {
				return KKSave.getWXCodeUrl(merchant, order);
			} else {
				return payment.save(merchant, order);
			}
		} catch (Exception e) {
			log.Error("乐付支付宝接口报错", e);
		}
		return null;
	}

	/**
	 * 微信获取
	 * 
	 * @param request
	 */
	@RequestMapping("/WXCallback")
	public String WXGetOpenId(HttpServletRequest request, Model model) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.out.println("==========微信回调参数==========");
			System.out.println(params);

			String code = String.valueOf(params.get("code"));
			String orderId = String.valueOf(params.get("state"));

			TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());

			PayInterface<KKMerchantConfig, KKOrderConfig> payment = new KKPayMent<KKMerchantConfig, KKOrderConfig>();
			// 商户信息
			KKMerchantConfig merchant = new KKMerchantConfig();
			merchant.setPayKey(__agument.get("payKey").toString());
			merchant.setProductType(__agument.get("payType").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setNotifyUrl(getRootPath(request, __record) + "/TPayment/KKPayCallback");
			merchant.setReturnUrl(getRootPath(request, __record) + "/TPayment/success");

			// 商户微信公众号参数
			merchant.setWXappid(__agument.get("appid").toString());
			merchant.setWXappSecret(__agument.get("appSecret").toString());
			merchant.setWXcallbackUrl(__agument.get("callbackUrl").toString());
			merchant.setWXcodeUrl(__agument.get("codeUrl").toString());
			merchant.setWXopenIdUrl(__agument.get("openIdUrl").toString());

			// 订单信息
			KKOrderConfig order = new KKOrderConfig();
			order.setOutTradeNo(__record.getOrdernumber());
			order.setOrderPrice(__record.getOrderamount().doubleValue() + "");
			order.setOrderTime(KKSave.getOrderTime());
			order.setProductName("Name");
			order.setOrderIp(__record.getTraceip());
			order.setOpenid(KKSave.getOpenId(merchant, code));

			String payMessage = payment.save(merchant, order);
			Map<String, String> payParams = JSONUnit.getMapFromJsonNew(payMessage);
			model.addAttribute("data", payParams);
			return "/payment/weixinpay";
		} catch (Exception ex) {
			log.Error("微信回调获取Code方法报错", ex);
			return Constant.PAGE_ERROR;
		}
	}

	/**
	 * 可可支付回调
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(KKAppConstants.callback_outTradeNo)
	@RequestMapping("/KKPayCallback")
	public String KKPaymentCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================KKPayCallback==================================================\n"
							+ params);
			// 获取支付结果
			String status = params.get(KKAppConstants.callback_tradeStatus).toString();
			// 获取订单ID
			String orderId = params.get(KKAppConstants.callback_outTradeNo).toString();
			addCallbackLog(params, "可可支付", orderId, status);
			if ("SUCCESS".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");

						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String md5Key = map.get("md5Key").toString();

						if (KKSave.checkSign(params, md5Key)) {
							log.Debug("签名校验通过！");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("可可支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("可可支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "SUCCESS";// 成功时必须返回此SUCCESS字段

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "SUCCESS";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("可可支付回调报错", e);
		}
		return "error";
	}

	/**
	 * 喜发支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String XFPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());
			PayInterface<XFMerchantConfig, XFOrderConfig> payment = new XFPayMent<XFMerchantConfig, XFOrderConfig>();
			// 商户信息
			XFMerchantConfig merchant = new XFMerchantConfig();
			merchant.setCid(__agument.get("cid").toString());
			merchant.setUid(takeDepositRecord.getLoginaccount());
			merchant.setSha1Key(__agument.get("sha1Key").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());

			// 订单信息
			XFOrderConfig order = new XFOrderConfig();
			order.setOrderId(takeDepositRecord.getOrdernumber());
			order.setAmount(ordermount.doubleValue() + "");
			order.setTime((System.currentTimeMillis() / 1000) + "");
			order.setIp(takeDepositRecord.getTraceip());
			order.setTflag(bankNo);

			if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.喜发网银.value)) {
				order.setType(XF_PayType.网银支付.value);
			} else if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.喜发微信支付宝.value)) {
				order.setType(XF_PayType.二维码存款.value);
			}

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("喜发支付宝接口报错", e);
		}
		return null;
	}

	/**
	 * 喜发支付回调
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/XFPayCallback")
	public String XFPaymentCallback(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("success", false);
		try {
			Map<String, String> params = XFSave.callbackUtil(request);
			System.err.println(
					"==================================================XFPayCallback==================================================\n"
							+ params);

			String jsonData = params.get("jsonData");
			String callbackSign = params.get(XFAppConstants.callback_sign);
			Map<String, String> p = JSONUnit.getMapFromJsonNew(jsonData);
			String orderId = p.get(XFAppConstants.callback_order_id);
			String status = p.get(XFAppConstants.callback_cmd);
			addCallbackLog2(params, "代收代付支付", orderId, status);
			if ("order_success".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");

						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String key = map.get("sha1Key").toString();

						if (XFSave.checkSign(jsonData, key, callbackSign)) {
							log.Debug("签名校验通过！");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("喜发支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("喜发支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							result.put("success", true);
							return result.toString();

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
							result.put("msg", "订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						result.put("success", true);
						return result.toString();
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
					result.put("msg", "订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
				result.put("msg", "订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("喜发支付回调报错", e);
			result.put("msg", "系统出错！");
		}
		return result.toString();
	}

	/**
	 * 旺付通支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String WFTPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<WFTMerchantConfig, WFTOrderConfig> payment = new WFTPayMent<WFTMerchantConfig, WFTOrderConfig>();
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			WFTMerchantConfig merchant = new WFTMerchantConfig();
			merchant.setMerId(__agument.get("merId").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());// merKey
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/WFTPayCallback");
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

			WFTOrderConfig order = new WFTOrderConfig();
			order.setOrderId(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(new DecimalFormat("0.00").format(ordermount));
			order.setBankCode(bankNo);
			order.setProductInfo("recharge");
			order.setRemark("remark");

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("旺付通支付接口报错", e);
		}
		return null;
	}

	/**
	 * 旺付通回调接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(WFTAppConstants.callback_OrdId)
	@RequestMapping("/WFTPayCallback")
	public String WFTPaymentCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================RYPayCallback==================================================\n"
							+ params);
			// 获取支付结果，
			String status = params.get(WFTAppConstants.callback_ResultCode).toString();
			// 获取订单ID
			String orderId = params.get(WFTAppConstants.callback_OrdId).toString();
			addCallbackLog(params, "旺付通支付", orderId, status);
			if ("success002".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String merKey = map.get("md5Key").toString();

						if (WFTSave.checkCallbackSign(params, merKey)) {
							log.Debug("签名校验通过！");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("旺付通支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("旺付通支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success|9999";// 成功时必须返回此success|9999字段

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "success|9999";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("旺付通支付回调报错", e);
		}
		return "fail";
	}

	/**
	 * 众宝支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String ZBPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());
			PayInterface<ZBMerchantConfig, ZBOrderConfig> payment = new ZBPayMent<ZBMerchantConfig, ZBOrderConfig>();
			// 商户信息
			ZBMerchantConfig merchant = new ZBMerchantConfig();
			merchant.setCustomer(__agument.get("customer").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setAsynbackurl(getRootPath(request, takeDepositRecord) + "/TPayment/ZBPayCallback");
			merchant.setSynbackurl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

			// 订单信息
			ZBOrderConfig order = new ZBOrderConfig();
			order.setOrderid(takeDepositRecord.getOrdernumber());
			order.setAmount(ordermount.doubleValue() + "");
			order.setRequestTime((System.currentTimeMillis() / 1000) + "");
			order.setBanktype(bankNo);
			order.setAttach(bankNo);

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("众宝支付宝接口报错", e);
		}
		return null;
	}

	/**
	 * 众宝支付回调
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(ZBAppConstants.callback_orderid)
	@RequestMapping("/ZBPayCallback")
	public String ZBPaymentCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================ZBPayCallback==================================================\n"
							+ params);

			String orderId = params.get(ZBAppConstants.callback_orderid).toString();
			String status = params.get(ZBAppConstants.callback_result).toString();
			addCallbackLog(params, "众宝支付", orderId, status);
			if ("0".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");

						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String md5Key = map.get("md5Key").toString();

						if (ZBSave.checkCallbackSign(params, md5Key)) {
							log.Debug("签名校验通过！");

							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("众宝支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("众宝支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "opstate=0";

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "opstate=0";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("众宝支付回调报错", e);
		}
		return "opstate=1";
	}

	/**
	 * 亿卡云支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String EKPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<EKMerchantConfig, EKOrderConfig> payment = new EKPayMent<EKMerchantConfig, EKOrderConfig>();
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			EKMerchantConfig merchant = new EKMerchantConfig();
			merchant.setParter(__agument.get("parter").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/EKPayCallback");
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

			EKOrderConfig order = new EKOrderConfig();
			order.setOrderId(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(new DecimalFormat("0.00").format(ordermount));
			order.setBankType(bankNo);
			order.setIP(takeDepositRecord.getTraceip());
			order.setAttach("remark");

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("亿卡云支付接口报错", e);
		}
		return null;
	}

	/**
	 * 亿卡云回调接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(EKAppConstants.callback_orderid)
	@RequestMapping("/EKPayCallback")
	public String EKPaymentCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================EKPayCallback==================================================\n"
							+ params);
			// 获取支付结果，
			String status = params.get(EKAppConstants.callback_opstate).toString();
			// 获取订单ID
			String orderId = params.get(EKAppConstants.callback_orderid).toString();
			addCallbackLog(params, "亿卡云支付", orderId, status);
			if ("0".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String merKey = map.get("md5Key").toString();

						if (EKSave.checkCallbackSign(params, merKey)) {
							log.Debug("签名校验通过！");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("亿卡云支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("亿卡云支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "opstate=0";// 成功时必须返回此opstate=0字段

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
							return "opstate=-2";
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "opstate=0";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("亿卡云支付回调报错", e);
		}
		return "opstate=-1";
	}

	/**
	 * 聚合支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String JHPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<JHMerchantConfig, JHOrderConfig> payment = new JHPayMent<JHMerchantConfig, JHOrderConfig>();

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			JHMerchantConfig merchant = new JHMerchantConfig();
			merchant.setMerid(__agument.get("merId").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/JHPayCallbakc");

			JHOrderConfig order = new JHOrderConfig();
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(ordermount.doubleValue() + "");
			order.setOrderTime(JHAppConstants.getOrderTime());
			order.setNoncestr(RandomString.UUID());

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("聚合支付接口报错", e);
		}
		return null;
	}

	/**
	 * 聚合支付回调接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(JHAppConstants.callback_merchantOutOrderNo)
	@RequestMapping("/JHPayCallbakc")
	public String JHPaymentCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================JHPayCallbakc==================================================\n"
							+ params);
			// 获取支付结果，
			String status = params.get(JHAppConstants.callback_payResult).toString();
			// 获取订单ID
			String orderId = params.get(JHAppConstants.callback_merchantOutOrderNo).toString();
			addCallbackLog(params, "聚合支付", orderId, status);
			if ("true".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String merKey = map.get("md5Key").toString();

						if (JHSave.checkCallbackSign(params, merKey)) {
							log.Debug("签名校验通过！");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("聚合支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("聚合支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return JHAppConstants.return_string;

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
							return JHAppConstants.return_string;
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("聚合支付回调报错", e);
		}
		return "fail";
	}

	/**
	 * 点点支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String DDPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<DDMerchantConfig, DDOrderConfig> payment = new DDPayMent<DDMerchantConfig, DDOrderConfig>();

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			DDMerchantConfig merchant = new DDMerchantConfig();
			merchant.setMerCode(__agument.get("merCode").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());
			merchant.setPayType(__agument.get("payType").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/DDPayCallbakc");
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

			DDOrderConfig order = new DDOrderConfig();
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			// 金额单位为分
			order.setAmount(ordermount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString());
			order.setName("recharge");

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("点点支付接口报错", e);
		}
		return null;
	}

	/**
	 * 点点支付回调接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/DDPayCallbakc")
	public String DDPaymentCallback(HttpServletRequest request) {
		try {
			Map<String, String> params = DDSave.callbackUtil(request);
			System.err.println(
					"==================================================DDPayCallbakc==================================================\n"
							+ params);
			// 获取支付结果，
			String status = params.get(DDAppConstants.callback_trade_status);
			// 获取订单ID
			String orderId = params.get(DDAppConstants.callback_outer_trade_no);
			addCallbackLog2(params, "点点支付", orderId, status);
			if ("TRADE_SECCESS".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String merCode = map.get("merCode").toString();
						String merKey = map.get("md5Key").toString();

						if (DDSave.checkCallbackSign(params, merCode, merKey)) {
							log.Debug("签名校验通过！");

							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("点点支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("点点支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "SUCCESS";

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
							return "SUCCESS";
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("点点支付回调报错", e);
		}
		return "fail";
	}

	/**
	 * Astropay支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	@RequestMapping("/Astropay")
	public String AstropayPayment(HttpServletRequest request, Model model) {
		try {
			Map<String, Object> object = getRequestParamters(request);
			// 获取支付用户
			EnterpriseEmployee ee = menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			// 获取支付类型
			EnterpriseThirdpartyPayment __etp = new EnterpriseThirdpartyPayment();
			__etp.setEnterprisecode(ee.getEnterprisecode());

			// 获取请求类型，PC / H5
			// String type = String.valueOf(object.get("type"));
			// if (EnterpriseThirdpartyPayment.Enum_type.H5.value.equals(type)) {
			// __etp.setH5status(EnterpriseThirdpartyPayment.Enum_status.启用.value);
			// } else {
			// __etp.setStatus(EnterpriseThirdpartyPayment.Enum_status.启用.value);
			// }
			__etp.setEnterprisethirdpartycode(String.valueOf(object.get("enterprisethirdpartycode")));
			List<EnterpriseThirdpartyPayment> __list = enterpriseThirdpartyPaymentService.select(__etp);

			__etp = __list.get(0);
			/* ============================== 生成订单 ============================== */
			TakeDepositRecord takeDepositRecord = BeanToMapUtil.convertMap(object, TakeDepositRecord.class);
			takeDepositRecord.setOrdernumber(RandomString.UUID());// 32位数的订单号

			takeDepositRecord.setParentemployeecode(ee.getParentemployeecode());
			takeDepositRecord.setPaymenttypecode(Enum_PayType.第三方即时支付.value);
			takeDepositRecord.setOrderamount(new BigDecimal(object.get("orderamount").toString()));
			takeDepositRecord.setEnterprisepaymentname(__etp.getThirdpartypaymenttypename());
			takeDepositRecord.setEnterprisepaymentaccount(__etp.getEnterprisethirdpartycode());
			takeDepositRecord.setEnterprisepaymentbank(__etp.getThirdpartypaymenttypecode());
			takeDepositRecord.setEmployeepaymentname("");
			takeDepositRecord.setEmployeepaymentaccount("");
			takeDepositRecord.setEmployeepaymentbank(
					String.valueOf(object.get("bankcode") == null ? "" : object.get("bankcode")));
			takeDepositRecord.setOrdertype((byte) Enum_ordertype.存款.value);
			takeDepositRecord.setOrderstatus((byte) Enum_orderstatus.审核中.value);
			takeDepositRecord.setOrdercreater("会员");
			takeDepositRecord.setOrdercomment("自动存款");
			takeDepositRecord.setOrderdate(new Date());
			if (takeDepositRecord.getFavourableid() != null) {
				if (takeDepositRecord.getFavourableid().trim().length() <= 0) {
					takeDepositRecord.setFavourableid(null);
				}
			}
			takeDepositRecord.setLoginaccount(ee.getLoginaccount());

			if (StringUtil.getString(object.get("H5BJL")).toUpperCase().equals("Y")) {
				takeDepositRecord.setFavourableid("H5BJL");
			}

			System.out.println("Astropay Params = " + object);

			takeDepositRecoredService.tc_save_money(takeDepositRecord);

			/* ============================== 发起支付 ============================== */
			PayInterface<AstropayMerchantConfig, AstropayOrderConfig> payment = new AstropayPayMent<AstropayMerchantConfig, AstropayOrderConfig>();

			// 订单参数
			String currency = String.valueOf(object.get("currency"));
			String userNo = takeDepositRecord.getEmployeecode();
			String cardNum = String.valueOf(object.get("cardNum"));
			String cardCode = String.valueOf(object.get("cardCode"));
			String expireYear = String.valueOf(object.get("expireYear"));
			String expireMonth = String.valueOf(object.get("expireMonth"));

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			AstropayMerchantConfig merchant = new AstropayMerchantConfig();
			merchant.setMerLogin(String.valueOf(__agument.get("merLogin")));
			merchant.setMerTransKey(String.valueOf(__agument.get("merTransKey")));
			merchant.setMerSecretKey(String.valueOf(__agument.get("merSecretKey")));
			merchant.setPayUrl(String.valueOf(__agument.get("payUrl")));
			merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/AstropayCallback");
			merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");

			AstropayOrderConfig order = new AstropayOrderConfig();
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(object.get("orderamount").toString());
			order.setCurrency(currency);
			order.setOrderType(Astro_PayType.AUTH_CAPTURE.value);
			order.setUserNo(userNo);
			order.setUserCardNum(cardNum);
			order.setUserCardCode(cardCode);
			order.setUserCardExpDate(expireMonth + "/" + expireYear);

			String result = payment.save(merchant, order);

			if (PayInterface.PAY_SUCCESS.equals(result)) {
				TakeDepositRecord __record = takeDepositRecoredService
						.findOrderNumberTakeDepositRecord(takeDepositRecord.getOrdernumber());

				DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

				handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
				handles.setAuditdesc("Astropay payment");
				handles.setAssignedtocode(__record.getEnterprisepaymentbank());
				handles.setAssignedtoaccount("System automatically");
				depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);

				/************************ 回调后执行充值返利活动处理 ************************/
				saveingActivityVerify(__record, handles);

				return "redirect:/TPayment/success";
			} else {
				log.Error("Astropay支付失败，返回信息：" + result);
				model.addAttribute("message", result);
				return Constant.PAGE_ERROR;
			}
		} catch (Exception e) {
			log.Error("Astropay支付报错", e);
			model.addAttribute("message", e.getMessage());
			return Constant.PAGE_ERROR;
		}
	}

	/**
	 * 聚合快捷支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String JHQuickPayPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<JHQuickPayMerchantConfig, JHQuickPayOrderConfig> payment = new JHQuickPayPayMent<JHQuickPayMerchantConfig, JHQuickPayOrderConfig>();

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			JHQuickPayMerchantConfig merchant = new JHQuickPayMerchantConfig();
			merchant.setPayKey(__agument.get("payKey").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());
			merchant.setProductType(__agument.get("productType").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/JHQuickPayCallback");

			JHQuickPayOrderConfig order = new JHQuickPayOrderConfig();
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(ordermount.doubleValue() + "");
			order.setBankNo("123456789");// 固定值
			order.setOrderName("Recharge");
			order.setOrderTime(JHQuickPaySave.getOrderTime());
			order.setOrderIP(takeDepositRecord.getTraceip());

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("聚合快捷支付接口报错", e);
		}
		return null;
	}

	/**
	 * 聚合快捷支付回调接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(JHQuickPayAppConstants.callback_outTradeNo)
	@RequestMapping("/JHQuickPayCallback")
	public String JHQuickPayCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================JHQuickPayCallback==================================================\n"
							+ params);
			// 获取支付结果，
			String status = params.get(JHQuickPayAppConstants.callback_tradeStatus).toString();
			// 获取订单ID
			String orderId = params.get(JHQuickPayAppConstants.callback_outTradeNo).toString();
			addCallbackLog(params, "聚合快捷支付", orderId, status);
			if ("SUCCESS".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String md5Key = map.get("md5Key").toString();

						if (JHQuickPaySave.checkSign(params, md5Key)) {
							log.Debug("签名校验通过！");

							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("聚合快捷支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("聚合快捷支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "SUCCESS";

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "SUCCESS";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("聚合快捷支付回调报错", e);
		}
		return "FAIL";
	}

	/**
	 * 付呗支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String FBPayPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<FBMerchantConfig, FBOrderConfig> payment = new FBPayMent<FBMerchantConfig, FBOrderConfig>();

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());
			FBMerchantConfig merchant = new FBMerchantConfig();
			merchant.setAppId(__agument.get("appId").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());
			merchant.setMethod(__agument.get("method").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/FBPayCallback");
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

			FBOrderConfig order = new FBOrderConfig();
			order.setOrderId(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(ordermount.doubleValue() + "");
			order.setOrderType(bankNo);
			order.setNonce(RandomString.UUID());

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("付呗支付接口报错", e);
		}
		return null;
	}

	/**
	 * 付呗支付回调接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/FBPayCallback")
	public String FBPayCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================FBPayCallback==================================================\n"
							+ params);
			// 获取支付结果
			String status = params.get(FBAppConstants.callback_result_code).toString();
			String data = params.get(FBAppConstants.callback_data).toString();
			JSONObject jsonData = JSONObject.fromObject(data);
			// 获取订单ID
			String orderId = jsonData.getString(FBAppConstants.callback_merchant_order_sn);
			addCallbackLog(params, "付呗支付", orderId, status);
			if ("200".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String md5Key = map.get("md5Key").toString();

						if (FBSave.checkSign(params, md5Key)) {
							log.Debug("签名校验通过！");

							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("付呗支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("付呗支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "SUCCESS";

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "SUCCESS";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("付呗支付回调报错", e);
		}
		return "FAIL";
	}

	/**
	 * 支付通接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	public String ZFTWebPayPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<ZFTMerchantConfig, ZFTOrderConfig> payment = new ZFTPayMent<ZFTMerchantConfig, ZFTOrderConfig>();

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());

			ZFTMerchantConfig merchant = new ZFTMerchantConfig();

			merchant.setMerId(__agument.get("merId").toString());
			merchant.setShaKey(__agument.get("shaKey").toString());
			merchant.setMerEmail(__agument.get("merEmail").toString());
			merchant.setAppName(__agument.get("appName").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setPayType(ZFT_PayType.直连模式.value);
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/ZFTCallback");
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");

			ZFTOrderConfig order = new ZFTOrderConfig();
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(ordermount.doubleValue() + "");
			order.setOrderBank(bankNo);
			order.setOrderTitle("Recharge");
			order.setOrderBody("Recharger");

			order.setIsApp("app");

			order.setUserIP(takeDepositRecord.getTraceip());
			order.setAppMsg(RandomString.UUID().toLowerCase());
			order.setAppType("wap");

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("支付通支付接口报错", e);
		}
		return null;
	}

	/**
	 * 支付通回调接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(ZFTAppConstants.callback_order_no)
	@RequestMapping("/ZFTCallback")
	public String ZFTPayCallbackk(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================ZFTCallback==================================================\n"
							+ params);
			// 获取支付结果
			String status1 = params.get(ZFTAppConstants.callback_trade_status).toString();
			String status2 = params.get(ZFTAppConstants.callback_is_success).toString();
			String orderId = params.get(ZFTAppConstants.callback_order_no).toString();
			addCallbackLog(params, "支付通支付", orderId, status1 + "-" + status2);
			if ("TRADE_FINISHED".equals(status1) && "T".equals(status2)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String shaKey = map.get("shaKey").toString();

						if (ZFTSave.checkSign(params, shaKey)) {
							log.Debug("签名校验通过！");

							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("支付通回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("支付通回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success";

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("支付通回调报错", e);
		}
		return "fail";
	}

	/**
	 * 银盛支付宝H5支付
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	private String YSALIPayPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<YSALIPayMerchantConfig, YSALIPayOrderConfig> payment = new YSALIPayPayMent<YSALIPayMerchantConfig, YSALIPayOrderConfig>();

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			YSALIPayMerchantConfig merchant = new YSALIPayMerchantConfig();
			merchant.setMerId(__agument.get("merId").toString());
			merchant.setMerName(__agument.get("merName").toString());
			merchant.setPrivateKeyUrl(__agument.get("privateKeyUrl").toString());
			merchant.setPublicKeyUrl(__agument.get("publicKeyUrl").toString());
			merchant.setPassword(__agument.get("password").toString());
			merchant.setBusinessCode(__agument.get("businessCode").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setMethod(__agument.get("method").toString());
			merchant.setTimeout(__agument.get("timeout").toString());
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/YSALIPayCallback");

			YSALIPayOrderConfig order = new YSALIPayOrderConfig();
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(ordermount.doubleValue() + "");
			order.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			order.setOrderSubject("Recharge");

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("银盛支付宝H5接口报错", e);
		}
		return null;
	}

	/**
	 * 银盛支付宝H5支付回调
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(YSALIPayAppConstants.callback_out_trade_no)
	@RequestMapping("/YSALIPayCallback")
	public String YSALIPayCallbackk(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================YSALIPayCallback==================================================\n"
							+ params);
			// 获取支付结果
			String status = params.get(YSALIPayAppConstants.callback_trade_status).toString();
			String orderId = params.get(YSALIPayAppConstants.callback_out_trade_no).toString();
			addCallbackLog(params, "银盛支付宝H5支付", orderId, status);
			if ("TRADE_SUCCESS".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String publicKeyURL = map.get("publicKeyUrl").toString();

						if (YSALIPaySave.checkSign(params, publicKeyURL)) {
							log.Debug("签名校验通过！");

							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("银盛支付宝H5回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("银盛支付宝H5回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success";

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("银盛支付宝H5接口回调报错", e);
		}
		return "fail";
	}

	/***
	 * 聚合收银台
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	private String JHH5PayPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<JHH5MerchantConfig, JHH5OrderConfig> payment = new JHH5PayMent<JHH5MerchantConfig, JHH5OrderConfig>();

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			JHH5MerchantConfig merchant = new JHH5MerchantConfig();
			merchant.setMerId(__agument.get("merId").toString());
			merchant.setAppId(__agument.get("appId").toString());
			merchant.setMd5Key(__agument.get("md5Key").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/JHH5Callback");

			JHH5OrderConfig order = new JHH5OrderConfig();
			order.setOrderId(takeDepositRecord.getOrdernumber());
			// 单位：分
			order.setOrderAmount(
					ordermount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString());
			order.setOrderUserId(takeDepositRecord.getLoginaccount());
			order.setSubjectNo("777");
			order.setSubject("Recharge");

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("聚合收银台接口报错", e);
		}
		return null;
	}

	/**
	 * 聚合收银台回调
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(JHH5AppConstants.callback_merchantOrderId)
	@RequestMapping("/JHH5Callback")
	public String JHH5CallbackCallbackk(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================JHH5Callback==================================================\n"
							+ params);
			// 获取支付结果
			String status = params.get(JHH5AppConstants.callback_tradeStatus).toString();
			String orderId = params.get(JHH5AppConstants.callback_merchantOrderId).toString();
			addCallbackLog(params, "银盛支付宝H5支付", orderId, status);
			if ("2".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String md5Key = map.get("md5Key").toString();

						if (JHH5Save.checkSign(params, md5Key)) {
							log.Debug("签名校验通过！");

							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("聚合收银台回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("聚合收银台回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success";

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("聚合收银台接口回调报错", e);
		}
		return "fail";
	}

	/***
	 * 星付支付接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	private String XingFPayPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<XingFMerchantConfig, XingFOrderConfig> payment = new XingFPayMent<XingFMerchantConfig, XingFOrderConfig>();
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			XingFMerchantConfig merchant = new XingFMerchantConfig();
			merchant.setService(__agument.get("service").toString());
			merchant.setMerId(__agument.get("merId").toString());
			merchant.setMerKey(__agument.get("merKey").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setExpireTime(__agument.get("timeout").toString());
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/XingFCallback");

			XingFOrderConfig order = new XingFOrderConfig();
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(ordermount.doubleValue() + "");
			order.setOrderDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			order.setClientIP(takeDepositRecord.getTraceip());
			order.setBankId(bankNo);
			order.setExtra(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
			order.setOrderSummary("Recharge");

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("星付支付接口报错", e);
		}
		return null;
	}

	/**
	 * 星付支付回调
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(XingFAppConstants.callback_tradeNo)
	@RequestMapping("/XingFCallback")
	public String XingFCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================XingFCallback==================================================\n"
							+ params);
			// 获取支付结果
			String status = params.get(XingFAppConstants.callback_status).toString();
			String orderId = params.get(XingFAppConstants.callback_tradeNo).toString();
			addCallbackLog(params, "星付支付", orderId, status);
			if ("1".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String md5Key = map.get("merKey").toString();

						if (XingFSave.checkSign(params, md5Key)) {
							log.Debug("签名校验通过！");

							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("星付支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("星付支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "SUCCESS";

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "SUCCESS";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("星付支付回调报错", e);
		}
		return "fail";
	}

	/**
	 * 多多支付
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	private String DuoPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<DuoMerchantConfig, DuoOrderConfig> payment = new DuoPayMent<DuoMerchantConfig, DuoOrderConfig>();
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			DuoMerchantConfig merchant = new DuoMerchantConfig();
			merchant.setMerId(__agument.get("merId").toString());
			merchant.setMd5Key(__agument.get("merKey").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/DuoCallback");

			DuoOrderConfig order = new DuoOrderConfig();
			order.setOrderId(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(new DecimalFormat("0.00").format(ordermount));
			order.setBankCode(bankNo);
			order.setProInfo("Recharge");
			order.setRemark("Remark");

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("多多支付接口报错", e);
		}
		return null;
	}

	/**
	 * 多多支付回调
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Token(DuoAppConstants.callback_OrdId)
	@RequestMapping("/DuoCallback")
	public String DuoCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================DuoCallback==================================================\n"
							+ params);
			// 获取支付结果
			String status = params.get(DuoAppConstants.callback_ResultCode).toString();
			String orderId = params.get(DuoAppConstants.callback_OrdId).toString();
			addCallbackLog(params, "多多支付", orderId, status);
			if ("success002".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String merKey = map.get("merKey").toString();

						if (DuoUtil.checkSign(params, merKey)) {
							log.Debug("签名校验通过！");

							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("多多支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("多多支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success|9999";

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "success|9999";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("多多支付回调报错", e);
		}
		return "fail";
	}

	/**
	 * 信付通接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	private String XFTPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<XFTMerchantConfig, XFTOrderConfig> payment = new XFTPayMent<XFTMerchantConfig, XFTOrderConfig>();
			String bankNo = (object.get("paymenttypebankcode") == null) ? ""
					: (object.get("paymenttypebankcode").toString());

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			XFTMerchantConfig merchant = new XFTMerchantConfig();
			merchant.setMerId(__agument.get("merId").toString());
			merchant.setMerKey(__agument.get("merKey").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/XFTCallback");

			XFTOrderConfig order = new XFTOrderConfig();
			order.setOrderId(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(new DecimalFormat("0.00").format(ordermount));
			order.setBankCode(bankNo);
			if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.信付通网银.value))
				order.setIsApp("pc");
			else
				order.setIsApp("app");

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("信付通支付接口报错", e);
		}
		return null;
	}

	@ResponseBody
	@Token(XFTAppConstants.callback_order_no)
	@RequestMapping("/XFTCallback")
	public String XFTCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================XFTCallback==================================================\n"
							+ params);
			// 获取支付结果
			String status = params.get(XFTAppConstants.callback_is_success).toString();
			String orderId = params.get(XFTAppConstants.callback_order_no).toString();
			addCallbackLog(params, "信付通支付", orderId, status);
			if ("T".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String merKey = map.get("merKey").toString();

						if (XFTUtil.checkSign(params, merKey)) {
							log.Debug("签名校验通过！");

							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("信付通支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("信付通支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "success";

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("信付通支付回调报错", e);
		}
		return "fail";
	}

	/**
	 * 畅汇接口
	 * 
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	private String CHPayment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<CHMerchantConfig, CHOrderConfig> payment = new CHPayMent<CHMerchantConfig, CHOrderConfig>();
			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
					.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			CHMerchantConfig merchant = new CHMerchantConfig();
			merchant.setMerCode(__agument.get("merCode").toString());
			merchant.setSha1Key(__agument.get("sha1Key").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());

			CHOrderConfig order = new CHOrderConfig();
			order.setP2_Order(takeDepositRecord.getOrdernumber());
			order.setP4_Amt(new DecimalFormat("0.00").format(ordermount));
			order.setP8_Url(getRootPath(request, takeDepositRecord) + "/TPayment/CHCallback");
			order.setPg_BankCode(object.get("paymenttypebankcode").toString()); // 网银支付要用到,同时存入支付的类型

			order.setPa_FrpId(__etp.getThirdpartypaymenttypecode()); // 支付类型
			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("畅汇支付接口报错", e);
		}
		return null;
	}

	@ResponseBody
	@Token("r6_Order")
	@RequestMapping("/CHCallback")
	public String CHCallback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println(
					"==================================================CHCallback==================================================\n"
							+ params);
			// 获取支付结果
			String status = params.get("r1_Code").toString(); // 1:成功,9999:失败
			String orderId = params.get("r6_Order").toString();
			addCallbackLog(params, "畅汇支付", orderId, status);
			if ("1".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService
								.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String shalKey = map.get("sha1Key").toString();

						if (CHPayUtil.checkSign(params, shalKey)) {
							log.Debug("签名校验通过！");
							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("畅汇支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("畅汇支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");
							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);
							return "success";
						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "success";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("畅汇支付回调报错", e);
		}
		return "fail";
	}
	/**
	 * 神奇支付
	 * @param request
	 * @param object
	 * @param ordermount
	 * @param __etp
	 * @param takeDepositRecord
	 * @return
	 */
	private String JH2Payment(HttpServletRequest request, Map<String, Object> object, BigDecimal ordermount,
			EnterpriseThirdpartyPayment __etp, TakeDepositRecord takeDepositRecord) {
		try {
			PayInterface<JH2MerchantConfig, JH2OrderConfig> payment = new JH2PayMent<JH2MerchantConfig, JH2OrderConfig>();
			String bankNo = (object.get("paymenttypebankcode") == null) ? "" : (object.get("paymenttypebankcode").toString());

			Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());

			JH2MerchantConfig merchant = new JH2MerchantConfig();
			merchant.setPayKey(__agument.get("payKey").toString());
			merchant.setMerKey(__agument.get("merKey").toString());
			merchant.setPayUrl(__agument.get("payUrl").toString());
			merchant.setProductType(__agument.get("type").toString());
			merchant.setReturnUrl(getRootPath(request, takeDepositRecord) + "/TPayment/success");
			merchant.setNotifyUrl(getRootPath(request, takeDepositRecord) + "/TPayment/JH2Callback");

			JH2OrderConfig order = new JH2OrderConfig();
			order.setOrderNo(takeDepositRecord.getOrdernumber());
			order.setOrderAmount(ordermount.toString());
			order.setOrderTime(JH2Util.getOrderTime());
			order.setOrderIP(takeDepositRecord.getTraceip());
			order.setProductName("RCHARGE");
			order.setRemark("REMARK");
			if (__etp.getThirdpartypaymenttypecode().equals(Enum_ThirdpartyPaymentType.神奇支付.value)) {
				order.setBankAccountType("PRIVATE_DEBIT_ACCOUNT");
				order.setBankCode(bankNo);
				order.setMobile("");
				if (WebInfoHandle.checkAgentIsMobile(request))
					order.setMobile("1");
			} else {
				merchant.setProductType(bankNo);
			}

			return payment.save(merchant, order);
		} catch (Exception e) {
			log.Error("神奇支付接口报错", e);
		}
		return null;
	}
	
	@ResponseBody
	@Token(JH2AppConstants.callback_outTradeNo)
	@RequestMapping("/JH2Callback")
	public String JH2Callback(HttpServletRequest request) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			System.err.println("==================================================JH2Callback==================================================\n"+ params);
			// 获取支付结果
			String status = params.get(JH2AppConstants.callback_tradeStatus).toString();
			String orderId = params.get(JH2AppConstants.callback_outTradeNo).toString();
			addCallbackLog(params, "神奇支付", orderId, status);
			if ("SUCCESS".equals(status)) {
				// 根据支付成功返回的订单号查询
				TakeDepositRecord __record = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderId);
				log.Debug("订单号:【" + orderId + "】查询充值记录:" + __record);
				if (null != __record) {
					if (__record.getOrderstatus() == ((byte) Enum_orderstatus.审核中.value)) {
						log.Debug("订单号:【" + orderId + "】获取账户解密参数...");
						Map<String, Object> map = enterpriseThirdpartyPaymentAgumentService.takeEnterprisePayAgument(__record.getEnterprisepaymentaccount());
						String merKey = map.get("merKey").toString();

						if (JH2Util.checkSign(params, merKey)) {
							log.Debug("签名校验通过！");

							DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();

							handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
							handles.setAuditdesc("神奇支付回调");
							handles.setAssignedtocode(__record.getEnterprisepaymentbank());
							handles.setAssignedtoaccount("神奇支付回调");
							depositWithdralOrderDelegateService.tc_autoHandleDeletegate(__record, handles);
							log.Debug("订单号:【" + orderId + "】订单审核成功");

							/************************ 回调后执行充值返利活动处理 ************************/
							saveingActivityVerify(__record, handles);

							return "SUCCESS";

						} else {
							log.Debug("订单号:【" + orderId + "】该笔订单 签名校验失败");
						}
					} else {
						log.Debug("订单号:【" + orderId + "】该笔订单已审核,重复请求无效");
						return "SUCCESS";
					}
				} else {
					log.Debug("订单号:【" + orderId + "】该笔订单不存在");
				}
			} else {
				log.Debug("订单号:【" + orderId + "】该笔订单，失败！");
			}
		} catch (Exception e) {
			log.Error("神奇支付回调报错", e);
		}
		return "FAIL";
	}
	/**
	 * 页面检测订单是否完成
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkOrder")
	public Map<String, Object> isOrderComplete(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", Constant.BooleanByte.NO);
		try {
			String ordernumber = request.getParameter("ordernumber");
			if (completedOrdernumberMap.containsKey(ordernumber)) {
				completedOrdernumberMap.remove(ordernumber);
				result.put("status", Constant.BooleanByte.YES);
			}
		} catch (Exception e) {
			log.Error("检查订单状态报错", e);
		}
		return result;
	}

	/**
	 * 支付完成的订单号集合 回调检测完成后将订单号加入这里面 检验订单是否完成，如果已经完成就将其删除
	 */
	private static Map<String, Long> completedOrdernumberMap = new HashMap<String, Long>();

	/**
	 * 将支付完成的订单号加入完成订单集合
	 * 
	 * @param ordernumber
	 */
	private synchronized void addCompletedOrdernumber(String ordernumber) {
		Long timestamp = System.currentTimeMillis() / 1000;

		Set<String> keys = completedOrdernumberMap.keySet();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Long value = completedOrdernumberMap.get(key);
			// 删除10分钟前的数据
			if (timestamp - value > 600) iterator.remove();
		}
		// 将新的订单号加入
		completedOrdernumberMap.put(ordernumber, timestamp);
	}

	/**
	 * 生成锐付订单号
	 * 
	 * @param originalOrderNo
	 * @param __etp
	 * @return
	 * @throws Exception
	 */
	private String getRFOrderNo(EnterpriseThirdpartyPayment __etp) throws Exception {
		// 获取RF商家订单号前缀
		Map<String, Object> __agument = enterpriseThirdpartyPaymentAgumentService
				.takeEnterprisePayAgument(__etp.getEnterprisethirdpartycode());
		String goods = __agument.get("goods").toString();

		// 根据时间戳生成orderNo
		String orderNo = String.valueOf(System.currentTimeMillis());
		return goods.concat(orderNo);
	}

	/**
	 * 将回调写入日志表
	 * 
	 * @param request
	 *            回调参数
	 * @param paymentName
	 *            支付名称
	 * @param ordernumber
	 *            订单号
	 * @param result
	 *            订单结果
	 */
	private void addCallbackLog(Map<String, Object> request, String paymentName, String ordernumber, String result) {
		try {
			userLogsService.addActivityBetRecord(new UserLogs("", "", "", UserLogs.Enum_operatype.存取款业务,
					"【" + paymentName + "】发起回调", ordernumber, "回调状态：" + result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将回调写入日志表
	 * 
	 * @param request
	 *            回调参数
	 * @param paymentName
	 *            支付名称
	 * @param ordernumber
	 *            订单号
	 * @param result
	 *            订单结果
	 */
	private void addCallbackLog2(Map<String, String> request, String paymentName, String ordernumber, String result) {
		try {
			userLogsService.addActivityBetRecord(new UserLogs("", "", "", UserLogs.Enum_operatype.存取款业务,
					"【" + paymentName + "】发起回调", ordernumber, "回调状态：" + result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("test")
	public String test(Model model) {
		model.addAttribute("url", "https://myun.tenpay.com/mqq/pay/qrcode.html?_wv=1027&_bid=2183&t=6V32c6bbf10cdedd7093262d5d346131");
		return "/payment/show_qrcode";
	}
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}