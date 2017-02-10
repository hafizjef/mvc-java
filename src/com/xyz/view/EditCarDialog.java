package com.xyz.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.xyz.crms.controller.manager.Facade;
import com.xyz.crms.model.Car;

public class EditCarDialog extends AbstractDialog {

	private static final long serialVersionUID = 1L;

	private JButton submitButton = new JButton("Submit");
	private JButton resetButton = new JButton("Reset");

	private JTextField plateNoInput = new JTextField();
	private JTextField modelInput = new JTextField();
	private JTextField priceInput = new JTextField();
	private JComboBox<String> statusInput = new JComboBox<>(new String[] {"Available", "Temporarily Unavailable",
			"Permanently Unavailable"});

	private Car car;

	public EditCarDialog(MainMenuFrame frame, Car car) {
		super(frame, new GridLayout(4, 2, 5, 5));

		this.car = car;

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

		center.add(new JLabel("Plate Number:", JLabel.RIGHT));
		center.add(plateNoInput);
		center.add(new JLabel("Model:", JLabel.RIGHT));
		center.add(modelInput);
		center.add(new JLabel("Price/hour (RM):", JLabel.RIGHT));
		center.add(priceInput);
		center.add(new JLabel("Status:", JLabel.RIGHT));
		center.add(statusInput);


		south.add(submitButton);
		south.add(resetButton);

		submitButton.addActionListener(this);
		resetButton.addActionListener(this);

		finalizeUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == submitButton) {

			String plateNo = plateNoInput.getText();
			String model = modelInput.getText();
			double price = Double.parseDouble(priceInput.getText());
			char status = statusInput.getSelectedItem().toString().charAt(0);


			car.setPlateNo(plateNo);
			car.setModel(model);
			car.setPrice(price);
			car.setStatus(status);

			try (Facade facade = new Facade();) {

				int add = facade.updateCar(car);

				if (add != 0) {
					JOptionPane.showMessageDialog(this, "Successfully edit an existing car", getTitle(), JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Unable to edit existing car", getTitle(), JOptionPane.WARNING_MESSAGE);
				}

			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, "Unable to edit existing car: " + ex.getMessage(), getTitle(), JOptionPane.WARNING_MESSAGE);
			}


		} else {
			
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
