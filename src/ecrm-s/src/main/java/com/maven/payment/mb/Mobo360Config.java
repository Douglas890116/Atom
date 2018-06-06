package com.maven.payment.mb;

/**
 * 
 * 类名：Mobo360Config
 * 功能：基础配置类
 * 详细：设置商户相关信息及证书文件和通知地址等
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * 该代码仅供学习和研究接口使用，只是提供一个参考。
 *
 */

public class Mobo360Config {
	// 请选择签名类型， MD5、CER(证书文件)、RSA
	public static final String SIGN_TYPE = "MD5";
//	public static final String SIGN_TYPE = "CER";
//	public static final String SIGN_TYPE = "RSA";

	//******************配置商户基本信息
	// 商户ID
	public static String PLATFORM_ID = "MerchTest";
	// 商户帐号
	public static String MERCHANT_ACC = "210001110100250";
	//******************配置商户基本信息***结束
	
	//******************使用MD5签名方式需配置下列参数
	// MD5签名的key值
	public static String MD5_KEY = "22c41d776c24deddca95b1709a88f04b";
	//******************MD5签名方式需配置***结束
	
	//******************使用证书签名方式需配置下列参数
	// 商户私钥文件--用于商户数据签名
	public static String PFX_FILE = "c:/temp/test/merchtest.pfx";
	// 摩宝支付公钥文件--用于摩宝支付返回数据验签
	public static String CERT_FILE = "c:/temp/test/mobaopay.cer";
	// 私钥文件密码
	public static String PASSWD = "merchtest";
	//******************证书签名方式需配置***结束

	//******************使用RSA签名方式需配置下列参数
	// 用于签名的商户RSA私钥
	public static String RSA_MERCH_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKnLEkZ8AZJWjMYTJwXarrAXFPkEdBvDo0rMDlOGXCRYyqG1azUBiksTnrbMiwseJ8qvLp6daISdTulj3JA5Tt0ENh9T2atzFDFsJVMVWJ69XLJ9ziMOg4cDvu9oksaUOv+dTx1Ek/HXGkPCWbVJFUAjk8Va7dkL8IAkmaSRwpcdAgMBAAECgYEAhYGqVgetiK5LNHfcyCqiDs2nbQIGdcpHvElkvmI4U1AJzEsFCAG1BsFfm6aLcet9KE7Enm1wwE2cvcEKrdCR48g7lTci2KK/wPZdFVIR1hzVEretcTUBFH544R/DR/hImIlNyak+5KY1DkPHDYSdLUxOikJ1SSGyIn1TEKRNYLUCQQDiAFMUdGmjlFRBVgnhR/ksdp1vcc3tWxkRY4TeL4uVMGlrDD0L2bA1w5njseNUm05pB1qJS8RVTD18Q7TO3wlLAkEAwFTCq8O/UjeqyElS1dksqMiOLeamH6lCVfxzvi0Amx1STP8seTU7XDoPpIPL6mr7PqkE/0U2nUd+giKHcZ/INwJBAI7TF64Al9Y54jlcL2hAvPbdi0cny7Up8iCsHQbxUywYaTauiFHZ4+NGVxWvkPQiJh53+D52NICXavACNdza1RsCQQCvFvq5/9PWd1METqwYfkMHzLyS0Nz/CcmYGeEMik945rKb4dmM5ocJqNnAvTMQTyA2pQwlj87uU725ntGLARj1AkBUFuI8cVqnW+7zhR45Njzmg4MsaeyDikdFyKPlm5s+S3O4PSI3X0d/KeKc9YEtWZbOgSXVfkbzO2QLOnql+QrQ";
	// 用于验签的摩宝支付RSA公钥
	public static String RSA_MBP_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1AhO/+OHKSTniibPQ0MceLMZaWqIqkcrayUrl4muc//+wXXZjSCKOTb1BnCAcCDK/IaUZ97IjA3vmvLjUjmRxd7dQ7YiwD58QUn8Z637WH0Si6KjnflUyns4OaoOvurCT4kWm5GlE29HqHhqQlCGm2ziHolsxnoUQwnmCTjHBnQIDAQAB/44cpJOeKJs9DQxx4sxlpaoiqRytrJSuXia5z//7BddmNIIo5NvUGcIBwIMr8hpRn3siMDe+a8uNSOZHF3t1DtiLAPnxBSfxnrftYfRKLoqOd+VTKezg5qg6+6sJPiRabkaUTb0eoeGpCUIabbOIeiWzGehRDCeYJOMcGdAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEARLoy5yzBJ2DIF9tsMTf+yVwMh05Qt9cjkuw4+zhKUOrhkkd7zefGzcMujEmPz51xxVvJNaFCu2z07yseEkXRZVvVO26xxAOjKbiWyYz+aOAiw/PmxYIE6xUw0xAVzUSJFJnuXFZdZBNG7Mq1J90N12igjhlPX62hIWqfCi2ffJA=";
	//******************RSA签名方式需配置***结束

	// 通知地址
	public static String MERCHANT_NOTIFY_URL = "http://192.168.31.104:8080/MobaoPayExample/callBack.jsp";
	//public static String MERCHANT_NOTIFY_URL = "http://127.0.0.1:8080/MobaoPayExample/callBack.jsp";
	// 摩宝支付网关
	// 测试地址
	public static String MOBAOPAY_GETWAY = "https://trade.mobaopay.uat/cgi-bin/netpayment/pay_gate.cgi";
	// 正式地址
	//public static String MOBAOPAY_GETWAY = "https://trade.mobaopay.com/cgi-bin/netpayment/pay_gate.cgi";
	
	// 摩宝支付接口版本
	public static final String MOBAOPAY_API_VERSION = "1.0.0.0";

	// 接口名称
	public static final String MOBAOPAY_APINAME_PAY = "WEB_PAY_B2C";
	public static final String MOBAOPAY_APINAME_QUERY = "MOBO_TRAN_QUERY";
	public static final String MOBAOPAY_APINAME_REFUND = "MOBO_TRAN_RETURN";

}
