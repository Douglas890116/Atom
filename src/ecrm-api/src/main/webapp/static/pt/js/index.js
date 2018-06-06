//导航栏悬浮
$(document).ready(function() {

		$('.nav_l ul li').hover(function() {
			$(this).find('.navsub').stop().show();
			},
			function(){
			$(this).find('.navsub').stop().hide();
			}
		);
		         
}); 

//PT老虎机
$(function(){
	$(".pt_img .rsp").hide();
	$(".pt_img").hover(function(){
		$(this).find(".rsp").stop().fadeTo(1000,0.5)
		$(this).find(".text").stop().animate({left:'0'}, {duration: 800})
	},function(){
		$(this).find(".rsp").stop().fadeTo(1000,0)
		$(this).find(".text").stop().animate({left:'190'}, {duration: "fast"})
		$(this).find(".text").animate({left:'-190'}, {duration: 0})
	});
});
