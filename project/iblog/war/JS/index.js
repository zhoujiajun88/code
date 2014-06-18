$(document).ready(function() {
	$('#article').hide();
	NProgress.set(0.0);
	// 取得文章列表
	$.ajax({
		type : 'POST',
		url : 'showArticle.json',
		data : {
			t : t,
			p : p
		},
		dataType : 'JSON',
		success : function(data) {
			NProgress.set(0.5);
			var status = data.status;
			if (status == true) {
				html = '';
				pageControl = '<section>';
				NProgress.set(0.6);
				if (data.article.length > 0) {// 显示文章列表
					$.each(data.article, function(i, n) {
						html += '<article onclick="window.location=\'article/' + n['articleStaticURL'] + '\';">';
						html += '<header>';
						html += '<h1>';
						html += '<a href="article/' + n['articleStaticURL'] + '">' + n['articleTitle'] + '</a>';
						html += '</h1>';
						html += '<time>' + n['articlePostDate'] + '</time>&nbsp;&nbsp;&nbsp;';
						html += n['articleReaderCount'] + '次阅读';
						html += '</header>';
						html += '<section>';
						html += n['articleDescription'];
						html += '</section>';
						html += '</article>';
					});
					NProgress.set(0.7);
					var pages = data.page;
					var allPages = data.allPages;
					if (allPages > 1) {
						// 不是第一页时，显示首页和上一页
						if (pages > 1) {
							pageControl += '<a href="/?p=1">首页</a>';
							pageControl += '<a href="/?p=' + (pages - 1) + '">上一页</a>';
						}
						// 第3页及第3页以上时，显示前两页的页标
						if (pages >= 3) {
							pageControl += '<a href="/?p=' + (pages - 2) + '">&nbsp;' + (pages - 2) + '&nbsp;</a>';
							pageControl += '<a href="/?p=' + (pages - 1) + '">&nbsp;' + (pages - 1) + '&nbsp;</a>';
						}
						// 当前页面
						pageControl += '<span>&nbsp;' + pages + '&nbsp;</span>';
						// 非倒数两页时，显示后两页的页标
						if (allPages - pages >= 2) {
							pageControl += '<a href="/?p=' + (pages + 1) + '">&nbsp;' + (pages + 1) + '&nbsp;</a>';
							pageControl += '<a href="/?p=' + (pages + 2) + '">&nbsp;' + (pages + 2) + '&nbsp;</a>';
						}
						// 页码没有到最后一页的时候，显示下一页与尾页的页标
						if (pages < allPages) {
							pageControl += '<a href="/?p=' + (pages + 1) + '">下一页</a>';
							pageControl += '<a href="/?p=' + allPages + '">尾页</a>';
						}
						pageControl += '</section>';
					}
					NProgress.set(0.9);
				} else {// 没有文章时
					html += '<article>没有';
					if (t != null && t != "") {
						html += '标签为' + t;
					}
					if (p == null || p == "") {
						p = 1;
					}
					html += '第' + p + '页的文章</article>';
				}
				$('#article').html(html);
				$('#article').append(pageControl);
				$('#article').fadeIn(1000);
				NProgress.set(0.9);
			} else {// 拉取文章错误
				alert(data.errorcode);
				NProgress.set(0.9);
			}
			NProgress.set(1.0);
		},
		error : function() {
			NProgress.set(1.0);
		}
	});
});
