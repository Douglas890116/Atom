# API 文档

## 文檔說明


## 版本記錄

|序號|版本|更新內容|更新時間|操作人|審核人|
|:--:|:---|:---|:---|:---|:---|
|1|V1.0.0|創建文檔|2018-06-17|Cloud.d||

## 錯誤碼列表

### 通用錯誤碼

* 1 請求成功
* 0 請求失敗
* 1001 操作失敗，請稍後嘗試
* 1002 參數錯誤
* 1003 邏輯事務錯誤

### 詳細錯誤碼
* 10000 用户不存在
* 10001 输入金额异常
* 10002 账户余额不足
* 10003 非会员用户
* 10004 资金账户不存在
* 10005 用户身份异常
* 10006 资金密码错误
* 10007 登录密码错误
* 10008 参数错误,修改失败
* 10009 该域名未注册
* 10010 用户名已存在
* 10011 该银行卡已被绑定
* 10012 企业银行卡不存在
* 10012 用户银行卡不存在
* 10013 银行卡未锁定
* 10013 银行卡未解锁
* 10014 解密失败
* 10015 该域名已被占用,请使用其他域名
* 10016 数据异常
* 10017 默认主域名禁止删除
* 10018 未设置用户洗码
* 10019 域名格式错误
* 10020 平台维护或升级中！请联系客服咨询
* 10021 用户名或密码错误
* 10022 游戏API系统异常
* 10023 账号或密码错误
* 10024 第三方即时支付账号不存在
* 10025 游戏类型错误
* 10026 未知的API许可
* 10027 该域名已被禁用
* 10028 用户名长度需大于6位,小于12位
* 10029 该银行卡需大于16位,小于19位
* 10030 您每天的提款次数为{0}笔,今天的提款次数已达到上限
* 10031 您每天的提款上限为{0}元,今天的提款金额已达到上限
* 10032 该品牌不存在
* 10033 {0}参数类型不存在
* 10034 未启用该游戏，请联系客服
* 10035 下分失败
* 10036 暂不支持
* 10037 非代理用户
* 10038 开户名称不得包含数字
* 10039 账号已禁用
* 100391 账号尚未激活，请先激活！
* 10040 不得出现中文汉字或中文标点符号的全角字符
* 10041 为确保取款操作成功，只能按10的倍数取款[整数]，个位数必须是0
* 10050 您是信用代用户，充值取款请联系您的上级代理！
* 10050 游戏密码同步中
* 10051 短信验证码错误
* 10052 该手机号已注册过
* 10053 元宝账户不存在

### 遊戲接口錯誤碼
* 0 成功
* -1 失敗
* 100 賬號已存在
* 1000 系统错误，请检查系统日志
* 1001 接口调用错误
* 1002 参数错误
* 1003 逻辑事务异常
* 1004 不支持的接口
* 10000 用户长度不能超过30位
* 10001 用户密码必须是8位数的数字
* 10002 缺少必须的参数，请检查

## API接口

### 接口說明

#### 加密方式
API請求採用[AES加密]和[MD5簽名]兩種方式

#### 請求參數
* params, 加密業務參數, 將業務參數的鍵值對(key=value)以&連接后, 進行AES加密, 再做URLEncode處理
* signature, 業務參數簽名, 將業務參數的鍵值對(key=value)以&連接后, 緊接MD5密匙, 然後做MD5簽名
* enterprisecode, 每個企業編碼, 用于配对对应的AES秘鑰與MD5秘鑰

 AES秘鑰與MD5秘鑰, 由平台生成, 每一個企業編碼對應一對AES秘鑰與MD5秘鑰

#### API請求樣例
* originParams = "name=Lancelot&gender=male&age=30&job=saber";
* params = URLEncoder(AES(originParams, AES_KEY));
* signature = MD5(originParams + MD5_KEY);

請求鏈接: ```http://域名/路徑?params=${params}&signature=${signature}&enterprisecode=${enterprisecode}```

#### 返回數據
接口返回數據為Json格式, 主要包含code與info兩個主要值

* 當code值為1時, 表示請求成功, 那麼info里的值就是具體的數據, 或者提示消息

```
{
"code" : "1",
"info" : Object
}
```

* 當code值不為1時, 表示出現錯誤, 那麼info里的值就是錯誤信息

```
{
"code" : "1001",
"info" : "操作失敗，請稍後嘗試"
}
```
--------------------------------------------------
**注: 以下接中的[參數]都是業務參數, 需要加密以及簽名**
### 會員站點相關接口

