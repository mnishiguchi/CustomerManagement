package com.mnishiguchi.java;

import java.util.Date;
import java.util.Stack;

/** Shows draft of new invoice. 
 * You can add an article to be purchased one by one. 
 * You can create an invoice file (/invoice/000000.txt) 
 * and save the data in it by clicking "Create Invoice" button.
 * */
public class NewInvoiceFrame
{
	// static variables - ingredients for creating an invoice
	public static String invoiceNumber; 
    public static Date purchaseDate;
    public static double amount;
	public static Stack<Article> purchasedArticles = new Stack<Article>();
	
	// constructor
	public NewInvoiceFrame()
	{
		
	}
}
