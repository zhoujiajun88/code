package com.ixiaozhi.blog;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ixiaozhi.entity.Article;
import com.ixiaozhi.entity.ArticleCount;
import com.ixiaozhi.tools.PMF;

public class ShowArticleListJSON extends HttpServlet {
	/**
	 * 查询文章列表，用于前台显示<br>
	 * 参数：p:页码，t:标签<br>
	 * 返回一个JSON格式化数据
	 */
	private static final long serialVersionUID = 6648350355378378560L;

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/javascript;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		PrintWriter out = resp.getWriter();
		String back = "";

		try {
			int pages = Integer.parseInt((req.getParameter("p") == null || req.getParameter("p").equals("")) ? "1" : req.getParameter("p"));// 当前页
			int allPages = -1;// 所有页数
			String tag = req.getParameter("t");

			List<Article> results = new ArrayList<Article>();

			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(Article.class);
			if (tag == null || tag.equals("")) {
				query.setOrdering("articlePostDate desc");
				query.setRange((pages - 1) * 5, pages * 5);
				results = (List<Article>) query.execute();

				if (!results.isEmpty()) {
					Query query2 = pm.newQuery(ArticleCount.class, "key==\"1\"");
					List<ArticleCount> results2 = (List<ArticleCount>) query2.execute();
					for (ArticleCount articleCountDatabase : results2) {
						int articleCount = articleCountDatabase.getArticleNumber();
						allPages = (articleCount - 1) / 5 + 1;
					}
				}
			} else {
				tag = URLDecoder.decode(tag, "UTF-8");
				query.setFilter("articleTags == tagParam");
				query.declareParameters("String tagParam");
				query.setOrdering("articlePostDate desc");
				results = (List<Article>) query.execute(tag);
			}

			JSONObject js = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			if (!results.isEmpty()) {
				for (Article article : results) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("articleTitle", article.getArticleTitle());
					jsonObject.put("articlePostDate", df.format(article.getArticlePostDate()));
					jsonObject.put("articleReaderCount", article.getArticleReaderCount());
					jsonObject.put("articleDescription", article.getArticleDescription());
					jsonObject.put("articleStaticURL", article.getArticleStaticURL());
					jsonArray.put(jsonObject);
				}
			}
			js.put("status", true);
			js.put("article", jsonArray);
			js.put("allPages", allPages);// 首页显示的时候，为正常数字；当显示tag文章的时候，显示-1
			js.put("page", pages);

			pm.close();
			query.closeAll();

			back = js.toString();

		} catch (Exception e) {
			e.printStackTrace();
			back = "{\"status\":false,\"errorcode\":\"" + e.toString().replaceAll("\"", " ") + "\"}";
		}

		String callback = req.getParameter("callback");
		if (callback == null || callback.equals("")) {
			out.print(back);
		} else {
			out.print(callback + "(" + back + ")");
		}
		out.close();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		doPost(req, resp);
	}
}
