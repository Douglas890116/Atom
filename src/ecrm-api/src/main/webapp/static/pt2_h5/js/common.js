mui('body').on('tap','a',function(){document.location.href=this.href;});  

mui('.mui-scroll-wrapper').scroll({
	deceleration: 0.0005,
	indicators: false,
	bounce: false
});

var mask = mui.createMask();//callback为用户点击蒙版时自动执行的回调

/*头部刷新按钮样式*/
$("img.refresh_btn").on("tap",function(){
	$(this).addClass("moves");
	setTimeout(function(){
		$("img.refresh_btn").removeClass("moves")
	},500);
})

/*封装的loading窗*/
 var timer = null;
 
 function message (){
 	/*
	  this.loading=function(ss){
	  	$(".loading_l").remove();
	  	 var loadDiv = null;
	  	 loadDiv = `
		 	<div class='loading_l'>
		 		<img src="../img/loading.gif"/>
		 		<p>数据加载中</p>
		 	</div>
		 `		 
		if(ss == "show" || ss == "" || ss == null){
			$(".mui-content").append(loadDiv);
		}else if(ss == "close"){
			$(".loading_l").remove();
		}
			
	  },
	  
	  this.success=function(){
	  	clearInterval(timer)
	  	$(".loading_s").remove();
	  	var loadDiv = null;
	  	loadDiv = `
		 	<div class='loading_s'>
		 		<img src="../img/duih.png"/>
		 		<p>操作成功</p>
		 	</div>
		 `
		 $(".mui-content").append(loadDiv);
		 timer = setInterval(function(){
		 	$(".loading_s").remove();
		 },2000)
	  }*/
}
 var mess = new message(); 	
 