#### 获取单个网站标识
**此接口無需加密**
```
路徑: Domain/TakeDomainConfig
參數: domain=${查詢的會員站點域名}
返回結果: {
    "code":"1",
    "info":{
        "enterprisecode":${企业编码},
        "domain_employeecode":${当前域名所属用户编码},
        "templatetype":${模板类型: XJW-现金网模板, },
        "logopath":${网站Logo地址},
        "agentsite":${代理站点},
        "paycallbackurl":${支付回调网址},
        "sign":${模板编码},
        "links":${推广域名, 数组类型},
        "domain_displayalias":${当前域名所属用户昵称},
        "brandname":${品牌名称},
        "brandcode":${品牌编码},
        "domain_loginaccount":${当前域名所属用户账号}
    }
}
示例: {
    "code":"1",
    "info":{
        "enterprisecode":"EN003Y",
        "domain_employeecode":"E000IY3X",
        "templatetype":"XJW",
        "logopath":"http://img.hyzonghe.net:80/uploadfiles/logo/1491299372085.png",
        "agentsite":"boya98.com",
        "paycallbackurl":"http://127.0.0.1:9090/ecrm-api/",
        "sign":"MB2017040401",
        "links":["boya97.com"],
        "domain_displayalias":"byshichang",
        "brandname":"博亚娱乐",
        "brandcode":"EB0000BI",
        "domain_loginaccount":"byshichang"
    }
}
```
#### 获取企业对应的AES_KEY和MD5_KEY
此接口為固定通信接口, 加密/簽名的秘鑰為固定的系統給出, 並且需要用次對秘鑰對返回的結果進行解密/驗簽, 
返回的數據為: **${MD5_KEY}&${AES_KEY}** 的加密與簽名, 需要前端進行解密/驗簽
```
路徑: Domain/enterpriseInfo
參數: enterprisecode=${企業編碼}
返回結果: {
    "code":"1",
    "info":{
        "signature":${signature = MD5(${MD5_KEY}&${AES_KEY} + ${FIXED_MD5_KEY})},
        "params":${params = URLEncoder(AES(${MD5_KEY}&${AES_KEY}, ${FIXED_AES_KEY}))}
    }
}
示例:{
    "code":"1",
    "info":{
        "signature":"d44a572203c6cfc4670b6d3c26367ec6",
        "params":"lVHEeFqK%2FiBD%2F1YiLDZNFAYzaqEsxCsNPbiRwrJlm13%2BnDnjwgT4dkCCzYgNcsnT"
    }
}
```
#### 获取所有网站标识
```
路徑: Domain/TakeAllDomainConfig
參數: enterprisecode=${企業編碼}
返回結果: {
    "code":"1",
    "info":[{
        "enterprisecode":${企业编码},
        "templatetype":${模板类型: XJW-现金网模板, },
        "logopath":${网站Logo地址},
        "paycallbackurl":${支付回调网址},
        "sign":${模板编码},
        "links":${推广域名, 数组类型},
        "brandname":${品牌名称},
        "brandcode":${品牌编码},
    }]
}
示例: {
	"code": "1",
	"info": [{
		"enterprisecode": "EN0030",
		"templatetype": "XJW",
		"logopath": "http://img.hyzonghe.net:80/uploadfiles/logo/1500946824903.gif",
		"paycallbackurl": "http://127.0.0.1:9090/ecrm-api/",
		"sign": "MB2017060321C",
		"links": [
		"321dw.com",
		"000h.321dw.com",
		"0010.321dw.com",
		"0011.321dw.com",
		"002a.321dw.com",
		"008b.321dw.com",
		"008c.321dw.com",
		"008e.321dw.com",
		"008f.321dw.com",
		"008g.321dw.com",
		"008h.321dw.com",
		"00ah.hui.8896.com",
		"00c1.hui.8896.com",
		"00cf.hui.8896.com",
		"00cv.hui.8896.com",
		"xr233.com",
		"www.xr233.com",
		"test.xr233.zhpt"],
		"brandname": "百乐门",
		"brandcode": "EB0000AS"
	},
	{
		"enterprisecode": "EN003Y",
		"templatetype": "XJW",
		"logopath": "http://img.hyzonghe.net:80/uploadfiles/logo/1491299372085.png",
		"paycallbackurl": "http://127.0.0.1:9090/ecrm-api/",
		"sign": "MB2017040401",
		"links": ["test.boya.zhpt",
		"00gp.boya.in997.com",
		"boya97.com",
		"00jw.boya97.com",
		"00jx.boya97.com",
		"00jz.boya97.com",
		"00km.boya97.com",
		"boya.in997.com",
		"00l9.boya97.com",
		"00ld.boya97.com",
		"00lf.boya97.com",
		"00ll.boya97.com",
		"00m1.boya97.com",
		"00m2.boya97.com",
		"00ml.boya97.com",
		"00mm.boya97.com",
		"00n1.boya97.com",
		"00n3.boya97.com",
		"00n6.boya97.com",
		"00n7.boya97.com",
		"00n8.boya97.com",
		"00nt.boya97.com",
		"00nu.boya97.com",
		"00nv.boya97.com",
		"00nx.boya97.com",
		"00rw.boya97.com",
		"00rx.boya97.com",
		"www.boya97.com",
		"00se.boya97.com",
		"00sf.boya97.com",
		"00sh.boya97.com",
		"00t9.boya97.com",
		"00ts.boya97.com",
		"00tt.boya97.com",
		"00u4.boya97.com",
		"00yh.boya97.com",
		"00yi.boya97.com",
		"00yl.boya97.com",
		"00yn.boya97.com",
		"00yp.boya97.com"],
		"brandname": "博亚娱乐",
		"brandcode": "EB0000BI"
	}]
}
```
#### 系统公告
```
路徑: Notic/Notic
參數: enterprisecode=${企業編碼}&brandcode=${品牌編碼}&start=${分頁}&limit=${數量}
返回結果: {
	"code": "1",
	"info": [{
		"begintime": ${公告開始時間},
		"endtime": ${公告結束時間},
		"brandcode": ${品牌編碼, ALL-表示所有品牌},
		"brandname": ${品牌名稱},
		"content": ${公告內容},
		"createtime": ${公告創建時間},
		"datastatus": ${數據狀態, 1-為正常, 系統只會讀取為1的數據},
		"enterprisecode": ${企業編碼},
		"enterprisename": ${企業名稱},
		"limit": 0,
		"noticcode": ${公告編碼},
		"notictype": ${公告類型, 目前只有1},
		"start": 0,
		"title": "${公告標題}"
	}]
}
示例: {
	"code": "1",
	"info": [{
		"begintime": "2018-07-05 22:34:14",
		"brandcode": "EB0000BD",
		"brandname": "",
		"content": "尊敬的会员你们好，上周一到周日的反水已经全部反到您的会员账号上了哦~请您注意查收~谢谢",
		"createtime": "2017-08-07 15:02:06",
		"datastatus": "",
		"endtime": "2018-12-31 13:54:00",
		"enterprisecode": "EN003K",
		"enterprisename": "",
		"limit": 0,
		"noticcode": "NT000000004Z",
		"notictype": "1",
		"start": 0,
		"title": "反水通知"
	},
	{
		"begintime": "2018-07-05 22:31:31",
		"brandcode": "ALL",
		"brandname": "",
		"content": "欢迎来到金蛋娱乐城，现在存款有送免费红利，详情请咨询我们的在线客服人员！",
		"createtime": "2017-05-04 13:54:40",
		"datastatus": "",
		"endtime": "2018-12-31 13:54:00",
		"enterprisecode": "EN003K",
		"enterprisename": "",
		"limit": 0,
		"noticcode": "NT000000001X",
		"notictype": "1",
		"start": 0,
		"title": "【金蛋娱乐城】"
	}]
}
```
#### 企业联络方式
```
路徑: Domain/Contact
參數: brandcode=${品牌编码}
返回結果: {
	"code": "1",
	"info": {
		"qq": [{
			"contacttitle": ${聯繫方式標題},
			"content": ${聯繫方式類容},
			"contenttype": ${聯繫方式類型: value-直接展示, link-鏈接可點擊}
		}],
		"phone": [{
			"contacttitle": ${聯繫方式標題},
			"content": ${聯繫方式類容},
			"contenttype": ${聯繫方式類型: value-直接展示, link-鏈接可點擊}
		}],
		"live800": [{
			"contacttitle": ${聯繫方式標題},
			"content": ${聯繫方式類容},
			"contenttype": ${聯繫方式類型: value-直接展示, link-鏈接可點擊}
		}],
		"wechat": [{
			"contacttitle": ${聯繫方式標題},
			"content": ${聯繫方式類容},
			"contenttype": ${聯繫方式類型: value-直接展示, link-鏈接可點擊}
		}],
		"email": [{
			"contacttitle": ${聯繫方式標題},
			"content": ${聯繫方式類容},
			"contenttype": ${聯繫方式類型: value-直接展示, link-鏈接可點擊}
		}]
	}
}
示例: {
	"code": "1",
	"info": {
		"qq": [{
			"contacttitle": "客服QQ",
			"content": "123456789",
			"contenttype": "value"
		},
		{
			"contacttitle": "客服QQ",
			"content": "987654321",
			"contenttype": "value"
		}],
		"phone": [{
			"contacttitle": "客服电话",
			"content": "15512345678",
			"contenttype": "value"
		}],
		"live800": [{
			"contacttitle": "在线客服",
			"content": "http://f88.live800.com/live800/chatClient/chatbox.jsp?companyID=68010&configID=146087&jid=9613944475",
			"contenttype": "link"
		}],
		"wechat": [{
			"contacttitle": "客服微信",
			"content": "jinta7777",
			"contenttype": "value"
		}],
		"email": [{
			"contacttitle": "客服邮箱",
			"content": "cs@jintaa.com",
			"contenttype": "value"
		},
		{
			"contacttitle": "客服邮箱",
			"content": "aff@jintaa.com",
			"contenttype": "value"
		}]
	}
}
```
#### 获取手机验证码(需要手機短信接口)
```
路徑: User/getVerifycode
參數: brandcode=${品牌編碼}&phoneno=${手機號碼}
返回結果:
示例:
```
#### 获取品牌广告图
```
路徑: EnterpriseBrand/banner
參數: brandcode=${品牌編碼}&bannertype=${banner類型: PC-PC端Banner, H5-移動端Banner}
返回結果: {
	"code": "1",
	"info": [{
		"bannername": ${banner名稱},
		"bannertype": ${banner類型: H5-移動端, PC-手機端},
		"brandcode": ${品牌編碼},
		"brandname": ${品牌名稱},
		"enterprisecode": ${企業名稱},
		"imgpath": ${banner圖片地址},
		"linkpath": ${內容展示頁面地址},
		"lsh": ${編號},
		"ord": ${排序, 越小排越前}
	}]
}
示例: {
	"code": "1",
	"info": [{
		"bannername": "首创转盘",
		"bannertype": "H5",
		"brandcode": "EB0000BD",
		"brandname": "金塔娱乐城",
		"enterprisecode": "EN003K",
		"imgpath": "https://img.hyzonghe.net/uploadfiles/1507861813569.jpg",
		"linkpath": "http://contents.good-game-network.com/leaflet/ggpokersite/fishbuffet/zh-cn/?tz=&btag1=jintabet",
		"lsh": 239,
		"ord": 1
	},
	{
		"bannername": "扑克介绍朋友",
		"bannertype": "H5",
		"brandcode": "EB0000BD",
		"brandname": "金塔娱乐城",
		"enterprisecode": "EN003K",
		"imgpath": "https://img.hyzonghe.net/uploadfiles/1511333509615.png",
		"linkpath": "/promo.html",
		"lsh": 252,
		"ord": 1
	}]
}
```
--------------------------------------------------
### 會員玩家相關接口

