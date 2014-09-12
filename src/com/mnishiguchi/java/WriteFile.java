package com.mnishiguchi.java;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;


/**
 * This class provides a few static methods to write data from a file
 */
public class WriteFile
{
	// constants
	private static final String DELIMITER = MainFrame.DELIMITER;
	private static final String PATH_CUSTOMER = MainFrame.PATH_CUSTOMER;
	private static final String PATH_INVOICE = MainFrame.PATH_INVOICE;
	private static final DecimalFormat FORMAT_AMOUNT = MainFrame.FORMAT_AMOUNT;
	private static final DateFormat FORMAT_DATE = MainFrame.FORMAT_DATE;
	
	/**
	 * @param filename - the name of a file that a PrintWriter object will be associated with.
	 * @return a - PrintWriter object associated with a specified file if its creation is successful; otherwise null.
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
	 * @param c - an array list of Customer objects
	 * @param filename - a file name, on which to write the customer data.
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
	 * @param c - a Customer object
	 */
	public static void writeCustomer(Customer c)
	{
		String filename1 = PATH_CUSTOMER + "customers.txt";
		
		PrintWriter out = openWriter(filename1);    // open file
		
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
	 * @param c - a Customer object
	 * @param data - an array list of this customer's purchase history
	 */
	public static void writePurchase(Customer c, ArrayList<Purchase> data)
	{
		String filename2 = PATH_CUSTOMER + c.getPhoneNumber() + ".txt";
		PrintWriter out = openWriter(filename2);    // open file
		
		String line = "";    // [0]=>date; [1]=> amount; [2]=>invoice#;
		for (Purchase p : data)    // for each purchase
		{
			line = FORMAT_DATE.format( p.getDate() );
			line += DELIMITER + FORMAT_AMOUNT.format( p.getAmount() );
			line += DELIMITER + p.getInvoiceNumber();
		}
		out.close();  // close file
	}

	/** Writes one purchase data item (date and dollar amount) on a file.
	 * @param c    a Customer object to represent this customer
	 * @param p   a Purchase object to represent a specific purchase record
	 */
	public static void writePurchase(Customer c, Purchase p)
	{
		String filename2 = PATH_CUSTOMER +  c.getPhoneNumber() + ".txt";
		PrintWriter out = openWriter(filename2);    // open file
		
		String line = FORMAT_DATE.format( p.getDate() );    // [0]=>date; [1]=> amount; [2]=>invoice#;
		line += DELIMITER + FORMAT_AMOUNT.format( p.getAmount() );
		line += DELIMITER + p.getInvoiceNumber();
		out.println(line);                          // write it on a line		
		out.close();  // close file
	}
	
	/** Writes an invoice on a file. Does nothing if the same invoice# exists.
	 * @param c - an Invoice object which is to be written on file
	 */
	public static void writeInvoice(Invoice inv)
	{
		String filePath = PATH_INVOICE +  inv.getInvoiceNumber() + ".txt";
		if ( ReadFile.exists(filePath) )
		{
			System.out.println("FilePath |" + filePath + "| exists");
			return;
		}
		
		PrintWriter out = openWriter(filePath);    // open file
		
		// write invoice header    [0]=>phoneNumber ; [1]=>purchaseDate
		String line = genInvoiceHeader(inv);
		if (line == null)	// ensure that line is not empty
		{
			System.out.println("");
			System.exit(0);
		}
		out.println(line);  // write it on the file
		
		// write invoice body    // [0]=> name; [1]=> price; [2]=> qty; 
		ArrayList<String> lines = genInvoiceBoby(inv);
		
		for (String row : lines)
		{
			out.println(row);  // write it on the file
		}
		out.close();  // close file
	}
	
	/**  Generates the header of an invoice file
	 * @param inv - an Invoice object
	 * @return a String object to represent an invoice file's header; null if it doesn't exist
	 * */
	private static String genInvoiceHeader(Invoice inv)
	{
		// [0]=>phoneNumber ; [1]=>purchaseDate
		String line = inv.getPhoneNumber();
		line += DELIMITER +  FORMAT_DATE.format( inv.getPurchaseDate() );
		
		// ensure that line is not empty
		if ( line == null|| line.equals("") ) return null;
		return line;
		}
	
	/**  Generates the body of an invoice file
	 * @param inv - an Invoice object
	 * @return an ArrayList of the lines of an invoice file's body; null if it is empty or error occurs
	 *  */
	private static ArrayList<String> genInvoiceBoby(Invoice inv)
	{
		// get purchased articles for this invoice
		Stack<Article> purchasedArticles = inv.getPurchasedArticles();
		
		ArrayList<String> lines = new ArrayList<String>();
		String line = "";
		for (Article a : purchasedArticles)    // [0]=> name; [1]=> price; [2]=> qty; 
		{
			line = a.getName();
			line += DELIMITER + a.getPrice();
			line += DELIMITER + a.getQuantity();
			lines.add(line);	// add line to the ArrayList
		}
		if ( lines.isEmpty() ) return null;    // check if the ArrayList is empty
		return lines;
	}
}
