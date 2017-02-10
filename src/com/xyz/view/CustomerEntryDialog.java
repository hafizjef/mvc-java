package com.xyz.view;

import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.xyz.crms.controller.manager.Facade;
import com.xyz.crms.model.Customer;

public class CustomerEntryDialog extends AbstractEntryDialog {

	private static final long serialVersionUID = 1L;

	private JTextField nameInput = new JTextField();
	private JTextField licenseInput = new JTextField();
	private JTextField phoneNoInput = new JTextField();
	
	private Customer customer;
	
	public CustomerEntryDialog(MainMenuFrame frame, Customer customer) {
		super(frame, customer, 3);

		this.customer = customer;	

		center.add(new JLabel("Name:", JLabel.RIGHT));
		center.add(nameInput);
		center.add(new JLabel("License Number:", JLabel.RIGHT));
		center.add(licenseInput);
		center.add(new JLabel("Tel Number:", JLabel.RIGHT));
		center.add(phoneNoInput);
		
		resetInput();
		finalizeUI(submitButton, resetButton);
	}

	@Override
	public void submitInput() {
			
			String message = "";
			String name = "", licenseNo = "";
			String phoneNo = "";

			int invalid = 0;
			
			try {
				name = validate("Name", nameInput.getText(), true, 15);
			} catch (ValidationException ex) {
				message += "\n" + ex.getMessage();
				invalid++;
			}
			
			try {
				licenseNo = validate("License Number", licenseInput.getText(), true, 15);
			} catch (ValidationException ex) {
				message += "\n" + ex.getMessage();
				invalid++;
			}

			try {
				phoneNo = validate("Phone Number", phoneNoInput.getText(), true, 15);
			} catch (ValidationException ex) {
				message += "\n" + ex.getMessage();
				invalid++;
			}

			if (invalid != 0) {

				if (invalid == 1) {
					JOptionPane.showMessageDialog(this, message.substring(3), getTitle(), JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Please make sure the following fields are correct:" + message, getTitle(), JOptionPane.WARNING_MESSAGE);
				}
			} else {
				
				if (addOperation) {
					customer = new Customer();
				}
				
				customer.setName(name);
				customer.setLicenseNo(licenseNo);
				customer.setPhoneNo(phoneNo);

				try (Facade facade = new Facade();) {

					if (addOperation) {
						
						int add = facade.addCustomer(customer);
						if (add != 0) {
							JOptionPane.showMessageDialog(this, "Successfully added a new customer", getTitle(), JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(this, "Unable to add a new customer", getTitle(), JOptionPane.WARNING_MESSAGE);
						}
						
						resetInput();
					} else {
						
						int update = facade.updateCustomer(customer);
						if (update != 0) {
							JOptionPane.showMessageDialog(this, "Successfully edit an existing customer", getTitle(), JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(this, "Unable to edit an existing customer", getTitle(), JOptionPane.WARNING_MESSAGE);
						}
						
					}

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(this, "Unable to save information: " + ex.getMessage(), getTitle(), JOptionPane.WARNING_MESSAGE);
				}
			}
	}
	
	protected void resetInput() {
		
		nameInput.grabFocus();
		
		if (addOperation) {
			// On add customer, Clear all input boxes
			nameInput.setText("");
			licenseInput.setText("");
			phoneNoInput.setText("");
			
		} else {
			// On update customer, set to default values
			nameInput.setText(customer.getName());
			licenseInput.setText(customer.getLicenseNo());
			phoneNoInput.setText(customer.getPhoneNo());
		}
	}
}
