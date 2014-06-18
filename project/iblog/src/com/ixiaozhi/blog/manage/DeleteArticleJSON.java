package com.ixiaozhi.blog.manage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ixiaozhi.entity.Article;
import com.ixiaozhi.entity.ArticleCount;
import com.ixiaozhi.tools.PMF;

public class DeleteArticleJSON extends HttpServlet {
	/**
	 * 删除一篇文章<br>
	 * 返回一个JSON的状态
	 */
	private static final long serialVersionUID = 1336051662848373124L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/javascript;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			String key = req.getParameter("key");
			// 数据库PM
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Article article = pm.getObjectById(Article.class, key);
			// 查询文章总数
			int articleCount = 0;
			Query query = pm.newQuery(ArticleCount.class, "key==\"1\"");
			@SuppressWarnings("unchecked")
			List<ArticleCount> results = (List<ArticleCount>) query.execute();
			if (!results.isEmpty()) {
				for (ArticleCount articleCountDatabase : results) {
					articleCount = articleCountDatabase.getArticleNumber() - 1;
				}
			} else {
				articleCount = 0;
			}
			// 封装文章数对象
			ArticleCount ac = new ArticleCount("1", articleCount);
			// 存入文章数,文章对象,标签对象至数据库
			pm.makePersistent(ac);
			// 从数据库删除
			pm.deletePersistent(article);
			pm.close();

			out.print("{\"status\":true,\"description\":\"删除成功\"}");
		} catch (Exception ex) {
			out.print("{\"status\":false,\"errorcode\":\"" + ex.toString().replaceAll("\"", " ") + "\"}");
		}
		out.close();
	}
}
