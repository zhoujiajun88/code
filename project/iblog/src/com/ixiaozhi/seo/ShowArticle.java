package com.ixiaozhi.seo;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ixiaozhi.entity.Article;
import com.ixiaozhi.tools.PMF;

public class ShowArticle {
	/**
	 * 取得文章内容，用于nosrcipt，便于搜索索引
	 */

	@SuppressWarnings("deprecation")
	public String getArticle(String articleStaticURL) {
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String html = "";
		try {
			// 利用URL从数据库里取得文章
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(Article.class, "articleStaticURL==articleStaticURLParam");
			query.declareParameters("String articleStaticURLParam");

			@SuppressWarnings("unchecked")
			List<Article> articles = (List<Article>) query.execute(articleStaticURL); // 判断取得的文章是否为空
			if (articles == null || articles.size() == 0) {
				pm.close();
				query.closeAll();
				return "没有找到文章内容";
			}

			Article article = articles.get(0);
			html += "<section>\n";
			html += "<header><h1>" + article.getArticleTitle() + "</h1></header>\n";
			html += "<time>" + df.format(article.getArticlePostDate()) + "</time>\n";
			html += "<article>" + article.getArticleDescription() + "\n<br><br><br><br>\n" + article.getArticleContent().getValue() + "</article>\n";
			html += "<section>关键字：";
			for (int i = 0; i < article.getArticleTags().size(); i++) {
				html += "<a href=\"/?t=" + URLEncoder.encode(article.getArticleTags().get(i)) + "\">" + article.getArticleTags().get(i) + "</a>&nbsp;&nbsp;";
			}
			html += "&nbsp;&nbsp;阅读次数：" + article.getArticleReaderCount() + "</section>\n";
			html += "</section>\n";

			pm.close();
			query.closeAll();

			return html;
		} catch (Exception e) {
			return "获取文章内容出错" + e.toString();
		}
	}
}
