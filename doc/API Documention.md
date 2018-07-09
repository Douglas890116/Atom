# API 文档

## 文檔說明
本文檔是包網系統API接口文檔, 幫助前端工程師能夠方便地接入.
接口主要分為
* 會員站點相關接口, 根據域名獲取相關會員站點信息, 然後生成會員站點, 展示給玩家
* 會員玩家相關接口, 對玩家的一些操作的接口, 註冊/登錄/添加信息/修改密碼/讀取信息, 等等
* 遊戲相關接口, 遊戲上分/下分/試玩/進入遊戲/遊戲數據查詢, 等等
* 資金活動接口, 新增修改玩家銀行卡/存取款/查詢存取款記錄, 等等
* 活動相關接口, 對於網站上的相關活動的接口, 領紅包等等
* 其他接口, 有一些整理出來, 還未用上的接口

注: 有些參數暫時不清楚具體含義, 故未寫說明, 但實際會用到的參數已基本說明含義, 會持續更新

## 版本記錄

|序號|版本|更新內容|更新時間|操作人|審核人|
|:--:|:---|:---|:---|:---|:---|
|1|V1.0.0|創建文檔|2018-06-17|Cloud.d||

## 錯誤碼列表
接口返回數據, 為Json格式, 主要包括code與info兩個值, 前端在得到結果后, 先判斷code是否為1: 
若為1, 根據需要是提示操作成功信息, 或者展示具體數據; 若不為1, 則直接提示錯誤信息info,

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
返回結果: {
    "code" : "1",
    "info" : ${驗證碼}
}
示例: {
    "code" : "1",
    "info" : "4375"
}
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
返回結果: {
    "code":"1",
    "info":${用戶賬戶總餘額(包括中心錢包+所有遊戲上的分數)}
}
示例: {
    "code":"1",
    "info":"1500.0"
}
```
#### 用戶所有遊戲及中心錢包餘額列表
```
路徑: Game/balancesAll
參數: employeecode=${用戶編碼}
返回結果: {
    "code":"1",
    "info":[{
        "gamecode":${遊戲編碼},
        "gametype":${遊戲類型},
        "gamename":${遊戲名稱},
        "gamebalance":${遊戲上餘額}
    }]
}
示例: {
    "code":"1",
    "info":[{
        "gamecode":"00000",
        "gametype":"CENTER",
        "gamename":"我的钱包",
        "gamebalance":"150"
    },
    {
        "gamecode":"G021",
        "gametype":"IDNGame",
        "gamename":"IDN扑克",
        "gamebalance":"25"
    }]
}
```
#### 獲取玩家单个游戏余额
```
路徑: Game/balance
參數: employeecode=${用戶編碼}&gameType=${遊戲類型}
返回結果: {
    "code":"1",
    "info":${遊戲餘額}
}
示例: {
    "code":"1",
    "info":"10.0"
}
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
#### 获取單個游戏账号
```
路徑: User/takeEmployeeAccountOne
參數: employeecode=${用戶編碼}$gameType=${遊戲類型}
返回結果: {
    "code":"1",
    "info":${加密后的遊戲賬號/密碼 = AES_ENCODE({"gamepassword=d0ZjIc04, gameaccount=vvvvvvv0}", AES_KEY)}
}
示例: {
    "code":"1",
    "info":""
}
```
#### 获取全部游戏账号
```
路徑: User/takeEmployeeAccount
參數: employeecode=${用戶編碼}
返回結果:  {
    "code":"1",
    "info":[{
        "gametype":${遊戲類型},
        "gamename":${遊戲名稱},
        "gameaccount":${用戶遊戲賬號},
        "gamepassword":${遊戲賬戶密碼}
    }]
}
示例:  {
    "code":"1",
    "info":[{
        "gametype":"TAGGame",
        "gamename":"AG遊戲",
        "gameaccount":"sa7ctPbT",
        "gamepassword":"abc132"
    },
    {
        "gametype":"NHQGame",
        "gamename":"HY遊戲",
        "gameaccount":"qwer1234",
        "gamepassword":"abc555"
    }]
}
```
#### 获取PT游戏分类
**此接口無需加密**
```
路徑: Game/PtGameMenu
參數: 無
返回結果: 參看示例, 結果固定
示例: {
	"code": "1",
	"info": [{
		"children": [{
			"children": [],
			"classchinaname": "奇迹大奖",
			"classenglishname": "Miracle Award",
			"gameclasscode": 1,
			"gametype": "PTGame",
			"parentclasstype": "18",
			"sort": "1"
		},
		{
			"children": [],
			"classchinaname": "5-10条线",
			"classenglishname": "5-10Line",
			"gameclasscode": 2,
			"gametype": "PTGame",
			"parentclasstype": "18",
			"sort": "2"
		},
		{
			"children": [],
			"classchinaname": "15-20条线",
			"classenglishname": "12-20Line",
			"gameclasscode": 3,
			"gametype": "PTGame",
			"parentclasstype": "18",
			"sort": "3"
		},
		{
			"children": [],
			"classchinaname": "25+条线",
			"classenglishname": "25+line",
			"gameclasscode": 4,
			"gametype": "PTGame",
			"parentclasstype": "18",
			"sort": "4"
		},
		{
			"children": [],
			"classchinaname": "多旋律",
			"classenglishname": "Polyrhythms",
			"gameclasscode": 5,
			"gametype": "PTGame",
			"parentclasstype": "18",
			"sort": "5"
		},
		{
			"children": [],
			"classchinaname": "累计",
			"classenglishname": "Grand total",
			"gameclasscode": 6,
			"gametype": "PTGame",
			"parentclasstype": "18",
			"sort": "6"
		},
		{
			"children": [],
			"classchinaname": "经典老虎机",
			"classenglishname": "Classic Slot Machine",
			"gameclasscode": 7,
			"gametype": "PTGame",
			"parentclasstype": "18",
			"sort": "7"
		},
		{
			"children": [],
			"classchinaname": "视频扑克",
			"classenglishname": "Video Poker",
			"gameclasscode": 8,
			"gametype": "PTGame",
			"parentclasstype": "18",
			"sort": "8"
		}],
		"classchinaname": "电动吃角子老虎",
		"classenglishname": "",
		"gameclasscode": 18,
		"gametype": "PTGame",
		"parentclasstype": "0",
		"sort": "1"
	},
	{
		"children": [{
			"children": [],
			"classchinaname": "老虎机-街机",
			"classenglishname": "Slot Machines - Arcade",
			"gameclasscode": 9,
			"gametype": "PTGame",
			"parentclasstype": "19",
			"sort": "10"
		},
		{
			"children": [],
			"classchinaname": "基诺-数字",
			"classenglishname": "Keno - Digital",
			"gameclasscode": 10,
			"gametype": "PTGame",
			"parentclasstype": "19",
			"sort": "11"
		},
		{
			"children": [],
			"classchinaname": "运动-虚拟",
			"classenglishname": "Sports - Virtual",
			"gameclasscode": 11,
			"gametype": "PTGame",
			"parentclasstype": "19",
			"sort": "12"
		}],
		"classchinaname": "街机游戏",
		"classenglishname": "",
		"gameclasscode": 19,
		"gametype": "PTGame",
		"parentclasstype": "0",
		"sort": "2"
	},
	{
		"children": [{
			"children": [],
			"classchinaname": "吃角子老虎机游戏",
			"classenglishname": "Slots Games",
			"gameclasscode": 12,
			"gametype": "PTGame",
			"parentclasstype": "20",
			"sort": "14"
		},
		{
			"children": [],
			"classchinaname": "其它",
			"classenglishname": "Other",
			"gameclasscode": 13,
			"gametype": "PTGame",
			"parentclasstype": "20",
			"sort": "15"
		}],
		"classchinaname": "累计游戏",
		"classenglishname": "",
		"gameclasscode": 20,
		"gametype": "PTGame",
		"parentclasstype": "0",
		"sort": "3"
	},
	{
		"children": [{
			"children": [],
			"classchinaname": "21点",
			"classenglishname": "21Point",
			"gameclasscode": 14,
			"gametype": "PTGame",
			"parentclasstype": "21",
			"sort": "16"
		},
		{
			"children": [],
			"classchinaname": "轮盘",
			"classenglishname": "Rotary table",
			"gameclasscode": 15,
			"gametype": "PTGame",
			"parentclasstype": "21",
			"sort": "17"
		},
		{
			"children": [],
			"classchinaname": "刮刮乐",
			"classenglishname": "Lottery",
			"gameclasscode": 17,
			"gametype": "PTGame",
			"parentclasstype": "21",
			"sort": "18"
		},
		{
			"children": [],
			"classchinaname": "其他",
			"classenglishname": "Other",
			"gameclasscode": 16,
			"gametype": "PTGame",
			"parentclasstype": "21",
			"sort": "19"
		}],
		"classchinaname": "牌桌-纸牌游戏",
		"classenglishname": "",
		"gameclasscode": 21,
		"gametype": "PTGame",
		"parentclasstype": "0",
		"sort": "4"
	}]
}
```
#### 获取PT游戏分类详细信息
**此接口無需加密**
```
路徑: Game/PtGameDetailsMenu
參數: gameclasscode=${遊戲信息編碼}
返回結果: {
	"code": "1",
	"info": [{
		"classdetailscode": ${詳細信息編碼},
		"classdetailsflag": ${詳細信息表示},
		"detailschinaname": ${遊戲名稱},
		"detailsenglishname": ${遊戲英文名稱},
		"disable": ${是否可用: 1-可用, 0-不可用},
		"gameclasscode": ${遊戲信息編碼},
		"path": ${遊戲圖片logo路徑},
		"picturename": ${遊戲圖片名稱},
		"sort": ${排序}
	}]
}
示例: {
	"code": "1",
	"info": [{
		"classdetailscode": 210,
		"classdetailsflag": "bj",
		"detailschinaname": "21点",
		"detailsenglishname": "",
		"disable": "1",
		"gameclasscode": "14",
		"path": "http://192.168.1.207:9090/ecrm-pic/ptgames/",
		"picturename": "bj.jpg",
		"sort": 1
	},
	{
		"classdetailscode": 211,
		"classdetailsflag": "bjsd",
		"detailschinaname": "投降21点",
		"detailsenglishname": "",
		"disable": "1",
		"gameclasscode": "14",
		"path": "http://192.168.1.207:9090/ecrm-pic/ptgames/",
		"picturename": "bjsd.jpg",
		"sort": 2
	}]
}
```
#### 获取PT账号
```
路徑: Game/PtLogin
參數: employeecode=${用戶編碼}
 {
    "code":"1",
    "info":${加密后的遊戲賬號/密碼 = AES_ENCODE("user=d0ZjIc04&password=vvvvvvv0", AES_KEY)}
}
示例: {
    "code":"1",
    "info":""
}
```
#### 游戏纪录
```
路徑: GRecords/RecordsAll
參數: employeecode=${用戶編碼}&start=${分頁}&limit=${數量}&gamePlatform=${遊戲類型, 可選參數}&gameBigType=${遊戲大類: SX-視訊, DZ-電子, TY-體育, CP-彩票, QP-棋牌}&startDate=${開始時間}&endDate=${結束時間}
返回結果: {
	"code": "1",
	"info": {
		"betMoneyAll": ${總投注額},
		"netMoneyAll": ${輸贏金額},
		"record": [{
			"betDay": ${投注時間},
			"betMoney": ${投注金額},
			"gameBigType": ${遊戲大類: SX-視訊, DZ-電子, TY-體育, CP-彩票, QP-棋牌},
			"netMoney": ${輸贏金額},
			"gamePlatform": ${遊戲名稱},
			"userName": ${遊戲賬戶},
			"validMoney": ${有效投注額}
		}],
		"count": ${數據量}
	}
}
示例: {
	"code": "1",
	"info": {
		"betMoneyAll": 57,
		"netMoneyAll": -37.5,
		"record": [{
			"betDay": "2017-11-02 18:05:59",
			"betMoney": 17,
			"gameBigType": "SX",
			"netMoney": -17,
			"gamePlatform": "BBIN波音",
			"userName": "hrjtyl1388",
			"validMoney": 17
		},
		{
			"betDay": "2017-11-02 18:04:42",
			"betMoney": 10,
			"gameBigType": "SX",
			"netMoney": -10,
			"gamePlatform": "BBIN波音",
			"userName": "hrjtyl1388",
			"validMoney": 10
		},
		{
			"betDay": "2017-11-02 18:03:58",
			"betMoney": 10,
			"gameBigType": "SX",
			"netMoney": -10,
			"gamePlatform": "BBIN波音",
			"userName": "hrjtyl1388",
			"validMoney": 10
		},
		{
			"betDay": "2017-11-02 18:03:22",
			"betMoney": 10,
			"gameBigType": "SX",
			"netMoney": 9.5,
			"gamePlatform": "BBIN波音",
			"userName": "hrjtyl1388",
			"validMoney": 10
		},
		{
			"betDay": "2017-11-02 18:02:39",
			"betMoney": 10,
			"gameBigType": "SX",
			"netMoney": -10,
			"gamePlatform": "BBIN波音",
			"userName": "hrjtyl1388",
			"validMoney": 10
		}],
		"count": 5
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
返回結果: {
    "code":"1",
    "info":"成功"
}
示例: {
    "code":"1003",
    "info":"此银行卡号存在异常，请您核实之后与客服联系咨询"
}
```
#### 编辑银行卡
```
路徑: User/EditUBankCard
參數: employeecode=${用戶編碼}&informationcode=${用戶信息編碼}&fundpassword=${資金密碼}&paymentaccount=${賬戶卡號}&accountname=${賬戶名稱}&openningbank=${開戶行名稱}&qq=${用戶QQ號, 非必須}&skype=${用戶Skype, 非必須}&email=${用戶郵箱, 非必須}
返回結果: {
    "code":"1",
    "info":"成功"
}
示例: {
    "code":"1003",
    "info":"此电话号码存在异常，请您核实之后与客服联系咨询"
}
```
#### 删除银行卡
```
路徑: User/DeleteUBankCard
參數: employeecode=${用戶編碼}&informationcode=${用戶信息編碼}&fundpassword=${資金密碼}
返回結果: {
    "code":"1",
    "info":"成功"
}
示例: {
    "code":"1003",
    "info":"用户银行卡不存在"
}
```
#### 查询用户银行卡
```
路徑: User/UBankCards
參數: employeecode=${用戶編碼}
返回結果: {
	"code": "1",
	"info": [{
		"accountname": ${用戶銀行賬戶名稱},
		"balance": ${賬戶餘額},
		"bankcode": ${銀行編碼},
		"bankname": ${銀行名稱},
		"brandcode": ${品牌編碼},
		"depositTotal": ${存款次數},
		"dividend": ${用戶分紅},
		"email": ${用戶郵箱},
		"employeecode": ${用戶編碼},
		"enterprisecode": ${企業編碼},
		"infomationcomment": ${用戶信息備註},
		"informationcode": ${用戶信息編碼},
		"ip": ${用戶IP},
		"lastLoginDate": ${最後登錄時間},
		"loginaccount": ${用戶賬戶},
		"openningbank": "广东省惠州市吉隆镇中国工商银行惠东吉隆支行",
		"parentemployeecode": ${上級用戶編碼},
		"paymentaccount": ${用戶銀行賬戶卡號},
		"phonenumber": ${用戶電話號碼},
		"qq": ${用戶QQ號},
		"registerDate": ${用戶註冊時間},
		"share": ${用戶占成},
		"skype": ${用戶Skype},
		"status": ${用戶銀行卡狀態: 1-鎖定, 2-未鎖定},
		"takeTotal": ${取款總額}
	}]
}
示例: {
	"code": "1",
	"info": [{
		"accountname": "張三",
		"balance": 0,
		"bankcode": "B006",
		"bankname": "中国工商银行",
		"brandcode": "EB0000BD",
		"depositTotal": 0,
		"dividend": 0,
		"email": "",
		"employeecode": "E000JVHM",
		"enterprisecode": "EN003K",
		"infomationcomment": "",
		"informationcode": "EEI00S23",
		"ip": "",
		"lastLoginDate": "",
		"loginaccount": "",
		"openningbank": "广东省惠州市吉隆镇中国工商银行惠东吉隆支行",
		"parentemployeecode": "E000JVHL",
		"paymentaccount": "621226200587444444",
		"phonenumber": "",
		"qq": "",
		"registerDate": "",
		"share": 0,
		"skype": "",
		"status": "1",
		"takeTotal": 0
	}]
}
```
#### 获取收款银行
```
路徑: Funds/EBankCards
參數: enterprisecode=${企業編碼}
返回結果: {
	"code": "1",
	"info": [{
		"accountname": ${收款銀行賬戶名稱},
		"enterprisecode": ${企業編碼},
		"openningaccount": ${收款銀行賬戶卡號},
		"enterpriseinformationcode": ${企業信息編碼},
		"bankname": ${銀行名稱},
		"bankcode": ${銀行編碼},
		"openningbank": ${收款銀行開戶張行}
	}]
}
示例: {
	"code": "1",
	"info": [{
		"accountname": "王朋亮",
		"enterprisecode": "EN003K",
		"openningaccount": "6212260402025408715",
		"enterpriseinformationcode": "EI00004H",
		"bankname": "中国工商银行",
		"bankcode": "B006",
		"openningbank": "石家庄开发区支行营业室"
	},
	{
		"accountname": "王朋亮",
		"enterprisecode": "EN003K",
		"openningaccount": "6214833116994263",
		"enterpriseinformationcode": "EI00004I",
		"bankname": "招商银行",
		"bankcode": "B016",
		"openningbank": "石家庄分行红旗大街支行"
	}]
}
```
#### 用户存款
```
路徑: Funds/Saving
參數: brandcode=${品牌編碼}&employeecode=${用戶編碼}&orderamount=${存款金額}&enterpriseinformationcode=${用戶信息編碼}&employeepaymentbank=${支付用的銀行編碼}&employeepaymentaccount=${賬戶卡號}&employeepaymentname=${賬戶名稱}&traceip=${用戶IP}&ordercomment=${用戶留言}
返回結果: {
    "code":"1",
    "info":"成功"
}
示例: {
    "code":"10012",
    "info":"企业银行卡不存在"
}
```
#### 用户取款
```
路徑: Funds/Taking
參數: brandcode=${品牌編碼}&employeecode=${用戶編碼}&orderamount=${取款金額}&ordercomment=${用戶留言}&informationcode=${用戶信息編碼}&traceip=${用戶IP}&fundpassword=${資金密碼}
返回結果: {
    "code":"1",
    "info":"成功"
}
示例: {
    "code":"1003",
    "info":"当前余额小于提款金额，无法提交提款申请。"
}
```
#### 存款记录
```
路徑: Fetch/SaveOrder
參數: brandcode=${品牌編碼}&employeecode=${用戶編碼}&orderstatus=${訂單狀態: 1-处理中,2-已处理,3-驳回,4-拒绝,5-待出款}&start=${分頁}&limit=${數量}&orderdate_begin=${開始時間}&orderdate_end=${結束時間}
返回結果: {
	"code": "1",
	"info": {
		"record": [{
			"allDepositMoney": 0,
			"allTakeMoney": 0,
			"betmoney": 0,
			"brandcode": ${品牌名稱},
			"creditrating": 0,
			"delegatecode": ${審核流水號},
			"depositNumber": 0,
			"displayalias": ${用戶暱稱},
			"employeecode": ${用戶編碼},
			"employeepaymentaccount": ${用戶支付賬戶卡號},
			"employeepaymentbank": ${用戶支付銀行},
			"employeepaymentname": ${用戶支付賬戶姓名},
			"end_date": "",
			"enterprisecode": ${企業編碼},
			"enterprisepaymentaccount": ${企業收款賬戶卡號},
			"enterprisepaymentbank": ${企業收款銀行},
			"enterprisepaymentname": ${企業收款賬戶名稱},
			"exchangerate": ${匯率},
			"favourableid": "",
			"favourablename": "",
			"flowcode": ${流程編碼},
			"handleemployee": ${訂單處理人},
			"handleovertime": ${訂單處理完成時間},
			"loginaccount": ${用戶賬戶},
			"mum": "",
			"netmoney": 0,
			"orderamount": ${訂單金額},
			"ordercomment": ${訂單備註},
			"ordercreater": ${訂單創建人},
			"orderdate": ${訂單時間},
			"ordernumber": ${訂單單號},
			"orderstatus": ${訂單狀態: 1-处理中, 2-已处理, 3-驳回, 4-拒绝, 5-待出款},
			"ordertype": ${訂單類型: 1-存款, 2-取款},
			"parentemployeeaccount": ${上級賬戶},
			"parentemployeecode": ${上級賬戶編碼},
			"paymenttypecode": ${支付類型: PM01-第三方支付, PM02-銀行轉賬支付},
			"pcontent": "",
			"platformtype": "",
			"pmoney": "",
			"ptime": "",
			"ptype": "",
			"quantity": 0,
			"ratex": 0,
			"savecount": 0,
			"savemoney": 0,
			"sign": "",
			"start_date": "",
			"takecount": 0,
			"takemoney": 0,
			"time": "",
			"traceip": ${發起支付的IP地址}
		}],
		"count": ${存款總次數},
		"sumamount": ${存款總量}
	}
}
示例: {
	"code": "1",
	"info": {
		"record": [{
			"allDepositMoney": 0,
			"allTakeMoney": 0,
			"betmoney": 0,
			"brandcode": "金塔娱乐城",
			"creditrating": 0,
			"delegatecode": 49961,
			"depositNumber": 0,
			"displayalias": "daihuan1",
			"employeecode": "E000JVHM",
			"employeepaymentaccount": "",
			"employeepaymentbank": "B019",
			"employeepaymentname": "",
			"end_date": "",
			"enterprisecode": "EN003K",
			"enterprisepaymentaccount": "EP00006S",
			"enterprisepaymentbank": "P057",
			"enterprisepaymentname": "众宝微信支付宝",
			"exchangerate": 0,
			"favourableid": "",
			"favourablename": "",
			"flowcode": "WF00003M",
			"handleemployee": "",
			"handleovertime": "2017-12-03 03:39:48",
			"loginaccount": "daihuan123",
			"mum": "",
			"netmoney": 0,
			"orderamount": 200.31,
			"ordercomment": "自动存款",
			"ordercreater": "会员daihuan1",
			"orderdate": "2017-12-03 03:39:14",
			"ordernumber": "ABAF231EFF43E78F4CD72C64DB3D72",
			"orderstatus": 2,
			"ordertype": 1,
			"parentemployeeaccount": "",
			"parentemployeecode": "E000JVHL",
			"paymenttypecode": "PM01",
			"pcontent": "",
			"platformtype": "",
			"pmoney": "",
			"ptime": "",
			"ptype": "",
			"quantity": 0,
			"ratex": 0,
			"savecount": 0,
			"savemoney": 0,
			"sign": "",
			"start_date": "",
			"takecount": 0,
			"takemoney": 0,
			"time": "",
			"traceip": "175.8.219.90"
		},
		{
			"allDepositMoney": 0,
			"allTakeMoney": 0,
			"betmoney": 0,
			"brandcode": "金塔娱乐城",
			"creditrating": 0,
			"delegatecode": 49907,
			"depositNumber": 0,
			"displayalias": "daihuan1",
			"employeecode": "E000JVHM",
			"employeepaymentaccount": "",
			"employeepaymentbank": "B019",
			"employeepaymentname": "",
			"end_date": "",
			"enterprisecode": "EN003K",
			"enterprisepaymentaccount": "EP00006Y",
			"enterprisepaymentbank": "P059",
			"enterprisepaymentname": "旺付通微信支付宝",
			"exchangerate": 0,
			"favourableid": "",
			"favourablename": "",
			"flowcode": "WF00003M",
			"handleemployee": "",
			"handleovertime": "2017-12-02 19:35:32",
			"loginaccount": "daihuan123",
			"mum": "",
			"netmoney": 0,
			"orderamount": 200.57,
			"ordercomment": "自动存款",
			"ordercreater": "会员daihuan1",
			"orderdate": "2017-12-02 19:35:16",
			"ordernumber": "AF4E0B99F75A44DE95CC837CB2F157AF",
			"orderstatus": 2,
			"ordertype": 1,
			"parentemployeeaccount": "",
			"parentemployeecode": "E000JVHL",
			"paymenttypecode": "PM01",
			"pcontent": "",
			"platformtype": "",
			"pmoney": "",
			"ptime": "",
			"ptype": "",
			"quantity": 0,
			"ratex": 0,
			"savecount": 0,
			"savemoney": 0,
			"sign": "",
			"start_date": "",
			"takecount": 0,
			"takemoney": 0,
			"time": "",
			"traceip": "175.8.219.90"
		}],
		"count": 12,
		"sumamount": 1876.05
	}
}
```
#### 取款记录
```
路徑: Fetch/TakeOrder
參數: brandcode=${品牌編碼}&employeecode=${用戶編碼}&orderstatus=${訂單狀態: 1-处理中,2-已处理,3-驳回,4-拒绝,5-待出款}&start=${分頁}&limit=${數量}&orderdate_begin=${開始時間}&orderdate_end=${結束時間}
返回結果: {
	"code": "1",
	"info": {
		"record": [{
			"allDepositMoney": 0,
			"allTakeMoney": 0,
			"betmoney": 0,
			"brandcode": ${品牌名稱},
			"creditrating": 0,
			"delegatecode": ${審核流水號},
			"depositNumber": 0,
			"displayalias": ${用戶暱稱},
			"employeecode": ${用戶編碼},
			"employeepaymentaccount": ${用戶取款賬戶卡號},
			"employeepaymentbank": ${用戶支付銀行},
			"employeepaymentname": ${用戶取款賬戶姓名},
			"end_date": "",
			"enterprisecode": ${企業編碼},
			"enterprisepaymentaccount": ${企業出款賬戶卡號},
			"enterprisepaymentbank": ${企業出款銀行},
			"enterprisepaymentname": ${企業粗款賬戶名稱},
			"exchangerate": ${匯率},
			"favourableid": "",
			"favourablename": "",
			"flowcode": ${流程編碼},
			"handleemployee": ${訂單處理人},
			"handleovertime": ${訂單處理完成時間},
			"loginaccount": ${用戶賬戶},
			"mum": "",
			"netmoney": 0,
			"orderamount": ${訂單金額},
			"ordercomment": ${訂單備註},
			"ordercreater": ${訂單創建人},
			"orderdate": ${訂單時間},
			"ordernumber": ${訂單單號},
			"orderstatus": ${訂單狀態: 1-处理中, 2-已处理, 3-驳回, 4-拒绝, 5-待出款},
			"ordertype": ${訂單類型: 1-存款, 2-取款},
			"parentemployeeaccount": ${上級賬戶},
			"parentemployeecode": ${上級賬戶編碼},
			"paymenttypecode": ${支付類型: PM01-第三方支付, PM02-銀行轉賬支付},
			"pcontent": "",
			"platformtype": "",
			"pmoney": "",
			"ptime": "",
			"ptype": "",
			"quantity": 0,
			"ratex": 0,
			"savecount": 0,
			"savemoney": 0,
			"sign": "",
			"start_date": "",
			"takecount": 0,
			"takemoney": 0,
			"time": "",
			"traceip": ${發起支付的IP地址}
		}],
		"count": ${存款總次數},
		"sumamount": ${存款總量}
	}
}
示例: {
	"code": "1",
	"info": {
		"count": 4,
		"record": [{
			"allDepositMoney": 0,
			"allTakeMoney": 0,
			"betmoney": 0,
			"brandcode": "金塔娱乐城",
			"creditrating": 0,
			"delegatecode": 49966,
			"depositNumber": 0,
			"displayalias": "daihuan1",
			"employeecode": "E000JVHM",
			"employeepaymentaccount": "6212262008020048386",
			"employeepaymentbank": "B006",
			"employeepaymentname": "戴欢",
			"end_date": "",
			"enterprisecode": "EN003K",
			"enterprisepaymentaccount": "EP00006Q",
			"enterprisepaymentbank": "",
			"enterprisepaymentname": "星付代付",
			"exchangerate": 0,
			"favourableid": "",
			"favourablename": "",
			"flowcode": "00000000",
			"handleemployee": "zoeycst",
			"handleovertime": "2017-12-03 05:41:50",
			"loginaccount": "daihuan123",
			"mum": "",
			"netmoney": 0,
			"orderamount": -800,
			"ordercomment": "完成审批",
			"ordercreater": "会员",
			"orderdate": "2017-12-03 05:34:28",
			"ordernumber": "8015122504680281009",
			"orderstatus": 2,
			"ordertype": 2,
			"parentemployeeaccount": "",
			"parentemployeecode": "E000JVHL",
			"paymenttypecode": "PM01",
			"pcontent": "",
			"platformtype": "",
			"pmoney": "",
			"ptime": "",
			"ptype": "",
			"quantity": 0,
			"ratex": 0,
			"savecount": 0,
			"savemoney": 0,
			"sign": "",
			"start_date": "",
			"takecount": 0,
			"takemoney": 0,
			"time": "",
			"traceip": "175.8.219.90"
		},
		{
			"allDepositMoney": 0,
			"allTakeMoney": 0,
			"betmoney": 0,
			"brandcode": "金塔娱乐城",
			"creditrating": 0,
			"delegatecode": 49723,
			"depositNumber": 0,
			"displayalias": "daihuan1",
			"employeecode": "E000JVHM",
			"employeepaymentaccount": "6212262008020048386",
			"employeepaymentbank": "B006",
			"employeepaymentname": "戴欢",
			"end_date": "",
			"enterprisecode": "EN003K",
			"enterprisepaymentaccount": "EP00006Q",
			"enterprisepaymentbank": "",
			"enterprisepaymentname": "星付代付",
			"exchangerate": 0,
			"favourableid": "",
			"favourablename": "",
			"flowcode": "00000000",
			"handleemployee": "zoeycst",
			"handleovertime": "2017-12-02 01:12:28",
			"loginaccount": "daihuan123",
			"mum": "",
			"netmoney": 0,
			"orderamount": -400,
			"ordercomment": "完成审批",
			"ordercreater": "会员",
			"orderdate": "2017-12-02 01:09:57",
			"ordernumber": "8015121481976631094",
			"orderstatus": 2,
			"ordertype": 2,
			"parentemployeeaccount": "",
			"parentemployeecode": "E000JVHL",
			"paymenttypecode": "PM01",
			"pcontent": "",
			"platformtype": "",
			"pmoney": "",
			"ptime": "",
			"ptype": "",
			"quantity": 0,
			"ratex": 0,
			"savecount": 0,
			"savemoney": 0,
			"sign": "",
			"start_date": "",
			"takecount": 0,
			"takemoney": 0,
			"time": "",
			"traceip": "175.8.219.90"
		}],
		"sumamount": -1900
	}
}
```
#### 用户账变记录
```
路徑: User/findAccountChange
參數: employeecode=${用戶編碼}&start=${分頁}&limit=${數量}&startDate=${開始時間}&endDate=${結束時間}
返回結果: {
	"code": "1",
	"info": {
		"record": [{
			"afteramount": ${賬變后金額},
			"currencycode": ${貨幣編碼},
			"employeecode": ${用戶編碼},
			"employeename": ${用戶賬戶},
			"enterprisecode": ${企業編碼},
			"loginaccount": ${用戶賬戶},
			"moneyaddtype": ${金額增加類型},
			"moneychangeamount": ${賬變金額},
			"moneychangecode": ${賬變單號},
			"moneychangedate": ${賬變時間},
			"moneychangetypecode": ${賬變類型編碼},
			"moneychangetypename": ${賬變類型名稱},
			"moneyinoutcomment": ${賬變備註},
			"ordernumber": ${訂單單號},
			"parentemployeecode": ${上級用戶編碼},
			"settlementamount": ${賬變前餘額},
			"timesort": ${排序}
		}],
		"count": 99,
		"sumamount": 0.69
	}
}
示例: {
	"code": "1",
	"info": {
		"record": [{
			"afteramount": 0.22,
			"currencycode": "",
			"employeecode": "E000JVHM",
			"employeename": "daihuan1",
			"enterprisecode": "EN003K",
			"loginaccount": "daihuan123",
			"moneyaddtype": "",
			"moneychangeamount": -100,
			"moneychangecode": "026A1120123947759B7BFA94AF0A498B",
			"moneychangedate": "2017-11-10 17:20:08",
			"moneychangetypecode": "AF0B2F04CCA64E3197F047402FEE5832",
			"moneychangetypename": "游戏上分",
			"moneyinoutcomment": "操作人:API 游戏上分【TAGGame】 批次号：9015103056081741023",
			"ordernumber": "9015103056081741023",
			"parentemployeecode": "E000JVHL",
			"settlementamount": 100.22,
			"timesort": 0
		},
		{
			"afteramount": 0.17,
			"currencycode": "",
			"employeecode": "E000JVHM",
			"employeename": "daihuan1",
			"enterprisecode": "EN003K",
			"loginaccount": "daihuan123",
			"moneyaddtype": "",
			"moneychangeamount": -4,
			"moneychangecode": "03AB58D4F11247BD9636D89B66D342C6",
			"moneychangedate": "2017-11-20 21:12:43",
			"moneychangetypecode": "AF0B2F04CCA64E3197F047402FEE5832",
			"moneychangetypename": "游戏上分",
			"moneyinoutcomment": "操作人:API 游戏上分【NHQGame】 批次号：9015111835636311776",
			"ordernumber": "9015111835636311776",
			"parentemployeecode": "E000JVHL",
			"settlementamount": 4.17,
			"timesort": 0
		}],
		"count": 99,
		"sumamount": 0.69
	}
}
```
#### 查询会员时间段内的存取款、优惠等数据統計
```
路徑: Game/allMoney
參數: employeecode=${用戶編碼}
返回結果: {
	"code": "1",
	"info": {
		"total_net_money": ${輸贏總金額},
		"total_activity_money": ${活動優惠總額},
		"total_stream_money": ${應打碼總額},
		"total_bet_money": ${投注總金額},
		"total_take_money": ${取款總金額},
		"total_deposit_money": ${存款總金額}
	}
}
示例: {
	"code": "1",
	"info": {
		"total_net_money": 288.25,
		"total_activity_money": 0,
		"total_stream_money": 9.64,
		"total_bet_money": 9897.5,
		"total_take_money": -1900,
		"total_deposit_money": 1876.05
	}
}
```

