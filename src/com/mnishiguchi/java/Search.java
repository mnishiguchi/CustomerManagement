package com.mnishiguchi.java;

import java.util.ArrayList;


/** Provides a few static methods for searching */
public class Search
{
    /**
     * @param name    a last name by which to search for a customer
     * @return result of the search as an array list of Customer objects if there is any, or null if none is found.
     */
    public static ArrayList<Customer> findCustomerByLastName(String name)
    {
        // read all the customers from customers.txt
    	ArrayList<Customer> customerList = ReadFile.getCustomerList();
        
    	// ensure that the list is not empty
    	if ( customerList == null || customerList.isEmpty() )
    		return null;
    	
    	// storage for search result
    	ArrayList<Customer> result = new ArrayList<Customer>();

    	// search 
    	for (Customer c: customerList)
    	{
    		if ( c.getLastName().equalsIgnoreCase(name) )
    			result.add(c);
    	}
    	return result;
    }

    /**
     * @param phoneNumber    a phone number by which to search for a customer
     * @return result of the search as an array list of Customer objects if there is any, or null if none is found.
     */
    public static ArrayList<Customer> findCustomerByPhone(String phoneNumber)
    {
    	// read all the customers from customers.txt
    	ArrayList<Customer> customerList = ReadFile.getCustomerList();

    	// ensure that the list is not empty
    	if ( customerList == null || customerList.isEmpty() )
    		return null;
    	// storage for search result
    	ArrayList<Customer> result = new ArrayList<Customer>();
    	
    	// search 
    	for (Customer c: customerList)
    	{
    		if ( c.getPhoneNumber().equalsIgnoreCase(phoneNumber) )
    			result.add(c);
    	}
    	return result;
	}
	

}
