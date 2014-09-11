package com.mnishiguchi.java;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	private static final String FORMAT_AMOUNT = MainFrame.FORMAT_AMOUNT;
	private static final DateFormat FORMAT_DATE = MainFrame.FORMAT_DATE;
	
	// ingredients for creating an invoice
	public static String invoiceNumber = "";    // get in this frame
	public static Date purchaseDate = new Date();    // get today's date
	public static double amount = 0.0;    // sum up Articles's subTolals
	public static Stack<Article> purchasedArticles = new Stack<Article>();
	// data sent from NewPurchaseFrame
	
	// Components
	private JLabel label1;
	private JButton button1, button2;
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
				// reset selected customer
				MainFrame.selectedCustomer = null;
				// reset ingredients
				invoiceNumber = "";
				purchaseDate = null;
				amount = 0.0;
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
		button1 = new JButton(" => Add Purchase? ");
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
		else	// prepare a TableModel for table creation
		{
			for (Article record : purchasedArticles)
			{
				// append to the tableRows array
				// [0]=>name; [1]=>price; [2]=>qty; [3]=>subTotal; 
				tableRow[0] = record.getName();
				tableRow[1] = String.format( FORMAT_AMOUNT, record.getPrice() );
				tableRow[2] = record.getQuantity();	    	
				tableRow[3] = String.format( 
				FORMAT_AMOUNT, ( record.getPrice() * record.getQuantity() ) );
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
		scroll.setBorder(BorderFactory.createLoweredBevelBorder());
		
		// add components to the frame
		this.add(box1, BorderLayout.NORTH);    // box with label & button
		this.add(scroll);                                                // table component with text area
		
		// ---------------- create a submit button ---------------------------
		button2 = new JButton("               Create a new Invoice               ");
		button2.addActionListener(handle);    // reuse event handler
		
		// create a panel and add button to it
		JPanel panel = new JPanel();
		panel.add( button2 );
		
		// add button2 to the frame
		this.add(panel, BorderLayout.SOUTH);
	
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
			if (e.getSource() == button1)
			{
				 // show a prompt for a new purchase
				new NewPurchaseFrame();
				NewInvoiceFrame.this.dispose();    // close this frame	
			}
			// ------------------- respond to button2 -------------------------
			else if (e.getSource() == button2)
			{
				 // TODO
			}
		}
	}
}
