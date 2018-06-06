

$(".mui-scroll .last").on("tap",function(){
	$(".addGame").addClass('on')
	mask.show();//显示遮罩
});

$(".close").on("tap",function(){
	$(".addGame").removeClass('on');
	mask.close();
});
/*
// 动态生产 游戏类别
for(var i =0 ;i<10;i++){
	var str = `
		 <ul class="game_name">
			<li>基诺--数字${i}</li>
			<li><div class="mui-switch mui-switch-green" data_id='${i}'><div class="mui-switch-handle"></div></div></li>
		</ul>
	`
	$(".addGame .mui-scroll").append(str);
	changes(i);
}
*/
//监听开关是否打开 
	$(".addGame").on("toggle",'div.mui-switch',function(event){
	if(event.detail.isActive){
		 var id = $(this).attr("data_id");
	     window.localStorage.setItem(id,'on') 
	     console.log(window.localStorage.getItem(id));     
	     changes(id);
	  }else{
	  	 var id = $(this).attr("data_id");
	     window.localStorage.setItem(id,'off') 
	     console.log(window.localStorage.getItem(id));
	     changes(id);
	  }
	})
/*	
// 根据打开的开关，把相应的游戏 添加到 index 滚动中
function changes (id){
	if(window.localStorage.getItem(id)=='on'){
		$(".addGame .game_name").eq(id).find('.mui-switch').addClass("mui-active");
		var names = $(".addGame .game_name").eq(id).find('.mui-switch').parent().prev().html();
	   	var contents = `
	   		 <div class="mui-control-item oneS${id}">${names}</div>
	   	`
	   	$(".scroll_row .mui-scroll .last").before(contents);
	}else if(window.localStorage.getItem(id)=='off'){
		$(".addGame .game_name").eq(id).find('.mui-switch').removeClass("mui-active");
		$(".oneS"+id).remove();
	}
}
*/
	
/*动态生成 游戏列表*/
	/*
for(var i =0;i<30;i++){
	var str =`
		<div class="mui-col-sm-4 mui-col-xs-4 lists">
       		<div>
            	<img src="img/pic.png" />
            	<ul class="try_play">
            		<li>免费试玩</li>
            		<li>立即游戏</li>
            	</ul>	
            </div>
        </div>
	`
	$(".game_list .mui-row").append(str);
}
*/
$(".lists>div").on('tap',function(){
	$(".try_play").removeClass("show");
	$(this).find(".try_play").addClass("show");
})