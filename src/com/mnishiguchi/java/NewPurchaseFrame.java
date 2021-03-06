package com.mnishiguchi.java;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;


public class NewPurchaseFrame extends JFrame
{
	// instance variables
	private ArrayList<JRadioButton>radioButtons = new ArrayList<JRadioButton>(Invoice.articles.length);
	private JRadioButton othersRadio;
	private JRadioButton selectedRadio;
	private JTextField othersField;
	private JTextField priceField;
	private JSpinner qtySpinner;
	private JButton button1;

	// constructor
	public NewPurchaseFrame()
	{
		// configuration of the frame
		this.setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Add New Purchase");
		this.setLocationRelativeTo(null);  // put it at the center of the screen
		
		JPanel mainPanel = new JPanel();    // for decorating with bevel
		mainPanel.setBorder( BorderFactory.createLoweredBevelBorder() );
		
		JPanel subPanel = new JPanel( new GridBagLayout() );
		subPanel.setBorder( BorderFactory.createEmptyBorder(15,0,5,0) );
		
		// ----------- create a panel with radioButtons --------------------------
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout( new GridBagLayout() );
		radioPanel.setBorder( BorderFactory.createLineBorder(Color.GRAY, 1, true) );
		
		// create radio buttons for regular items
		ButtonGroup group = new ButtonGroup();
		JRadioButton rb = null;
		int index = 0;
		for (String name : Invoice.articles)
		{
		 	// create radio buttons; set the first one selected by default
			rb = (index == 0) ? new JRadioButton(name) : new JRadioButton(name);
			group.add( rb );    // add to the ButtonGroup
			radioButtons.add(rb);
			index += 1;    // increment index
		}
		// create an extra radio button for others
		othersRadio = new JRadioButton("Others", true);
		othersRadio.addItemListener( new RadioSelectedListener() );
		group.add( othersRadio );    // add to the ButtonGroup
		othersField = new JTextField(20);
		
		// add radioButtons to radioPanel
		addGridItem(radioPanel, radioButtons.get(0), 0, 0, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, radioButtons.get(1), 1, 0, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, radioButtons.get(2), 2, 0, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, radioButtons.get(3), 3, 0, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, radioButtons.get(4), 0, 1, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, radioButtons.get(5), 1, 1, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, radioButtons.get(6), 2, 1, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, radioButtons.get(7), 3, 1, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, radioButtons.get(8), 0, 2, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, radioButtons.get(9), 1, 2, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, radioButtons.get(10), 2, 2, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, radioButtons.get(11), 3, 2, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel,othersRadio, 0, 3, 1, 1, GridBagConstraints.WEST);
		addGridItem(radioPanel, othersField, 1, 3, 3, 1, GridBagConstraints.WEST);
		
		// add radioPanel  to the mainPanel
		addGridItem(subPanel, radioPanel, 0, 0, 1, 1, GridBagConstraints.CENTER);

		// ----------- create a Box with price & qty fields -------------------
		Box box1 = Box.createHorizontalBox();	  
		// add a label to box1
		box1.add( new JLabel("Unit Price : $") );    
		// add a text field to box1
		priceField = new JTextField(12);
		box1.add(priceField);    
		// add a separator to box1
		box1.add( Box.createHorizontalStrut(20) );	// blank space
		// add a text field to box1
		box1.add( new JLabel("Qty : ") );
		// add a spinner to box1
		qtySpinner = new JSpinner( new SpinnerNumberModel(0,0,32,1) );
		box1.add(qtySpinner);
		// add box1 to the mainPanel
		addGridItem(subPanel, box1, 0, 1, 1, 1, GridBagConstraints.CENTER);

		// ----------- create a Box with a submit button --------------------
		Box box2 = Box.createHorizontalBox();	  
		// create a button
		button1 = new JButton("Add to Shopping Cart");
		OnButtonClickListener handle = new OnButtonClickListener();
		button1.addActionListener(handle);
		// add button1
		box2.add(button1);
		// add box2 to the mainPanel
		addGridItem(subPanel, box2, 0, 2, 1, 1, GridBagConstraints.CENTER);
		
		mainPanel.add(subPanel);
		this.add(mainPanel);
		this.setVisible(true);	    // show this frame    
	}
	
	/** Helper Method to add  a components to a specified GridBag cell
	 * @param p    a panel with the GridBagLayout
	 * @param c    a JComponent item to add
	 * @param x    the x position of the component
	 * @param y    the y position of the component
	 * @param width    number of columns spanned by the component
	 * @param height    number of rows spanned by the component
	 * @param align    one of the constants defined by GridBagConstraints class, 
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
		// ingredients of an Article object
		private String name;
		private double price;
		private int quantity;
		
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if (event.getSource() == button1)
			{
				// ------------------ validate price field --------------------
				String amount = priceField.getText();	// get a price from user input
			
				if ( amount.equals("") )    // ensure text field is not empty
				{
					popupNotice("Please enter the price of the selected item.");
					priceField.requestFocus();
					return;    // quit this procedure right now
				}
				else if ( StringChecker.isFloat(amount) == false  )
				{
					popupNotice("Invalid Dollar Amount  (Example : 3.99)");
					priceField.requestFocus();
					return;    // quit this procedure right now
				}
				// ------------------ process data ----------------------------
				for (JRadioButton rb : radioButtons)   // search for the selected article
				{
					if ( rb.isSelected() )
					{
						selectedRadio = rb;
						break; 
					}
				}
				
				if ( selectedRadio != null )    // if found, it's one of the regular items.
				{
					name = selectedRadio.getText();
				}
				else    // if null, "Others" is selected.
				{
					if ( othersField.getText().equals("") )    // ensure that the field is filled in.
					{
						popupNotice("Please enter  the name of this item.");
						othersField.requestFocus();
						return;
					}
					name =  othersField.getText();
				}
			
				try
				{
					price =Double.parseDouble( amount );    // get price
					quantity = (int) qtySpinner.getValue();    // get quantity
				}
				catch (NumberFormatException ex)
				{
					System.out.println("Number Parse Error in NewPurchaseFrame.");
					System.exit(0);
				}
				// create an Article object and add to the ArrayList
				ShoppingCartFrame.purchasedArticles.add( new Article (name, price, quantity) );
				
				new ShoppingCartFrame();    // show a new invoice's draft
				NewPurchaseFrame.this.dispose();    // close this frame	
			}
		}
		/** Pops up a notice window with a specified message. */
		private void popupNotice(String msg)
		{
			JOptionPane.showMessageDialog( NewPurchaseFrame.this, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/** When "Others" is selected, moves the focus to the text field. */
	class RadioSelectedListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				othersField.requestFocus();
			}
		}
	}
}
