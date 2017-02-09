package com.xyz.crms.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import com.xyz.crms.model.Rental;

public class RentalManager extends AbstractTableManager {

	RentalManager(Facade facade) {
		super(facade);
	}

	int addRental(Rental rental) throws SQLException {

		GregorianCalendar calendar = new GregorianCalendar();

		calendar.setTime(rental.getStart());
		calendar.add(GregorianCalendar.MINUTE, rental.getDuration() * 60 - 1);

		Date end = calendar.getTime();
		PreparedStatement check = facade.prepareStatement("SELECT * FROM Rental WHERE (CarID = ? OR CustomerID = ?) AND ? BETWEEN Start AND {fn TIMESTAMPADD(SQL_TSI_MINUTE, Duration * 60 -1, Start)}");

		check.setInt(1,  rental.getCarID());
		check.setInt(2,  rental.getCustomerID());
		check.setTimestamp(3, toTimestamp(end));

		int status = 0;
		ResultSet rs = check.executeQuery();

		if (!rs.next())
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO Rental (CarID, CustomerID, Start, Duration, Amount) SELECT CarID, ?, ?, ?, ? * Price FROM Car WHERE CarID = ?");

			ps.setInt(1, rental.getCustomerID());
			ps.setTimestamp(2, toTimestamp(rental.getStart()));
			ps.setInt(3,  rental.getDuration());
			ps.setInt(4, rental.getDuration());
			ps.setInt(5, rental.getCarID());

			status = ps.executeUpdate();

			if (status != 0)
			{
				rs = ps.getGeneratedKeys();
				if (rs.next())
					rental.setRentalID(rs.getInt(1));
			}

		}
		return status;
	}

	ArrayList<Rental> searchRentals(int id, int type, Date date) throws SQLException {

		PreparedStatement ps = null;

		ps = facade.prepareStatement("SELECT * FROM Rental WHERE " + (type == 0 ? "CarID" : "CustomerID") + " = ? AND ? BETWEEN Start AND "
				+ "{fn TIMESTAMPADD(SQL_TSI_MINUTE, Duration * 60 -1, Start)}");

		//		if (type == 0)
		//			ps = facade.prepareStatement("SELECT * FROM Rental WHERE CarID = ? AND ? BETWEEN Start AND "
		//					+ "{fn TIMESTAMPADD(SQL_TSI_MINUTE, Duration * 60 -1, Start)}"); 
		//		else
		//			ps = facade.prepareStatement("SELECT * FROM Rental WHERE CustomerID = ? AND ? BETWEEN Start AND "
		//					+ "{fn TIMESTAMPADD(SQL_TSI_MINUTE, Duration * 60 -1, Start)}");

		ps.setInt(1, id);
		ps.setTimestamp(2, toTimestamp(date));

		//ArrayList<Rental> cars = searchRentals(ps);
		ArrayList<Rental> rentals = new ArrayList<>();
		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			Rental rental = new Rental();

			rental.setRentalID(rs.getInt("RentalID"));
			rental.setCarID(rs.getInt("CarID"));
			rental.setCustomerID(rs.getInt("CustomerID"));
			rental.setStart(rs.getDate("Start"));
			rental.setDuration(rs.getInt("Duration"));
			rental.setAmount(rs.getDouble("Amount"));

			rentals.add(rental);
		}
		return rentals;
	}

}
