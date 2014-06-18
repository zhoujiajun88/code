package com.ixiaozhi.tools;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 目录默认主页， 如 /sign 跳转至 /sign/
 * 
 * @author zjj
 * 
 */
public class RedirectSignServlet extends HttpServlet {

	private static final long serialVersionUID = -859519456749851710L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.sendRedirect("/sign/");
	}
}
