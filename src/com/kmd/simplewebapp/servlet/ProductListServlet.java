package com.kmd.simplewebapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kmd.simplewebapp.beans.Product;
import com.kmd.simplewebapp.utils.DBUtils;
import com.kmd.simplewebapp.utils.MyUtils;

/**
 * Servlet implementation class ProductListServlet
 */
@WebServlet(urlPatterns = {"/productList"})
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = MyUtils.getStoredConnection(request);
		
		String errorString = null;
		List<Product> list = null;
		
		try {
			list = DBUtils.queryProduct(conn);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			errorString = e.getMessage();
		}
		// Store info to Request Atrribute before forwarding to productListView.jsp
		request.setAttribute("errorString", errorString);
		request.setAttribute("productList", list);
		
		// Forward to productListView.jsp
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/productListView.jsp");
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