#### 注册
```
路徑: User/register
參數: brandcode=${品牌编码}&loginaccount=${用户名}&loginpassword=${登陸密碼}&fundpassword=${資金密碼}&displayalias=${玩家暱稱}&domain=${註冊地址}
返回結果: {
    "code" : "1",
    "info" : "成功"
}
示例: {
    "code":"1003",
    "info":"密码应为数字和字符的组合"
}
```
#### 登录
```
路徑: User/login
參數: loginaccount=${用戶名}&loginpassword=${登陸密碼}&loginip=${登陸IP}&browserversion=${瀏覽器信息}&opratesystem=${操作系統信息}
返回結果: {
	"code": "1",
	"info": {
		"qq": ${用戶QQ號},
		"fundpassword": ${用戶是否設置自己密碼: true-已設置, false-未設置, 字符串類型},
		"employeetypecode": ${用戶類型編碼, 固定T003玩家類型},
		"alipay": ${用戶支付寶賬號},
		"logindatetime": ${之策時間},
		"parentemployeecode": ${上級用戶編碼},
		"wechat": ""用戶微信賬號,
		"loginaccount": ${用戶賬戶},
		"brandcode": ${品牌編碼},
		"lastlogintime": ${最後登錄時間},
		"phoneno": ${用戶電話號碼},
		"employeetypename": ${用戶類型名稱, 固定會員},
		"displayalias": ${用戶暱稱},
		"apiurl": ${接口地址},
		"phonestatus": ${手機號碼是否驗證: 1-已驗證, 0-未驗證},
		"last_fingerprintcode": ${上次登錄的指紋碼},
		"enterprisecode": ${企業編碼},
		"last_loginip": ${上次登錄IP},
		"employeecode": ${用戶編碼},
		"employeelevelcode": ${用戶等級編碼},
		"parentemployeeaccount": ${上級用戶賬戶},
		"email": ${用戶郵箱},
		"employeelevelname": ${用戶等級名稱}
	}
}
示例: {
	"code": "1",
	"info": {
		"qq": "",
		"fundpassword": "true",
		"employeetypecode": "T003",
		"alipay": "",
		"logindatetime": "2017-11-10 16:57:59",
		"parentemployeecode": "E000JVHL",
		"wechat": "",
		"loginaccount": "daihuan123",
		"brandcode": "EB0000BD",
		"lastlogintime": "2017-12-03 20:25:21",
		"phoneno": "13080552596",
		"employeetypename": "会员",
		"displayalias": "daihuan1",
		"apiurl": "http://127.0.0.1:9090/ecrm-api",
		"phonestatus": "0",
		"last_fingerprintcode": null,
		"enterprisecode": "EN003K",
		"last_loginip": "169.36.59.48",
		"employeecode": "E000JVHM",
		"employeelevelcode": "0034",
		"parentemployeeaccount": "adh888",
		"email": "282704771@qq.com",
		"employeelevelname": "一星会员"
	}
}
```
#### 根据用户编号获取用户信息
```
路徑: User/takeEmployee
參數: employeecode=${用户编码}
返回結果: {
	"code": "1",
	"info": {
		"fundpassword": ${用戶是否設置自己密碼: true-已設置, false-未設置, 字符串類型},
		"employeetypecode": ${用戶類型編碼, 固定T003玩家類型},
		"is_regedit_bag": ${是否已领取注册红包},
		"takeTimeOfDay": ${每日最多取款次数},
		"lastlogintime": ${最後登錄時間},
		"displayalias": ${用戶暱稱},
		"phonestatus": ${手機號碼是否驗證: 1-已驗證, 0-未驗證},
		"enterprisecode": ${企業編碼},
		"employeecode": ${用戶編碼},
		"employeelevelord": ${用戶等級級別},
		"parentemployeeaccount": ${上級用戶賬戶},
		"email": ${用戶郵箱},
		"employeelevelname": ${用戶等級名稱},
		"qq": ${用戶QQ號},
		"alipay": ${用戶支付寶賬戶},
		"logindatetime": ${用戶註冊時間},
		"parentemployeecode": ${上級用戶編碼},
		"wechat": ${用戶微信賬號},
		"loginaccount": ${用戶賬戶},
		"brandcode": ${品牌編碼},
		"phoneno": ${用戶電話號碼},
		"employeetypename": ${用戶類型名稱},
		"takeMoneyOfDay": ${每日最多取款总额},
		"apiurl": ${接口地址},
		"employeelevelcode": ${用戶等級編碼}
	}
}
示例: {
	"code": "1",
	"info": {
		"fundpassword": "true",
		"employeetypecode": "T003",
		"is_regedit_bag": "false",
		"takeTimeOfDay": 99,
		"lastlogintime": "2018-07-05 23:06:04",
		"displayalias": "daihuan1",
		"phonestatus": "0",
		"enterprisecode": "EN003K",
		"employeecode": "E000JVHM",
		"employeelevelord": "1",
		"parentemployeeaccount": "adh888",
		"email": "282704771@qq.com",
		"employeelevelname": "一星会员",
		"qq": "",
		"alipay": "",
		"logindatetime": "2017-11-10 16:57:59",
		"parentemployeecode": "E000JVHL",
		"wechat": "",
		"loginaccount": "daihuan123",
		"brandcode": "EB0000BD",
		"phoneno": "13080552596",
		"employeetypename": "会员",
		"takeMoneyOfDay": 10000,
		"apiurl": "http://127.0.0.1:9090/ecrm-api",
		"employeelevelcode": "0034"
	}
}
```
#### 修改登录密码
```
路徑: User/updatepwd
參數: employeecode=${用户编码}&oldloginpassword=${原始密碼}&newloginpassword=${新密碼}
返回結果: {
    "code":"1",
    "info":"成功"
}
示例: {
    "code":"1001",
    "info":"操作失敗，請稍後嘗試"
}
```
#### 修改资金密码
```
路徑: User/updatefpwd
參數: employeecode=${用戶編碼}&oldfundpassword=${原始資金密碼}&newfundpassword=${新資金密碼}
返回結果: {
    "code":"1",
    "info":"成功"
}
示例: {
    "code":"1003",
    "info":"密码应为数字+字符"
}
```
#### 更新会员信息
```
路徑: User/updateInfo
參數: employeecode=${用戶編碼}&qq=${用戶QQ號}&email=${用戶郵箱}&phoneno=${用戶電話}&verifycode=${手機驗證碼}
返回結果: {
    "code":"1",
    "info":"成功"
}
示例: {
    "code":"1003",
    "info":"密码应为数字+字符"
}
```
#### 获取企業会员等级
```
路徑: User/findEmployeeLovel
參數: enterprisecode=${企業編碼}
返回結果: {
	"code": "1",
	"info": [{
		"conditionlevel": ${等級條件, 充值多少錢, 就是該等級會員},
		"employeeLevelCode": ${等級編碼},
		"employeeLevelName": ${等級名稱},
		"enterpriseCode": ${企業編碼},
		"isdefault": ${是否是默認等級},
		"ord": ${排序, 越小越靠前},
		"sign": "",
		"takeMoneyOfDay": ${該等級下,每日最多可取款金額},
		"takeTimeOfDay": ${該等級下,每日最多可取款次數}
	}]
}
示例: {
	"code": "1",
	"info": [{
		"conditionlevel": "1-9999",
		"employeeLevelCode": "0034",
		"employeeLevelName": "一星会员",
		"enterpriseCode": "EN003K",
		"isdefault": "1",
		"ord": 1,
		"sign": "",
		"takeMoneyOfDay": 10000,
		"takeTimeOfDay": 99
	},
	{
		"conditionlevel": "10000-49999",
		"employeeLevelCode": "0035",
		"employeeLevelName": "二星会员",
		"enterpriseCode": "EN003K",
		"isdefault": "0",
		"ord": 2,
		"sign": "",
		"takeMoneyOfDay": 100000,
		"takeTimeOfDay": 99
	}]
}
```
#### 用户未读站内信数量
```
路徑: UserMessage/MessageCount
參數: employeecode=${用戶編碼}
返回結果: {
    "code":"1",
    "info":${數量}
}
示例: {
    "code":"1",
    "info":"34"
}
```
#### 用户未读站內信消息
```
路徑: UserMessage/SysMessage
參數: employeecode=${用戶編碼}
返回結果: {
	"code": "1",
	"info": {
		"rows": [{
			"acceptaccount": ${收信人賬戶},
			"acceptemployeecode": ${收信人用戶編碼},
			"brandcode": "",
			"enterprisecode": ${企業編碼},
			"messagecode": ${站內信編碼},
			"messagetextcode": ${信息內容編碼},
			"messagetype": ${站內信類型: 1-系統消息, 2-用戶消息},
			"readstatus": ${已讀狀態: 1-未讀, 2-已讀},
			"replaycode": ${回復編號},
			"sendemployeeaccount": ${發信人賬戶},
			"sendemployeecode": ${發信人用戶編碼},
			"sign": "",
			"text": {
				"content": "${站內信內容}",
				"datastatus": ${數據狀態: 1-正常, -1-刪除},
				"messagetextcode": ${消息內容編碼},
				"sendtime": ${發送時間}
			}
		}],
		"results": ${未讀消息數量}
	}
}
示例: {
	"code": "1",
	"info": {
		"rows": [{
			"acceptaccount": "daihuan123",
			"acceptemployeecode": "E000JVHM",
			"brandcode": "",
			"enterprisecode": "EN003K",
			"messagecode": 28894,
			"messagetextcode": 25967,
			"messagetype": "1",
			"readstatus": "1",
			"replaycode": 0,
			"sendemployeeaccount": "SERACSM",
			"sendemployeecode": "E000IXAL",
			"sign": "",
			"text": {
				"content": "您单号为8015110158893451264的取款订单已审核通过！",
				"datastatus": "1",
				"messagetextcode": 25967,
				"sendtime": "2017-11-18 22:42:07"
			}
		},
		{
			"acceptaccount": "daihuan123",
			"acceptemployeecode": "E000JVHM",
			"brandcode": "",
			"enterprisecode": "EN003K",
			"messagecode": 30883,
			"messagetextcode": 27956,
			"messagetype": "1",
			"readstatus": "1",
			"replaycode": 0,
			"sendemployeeaccount": "SERACSM",
			"sendemployeecode": "E000IXAL",
			"sign": "",
			"text": {
				"content": "您单号为8015118777110191089的取款订单已完成审批，请等待到账！",
				"datastatus": "1",
				"messagetextcode": 27956,
				"sendtime": "2017-11-28 22:10:56"
			}
		}],
		"results": 2
	}
}
```
#### 用户读取站内信的
```
路徑: UserMessage/updateSysMessage
參數: employeecode=${用戶編碼}&messagecode=${消息編碼}
返回結果: {
    "code":"1",
    "info":"标记成功"
}
示例: {
    "code":"1",
    "info":"标记成功"
}
```
--------------------------------------------------
### 遊戲相關接口

