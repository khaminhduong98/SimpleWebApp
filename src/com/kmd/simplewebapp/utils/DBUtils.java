package com.kmd.simplewebapp.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kmd.simplewebapp.beans.Product;
import com.kmd.simplewebapp.beans.UserAccount;

public class DBUtils {
	
	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {
		
		String sql = "select * from productmanagement.user_account where USER_NAME = ? and PASSWORD = ?";
		
		PreparedStatement psm = conn.prepareStatement(sql);
		psm.setString(1, userName);
		psm.setString(2, password);
		
		ResultSet rs = psm.executeQuery();
		if (rs.next()) {
			String gender = rs.getString("GENDER");
			
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			
			return user;
		}
		return null;
		
	}
	
	public static UserAccount findUser(Connection conn, String userName) throws SQLException {
		
		String sql = "select * from productmanagement.user_account where USER_NAME = ?";
		
		PreparedStatement psm = conn.prepareStatement(sql);
		psm.setString(1, userName);
		
		ResultSet rs = psm.executeQuery();
		if (rs.next()) {
			String password = rs.getString("PASSWORD");
			String gender = rs.getString("GENDER");
			
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			
			return user;
		}
		
		return null;
		
	}
	
	public static List<Product> queryProduct(Connection conn) throws SQLException {
		
		String sql = "select * from productmanagement.product";
		
		PreparedStatement psm = conn.prepareStatement(sql);
		
		ResultSet rs = psm.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {
			String code = rs.getString("CODE");
			String name = rs.getString("NAME");
			float price = rs.getFloat("PRICE");
			
			Product product = new Product();
			product.setCode(code);
			product.setName(name);
			product.setPrice(price);
			
			list.add(product);
		}
		
		return list;
		
	}
	
	public static Product findProduct(Connection conn, String code) throws SQLException {
		
		String sql = "select * from productmanagement.product where CODE = ?";
		
		PreparedStatement psm = conn.prepareStatement(sql);		
		psm.setString(1, code);
		
		ResultSet rs = psm.executeQuery();
		while (rs.next()) {
			String name = rs.getString("NAME");
			float price = rs.getFloat("PRICE");
			
			Product product = new Product(code, name, price);
			return product;
		}
		return null;
	}
	
	public static void insertProduct(Connection conn, Product product) throws SQLException {
		
		String sql = "insert into productmanagement.product(CODE, NAME, PRICE) values (?, ?, ?)";
		
		PreparedStatement psm = conn.prepareStatement(sql);
		psm.setString(1, product.getCode());
		psm.setString(2, product.getName());
		psm.setFloat(3, product.getPrice());
		
		psm.executeUpdate();
	}
	
	public static void updateProduct(Connection conn, Product product) throws SQLException {
		
		String sql = "update productmanagement.product set NAME = ?, PRICE = ? where CODE = ?";
		
		PreparedStatement psm = conn.prepareStatement(sql);
		psm.setString(1, product.getName());
		psm.setFloat(2, product.getPrice());
		psm.setString(3, product.getCode());
		
		psm.executeUpdate();
	}
	
	public static void deleteProduct(Connection conn, String code) throws SQLException {
		
		String sql = "delete from productmanagement.product where CODE = ?";
		
		PreparedStatement psm = conn.prepareStatement(sql);
		psm.setString(1, code);
		
		psm.executeUpdate();
	}

}
