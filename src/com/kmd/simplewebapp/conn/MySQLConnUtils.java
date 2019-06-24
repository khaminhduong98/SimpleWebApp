package com.kmd.simplewebapp.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnUtils {
	
public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
		
		String hostName = "localhost";
		String dbName = "productmanagement";
		String username = "root";
		String password = "Duongkhaminh25";
			
		return getMySQLConnection(hostName, dbName, username, password);
		
	}
	
	public static Connection getMySQLConnection(String hostName, String dbName, String username, String password) throws SQLException, ClassNotFoundException {
		
		// Need for Java 5
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// Configurate URL Connection for MySQL
		String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
		
		Connection conn = DriverManager.getConnection(connectionURL, username, password);
		
		return conn;
		
	}

}
