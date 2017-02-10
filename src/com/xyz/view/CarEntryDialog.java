package com.xyz.view;

import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.xyz.crms.controller.manager.Facade;
import com.xyz.crms.model.Car;

public class CarEntryDialog extends AbstractEntryDialog {

	private static final long serialVersionUID = 1L;

	private JTextField plateNoInput = new JTextField();
	private JTextField modelInput = new JTextField();
	private JTextField priceInput = new JTextField();
	private JComboBox<String> statusInput = new JComboBox<>();
	
	private Car car;
	
	public CarEntryDialog(MainMenuFrame frame, Car car) {
		super(frame, car, 4);

		this.car = car;
		this.addOperation = car == null;
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		
		model.addElement("Available");
		model.addElement("Temporarily Unavailable");
		
		if (!addOperation) {
			model.addElement("Permanently Unavailable");
		}
		
		statusInput.setModel(model);
		

		center.add(new JLabel("Plate Number:", JLabel.RIGHT));
		center.add(plateNoInput);
		center.add(new JLabel("Model:", JLabel.RIGHT));
		center.add(modelInput);
		center.add(new JLabel("Price/hour (RM):", JLabel.RIGHT));
		center.add(priceInput);
		center.add(new JLabel("Status:", JLabel.RIGHT));
		center.add(statusInput);
		
		resetInput();
		finalizeUI(submitButton, resetButton);
	}

	@Override
	protected void submitInput() {
			
			char status = statusInput.getSelectedItem().toString().charAt(0);
			
			String message = "";
			String plateNo = null, model = null;
			double price = -1;

			int invalid = 0;
			
			try {
				plateNo = validate("Plate number", plateNoInput.getText(), true, 15);
			} catch (ValidationException ex) {
				message += "\n" + ex.getMessage();
				invalid++;
			}
			
			try {
				model = validate("Model", modelInput.getText(), true, 15);
			} catch (ValidationException ex) {
				message += "\n" + ex.getMessage();
				invalid++;
			}

			try {
				price = validate("Price", priceInput.getText(), true, true, true, 1, 20);
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
					car = new Car();
				}
				
				car.setPlateNo(plateNo);
				car.setModel(model);
				car.setPrice(price);
				car.setStatus(status);

				try (Facade facade = new Facade();) {

					if (addOperation) {
						
						int add = facade.addCar(car);
						if (add != 0) {
							JOptionPane.showMessageDialog(this, "Successfully added a new car", getTitle(), JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(this, "Unable to add a new car", getTitle(), JOptionPane.WARNING_MESSAGE);
						}
						
						resetInput();
					} else {
						
						int update = facade.updateCar(car);
						if (update != 0) {
							JOptionPane.showMessageDialog(this, "Successfully edit an existing car", getTitle(), JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(this, "Unable to edit an existing car", getTitle(), JOptionPane.WARNING_MESSAGE);
						}
						
					}

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(this, "Unable to save information: " + ex.getMessage(), getTitle(), JOptionPane.WARNING_MESSAGE);
				}
			}
	}
	
	@Override
	protected void resetInput() {
		
		plateNoInput.grabFocus();
		
		if (addOperation) {
			// On add car, Clear all input boxes
			plateNoInput.setText("");
			modelInput.setText("");
			priceInput.setText("");
			statusInput.setSelectedIndex(0);
			
		} else {
			// On update car, set to default values
			plateNoInput.setText(car.getPlateNo());
			modelInput.setText(car.getModel());
			priceInput.setText(Double.toString(car.getPrice()));

			switch (car.getStatus()) {

			case 'A':
				statusInput.setSelectedIndex(0);
				break;
			case 'T':
				statusInput.setSelectedIndex(1);
				break;
			case 'P':
				statusInput.setSelectedIndex(2);
				break;
			}
		}
	}
}
