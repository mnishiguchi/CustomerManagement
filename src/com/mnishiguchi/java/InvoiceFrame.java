package com.mnishiguchi.java;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


public class InvoiceFrame extends JFrame
{
	// constant
	public static final String FORMAT_DATE = "yyyy-MM-dd HH:mm" ;  // 2014-08-22 16:24
	public static final String FORMAT_AMOUNT = "%.2f";      // 1234.56
	
	// instance variables
	private JLabel label1, label2;
    String invoiceNumber; 
    Date purchaseDate;
    Stack<Article> purchasedArticles;
	
	// constructor
	public InvoiceFrame(String invoiceNumber)
	{
		// configuration of the frame
		this.setSize(400, 250);
		this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setTitle("Purchase Details");
	    this.setLocationRelativeTo(null);  // put it at the center of the screen
        
	    // create an Invoice object
        Invoice inv = ReadFile.getInvoice(invoiceNumber);
        
        // ensure that invoice is not null
        // TODO
        
        // get data from invoice
        invoiceNumber = invoiceNumber; 
        purchaseDate = inv.getPurchaseDate();
        purchasedArticles = inv.getPurchasedArticles();
        
	    // create a panel with BorderLayout
	    JPanel panel1 = new JPanel();
	    panel1.setLayout(new BorderLayout());
	    
	    // create a Box
	    Box box1 = Box.createHorizontalBox();
	    
	    // create a label
	    label1 = new JLabel("Invoice Number : " + invoiceNumber);
	    label2 = new JLabel("Date : " + purchaseDate);
	    
	    // add labels to box1
	    box1.add( label1 );
	    box1.add( Box.createHorizontalGlue() );  // to separate components as far as possible
	    box1.add( label2 );
	    box1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	    
    	// Date Format 2014-08-22 16:24
    	DateFormat format1 = new SimpleDateFormat(FORMAT_DATE);
    	
    	// create a table model	
      	Object[] tableRow = new Object[4];       // [0]=>article; [1]=>price; [2]=>qty ;[3]=>total
      	Object[] tableHead = {"Article", "Price", "Qty", "Total"};
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
		    	
		    	// append to the tableRows array
		    	//tableRow[0] = ???;
		    	//tableRow[1] = ???;  
		    	//tableRow[2] =  ???;
		    	//tableRow[3] =  ???; 
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
	    
	    // now frame is ready to be displayed
	    this.setVisible(true);
	}
}
