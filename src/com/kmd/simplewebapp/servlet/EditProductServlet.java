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

import com.kmd.simplewebapp.beans.Product;
import com.kmd.simplewebapp.utils.DBUtils;
import com.kmd.simplewebapp.utils.MyUtils;

/**
 * Servlet implementation class EditProductServlet
 */
@WebServlet("/editProduct")
public class EditProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String code = request.getParameter("code");
		
		Product product = null;
		boolean hasError = false;
		String errorString = null;
		
		try {
			Connection conn = MyUtils.getStoredConnection(request);
			
			// Find product by CODE
			product = DBUtils.findProduct(conn, code);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			hasError = true;
			errorString = e.getMessage();
		}
		
		// If Product doesn't exist AND hasError = true -> Redirect to productListView.jsp
		if (hasError && product == null) {
			response.sendRedirect(request.getContextPath() + "/productList");
			return;
		}
		// Otherwise -> Forward to editProductView.jsp
		// Store info to Request Atrribute before forwarding
		request.setAttribute("errorString", errorString);
		request.setAttribute("product", product);
		
		// Forward to editProductView.jsp
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	// When user type enough required info, then hit Submit -> execute doPost()
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String priceStr = request.getParameter("price");
		float price = 0;
		try {
			price = Float.parseFloat(priceStr);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Product product = new Product(code, name, price);
		boolean hasError = false;
		String errorString = null;
		
		try {
			Connection conn = MyUtils.getStoredConnection(request);
			
			// Update Product to DB
			DBUtils.updateProduct(conn, product);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			hasError = true;
			errorString = e.getMessage();
		}
		
		// Store info to Request Atrribute before forwarding
		request.setAttribute("errorString", errorString);
		request.setAttribute("product", product);
		
		// Handle error case: hasError = true -> Forward to editProductView.jsp AGAIN
		if (hasError) {	
			// Forward to createProductView.jsp AGAIN if it occurs error
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
			dispatcher.forward(request, response);
		}
		// Handle no error case: hasError = false -> Redirect to productListView.jsp
		else {
			// Redirect to productListView.jsp
			response.sendRedirect(request.getContextPath() + "/productList");
		}
	}

}