#### 获取基础银行信息
**此接口無需加密**
```
路徑: Funds/Banks
參數: 無
返回結果: {
	"code": "1",
	"info": [{
		"bankcode": ${銀行編碼},
		"banklogo": ${銀行Logo地址},
		"bankname": ${銀行名稱},
		"bankurl": ${銀行官網地址},
		"displayorder": ${排序}
	}]
}
示例: {
	"code": "1",
	"info": [{
		"bankcode": "B006",
		"banklogo": "/icon/bank/ICBC_OUT.gif",
		"bankname": "中国工商银行",
		"bankurl": "http://www.icbc.com.cn/icbc/",
		"displayorder": 1
	},
	{
		"bankcode": "B015",
		"banklogo": "/icon/bank/CCB_OUT.gif",
		"bankname": "中国建设银行",
		"bankurl": "http://www.ccb.com/cn/home/indexv3.html",
		"displayorder": 2
	}]
}
```
#### 获取获取企业第三方支付
```
路徑: TPayment/EThirdpartys
參數: enterprisecode=${企業編碼}&type=${支付類型: PC-PC端發起支付, H5-移動端發起支付}
返回結果: {
	"code": "1",
	"info": [{
		"thirdpartypaymenttypename": ${支付名稱},
		"banks": [{
			"bankcode": ${銀行編碼},
			"bankname": ${銀行名稱},
			"enable": ${是否可用: 1-可用, -1-不可用},
			"id": ${銀行ID},
			"paymenttypebankcode": ${第三方支付銀行編碼},
			"sign": "",
			"thirdpartypaymenttypecode": ${第三方支付編碼},
			"thirdpartypaymenttypename": ${第三方支付名稱}
		}],
		"paycallbackurl": ${支付回調域名},
		"minmoney": ${存款最小金額},
		"maxmoney": ${存款最大金額},
		"enterprisethirdpartycode": ${企業第三方支付編碼},
		"callbackurl": ${第三方支付回調域名},
		"thirdpartypaymenttypecode": ${第三方支付編碼}
	}]
}
示例: {
	"code": "1",
	"info": [{
		"thirdpartypaymenttypename": "微信通道3",
		"banks": [{
			"bankcode": "B019",
			"bankname": "微信",
			"enable": "",
			"id": 431,
			"paymenttypebankcode": "1004",
			"sign": "",
			"thirdpartypaymenttypecode": "P057",
			"thirdpartypaymenttypename": ""
		}],
		"paycallbackurl": "http://127.0.0.1:9090/ecrm-api",
		"minmoney": 50,
		"maxmoney": 3000,
		"enterprisethirdpartycode": "EP00006S",
		"callbackurl": "http://api.jdpayment.com/",
		"thirdpartypaymenttypecode": "P057"
	},
	{
		"thirdpartypaymenttypename": "网银通道4",
		"banks": [{
			"bankcode": "B005",
			"bankname": "中国农业银行",
			"enable": "",
			"id": 398,
			"paymenttypebankcode": "ABC",
			"sign": "",
			"thirdpartypaymenttypecode": "P058",
			"thirdpartypaymenttypename": ""
		},
		{
			"bankcode": "B006",
			"bankname": "中国工商银行",
			"enable": "",
			"id": 399,
			"paymenttypebankcode": "ICBC",
			"sign": "",
			"thirdpartypaymenttypecode": "P058",
			"thirdpartypaymenttypename": ""
		},
		{
			"bankcode": "B015",
			"bankname": "中国建设银行",
			"enable": "",
			"id": 400,
			"paymenttypebankcode": "CCB",
			"sign": "",
			"thirdpartypaymenttypecode": "P058",
			"thirdpartypaymenttypename": ""
		},
		{
			"bankcode": "B003",
			"bankname": "交通银行",
			"enable": "",
			"id": 401,
			"paymenttypebankcode": "BCOM",
			"sign": "",
			"thirdpartypaymenttypecode": "P058",
			"thirdpartypaymenttypename": ""
		},
		{
			"bankcode": "B004",
			"bankname": "中国银行",
			"enable": "",
			"id": 402,
			"paymenttypebankcode": "BOC",
			"sign": "",
			"thirdpartypaymenttypecode": "P058",
			"thirdpartypaymenttypename": ""
		},
		{
			"bankcode": "B016",
			"bankname": "招商银行",
			"enable": "",
			"id": 403,
			"paymenttypebankcode": "CMB",
			"sign": "",
			"thirdpartypaymenttypecode": "P058",
			"thirdpartypaymenttypename": ""
		}],
		"paycallbackurl": "http://127.0.0.1:9090/ecrm-api",
		"minmoney": 100,
		"maxmoney": 50000,
		"enterprisethirdpartycode": "EP00006X",
		"callbackurl": "http://api.jdpayment.com/",
		"thirdpartypaymenttypecode": "P058"
	}]
}
```
#### 获取企业第三方支付银行(已廢除)
**此接口無需加密**
```
路徑: TPayment/TPayMentBank
參數: thirdpartypaymenttypecode=${第三方支付編碼}
返回結果: {
	"code": "1",
	"info": [{
		"paymenttypebankcode": ${第三方支付銀行編碼},
		"bankname": ${銀行名稱},
		"bankcode": ${銀行編碼}
	}]
}
示例: {
	"code": "1",
	"info": [{
		"paymenttypebankcode": "ABC",
		"bankname": "中国农业银行",
		"bankcode": "B005"
	},
	{
		"paymenttypebankcode": "ICBC",
		"bankname": "中国工商银行",
		"bankcode": "B006"
	}]
}
```
#### 提交第三方支付
```
路徑: TPayment/ESaving
參數: brandcode=${品牌編碼}&employeecode=${用戶編碼}&orderamount=${存款金額}&enterprisethirdpartycode=${企業第三方支付編碼}&traceip=${用戶IP}&paymenttypebankcode=${企業第三方支付銀行編碼}
返回結果: 無返回值, 直接跳轉到支付頁面
示例: 無
```
--------------------------------------------------
### 活動相關接口

