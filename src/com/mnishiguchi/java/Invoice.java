package com.mnishiguchi.java;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class Invoice
{
    private String invoiceNumber; 
    private Date purchaseDate;
    private Stack<PurchasedArticle> purchasedArticles;
	
	// constructor
    public Invoice(String invoiceNumber)
    {
    	this.invoiceNumber = invoiceNumber; 
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