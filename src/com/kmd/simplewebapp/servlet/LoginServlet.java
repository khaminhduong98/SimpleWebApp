package com.kmd.simplewebapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kmd.simplewebapp.beans.UserAccount;
import com.kmd.simplewebapp.utils.DBUtils;
import com.kmd.simplewebapp.utils.MyUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    // Forward to loginView.jsp
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
		dispatcher.forward(request, response);
	}

	// When user enters userName & password, then tap Submit -> execute doPost()
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String rememberMeStr = request.getParameter("rememberMe");
		boolean remember = "Y".equals(rememberMeStr);
		
		UserAccount user = null;
		boolean hasError = false;
		String errorString = null;
		
		if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
			hasError = true;
			errorString = "Required User Name and Password!";
		} else {
			Connection conn = MyUtils.getStoredConnection(request);
			
			try {
				// Find user in DB
				user = DBUtils.findUser(conn, userName, password);
				
				if (user == null) {
					hasError = true;
					errorString = "User Name or Password invalid";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				hasError = true;
				errorString = e.getMessage();
			}
		}
		
		// Handle error case: hasError = true -> Forward to loginView.jsp
		if (hasError) {
			user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			
			// Store info to Request Atrribute before forwarding
			request.setAttribute("errorString", errorString);
			request.setAttribute("user", user);
			
			// Forward to loginView.jsp
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB_INF/views/loginView.jsp");
			dispatcher.forward(request, response);
		}
		// Handle no error case: hasError = false -> Store User to Session -> Redirect to userInfo.jsp
		else {
			HttpSession session = request.getSession();
			MyUtils.storeLoginedUser(session, user);
			
			// If user choose Remember Me feature
			if (remember) {
				MyUtils.storeUserCookie(response, user);
			}
			// Otherwise -> delete Cookie
			else {
				MyUtils.deleteUserCookie(response);
			}
			
			// Redirect to userInfo.jsp
			response.sendRedirect(request.getContextPath() + "/userInfo");
		}
	}

}
