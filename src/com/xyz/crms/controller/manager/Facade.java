package com.xyz.crms.controller.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.xyz.crms.model.Car;
import com.xyz.crms.model.Customer;

// Singleton
public class Facade implements AutoCloseable {
	private Connection connection;
	private CarManager carManager;
	private CustomerManager customerManager;
	
	static {
		
		// Load DB Driver
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException ex) {
			System.err.println("Unable to load database driver. The system will exit now.");
			System.exit(0);
		}
		
	}
	
	private Connection getConnection() throws SQLException {
		
		// Re-create db connection on connection failure / loss
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");
		}
		return connection;
	}
	
	private CarManager getCarManager() {
		return carManager == null ? carManager = new CarManager(this) : carManager;
	}
	
	private CustomerManager getCustomerManager() {
		return customerManager == null ? customerManager = new CustomerManager(this) : customerManager;
	}
	
	@Override
	public void close() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
	
	PreparedStatement prepareStatement(String sql) throws SQLException {
		return getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	}
	
	
	// Car Manager Methods
	public int addCar(Car car) throws SQLException {                                                      
		return getCarManager().addCar(car);                                                               
	}                                                                                                     
	                                                                                                      
	public int updateCar(Car car) throws SQLException{                                                    
		return getCarManager().updateCar(car);
	}
	
	public ArrayList<Car> searchCars(String keyword, int type) throws SQLException{
		return getCarManager().searchCars(keyword, type);
	}
	
	public ArrayList<Car> searchCars(double price, int type) throws SQLException{
		return getCarManager().searchCars(price, type);
	}
	
	public ArrayList<Car> searchCars(Date start) throws SQLException{
		return getCarManager().searchCars(start);
	}
	
	
	// Customer Manager Methods
	public int addCustomer(Customer customer) throws SQLException{
		return getCustomerManager().addCustomer(customer);
	}
	
	public int updateCustomer(Customer customer) throws SQLException{
		return getCustomerManager().updateCustomer(customer);
	}
	
	public ArrayList<Customer> searchCustomers(String keyword) throws SQLException{
		return getCustomerManager().searchCustomers(keyword);
	}
	
	public ArrayList<Customer> searchCustomers(Date start) throws SQLException{
		return getCustomerManager().searchCustomers(start);
	}
}
