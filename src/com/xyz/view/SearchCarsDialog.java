package com.xyz.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.xyz.crms.controller.manager.Facade;
import com.xyz.crms.model.Car;

public class SearchCarsDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> criteriaInput = new JComboBox<>(new String[] {"Plate Number", "Model", 
			"Maximum Price", "Exact Price", "Minimum Price"});
	
	private JTextField searchInput = new JTextField();
	private JList<Car> carList = new JList<>();
	private JButton searchButton = new JButton("Search");
	private JButton editButton = new JButton("Edit");
	
	public SearchCarsDialog(MainMenuFrame frame) {
		super(frame, frame.getTitle(), true);
		
		JPanel north = new JPanel();
		JPanel center = new JPanel(new BorderLayout());
		JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		BoxLayout box = new BoxLayout(north, BoxLayout.LINE_AXIS);
		
		carList.setEnabled(false);
		carList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		editButton.setEnabled(false);
		
		north.add(criteriaInput);
		north.add(searchInput);
		north.add(searchButton);
		
		south.add(editButton);
		center.add(new JScrollPane(carList));
		
		north.setLayout(box);
		center.setBorder(BorderFactory.createEmptyBorder(5, 16, 0, 16));
		north.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
		south.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 16));
		
		searchButton.addActionListener(this);
		editButton.addActionListener(this);
		
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);

		this.setSize(400, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == searchButton) {
			int type = criteriaInput.getSelectedIndex();
			String keyword = searchInput.getText();
		
			try (Facade facade = new Facade()) {
				ArrayList<Car> cars = null;
				
				if (type == 0 || type == 1) {
					cars = facade.searchCars(keyword, type);
				} else {
					
					double price;
					
					try {
						price = Double.parseDouble(keyword);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(this, "Invalid value", getTitle(), JOptionPane.INFORMATION_MESSAGE);
						searchInput.grabFocus();
						return;
					}
					
					cars = facade.searchCars(price, type - 2);
				}
				
				DefaultListModel<Car> model = new DefaultListModel<>();
				
				if (cars.size() !=0) {
					carList.setEnabled(true);
					editButton.setEnabled(true);
					
					for (Car car : cars) {
						model.addElement(car);
					}
					
				} else {
					JOptionPane.showMessageDialog(this, "No results found", getTitle(), JOptionPane.INFORMATION_MESSAGE);
					carList.setEnabled(false);
					editButton.setEnabled(false);
					searchInput.grabFocus();
				}
				
				
				carList.setModel(model);
			} catch (SQLException ex) {
				
			}
		} else if (source == editButton) {
			
			Car car = carList.getSelectedValue();
			
			if (car != null) {
				new EditCarDialog((MainMenuFrame) this.getParent(), car);
			} else {
				JOptionPane.showMessageDialog(this, "Please select a car to edit", getTitle(), JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
}
