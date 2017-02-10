package com.xyz.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.xyz.crms.controller.manager.Facade;
import com.xyz.crms.model.Car;

public class AddCarDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton submitButton = new JButton("Submit");
	private JButton resetButton = new JButton("Reset");

	private JTextField plateNoInput = new JTextField();
	private JTextField modelInput = new JTextField();
	private JTextField priceInput = new JTextField();
	private JComboBox<String> statusInput = new JComboBox<>(new String[] {"Available", "Temporarily Unavailable"});

	public AddCarDialog(MainMenuFrame frame) {
		super(frame, frame.getTitle(), true);

		JPanel center = new JPanel(new GridLayout(4, 2, 5, 5));
		JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		center.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
		south.setBorder(BorderFactory.createEmptyBorder(12, 10, 10, 10));

		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);

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

		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(frame);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == submitButton) {
			// TODO Implement submit
			String message = "";
			String plateNo = plateNoInput.getText();
			String model = modelInput.getText();
			double price = 0;
			char status = statusInput.getSelectedItem().toString().charAt(0);

			int invalid = 0;

			if (plateNo.isEmpty()) {
				invalid++;
				message += "\n- Plate number is required";

			} else {
				if (plateNo.length() > 15) {
					invalid++;
					message += "\n- Plate number must be less than 16 chars";
				}
			}

			if (model.isEmpty()) {
				invalid++;
				message += "\n- Model is required";
			} else {
				if (model.length() > 100) {
					invalid++;
					message += "\n- Model must be less than 101 chars";
				}
			}

			try {
				price = Double.parseDouble(priceInput.getText());
				if (price <= 0) {
					invalid++;
					message += "\n- Price must be greater than 0";
				}
			} catch (Exception ex) {
				invalid++;
				message += "\n- Price must be a number";
			}

			if (invalid != 0) {

				if (invalid == 1) {
					JOptionPane.showMessageDialog(this, message.substring(3), getTitle(), JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Please make sure the following fields are correct:" + message, getTitle(), JOptionPane.WARNING_MESSAGE);
				}
			} else {
				Car car = new Car();

				car.setPlateNo(plateNo);
				car.setModel(model);
				car.setPrice(price);
				car.setStatus(status);

				try (Facade facade = new Facade();) {

					int add = facade.addCar(car);

					if (add != 0) {
						JOptionPane.showMessageDialog(this, "Successfully added a new car", getTitle(), JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "Unable to add a new car", getTitle(), JOptionPane.WARNING_MESSAGE);
					}

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(this, "Unable to add a new car: " + ex.getMessage(), getTitle(), JOptionPane.WARNING_MESSAGE);
				}
			}


		} else {
			plateNoInput.setText("");
			modelInput.setText("");
			priceInput.setText("");
			statusInput.setSelectedIndex(0);
		}
	}
}
