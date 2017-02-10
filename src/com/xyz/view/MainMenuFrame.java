package com.xyz.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class MainMenuFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JMenuItem addCarMenuItem = new JMenuItem("Add Car");
	private JMenuItem searchCarsMenuItem = new JMenuItem("Search Cars");
	private JMenuItem addCustomerMenuItem = new JMenuItem("Add Customer");
	private JMenuItem searchCustomersMenuItem = new JMenuItem("Search Customer");
	private JMenuItem addRentalMenuItem = new JMenuItem("Add Rental");
	private JMenuItem searchRentalsMenuItem = new JMenuItem("Search Rentals");
	private JMenuItem viewReportsMenuItem = new JMenuItem("View Reports");

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
		
		carMenu.add(addCarMenuItem);
		carMenu.add(searchCarsMenuItem);
		customerMenu.add(addCustomerMenuItem);
		customerMenu.add(searchCustomersMenuItem);
		rentalMenu.add(addRentalMenuItem);
		rentalMenu.add(searchRentalsMenuItem);
		rentalMenu.add(viewReportsMenuItem);
		
		
		addCarMenuItem.addActionListener(this);
		searchCarsMenuItem.addActionListener(this);
		addCustomerMenuItem.addActionListener(this);
		searchCustomersMenuItem.addActionListener(this);
		
		this.add(new JLabel("Welcome to " + getTitle(), JLabel.CENTER));
		
		this.setSize(400, 300);
		this.setResizable(false);
		this.setJMenuBar(menubar);
		this.setLocationRelativeTo(null); // set window location
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) throws Exception {
		//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		new MainMenuFrame();
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		Object source = event.getSource();
		
		if (source == addCarMenuItem) {
			new CarEntryDialog(this, null);
		} else if (source == searchCarsMenuItem) {
			new SearchCarsDialog(this);
		} else if (source == addCustomerMenuItem) {
			new CustomerEntryDialog(this, null);
		} else if (source == searchCustomersMenuItem) {
			new SearchCustomerDialog(this);
		}
	}

}
