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
 * Servlet implementation class CreateProductServlet
 */
@WebServlet("/createProduct")
public class CreateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // Forward to createProductView.jsp
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	// When user type enough required info, then hit Submit -> execute doPost()
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
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
		
		// Product CODE must be specific [a-zA-Z_0-9] and has at least 1 letter
		String regex = "\\w+";
		
		if (code == null || !code.matches(regex)) {
			hasError = true;
			errorString = "Product Code invalid";
		} else {
			Connection conn = MyUtils.getStoredConnection(request);
			
			try {
				// Insert Product to DB
				DBUtils.insertProduct(conn, product);
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
				hasError = true;
				errorString = e.getMessage();
			}
		}
		
		// Handle error case: hasError = true -> Forward to createProductView.jsp AGAIN
		if (hasError) {
			// Store info to Request Atrribute before forwarding
			request.setAttribute("errorString", errorString);
			request.setAttribute("product", product);
					
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
