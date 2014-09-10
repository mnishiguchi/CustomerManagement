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


/**  This class provides a few static methods to read data from a file  */
public class ReadFile
{
	// Static constants
	public static final String DELIMITER = "\t";
	public static final String PATH_CUSTOMER = "C:customer_data\\";
	public static final String PATH_INVOICE = "C:invoice\\";
	public static final DateFormat FORMAT_DATE =
        new SimpleDateFormat("yyyy-MM-dd HH:mm");  // 2014-08-22 16:24
	
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
        	System.out.println("I/O Error in ReadFile.getScanner, when trying to open a file and create a Scanner object.");
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
    	// get data from the file "customers.txt"
    	ArrayList<String> lines = getDataFromFile(PATH_CUSTOMER + "customers.txt");	// to store raw data
    	String[] data;	// to store processed data
    	
    	// ensure that some customers exist
    	if ( lines.isEmpty() )  return null;
    	
		//sort the lines
		Collections.sort(lines);
       
		// create a Customer object from each line
    	ArrayList<Customer> customers = new ArrayList<Customer>();
    	String lastName, firstName, phoneNumber, zipCode;
    	Customer.Prefix prefix;
        for (String temp: lines)
        {
        	data = temp.split(DELIMITER); 
        	if (data.length != 5)    // ensure that data has 4 items
            {
            		System.out.println("Invalid Data format in ReadFile.getCustomerList - data.length should be 5");
            		continue;
            }

	    	lastName = data[0];
	        firstName = data[1];
	        phoneNumber = data[2];
	        zipCode  = data[3];
	        prefix  =  ( data[4].equals("Mr.") ) ?  Customer.Prefix.MR : Customer.Prefix.MS;
	        customers.add( new Customer(lastName, firstName, phoneNumber, zipCode, prefix) ) ;	
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
    	
        // get data from the file 2021234567.txt
        ArrayList<String> lines = getDataFromFile( PATH_CUSTOMER +  c.getPhoneNumber() + ".txt" );	// to store raw data
        
    	String[] data;       // to store processed data
        for ( String line: lines )
        {
	    	data = line.split(DELIMITER);
	    	
	    	// ensure that data has exactly two items
	    	if (data.length != 2)
	    	{
	       		System.out.println("Invalid Data format in ReadFile.getPurchaseList - data.length should be 2");
	       		continue;
	    	}

	    	try
	    	{
	    	    // parse date
	    	    date = (Date) FORMAT_DATE.parse( data[0] );
		    	// parse amount
		    	amount = Double.parseDouble( data[1].replaceAll(",", "") );    // remove commas if any
		        purchaseData.add( new Purchase(date, amount) ) ;
	    	}
	    	catch (ParseException ex)
	    	{
	       		System.out.println("Date Parse Error in ReadFile.getPurchaseList");
	       		System.exit(0);
	    	}
	    	catch (NumberFormatException ex)
	    	{
	        	System.out.println("Number Parse Error in ReadFile.getPurchaseList");
	        	System.exit(0);
	    	}
	    }
    	return purchaseData; 
    }
    
    public static Invoice getInvoice(String invoiceNumber)
    {
    	// create an Invoice object
    	Invoice inv = new Invoice(invoiceNumber);
    	
    	// get data from the invoice file
    	String filePath = PATH_INVOICE + invoiceNumber + ".txt";
    	ArrayList<String> lines = getDataFromFile(filePath);
    	
    	String[] data;	// to store processed data temporarily
    	
    	// get the purchase date from header and add it to the Invoice object
    	try
    	{
    	    String header = lines.get(0);
    	    lines.remove(0);  // remove this line from the ArrayList
    	    Date d = (Date) FORMAT_DATE.parse( header);
    	    inv.setPurchaseDate(d);
    	}
    	catch (ParseException ex)
    	{
    		System.out.println("Date parse error in ReadFile.getInvoice");
    		System.exit(0);
    	}
    	
    	// get purchased articles
    	Stack<Article> purchasedArticles = new Stack<Article>();
    	String name = "";
    	double price = 0.0;
    	int quantity = 0;
        for (String temp: lines)
        {
        	data = temp.split(DELIMITER); 
        	if (data.length != 3)    // ensure that data has 3items
        	{
        		System.out.println("Invalid Data format in ReadFile.getInvoice - data.length should be 3");
        		continue;
        	}
        	// process data and add a new Article to the ArrayList
        	try
        	{
            	name = data[0];
            	price = Double.parseDouble(data[1]);
            	quantity = Integer.parseInt(data[2]);
            	purchasedArticles.add( new Article(name, price, quantity) ) ;	
        	}
        	catch (NumberFormatException ex)
        	{
    	        System.out.println("Number Parse Error in ReadFile.getInvoice");
    	        System.exit(0);
        	}
        }
        // add the ArrayList of purchased articles to the Invoice
    	inv.setPurchasedArticles(purchasedArticles);
    			
    	return inv;
    }
    
    /** Reads data from a file
     * @param	filePath	the path to the file to be read
     * @return	an ArrayList object with each line of the file as an array element
     */
    private static ArrayList<String> getDataFromFile(String filePath)
    {
    	// open file
    	Scanner in = getScanner(filePath);
    	
    	String line = "";                                                                      // temporary storage
    	ArrayList<String> lines = new ArrayList<String>();    // to store raw data
    	
        while ( in.hasNextLine() )
        {
	    	line = in.nextLine();  // read a line
	        
	    	// if a line has data, add to temporary storage
	    	if ( line.equals("") == false)
	    	{
	    		lines.add(line); 
	    	}
        }
    	in.close();  // close file
    	return lines ;
    }
}