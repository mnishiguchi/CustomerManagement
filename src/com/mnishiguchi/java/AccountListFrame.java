package com.mnishiguchi.java;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class AccountListFrame extends JFrame
{
	// instance variables
	private JTable table;
	private JButton button1;
	private ArrayList<Customer> customers;
	
	// constructor
	public AccountListFrame( ArrayList<Customer> customers )
	{
	    // remember the passed customer list
		this.customers = customers;
		
		// configuration of the frame
		this.setSize(400, 250);
		this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setTitle("Account List");
	    this.setLocationRelativeTo(null);  // put it at the center of the screen
	    
	    // ensure customer exist
	    if (customers == null || customers.isEmpty() )
	    {
			JOptionPane.showMessageDialog( this, 
					"There is no customer to show!", "Message", JOptionPane.INFORMATION_MESSAGE);
	    	this.dispose();    // close this frame
	    	return;                  // end this procedure right now
	    }
	    
    	// create a table model
      	Object[] tableRow = new Object[5];       
      	// [0]=>sufix; [1]=>lastName; [2]=>firstName; [3]=>phoneNumber; [4]=>zipCode
      	Object[] tableHead = {"", "Last Name", "First Name", "Telephone", "Zip Code"};
      	DefaultTableModel tableModel = new DefaultTableModel(tableHead, 0);    // initially no row
      	
      	// create table rows
	    for (Customer c: customers)
	    {
	    	// append to the tableRows array
	    	tableRow[0] = c.getPrefix();
	    	tableRow[1] = c.getLastName();
	    	tableRow[2] = c.getFirstName();
	    	tableRow[3] = formatPhoneNumber( c.getPhoneNumber());
	    	tableRow[4] = c.getZipCode();
	     	tableModel.addRow(tableRow);
	    }
    	// create a table
	    table = new JTable(tableModel);
      	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      	
	    // set column format (align center)
      	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      	centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
      	table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
      	table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
      	table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
      	table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
      	table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

	    // set column widths
      	table.getColumnModel().getColumn(0).setPreferredWidth(33);
      	table.getColumnModel().getColumn(1).setPreferredWidth(90);
      	table.getColumnModel().getColumn(2).setPreferredWidth(90); 
      	table.getColumnModel().getColumn(3).setPreferredWidth(100); 
      	table.getColumnModel().getColumn(4).setPreferredWidth(60);
      	
	    // add table to scroll pane
	    JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
	    		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scroll.setBorder(BorderFactory.createLoweredBevelBorder()); 
	    
	    // add scroll to the frame
	    this.add(scroll, BorderLayout.CENTER);
	    
	    // create buttons with an event handler
	    button1 = new JButton("     Show Purchase History     ");
	    OnButtonClickListener handle = new OnButtonClickListener();
	    button1.addActionListener(handle);
	    
	    // create a panel and add button to it
	    JPanel panel = new JPanel();
	    panel.add( button1 );
	    
	    // add button1 to the frame
	    this.add(panel, BorderLayout.SOUTH);
	    
	    this.setVisible(true);    // show this frame
	}
	
	/** Hyphenates a 10-digit numeric string (0000000000=>000-000-0000 */
	private String formatPhoneNumber(String phoneNumber)
	{
		String s = "";
		for ( int i = 0, end = phoneNumber.length(); i < end; i++)
		{
			if (i == 3 || i == 6)
			{
				s  += "-";
			}
			s += phoneNumber.charAt(i);
		}
		return s;
	}
	
	/** Inner class to listen for a click on buttons */
	private class OnButtonClickListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == button1)                       // respond to button1
			{
				if ( customers == null || customers.isEmpty() )
				{
					JOptionPane.showMessageDialog( AccountListFrame.this, 
							"No customer data to show!", "Message", 
							JOptionPane.INFORMATION_MESSAGE);	
					AccountListFrame.this.dispose();    // close this frame
				}
				else
				{
					// identify index of the selected row
					int selectedIndex = table.getSelectedRow();  // -1 is returned if no row is selected.
					
					// ensure that a row is selected
					if (selectedIndex == -1)
					{
						JOptionPane.showMessageDialog( AccountListFrame.this, 
								"Please select a customer.", "Message", 
								JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						// remember this customer
						MainFrame.selectedCustomer = customers.get(selectedIndex);
						
						// show purchase data of this selected customer
						new PurchaseHistoryFrame();
						AccountListFrame.this.dispose();     // close this frame
					}
				}
			}
		}
	}
}
