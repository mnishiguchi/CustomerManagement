package com.mnishiguchi.java;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;


/** A frame for the main menu. */
public class MainFrame extends JFrame
{
	// Global constants
    public static final String DELIMITER = "\t";
	public static final String PATH_CUSTOMER = "C:customer_data\\";
	public static final String PATH_INVOICE = "C:invoice\\";
	public static final int WIDTH = 480;
	public static final int HEIGHT = 300;
	
	//public static final String FORMAT_AMOUNT = "%.2f";      // 1234.56
	public static final DecimalFormat FORMAT_AMOUNT = new DecimalFormat("#,###,##0.00");
	public static final DateFormat FORMAT_DATE = 	new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	// global reference
	public static Customer selectedCustomer;
	public static String invoiceNumber = "";
	public static Stack<Article> purchasedArticles = new Stack<Article>();
	
    // instance variables
	private JButton button1,  button2, button3, button4, button5;
	
	public static void main(String[] args)   // program starts from here
	{
		UIManager.put("Button.font", new Font("Calibri",Font.PLAIN,16) );
		UIManager.put("TextField.font", new Font("Arial",Font.PLAIN,14));
		UIManager.put("Table.font", new Font("Arial",Font.PLAIN,14));
		UIManager.put("Label.font", new Font("Arial",Font.PLAIN,14));
		//UIManager.put("RadioButton.font", /* font of your liking */);
		
		//UIManager.put("Panel.font", /* font of your liking */);

		//UIManager.put("TableHeader.font", /* font of your liking */);
		
		new MainFrame();
	}
	
	// constructor
	public MainFrame()
	{
		// configuration of the frame
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Main Menu");
		this.setLocationRelativeTo(null);  // put it at the center of the screen
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder( BorderFactory.createLoweredBevelBorder() );
		
		JPanel subPanel = new JPanel();
		subPanel.setBorder( BorderFactory.createEmptyBorder(16,0,0,0) );
		
		// create buttons
		button1 = new JButton("Create A New Account");
		button2 = new JButton("Show All Accounts");
		button3 = new JButton("Search By Phone Number");
		button4 = new JButton("Search By Last Name");
		button5 = new JButton("Search By Invoice Number");
		JButton[] buttons = {button1, button2, button3, button4, button5};
		int numOfButtons = 5;
		
		// create an event handler
		OnButtonClickListener handle = new OnButtonClickListener();
		
		Box buttonBox = Box.createVerticalBox();
		JPanel[] panels = new JPanel[numOfButtons];
		for (int i = 0; i < numOfButtons; i++)
		{
			buttons[i].addActionListener(handle);    // add event handler to each button
			
			// make buttons stretch  across the available width by putting them in BorderLayout.NORTH
			if (i >0)  	buttonBox.add( Box.createVerticalStrut(16) );    // spacer
			panels[i] = new JPanel( new BorderLayout() );
			panels[i].add(buttons[i], BorderLayout.NORTH);
			buttonBox.add( panels[i] );
		}
		subPanel.add(buttonBox);
		mainPanel.add(subPanel);
		this.add(mainPanel);
	    this.setVisible(true);    // show this frame
	}

	/** Inner class to listen for a click on buttons */
	private class OnButtonClickListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			 // ---------------------- respond to button1 ----------------------
			if (e.getSource() == button1)
			{
				new CreateAcountFrame();
			}
			// ---------------------- respond to button2 ----------------------
			else if (e.getSource() == button2)
			{
				// read all the customers from customers.txt
				ArrayList<Customer> customerList = ReadFile.getCustomerList();
				
				if (customerList == null || customerList.isEmpty() )    // ensure customer exist
				{
					popupNotice("No customer exists!");
				}
				else
				{
					// display a list of existing customers
					new AccountListFrame( customerList );
				}
			}
			// ---------------------- respond to button3 ----------------------
			else if (e.getSource() == button3)
			{
				String phoneNumber;
				while (true)
				{
					// get a phone number from user input
					phoneNumber = JOptionPane.showInputDialog( MainFrame.this, 
							"Please enter a phone number: ", "Search By Phone Number", JOptionPane.QUESTION_MESSAGE);
					
					if (phoneNumber == null)  return;    // if user clicked on cancel button, end this procedure
					
					// ensure text field is not empty
					if ( phoneNumber.equals("") )
					{
						popupNotice("You did not enter anything!");
					}
					else if  ( phoneNumber.matches("^|\\d{10}$") == false )
					{
						popupNotice("Please enter a 10-digit number (Example: 2021118888)");
					}
					else break;
				}
				// search for a customer by phone number
				ArrayList<Customer> result = Search.findCustomerByPhone(phoneNumber);
				
				if ( result == null || result.isEmpty() )
				{
					popupNotice("No customer data found");	
				}
				else
				{
					new  AccountListFrame( result );    // show search result
				}
			}
			// ---------------------- respond to button4 ----------------------
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
						popupNotice("You did not enter anything!");
					}
					// validate last name
					else if ( lastName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*") == false )
					{
						popupNotice("Invalid Last Name Format");
					}
					else break;
				}
				// search for a customer by last name
				ArrayList<Customer> result = Search.findCustomerByLastName(lastName);
				
				if ( result == null || result.isEmpty() )
				{
					popupNotice("no customer data found");	
				}
				else
				{
					new  AccountListFrame( result );    // show search result
				}
			}
			// ---------------------- respond to button5 ---------------------- 
			else if (e.getSource() == button5)
			{
				String invoiceNumber;
				while (true)
				{
					// get a phone number from user input
					invoiceNumber = JOptionPane.showInputDialog( MainFrame.this, 
							"Please enter an invoice: ", "Search By Invoice Number", JOptionPane.QUESTION_MESSAGE);
					
					if (invoiceNumber == null)  return;    // if user clicked on cancel button, end this procedure
					
					// ensure text field is not empty
					if ( invoiceNumber.equals("") )
					{
						popupNotice("You did not enter anything!");
					}
					else if  ( invoiceNumber.matches("^|\\d{5}$") == false )
					{
						popupNotice("Please enter a 5-digit number (Example: 12345)");
					}
					else break;
				}
				// search for an invoice
				Invoice result = Invoice.findByInvoiceNumber(invoiceNumber);
				
				if ( result == null )
				{
					popupNotice("No invoice found");
				}
				else
				{
					new  InvoiceFrame( result );    // show search result
				}
			}
		}
		/** Pops up a notice window with a specified message. */
		private void popupNotice(String msg)
		{
			JOptionPane.showMessageDialog( MainFrame.this, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
