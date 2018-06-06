<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <div class="demo-content" style="margin: 30px;">
    <div class="row">
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr/>
        <label class="control-label">
        <strong>
            <p>请上传需要导入数据的Excel文件，【*.xls 或 *.xlsx】<p>
            <p>数据格式：</p>
            <li>第一行为表头，第二行开始为实际数据</li>
            <li>数据列从左至右按【活动编码、用户账号、充值金额、流水倍数、充值说明】顺序排列</li>
        </strong>
        </label>
        <div class="controls">
        <div class="span8">  
              <div id="J_Uploader"></div>
        </div>
        </div>
    </div>
  </div>
</body>
</html>
<!-- script start --> 
<script src="http://g.alicdn.com/bui/bui/1.1.21/config.js"></script>
<script type="text/javascript">
BUI.use('bui/uploader',function (Uploader) { 
  /**
   * 返回数据的格式
   *  默认是 {url : 'url'},否则认为上传失败
   *  可以通过isSuccess 更改判定成功失败的结构
   */
  var uploader = new Uploader.Uploader({
    render : '#J_Uploader',
    url : '${ctx}/moneyInAndOut/uploadRecord',
    rules : {
        //文的类型
        ext: ['.xlsx,.xls','文件类型只能为{0}'],
        //上传的最大个数
        max: [1, '文件的最大个数不能超过{0}个'],
        //文件大小的最大值,单位也是kb
        maxSize: [10240, '文件大小不能大于10M']
    },
    //根据业务需求来判断上传是否成功
    isSuccess: function(result){
      if(result && result.status){
          BUI.Message.Alert("上传成功.");
      }else{
          BUI.Message.Alert("上传失败!");
      }
    }
  }).render();
});
</script>
<!-- script end -->