package com.xyz.crms.controller.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.xyz.crms.model.Customer;

public class CustomerManager {
	
	// Helper methods

	private void writeCustomer(Customer customer, PreparedStatement ps) throws SQLException {

		ps.setString(1, customer.getName());
		ps.setString(2, customer.getLicenseNo());
		ps.setString(3, customer.getPhoneNo());

		if (customer.getCustomerID() != 0) {
			ps.setInt(4, customer.getCustomerID());
		}
	}

	private ArrayList<Customer> searchCustomers(PreparedStatement ps) throws SQLException {

		ArrayList<Customer> customers = new ArrayList<>();
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			Customer customer = new Customer();

			customer.setCustomerID(rs.getInt("CustomerID"));
			customer.setName(rs.getString("Name"));
			customer.setLicenseNo(rs.getString("LicenseNo"));
			customer.setPhoneNo(rs.getString("PhoneNo"));

			customers.add(customer);
		}
		
		rs.close();
		return customers;
	}
	
	// End helper methods

	public int addCustomer(Customer customer) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Customer (Name, LicenseNo, PhoneNo) VALUES (?, ?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS);

		writeCustomer(customer, ps);


		int status = ps.executeUpdate();
		connection.close();

		if (status != 0) {
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				customer.setCustomerID(rs.getInt(1));
			}
		}

		return status;
	}

	public int updateCustomer(Customer customer) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = connection.prepareStatement("UPDATE Customer SET Name=?, LicenseNo=?, PhoneNo=? WHERE CustomerID=?",
				PreparedStatement.RETURN_GENERATED_KEYS);

		writeCustomer(customer, ps);

		int status = ps.executeUpdate();
		connection.close();

		if (status != 0) {
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				customer.setCustomerID(rs.getInt(1));
			}
		}

		return status;
	}


	public int deleteCustomer(Customer customer) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = connection.prepareStatement("DELETE FROM Customer WHERE CustomerID=?",
				PreparedStatement.RETURN_GENERATED_KEYS);

		ps.setInt(1, customer.getCustomerID());

		int status = ps.executeUpdate();
		connection.close();

		return status;
	}


	public ArrayList<Customer> searchCustomers(String keyword) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = null;

		// Ternary operator
		ps = connection.prepareStatement("SELECT * FROM Customer WHERE UPPER(Name) LIKE ?",
				PreparedStatement.RETURN_GENERATED_KEYS) ;

		ps.setString(1, "%" + keyword.toUpperCase() + "%");

		ArrayList<Customer> customers = searchCustomers(ps);

		connection.close();

		return customers;
	}


	public ArrayList<Customer> searchCustomers(Date start) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Customer WHERE CustomerID NOT IN (SELECT CustomerID FROM Rental WHERE ? BETWEEN"
				+ " Start AND {fn TIMESTAMPADD(SQL_TSI_MINUTE, Duration * 60 - 1, Start)}) AND Status = 'A'");

		// Convert Date to timestamp
		ps.setTimestamp(1, new Timestamp(start.getTime()));

		ArrayList<Customer> customers = searchCustomers(ps);
		connection.close();

		return customers;
	}
}
