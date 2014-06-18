package com.ixiaozhi.blog.manage;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ixiaozhi.entity.Article;
import com.ixiaozhi.tools.PMF;

public class QueryArticleByKeyJSON extends HttpServlet {
	/**
	 * 根据Key查询详细的文章<br>
	 * 返回一个JSON格式化数据
	 */
	private static final long serialVersionUID = -8964683442356383292L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/javascript;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			// 取得网址参数
			String keys = req.getParameter("key");
			// 封闭成Key
			Key key = KeyFactory.stringToKey(keys);
			// 查询
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Article article = pm.getObjectById(Article.class, key);
			// 返回JSON
			JSONObject js = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("key", keys);
			jsonObject.put("articleAuthor", article.getArticleAuthor().getEmail());
			jsonObject.put("articleTitle", article.getArticleTitle());
			jsonObject.put("articleContent", article.getArticleContent().getValue());
			jsonObject.put("articleDescription", article.getArticleDescription());
			jsonObject.put("articlePostDate", df.format(article.getArticlePostDate()));
			jsonObject.put("articleReaderCount", article.getArticleReaderCount());
			jsonObject.put("articleStaticURL", article.getArticleStaticURL());
			jsonObject.put("articleTags", article.getArticleTags());
			jsonArray.put(jsonObject);
			js.put("status", true);
			js.put("article", jsonArray);

			pm.close();

			out.print(js);
		} catch (Exception e) {
			out.print("{\"status\":false,\"errorcode\":\"" + e.toString().replaceAll("\"", " ") + "\"}");
		}
		out.close();
	}
}
