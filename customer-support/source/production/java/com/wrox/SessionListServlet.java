package com.wrox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "sessionListServlet", urlPatterns = "/sessions")
/**
 * 用于显示当前所有会话状态的servlet
 *
 */
public class SessionListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 检测权限
		if (request.getSession().getAttribute("username") == null) {
			response.sendRedirect("login");
			return;
		}
		// 传递一些session注册类中的一些参数，供前端显示
		request.setAttribute("numberOfSessions", SessionRegistry.getNumberOfSessions());
		request.setAttribute("sessionList", SessionRegistry.getAllSessions());
		request.getRequestDispatcher("/WEB-INF/jsp/view/sessions.jsp").forward(request, response);
	}
}
