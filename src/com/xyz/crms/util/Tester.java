package com.xyz.crms.util;

import java.sql.SQLException;
import java.util.ArrayList;

import com.xyz.crms.controller.manager.CarManager;
import com.xyz.crms.model.Car;

public class Tester {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		//		Car car = new Car();
		//		
		//		car.setCarID(4);
		//		car.setPlateNol("TV 777");
		//		car.setModel("Nissan Skyline");
		//		car.setPrice(5.0);
		//		car.setStatus('A');
		//		
		//		CarManager manager = new CarManager();
		//		int status = manager.updateCar(car);
		//		
		//		if (status != 0) {
		//			System.out.println("Successfully add a new car with car ID : " + car.getCarID());
		//		} else {
		//			System.out.println("Unable to add a new car!");
		//		}

		CarManager manager = new CarManager();
		ArrayList<Car> cars = manager.searchCars("sky", 1);
		
		for ( Car car : cars ) {
			
			System.out.println(car.getCarID() + "\t" + car.getModel() + "\t" + car.getPlateNol());
		}
	}

}
