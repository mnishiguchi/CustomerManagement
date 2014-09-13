package com.mnishiguchi.java;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class InvoiceFrame extends JFrame
{
	// constant
	public static final DecimalFormat FORMAT_AMOUNT = MainFrame.FORMAT_AMOUNT;
	public static final DateFormat FORMAT_DATE = MainFrame.FORMAT_DATE;
	
	// instance variables
	private JLabel label1, label2;
	
	private String invoiceNumber;
	private String phoneNumber;
	private Date purchaseDate;
	private double grandTotal;
	private Stack<Article> purchasedArticles;
	
	// constructors
	public InvoiceFrame(String invoiceNumber)
	{
		//get invoice from the file
		Invoice inv = ReadFile.getInvoice(invoiceNumber);		
		new InvoiceFrame( inv.getInvoiceNumber() );
	}

	// constructor
	public InvoiceFrame(Invoice inv)
	{
		if (inv == null)    // ensure that invoice is not null
		{
			JOptionPane.showMessageDialog( this, 
					"Specified invoice data is empty.", 
					"Message", JOptionPane.INFORMATION_MESSAGE);
			return;    // quit this procedure right now
		}
		
		// get data from this Invoice object
		invoiceNumber = inv.getInvoiceNumber();
		phoneNumber = inv.getPhoneNumber();
		purchaseDate = inv.getPurchaseDate();
		grandTotal = inv.getGrandTotal();
		purchasedArticles = inv.getPurchasedArticles();
		
		// configuration of the frame
		this.setSize(400, 250);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Invoice #" + invoiceNumber);
		this.setLocationRelativeTo(null);  // put it at the center of the screen
		
		// create a panel with BorderLayout
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		
		// create a Box
		Box box1 = Box.createHorizontalBox();
		
		// if the user uses "Search By Invoice Number" function,
		// the app doesn't have any Customer object registered.
		if (MainFrame.selectedCustomer == null)
		{
			ArrayList<Customer> c = Search.findCustomerByPhone(phoneNumber);
			MainFrame.selectedCustomer = c.get(0);
		}
		
		// create a label
		label1 = new JLabel( MainFrame.selectedCustomer.getLabelString() );
		label2 = new JLabel(FORMAT_DATE.format(purchaseDate) );
		
		// add labels to box1
		box1.add( label1 );
		box1.add( Box.createHorizontalGlue() );
		box1.add( label2 );
		box1.setBorder( BorderFactory.createEmptyBorder(10,5,10,5) );

		// create a table model	
		Object[] tableRow = new Object[4];       // [0]=>article; [1]=>price; [2]=>qty ;[3]=>total
		Object[] tableHead = {"Article", "Unit Price", "Qty", "Subtotal"};
		DefaultTableModel tableModel = new DefaultTableModel(tableHead, 0);    // initially no row
		
		// ensure that purchaseList is not null
		if ( purchasedArticles == null || purchasedArticles.isEmpty() )
		{
			tableRow[0] = "(no data)";
			tableRow[1] = "(no data)";
			tableRow[2] = "(no data)";
			tableRow[3] = "(no data)";   		
			tableModel.addRow(tableRow);
		}
		else
		{
			for ( Article record : purchasedArticles)
			{
				// append to the tableRows array    // [0]=> name; [1]=> price; [2]=> qty; [3]= subTotal
				tableRow[0] = record.getName();
				tableRow[1] = FORMAT_AMOUNT.format( record.getPrice() );
				tableRow[2] = record.getQuantity();
				tableRow[3] = FORMAT_AMOUNT.format( record.getSubTotal() ); 
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
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		// set column widths
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(40);
		table.getColumnModel().getColumn(2).setPreferredWidth(20); 
		table.getColumnModel().getColumn(3).setPreferredWidth(90); 
		
		// add table to scroll pane
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(BorderFactory.createLoweredBevelBorder());
		JPanel tablePanel = new JPanel( new BorderLayout() );
		
		Box footerBox = Box.createHorizontalBox();  // for Grand Total
		footerBox.setBorder( BorderFactory.createEmptyBorder(10,0,10,0));
		footerBox.add( Box.createHorizontalGlue() );
		JLabel totalPanel = new JLabel("Grand Total :     $" + FORMAT_AMOUNT.format(grandTotal) );
		totalPanel.setFont( new Font("Arial",Font.PLAIN,16) );
		footerBox.add( totalPanel );
		//footerBox.add( Box.createHorizontalStrut(15) );
		//footerBox.add( new JLabel( "$" + FORMAT_AMOUNT.format(grandTotal) ) );
		footerBox.add( Box.createHorizontalStrut(30) );

		tablePanel.add(scroll, BorderLayout.CENTER);
		tablePanel.add(footerBox, BorderLayout.SOUTH);
		
		// add components to the frame
		this.add(box1, BorderLayout.NORTH);    // box with label & button
		this.add(tablePanel, BorderLayout.CENTER);    // table component with text area
		
		this.setVisible(true);    // show this frame
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
