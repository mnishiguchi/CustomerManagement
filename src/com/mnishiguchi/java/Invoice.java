package com.mnishiguchi.java;

import java.util.Date;
import java.util.HashMap;
import java.util.Stack;

/** Shows an invoice associated with a specified invoice number */
public class Invoice
{
	// static constants
	public static final long LIFE_SPAN = 31556952000L;    // one year
	
	public static String[] articles = {
			"Trousers","M-Suits", "Coats","Shirts","Jackets","Uniforms",
			"Sheets","Dresses", "Skirts","L-Suits","Blouses","Sweaters"
			};
	
	// instance variables
	private String invoiceNumber;
	private String phoneNumber;
	private Date purchaseDate;
	private double amount;
	private Stack<Article> purchasedArticles;
	
	// constructor
	public Invoice(String invoiceNumber)
	{
		this.invoiceNumber = invoiceNumber; 
	}
	
	public Invoice(String invoiceNumber, String phoneNumber, Date purchaseDate, 
			double amount, Stack<Article> purchasedArticles)
	{
		this.invoiceNumber = invoiceNumber;
		this.phoneNumber = phoneNumber;;
		this.purchaseDate = purchaseDate;
		this.amount = amount;
		this.purchasedArticles = purchasedArticles;
	}

	/**  A static method to check if an Invoice object is expired by referencing the constant Invoice.LIFE_SPAN.
	*  @param purchaseDate			a Date object to represent a purchase date
	*  @return	true if the Invoice object is expired; false otherwise
	*/
	public static boolean isExpired(Date purchaseDate)
	{
		// get purchaseDate in milliseconds		
		long purchaseDate_millisec = purchaseDate.getTime();
		
		// get today's Date in milliseconds
		Date t = new Date();
		long today_millisec = t.getTime();
		
		return (today_millisec - purchaseDate_millisec) < LIFE_SPAN;
	}
	
	/** Checks if the invoice with a specified invoice number already exists.
	 * @param invoiceNumber
	 * @return true if the invoice already exists, else false
	 */
	public static boolean exists(String invoiceNumber)
	{
		String filePath = MainFrame.PATH_INVOICE + invoiceNumber + ".txt";
		return ReadFile.exists(filePath);
	}
	
	public static Invoice findByInvoiceNumber(String invoiceNumber)
	{
		Invoice inv = ReadFile.getInvoice(invoiceNumber);
		return inv;
	}
	
	/**
	 * @param purchasedArticles - Stack of Article objects that represents invoice's rows
	 * @return sum of each row's subtotal
	 */
	public double getGrandTotal()
	{
		double d = 0.0;
		for (Article a : this.purchasedArticles)
		{
			d += a.getSubTotal();
		}
		return d;
	}
	
	// accessor methods
	public String getInvoiceNumber()
	{
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber)
	{
		this.invoiceNumber = invoiceNumber;
	}
	public Date getPurchaseDate()
	{
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}
	public Stack<Article> getPurchasedArticles()
	{
		return purchasedArticles;
	}
	public void setPurchasedArticles(Stack<Article> purchasedArticles)
	{
		this.purchasedArticles = purchasedArticles;
	}
	public double getAmount()
	{
		return amount;
	}
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
}
