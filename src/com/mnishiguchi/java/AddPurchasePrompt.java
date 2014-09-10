package com.mnishiguchi.java;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import javax.swing.*;


public class AddPurchasePrompt extends JFrame
{
	// instance variables
	private JButton button1;
	private JTextField purchaseInput;
	private Customer customer;
	
	// constructor
	public AddPurchasePrompt(Customer c)
	{
		this.customer = c;
		
		// configuration of the frame
		this.setSize(400, 100);
		this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setTitle("Add New Purchase");
	    this.setLocationRelativeTo(null);  // put it at the center of the screen
	    
	    // create a panel
	    JPanel panel = new JPanel();
	    
	    // add a label
	    panel.add(new JLabel("New Purchase : $"));
        
	    // add a text field
	    purchaseInput = new JTextField(16);
	    panel.add(purchaseInput);
	    
		// create a button
	    button1 = new JButton("OK");
	    OnButtonClickListener handle = new OnButtonClickListener();
	    button1.addActionListener(handle);
	    
	    // add button1
	    panel.add(button1);

	    // add panel to the frame
	    this.add(panel);
	    
	    // now frame is ready to be displayed
	    this.setVisible(true);	    
	}
	
	/** Inner class to listen for a click on button1.
	 * Searches for a customer purchase data based on last name that the user inputed
	 * If user input is empty, re-prompts the user.
	 */
	private class OnButtonClickListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if (event.getSource() == button1)
			{
				// get a purchase amount from user input
				String amount = purchaseInput.getText();
				
				// ensure text field is not empty
				if (amount.length() == 0)
				{
					JOptionPane.showMessageDialog( AddPurchasePrompt.this, 
							"You did not enter anything!", "Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else if ( StringChecker.isFloat(amount) == false )
				{
					JOptionPane.showMessageDialog( AddPurchasePrompt.this,
                            "Invalid Dollar Amount  (Example : 123.45)", 
                            "Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					// add this purchase to this customer's purchase data file
					try
					{
						Purchase p = new Purchase( new Date(), Double.parseDouble(amount) );
						WriteFile.writePurchase(customer, p);
					}
					catch (NumberFormatException e)
					{
						JOptionPane.showMessageDialog(AddPurchasePrompt.this,
								"Could not read a purchase amount due to invalid format.", 
								"Number Parse Error", JOptionPane.ERROR_MESSAGE);
					}
					// show a new purchase history
					new PurchaseHistoryFrame(customer);  
					
					AddPurchasePrompt.this.dispose();    // close this frame	
				}
				// move the focus back to the text field
				purchaseInput.requestFocus();
			}
		}
	}
}