#### 领取优惠活动(請求結果根據活動不同, 返回結果, 今後根據活動詳情, 再進行接口對接)
```
路徑: MemBerActivity/trigger
參數: employeecode=${用戶編碼}&enterprisebrandactivitycode=${活動編碼}&loginip=${用戶IP}
返回結果: {
	"code": "1",
	"info": ${具體活動的提示信息},
}
示例: {
    "code":"1003",
    "info":"活动模板不存在"
}
```
#### 获取优惠活动数据
```
路徑: ActivityData/trigger
參數: employeecode=${用戶編碼}&enterprisebrandactivitycode=${活動編碼}
返回結果: {
	"code": "1",
	"info": ${具體活動的結果數據, 待完善},
}
示例: {
    "code":"1003",
    "info":"活动模板不存在"
}
```
#### 获取优惠记录数据
```
路徑: ActivityData/benefitRecord
參數:
返回結果: {
	"code": "1",
	"info": {
		"record": [{
			"activityname": ${優惠編碼},
			"alreadybet": ${已打碼金額},
			"betrecordcode": ${數據流水號},
			"betrecordstatus": ${打碼狀態: 1-已完成, 0-未完成},
			"brandcode": ${品牌編碼},
			"createtime": ${创建創建時間},
			"ecactivitycode": ${定制活動編碼},
			"employeecode": ${用戶編碼},
			"enterprisecode": ${企業編碼},
			"finishtime": ${完成時間},
			"loginaccount": ${用戶賬戶},
			"mustbet": ${需打碼金額},
			"ordernumber": ${業務單號},
			"parentemployeeaccount": ${上級用戶賬戶},
			"parentemployeecode": ${上級用戶編碼},
			"recharge": ${充值金額}
		}],
		"count": ${数据量}
	}
}
示例: {
	"code": "1",
	"info": {
		"record": [{
			"activityname": "存款所需流水",
			"alreadybet": 0,
			"betrecordcode": 6027,
			"betrecordstatus": "0",
			"brandcode": "EB0000BD",
			"createtime": "2017-05-02 13:20:27",
			"ecactivitycode": 0,
			"employeecode": "E000IX3K",
			"enterprisecode": "EN003K",
			"finishtime": "",
			"loginaccount": "ianwang",
			"mustbet": 100,
			"ordernumber": "",
			"parentemployeeaccount": "eggagent",
			"parentemployeecode": "E000IX1V",
			"recharge": 100
		},
		{
			"activityname": "存款所需流水",
			"alreadybet": 0,
			"betrecordcode": 6002,
			"betrecordstatus": "0",
			"brandcode": "EB0000BD",
			"createtime": "2017-04-29 14:40:45",
			"ecactivitycode": 0,
			"employeecode": "E000IX3K",
			"enterprisecode": "EN003K",
			"finishtime": "",
			"loginaccount": "ianwang",
			"mustbet": 200,
			"ordernumber": "",
			"parentemployeeaccount": "eggagent",
			"parentemployeecode": "E000IX1V",
			"recharge": 200
		},
		{
			"activityname": "存款所需流水",
			"alreadybet": 0,
			"betrecordcode": 6001,
			"betrecordstatus": "0",
			"brandcode": "EB0000BD",
			"createtime": "2017-04-29 14:39:51",
			"ecactivitycode": 0,
			"employeecode": "E000IX3K",
			"enterprisecode": "EN003K",
			"finishtime": "",
			"loginaccount": "ianwang",
			"mustbet": 200,
			"ordernumber": "",
			"parentemployeeaccount": "eggagent",
			"parentemployeecode": "E000IX1V",
			"recharge": 200
		}],
		"count": 3
	}
}
```
#### 获取优惠活动内容
```
路徑: BrandActivity/trigger
參數: brandcode=${品牌編碼}&way=${獲取方式: List, 固定值}
返回結果: {
	"code": "1",
	"info": [{
		"enterprisebrandactivitycode": ${活動編碼},
		"endtime": ${活動結束時間},
		"begintime": ${活動開始時間},
		"activityname": ${活動名稱},
		"activityimage": ${活動圖片},
		"brandcode": ${品牌編碼},
		"parameters": ${活動參數},
		"activitycontent": ${活動內容, HTML, 可直接展示},
		"activitynature": ${活動類型: 0-普通, 1-特殊}
	}]
}
示例: {
	"code": "1",
	"info": [{
		"enterprisebrandactivitycode": 139,
		"endtime": "2018-07-07 23:59:59.0",
		"begintime": "2017-11-01 00:00:00.0",
		"activityname": "体育再存红利",
		"activityimage": "https://img.hyzonghe.net/uploadfiles/1509531518070.jpg",
		"brandcode": "EB0000BD",
		"parameters": [],
		"activitycontent": "<p>\r\n\t<br />\r\n</p>\r\n<p>\r\n\t<img src=\"http://img.hyzonghe.net:80/uploadfiles/1509532931386.png\" alt=\"\" /> \r\n</p>\r\n<p>\r\n\t<br />\r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"background:white;\">\r\n\t<span style=\"background-color:#FFFFFF;\"><strong>【</strong></span><strong>申请方式】</strong> \r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"background:white;\">\r\n\t所有申请过首存优惠及有过存款的会员，存款后联系在线客服办理\r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"background:white;\">\r\n\t<br />\r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"background:white;\">\r\n\t<strong>【活动条款及规则】</strong> \r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"margin-left:0cm;text-indent:-18pt;background:white;\">\r\n\t<br />\r\n</p>\r\n<ol>\r\n\t<li>\r\n\t\t1. <span>优惠开始于北京时间2017年11月1日00.00.01开始 结束时间为2017年12月31日23.59.59</span> \r\n\t</li>\r\n\t<li>\r\n\t\t2.   \r\n该优惠需要在存款后游戏前联系在线客服申请.该活动每位会员每周只可申请一次 此活动只适用于体育平台\r\n\t</li>\r\n\t<li>\r\n\t\t3.   \r\n如需取消再存优惠，必须在开始游戏前联系“在线客服”并待完全处理完毕后，方可开始游戏。\r\n\t</li>\r\n\t<li>\r\n\t\t4.   \r\n取消优惠申请后会员需投注存款金额的1倍流水可申请提款。\r\n\t</li>\r\n\t<li>\r\n\t\t5.   \r\n此优惠产生的所有投注额不予返水共享\r\n\t</li>\r\n\t<li>\r\n\t\t6.   \r\n申请再存优惠的同时不可参与其他优惠\r\n\t</li>\r\n\t<li>\r\n\t\t7.   \r\n金塔娱乐一般条款与规则适用于该优惠。\r\n\t</li>\r\n\t<li>\r\n\t\t8.   \r\n金塔娱乐保留随时停止该活动，且不需通知玩家的权利，并拥有活动最终解释权。\r\n\t</li>\r\n\t<li>\r\n\t\t活动期间，每位有效玩家，每一手机号码、电子邮件、相同银行卡，每一个IP地址，每一台电脑每周只能享受一次优惠\r\n\t</li>\r\n\t<li>\r\n\t\t1.   \r\n如发现有违规者或者进行无风险投注及倍投、梭哈，平台将保留无限期审核扣回红利及所产生的利润权利。本活动遵守金塔娱乐城的条款条规，同时每位玩家只能申请一种金塔娱乐城优惠活动，除非另有注明。所有平局，无效注单，投注输赢赛果的注单和低于1.70（欧洲盘）赔率的投注（马来盘赔率0.50；香港盘赔率0.70；印尼盘赔率-2.00）和非体育项目,百练赛以及虚拟运动，将不会计算在有效投注额内。\r\n\t</li>\r\n\t<li>\r\n\t\t2.   \r\n 此优惠所产生的所有投注额不予返水优惠共享\r\n\t</li>\r\n</ol>\r\n<p>\r\n\t<br />\r\n</p>\r\n<p class=\"MsoNormal\">\r\n\t<br />\r\n</p>\r\n<p class=\"MsoNormal\">\r\n\t<br />\r\n</p>\r\n<p class=\"MsoNormal\">\r\n\t<span> </span> \r\n</p>",
		"activitynature": "0"
	},
	{
		"enterprisebrandactivitycode": 160,
		"endtime": "2018-07-07 23:59:59.0",
		"begintime": "2017-11-01 00:00:00.0",
		"activityname": "体育首存88%",
		"activityimage": "https://img.hyzonghe.net/uploadfiles/1509440020079.jpg",
		"brandcode": "EB0000BD",
		"parameters": [],
		"activitycontent": "<p>\r\n\t<br />\r\n</p>\r\n<p>\r\n\t<img src=\"http://img.hyzonghe.net:80/uploadfiles/1509597641491.png\" alt=\"\" /> \r\n</p>\r\n<p>\r\n\t<br />\r\n</p>\r\n<p>\r\n\t<strong>【领奖步骤】</strong> \r\n</p>\r\n1. 注册您的金塔娱乐城账户，进行有效存款；<br />\r\n2.通过在线聊天或者发送电子邮件给我们，并注明您的用户名，存款单号 和申请首存优惠的方案即可；<br />\r\n3.我们及时为您添加红利。<br />\r\n<br />\r\n<strong>【活动规则】</strong><br />\r\n1. 活动时间：2017/11/01  00:00:01 至2017/12/31  23:59:59 (GMT +8)；<br />\r\n2. 本优惠活动仅限于金塔娱乐城体育平台（投注于真人娱乐城和扑克平台不计算有效投注额）；<br />\r\n3. 活动期间，每位有效玩家，每一手机号码、电子邮箱、相同银行卡，每一个IP地址，每一台电脑者只能享受一次优惠<br />\r\n4.  如发现有违规者或则进行对冲投注、梭哈、倍投，我们将保留无限期审核扣回红利及所产生的利润权利；<br />\r\n5. 如果在未向客服申请红利之前已经投注，我们将视为您放弃本优惠享有权利；<br />\r\n6. 金塔娱乐城有权在任何时间修改和取消此优惠活动，并保留无须另行通知玩家的权利；<br />\r\n7. 本活动遵守金塔娱乐城的条款条规，同时每位玩家只能申请一种金塔娱乐城优惠活动，除非另有注明。所有平局，无效注单，投注输赢赛果的单和低于1.70（欧洲盘）赔率的投注（马来盘赔率0.50；香港盘赔率0.70；印尼盘赔率-2.00）和非体育项目,百练赛以及虚拟运动，将不会计算在有效投注额内。<br />\r\n8. 此优惠产生的所有投注额不予返水共享<br />",
		"activitynature": "0"
	}]
}
```
#### 用户存款时的优惠编码
```
路徑: User/findUserFavourable
參數: employeecode=${用戶編碼}
返回結果: {
	"code": "1",
	"info": [{
		"employeecode": ${用戶編碼},
		"endtime": ${優惠活動結束時間},
		"enterprisecode": ${企業編碼},
		"favourableid": ${優惠活動編碼},
		"favourablename": ${優惠活動名稱},
		"isdeault": ${是否默認: 1-默認, 0-非默認},
		"isonce": ${是否一次性活動: 1-多次, 0-一次},
		"loginaccount": ${用戶賬號},
		"lsbs": ${流水倍數},
		"lsh": ${流水號},
		"starttime": ${優惠活動開始時間},
		"status": ${狀態: 1-啟用, 0-禁用}
	}]
}
示例: {
	"code": "1",
	"info": [{
		"employeecode": "E000JVHM",
		"endtime": "2017-06-30 23:59:59",
		"enterprisecode": "EN003K",
		"favourableid": "36374f9c-1707-4419-857c-ce71de5f6e36",
		"favourablename": "68%老虎机首存",
		"isdeault": 0,
		"isonce": 0,
		"loginaccount": "daihuan123",
		"lsbs": 20,
		"lsh": "064f56c7-5225-499a-85c9-69588bb3cde6",
		"starttime": "2017-05-01 00:00:00",
		"status": 0
	},
	{
		"employeecode": "E000JVHM",
		"endtime": "2017-06-30 23:59:59",
		"enterprisecode": "EN003K",
		"favourableid": "df26d75b-f0ed-46dc-bd37-0f26bd0982b5",
		"favourablename": "月月拿月月送",
		"isdeault": 0,
		"isonce": 1,
		"loginaccount": "daihuan123",
		"lsbs": 1,
		"lsh": "0ad5f75d-da5d-4900-9e0b-e192d6137be9",
		"starttime": "2017-05-01 00:00:00",
		"status": 0
	}]
}
```
#### 获取会员注册红包纪录
```
路徑: Redbag/Draw
參數: employeecode=${用戶編碼}&enterprisebrandactivitycode=${活動編碼}&loginip=${用戶IP}
返回結果:
示例:
```
--------------------------------------------------
# 以下接口還在開發完善中, 主要用於手機APP
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
