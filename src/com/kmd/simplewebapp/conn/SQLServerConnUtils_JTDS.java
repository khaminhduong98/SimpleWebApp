package com.kmd.simplewebapp.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnUtils_JTDS {

	// Connect to SQL Server 
	public static Connection getSQLServerConnection_JTDS() throws SQLException, ClassNotFoundException {
		
		// Configurate conn parameters
		String hostName = "localhost";
		String sqlInstanceName = "SQLEXPRESS";
		String dbName = "ProductManagement";
		String userName = "sa";
		String password = "sa";
		
		return getSQLServerConnection_JTDS(hostName, sqlInstanceName, dbName, userName, password);
	}
	
	// Connect to SQL Server using JTDS library
	public static Connection getSQLServerConnection_JTDS(String hostName, String sqlInstanceName, String dbName, String userName, String password) throws ClassNotFoundException, SQLException {
		
		// (Only need for < Java 5)
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		
		// Configurate URL Connection for SQL Server
		// Ex: jdbc:jtds:sqlserver://localhost:1433/databaseName;instance=SQLEXPRESS
		String connectionURL = "jdbc:jtds:sqlserver://" + hostName + ":1433/" + dbName + ";instance=" + sqlInstanceName;
		
		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}
	
}
