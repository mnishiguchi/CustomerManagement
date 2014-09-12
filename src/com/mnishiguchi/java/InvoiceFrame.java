package com.mnishiguchi.java;
import java.awt.BorderLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	public static final String FORMAT_AMOUNT = MainFrame.FORMAT_AMOUNT;
	public static final DateFormat FORMAT_DATE = MainFrame.FORMAT_DATE;
	
	// instance variables
	private JLabel label1, label2;
	
	private String invoiceNumber;
	private String phoneNumber;
	private Date purchaseDate;
	private double amount;
	private Stack<Article> purchasedArticles;
	
	// constructors
	public InvoiceFrame(String invoiceNumber)
	{
		//get invoice from the file
		Invoice inv = ReadFile.getInvoice(invoiceNumber);
		if (inv == null) 
		{
			System.out.println("Error occurred (InvoiceFrame constructor) - inv == null");
			return;
		}
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
		amount = inv.getAmount();
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
		
		// create a label
		label1 = new JLabel( MainFrame.selectedCustomer.getLabelString() );
		label2 = new JLabel( "Purchased on : " + FORMAT_DATE.format(purchaseDate) );
		
		// add labels to box1
		box1.add( label1 );
		box1.add( Box.createHorizontalGlue() );
		box1.add( label2 );
		box1.setBorder( BorderFactory.createEmptyBorder(5, 10, 5, 10) );
		
		// create a table model	
		Object[] tableRow = new Object[4];       // [0]=>article; [1]=>price; [2]=>qty ;[3]=>total
		Object[] tableHead = {"Article", "Unit Price", "Qty", "Total"};
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
			Article record;    // temporary storage for each purchase record
			while (purchasedArticles.isEmpty() == false)
			{
				// pop a record
				record = purchasedArticles.pop();
				
				// append to the tableRows array    // [0]=> name; [1]=> price; [2]=> qty; [3]= subTotal
				tableRow[0] = record.getName();
				tableRow[1] = String.format( FORMAT_AMOUNT, record.getPrice() );
				tableRow[2] = record.getQuantity();
				tableRow[3] = String.format( FORMAT_AMOUNT, record.getSubTotal() ); 
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
		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(BorderFactory.createLoweredBevelBorder());
		
		// add components to the frame
		this.add(box1, BorderLayout.NORTH);    // box with label & button
		this.add(scroll);                                                // table component with text area
		
		this.setVisible(true);    // show this frame
	}
}
