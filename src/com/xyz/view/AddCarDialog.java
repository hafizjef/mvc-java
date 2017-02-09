package com.xyz.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddCarDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public AddCarDialog(MainMenuFrame frame) {
		super(frame, frame.getTitle(), true);
		
		JPanel center = new JPanel(new GridLayout(4, 2));
		JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		
		JTextField plateNoInput = new JTextField();
		JTextField modelInput = new JTextField();
		JTextField priceInput = new JTextField();
		JComboBox<String> statusInput = new JComboBox<>(new String[] {"Available", "Temporarily Unavailable"});
		
		this.add(new JLabel("Plate Number:"));
		this.add(plateNoInput);
		this.add(new JLabel("Model:"));
		this.add(modelInput);
		this.add(new JLabel("Price/hour (RM)"));
		this.add(priceInput);
		this.add(new JLabel("Status:"));
		this.add(statusInput);
		
		JButton submitButton = new JButton("Submit");
		JButton resetButton = new JButton("Reset");
				south.add(submitButton);
		south.add(resetButton);
		
		
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