#### 进入游戏
```
路徑: Game/play
參數: brandcode=${品牌編碼}&employeecode=${用戶編碼}&gametype=${遊戲類型}
返回結果: {
    "code":"1",
    "info":${開始遊戲鏈接}
}
示例: {
    "code":"1003",
    "info":"平台维护或升级中！请联系客服咨询"
}
```
#### 獲取玩家余额總數
```
路徑: Game/balances
參數: employeecode=${用戶編碼}
返回結果:
示例:
```
#### 用戶所有遊戲及中心錢包餘額列表
```
路徑: Game/balancesAll
參數: employeecode=${用戶編碼}
返回結果:
示例:
```
#### 獲取玩家单个游戏余额
```
路徑: Game/balance
參數: employeecode=${用戶編碼}&gameType=${遊戲類型}
返回結果:
示例:
```
#### 游戏试玩
```
路徑: Game/tryPlay
參數: gametype=${遊戲編碼}
返回結果: {
    "code":"1",
    "info":${試玩遊戲鏈接}
}
示例: {
    "code":"1003",
    "info":"平台维护或升级中！请联系客服咨询"
}
```
#### 获取游戏平台状态
```
路徑: Game/gamestatus
參數: brandcode=${品牌編碼}&gameType=${遊戲類型}
返回結果: {
    "code":"1",
    "info":"true"
}
示例: {
    "code":"1003",
    "info":"平台维护或升级中！请联系客服咨询"
}
```
#### 获取游戏账号
```
路徑: User/takeEmployeeAccountOne
參數:
返回結果:
示例:
```
#### 获取全部游戏账号
```
路徑: User/takeEmployeeAccount
參數: employeecode=${用戶編碼}
返回結果:
示例:
```
#### 获取PT游戏分类
**此接口無需加密**
```
路徑: Game/PtGameMenu
參數: 無
返回結果:
示例:
```
#### 获取PT游戏分类详细信息
**此接口無需加密**
```
路徑: Game/PtGameDetailsMenu
參數: gameclasscode
返回結果:
示例:
```
#### 获取PT账号
```
路徑: Game/PtLogin
參數: employeecode=${用戶編碼}
返回結果:
示例:
```
#### 游戏纪录
```
路徑: GRecords/Records
參數: employeecode=${用戶編碼}&gametype=${遊戲類型}&start=${分頁}&limit=${每頁數量}
返回結果: {
    "code":"1",
    "info":{
        "winningCredits":${總投注額},
        "washCodeCredits":${有效投注額},
        "bettingCredits":${輸贏金額},
        "record":[${具體數據}],
        "count":0
        
    }
}
示例: {
    "code":"1",
    "info":{
        "winningCredits":null,
        "washCodeCredits":null,
        "bettingCredits":null,
        "record":[],
        "count":0
        
    }
}
```
#### 游戏手动上分接口
```
路徑: Game/upIntegralGame
參數: employeecode=${用戶編碼}&gametype=${遊戲類型}&brandcode=${品牌編碼}&money=${上分金額}
返回結果: {
    "code":"1",
    "info":"手動上分完畢"
}
示例: {
    "code":"1003",
    "info":"平台维护或升级中！请联系客服咨询"
}
```
#### 游戏手动下分接口
```
路徑: Game/downIntegralGame
參數: employeecode=${用戶編碼}&gametype=${遊戲類型}&brandcode=${品牌編碼}&money=${下分金額}
返回結果: {
    "code":"1",
    "info":"手動下分完畢"
}
示例: {
    "code":"1003",
    "info":"平台维护或升级中！请联系客服咨询"
}
```
#### 可用的游戏列表
```
路徑: GRecords/BrandGameAll
參數: brandcode=${品牌編碼}
返回結果: {
	"code": "1",
	"info": {
		"record": [{
			"android": "",
			"downloadurl": ${下載地址},
			"gamecode": ${遊戲編碼},
			"gamename": ${遊戲名稱},
			"gametype": ${遊戲類型},
			"h5": "",
			"ischoice": false,
			"iso": "",
			"picid": ${圖片編碼},
			"sort": ${排序}
		}],
		"count": ${數量}
	}
}
示例: {
	"code": "1",
	"info": {
		"record": [{
			"android": "",
			"downloadurl": "",
			"gamecode": "",
			"gamename": "BBIN波音",
			"gametype": "BBINGame",
			"h5": "",
			"ischoice": false,
			"iso": "",
			"picid": "pic002",
			"sort": 2
		},
		{
			"android": "",
			"downloadurl": "",
			"gamecode": "",
			"gamename": "AG游戏",
			"gametype": "TAGGame",
			"h5": "",
			"ischoice": false,
			"iso": "",
			"picid": "pic003",
			"sort": 3
		}],
		"count": 2
	}
}
```
--------------------------------------------------
### 資金相關接口

