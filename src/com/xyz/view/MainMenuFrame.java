package com.xyz.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenuFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public MainMenuFrame() {
		
		super("XYZ Sdn. Bhd. Car Rental Management System");
		
		// Menu bar
		JMenuBar menubar = new JMenuBar();
		JMenu carMenu = new JMenu("Car");
		JMenu customerMenu = new JMenu("Customer");
		JMenu rentalMenu = new JMenu("Rental");
		
		menubar.add(carMenu);
		menubar.add(customerMenu);
		menubar.add(rentalMenu);
		
		JMenuItem addCarMenuItem = new JMenuItem("Add Car");
		JMenuItem searchCarsMenuItem = new JMenuItem("Search Cars");
		JMenuItem addCustomerMenuItem = new JMenuItem("Add Customer");
		JMenuItem searchCustomersMenuItem = new JMenuItem("Search Customer");
		JMenuItem addRentalMenuItem = new JMenuItem("Add Rental");
		JMenuItem searchRentalsMenuItem = new JMenuItem("Search Rentals");
		JMenuItem viewReportsMenuItem = new JMenuItem("View Reports");
		
		carMenu.add(addCarMenuItem);
		carMenu.add(searchCarsMenuItem);
		customerMenu.add(addCustomerMenuItem);
		customerMenu.add(searchCustomersMenuItem);
		rentalMenu.add(addRentalMenuItem);
		rentalMenu.add(searchRentalsMenuItem);
		rentalMenu.add(viewReportsMenuItem);
		
		this.add(new JLabel("Welcome to " + getTitle(), JLabel.CENTER));
		
		this.setSize(400, 300);
		this.setResizable(false);
		this.setJMenuBar(menubar);
		this.setLocationRelativeTo(null); // set window location
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainMenuFrame();
		
	}

}
