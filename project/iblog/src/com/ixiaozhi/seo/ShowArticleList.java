package com.ixiaozhi.seo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ixiaozhi.entity.Article;
import com.ixiaozhi.tools.PMF;

public class ShowArticleList {
	@SuppressWarnings("unchecked")
	public String getArticleList(String page, String tag) {
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			int pages = Integer.parseInt((page == null || page.equals("")) ? "1" : page);// 当前页
			String html = "";
			List<Article> results = new ArrayList<Article>();

			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(Article.class);

			if (tag == null || tag.equals("")) {
				query.setOrdering("articlePostDate desc");
				query.setRange((pages - 1) * 5, pages * 5);
				results = (List<Article>) query.execute();
			} else {
				query.setFilter("articleTags == tagParam");
				query.declareParameters("String tagParam");
				query.setOrdering("articlePostDate desc");
				results = (List<Article>) query.execute(tag);
			}
			html += "<section>\n";
			if (!results.isEmpty()) {
				for (Article article : results) {
					html += "<section>\n";
					html += "<header><a href=\"/article/" + article.getArticleStaticURL() + "\" >" + article.getArticleTitle() + "</a></header>\n";
					html += "<time>" + df.format(article.getArticlePostDate()) + "</time>\n";
					html += "<article>" + article.getArticleDescription() + "</article>\n";
					html += "</section>\n";
				}
			}
			html += "</section>\n";

			pm.close();
			query.closeAll();

			return html;
		} catch (Exception e) {
			return "获取文章列表出错" + e.toString();
		}
	}
}
