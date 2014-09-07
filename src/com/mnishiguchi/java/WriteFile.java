package com.mnishiguchi.java;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * This class provides a few static methods to write data from a file
 */
public class WriteFile
{
    // constants
    public static final String DELIMITER = "\t";
	public static final String FORMAT_DATE = "yyyy-MM-dd HH:mm" ;  // 2014-08-22 16:24
	public static final String FORMAT_AMOUNT = "%.2f";      // 1234.56
	public static final String PATH = "C:customer_data\\"; 
    
	/**
     * @param filename    the name of a file that a PrintWriter object will be associated with.
     * @return a PrintWriter object associated with a specified file if its creation is successful. Otherwise null.
     */	
    private static PrintWriter openWriter(String filename)
    {
    	try
    	{
    		File file = new File(filename);
    		
    		if( file.exists() == false )    //if file doesn't exists, then create it
    		{
    			file.createNewFile();
    		}
    		PrintWriter out = new PrintWriter ( new BufferedWriter( new FileWriter(file, true) ) );
    		return out;
    	}
    	catch (IOException e)
    	{
    		System.out.println("I/O Error, when trying to open a file and create a PrintWriter object.");
    		System.exit(0);
    	}
    	return null;
    }
    
    /**
     * Writes all the customer data in array list onto a specified file.
     * @param c                 an array list of Customer objects
     * @param filename   a file name, on which to write the customer data.
     */
    public static void writeCustomer(ArrayList<Customer> customers)
    {
    	String filename1 = PATH + "customers.txt";
    	
    	// open file
    	PrintWriter out = openWriter(filename1);
    	
    	String line;
    	for (Customer c: customers)    // for each item
        {
        	// write it on a line
        	line = c.getLastName();
        	line += DELIMITER + c.getFirstName();
        	line += DELIMITER + c.getPhoneNumber();
        	line += DELIMITER + c.getZipCode();
        	line += DELIMITER + c.getPrefix();
        	out.println(line);
        }
    	out.close();  // close file
    }       
    
    /**
     * @param c
     */
    public static void writeCustomer(Customer c)
    {
    	String filename1 = PATH + "customers.txt";
    	
    	// open file
    	PrintWriter out = openWriter(filename1);
    	
        // write it on a line
        String line = c.getLastName();
        line += DELIMITER + c.getFirstName();
        line += DELIMITER + c.getPhoneNumber();
        line += DELIMITER + c.getZipCode();
        line += DELIMITER + c.getPrefix();
        out.println(line);

    	out.close();  // close file
    }   
    
    /**
     * Writes an arrray of purchase data items(date and dollar amount) on a file.
     * @param c          a Customer object
     * @param data    an array list of this customer's purchase history
     */
    public static void writePurchase(Customer c, ArrayList<Purchase> data)
    {
        // Date Format
    	DateFormat df = new SimpleDateFormat(FORMAT_DATE);
    	
		// filename 2021234567.txt
		String filename2 = PATH + c.getPhoneNumber() + ".txt";
		
		// open file
    	PrintWriter out = openWriter(filename2); 	
    	
    	String line = "";
    	for (Purchase p: data)                   // for each purchase
    	{
        	line = df.format( p.getDate() );
        	line += DELIMITER + String.format( FORMAT_AMOUNT, p.getAmount() ); 
        }
        out.close();  // close file
    }

    /**
     * Writes one purchase data item (date and dollar amount) on a file.
     * @param c    a Customer object to represent this customer
     * @param p   a Purchase object to represent a specific purchase record
     */
    public static void writePurchase(Customer c, Purchase p)
    {
    	// Date Format 2014-08-22 16:24
    	DateFormat df = new SimpleDateFormat(FORMAT_DATE);
    	
		// filename 2021234567.txt
		String filename2 = PATH +  c.getPhoneNumber() + ".txt";
		
		// open file
    	PrintWriter out = openWriter(filename2); 	
    	
    	String line = df.format( p.getDate() );
        line += DELIMITER + String.format( FORMAT_AMOUNT, p.getAmount() );    
        out.println(line);                          // write it on a line
        
        out.close();  // close file
    }
}
