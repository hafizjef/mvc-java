package com.xyz.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class AbstractDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	protected MainMenuFrame frame;
	protected JPanel center = new JPanel();
	protected JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	protected Border empty = BorderFactory.createEmptyBorder(10, 16, 0, 16);

	public AbstractDialog(MainMenuFrame frame, LayoutManager layout) {
		super(frame, frame.getTitle(), true);
		
		this.frame = frame;
		
		center.setBorder(empty);
		center.setLayout(layout);
		
		center.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
		south.setBorder(BorderFactory.createEmptyBorder(12, 10, 10, 10));
		
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	protected void finalizeUI(JButton... buttons) {
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].addActionListener(this);
			
			if (i == 0) {
				this.getRootPane().setDefaultButton(buttons[0]);
			}
		}
		
		this.pack();
		this.setSize(frame.getWidth(), getHeight());
		this.setResizable(false);
		this.setLocationRelativeTo(frame);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
	
	}
	
	protected String validate(String name, String value, boolean required, int maxlength) throws Exception {
		int length = value.trim().length();
		
		if (required) {
			if (length == 0) {
				throw new Exception("- " + name + " is required.");
			} else if (maxlength != -1 && length > maxlength) {
				throw new Exception("- " + "must be between 1-" + maxlength + " characters");
			}
		} else {
			if (maxlength != -1 && length > maxlength) {
				throw new Exception("- " + "must be less or equals to " + maxlength + " characters");
			}
		}
		
		return value;	
	}

}