package com.kmd.simplewebapp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kmd.simplewebapp.beans.UserAccount;
import com.kmd.simplewebapp.utils.MyUtils;

/**
 * Servlet implementation class UserInfoServlet
 */
@WebServlet(urlPatterns = {"/userInfo"})
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		// Check login user
		UserAccount loginedUser = MyUtils.getLoginedUser(session);
		
		// If login not yet -> Redirect to loginView.jsp
		if (loginedUser == null) {
			// Redirect to loginView.jsp
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		// Store info to Request Atrribute before forwarding and for continue using
		request.setAttribute("user", loginedUser);
		
		// If logined -> Forward to userInfo.jsp
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userInfoView.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
