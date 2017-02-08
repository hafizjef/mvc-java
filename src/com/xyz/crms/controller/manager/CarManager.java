package com.xyz.crms.controller.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.xyz.crms.model.Car;

public class CarManager {
	
	// Helper methods

	private void writeCar(Car car, PreparedStatement ps) throws SQLException {

		ps.setString(1, car.getPlateNol());
		ps.setString(2, car.getModel());
		ps.setDouble(3, car.getPrice());
		ps.setString(4, Character.toString(car.getStatus()));

		if (car.getCarID() != 0) {
			ps.setInt(5, car.getCarID());
		}
	}

	private ArrayList<Car> searchCars(PreparedStatement ps) throws SQLException {

		ArrayList<Car> cars = new ArrayList<>();
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			Car car = new Car();

			car.setCarID(rs.getInt("CarID"));
			car.setPlateNol(rs.getString("PlateNo"));
			car.setModel(rs.getString("Model"));
			car.setPrice(rs.getDouble("Price"));
			car.setStatus(rs.getString("Status").charAt(0));

			cars.add(car);
		}
		
		rs.close();
		return cars;
	}
	
	// End helper methods

	public int addCar(Car car) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Car (PlateNo, Model, Price, Status) VALUES (?, ?, ?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS);

		writeCar(car, ps);


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

	public int updateCar(Car car) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = connection.prepareStatement("UPDATE Car SET PlateNo=?, Model=?, Price=?, Status=? WHERE CarID=?",
				PreparedStatement.RETURN_GENERATED_KEYS);

		writeCar(car, ps);

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

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = null;

		// Ternary operator
		ps = connection.prepareStatement("SELECT * FROM Car WHERE UPPER(" + (type == 0 ? "PlateNo" : "Model") + ") LIKE ?",
				PreparedStatement.RETURN_GENERATED_KEYS) ;

		ps.setString(1, "%" + keyword.toUpperCase() + "%");

		ArrayList<Car> cars = searchCars(ps);

		connection.close();

		return cars;
	}

	public ArrayList<Car> searchCars(double price, int type) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = null;


		if (type == 0) {
			ps = connection.prepareStatement("SELECT * FROM Car WHERE Price <= ?", PreparedStatement.RETURN_GENERATED_KEYS);
		} else if (type == 1) {
			ps = connection.prepareStatement("SELECT * FROM Car WHERE Price = ?", PreparedStatement.RETURN_GENERATED_KEYS);
		} else {
			ps = connection.prepareStatement("SELECT * FROM Car WHERE Price >= ?", PreparedStatement.RETURN_GENERATED_KEYS);
		}

		ps.setDouble(1, price);
		ArrayList<Car> cars = searchCars(ps);
		connection.close();

		return cars;
	}

	public ArrayList<Car> searchCars(Date start) throws ClassNotFoundException, SQLException{

		// Load DB Driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/dbCRMS", "user", "123");

		// Create SQL Statement
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Car WHERE CarID NOT IN (SELECT CarID FROM Rental WHERE ? BETWEEN"
				+ " Start AND {fn TIMESTAMPADD(SQL_TSI_MINUTE, Duration * 60 - 1, Start)}) AND Status = 'A'");

		// Convert Date to timestamp
		ps.setTimestamp(1, new Timestamp(start.getTime()));

		ArrayList<Car> cars = searchCars(ps);
		connection.close();

		return cars;
	}
}
