package com.ixiaozhi.blog.manage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.UserServiceFactory;
import com.ixiaozhi.entity.Article;
import com.ixiaozhi.entity.ArticleCount;
import com.ixiaozhi.entity.Tags;
import com.ixiaozhi.tools.PMF;

public class PostArticleJSON extends HttpServlet {
	/**
	 * 发布或修改文章<br>
	 * 返回一个JSON的状态
	 */
	private static final long serialVersionUID = 5247688856547260701L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/javascript;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			// 获取提交的表单
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			String description = req.getParameter("description");
			String tag = req.getParameter("tags");
			String staticURL = req.getParameter("staticURL");
			String key = req.getParameter("key");
			// 解析标签tags
			String tags[] = tag.split(",");
			List<String> tagList = Arrays.asList(tags);

			if (key == null || key.equals("")) { // 发布新文章
				// 变量
				int articleCount = 1;
				// 获取当前的日期
				Date date = new Date();
				// 封装文章对象
				Article article = new Article(UserServiceFactory.getUserService().getCurrentUser(), title, new Text(content), description, date, 0, staticURL, tagList);
				// 数据库PM
				PersistenceManager pm = PMF.get().getPersistenceManager();
				// 查询文章总数
				Query query = pm.newQuery(ArticleCount.class, "key==\"1\"");
				@SuppressWarnings("unchecked")
				List<ArticleCount> results = (List<ArticleCount>) query.execute();
				if (!results.isEmpty()) {
					for (ArticleCount articleCountDatabase : results) {
						articleCount = articleCountDatabase.getArticleNumber() + 1;
					}
				} else {
					articleCount = 1;
				}
				// 封装文章数对象
				ArticleCount ac = new ArticleCount("1", articleCount);
				// 存入文章数,文章对象,标签对象至数据库
				pm.makePersistentAll(ac, article);
				// 封装文章标签对象
				for (int temp = 0; temp < tagList.size(); temp++) {
					pm.makePersistent(new Tags(tagList.get(temp)));
				}
				pm.close();
				query.closeAll();
			} else {// 编辑文章
				// 获取当前的日期
				Date date = new Date();
				// 封装文章对象
				Article article = new Article(UserServiceFactory.getUserService().getCurrentUser(), title, new Text(content), description, date, 0, staticURL, tagList);
				article.setKey(KeyFactory.stringToKey(key));
				// 数据库PM
				PersistenceManager pm = PMF.get().getPersistenceManager();
				Article oldarticle = pm.getObjectById(Article.class, key);
				article.setArticleReaderCount(oldarticle.getArticleReaderCount());
				article.setArticlePostDate(oldarticle.getArticlePostDate());
				// 更新至数据库
				pm.makePersistent(article);
				pm.close();
			}
			out.print("{\"status\":true,\"description\":\"操作成功\"}");
		} catch (Exception ex) {
			out.print("{\"status\":false,\"errorcode\":\"" + ex.toString().replaceAll("\"", " ") + "\"}");
		}
		out.close();
	}
}
