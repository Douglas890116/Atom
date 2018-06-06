/*top_nav 自动刷新时间*/


function showtimes() {
	var Datas = new Date();
	var	nowData = Datas.getFullYear()+"-"+(Datas.getMonth()+1)+"-"+Datas.getDate()+" "+Datas.getHours()+":"+Datas.getMinutes()+":"+Datas.getSeconds()+"(GMT+8)"
	$(".times>a").html(nowData);
}

setInterval(showtimes,1000);// 注意函数名没有引号和括弧！ 

$(".gameN>li").click(function(){
	$(this).addClass("on").siblings().removeClass("on")
})

$(".tbl").click(function(){
	$(".tbl ul").removeClass("show");
	$(this).find("ul").addClass('show');
})
