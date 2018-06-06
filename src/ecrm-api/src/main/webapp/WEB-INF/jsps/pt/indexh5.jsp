<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.maven.utils.AESUtil"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static"></c:set>
<c:set var="images" value="${ctx}/static/pt/imagesh5/"></c:set>

<html>
<head>

<meta charset="UTF-8">

<title>【${sessionScope.username}】PT老虎机游戏</title>

		<meta charset="utf-8" />
		<title>PT老虎机</title>
		<meta name="description" content="">
        <meta name="keywords" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<link rel="stylesheet" href="${statics }/avh5/css/amazeui.min.css" type="text/css"/>
		<link rel="stylesheet" href="${statics }/avh5/css/app.css" type="text/css"  />
		<script src="${statics }/avh5/js/jquery.min.js"></script>
		<script src="${statics }/avh5/js/amazeui.min.js"></script>

<style type="text/css">
.query_hint{
 border:5px solid #939393;
 width:250px;
 height:80px;
 line-height:85px;
 padding:0 20px;
 position:absolute;
 left:50%;
 margin-left:-140px;
 top:50%;
 margin-top:-40px;
 font-size:15px;
 color:#333;
 font-weight:bold;
 text-align:center;
 background-color:#f9f9f9;
}
.query_hint img{position:relative;top:10px;left:-8px;}
</style>
		
<script type="text/javascript" src="https://login.luckydragon88.com/jswrapper/integration.js.php?casino=greatfortune88"></script>

<script type="text/javascript">

function getUrlVars() {
	var vars = {};
	var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
		vars[key] = value;
	});
	return vars;

}

iapiSetCallout('GetTemporaryAuthenticationToken', calloutGetTemporaryAuthenticationToken);


//'ngm', 'ano'
var currentgame = "";
var gametype = "";
function askTempandLaunchGame(type, game) {
	currentgame = game;
	gametype = type;
	var realMode = 1;
	
	$("#query_hint").show();
	
	iapiRequestTemporaryToken(realMode, '424', 'GamePlay');	
}

var clientUrl = "";
function launchMobileClient(temptoken) {
	if (gametype == "mps") {
		clientUrl=''+'?username=' + getUrlVars()["username"] + '&temptoken=' + temptoken + '&game=' + currentgame + '&real=1';
	} else if (gametype = "ngm") {
		//clientUrl = 'http://hub.ld176888.com/igaming/' + '?gameId=' + currentgame + '&real=1' + '&username=${sessionScope.username}&lang=' + document.getElementById("languageform").language.value + '&tempToken=' + temptoken + '&lobby=' + location.href.substring(0,location.href.lastIndexOf('/')+1) + '/indexh5' + '&support=' + location.href.substring(0,location.href.lastIndexOf('/')+1) + '/supporth5' + '&logout=' + location.href.substring(0,location.href.lastIndexOf('/')+1) + '/logouth5';
		
		var uuusername = '${sessionScope.username}'.toUpperCase();
		clientUrl = 'http://hub.ld176888.com/igaming/' + '?gameId=' + currentgame + '&real=1' + '&username='+uuusername+'&lang=zh-cn&tempToken=' + temptoken + 
				'&lobby=${sessionScope.homeurl}' + 
				'&support=${sessionScope.homeurl}' + 
				'&logout=${sessionScope.homeurl}';
		
	}
	document.location = clientUrl;
}


//CALLOUT----------------------------------------------



function calloutGetTemporaryAuthenticationToken(response) {
	
	if (response.errorCode && response.errorCode != 6) {
	//if (response.errorCode) {
		alert("Token failed. " + response.playerMessage + " Error code: " + response.errorCode);
	}
	else {
		launchMobileClient(response.sessionToken.sessionToken);		
	}
}

//如果有默认游戏代码值，则直接跳转
var gametype = "${gametype}" ;
if(gametype != "") {
	askTempandLaunchGame('ngm', gametype);
}


</script>



</head>

<body>

<div id="query_hint" class="query_hint" style="display:none">
    <img src="${ctx }/static/pt/thumb.gif" />正在加载游戏，等会哦...
</div>

