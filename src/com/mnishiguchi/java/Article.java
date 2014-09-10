package com.mnishiguchi.java;


/** Represents each item on the invoice */
public class Article
{
    // instance variables
	private String name;
    private double price;
    private int quantity;
    private double subTotal;
    
    // constructor
    public Article(String name, double price, int quantity)
    {
    	this.name = name;
    	this.price = price;
    	this.quantity = quantity;
    	this.subTotal = price * quantity;
    }
    
    // accessor methods
	public String getName()
	{
		return name;
	}
	public double getPrice()
	{
		return price;
	}
	public int getQuantity()
	{
		return quantity;
	}
	public double getSubTotal()
	{
		return subTotal;
	}
}
