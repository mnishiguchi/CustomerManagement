package com.mnishiguchi.java;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/** A frame for the main menu. */
public class MainFrame extends JFrame
{
	// class variables
	public static Customer currentCustomer;  // remember customer
	
    // instance variables
	private JButton button1,  button2, button3, button4;
	
	public static void main(String[] args)   // program starts from here
	{
		new MainFrame();
	}
	
	// constructor
	public MainFrame()
	{
		// configuration of the frame
		this.setSize(400, 250);
		this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setTitle("Main Menu");
	    this.setLocationRelativeTo(null);  // put it at the center of the screen
		    
	    // create a panel with the GridBagLayout
	    JPanel panel = new JPanel();
	    panel.setLayout( new GridBagLayout() );
		    
	    // add labels of button names 
	    addGridItem(panel, new JLabel("Create A New Account : "), 0, 0, 1, 1, GridBagConstraints.EAST);
	    addGridItem(panel, new JLabel("Show All Accounts : "), 0, 1, 1, 1, GridBagConstraints.EAST);
	    addGridItem(panel, new JLabel("Search By Phone Number : "), 0, 2, 1, 1, GridBagConstraints.EAST);
	    addGridItem(panel, new JLabel("Search By Last Name : "), 0, 3, 1, 1, GridBagConstraints.EAST);
		    
	    // create buttons
	    button1 = new JButton("OK");
		button2 = new JButton("OK");
	    button3 = new JButton("OK");
	    button4 = new JButton("OK");
		    
	    // set an event handler on buttons
	    OnButtonClickListener handle = new OnButtonClickListener();
	    button1.addActionListener(handle);
	    button2.addActionListener(handle);	    
		button3.addActionListener(handle);	    
		button4.addActionListener(handle);	    
		    
		// add buttons
        addGridItem(panel, button1, 1, 0, 1, 1, GridBagConstraints.WEST);
	    addGridItem(panel, button2, 1, 1, 1, 1, GridBagConstraints.WEST);
	    addGridItem(panel, button3, 1, 2, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, button4, 1, 3, 1, 1, GridBagConstraints.WEST);

	    this.add(panel);    // add panel to the frame
		    
	    this.setVisible(true);    // show this frame
	}
		
	/**
	 * Helper Method to add  a components to a specified GridBag cell
	 * @param p             a panel with the GridBagLayout
	 * @param c             a JComponent item to add
	 * @param x             the x position of the component
	 * @param y             the y position of the component
	 * @param width    number of columns spanned by the component
	 * @param height    number of rows spanned by the component
	 * @param align       one of the constants defined by GridBagConstraints class, 
	 *                                    which indicate where to place the component if it doesn't fill the space.
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
		
	/**
	 * Inner class to listen for a click on buttons
	 */
	private class OnButtonClickListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			 /* ==== respond to button1 ==== */
			if (e.getSource() == button1)                      
			{			    
				new CreateAcountPrompt();
			}
			/* ==== respond to button2 ==== */
			else if (e.getSource() == button2)
			{
			    // read all the customers from customers.txt
			    ArrayList<Customer> customerList = ReadFile.getCustomerList();
			    	
			     // ensure customer exist
				if (customerList == null || customerList.isEmpty() )
				{
					JOptionPane.showMessageDialog( MainFrame.this, 
							"No customer exists!", "Message", JOptionPane.INFORMATION_MESSAGE);		   
				}
			    else
			    {				  					
			    	// display a list of existing customers
					new AccountListFrame( customerList );						
			    }
			}
			/* ==== respond to button3 ==== */
			else if (e.getSource() == button3)
			{			
				String phoneNumber;
				while (true)
				{
					// get a phone number from user input
					phoneNumber = JOptionPane.showInputDialog( MainFrame.this, 
							"Please enter a phone number: ", "Search By Phone Number", JOptionPane.QUESTION_MESSAGE);
					
					if (phoneNumber == null) return;    // if user clicked on cancel button, end this procedure
					
					// ensure text field is not empty
					if ( phoneNumber.equals("") )
					{
						JOptionPane.showMessageDialog( MainFrame.this, 
								"You did not enter anything!", "Message", JOptionPane.INFORMATION_MESSAGE);
					}
					else if  ( phoneNumber.matches("^|\\d{10}$") == false )
					{
						JOptionPane.showMessageDialog( MainFrame.this, 
								"Please enter a 10-digit number (Example: 2021118888)", "Message", JOptionPane.INFORMATION_MESSAGE);
					}
					else break;
				}
				// search for a customer by phone number
				ArrayList<Customer> result = Search.findCustomerByPhone(phoneNumber);
                
				if ( result == null || result.isEmpty() )
				{
					JOptionPane.showMessageDialog( MainFrame.this, 
							"No customer data to show!", "Message", JOptionPane.INFORMATION_MESSAGE);	
				}
				else
				{
					new  AccountListFrame( result );    // show search result
				}
			}
			/* ==== respond to button4 ==== */
			else if (e.getSource() == button4)
			{
				//new SearchByLastNamePrompt();
				String lastName;
				while (true)
				{
					// get a last name from user input
					lastName = JOptionPane.showInputDialog( MainFrame.this, 
							"Please enter a last name: ", "Search By Last Name", JOptionPane.QUESTION_MESSAGE);
					
					if (lastName == null) return;     // if user clicked on cancel button, end this procedure

					// ensure text field is not empty
					if ( lastName.equals("") )
					{
							JOptionPane.showMessageDialog( MainFrame.this, 
									"You did not enter anything!", "Message", JOptionPane.INFORMATION_MESSAGE );
					}
					// validate last name
					else if ( lastName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*") == false )
					{
						JOptionPane.showMessageDialog(	MainFrame.this, 
								"Invalid Last Name Format", "Message", JOptionPane.INFORMATION_MESSAGE);									
					}
					else break;
				}
				// search for a customer by last name
				ArrayList<Customer> result = Search.findCustomerByLastName(lastName);

				if ( result == null || result.isEmpty() )
				{
					JOptionPane.showMessageDialog( MainFrame.this, 
							"No customer data to show!", "Message", JOptionPane.INFORMATION_MESSAGE);	
				}
				else
				{
					new  AccountListFrame( result );    // show search result
				}
			}
		}
	}
}