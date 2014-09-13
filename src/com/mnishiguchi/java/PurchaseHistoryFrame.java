package com.mnishiguchi.java;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private static final DecimalFormat FORMAT_AMOUNT = MainFrame.FORMAT_AMOUNT;
	private static final DateFormat FORMAT_DATE = MainFrame.FORMAT_DATE;
	
	private JTable table;
	private JButton button1, button2;
	private Customer c = MainFrame.selectedCustomer;
	private Stack<Purchase> purchaseList;
	
	// constructor
	public PurchaseHistoryFrame()
	{
		// configuration of the frame
		this.setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Purchase History");
		this.setLocationRelativeTo(null);  // put it at the center of the screen

		// get ustomer's purchase history
		purchaseList =  ReadFile.getPurchaseList(c);
		
		// create a panel with BorderLayout
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		
		// --------------------- create the header -------------------------------
		// create a Box
		Box box1 = Box.createHorizontalBox();
		
		// create button with an event handler
		button1 = new JButton("Add New Purchase");
		OnButtonClickListener handle = new OnButtonClickListener();
		button1.addActionListener(handle);
		
		// add label and button to box1
		box1.add( new JLabel( c.getLabelString() ) );    // a customer info label
		box1.add( Box.createHorizontalGlue() );    // to separate components as far as possible
		box1.add( button1 );
		box1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));    // padding
		
		// --------------------- create the table -------------------------------
		// create a table model	
		Object[] tableRow = new Object[3];       // [0]=>date; [1]=>amount; [2]=>invoiceNumber
		Object[] tableHead = {"Date", "Invoice#", "Subtotal"};
		DefaultTableModel tableModel = new DefaultTableModel(tableHead, 0);    // initially no row
		
		if ( purchaseList == null || purchaseList.isEmpty() )    // ensure that purchaseList is not null
		{
			tableRow[0] = "(no data)";
			tableRow[1] = "(no data)";
			tableRow[2] = "(no data)";
			tableModel.addRow(tableRow);
		}
		else    // prepare a TableModel for table creation
		{
			for ( Purchase record : purchaseList)
			{
				// append to the tableRows array
				tableRow[0] = FORMAT_DATE.format( record.getDate() ) ;
				tableRow[1] = record.getInvoiceNumber();
				tableRow[2] =  FORMAT_AMOUNT.format( record.getAmount() );
				tableModel.addRow(tableRow);
			}
		}
		// create a table
		table = new JTable(tableModel);
		
		// format each table row (align center)
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
		// set column widths
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(90); 
		
		// add table to scroll pane
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(BorderFactory.createLoweredBevelBorder());
		JPanel tablePanel = new JPanel( new BorderLayout() );
		
		Date d = purchaseList.get( purchaseList.size() - 1).getDate();    // oldest date
		String since = FORMAT_DATE.format(d);
		double t = getGrandTotal(purchaseList);    // sum of purchase amounts
		String total =  "$" + FORMAT_AMOUNT.format(t);
		
		Box footerBox = Box.createHorizontalBox();  // for Grand Total
		footerBox.setBorder( BorderFactory.createEmptyBorder(10,0,10,0));
		footerBox.add( Box.createHorizontalGlue() );
		footerBox.add( new JLabel("Since " + since + "        -        Total : " + total) );
		footerBox.add( Box.createHorizontalStrut(60) );
		tablePanel.add(scroll, BorderLayout.CENTER);
		tablePanel.add(footerBox, BorderLayout.SOUTH);
		
		// --------------------- create the footer -------------------------------
		// create buttons with an event handler
		button2 = new JButton("Show Invoice");
		button2.addActionListener(handle);	// reuse event handler
		JPanel buttonPanel = new JPanel( new BorderLayout() );
		buttonPanel.add(button2, BorderLayout.NORTH);
		buttonPanel.setBorder( BorderFactory.createEtchedBorder());
		
		this.add(box1, BorderLayout.NORTH);    // add to frame
		this.add(tablePanel, BorderLayout.CENTER);    // add to frame
		this.add(buttonPanel, BorderLayout.SOUTH);   // add to frame
		
		this.setVisible(true);    // show this frame
	}
	
	/**
	 * @param purchasedArticles - Stack of Article objects that represents invoice's rows
	 * @return sum of each row's subtotal
	 */
	public double getGrandTotal(Stack<Purchase> purchaseList)
	{
		double d = 0.0;
		for (Purchase p : this.purchaseList)
		{
			d += p.getAmount();
		}
		return d;
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
			else if (e.getSource() == button2)    // show invoice
			{
				if ( purchaseList == null ||purchaseList.isEmpty() )    // ensure the list is not empty
				{
					JOptionPane.showMessageDialog( PurchaseHistoryFrame.this, 
							"No invoice to show.", "Message", JOptionPane.INFORMATION_MESSAGE);
					PurchaseHistoryFrame.this.dispose();    // close this frame
				}
				else
				{
					// identify index of the selected row
					int selectedIndex = table.getSelectedRow();  // -1 is returned if no row is selected.
					
					// ensure that a row is selected
					if (selectedIndex == -1)
					{
						JOptionPane.showMessageDialog( PurchaseHistoryFrame.this, 
								"Please select a purchase record.", "Message", 
								JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						// show the invoice of this selected purchase
						String invoiceNumber = purchaseList.get(selectedIndex).getInvoiceNumber();
						new InvoiceFrame( ReadFile.getInvoice(invoiceNumber) );
					}
				}
			}
		}
	}
}
