<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.ixiaozhi.seo.ShowArticle"%>
<!DOCTYPE HTML>
<html xmlns:wb="http://open.weibo.com/wb">
<head>
<title>有爱的小止</title>
<meta name="author" content="zhoujiajun,http://www.zhoujiajun.com" />
<meta charset="UTF-8" />
<%
	// 取得网址参数
	String pathInfo = request.getPathInfo();
	if (pathInfo == null) {
		response.sendRedirect("../404.html");
		return;
	}
	String articleStaticURL = pathInfo.substring(1);
%>
<script>
	var url ='<%=articleStaticURL%>';
</script>
<link rel="stylesheet" href="../CSS/common.css" />
<link rel="stylesheet" href="../CSS/nprogress.css" />
<script type="text/javascript" src="../JS/jquery.js"></script>
<script type="text/javascript" src="../JS/jquery.cookie.js"></script>
<script type="text/javascript" src="../JS/nprogress.js"></script>
<script type="text/javascript" src="../JS/common.js"></script>
<script type="text/javascript" src="../JS/showArticle.js"></script>
<!--[if lt IE 9]><script src="../JS/html5.js"></script><![endif]-->
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=3528657635" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header id="banner"> </header>

	<aside>
		<nav id="nav"></nav>
		<section>
			<header> 小止 </header>
			<div id="ixiaozhi"></div>
		</section>
		<section>
			<header> 推荐阅读 </header>
			<div id="guest_like"><script type="text/javascript" c=gd charset="utf-8" src="http://tui.cnzz.net/cs.php?id=1000077025"></script>
			</div>
		</section>
		<section>
			<header> 标签云 </header>
			<div id="tag_cloud"></div>
		</section>
		<section>
			<header> 友情链接 </header>
			<div id="friendly_link"></div>
		</section>
	</aside>

	<article>
		<header>
			<h1 id="articleTitle"></h1>
			<time>
				时间<span id="postDate"></span>标签<span id="tags"></span>阅读数<span id="readerCount"></span> 作者<span><a href="https://plus.google.com/+%E5%91%A8%E5%AE%B6%E4%BF%8A?rel=author"> 周家俊</a></span>
				<!-- JiaThis Button BEGIN -->
				<div class="jiathis_style" style="float: right;">
					<a class="jiathis_button_tsina"></a> <a class="jiathis_button_weixin"></a> <a class="jiathis_button_cqq"></a> <a class="jiathis_button_qzone"></a> <a class="jiathis_button_tqq"></a> <a
						class="jiathis_button_renren"></a> <a class="jiathis_button_fb"></a> <a class="jiathis_button_twitter"></a> <a class="jiathis_button_email"></a> <a class="jiathis_button_fav"></a> <a
						class="jiathis_button_print"></a> <a class="jiathis_button_copy"></a>
				</div>
				<!-- JiaThis Button END -->

			</time>
		</header>
		<section id="articleDescription"></section>
		<section id="articleContent">文章内容读取中</section>
	</article>

	<div id="comment">
		<wb:comments url="auto" border="y" width="auto" appkey="3528657635"></wb:comments>
	</div>

	<footer id="copyright"> </footer>
	
	<noscript>
		<%
			ShowArticle sa = new ShowArticle();
			out.println(sa.getArticle(articleStaticURL));
		%>
	</noscript>
</body>
</html>