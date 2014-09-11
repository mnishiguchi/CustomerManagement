package com.mnishiguchi.java;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class PurchaseHistoryFrame extends JFrame
{
	// constant
	private static final String FORMAT_AMOUNT = MainFrame.FORMAT_AMOUNT;
	private static final DateFormat FORMAT_DATE = MainFrame.FORMAT_DATE;
	
	// instance variables
	private JLabel label1;
	private JButton button1, button2;
	Customer c = MainFrame.selectedCustomer;
	
	// constructor
	public PurchaseHistoryFrame()
	{
		// configuration of the frame
		this.setSize(400, 250);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Purchase History");
		this.setLocationRelativeTo(null);  // put it at the center of the screen

		// get ustomer's info and purchase history
		String prefix = c.getPrefix();    // "Mr." or "Ms."
		String lastName = c.getLastName();
		String phoneNumber = c.getPhoneNumber();
		Stack<Purchase> purchaseList =  ReadFile.getPurchaseList(c);

		// create a panel with BorderLayout
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());

		// create a Box
		Box box1 = Box.createHorizontalBox();
		
		// create a label
		label1 = new JLabel(prefix + " " + lastName + " - " + phoneNumber);
		
		// create button with an event handler
		button1 = new JButton("=> Add New Purchase");
		OnButtonClickListener handle = new OnButtonClickListener();
		button1.addActionListener(handle);
		
		// add label and button to box1
		box1.add( label1 );
		box1.add( Box.createHorizontalGlue() );  // to separate components as far as possible
		box1.add( button1 );
		box1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		// create a table model	
		Object[] tableRow = new Object[3];       // [0]=>date; [1]=>amount; [2]=>invoiceNumber
		Object[] tableHead = {"Date", "Amount(US$)", "Invoice#"};
		DefaultTableModel tableModel = new DefaultTableModel(tableHead, 0);    // initially no row
		
		// ensure that purchaseList is not null
		if ( purchaseList == null || purchaseList.isEmpty() )
		{
			tableRow[0] = "(no data)";
			tableRow[1] = "(no data)";
			tableRow[2] = "(no data)";
			tableModel.addRow(tableRow);
		}
		else
		{
		Purchase record;    // temporary storage for each purchase record
		while (purchaseList.isEmpty() == false)
			{
				// pop a record
				record = purchaseList.pop();
				
				// append to the tableRows array
				tableRow[0] = FORMAT_DATE.format( record.getDate() ) ;
				tableRow[1] =  String.format( FORMAT_AMOUNT, record.getAmount() );
				tableRow[2] = record.getInvoiceNumber();
				tableModel.addRow(tableRow);
			}
		}
		// create a table
		JTable table = new JTable(tableModel);
		
		// format each table row (align center)
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		
		// add table to scroll pane
		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(BorderFactory.createLoweredBevelBorder());
		
		// add components to the frame
		this.add(box1, BorderLayout.NORTH);    // box with label & button
		this.add(scroll);                                                // table component with text area
		
		// create buttons with an event handler
		button2 = new JButton("Show Invoice");
		button2.addActionListener(handle);	// reuse event handler
		
		// create a panel and add button to it
		JPanel panel = new JPanel();
		panel.add( button2 );
		
		// add button2 to the frame
		this.add(panel, BorderLayout.SOUTH);

		// now frame is ready to be displayed
		this.setVisible(true);
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
				PurchaseHistoryFrame.this.dispose();    // close this frame	
			}
			// ------------------- respond to button2 -------------------------
			else if (e.getSource() == button2)
			{
				 // TODO
			}
		}
	}
}