#### 添加用户银行卡
```
路徑: User/AddUBankCard
參數: employeecode=${用戶編碼}&fundpassword=${資金密碼}&paymentaccount=${賬戶卡號}&accountname=${賬戶名稱}&openningbank=${開戶行名稱}&bankcode=${銀行編碼}
返回結果:
示例:
```
#### 编辑银行卡
```
路徑: User/EditUBankCard
參數: employeecode=${用戶編碼}&informationcode=${用戶信息編碼}&fundpassword=${資金密碼}&paymentaccount=${賬戶卡號}&accountname=${賬戶名稱}&openningbank=${開戶行名稱}&qq=${用戶QQ號, 非必須}&skype=${用戶Skype, 非必須}&email=${用戶郵箱, 非必須}
返回結果:
示例:
```
#### 删除银行卡
```
路徑: User/DeleteUBankCard
參數: employeecode=${用戶編碼}&informationcode=${用戶信息編碼}&fundpassword=${資金密碼}
返回結果:
示例:
```
#### 查询银行卡
```
路徑: User/UBankCards
參數: employeecode=${用戶編碼}
返回結果:
示例:
```
#### 用户存款
```
路徑: Funds/Saving
brandcode=${品牌編碼}&employeecode=${用戶編碼}&orderamount=${存款金額}&enterpriseinformationcode=${用戶信息編碼}&employeepaymentbank=${支付用的銀行編碼}&employeepaymentaccount=${賬戶卡號}&employeepaymentname=${賬戶名稱}&traceip=${用戶IP}&ordercomment=${用戶留言}
參數:
返回結果:
示例:
```
#### 用户取款
```
路徑: Funds/Taking
參數: brandcode=${品牌編碼}&employeecode=${用戶編碼}&orderamount=${取款金額}&ordercomment=${用戶留言}&informationcode=${用戶信息編碼}&traceip=${用戶IP}&fundpassword=${資金密碼}
返回結果:
示例:
```
#### 存款记录
```
路徑: Fetch/SaveOrder
參數: brandcode=${品牌編碼}&employeecode=${用戶編碼}&orderstatus=${訂單狀態: 1-处理中,2-已处理,3-驳回,4-拒绝,5-待出款}&start=${分頁}&limit=${數量}&orderdate_begin=${開始時間}&orderdate_end=${結束時間}
返回結果:
示例:
```
#### 取款记录
```
路徑: Fetch/TakeOrder
參數: brandcode=${品牌編碼}&employeecode=${用戶編碼}&orderstatus=${訂單狀態: 1-处理中,2-已处理,3-驳回,4-拒绝,5-待出款}&start=${分頁}&limit=${數量}&orderdate_begin=${開始時間}&orderdate_end=${結束時間}
返回結果:
示例:
```
#### 用户账变记录
```
路徑: User/findAccountChange
參數: employeecode=${用戶編碼}&start=${分頁}&limit=${數量}&startDate=${開始時間}&endDate=${結束時間}
返回結果:
示例:
```
#### 查询会员时间段内的存取款、优惠等数据統計
```
路徑: Game/allMoney
參數: employeecode=${用戶編碼}
返回結果:
示例:
```
#### 获取收款银行
```
路徑: Funds/EBankCards
參數: enterprisecode=${企業編碼}
返回結果:
示例:
```
#### 获取基础银行信息
**此接口無需加密**
```
路徑: Funds/Banks
參數: 無
返回結果:
示例:
```
#### 获取获取企业第三方支付
```
路徑: TPayment/EThirdpartys
參數: enterprisecode=${企業編碼}
返回結果:
示例:
```
#### 获取企业第三方支付银行(已廢除)
```
路徑: TPayment/TPayMentBank
參數:
返回結果:
示例:
```
#### 提交第三方支付
```
路徑: TPayment/ESaving
參數: brandcode=${品牌編碼}&employeecode=${用戶編碼}&orderamount=${存款金額}&enterprisethirdpartycode=${企業第三方支付編碼}&traceip=${用戶IP}&paymenttypebankcode=${企業第三方支付銀行編碼}
返回結果:
示例:
```
--------------------------------------------------
### 活動相關接口

