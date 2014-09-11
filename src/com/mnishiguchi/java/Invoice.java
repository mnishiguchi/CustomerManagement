package com.mnishiguchi.java;

import java.util.Date;
import java.util.Stack;

/** Shows an invoice associated with a specified invoice number */
public class Invoice
{
	// static constants
	public static final long LIFE_SPAN = 31556952000L;    // one year
	public static String[] articles = {
			"Trounsers","M-Suits", "Coats","Shirts","Jackets","Uniforms",
			"Sheets","Dresses", "Skirts","L-Suits","Blouses","Sweaters"
			};
	
	// instance variables
	private String invoiceNumber; 
	private Date purchaseDate;
	private double amount;
	private Stack<Article> purchasedArticles;
	
	// constructor
	public Invoice(String invoiceNumber)
	{
		this.invoiceNumber = invoiceNumber; 
	}
	public Invoice(String invoiceNumber, Date purchaseDate, double amount, Stack<Article> purchasedArticles)
	{
		this.invoiceNumber = invoiceNumber;
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
}