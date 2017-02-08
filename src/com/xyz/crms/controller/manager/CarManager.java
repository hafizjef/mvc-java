package com.xyz.crms.controller.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xyz.crms.model.Car;

public class CarManager {

	public int addCar(Car car) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Car (PlateNo, Model, Price, Status) VALUES (?, ?, ?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS);

		ps.setString(1, car.getPlateNol());
		ps.setString(2, car.getModel());
		ps.setDouble(3, car.getPrice());
		ps.setString(4, Character.toString(car.getStatus()));

		int status = ps.executeUpdate();
		connection.close();

		//		if (status != 0) {
		//			ResultSet rs = ps.getGeneratedKeys();
		//			
		//			if (rs.next()) {
		//				car.setCarID(rs.getInt(1));
		//			}
		//		}

		return status;
	}

	public int updateCar(Car car) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = connection.prepareStatement("UPDATE Car SET PlateNo=?, Model=?, Price=?, Status=? WHERE CarID=?",
				PreparedStatement.RETURN_GENERATED_KEYS);

		ps.setString(1, car.getPlateNol());
		ps.setString(2, car.getModel());
		ps.setDouble(3, car.getPrice());
		ps.setString(4, Character.toString(car.getStatus()));
		ps.setInt(5, car.getCarID());

		int status = ps.executeUpdate();
		connection.close();

		if (status != 0) {
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				car.setCarID(rs.getInt(1));
			}
		}

		return status;
	}


	public int deleteCar(Car car) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = connection.prepareStatement("DELETE FROM Car WHERE CarID=?",
				PreparedStatement.RETURN_GENERATED_KEYS);

		ps.setInt(1, car.getCarID());

		int status = ps.executeUpdate();
		connection.close();

		return status;
	}

	public ArrayList<Car> searchCars(String keyword, int type) throws ClassNotFoundException, SQLException{
		
		// Initialize array 
		ArrayList<Car> cars = new ArrayList<>();
		
		
		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = null;
		
		// Ternary operator
		ps = connection.prepareStatement("SELECT * FROM Car WHERE UPPER(" + (type == 0 ? "PlateNo" : "Model") + ") LIKE ?",
				PreparedStatement.RETURN_GENERATED_KEYS) ;
		
		ps.setString(1, "%" + keyword.toUpperCase() + "%");


		ResultSet rs = ps.executeQuery();
		connection.close();

		return cars;
	}
}
