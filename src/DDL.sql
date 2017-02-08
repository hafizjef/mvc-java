CREATE TABLE Car
(
	CarID INT
	CONSTRAINT Car_PK PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	
	PlateNo VARCHAR(15)
	CONSTRAINT PlateNo_NN NOT NULL
	CONSTRAINT PlateNo_UQ UNIQUE,
	
	Model VARCHAR(100)
	CONSTRAINT Model_NN NOT NULL,
	
	Price DOUBLE
	CONSTRAINT Price_NN NOT NULL,
	
	Status VARCHAR(1)
	CONSTRAINT Status_NN NOT NULL,
	
	CONSTRAINT Price_CK CHECK(Price BETWEEN 1 AND 20),
	CONSTRAINT Status_CK CHECK(Status IN('A', 'T', 'P'))
);

CREATE TABLE Customer
(
	CustomerID INT
	CONSTRAINT Customer_PK PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	
	Name VARCHAR(100)
	CONSTRAINT Name_NN NOT NULL,
	
	LicenseNo VARCHAR(20)
	CONSTRAINT LicenseNo_NN NOT NULL
	CONSTRAINT LicenseNo_UQ UNIQUE,
	
	PhoneNo VARCHAR(15)
	CONSTRAINT PhoneNo_NN NOT NULL
);

CREATE TABLE Rental
(
	RentalID INT
	CONSTRAINT Rental_PK PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	
	CarID INT
	CONSTRAINT Rental_Car_FK REFERENCES Car(CarID)
	CONSTRAINT Rental_Car_NN NOT NULL,
	
	CustomerID INT
	CONSTRAINT Rental_Customer_FK REFERENCES Customer(CustomerID)
	CONSTRAINT Rental_Customer_NN NOT NULL,
	
	Start TIMESTAMP
	CONSTRAINT Start_NN NOT NULL,
	
	Duration INT
	CONSTRAINT Duration_NN NOT NULL,
	
	Amount DOUBLE
	CONSTRAINT Amount_NN NOT NULL,
	
	CONSTRAINT Duration_CK CHECK(Duration > 0),
	CONSTRAINT Amount_CK CHECK(Amount > 0)
);


