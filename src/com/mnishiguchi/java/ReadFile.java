package com.mnishiguchi.java;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.Stack;


/**
 * This class provides a few static methods to read data from a file
 */
public class ReadFile
{
	// constants
	public static final String DELIMITER = "\t";
	public static final String FORMAT_DATE = "yyyy-MM-dd HH:mm" ;  // 2014-08-22 16:24
	public static final String PATH = "C:customer_data\\"; 
    
	/**
     * Creates a Scanner object to read a specified file.
     * @param     path of the file to be opened
     * @return     a Scanner object associated with the specified file
     */
    private static Scanner getScanner(String filename)
    {
        Scanner in = null;
    	try
    	{
    		File file = new File(filename);
    		
    		if (file.exists() == false)    //if file doesn't exists, then create it
    		{
    			file.createNewFile();
    		}
    		
    		in = new Scanner(file);
    	}
    	catch (IOException e)
    	{
        	System.out.println("I/O Error, when trying to open a file and create a Scanner object.");
        	System.exit(0);
    	}
    	return in;
    }    
    
    /**
     *  Reads the customer data from a file
     * @return   an ArrayList of all the customers, null if data is empty
     */
    public static ArrayList<Customer> getCustomerList()
    {
    	// open file
    	Scanner in = getScanner( PATH + "customers.txt" );
    	
    	String line = "";                                                                      // temporary storage
    	ArrayList<String> lines = new ArrayList<String>();    // to store raw data
    	String[] data;                                                                         // to store processed data
    	
        while ( in.hasNextLine() )
        {
	    	line = in.nextLine();  // read a line
	
	    	if ( line.equals("") == false)
                lines.add(line);    // if a line has data, add to temporary storage
        }
    	in.close();  // close file
    	
    	// ensure that some customers exist
    	if ( lines.isEmpty())
    		return null;
    	
		//sort the lines
		Collections.sort(lines);
       
		// create a Customer object from each line
    	ArrayList<Customer> customers = new ArrayList<Customer>();
    	String lastName, firstName, phoneNumber, zipCode;
    	Customer.Prefix prefix;
        for (String temp: lines)
        {
        	data = temp.split(DELIMITER); 
        	if (data.length == 5)    // ensure that data has 4 items
        	{
	    		lastName = data[0];
	            firstName = data[1];
	        	phoneNumber = data[2];
	        	zipCode  = data[3];
	        	prefix  =  ( data[4].equals("Mr.") ) ?  Customer.Prefix.MR : Customer.Prefix.MS;
	        	customers.add( new Customer(lastName, firstName, phoneNumber, zipCode, prefix) ) ;	
        	}
        	else
        	{
        		System.out.println("Customer Format Error: not exactly 5 items");
        		System.exit(0);
        	}
        }
    	return customers;
    }
    
    /**
     * Reads the purchase data of a specified customer from a file
     * @param c   a Customer object to represent this customer
     * @return      an ArrayList of this customer's purchase data, null if data is empty
     */
    public static Stack<Purchase>  getPurchaseList(Customer c)
    {
        Stack<Purchase> purchaseData = new Stack<Purchase>();
        Date date = null;
        double amount = 0.0;
        
    	// Date Format 2014-08-22 16:24
    	DateFormat df = new SimpleDateFormat(FORMAT_DATE);
    	
    	// open file   // filename 2021234567.txt
    	Scanner in = getScanner( PATH +  c.getPhoneNumber() + ".txt" );
    	
    	String line = "";    // to store raw data
    	String[] data;       // to store processed data
    	
        while ( in.hasNextLine() )
        {
	    	line = in.nextLine();    // scan a line	
	    	data = line.split(DELIMITER);
	    	
	    	// ensure that data has exactly two items
	    	if (data.length != 2)
	    	{
	    		System.out.println("Excluded a purchase record that does not have exactly 2 items");
	    		continue;
	    	}
	    		
	    	try
	    	{
	    	    // parse date
	    	    date = (Date) df.parse( data[0] );
		    	// parse amount
		    	amount = Double.parseDouble( data[1].replaceAll(",", "") );    // remove commas if any
		        purchaseData.add( new Purchase(date, amount) ) ;
	    	}
	    	catch (ParseException e)
	    	{
	       		System.out.println("Date Parse Error");
	       		continue;
	    	}
	    	catch (NumberFormatException e)
	    	{
	        	System.out.println("Number Parse Error");
	        	continue;
	    	}
	    }

    	in.close();  // close file
    	
    	return purchaseData; 
    }
}
