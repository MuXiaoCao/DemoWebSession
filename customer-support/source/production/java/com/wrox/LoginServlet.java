package com.wrox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@WebServlet(name = "loginServlet", urlPatterns = "/login")
/**
 * 登录servlet
 *
 */
public class LoginServlet extends HttpServlet {
	
	// 用户数据库
	private static final Map<String, String> userDatabase = new Hashtable<>();

	static {
		userDatabase.put("Nicholas", "password");
		userDatabase.put("Sarah", "drowssap");
		userDatabase.put("Mike", "wordpass");
		userDatabase.put("John", "green");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 退出登录
		if (request.getParameter("logout") != null) {
			// session的注销
			session.invalidate();
			response.sendRedirect("login");
			return;
		} else if (session.getAttribute("username") != null) {
			response.sendRedirect("tickets");
			return;
		}

		request.setAttribute("loginFailed", false);
		request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 判断session中是否已经注册该用户，如果是则重定向到票据页面
		if (session.getAttribute("username") != null) {
			response.sendRedirect("tickets");
			return;
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// 登录验证
		if (username == null || password == null || !LoginServlet.userDatabase.containsKey(username)
				|| !password.equals(LoginServlet.userDatabase.get(username))) {
			// 登录失败，则跳转到登录界面
			request.setAttribute("loginFailed", true);
			request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(request, response);
		} else {
			// 登录成功，则首先注册session，然后重置sessionID
			session.setAttribute("username", username);
			request.changeSessionId();
			// 重定向到票据页面
			response.sendRedirect("tickets");
		}
	}
}
