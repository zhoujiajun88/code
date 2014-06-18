package com.ixiaozhi.blog.manage;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ixiaozhi.entity.Article;
import com.ixiaozhi.tools.PMF;

public class ShowAllArticleJSON extends HttpServlet {
	/**
	 * 查询文章列表，用于后台的管理<br>
	 * 返回一个JSON格式化数据
	 */
	private static final long serialVersionUID = -6384992618317492879L;

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/javascript;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		PrintWriter out = resp.getWriter();
		try {
			int pages = Integer.parseInt(req.getParameter("page") == null ? "1" : req.getParameter("page"));
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(Article.class);
			query.setOrdering("articlePostDate desc");
			query.setRange((pages - 1) * 15, pages * 15);
			List<Article> results = (List<Article>) query.execute();

			JSONObject js = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			if (!results.isEmpty()) {
				for (Article article : results) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("key", KeyFactory.keyToString(article.getKey()));
					jsonObject.put("articleAuthor", article.getArticleAuthor().getEmail());
					jsonObject.put("articleTitle", article.getArticleTitle());
					jsonObject.put("articlePostDate", df.format(article.getArticlePostDate()));
					jsonObject.put("articleReaderCount", article.getArticleReaderCount());
					jsonArray.put(jsonObject);
				}
			}
			js.put("status", true);
			js.put("article", jsonArray);

			pm.close();
			query.closeAll();

			out.print(js);
		} catch (Exception e) {
			out.print("{\"status\":false,\"errorcode\":\"" + e.toString().replaceAll("\"", " ") + "\"}");
		}
		out.close();
	}
}
