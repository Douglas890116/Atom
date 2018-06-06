<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
  <body> 
    <div class="errorContainer">
        <h2>Operation Success! 操作成功,<a href="${ctx}${urls}">点击此处立即返回(<span id="timeId"></span>)</a></h2>
    </div>
  </body>
<script type="text/javascript">
 $(document).ready(function() {
    function jump(count) { 
        $('#timeId').text(count); 
        setTimeout(function(){ 
            count--; 
            if(count > 0) { 
                jump(count); 
            } else {
                location.href="${ctx}${urls}";  
            } 
        }, 1000); 
    } 
    jump(3); 
}); 
</script>
</html>
