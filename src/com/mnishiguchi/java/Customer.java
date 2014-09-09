package com.mnishiguchi.java;


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
}
