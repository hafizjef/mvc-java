package com.xyz.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.xyz.crms.controller.manager.Facade;
import com.xyz.crms.model.Customer;

public class SearchCustomerDialog extends AbstractDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> criteriaInput = new JComboBox<>(new String[] {"Name", "Date"});
	
	private JTextField searchInput = new JTextField();
	private JList<Customer> customerList = new JList<>();
	private JButton searchButton = new JButton("Search");
	private JButton editButton = new JButton("Edit");
	
	public SearchCustomerDialog(MainMenuFrame frame) {
		super(frame, new BorderLayout());
		
		JPanel north = new JPanel();
		BoxLayout box = new BoxLayout(north, BoxLayout.LINE_AXIS);
		
		
		editButton.setEnabled(false);
		customerList.setEnabled(false);
		customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		north.add(criteriaInput);
		north.add(searchInput);
		north.add(searchButton);
		
		south.add(editButton);
		center.add(new JScrollPane(customerList));
		
		north.setLayout(box);
		north.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
		
		
		this.add(north, BorderLayout.NORTH);

		finalizeUI(searchButton, editButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == searchButton) {
			int type = criteriaInput.getSelectedIndex();
			String keyword = searchInput.getText();
		
			try (Facade facade = new Facade()) {
				
				ArrayList<Customer> customers = null;
				
				try {
					
					if (type == 0) {
						
						keyword = validate("Name", keyword, true, -1);
						customers = facade.searchCustomers(keyword);
					} else {
						
						Date date = validate("Date", keyword);
						customers = facade.searchCustomers(date);
					}
				} catch (ValidationException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage().substring(1), getTitle(), JOptionPane.WARNING_MESSAGE);
				}
				
				
				
				DefaultListModel<Customer> model = new DefaultListModel<>();
				
				if (customers != null) {
					if (customers.size() !=0) {
						customerList.setEnabled(true);
						editButton.setEnabled(true);
						
						for (Customer customer : customers) {
							model.addElement(customer);
						}
						
					} else {
						JOptionPane.showMessageDialog(this, "No results found", getTitle(), JOptionPane.INFORMATION_MESSAGE);
						customerList.setEnabled(false);
						editButton.setEnabled(false);
						searchInput.grabFocus();
					}
					
					customerList.setModel(model);
				}
				
			} catch (SQLException ex) {}
			
		} else if (source == editButton) {
			
			Customer customer = customerList.getSelectedValue();
			
			if (customer != null) {
				new CustomerEntryDialog((MainMenuFrame) this.getParent(), customer);
			} else {
				JOptionPane.showMessageDialog(this, "Please select a customer to edit", getTitle(), JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
}
