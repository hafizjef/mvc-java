package com.xyz.crms.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import com.xyz.crms.controller.manager.CarManager;
import com.xyz.crms.controller.manager.Facade;
import com.xyz.crms.model.Car;
import com.xyz.crms.model.Customer;
import com.xyz.crms.model.Rental;

public class Tester 
{
	public static void main(String[] args) throws Exception
	{
		/*Car car = new Car();
		car.setCarID(4);
		car.setPlateNo("FKU 1234");
		car.setModel("PERDANA");
		car.setPrice(5);
		car.setStatus('A');
		
		CarManager manager = new CarManager();
		int status = manager.updateCar(car);
		
		if(status !=0)
			System.out.println("Successfully to add a new car with car ID" + car.getCarID());
		else 
			System.out.println("Unable to add a new car");*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		// am/pm SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		System.out.print("Please enter a rental time : ");
		Date start = sdf.parse(reader.readLine());
		
		Facade facade = new Facade();
		ArrayList<Car> cars = facade.searchCars(start);
		ArrayList<Customer> customers = facade.searchCustomers(start);
		
		for(int i = 0; i < cars.size(); i++)
		{
			Car car = cars.get(i);
			
			System.out.println(car.getCarID()+ "\t" +car.getPlateNo()+ "\t" +car.getModel()+ "\t" +car.getPrice()+ "\t" +car.getStatus());
		}
		
		for (Customer customer : customers)
			System.out.println(customer.getCustomerID() + "\t" +customer.getName() + "\t" +customer.getLicenseNo()+ "\t" +customer.getPhoneNo());
		
		System.out.println("Please select a car : ");
		int carID = Integer.parseInt(reader.readLine());
		
		System.out.println("Please select a customer : ");
		int customerID = Integer.parseInt(reader.readLine());
		
		System.out.println("Please select a duration : ");
		int duration = Integer.parseInt(reader.readLine());
		
		Rental rental = new Rental();
		
		rental.setCarID(carID);
		rental.setCustomerID(customerID);
		rental.setStart(start);
		rental.setDuration(duration);
		
		int status = facade.addRental(rental);
		
		if(status != 0)
			System.out.println("Successfully added a new rental");
		else
			System.out.println("Unable to add a new rental");
		
		facade.close();
	}

}
