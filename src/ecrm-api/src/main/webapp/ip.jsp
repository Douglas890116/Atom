<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.maven.util.WebInfoHandle"%>
<%@ page import="com.maven.util.HttpPostUtil"%>
<%@ page import="net.sf.json.JSONObject"%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link href="${ctx }/static/css/public.css" rel="stylesheet" type="text/css">
	<title>IP归属地</title>
	
	
<%
/***
{
    "code": 0,
    "data": {
        "country": "菲律宾",
        "country_id": "PH",
        "area": "",
        "area_id": "",
        "region": "",
        "region_id": "",
        "city": "",
        "city_id": "",
        "county": "",
        "county_id": "",
        "isp": "",
        "isp_id": "",
        "ip": "180.232.108.150"
    }
}
***/

String ip = request.getParameter("ip");
String url = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;

String result = null;
try {
	result = HttpPostUtil.doGetSubmit(url);
} catch (Exception e) {
	e.printStackTrace();
}

String country = "请刷新重试";
String country_id = "请刷新重试";
String area = "请刷新重试";
String region = "请刷新重试";
String city = "请刷新重试";

if(result != null) {
	JSONObject object = JSONObject.fromObject(result);
	if(object.getString("code").equals("0")) {
		object = object.getJSONObject("data");
		country = object.getString("country");
		country_id = object.getString("country_id");
		area = object.getString("area");
		region = object.getString("region");
		city = object.getString("city");
	}
}

%>
</head>
	
<body bgcolor="#333333">

	<p></p><p></p><p></p><p></p>
	
	<center>
    	
    	<table border="0" cellpadding="4" cellspacing="0" class="toTab" width="50%" align="center">
  			  <tbody>
      			
      			<tr>
     				<td class="toLabelTd tdCenter" colspan="2" align="center">
     					<h3><font color="red">查询IP：<%= ip %></font></h3>
     				</td>
     			</tr>
     			<tr>
      				<td class="toDataTd tdCenter" width="40%" align="right">国家：</td>
      			    <td class="toDataTd tdCenter" width="60%"><h3><%= country %></h3></td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="40%" align="right">国家编码：</td>
      			    <td class="toDataTd tdCenter" width="60%"><h3><%= country_id %></h3></td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="40%" align="right">地区：</td>
      			    <td class="toDataTd tdCenter" width="60%"><h3><%= area %></h3></td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="40%" align="right">省份：</td>
      			    <td class="toDataTd tdCenter" width="60%"><h3><%= region %></h3></td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="40%" align="right">城市：</td>
      			    <td class="toDataTd tdCenter" width="60%"><h3><%= city %></h3></td>
      			</tr>
      			
      			
      			<tr>
     				<td class="toLabelTd tdCenter" colspan="3" align="center">
     					<h3>你本机IP是：<%= WebInfoHandle.getClientRealIP(request) %></h3>
     				</td>
     			</tr>
     			
  			  </tbody>
    	</table>
    	
    </center>
</body>
</html>
