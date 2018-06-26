    <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
    <html>  
        <head>  
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
            <meta http-equiv="Pragma" content="no-cache" />  
            <script type="text/javascript" src="ajaxfileupload/ajax-pushlet-client.js"></script>         
            <script type="text/javascript">  
            	PL.webRoot = "http://localhost:8080/ecrm-comet/";
                PL._init();   
                PL.joinListen('/message/testmessage');  
                function onData(event) {   
                    alert(event.get("message"));   
                    // 离开  
                    // PL.leave();  
                }  
                parent.jsonptest("调用父方法");
            </script>  
        </head>  
        <body>  
        </body>  
    </html>  