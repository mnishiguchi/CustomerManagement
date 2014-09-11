package com.mnishiguchi.java;
import java.util.Date;


/** Represents a purchase record */
public class Purchase
{
	// instance variables
	private Date date;
	private double amount;
	private String invoiceNumber;
	
	// constructor
	//public Purchase(Date date, double amount)
	//{
	//	this.date = date;
	//	this.amount = amount;
	//}
	
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
}
