/**
 * @author: personal@zhoujiajun.com
 */
$.ajaxSetup({
	cache : false
});

$(document).ready(
		function() {
			addNew('A1', 'addArticle.html', 'A2', 'addArticleSide.html', '在这里可以添加文章或者修改文章内容', '写文章', 900, 400, 20, 20, 'assets/images/icons/icon_16_write.png',
					'assets/images/icons/icon_22_write.png', 'assets/images/icons/icon_32_write.png');

			addNew('B1', 'articleList.html', 'B2', 'articleListSide.html', '这里可以管理所有的文章', '博文管理', 800, 400, 20, 100, 'assets/images/icons/icon_16_list.png',
					'assets/images/icons/icon_22_list.png', 'assets/images/icons/icon_32_list.png');

			addNew('C1', 'viewCount.html', 'C2', 'viewCountSide.html', '整站访问计数', '访问计数', 400, 200, 20, 180, 'assets/images/icons/icon_16_list.png',
					'assets/images/icons/icon_22_list.png', 'assets/images/icons/icon_32_list.png');
		});

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

/**
 * 新定义一个window窗口
 * 
 * @author personal@zhoujiajun.com
 * 
 * @param contentID
 *            内容ID 不可重复，必须
 * @param contentPage
 *            内容网页，必须
 * @param asideID
 *            边栏ID 不可重复，必须
 * @param asidePage
 *            边栏内容 不可重复，必须
 * @param bottomText
 *            底部窗体文字
 * @param windowTitle
 *            窗口标题，必须
 * @param windowWidth
 *            窗体宽度
 * @param windowHeight
 *            窗体高度
 * @param iconLocationX
 *            桌面图标距离屏幕左侧
 * @param iconLocationY
 *            桌面图标距离屏幕右侧
 * @param iconAddress16
 *            图标地址16*16 标题栏
 * @param iconAddress22
 *            图标地址22*22 任务栏
 * @param iconAddress32
 *            图标地址32*32 桌面
 */
function addNew(contentID, contentPage, asideID, asidePage, bottomText, windowTitle, windowWidth, windowHeight, iconLocationX, iconLocationY, iconAddress16, iconAddress22, iconAddress32) {
	$("#desktop").prepend(
			'<a class="abs icon" style="left:' + iconLocationX + 'px;top:' + iconLocationY + 'px;" href="#icon_dock_' + contentID + asideID + '" ' + ' openContentPage="' + contentPage
					+ '" openAsidePage="' + asidePage + '" contentPageID="' + contentID + '" asidePageID="' + asideID + '" >' + '<img src="' + iconAddress32 + '" />' + windowTitle + '</a>');

	$("#desktop").append(
			'<div id="window_' + contentID + asideID + '" class="abs window" style="width:' + windowWidth + 'px;height:' + windowHeight + 'px;" ><div class="abs window_inner"> '
					+ '  <div class="window_top">' + '    <span class="float_left">' + '     <img src="' + iconAddress16 + '"/>' + windowTitle + '     </span>' + '      <span class="float_right">'
					+ '      <a href="#" class="window_min"></a>' + '      <a href="#" class="window_resize"></a>' + '     <a href="#icon_dock_' + contentID + asideID + '" class="window_close"></a>'
					+ ' </span>' + '     </div>' + '  <div class="abs window_content">' + '      <div class="window_aside" id="' + asideID + '">' + '      </div>'
					+ '      <div class="window_main" id="' + contentID + '"></div>' + ' </div>' + '  <div class="abs window_bottom">' + bottomText + '   </div>' + '   </div>'
					+ '       <span class="abs ui-resizable-handle ui-resizable-se"></span>' + '   </div>');

	$("#dock")
			.append(' <li id="icon_dock_' + contentID + asideID + '">' + '  <a href="#window_' + contentID + asideID + '">' + ' <img src="' + iconAddress22 + '"/>' + windowTitle + ' </a>' + '</li>');
}

/**
 * 弹出Kindeditor Dialog
 * 
 * okfun，ok 点击执行的 js 函数名；okparam，ok 点击执行的参数
 */
function showDialog(titleText, bodyText, okfun, okparam) {
	var dialog = KindEditor.dialog({
		width : 350,
		title : titleText,
		body : '<div style="margin:10px;"><strong>' + bodyText + '</strong></div>',
		closeBtn : {
			name : '关闭',
			click : function(e) {
				dialog.remove();
			}
		},
		yesBtn : {
			name : '确定',
			click : function(e) {
				okfun(okparam);
				dialog.remove();
			}
		},
		noBtn : {
			name : '取消',
			click : function(e) {
				dialog.remove();
			}
		}
	});
}
/**
 * 弹出只有确定的kindeditor Dialog
 */
function showTip(titleText, bodyText) {
	var dialog = KindEditor.dialog({
		width : 350,
		title : titleText,
		body : '<div style="margin:10px;"><strong>' + bodyText + '</strong></div>',
		closeBtn : {
			name : '关闭',
			click : function(e) {
				dialog.remove();
			}
		},
		yesBtn : {
			name : '确定',
			click : function(e) {
				dialog.remove();
			}
		}
	});
}

/**
 * 没有动作
 */
function noAction() {

}