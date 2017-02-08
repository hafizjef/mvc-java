package com.xyz.crms.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.xyz.crms.model.Customer;

class CustomerManager {
	
	private Facade facade;
	
	CustomerManager(Facade facade) {
		this.facade = facade;
	}
	
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

	int addCustomer(Customer customer) throws SQLException{

		PreparedStatement ps = facade.prepareStatement("INSERT INTO Customer (Name, LicenseNo, PhoneNo) VALUES (?, ?, ?)");

		writeCustomer(customer, ps);
		int status = ps.executeUpdate();

		if (status != 0) {
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				customer.setCustomerID(rs.getInt(1));
			}
		}

		return status;
	}

	int updateCustomer(Customer customer) throws SQLException{
		
		PreparedStatement ps = facade.prepareStatement("UPDATE Customer SET Name=?, LicenseNo=?, PhoneNo=? WHERE CustomerID=?");

		writeCustomer(customer, ps);
		int status = ps.executeUpdate();

		if (status != 0) {
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				customer.setCustomerID(rs.getInt(1));
			}
		}

		return status;
	}


	int deleteCustomer(Customer customer) throws SQLException{
		
		PreparedStatement ps = facade.prepareStatement("DELETE FROM Customer WHERE CustomerID=?");

		ps.setInt(1, customer.getCustomerID());
		int status = ps.executeUpdate();
		return status;
	}


	ArrayList<Customer> searchCustomers(String keyword) throws SQLException{

		PreparedStatement ps = null;

		// Ternary operator
		ps = facade.prepareStatement("SELECT * FROM Customer WHERE UPPER(Name) LIKE ?") ;
		ps.setString(1, "%" + keyword.toUpperCase() + "%");
		ArrayList<Customer> customers = searchCustomers(ps);

		return customers;
	}


	ArrayList<Customer> searchCustomers(Date start) throws SQLException{

		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Customer WHERE CustomerID NOT IN (SELECT CustomerID FROM Rental WHERE ? BETWEEN"
				+ " Start AND {fn TIMESTAMPADD(SQL_TSI_MINUTE, Duration * 60 - 1, Start)}) AND Status = 'A'");

		// Convert Date to timestamp
		ps.setTimestamp(1, new Timestamp(start.getTime()));
		ArrayList<Customer> customers = searchCustomers(ps);
		return customers;
	}
}