<div class="box">
		  <div class="header"><!--header开始-->
          <div class="s-logo"><a href="index.html"><img src="${ctx }/static/pt/images/logo.png" width="80"></a></div>
          
            <button class="am-btn am-btn-primary am-icon-bars category" data-am-offcanvas="{target: '#doc-oc-demo2', effect: 'push'}"></button>
			<!-- 侧边栏内容 -->
			<div id="doc-oc-demo2" class="am-offcanvas">
			  <div class="am-offcanvas-bar">
			    <div class="am-offcanvas-content">
                    <p><a href="${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("最新游戏") %>">最新游戏</a></p>
                    <p><a href="${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("热门游戏") %>">热门游戏</a></p>
                    <p><a href="${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("澳门老虎机") %>">澳门老虎机</a></p>
                    <p><a href="${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("漫威老虎机") %>">漫威老虎机</a></p>
                    <p><a href="${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("累积大奖") %>">累积大奖</a></p>
                    <%-- <p><a href="${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("吃角子老虎机") %>">吃角子老虎机</a></p>
                    <p><a href="${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("街机游戏") %>">街机游戏</a></p> --%>
                    
			    </div>
			  </div>
			</div>
		  </div><!--header结束-->
		  
		  <br>
			<center>
			<form id="languageform">
			<select name="language" id="language">
			  <option value="en">English (en)</option>
			  <option value="zh_cn">Simplified Chinese (zh-cn)</option>
			  <option value="ch">Traditional Chinese (ch)</option>
			</select>
			</form>
			</center>
		  
		  <ul class="menu">
		  	<c:forEach var="item" items="${data }" varStatus="i">
			     
			     <li> <a href="javascript:;" onClick="askTempandLaunchGame('ngm', '${item.gamecodeh5}')"><img src="${ctx}/static/pt/imagesh5/${item.gamecodeh5}.png" width="150" height="150"/> 
			     <p style="white-space:nowrap;  text-overflow:ellipsis;overflow:hidden;">${item.cnname }</p> 
			     <p style="white-space:nowrap;  text-overflow:ellipsis;overflow:hidden;">${item.enname }</p> 
			     </a> 
			     </li>
			     
	        </c:forEach>
		    
		  </ul>
		  
		</div>

<!-- 
<br>
<center>
<form id="languageform">
<select name="language" id="language">
  <option value="en">English (en)</option>
  <option value="zh_cn">Simplified Chinese (zh-cn)</option>
  <option value="ch">Traditional Chinese (ch)</option>
</select>
</form>
</center>



<center><table border="0">
<tbody align="center">

<tr>



</tr>


</tbody>
</table>

<center>
<table border="0">
<tbody align="center">
<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ano')"><img src="${images }/ano.png"><br><center>A Night Out</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'art')"><img src="${images }/art.png"><br><center>Arctic Treasure</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ba')"><img src="${images }/ba.png"><br><center>Baccarat</a></td></center>

</tr>
<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'bl')"><img src="${images }/bl.png"><br><center>Beach Life</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'mobbj')"><img src="${images }/mobbj.png"><br><center>Blackjack</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'bjs')"><img src="${images }/bjs.png"><br><center>Blackjack Switch</a></td></center>

</tr>
<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'bob')"><img src="${images }/bob.png"><br><center>Bonus Bears</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ct')"><img src="${images }/ct.png"><br><center>Captains Treasure</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'cheaa')"><img src="${images }/cheaa.png"><br><center>Casino Holdem</a></td></center>

</tr>
<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'catqc')"><img src="${images }/catqc.png"><br><center>Cat Queen</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'chl')"><img src="${images }/chl.png"><br><center>Cherry Love</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ashcpl')"><img src="${images }/ashcpl.png"><br><center>Chests of Plenty</a></td></center>

</tr>

<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'mobdt')"><img src="${images }/mobdt.png"><br><center>Desert Treasure</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'dlm')"><img src="${images }/dlm.png"><br><center>Dr Lovemore</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'eas')"><img src="${images }/eas.png"><br><center>Easter Surprise</a></td></center>

</tr>
<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'esm')"><img src="${images }/esm.png"><br><center>Esmeralda</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'mobro')"><img src="${images }/mobro.png"><br><center>European Roulette</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'evj')"><img src="${images }/evj.png"><br><center>Everybodys Jackpot</a></td></center>

</tr>
<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'fsf')"><img src="${images }/fsf.png"><br><center>Fantastic Four</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'fdt')"><img src="${images }/fdt.png"><br><center>Frankie Dettoris</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'fdtjg')"><img src="${images }/fdtjg.png"><br><center>Frankie Dettoris JP</a></td></center>


</tr>
<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ashfmf')"><img src="${images }/ashfmf.png"><br><center>Full Moon Fortunes</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'fnfrj')"><img src="${images }/fnfrj.png"><br><center>Funky Fruits</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ges')"><img src="${images }/ges.png"><br><center>Geisha Story</a></td></center>

</tr>
<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'glrj')"><img src="${images }/glrj.png"><br><center>Gladiator Jackpot</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'gos')"><img src="${images }/gos.png"><br><center>Golden Tour</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'bib')"><img src="${images }/bib.png"><br><center>Great Blue</a></td></center>


