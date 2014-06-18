package com.ixiaozhi.seo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ixiaozhi.entity.Article;
import com.ixiaozhi.entity.Tags;
import com.ixiaozhi.tools.PMF;

public class SiteMapServlet extends HttpServlet {
	/**
	 * 生成标准的sitemap xml格式化数据
	 */
	private static final long serialVersionUID = -4500484531129043092L;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		// 修改网站域名
		String site = "http://" + req.getHeader("Host");

		resp.setContentType("application/xml;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd");

		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" >");
		out.println("<!-- created with iBlog System, author: www.zhoujiajun.com -->");

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Article.class);
		query.setOrdering("articlePostDate desc");

		List<Article> results = (List<Article>) query.execute();
		for (Article article : results) {
			out.println("<url>");
			out.print("    <loc>");
			out.print(site + "/article/" + article.getArticleStaticURL());
			out.println("</loc>");
			out.print("    <lastmod>");
			out.print(df.format(article.getArticlePostDate()));
			out.println("</lastmod>");
			out.println("    <changefreq>weekly</changefreq>");
			out.println("</url>");
		}

		Query query2 = pm.newQuery(Tags.class);
		List<Tags> tags = (List<Tags>) query2.execute();
		for (Tags tag : tags) {
			out.println("<url>");
			out.print("    <loc>");
			out.print(site + "/?t=" + URLEncoder.encode(tag.getTagName()));
			out.println("</loc>");
			out.println("    <changefreq>weekly</changefreq>");
			out.println("</url>");
		}

		out.println("</urlset>");
		out.close();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		doGet(req, resp);
	}
}
