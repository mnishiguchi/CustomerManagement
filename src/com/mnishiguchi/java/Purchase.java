package com.mnishiguchi.java;
import java.util.Date;


/** Represents a purchase record */
public class Purchase
{
	// instance variables
	private Date date;
	private double amount;
    
    // constructor
    public Purchase(Date date, double amount)
    {
    	this.date = date;
    	this.amount = amount;
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
}
