package com.ixiaozhi.blog;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import com.ixiaozhi.tools.PMF;

public class ShowArticleByURLJSON extends HttpServlet {
	/**
	 * 通过URL返回一篇文章<br>
	 * 如果没有找到文章跳转至404.html页
	 */
	private static final long serialVersionUID = 513699606787243607L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/javascript;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();

		// 日期格式化器
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		String back = "";

		try {
			String articleStaticURL = req.getParameter("url");

			// 利用URL从数据库里取得文章
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(Article.class, "articleStaticURL==articleStaticURLParam");
			query.declareParameters("String articleStaticURLParam");

			@SuppressWarnings("unchecked")
			List<Article> articles = (List<Article>) query.execute(articleStaticURL); // 判断取得的文章是否为空
			if (articles == null || articles.size() == 0) {
				pm.close();
				query.closeAll();
				out.print("{\"status\":false,\"errorcode\":\"404\"}");
				out.close();
				return;
			}

			Article article = articles.get(0); // 文章的阅读数+1
			article.setArticleReaderCount(article.getArticleReaderCount() + 1);
			pm.makePersistent(article);
			pm.close();
			query.closeAll();

			JSONObject js = new JSONObject();
			JSONArray jsonArray = new JSONArray();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("articleTitle", article.getArticleTitle());
			jsonObject.put("articlePostDate", df.format(article.getArticlePostDate()));
			jsonObject.put("articleTags", article.getArticleTags());
			jsonObject.put("articleReaderCount", article.getArticleReaderCount());
			jsonObject.put("articleDescription", article.getArticleDescription());
			jsonObject.put("articleContent", article.getArticleContent().getValue());
			jsonArray.put(jsonObject);

			js.put("status", true);
			js.put("article", jsonArray);

			back = js.toString();
		} catch (Exception e) {
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
