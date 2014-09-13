package com.mnishiguchi.java;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class CreateAcountFrame  extends JFrame
{
	// instance variables
	private JButton button1;
	private JTextField textField1, textField2, textField3, textField4;
	private JRadioButton radioMr, radioMs;
	
	// constructor
	public CreateAcountFrame()
	{
		// configuration of the frame
		this.setSize(400, 250);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Create A New Acount");
		this.setLocationRelativeTo(null);  // put it at the center of the screen
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder( BorderFactory.createLoweredBevelBorder() );
		
		// create a panel with the GridBagLayout
		JPanel panel = new JPanel();
		panel.setLayout( new GridBagLayout() );
		panel.setBorder( BorderFactory.createEmptyBorder(9,9,9,9) );
		
		// add button names to the panel
		addGridItem(panel, new JLabel("Last Name: "), 0, 0, 1, 1, GridBagConstraints.EAST);
		addGridItem(panel, new JLabel("First Name : "), 0, 1, 1, 1, GridBagConstraints.EAST);
		addGridItem(panel, new JLabel("Phone Number: "), 0, 2, 1, 1, GridBagConstraints.EAST);
		addGridItem(panel, new JLabel("Zip Code: "), 0, 3, 1, 1, GridBagConstraints.EAST);
		
		// create text fields
		textField1 = new JTextField(16);
		textField2 = new JTextField(16);
		textField3 = new JTextField(16);
		textField4 = new JTextField(16);
		
		// add text fields to the panel
		addGridItem(panel, textField1, 1, 0, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, textField2, 1, 1, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, textField3, 1, 2, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, textField4, 1, 3, 1, 1, GridBagConstraints.WEST);
		
		// create radio buttons
		ButtonGroup prefix = new ButtonGroup();
		radioMr = new JRadioButton("Mr.");
		radioMr.setSelected(true);  // selected by default
		radioMs = new JRadioButton("Ms.");
		prefix.add(radioMr);
		prefix.add(radioMs);
		
		// add radio buttons to the panel
		addGridItem(panel, radioMr, 0, 4, 1, 1, GridBagConstraints.EAST);
		addGridItem(panel, radioMs, 1, 4, 1, 1, GridBagConstraints.WEST);	    
		
		// create button with event handler
		button1 = new JButton("Create A New Acount");
		OnButtonClickListener handle = new OnButtonClickListener();
		button1.addActionListener(handle);
		
		// add button to the panel
		addGridItem(panel, button1, 1, 5, 1, 1, GridBagConstraints.WEST);
		
		mainPanel.add(panel);             // add panel to the frame	
		this.add(mainPanel);
		this.setVisible(true);    // show this frame
	}
	
	/**
	 * Helper Method to add  a components to a specified GridBag cell
	 */
	private void addGridItem(JPanel p, JComponent c, int x, int y, int width, int height, int align)
	{
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = width;      // number of columns spanned by the component
		gc.gridheight = height;    // number of rows spanned by the component
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5,5,5,5);    // padding
		gc.anchor = align;
		gc.fill = GridBagConstraints.NONE;
		p.add(c,  gc);
	}
	
	/** Inner class to listen for a click on button1 */
	private class OnButtonClickListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			String lastName, firstName, phoneNumber, zipCode;
			Customer.Prefix prefix;
			
			if (event.getSource() == button1)
			{
				// get data from user input
				lastName = textField1.getText();
				firstName = textField2.getText();
				phoneNumber = textField3.getText();
				zipCode = textField4.getText();
					
				// ensure text field is not empty
				if ( lastName.equals("") || firstName.equals("") || phoneNumber.equals("") || zipCode.equals("") )
				{
					JOptionPane.showMessageDialog(	CreateAcountFrame.this, 
							"Please fill in all the fields!", "Message", JOptionPane.INFORMATION_MESSAGE);
					textField1.requestFocus();    // move the focus back to the text field
				}	
				// validate last name
				else if ( lastName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*") == false )
				{
					JOptionPane.showMessageDialog(	CreateAcountFrame.this, 
							"Invalid Last Name Format", "Message", JOptionPane.INFORMATION_MESSAGE);			
					textField1.requestFocus();    // move the focus back to the text field
				}
				// validate first name
				else if ( firstName.matches( "[A-Z][a-zA-Z]*") == false )
				{
					JOptionPane.showMessageDialog( CreateAcountFrame.this, 
							"Invalid First Name Format", "Message", JOptionPane.INFORMATION_MESSAGE);
					textField2.requestFocus();    // move the focus back to the text field
				}
				// ensure that phone number is a 10-digit String
				else if  ( phoneNumber.matches("^|\\d{10}$") == false )
				{
					JOptionPane.showMessageDialog( CreateAcountFrame.this, 
							"Please enter a 10-digit number (Example: 2021118888)",
							"Message", JOptionPane.INFORMATION_MESSAGE);
					textField3.requestFocus();    // move the focus back to the text field    
				}
				// ensure that zipcode is a 5-digit String
				else if  ( zipCode.matches("^|\\d{5}$") == false )
				{
					JOptionPane.showMessageDialog( CreateAcountFrame.this, 
							"Please enter a 5-digit number (Example: 12345)", 
							"Message", JOptionPane.INFORMATION_MESSAGE);
					textField4.requestFocus();    // move the focus back to the text field
				}
				else
				{
					// get prefix from radio buttons
					prefix = ( radioMr.isSelected() ) ? Customer.Prefix.MR : Customer.Prefix.MS;
					
					// create a Customer object
					Customer customer = new Customer(lastName, firstName, phoneNumber, zipCode, prefix);
					
					// check if this customer already exists
					if ( customer.exists() )
					{
						JOptionPane.showMessageDialog( CreateAcountFrame.this, 
								"This customer exists in your customer list!", "Message", 
								JOptionPane.INFORMATION_MESSAGE);
						
						// remember this customer
						MainFrame.selectedCustomer = customer;
						
						// show this customer's purchase history
						new PurchaseHistoryFrame();
						CreateAcountFrame.this.dispose();    // close this frame	
					}
					else
					{
						// append this customer to customer.txt
						WriteFile.writeCustomer(customer);
						
						// remember this customer
						MainFrame.selectedCustomer = customer;
						
						// show this customer's purchase history
						new PurchaseHistoryFrame();
						CreateAcountFrame.this.dispose();    // close this frame	
					}
				}
			}
		}
	}
}
