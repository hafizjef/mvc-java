package com.xyz.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.xyz.crms.controller.manager.Facade;
import com.xyz.crms.model.Customer;

public class CustomerEntryDialog extends AbstractDialog {

	private static final long serialVersionUID = 1L;

	private JButton submitButton = new JButton("Submit");
	private JButton resetButton = new JButton("Reset");

	private JTextField nameInput = new JTextField();
	private JTextField licenseInput = new JTextField();
	private JTextField phoneNoInput = new JTextField();

	private boolean addOperation;
	
	private Customer customer;
	
	public CustomerEntryDialog(MainMenuFrame frame, Customer customer) {
		super(frame, new GridLayout(4, 2, 5, 5));

		this.customer = customer;
		this.addOperation = customer == null;
		
		DefaultComboBoxModel<String> licenseNo = new DefaultComboBoxModel<>();
		
		licenseNo.addElement("Available");
		licenseNo.addElement("Temporarily Unavailable");
		
		if (!addOperation) {
			licenseNo.addElement("Permanently Unavailable");
		}
		
		

		center.add(new JLabel("Name:", JLabel.RIGHT));
		center.add(nameInput);
		center.add(new JLabel("License Number:", JLabel.RIGHT));
		center.add(licenseInput);
		center.add(new JLabel("Tel Number:", JLabel.RIGHT));
		center.add(phoneNoInput);
		
		south.add(submitButton);
		south.add(resetButton);
	
		this.getRootPane().setDefaultButton(submitButton);
		
		resetInput();
		
		finalizeUI(submitButton, resetButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == submitButton) {
			
			String message = "";
			String name = "", licenseNo = "";
			String phoneNo = "";

			int invalid = 0;
			
			try {
				name = validate("Name", nameInput.getText(), true, 15);
			} catch (Exception ex) {
				message += "\n" + ex.getMessage();
				invalid++;
			}
			
			try {
				licenseNo = validate("License Number", licenseInput.getText(), true, 15);
			} catch (Exception ex) {
				message += "\n" + ex.getMessage();
				invalid++;
			}

			try {
				phoneNo = validate("Phone Number", phoneNoInput.getText(), true, 15);
			} catch (Exception ex) {
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


		} else if (source == resetButton) {
			resetInput();
		}
	}
	
	private void resetInput() {
		
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