</tr>
<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'hf')"><img src="${images }/hf.png"><br><center>Halloween Fortune</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ashhotj')"><img src="${images }/ashhotj.png"><br><center>Heart of the Jungle</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'hk')"><img src="${images }/hk.png"><br><center>Highway Kings</a></td></center>

</tr>
<tr>

<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'hlk2')"><img src="${images }/hlk2.png"><br><center>Hulk</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ir')"><img src="${images }/ir.png"><br><center>Ice Run</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'irl')"><img src="${images }/irl.png"><br><center>Irish Luck</a></td></center>

</tr>
<tr>

<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ir2')"><img src="${images }/ir2.png"><br><center>Iron Man 2</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'irmn3')"><img src="${images }/irmn3.png"><br><center>Iron Man 3</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'jpgt')"><img src="${images }/jpgt.png"><br><center>Jackpot Giant</a></td></center>

</tr>
<tr>

<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'jb_mh50')"><img src="${images }/jb_mh50.png"><br><center>Jacks or Better</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'kkg')"><img src="${images }/kkg.png"><br><center>Kong</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'lm')"><img src="${images }/lm.png"><br><center>Lotto Madness</a></td></center>

</tr>


<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'gtsmrln')"><img src="${images }/gtsmrln.png"><br><center>Marilyn Monroe</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'mrcb')"><img src="${images }/mrcb.png"><br><center>Mr. Cashback</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'nian')"><img src="${images }/nian.png"><br><center>Nian Nian You Yu</a></td></center>
</tr>

<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'pmn')"><img src="${images }/pmn.png"><br><center>Panther Moon</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'pgv')"><img src="${images }/pgv.png"><br><center>Penguin Vacation</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'bjp')"><img src="${images }/bjp.png"><br><center>Perfect Blackjack</a></td></center>
</tr>

<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'pst')"><img src="${images }/pst.png"><br><center>Pharaohs Secrets</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'pnp')"><img src="${images }/pnp.png"><br><center>Pink Panther</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'gtspor')"><img src="${images }/gtspor.png"><br><center>Plenty O Fortune</a></td></center>
</tr>

<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'gtssmbr')"><img src="${images }/gtssmbr.png"><br><center>Samba Brazil</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ssp')"><img src="${images }/ssp.png"><br><center>Santa Surprise</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'samz')"><img src="${images }/samz.png"><br><center>Secrets of the Amazon</a></td></center>
</tr>

<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'shmst')"><img src="${images }/shmst.png"><br><center>Sherlock Mystery</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'sis')"><img src="${images }/sis.png"><br><center>Silent Samurai</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'sib')"><img src="${images }/sib.png"><br><center>Silver Bullet</a></td></center>
</tr>

<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'spidc')"><img src="${images }/spidc.png"><br><center>Spider-Man</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'sol')"><img src="${images }/sol.png"><br><center>Streak of Luck</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'cnpr')"><img src="${images }/cnpr.png"><br><center>Sweet Party</a></td></center>
</tr>

<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'tpd2')"><img src="${images }/tpd2.png"><br><center>Thai Paradise</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'avng')"><img src="${images }/avng.png"><br><center>The Avengers</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'donq')"><img src="${images }/donq.png"><br><center>The Riches of<br>Don Quixote</a></td></center>
</tr>

<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'gtssprs')"><img src="${images }/gtssprs.png"><br><center>The Sopranos</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'tmqd')"><img src="${images }/tmqd.png"><br><center>The Three Musketeers</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'vcstd')"><img src="${images }/vcstd.png"><br><center>Vacation Station Deluxe</a></td></center>
</tr>

<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'whk')"><img src="${images }/whk.png"><br><center>White King</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ashwwm')"><img src="${images }/ashwwm.png"><br><center>Who Wants To Be<br>A Millionaire</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'gtswg')"><img src="${images }/gtswg.png"><br><center>Wild Gambler</a></td></center>
</tr>


<tr>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'ashwgaa')"><img src="${images }/ashwgaa.png"><br><center>Wild Gambler<br>Arctic Adventure</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'wlg')"><img src="${images }/wlg.png"><br><center>Wu Long</a></td></center>
<td><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'xmn50')"><img src="${images }/xmn50.png"><br><center>X-Men 50 Lines</a></td></center>
</tr>

<tr>
<td colspan="3"><a href="javascript:void(0)" onClick="askTempandLaunchGame('ngm', 'zcjb')"><img src="${images }/zcjb.png"><br><center>Zhao Cai Jin Bao</a></td></center>
</tr>


</tbody>
</table></center>
 -->
 
 
</body></html>
