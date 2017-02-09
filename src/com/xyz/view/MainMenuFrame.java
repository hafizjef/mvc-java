package com.xyz.view;

import javax.swing.JFrame;

public class MainMenuFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public MainMenuFrame() {
		super("XYZ Sdn. Bhd. Car Rental Management System");
		this.setSize(400, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainMenuFrame();
	}

}
