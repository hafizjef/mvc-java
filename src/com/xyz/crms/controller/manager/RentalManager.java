package com.xyz.crms.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;

import com.xyz.crms.model.Rental;

class RentalManager extends AbstractTableManager {

	RentalManager(Facade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}
	
	int addRental(Rental rental) throws SQLException {
		
		GregorianCalendar calendar = new GregorianCalendar();
		
		calendar.setTime(rental.getStart());
		calendar.add(GregorianCalendar.MINUTE, rental.getDuration() * 60 - 1);
		
		Date end = calendar.getTime();
		
		PreparedStatement check = facade.prepareStatement("SELECT * FROM Rental WHERE (CarID=? OR CustomerID=?) AND ? "
				+ "BETWEEN Start AND {fn TIMESTAMPADD(SQL_TSI_MINUTE, Duration * 60 - 1)}");
		
		check.setInt(1, rental.getCarID());
		check.setInt(2, rental.getCustomerID());
		check.setTimestamp(3, toTimestamp(end));
		
		int status = 0;
		ResultSet rs = check.executeQuery();
		
		if (!rs.next()) {
			PreparedStatement ps = facade.prepareStatement("INSERT INTO Rental (CarID, CustomerID, Start, Duration, Amount) ");
		}
		
		return status;
	}
}
