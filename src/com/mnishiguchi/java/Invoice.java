package com.mnishiguchi.java;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class Invoice
{
    private String invoiceNumber; 
    private Date purchaseDate;
    private String amount;
    private Stack<PurchasedArticle> purchasedArticles;
	
	// constructor
    public Invoice(String invoiceNumber)
    {
    	this.invoiceNumber = invoiceNumber; 
    }
    public Invoice(String invoiceNumber, Date purchaseDate, String amount, Stack<PurchasedArticle> purchasedArticles)
    {
    	this.invoiceNumber = invoiceNumber;
    	this.purchaseDate = purchaseDate;
    	this.amount = amount;
    	this.purchasedArticles = purchasedArticles;
    }
    
    /**  A static method to check if an Invoice object is expired.
     *  @param purchaseDate			a Date object to represent a purchase date
     *  @param millisec						a life span in milliseconds
     *  @return	true if the Invoice object is expired; false otherwise
     */
    public static boolean isExpired(Date purchaseDate, long life_millisec)
	{
        // get purchaseDate in milliseconds		
		long purchaseDate_millisec = purchaseDate.getTime();
		
		// get today's Date in milliseconds
		Date t = new Date();
		long today_millisec = t.getTime();
		
    	return (today_millisec - purchaseDate_millisec) < life_millisec;
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
	public Stack<PurchasedArticle> getPurchasedArticles()
	{
		return purchasedArticles;
	}
	public void setPurchasedArticles(Stack<PurchasedArticle> purchasedArticles)
	{
		this.purchasedArticles = purchasedArticles;
	}
}