function getRem(pwidth,prem){
	var html = document.getElementsByTagName("html")[0];
	var oWidth = document.body.clientWidth || document.documentElement.clientWidth;
	html.style.fontSize = oWidth/pwidth*prem+"px";
}

window.onload = function(){
	getRem(414,100);
}

// onresize 屏幕宽度改变的时候
window.onresize = function(){
	getRem(414,100);
}

mui('body').on('tap','a',function(){document.location.href=this.href})