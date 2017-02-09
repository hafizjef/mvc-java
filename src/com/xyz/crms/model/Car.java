package com.xyz.crms.model;

public class Car {
	
	private int carID;
	private String plateNo;
	private String model;
	private double price;
	private char status;

	public void setCarID(int carID) {
		this.carID = carID;
	}
	
	public int getCarID() {
		return carID;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return model + " (" + plateNo + ") - RM " + price;
	}
	
}
