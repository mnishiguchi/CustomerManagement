package com.mnishiguchi.java;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;


public class NewPurchaseFrame extends JFrame
{
	// instance variables
	private String[] articles = {
			"Item0","Item1", "Item2","Item3","Item4","Item5",
			"Item6","Item7", "Item8","Item9","Item10","Item11"
			};
	ArrayList<JRadioButton>radioButtons = new ArrayList<JRadioButton>(articles.length);
	private JTextField priceField;
	private JSpinner qtySpinner;
	private JButton button1;
	private Customer customer;
	
	// constructor
	public NewPurchaseFrame(Customer c)
	{
		this.customer = c;
		
		// configuration of the frame
		this.setSize(400, 250);
		this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setTitle("Add New Purchase");
	    this.setLocationRelativeTo(null);  // put it at the center of the screen
	    
	    // mainPanel
	    JPanel mainPanel = new JPanel( new GridBagLayout() );
	    
	    // ----------- create a panel with radioButtons --------------------------
	    JPanel radioPanel = new JPanel();
	    radioPanel.setLayout( new GridBagLayout() );
	    
	    // create radio buttons
	    ButtonGroup group = new ButtonGroup();
	    JRadioButton rb = null;
	    int index = 0;
	    for (String name : articles)
	    {
	    	rb = new JRadioButton(name);
	    	radioButtons.add(rb);
	    	group.add( radioButtons.get(index) );
	    	index += 1;	// increment index
	    }
	    // add radioButtons to radioPanel
	    addGridItem(radioPanel, radioButtons.get(0), 0, 0, 1, 1, GridBagConstraints.CENTER);
	    addGridItem(radioPanel, radioButtons.get(1), 1, 0, 1, 1, GridBagConstraints.CENTER);
	    addGridItem(radioPanel, radioButtons.get(2), 2, 0, 1, 1, GridBagConstraints.CENTER);
	    addGridItem(radioPanel, radioButtons.get(3), 3, 0, 1, 1, GridBagConstraints.CENTER);
	    addGridItem(radioPanel, radioButtons.get(4), 0, 1, 1, 1, GridBagConstraints.CENTER);
	    addGridItem(radioPanel, radioButtons.get(5), 1, 1, 1, 1, GridBagConstraints.CENTER);
	    addGridItem(radioPanel, radioButtons.get(6), 2, 1, 1, 1, GridBagConstraints.CENTER);
	    addGridItem(radioPanel, radioButtons.get(7), 3, 1, 1, 1, GridBagConstraints.CENTER);
	    addGridItem(radioPanel, radioButtons.get(8), 0, 2, 1, 1, GridBagConstraints.CENTER);
	    addGridItem(radioPanel, radioButtons.get(9), 1, 2, 1, 1, GridBagConstraints.CENTER);
	    addGridItem(radioPanel, radioButtons.get(10), 2, 2, 1, 1, GridBagConstraints.CENTER);
	    addGridItem(radioPanel, radioButtons.get(11), 3, 2, 1, 1, GridBagConstraints.CENTER);
	    
	    // add radioPanel  to the mainPanel
	    addGridItem(mainPanel, radioPanel, 0, 0, 1, 1, GridBagConstraints.CENTER);
	    
	    // ----------- create a Box with price & qty fields -------------------
	    Box box1 = Box.createHorizontalBox();	  
	    // add a label to box1
	    box1.add( new JLabel("Unit Price : $") );    
	    // add a text field to box1
	    priceField = new JTextField(8);
	    box1.add(priceField);    
	    // add a separator to box1
	    box1.add( Box.createHorizontalStrut(20) );	// blank space
	    // add a text field to box1
	    box1.add( new JLabel("Qty : ") );
	    // add a spinner to box1
	    qtySpinner = new JSpinner( new SpinnerNumberModel(0,0,32,1) );
	    box1.add(qtySpinner);
	    // add box1 to the mainPanel
	    addGridItem(mainPanel, box1, 0, 1, 1, 1, GridBagConstraints.CENTER);
	    
	 // ----------- create a Box with a submit button --------------------
	    Box box2 = Box.createHorizontalBox();	  
		// create a button
	    button1 = new JButton("          Submit          ");
	    OnButtonClickListener handle = new OnButtonClickListener();
	    button1.addActionListener(handle);
	    // add button1
	    box2.add(button1);
	    // add box2 to the mainPanel
	    addGridItem(mainPanel, box2, 0, 2, 1, 1, GridBagConstraints.CENTER);
	    
	    this.add(mainPanel);
	    this.setVisible(true);		// show this frame    
	}
	
	/** Helper Method to add  a components to a specified GridBag cell
	 * @param p			a panel with the GridBagLayout
	 * @param c			a JComponent item to add
	 * @param x			the x position of the component
	 * @param y			the y position of the component
	 * @param width	number of columns spanned by the component
	 * @param height	number of rows spanned by the component
	 * @param align		one of the constants defined by GridBagConstraints class, 
	 * 								which indicate where to place the component if it doesn't fill the space.
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
				// TODO
			}
				/**********************
				 // get a purchase amount from user input
				//String amount = purchaseInput.getText();
				
				// ensure text field is not empty
				if (amount.length() == 0)
				{
					JOptionPane.showMessageDialog( NewPurchaseFrame.this, 
							"You did not enter anything!", "Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else if ( StringChecker.isFloat(amount) == false )
				{
					JOptionPane.showMessageDialog( NewPurchaseFrame.this,
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
						JOptionPane.showMessageDialog(NewPurchaseFrame.this,
								"Could not read a purchase amount due to invalid format.", 
								"Number Parse Error", JOptionPane.ERROR_MESSAGE);
					}
					// show a new purchase history
					new PurchaseHistoryFrame(customer);  
					
					NewPurchaseFrame.this.dispose();    // close this frame	
				}
				// move the focus back to the text field
				purchaseInput.requestFocus();
			}
		}
		**********************/
		}
	}
}