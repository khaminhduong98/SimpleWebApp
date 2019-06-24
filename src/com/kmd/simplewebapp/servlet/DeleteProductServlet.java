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

import com.kmd.simplewebapp.utils.DBUtils;
import com.kmd.simplewebapp.utils.MyUtils;

/**
 * Servlet implementation class DeleteProductServlet
 */
@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String code = request.getParameter("code");
		
		boolean hasError = false;
		String errorString = null;
		
		try {
			Connection conn = MyUtils.getStoredConnection(request);
			
			// Find product by CODE
			DBUtils.deleteProduct(conn, code);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			hasError = true;
			errorString = e.getMessage();
		}
		
		// If hasError = true -> Forward to deleteProductErrorView.jsp
		if (hasError) {
			// Store info to Request Atrribute before forwarding
			request.setAttribute("errorString", errorString);
			
			// Forward to deleteProductErrorView.jsp
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/deleteProductErrorView.jsp");
			dispatcher.forward(request, response);
		}
		// If hasError = false -> Redirect to productListView.jsp
		else {
			response.sendRedirect(request.getContextPath() + "/productList");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		
	}

}
