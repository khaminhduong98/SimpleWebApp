package com.kmd.simplewebapp.utils;

import java.sql.Connection;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kmd.simplewebapp.beans.UserAccount;

public class MyUtils {

	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
	
	private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
	
	// Store Connection instance to Request Attribute (Storing's info only lasts from Request Time to when data responsed to client's browser)
	public static void storeConnection(ServletRequest request, Connection conn) {
		
		request.setAttribute(ATT_NAME_CONNECTION, conn);
	}
	
	// Get Connection instance stored in Request Attribute
	public static Connection getStoredConnection(ServletRequest request) {
		
		Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
		return conn;
	}
	
	// Store Logined User Info to Session
	public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
		
		// Can access by ${loginedUser} on JSP
		session.setAttribute("loginedUser", loginedUser);
	}
	
	// Get Logined User Info stored in Session
	public static UserAccount getLoginedUser(HttpSession session) {
		
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
		return loginedUser;
	}
	
	// Store UserName to Cookie
	public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
		
		System.out.println("Store user cookie");
		
		// Init 1 cookie with UserName value and Set time for this cookie
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());
		cookieUserName.setMaxAge(24 * 60 * 60); // 1 day (changed to second)
		
		response.addCookie(cookieUserName);
	}
	
	// Get UserName in Cookie
	public static String getUserNameInCookie(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (ATT_NAME_USER_NAME.equals(cookie.getName()))
					return cookie.getValue();
			}
		}
		return null;
	}
	
	// Delete User Cookie
	public static void deleteUserCookie(HttpServletResponse response) {
		
		// Init 1 cookie with NULL value and Set time = 0
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
		cookieUserName.setMaxAge(0);
		
		response.addCookie(cookieUserName);
	}
	
}
