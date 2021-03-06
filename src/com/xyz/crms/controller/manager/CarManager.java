package com.xyz.crms.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.xyz.crms.model.Car;

class CarManager extends AbstractTableManager {
	CarManager(Facade facade) {
		super(facade);
	}
	
	
	// Helper methods

	private void writeCar(Car car, PreparedStatement ps) throws SQLException {

		ps.setString(1, car.getPlateNo());
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
			car.setPlateNo(rs.getString("PlateNo"));
			car.setModel(rs.getString("Model"));
			car.setPrice(rs.getDouble("Price"));
			car.setStatus(rs.getString("Status").charAt(0));

			cars.add(car);
		}
		
		rs.close();
		return cars;
	}
	
	// End helper methods

	int addCar(Car car) throws SQLException{

		// Create SQL Statement
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Car (PlateNo, Model, Price, Status) VALUES (?, ?, ?, ?)");

		writeCar(car, ps);


		int status = ps.executeUpdate();

		if (status != 0) {
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				car.setCarID(rs.getInt(1));
			}
		}

		return status;
	}

	int updateCar(Car car) throws SQLException{

		// Create SQL Statement
		PreparedStatement ps = facade.prepareStatement("UPDATE Car SET PlateNo=?, Model=?, Price=?, Status=? WHERE CarID=?");

		writeCar(car, ps);

		int status = ps.executeUpdate();

		if (status != 0) {
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				car.setCarID(rs.getInt(1));
			}
		}

		return status;
	}


	int deleteCar(Car car) throws SQLException{

		// Create SQL Statement
		PreparedStatement ps = facade.prepareStatement("DELETE FROM Car WHERE CarID=?");

		ps.setInt(1, car.getCarID());

		int status = ps.executeUpdate();

		return status;
	}


	ArrayList<Car> searchCars(String keyword, int type) throws SQLException{

		// Create SQL Statement
		PreparedStatement ps = null;

		// Ternary operator
		ps = facade.prepareStatement("SELECT * FROM Car WHERE UPPER(" + (type == 0 ? "PlateNo" : "Model") + ") LIKE ? ORDER BY Model") ;
		ps.setString(1, "%" + keyword.toUpperCase() + "%");

		ArrayList<Car> cars = searchCars(ps);
		return cars;
	}

	ArrayList<Car> searchCars(double price, int type) throws SQLException{

		// Create SQL Statement
		PreparedStatement ps = null;


		if (type == 0) {
			ps = facade.prepareStatement("SELECT * FROM Car WHERE Price <= ? ORDER BY Model");
		} else if (type == 1) {
			ps = facade.prepareStatement("SELECT * FROM Car WHERE Price = ? ORDER BY Model");
		} else {
			ps = facade.prepareStatement("SELECT * FROM Car WHERE Price >= ? ORDER BY Model");
		}

		ps.setDouble(1, price);
		ArrayList<Car> cars = searchCars(ps);

		return cars;
	}

	ArrayList<Car> searchCars(Date start) throws SQLException{

		// Create SQL Statement
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Car WHERE CarID NOT IN (SELECT CarID FROM Rental WHERE ? BETWEEN"
				+ " Start AND {fn TIMESTAMPADD(SQL_TSI_MINUTE, Duration * 60 - 1, Start)}) AND Status = 'A' ORDER BY Model");

		// Convert Date to timestamp
		ps.setTimestamp(1, toTimestamp(start));

		ArrayList<Car> cars = searchCars(ps);

		return cars;
	}
}
