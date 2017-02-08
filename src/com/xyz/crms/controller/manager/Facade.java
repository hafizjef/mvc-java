package com.xyz.crms.controller.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton
public class Facade {
	
	private Connection connection;
	
	public Facade() throws ClassNotFoundException, SQLException {
		
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");
	}
	
	public Connection getConnection() {
		return connection;
	}
}
