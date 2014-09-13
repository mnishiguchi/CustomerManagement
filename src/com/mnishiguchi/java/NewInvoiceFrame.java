package com.mnishiguchi.java;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


/** Shows draft of new invoice. 
 * You can add an article to be purchased one by one. 
 * You can create an invoice file (/invoice/000000.txt) 
 * and save the data in it by clicking "Create Invoice" button.
 * */
public class NewInvoiceFrame extends JFrame
{
	// Static constants
	private static final DecimalFormat FORMAT_AMOUNT = MainFrame.FORMAT_AMOUNT;
	
	// ingredients of Invoice body    // data sent from NewPurchaseFrame
	public static Stack<Article> purchasedArticles = new Stack<Article>();
	
	// Components
	private JLabel label1, label2;
	private JButton button1, button2;
	private JTextField invoiceField;
	private String invoiceNumber;
	private Date now;
	private Customer customer = MainFrame.selectedCustomer;
	
	// constructor
	public NewInvoiceFrame()
	{
		System.out.println("purchasedArticles.size = " + purchasedArticles.size());
		
		// configuration of the frame
		this.setSize(400, 250);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("New Invoice");
		this.setLocationRelativeTo(null);  // put it at the center of the screen
		
		// listen for this frame's getting closed
		this.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				// reset some static fields
				MainFrame.selectedCustomer = null;
				purchasedArticles.clear();
			}
		});
		
		// create a panel with BorderLayout
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		
		// ----------- create a Box with a label and a button -----------------
		Box box1 = Box.createHorizontalBox();
		
		// create a label with a customer info
		label1 = new JLabel(customer.getPrefix() + " " + 
				customer.getLastName() + " - " + customer.getPhoneNumber());
		
		// create button with an event handler
		button1 = new JButton("Add Purchase");
		OnButtonClickListener handle = new OnButtonClickListener();
		button1.addActionListener(handle);
		
		// add label and button to box1
		box1.add( label1 );
		box1.add( Box.createHorizontalGlue() );  // to separate components as far as possible
		box1.add( button1 );
		box1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
	 // ------------------------- create a table ----------------------------
		// create a table model	
		Object[] tableRow = new Object[4];       // [0]=>name; [1]=>price; [2]=>qty; [3]=>subTotal; 
		Object[] tableHead = {"Article", "Unit Price(US$)", "Qty", "Subtotal"};
		DefaultTableModel tableModel = new DefaultTableModel(tableHead, 0);    // initially no row
		
		// ensure that purchaseList is not null
		if ( purchasedArticles == null || purchasedArticles.isEmpty() )
		{
			tableRow[0] = "(no data)";
			tableRow[1] = "(no data)";
			tableRow[2] = "---";
			tableRow[3] = "(no data)";
			tableModel.addRow(tableRow);
		}
		else    // prepare a TableModel for table creation
		{
			for (Article record : purchasedArticles)
			{
				// append to the tableRows array
				// [0]=>name; [1]=>price; [2]=>qty; [3]=>subTotal; 
				tableRow[0] = record.getName();
				tableRow[1] = FORMAT_AMOUNT.format( record.getPrice() );
				tableRow[2] = record.getQuantity();	    	
				tableRow[3] = FORMAT_AMOUNT.format( record.getPrice() * record.getQuantity() );
				tableModel.addRow(tableRow);
			}
		}
		// create a table using the tableModel prepared above
		JTable table = new JTable(tableModel);
		
		// format each table row (align center)
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		
		// set column widths
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(33); 
		table.getColumnModel().getColumn(3).setPreferredWidth(100); 
		
		// add table to scroll pane
		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder( BorderFactory.createLoweredBevelBorder() );
		
		// add components to the frame
		this.add(box1, BorderLayout.NORTH);    // box with label & button
		this.add(scroll);                                                // table component with text area
		
		// ---------------- create the create invoice section -----------------
		Box footerBox = Box.createHorizontalBox();    // container
		
		// create a text field for invoice number input
		invoiceField = new JTextField(10);
		
		// create submit button
		//JPanel buttonPanel = new JPanel( new BorderLayout() );
		button2 = new JButton("Create a new Invoice");
		button2.addActionListener(handle);    // reuse event handler
		//buttonPanel.add(button2, BorderLayout.NORTH);
		
		// add components to footerBox
		footerBox.add( new JLabel("Invoice# : ") );
		footerBox.add(invoiceField);
		footerBox.add( Box.createHorizontalStrut(10) );
		footerBox.add(button2);
		
		// add padding to footerBox
		JPanel footerPadding = new JPanel();
		footerPadding.setBorder( BorderFactory.createEmptyBorder(2,2,2,2) );
		footerPadding.add(footerBox);    // add padding

		// add button2 to the frame
		this.add(footerPadding, BorderLayout.SOUTH);
		this.setVisible(true);		// show this frame
	}
	
	/**
	 * Inner class to listen for a click on buttons
	 */
	private class OnButtonClickListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// ------------------- respond to button1 -------------------------
			if (e.getSource() == button1)    // add new purchase
			{
				 // show a prompt for a new purchase
				new NewPurchaseFrame();
				NewInvoiceFrame.this.dispose();    // close this frame	
			}
			// ------------------- respond to button2 -------------------------
			else if (e.getSource() == button2)    // create a new invoice
			{
				// get invoice number from user input
				String input = invoiceField.getText().toString();
				if  ( input.matches("^|\\d{5}$") == false )
				{
					popupNotice("Please enter a 5-digit number (Example: 12345)");
					invoiceField.requestFocus();    // move the focus back to the text field
					return;    // quit this procedure right now
				}
				else if ( input.equals("") )
				{
					popupNotice("Please enter an invoice number.");
					invoiceField.requestFocus();    // move the focus back to the text field
					return;    // quit this procedure right now
				}
				
				// Ensure that the same # doesn't exist
				if ( Invoice.exists(input) )
				{
					popupNotice("This invoice# already exists.");
					invoiceField.requestFocus();    // move the focus back to the text field
					return;    // quit this procedure right now
				}
				else { invoiceNumber = input; }
				
				// validate purchasedArticles
				if ( purchasedArticles.isEmpty() || purchasedArticles == null )
				{
					popupNotice("Please provide purchased articles and their quantities.");
					return;    // quit this procedure right now
				}
				
				// create a new Invoice object
				now = new Date();   // current date&time
				double amount = getGrandTotal(purchasedArticles);
				Invoice inv = new Invoice(invoiceNumber, customer.getPhoneNumber(), 
						now, amount, purchasedArticles);
				
				WriteFile.writeInvoice(inv);    // create an invoice file with this Invoice object
				
				// create a new Purchase object
				Purchase purchase = new Purchase(now, amount, invoiceNumber);
				WriteFile.writePurchase(customer, purchase);    // add this purchase to file
				
				new InvoiceFrame(inv);    // show this invoice
				NewInvoiceFrame.this.dispose();    // close this frame
			}
		}
		
		/** Popup a notice window with a specified message. */
		private void popupNotice(String msg)
		{
			JOptionPane.showMessageDialog( NewInvoiceFrame.this, msg,
					"Message", JOptionPane.INFORMATION_MESSAGE);
		}
		
		/**
		 * @param purchasedArticles - Stack of Article objects that represents invoice's rows
		 * @return sum of each row's subtotal
		 */
		private double getGrandTotal(Stack<Article> purchasedArticles)
		{
			double d = 0.0;
			for (Article a : purchasedArticles)
			{
				d += a.getSubTotal();
			}
			return d;
		}
	}
}
