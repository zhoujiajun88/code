package com.ixiaozhi.blog.manage;

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
 * 后台查看访问次数
 * 
 * @author zjj
 * 
 */
public class ShowCountJSON extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2474768688199236288L;

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("text/javascript;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			long count = 0;
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
				count = 0;
			}
			out.println("{\"count\":" + count + "}");
		} catch (Exception ex) {
			out.println("{\"count\":0}");
		}
	}
}
