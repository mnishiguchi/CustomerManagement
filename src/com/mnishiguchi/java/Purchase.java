package com.mnishiguchi.java;
import java.util.Collection;
import java.util.Date;


/** Represents a purchase record */
public class Purchase
{
	// instance variables
	private Date date;
	private double amount;
	private String invoiceNumber;
	
	// constructor
	public Purchase(Date date, double amount, String invoiceNumber)
	{
		this.date = date;
		this.amount = amount;
		this.invoiceNumber = invoiceNumber;
	}
	
	// accessor methods
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}	
	public double getAmount()
	{
		return amount;
	}
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	public String getInvoiceNumber()
	{
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber)
	{
		this.invoiceNumber = invoiceNumber;
	}
	
	/**  checks if a Purchase  object is expired by referencing the constant Invoice.LIFE_SPAN.
	*  @return true if the Purchase object is expired; false otherwise
	*/
	public boolean isExpired()
	{
		// get purchaseDate in milliseconds		
		long purchaseDate_millisec = this.getDate().getTime();
		// get today's Date in milliseconds
		long today_millisec = new Date().getTime();
		// check the time span
		return (today_millisec - purchaseDate_millisec) > Invoice.LIFE_SPAN;
	}
}
