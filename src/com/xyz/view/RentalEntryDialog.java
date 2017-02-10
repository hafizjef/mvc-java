package com.xyz.view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.xyz.crms.model.Car;
import com.xyz.crms.model.Customer;

public class RentalEntryDialog extends AbstractEntryDialog {

	private static final long serialVersionUID = 1L;

	private JTextField dateInput = new JTextField();
	private JComboBox<Car> carsInput = new JComboBox<>();
	private JComboBox<Customer> customerInput = new JComboBox<>();
	private JTextField durationInput = new JTextField();
	
	private JButton searchButton = new JButton("Search");
	
	public RentalEntryDialog(MainMenuFrame frame) {
		
		super(frame, null, 5);
		
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.LINE_AXIS);
		
		panel.setLayout(layout);
		
		panel.add(dateInput);
		panel.add(searchButton);
		
		center.add(new JLabel("Rental (dd/MM/yyyy HH:mm):", JLabel.RIGHT));
		center.add(panel);
		center.add(new JLabel("Car:", JLabel.RIGHT));
		center.add(carsInput);
		center.add(new JLabel("Customer:", JLabel.RIGHT));
		center.add(customerInput);
		center.add(new JLabel("Duration (hours):", JLabel.RIGHT));
		center.add(durationInput);
		
		resetInput();
		finalizeUI(searchButton, submitButton, resetButton);
	}
	
	

	@Override
	protected void submitInput() {
		
	}

	@Override
	protected void resetInput() {
		dateInput.setText("");
		durationInput.setText("");
		
	}

}
