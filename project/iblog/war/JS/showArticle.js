$(document).ready(function() {
	NProgress.set(0.0);
	var title = $(document).attr('title');
	$('#articleContent').hide();
	$('#articleDescription').hide();
	$('#comment').hide();
	NProgress.set(0.4);
	// 取得文章数据
	$.ajax({
		type : 'POST',
		url : '/showArticleByURL.json',
		data : {
			url : url
		},
		dataType : 'JSON',
		success : function(data) {
			NProgress.set(0.5);
			var status = data.status;
			if (status == true) {
				var article = data.article[0];
				// 设置标题，及各项内容
				$(document).attr('title', article.articleTitle + " - " + title);
				$('#articleTitle').html(article.articleTitle);
				$('#postDate').html(article.articlePostDate);
				var tagstemp = '[&nbsp;&nbsp;';
				for (var i = 0; i < article.articleTags.length; i++) {
					tagstemp += '<a href="/?t=' + encodeURI(article.articleTags[i]) + '">' + article.articleTags[i] + '</a>&nbsp;&nbsp;';
				}
				$('#tags').html(tagstemp + ']');
				$('#readerCount').html(article.articleReaderCount);
				$('#articleDescription').html(article.articleDescription);
				$('#articleContent').html(article.articleContent);
				NProgress.set(0.7);
				$('#articleContent').slideDown("2000");
				$('#articleDescription').slideDown("3000");
				$('#comment').slideDown("2000");
				// 设置分享内容
				var jiathis_config = {
					siteNum : 12,
					sm : "tsina,weixin,cqq,qzone,tqq,renren,fb,twitter,email,fav,print,copy",
					summary : "",
					boldNum : 5,
					ralateuid : {
						"tsina" : "有爱的小止"
					},
					appkey : {
						"tsina" : "3528657635"
					},
					shortUrl : false,
					hideMore : true
				}
				NProgress.set(0.8);
				$.getScript("http://v3.jiathis.com/code/jia.js");
				NProgress.set(0.9);
			} else {
				var errorcode = data.errorcode;
				if (errorcode == '404') {
					window.location = "/404.html";
				} else {
					alert(errorcode);
				}
			}
			NProgress.set(1.0);
		},
		error : function() {
			NProgress.set(1.0);
		}
	});
});