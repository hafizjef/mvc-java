package com.xyz.crms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.xyz.crms.controller.manager.Facade;
import com.xyz.crms.model.Car;

public class Tester {

	public static void main(String[] args) throws Exception {

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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		
		System.out.println("Please Enter a rental time : ");
		Date start = sdf.parse(reader.readLine());
		
		

		Facade facade = new Facade();

		ArrayList<Car> cars = facade.searchCars("sky", 1);

		for (Car c : cars) {

			System.out.println(c.getCarID() + "\t" + c.getModel());
		}



		facade.close();
	}

}
