package com.mnishiguchi.java;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Stack;


/**
 * This class provides a few static methods to write data from a file
 */
public class WriteFile
{
    // constants
    public static final String DELIMITER = "\t";
	public static final String PATH_CUSTOMER = "C:customer_data\\";
	public static final String PATH_INVOICE = "C:invoice\\";
	public static final String FORMAT_AMOUNT = "%.2f";      // 1234.56
	public static final DateFormat FORMAT_DATE =
        new SimpleDateFormat("yyyy-MM-dd HH:mm");  // 2014-08-22 16:24
	
	/**
     * @param	filename	 the name of a file that a PrintWriter object will be associated with.
     * @return	a PrintWriter object associated with a specified file if its creation is successful. Otherwise null.
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
    		System.out.println("I/O Error in WriteFile.openWriter, when trying to open a file and create a PrintWriter object.");
    		System.exit(0);
    	}
    	return null;
    }
    
    /** Writes all the customer data in array list onto a specified file.
     * @param c				an array list of Customer objects
     * @param filename	a file name, on which to write the customer data.
     */
    public static void writeCustomer(ArrayList<Customer> customers)
    {
    	String filename1 = PATH_CUSTOMER + "customers.txt";
    	
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
    
    /** writeCustomer(Customer c)
     * @param c	a Customer object
     */
    public static void writeCustomer(Customer c)
    {
    	String filename1 = PATH_CUSTOMER + "customers.txt";
    	
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
    
    /** Writes an arrray of purchase data items(date and dollar amount) on a file.
     * @param c		a Customer object
     * @param data	an array list of this customer's purchase history
     */
    public static void writePurchase(Customer c, ArrayList<Purchase> data)
    {
		// filename 2021234567.txt
		String filename2 = PATH_CUSTOMER + c.getPhoneNumber() + ".txt";
		
		// open file
    	PrintWriter out = openWriter(filename2); 	
    	
    	String line = "";
    	for (Purchase p: data)                   // for each purchase
    	{
        	line = FORMAT_DATE.format( p.getDate() );
        	line += DELIMITER + String.format( FORMAT_AMOUNT, p.getAmount() ); 
        }
        out.close();  // close file
    }

    /** Writes one purchase data item (date and dollar amount) on a file.
     * @param c    a Customer object to represent this customer
     * @param p   a Purchase object to represent a specific purchase record
     */
    public static void writePurchase(Customer c, Purchase p)
    {
		// filename 2021234567.txt
		String filename2 = PATH_CUSTOMER +  c.getPhoneNumber() + ".txt";
		
		// open file
    	PrintWriter out = openWriter(filename2); 	
    	
    	String line = FORMAT_DATE.format( p.getDate() );
        line += DELIMITER + String.format( FORMAT_AMOUNT, p.getAmount() );    
        out.println(line);                          // write it on a line
        
        out.close();  // close file
    }
    
    /** Writes an invoice on a file.
     * @param c	an Invoice object which is to be written on file
     */
    public static void writeInvoice(Invoice inv)
    {
		// filename invoice_number.txt
		String filename3 = PATH_INVOICE +  inv.getInvoiceNumber() + ".txt";
		
		// open file
    	PrintWriter out = openWriter(filename3); 	
    	
    	// write purchase date as a file header
    	String line = genInvoiceHeader(inv);
    	if (line == null)	// ensure that line is not empty
    	{
    		System.out.println("");
    		System.exit(0);
    	}
        out.println(line);  // write it on the file
        
        // write purchased articles as a file body
        ArrayList<String> lines = genInvoiceBoby(inv);
        
        for (String row : lines)
        {
        	out.println(row);  // write it on the file
        }
        out.close();  // close file
    }
    
    /**  Generates a String for the header of an invoice file
     * @param inv		an Invoice object
     * @return	a String object to represent an invoice file's header; null if it doesn't exist
     */
    private static String genInvoiceHeader(Invoice inv)
    {
    	String line = FORMAT_DATE.format( inv.getPurchaseDate() );
    	
    	// ensure that line is not empty
    	if ( line == null|| line.equals("") ) return null;
    	
    	return line;
    }
    
    /**  Generates an ArrayList of Strings for the body of an invoice file
     * @param inv		an Invoice object
     * @return	an ArrayList of the lines of an invoice file's body; null if it is empty or error occurs
     */
    private static ArrayList<String> genInvoiceBoby(Invoice inv)
    {
    	// get purchased articles for this invoice
    	Stack<Article> purchasedArticles = inv.getPurchasedArticles();
    	
    	ArrayList<String> lines = new ArrayList<String>();
    	String line = "";
    	for (Article a : purchasedArticles)
    	{
    		line = DELIMITER + a.getName();
            line += DELIMITER + a.getPrice();
            line += DELIMITER + a.getQuantity();
            lines.add(line);	// add line to the ArrayList
    	}
    	if ( lines.isEmpty() ) return null;	// check if the ArrayList is empty
    	return lines;
    }
}