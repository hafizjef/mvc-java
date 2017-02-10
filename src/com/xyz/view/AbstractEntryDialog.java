package com.xyz.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

public abstract class AbstractEntryDialog extends AbstractDialog {

	private static final long serialVersionUID = 1L;

	protected JButton submitButton = new JButton("Submit");
	protected JButton resetButton = new JButton("Reset");

	protected boolean addOperation;
		
	public AbstractEntryDialog(MainMenuFrame frame, Object object, int row) {
		super(frame, new GridLayout(row, 2, 5, 5));
		
		this.addOperation = object == null;
		
		south.add(submitButton);
		south.add(resetButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == submitButton) {
			submitInput();
		} else if (source == resetButton) {
			resetInput();
		}
	}
	
	protected abstract void submitInput();
	protected abstract void resetInput();
	
}
