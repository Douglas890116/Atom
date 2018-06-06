<%@page import="com.maven.config.SystemConstant"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<html>  
    <head>  
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
        <meta http-equiv="Pragma" content="no-cache" />  
        <script type="text/javascript" src="${statics }/js/ajax-pushlet-client.js"></script>         
        <script type="text/javascript">  
        PL.webRoot = "${SystemConstant.getProperties('push.server')}";
        PL._init();   
        PL.joinListen('/message/testmessage');  
        function onData(event) {   
            alert(event.get("message"));   
            // 离开  
            // PL.leave();  
        }  
        </script>  
    </head>   
</html>  