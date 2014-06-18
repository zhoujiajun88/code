package com.ixiaozhi.blog;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ixiaozhi.entity.Tags;
import com.ixiaozhi.tools.PMF;

public class ShowTagsListJSON extends HttpServlet {
	/**
	 * 获得前二十条标签<br>
	 * 返回一个标签列表
	 */
	private static final long serialVersionUID = 9084275890078089315L;

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/javascript;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();

		String back = "";

		try {
			List<Tags> tags = new ArrayList<Tags>();

			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(Tags.class);
			query.setRange(0, 20);
			tags = (List<Tags>) query.execute();

			JSONObject js = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			if (!tags.isEmpty()) {
				for (Tags tag : tags) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("tag", tag.getTagName());
					jsonArray.put(jsonObject);
				}
			}
			js.put("status", true);
			js.put("tags", jsonArray);

			pm.close();
			query.closeAll();

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
