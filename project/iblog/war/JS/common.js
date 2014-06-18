// 滚动变量
var finished = true;
$(document).ready(function() {
	// 载入网头
	$('#banner').load('/header.html');
	// 载入导航
	$('#nav').load('/nav.html');
	// 载入友情链接
	$('#friendly_link').load('/friendlylink.html');
	// 载入标签云
	$('#tag_cloud').load('/tags.html');
	// 载入页脚
	$('#copyright').load('/footer.html');
	// 载入
	$('#ixiaozhi').load('/ixiaozhi.html');
	// 载入背景
	var bgimages = $.cookie('bgimages');
	if (bgimages == null || bgimages == '') {
		bgimages = Math.floor(Math.random() * 4 + 1); // 1-4
	}
	changeBggrondImg(bgimages);
	viewCount();
});
$(window).scroll(function() {
	if (finished && $(document).scrollTop() > 50) {
		finished = false;
		$('#header_bg').hide();
		$('#banner').animate({width:"300px"});
	}
	if (!(finished) && $(document).scrollTop() <= 50) {
		finished = true;
		$('#header_bg').show();
		$('#banner').animate({width:"100%"});
	}
})
// 更换背景选项
function changeBggrondImg(bgimages) {
	$.cookie('bgimages', bgimages, {
		path : "/"
	});
	$('body').css('background', 'url("../images/bg' + bgimages + '.png")');
}

// 获得URL参数
(function($) {
	$.getUrlParams = function() {
		var vars = [], hash;
		var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
		for (var i = 0; i < hashes.length; i++) {
			hash = hashes[i].split('=');
			vars.push(hash[0]);
			vars[hash[0]] = hash[1];
		}
		return vars;
	}
	$.getUrlParam = function(name) {
		return $.getUrlParams()[name];
	}
})(jQuery);

// 访问统计
function viewCount() {
	$.ajax({
		type : 'post',
		url : '/viewCount.json',
		dataType : 'json',
		success : function(data) {
			$("#viewCount").text("iBlog-" + data.count);
		}
	});
}