#### 领取优惠活动
```
路徑: MemBerActivity/trigger
參數: employeecode=${用戶編碼}&enterprisebrandactivitycode=${活動編碼}&loginip=${用戶IP}
返回結果:
示例:
```
#### 获取优惠活动数据
```
路徑: ActivityData/trigger
參數:
返回結果:
示例:
```
#### 获取优惠记录数据
```
路徑: ActivityData/benefitRecord
參數:
返回結果:
示例:
```
#### 获取优惠活动内容
```
路徑: BrandActivity/trigger
參數: brandcode=${品牌編碼}&way=${獲取方式}
返回結果:
示例:
```
#### 用户存款时的优惠编码
```
路徑: User/findUserFavourable
參數: employeecode=${用戶編碼}
返回結果:
示例:
```
#### 获取会员注册红包纪录
```
路徑: Redbag/Draw
參數:
返回結果:
示例:
```
--------------------------------------------------
### 其他接口

#### 获取企业注册用户数和月充值数
```
路徑: Statistics/ERegisterSave
參數:
返回結果:
示例:
```
#### 获取用户账号密码模拟登录
```
路徑: User/takeEmployeeLoginaccount
參數: loginaccount=${登錄賬號}
返回結果:
示例:
```
#### 客户端上分接口
```
路徑: Game/upIntegral
參數: brandcode=${品牌編碼}&employeecode=${用戶編碼}&gametype=${遊戲類型}&application=h5
返回結果:
示例:
```
