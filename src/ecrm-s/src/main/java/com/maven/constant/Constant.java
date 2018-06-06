package com.maven.constant;
/**
 * 常用的常量字段
 */
public class Constant {
	
 /* ================================================================================================*/
	
  /** 请求间隔时间Session */
  public static final String USER_REQUEST_INTERVAL = "ERCM_USER_REQUEST_INTERVAL";
  /** 用户Session   */
  public static final String USER_SESSION = "ERCM_USER_SESSION";
  /** 企业游戏列表 */
  public static final String ENTERPRISE_GAMES = "ERCM_ENTERPRISE_GAMES";
  /** 用户上级股东编码Session   */
  public static final String USER_SHAREHOLDER = "ERCM_USER_SHAREHOLDER";
  /** 系统限制权限Session */
  public static final String SYSTEM_PSERSSION = "ERCM_SYSTEM_PSERSSION";
  /** 用户权限Session */
  public static final String USER_PSERSSION = "ERCM_USER_PSERSSION";
  /** 工作流Session */
  public static final String USER_WORKING_FLOW = "USER_WORKING_FLOW";
  /** 是否含有存款工作流Session */
  public static final String USER_WORKING_FLOW_DEPOSIT = "USER_WORKING_FLOW_DEPOSIT";
  /** 是否含有取款工作流Session */
  public static final String USER_WORKING_FLOW_TAKE = "USER_WORKING_FLOW_TAKE";
  
  /** 用户所有下级 */
  public static final String USER_JUNIOR = "USER_JUNIOR";
  /** 关联菜单 */
  public static final String MENU_RELATION = "MENUS";
  /** 好赢APP Md5密钥 */
  public static final String HY_APP_MD5Key = "HY.APP.MD5Key";
  /** 好赢APP AES密钥 */
  public static final String HY_APP_AESKey = "HY.APP.AESKey";
  /** Web 模板端 Md5 密钥 */
  public static final String WEB_TEMPLATE_MD5Key = "WEB.TEMPLATE.MD5Key";
  /** Web 模板端 AES 密钥 */
  public static final String WEB_TEMPLATE_AESKey = "WEB.TEMPLATE.AESKey";
  
  /** H5 模板端 Md5 密钥 */
  public static final String H5_TEMPLATE_MD5Key = "H5.TEMPLATE.MD5Key";
  /** H5 模板端 AES 密钥 */
  public static final String H5_TEMPLATE_AESKey = "H5.TEMPLATE.AESKey";
  
  
  /** 隐私数据权限 */
  public static final String PRIVATE_DATA_PSERSSION = "PRIVATE_DATA_PSERSSION";
  /** 待出款工作流编码 */
  public static final String DCKGZLBM = "00000000";
  /** 自动出款工作流编码 */
  public static final String ATKGZLBM = "00000001";
  /** 语言 */
  public static final String LANGUAGE = "LANGUAGE";
  
  /* ================================================================================================*/
  /** 请求报错 异常跳转页面 */
  public static final String PAGE_ACTIONFAILD = "/error/actionFaild";
  /** 解密失败 异常跳转页面 */
  public static final String PAGE_DECODEREFUSE = "/error/decodeRefuse";
  /** 未通过逻辑校验 异常跳转页面 */
  public static final String PAGE_LOGICVALIDATEFIAL = "/error/logicValidateFial";
  /** 无页面访问权限 异常跳转页面 */
  public static final String PAGE_NOPRIVILEGE = "/error/noPrivilege";
  /** 传入参数错误 异常跳转页面 */
  public static final String PAGE_PARAMSERROR = "/error/paramsError";
  /** 操作成功 跳转页面 */
  public static final String PAGE_SUCCESS = "/error/success";
  /** 身份不匹配 */
  public static final String PAGE_IDENTITY_NO_MATCH = "/error/IdentityNoMatching";
  /** 错误页面 */
  public static final String PAGE_ERROR = "/error/error";
  /** 重复请求页面 */
  public static final String PAGE_INTERVAL = "/error/requestInterval";
  
  /** Ajax报错 异常提示 */
  public static final String AJAX_ACTIONFAILD = "操作失败，请稍后尝试";
  /** Ajax解密失败 异常提示 */
  public static final String AJAX_DECODEREFUSE = "解密验证未通过，禁止操作";
  
  /* ================================================================================================*/
  
  /** 
   * Boolean类型字段说明 
   * */
  public interface BooleanByte {
    public static Integer YES = 1;
    public static Integer NO = 0;
  }
  
  /**
   * 数据状态枚举
   * @author Administrator
   */
  public enum Enum_DataStatus{
	正常(1,"正常"),
	删除(-1,"删除");
	  public int value;
      public String desc;
      private Enum_DataStatus(int _value, String _desc) {
          this.value = _value;
          this.desc = _desc;
      }
      public String getDesc() {  
          return desc;  
      } 
      public int getValue() {  
          return value;  
      }  
	}
  
  
}
