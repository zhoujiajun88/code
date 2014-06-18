<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.ixiaozhi.seo.ShowArticleList"%>
<!DOCTYPE HTML>
<html>
<head>
<title>有爱的小止</title>
<meta name="keywords" content="有爱的小止,技术博客,JAVA,GAE博客,GAEblog" />
<meta name="description" content="有爱的小止，记录技术。有爱的小止博客是一个个人博客，博主喜欢互联网新技术，关注移动互联网。博客里记录了个人的学习工作经历，有关Java, Android等等。博客是在appengine平台上构建。" />
<meta name="author" content="zhoujiajun,http://www.zhoujiajun.com" />
<meta charset="UTF-8" />
<%
	String pages = request.getParameter("p") == null ? "" : request
			.getParameter("p");
	String tag = request.getParameter("t") == null ? "" : request
			.getParameter("t");
%>
<script>
	var p = '<%=pages%>';
	var t = '<%=tag%>';
</script>
<link rel="stylesheet" href="CSS/common.css" />
<link rel="stylesheet" href="CSS/nprogress.css" />
<script type="text/javascript" src="JS/jquery.js"></script>
<script type="text/javascript" src="JS/jquery.cookie.js"></script>
<script type="text/javascript" src="JS/nprogress.js"></script>
<script type="text/javascript" src="JS/common.js"></script>
<script type="text/javascript" src="JS/index.js"></script>
<!--[if lt IE 9]><script src="JS/html5.js"></script><![endif]-->
</head>
<body>
	<header id="banner"> </header>

	<aside>
		<nav id="nav"></nav>
		<section>
			<header> 推荐阅读 </header>
			<div id="guest_like"><script type="text/javascript" c=gd charset="utf-8" src="http://tui.cnzz.net/cs.php?id=1000077025"></script>
			</div>
		</section>
		<section>
			<header> 小止 </header>
			<div id="ixiaozhi"></div>
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


	<section id="article">
		<article>文章列表正在努力加载中。。。</article>
	</section>

	<footer id="copyright"></footer>

	<noscript>
		<%
			ShowArticleList sa = new ShowArticleList();
			out.println(sa.getArticleList(pages, tag));
		%>
	</noscript>
</body>
</html>
