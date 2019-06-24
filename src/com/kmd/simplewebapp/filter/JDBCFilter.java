package com.kmd.simplewebapp.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.kmd.simplewebapp.conn.ConnectionUtils;
import com.kmd.simplewebapp.utils.MyUtils;

@WebFilter(filterName = "jdbcFilter", urlPatterns = {"/*"})
public class JDBCFilter implements Filter {
	
	public JDBCFilter() {
	}
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	// Check if current Request target to Servlet or something else
	private boolean needJDBC(HttpServletRequest request) {
		
		System.out.println("JDBC Filter");
		//
		// Servlet Url-pattern: /spath/*
		//
		// => Servlet Path /spath
		String servletPath = request.getServletPath();
		// => Path Info /abc/mnp
		String pathInfo = request.getPathInfo();
		
		String urlPattern = servletPath;
		
		if (pathInfo != null) {
			// => /spath/*
			//urlPattern += "/*";
			urlPattern = servletPath + "/*";
		}
		
		// Use Map -> get Servlet Registrations
		// Key: ServletName
		// Name: ServletRegistration
		Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext().getServletRegistrations();
		
		// Set of all Servlets in WebApp
		Collection<? extends ServletRegistration> values = servletRegistrations.values();
		for (ServletRegistration sr : values) {
			Collection<String> mappings = sr.getMappings();
			if (mappings.contains(urlPattern)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		// Only open connection for requests having special path (to servlets, jsp, etc.)
		//
		// Avoid opening connection for normal requests (image, css, js, etc.)
		//
		if (this.needJDBC(req)) {
			
			System.out.println("Open Connection for: " + req.getServletPath());
			
			Connection conn = null;
			try {
				// Create connection instance to connect to database
				conn = ConnectionUtils.getConnection();
				// Set autoCommit = false -> to control on my own
				conn.setAutoCommit(false);
				
				// Store connection instance to Request Attribute
				MyUtils.storeConnection(request, conn);
				
				// Allow Request go on (to next Filter or Target)
				chain.doFilter(request, response);
				
				// Call commit() method to complete transaction with database
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				ConnectionUtils.rollbackQuietly(conn);
			} finally {
				ConnectionUtils.closeQuietly(conn);
			}
		}
		// Handle normal requests
		else {
			// Allow Request go on (to next Filter or Target)
			chain.doFilter(request, response);
		}

	}

}
