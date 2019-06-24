package com.kmd.simplewebapp.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnUtils_SQLJDBC {

	// Connect to SQL Server (Use SQLJDBC library)
	public static Connection getSQLServerConnection_SQLJDBC() throws SQLException, ClassNotFoundException {
		
		// Configurate conn parameters
		String hostName = "localhost";
		String sqlInstanceName = "SQLEXPRESS";
		String dbName = "ProductManagement";
		String userName = "sa";
		String password = "sa";
		
		return getSQLServerConnection_SQLJDBC(hostName, sqlInstanceName, dbName, userName, password);
		
	}
	
	// Connect to SQL Server using JTDS library
	public static Connection getSQLServerConnection_SQLJDBC(String hostName, String sqlInstanceName, String dbName, String userName, String password) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		// Configurate URL Connection for SQL Server
		// Ex: jdbc:sqlserver//localhost:1433;instance=SQLEXPRESS;databaseName=ProductManagement
		String connectionURL = "jdbc:sqlserver://" + hostName + ":1433" + ";instance=" + sqlInstanceName + ";databaseName=" + dbName;
		
		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		
		return conn;
		
	}
	
}
