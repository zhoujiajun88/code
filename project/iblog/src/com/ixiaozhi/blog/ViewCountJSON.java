package com.ixiaozhi.blog;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ixiaozhi.entity.WebSiteCount;
import com.ixiaozhi.tools.PMF;

/**
 * 访问次数+1 仅支持post 方法
 * 
 * @author zjj
 * 
 */
public class ViewCountJSON extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1583977339501777680L;

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("text/javascript;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			long count = 1;
			// 数据库PM
			PersistenceManager pm = PMF.get().getPersistenceManager();
			// 查询访问总数
			Query query = pm.newQuery(WebSiteCount.class, "key==\"1\"");
			List<WebSiteCount> results = (List<WebSiteCount>) query.execute();
			if (!results.isEmpty()) {
				for (WebSiteCount countClass : results) {
					count = countClass.getViewNumber() + 1;
				}
			} else {
				count = 1;
			}
			// 封装访问总数对象
			WebSiteCount ac = new WebSiteCount("1", count);
			// 存入访问总数至数据库
			pm.makePersistentAll(ac);
			out.println("{\"count\":" + count + "}");
		} catch (Exception ex) {
			out.println("{\"count\":0}");
		}
	}
}
