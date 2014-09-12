package com.mnishiguchi.java;

import java.util.ArrayList;


/** Represents a customer */
public class Customer
{
	// instance variables
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String zipCode;
    public enum Prefix {MR, MS};
    private Prefix  prefix;  // MR, MS
    
    // constructor
    public Customer( String lastName, String firstName, String phoneNumber, String zipCode, Prefix prefix)
    {
    	this.lastName = lastName;
    	this.firstName = firstName;
    	this.phoneNumber = phoneNumber;
    	this.zipCode = zipCode;
    	this.prefix = prefix;
    }
    
    // accessor methods
	public String getLastName()
	{
		return this.lastName;
	}
	public String getFirstName()
	{
		return this.firstName;
	}
	public  String getPhoneNumber()
	{
		return this.phoneNumber;
	}
	public  String getZipCode()
	{
		return this.zipCode;
	}
	public String getPrefix()
	{
		String p = (this.prefix == Prefix.MR) ? "Mr." : "Ms.";
		return p;
	}
	public String getLabelString()
	{
		String s= this.getPrefix() + " " + this.lastName + " - " + this.phoneNumber;
		return s;
	}

	public boolean exists()
	{
		// read all the customers from customers.txt
		ArrayList<Customer> customerList = ReadFile.getCustomerList();    
		
		// ensure that the list is not empty
		if ( customerList == null || customerList.isEmpty() )
		{
			return false;
		}
		
		String lastName = this.getLastName();
		String firstName = this.getFirstName();
		String phoneNumber = this.getPhoneNumber();
		String zipCode = this.getZipCode();
		
		for (Customer c : customerList)
		{
			if ( c.getLastName().equalsIgnoreCase(lastName) &&
					c.getFirstName().equalsIgnoreCase(firstName) &&
					c.getPhoneNumber().equals(phoneNumber) &&
					c.getZipCode().equals(zipCode) )
			{
				return true;
			}
		}
		return false;
	}
}
